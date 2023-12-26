import java.util.List;
import java.util.HashSet;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.stream.Collectors;
import com.google.gson.Gson;

public class GameState {
    final private List<Player> players;
    final private Deck deck;
    final private Deck discardPile;
    private boolean roundEnded;
    private int currentPlayerIndex;

    // 1 deck is used for 2-4 players.
    // 2 decks are used for 5-7 players.
    public GameState(int numberOfPlayers, Scanner scanner) {
        players = new ArrayList<>();
        int numDecks = (numberOfPlayers >= 5) ? 2 : 1;
        deck = new Deck(numDecks);
        discardPile = new Deck();
        initializePlayers(numberOfPlayers, scanner);
        dealCards();
        startDiscardPile();
        currentPlayerIndex = 0;
        roundEnded = false;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public Deck getDiscardPile() {
        return discardPile;
    }

    public Player getCurrentPlayer() {
        return players.get(currentPlayerIndex);
    }

    private void initializePlayers(int numberOfPlayers, Scanner scanner) {
        for (int i = 1; i <= numberOfPlayers; i++) {
            System.out.print("Enter name for Player " + i + ": ");
            String playerName = scanner.nextLine();
            players.add(new Player(playerName));
        }
    }

    private int determineCardsPerPlayer(int numberOfPlayers) {
        if (numberOfPlayers == 2) return 10;
        if (numberOfPlayers <= 4) return 7;
        return 6;
    }

    private void dealCards() {
        int cardsPerPlayer = determineCardsPerPlayer(players.size());
        for (Player player : players) {
            for (int i = 0; i < cardsPerPlayer; i++) {
                Card drawnCard = deck.drawCard();
                if (drawnCard != null) {
                    player.getHand().add(drawnCard);
                }
            }
        }
    }

    private void startDiscardPile() {
        Card topCard = deck.drawCard();
        if (topCard != null) {
            discardPile.addCard(topCard);
        }
    }

    public void nextTurn() {
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
    }

    public void playerDrawsCard(boolean fromDiscard) {
        Player currentPlayer = players.get(currentPlayerIndex);
        if (fromDiscard && !discardPile.isEmpty()) {
            currentPlayer.drawCard(discardPile, true);
        } else if (!deck.isEmpty()) {
            currentPlayer.drawCard(deck, false);
        } else {
            deck.reshuffleDiscardIntoDeck(discardPile);
            currentPlayer.drawCard(deck, false);
        }
    }

    public boolean isRoundEnded() {
        return roundEnded;
    }

    public boolean playerDiscardsCard(Card card) {
        Player currentPlayer = players.get(currentPlayerIndex);

        if (currentPlayer.canDiscard(card)) {
            boolean discarded = currentPlayer.discardCard(card, discardPile);

            if (discarded && currentPlayer.getHand().isEmpty()) {
                currentPlayer.setGameWinner(true);
                roundEnded = true;
            }

            return discarded;
        }

        return false;
    }

    public boolean playerFormsMeld(List<Card> cards) {
        Player currentPlayer = players.get(currentPlayerIndex);

        if (new HashSet<>(currentPlayer.getHand()).containsAll(cards)) {
            if (currentPlayer.formMeld(cards)) {
                currentPlayer.getHand().removeAll(cards);
                return true;
            } else {

                System.out.println("Can't Meld. Please try a different action.");
            }
        } else {

            System.out.println("Can't Meld. You do not have all the required cards.");
        }

        return false;
    }

    public boolean playerLaysOff(int targetPlayerIndex, int meldIndex, List<Card> cards) {
        if (targetPlayerIndex >= 0 && targetPlayerIndex < players.size()) {
            Player targetPlayer = players.get(targetPlayerIndex);
            Meld targetMelds = targetPlayer.getMelds();

            if (meldIndex >= 0 && meldIndex < targetMelds.getMelds().size() && targetMelds.layoff(meldIndex, cards)) {
                Player currentPlayer = players.get(currentPlayerIndex);
                currentPlayer.getHand().removeAll(cards);
                return true;
            } else {

                System.out.println("Can't Layoff. Please try a different action.");
            }
        }

        return false;
    }

    private static void displayHand(Player player) {
        System.out.println("Your hand:");
        List<Card> hand = player.getHand();
        for (int i = 0; i < hand.size(); i++) {
            System.out.println((i + 1) + ": " + hand.get(i));
        }
    }

    private void displayAllMelds() {
        System.out.println("Current Melds on Table:");
        for (int i = 0; i < players.size(); i++) {
            System.out.println("Player " + (i + 1) + " Melds:");
            List<List<Card>> melds = players.get(i).getMelds().getMelds();
            for (int j = 0; j < melds.size(); j++) {
                System.out.println("  Meld " + (j + 1) + ": " + melds.get(j));
            }
        }
    }



    // Wasn't able to full incorporate JSON communication to the Front-End.
    // Creating a method to serialize GameState to JSON.
    public String toJson() {
        Gson gson = new Gson();
        Collectors Collectors = null;
        var tempState = new Object() {
            final String type = "GameState";
            final List<String> playerStates = players.stream().map(Player::toJson).collect(java.util.stream.Collectors.toList());
            final String deckState = deck.toJson();
            final String discardPileState = discardPile.toJson();
            final int currentPlayerIndex = GameState.this.currentPlayerIndex;
            final boolean roundEnded = GameState.this.roundEnded;
        };
        return gson.toJson(tempState);
    }

    public static GameState fromJson(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, GameState.class);
    }

    public static String readJsonFromStdin() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }



    // The main function to initiate the Rummy Game.
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to Basic Rummy!");
        System.out.println("Rules: Players need to draw and discard cards in order to form melds or lay offs.");
        System.out.println("A meld can be 3+ cards of the same rank or it can be a run of 3+ cards in a sequence with the same suit.");
        System.out.println("A player wins by discarding all their cards from their hand.");
        System.out.println("The winner's score is calculated based on the remaining cards in other players' hands.\n");

        System.out.print("Enter the number of players (2-7): ");
        int numOfPlayers = scanner.nextInt();
        while (numOfPlayers < 2 || numOfPlayers > 7) {
            System.out.print("Invalid number of players. Please enter a number between 2 and 7: ");
            numOfPlayers = scanner.nextInt();
        }
        scanner.nextLine();

        GameState gameState = new GameState(numOfPlayers, scanner);

        while (!gameState.isRoundEnded()) {
            Player currentPlayer = gameState.getCurrentPlayer();
            System.out.println(currentPlayer.getName() + "'s turn:");

            displayHand(currentPlayer);

            Card topDiscard = gameState.getDiscardPile().peekTopCard();
            System.out.println("Top card of discard pile: " + (topDiscard != null ? topDiscard.toString() : "None"));

            gameState.displayAllMelds();

            System.out.println("Draw a card. Choose (1) for draw pile, (2) for discard pile:");
            int drawChoice = scanner.nextInt();
            boolean drawFromDiscard = drawChoice == 2;
            gameState.playerDrawsCard(drawFromDiscard);

            displayHand(currentPlayer);

            boolean actionSuccessful;
            do {
                actionSuccessful = true;

                System.out.println("Do you want to meld or lay off? (1) Meld, (2) Layoff, (3) Skip:");
                int actionChoice = scanner.nextInt();
                scanner.nextLine();

                if (actionChoice == 1) {
                    System.out.println("Select the indices of cards to form a meld (separated by space):");
                    String[] cardIndices = scanner.nextLine().split(" ");
                    List<Card> cardsToMeld = new ArrayList<>();
                    for (String index : cardIndices) {
                        int idx = Integer.parseInt(index.trim()) - 1;
                        if (idx >= 0 && idx < currentPlayer.getHand().size()) {
                            cardsToMeld.add(currentPlayer.getHand().get(idx));
                        } else {
                            System.out.println("Invalid card index: " + (idx + 1));
                            actionSuccessful = false;
                            break;
                        }
                    }
                    if (actionSuccessful) {
                        actionSuccessful = gameState.playerFormsMeld(cardsToMeld);
                        if (actionSuccessful) {
                            displayHand(currentPlayer);
                        }
                    }
                } else if (actionChoice == 2) {
                    System.out.println("Enter the target player index and meld index (separated by space):");
                    String[] inputs = scanner.nextLine().split(" ");
                    int targetPlayerIndex = Integer.parseInt(inputs[0].trim()) - 1;
                    int meldIndex = Integer.parseInt(inputs[1].trim()) - 1;

                    System.out.println("Select the indices of cards to lay off (separated by space):");
                    String[] layoffIndices = scanner.nextLine().split(" ");
                    List<Card> cardsToLayoff = new ArrayList<>();
                    for (String index : layoffIndices) {
                        int idx = Integer.parseInt(index.trim()) - 1;
                        if (idx >= 0 && idx < currentPlayer.getHand().size()) {
                            cardsToLayoff.add(currentPlayer.getHand().get(idx));
                        } else {
                            System.out.println("Invalid card index: " + (idx + 1));
                            actionSuccessful = false;
                            break;
                        }
                    }
                    if (actionSuccessful) {
                        actionSuccessful = gameState.playerLaysOff(targetPlayerIndex, meldIndex, cardsToLayoff);
                        if (actionSuccessful) {
                            displayHand(currentPlayer);
                        }
                    }
                } else if (actionChoice == 3) {
                    break;
                } else {
                    System.out.println("Invalid choice. Please try again.");
                    actionSuccessful = false;
                }
            } while (!actionSuccessful);

            boolean validDiscard;
            do {
                validDiscard = true;

                if (!currentPlayer.gw()) {
                    System.out.println("Select the index of the card to discard:");
                    int discardIndex = scanner.nextInt() - 1;
                    scanner.nextLine();

                    if (discardIndex >= 0 && discardIndex < currentPlayer.getHand().size()) {
                        Card cardToDiscard = currentPlayer.getHand().get(discardIndex);
                        validDiscard = gameState.playerDiscardsCard(cardToDiscard);

                        if (!validDiscard) {
                            System.out.println("You cannot discard the card you just drew. Please choose a different card.");
                        }
                    } else {
                        System.out.println("Invalid card index. Please try again.");
                        validDiscard = false;
                    }
                }
            } while (!validDiscard);

            currentPlayer.endTurn();
            gameState.nextTurn();
        }

        Player winner = null;
        int totalPoints = 0;

        for (Player player : gameState.getPlayers()) {

            if (player.gw()) {
                winner = player;
            } else {
                totalPoints += player.calculatePoints();
            }
        }

        if (winner != null) {
            System.out.println("Congratulations, " + winner.getName() + "! You've won the game of Rummy.");
            System.out.println("Your score: " + totalPoints);
        }

        scanner.close();
    }
}

