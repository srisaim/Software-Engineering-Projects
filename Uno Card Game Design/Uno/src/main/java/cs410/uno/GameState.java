package cs410.uno;

import java.util.List;
import java.util.Stack;
import java.util.Random;
import java.util.ArrayList;

public class GameState {
    // Variable currentIndex keeps track of the current player's index.
    // The deck represents the draw pile where players draw cards from.
    // The players list is the list of Player objects in the game.
    // The discardPile represents the pile where players use or place their cards.
    // The isClockwise keeps track of the current direction of the gameplay.
    // The currentWildCardColor stores the current color which was selected for the wild cards use.
    private int currentIndex;
    private Deck deck;
    private List<Player> players;
    private Stack<Card> discardPile;
    private boolean isClockwise = true;
    private Card.Color currentWildCardColor;

    // This initializes a new game with the specified amount of players and cards using initializeGame.
    // It returns GS, which is a GameState object representing the game initialized.
    public static GameState startGame(int countPlayers, int countInitialCardsPerPlayer, int countDigitCardsPerColor, int countSpecialCardsPerColor, int countWildCards) {
        GameState GS = new GameState();
        GS.initializeGame(countPlayers, countInitialCardsPerPlayer, countDigitCardsPerColor, countSpecialCardsPerColor, countWildCards);
        return GS;
    }

    // Simply returns the current player playing.
    public Player getCurrentPlayer() {
        return players.get(currentIndex);
    }

    // Simply returns the top card on the discard pile.
    public Card getTopCardOfDiscardPile() {
        return discardPile.peek();
    }

    // Simply returns the list of players active in the game.
    public List<Player> getPlayers() {
        return players;
    }

    // Simply checks if the draw pile is empty.
    public boolean isDrawPileEmpty() {
        return deck.isEmpty();
    }

    // Simply checks if the discard pile is not empty.
    public boolean isDiscardPileNotEmpty() {
        return !discardPile.isEmpty();
    }

    // Simply checks if the game is over, where a player runs out of cards.
    // Checks if any player has an empty hand, no cards.
    public boolean isGameOver() {
        for (Player player : players) {
            if (player.isHandEmpty()) {
                return true;
            }
        }
        return false;
    }

    // This Initializes the game by setting it up with certain amount of players, creates and shuffles the deck,
    // distributes the set amount of cards to each player, and places the first card on the discard pile to start game.
    // In a scenario where the first card is a wild card, it also sets an initial wild card color to start the game.
    private void initializeGame(int countPlayers, int countInitialCardsPerPlayer, int countDigitCardsPerColor, int countSpecialCardsPerColor, int countWildCards) {
        deck = new Deck(countDigitCardsPerColor, countSpecialCardsPerColor, countWildCards);
        players = new ArrayList<>();

        for (int x = 0; x < countPlayers; x++) {
            Player p = new Player("Player " + (x + 1));
            for (int y = 0; y < countInitialCardsPerPlayer; y++) {
                p.addCard(deck.drawCard());
            }
            players.add(p);
        }

        discardPile = new Stack<>();
        discardPile.push(deck.drawCard());
        Card firstCard = discardPile.peek();

        if (firstCard.getType() == Card.Type.WILD) {
            currentWildCardColor = getRandomColor();
        } else {
            currentWildCardColor = firstCard.getColor();
        }
    }

    // This mostly manages and overlooks the actions of a single player during their turn.
    // The current player playing will try to play a card that matches the top card on the discard pile.
    // If the player cannot play a card during their turn, they draw a new card from the draw pile.
    // And after playing or drawing, runOneTurn quickly checks if there is a player with no cards, meaning they won,
    // if not, it's the next player's turn in the rotation.
    public void runOneTurn() {
        if (isGameOver()) {
            System.out.println("Game Over!");
            return;
        }

        Player cPlayer = players.get(currentIndex);
        Card topCard = discardPile.peek();
        System.out.println("It's " + cPlayer.getName() + "'s turn. Top card: " + topCard);
        Card playedCard = cPlayer.playCard(topCard, currentWildCardColor);

        if (playedCard == null) {
            refreshDrawPileFromDiscardPile();
            Card drawnCard = deck.drawCard();
            System.out.println(cPlayer.getName() + " draws a card.");
            cPlayer.addCard(drawnCard);
            playedCard = cPlayer.playCard(topCard, currentWildCardColor);
        }

        if (playedCard != null) {
            System.out.println(cPlayer.getName() + " plays " + playedCard);
            discardPile.push(playedCard);
            applyCardEffects(playedCard);
        } else {
            System.out.println(cPlayer.getName() + " couldn't play a card.");
        }

        if (!cPlayer.isHandEmpty()) {
            determineNextPlayer(playedCard);
            System.out.println(cPlayer.getName() + " has " + cPlayer.getHand().size() + " card(s) left.");
        } else {
            System.out.println("UNO, " + cPlayer.getName() + " has won!");
        }
    }

    // This is for initiating the effects of all the cards playable in the game.
    // Using switch statements to handle the different card types, the special cards play as the following:
    //      Skip basically skips the next player's turn.
    //      Reverse changes the rotation of the gameplay.
    //      Draw Two makes the next player in the rotation draw two cards and also lose their turn.
    //      Wild Cards have the ability to change the current active color.
    private void applyCardEffects(Card card) {
        switch (card.getType()) {
            case SKIP:
                currentIndex = (currentIndex + (isClockwise ? 1 : -1) + players.size()) % players.size();
                break;
            case REVERSE:
                isClockwise = !isClockwise;
                if (players.size() == 2) {
                    currentIndex = (currentIndex + 1) % players.size();
                }
                break;
            case DRAW_TWO:
                int nextIndex = (currentIndex + (isClockwise ? 1 : -1) + players.size()) % players.size();
                players.get(nextIndex).addCard(deck.drawCard());
                players.get(nextIndex).addCard(deck.drawCard());
                System.out.println("Player " + (nextIndex + 1) + " draws two cards and their turn is skipped.");
                break;
            case WILD:
                Player cPlayer = players.get(currentIndex);
                currentWildCardColor = cPlayer.chooseColorForWildCard();
                System.out.println(cPlayer.getName() + " changes the color to " + currentWildCardColor);
                break;
        }
    }

    // This generates a random color following the standard UNO colors, excluding WILD.
    // It's basically used when a wild card is played.
    private Card.Color getRandomColor() {
        Random rand = new Random();
        Card.Color[] col = {Card.Color.RED, Card.Color.BLUE, Card.Color.GREEN, Card.Color.YELLOW};
        return col[rand.nextInt(col.length)];
    }

    // This calculates which player should play for the next turn.
    // It follows the current rotation of the gameplay and any changes made by special cards.
    // Keeps updating currentIndex, which is used to find the next player.
    // This method also has handles a scenario where if a Draw Two card is played, it also skips the next player's turn.
    private void determineNextPlayer(Card playedCard) {
        currentIndex = (currentIndex + (isClockwise ? 1 : -1) + players.size()) % players.size();

        if (playedCard != null && playedCard.getType() == Card.Type.DRAW_TWO) {
            currentIndex = (currentIndex + (isClockwise ? 1 : -1) + players.size()) % players.size();
        }
    }

    // Simply creates a new draw pile if the existing one runs out of cards.
    // It basically takes all the cards from the discard pile, shuffles them, and makes it into a new draw pile.
    // This ensures that the game can continue if all the cards in the draw pile have been used.
    private void refreshDrawPileFromDiscardPile() {
        if (isDrawPileEmpty() && isDiscardPileNotEmpty()) {
            Card topCard = discardPile.pop();

            while (isDiscardPileNotEmpty()) {
                deck.addCard(discardPile.pop());
            }

            deck.shuffle();

            discardPile.push(topCard);
        }
    }
}
