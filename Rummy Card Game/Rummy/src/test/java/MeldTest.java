import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MeldTest {

    @Test
    void addMeld() {
        Meld meld = new Meld();
        List<Card> realMeld = new ArrayList<>();
        List<List<Card>> melds = new ArrayList<>();
        realMeld.add(Card.of(Card.Suit.HEARTS, Card.Rank.JACK));
        realMeld.add(Card.of(Card.Suit.SPADES, Card.Rank.JACK));
        realMeld.add(Card.of(Card.Suit.CLUBS, Card.Rank.JACK));
        melds.add(realMeld);
        assertTrue(meld.addMeld(realMeld));
        assertEquals(meld.getMelds(), melds);

        List<Card> nonMeld = new ArrayList<>();
        nonMeld.add(Card.of(Card.Suit.HEARTS, Card.Rank.JACK));
        assertFalse(meld.addMeld(nonMeld));
        assertEquals(meld.getMelds(), melds);
    }

    @Test
    void layoff() {
        Meld meld = new Meld();
        List<Card> realMeld = new ArrayList<>();
        List<Card> realLayoff = new ArrayList<>();
        List<List<Card>> melds = new ArrayList<>();
        realMeld.add(Card.of(Card.Suit.HEARTS, Card.Rank.JACK));
        realMeld.add(Card.of(Card.Suit.SPADES, Card.Rank.JACK));
        realMeld.add(Card.of(Card.Suit.CLUBS, Card.Rank.JACK));
        meld.addMeld(new ArrayList<>(realMeld));
        realLayoff.add(Card.of(Card.Suit.DIAMONDS, Card.Rank.JACK));
        realMeld.addAll(realLayoff);
        melds.add(realMeld);
        assertTrue(meld.layoff(0, realLayoff));
        assertEquals(meld.getMelds(), melds);

        List<Card> nonLayoff = new ArrayList<>();
        nonLayoff.add(Card.of(Card.Suit.HEARTS, Card.Rank.QUEEN));
        assertFalse(meld.addMeld(nonLayoff));
        assertEquals(meld.getMelds(), melds);
    }

    @Test
    void canMeld() {
        Meld meld = new Meld();
        List<Card> meldSet = new ArrayList<>();
        meldSet.add(Card.of(Card.Suit.HEARTS, Card.Rank.JACK));
        meldSet.add(Card.of(Card.Suit.SPADES, Card.Rank.JACK));
        meldSet.add(Card.of(Card.Suit.CLUBS, Card.Rank.JACK));

        List<Card> meldRun = new ArrayList<>();
        meldRun.add(Card.of(Card.Suit.HEARTS, Card.Rank.TEN));
        meldRun.add(Card.of(Card.Suit.HEARTS, Card.Rank.JACK));
        meldRun.add(Card.of(Card.Suit.HEARTS, Card.Rank.QUEEN));
        assertTrue(meld.isRun(meldRun));

        List<Card> nonMeld = new ArrayList<>();
        nonMeld.add(Card.of(Card.Suit.HEARTS, Card.Rank.JACK));
        nonMeld.add(Card.of(Card.Suit.HEARTS, Card.Rank.JACK));
        nonMeld.add(Card.of(Card.Suit.BLANK, Card.Rank.JOKER));
        nonMeld.add(Card.of(Card.Suit.HEARTS, Card.Rank.QUEEN));

        assertTrue(meld.canMeld(meldSet));
        assertTrue(meld.canMeld(meldRun));
        assertFalse(meld.canMeld(nonMeld));
    }

    @Test
    void isSet() {
        Meld meld = new Meld();
        List<Card> meldSet = new ArrayList<>();
        meldSet.add(Card.of(Card.Suit.HEARTS, Card.Rank.JACK));
        meldSet.add(Card.of(Card.Suit.SPADES, Card.Rank.JACK));
        meldSet.add(Card.of(Card.Suit.CLUBS, Card.Rank.JACK));

        List<Card> jokerMeldSet = new ArrayList<>();
        jokerMeldSet.add(Card.of(Card.Suit.BLANK, Card.Rank.JOKER));
        jokerMeldSet.add(Card.of(Card.Suit.BLANK, Card.Rank.JOKER));
        jokerMeldSet.add(Card.of(Card.Suit.BLANK, Card.Rank.JACK));

        List<Card> nonMeldSet = new ArrayList<>();
        nonMeldSet.add(Card.of(Card.Suit.HEARTS, Card.Rank.JACK));
        nonMeldSet.add(Card.of(Card.Suit.SPADES, Card.Rank.JACK));
        nonMeldSet.add(Card.of(Card.Suit.BLANK, Card.Rank.JOKER));
        nonMeldSet.add(Card.of(Card.Suit.CLUBS, Card.Rank.QUEEN));

        List<Card> tooSmallMeldSet = new ArrayList<>();
        tooSmallMeldSet.add(Card.of(Card.Suit.HEARTS, Card.Rank.JACK));
        tooSmallMeldSet.add(Card.of(Card.Suit.SPADES, Card.Rank.JACK));

        assertTrue(meld.isSet(meldSet));
        assertTrue(meld.isSet(jokerMeldSet));
        assertFalse(meld.isSet(nonMeldSet));
        assertFalse(meld.isSet(tooSmallMeldSet));
    }

    @Test
    void isRun() {
        Meld meld = new Meld();
        List<Card> meldRun = new ArrayList<>();
        meldRun.add(Card.of(Card.Suit.HEARTS, Card.Rank.TEN));
        meldRun.add(Card.of(Card.Suit.HEARTS, Card.Rank.JACK));
        meldRun.add(Card.of(Card.Suit.HEARTS, Card.Rank.QUEEN));

        List<Card> jokerMeldRun = new ArrayList<>();
        jokerMeldRun.add(Card.of(Card.Suit.SPADES, Card.Rank.EIGHT));
        jokerMeldRun.add(Card.of(Card.Suit.BLANK, Card.Rank.JOKER));
        jokerMeldRun.add(Card.of(Card.Suit.BLANK, Card.Rank.JOKER));
        jokerMeldRun.add(Card.of(Card.Suit.SPADES, Card.Rank.JACK));

        List<Card> nonRunMeldRun = new ArrayList<>();
        nonRunMeldRun.add(Card.of(Card.Suit.HEARTS, Card.Rank.JACK));
        nonRunMeldRun.add(Card.of(Card.Suit.HEARTS, Card.Rank.JACK));
        nonRunMeldRun.add(Card.of(Card.Suit.BLANK, Card.Rank.JOKER));
        nonRunMeldRun.add(Card.of(Card.Suit.HEARTS, Card.Rank.QUEEN));

        List<Card> nonSuitMeldRun = new ArrayList<>();
        nonRunMeldRun.add(Card.of(Card.Suit.SPADES, Card.Rank.TEN));
        nonRunMeldRun.add(Card.of(Card.Suit.HEARTS, Card.Rank.JACK));
        nonRunMeldRun.add(Card.of(Card.Suit.BLANK, Card.Rank.JOKER));
        nonRunMeldRun.add(Card.of(Card.Suit.HEARTS, Card.Rank.QUEEN));

        List<Card> tooFewJokersMeldRun = new ArrayList<>();
        nonRunMeldRun.add(Card.of(Card.Suit.HEARTS, Card.Rank.EIGHT));
        nonRunMeldRun.add(Card.of(Card.Suit.BLANK, Card.Rank.JOKER));
        nonRunMeldRun.add(Card.of(Card.Suit.HEARTS, Card.Rank.QUEEN));

        List<Card> tooSmallMeldRun = new ArrayList<>();
        tooSmallMeldRun.add(Card.of(Card.Suit.HEARTS, Card.Rank.JACK));
        tooSmallMeldRun.add(Card.of(Card.Suit.HEARTS, Card.Rank.JACK));

        assertTrue(meld.isRun(meldRun));
        assertTrue(meld.isRun(jokerMeldRun));
        assertFalse(meld.isRun(nonRunMeldRun));
        assertFalse(meld.isRun(nonSuitMeldRun));
        assertFalse(meld.isRun(tooFewJokersMeldRun));
        assertFalse(meld.isRun(tooSmallMeldRun));
    }

    @Test
    void getMelds() {
        Meld meld = new Meld();
        List<List<Card>> melds = new ArrayList<>();

        List<Card> cardList = new ArrayList<>();
        cardList.add(Card.of(Card.Suit.HEARTS, Card.Rank.JACK));
        cardList.add(Card.of(Card.Suit.SPADES, Card.Rank.JACK));
        cardList.add(Card.of(Card.Suit.CLUBS, Card.Rank.JACK));

        List<Card> cardList2 = new ArrayList<>();
        cardList2.add(Card.of(Card.Suit.HEARTS, Card.Rank.QUEEN));
        cardList2.add(Card.of(Card.Suit.SPADES, Card.Rank.QUEEN));
        cardList2.add(Card.of(Card.Suit.CLUBS, Card.Rank.QUEEN));
        meld.addMeld(new ArrayList<>(cardList2));
        melds.add(cardList2);

        assertEquals(meld.getMelds(), melds);
    }

    @Test
    void toJson() {
        Meld meld = new Meld();
        List<Card> realMeld = new ArrayList<>();
        realMeld.add(Card.of(Card.Suit.HEARTS, Card.Rank.JACK));
        realMeld.add(Card.of(Card.Suit.SPADES, Card.Rank.JACK));
        realMeld.add(Card.of(Card.Suit.CLUBS, Card.Rank.JACK));
        meld.addMeld(new ArrayList<>(realMeld));
        assertEquals(meld.toJson(), "[[{\"suit\":\"HEARTS\",\"rank\":\"JACK\"},{\"suit\":\"SPADES\",\"rank\":\"JACK\"},{\"suit\":\"CLUBS\",\"rank\":\"JACK\"}]]");
    }
}