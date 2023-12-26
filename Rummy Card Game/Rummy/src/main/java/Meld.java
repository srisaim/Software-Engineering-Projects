import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import com.google.gson.Gson;

public class Meld {
    // Implement player logic and moves
    List<List<Card>> melds;

    public Meld(){
        melds = new ArrayList<>();
    }

    // Adds a new valid meld to the existing list of melds.
    // True if meld is valid and is added, and false otherwise.
    public boolean addMeld(List<Card> meld){
        if(canMeld(meld)) {
            melds.add(meld);
            return true;   //maybe return meld
        }
        return false;
    }

    // Adds cards to an existing meld if they can create a valid meld.
    // True if layoff creates a valid meld, and false otherwise.
    public boolean layoff(int meldIndex, List<Card> layoff){
        List<Card> newMeld = new ArrayList<>(melds.get(meldIndex)); // maybe can do this and next line in one line
        newMeld.addAll(layoff);
        if(canMeld(newMeld)){
            melds.set(meldIndex, newMeld);
            return true;    // maybe return meld
        }
        return false;
    }

    // Finds all types of possible melds with a card being laid off.
    public List<Integer> findLayoffs(List<Card> layoff){
        List<Integer> indexList = new ArrayList<>();
        for ( int i = 0; i < melds.size(); i++ ) {
            List<Card> newMeld = new ArrayList<>(melds.get(i));
            newMeld.addAll(layoff);
            if(canMeld(newMeld)){
                indexList.add(i);
            }
        }
        return indexList;
    }

    // Simply checks if the list of cards can form a valid meld, between a set and run
    public boolean canMeld(List<Card> meld){
        return isRun(meld) || isSet(meld);
    }

    // A more in-depth check to see if a list of cards can form a valid set.
    public boolean isSet(List<Card> meld) {
        if(meld.size() < 3)
            return false;
        return meld.stream()
                .filter(card -> card.rank() != Card.Rank.JOKER)
                .map(Card::rank)
                .distinct()
                .limit(2)
                .count() <= 1;

    }

    // A more in-depth check to see if a list of cards can form a valid run.
    public boolean isRun(List<Card> meld) {
        long numJoker;
        Card prevCard;

        if(meld.size() < 3)
            return false;

        numJoker = meld.stream()
                .filter(card -> card.rank() == Card.Rank.JOKER).count();
        List<Card> noJokerMeld = meld.stream()
                .filter(card -> card.rank() != Card.Rank.JOKER).collect(Collectors.toList());
        if(noJokerMeld.size() < 2)
            return true;
        noJokerMeld.sort(Comparator.comparing(Card::rank));

        prevCard = noJokerMeld.get(0);
        for (int i = 1; i < noJokerMeld.size(); i++){
            Card currentCard = noJokerMeld.get(i);
            if(prevCard.rank() == currentCard.rank() ||
                    prevCard.suit() != currentCard.suit())
                return false;
            numJoker -= currentCard.rank().ordinal() - prevCard.rank().ordinal() - 1;
            if(numJoker < 0)
                return false;
            prevCard = currentCard;
        }
        return true;

    }

    // Simply returns the list of all melds.
    public List<List<Card>> getMelds() {
        return melds;
    }

    // Creating a method to serialize Melds to JSON.
    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this.melds);
    }
}

