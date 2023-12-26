package cs410.webfilmz;

/*
 *
 * DO NOT MAKE ANY CHANGES TO THIS FILE
 *
 * Add your own tests to UserTest.java.
 *
 */

import java.util.Set;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BaseUserTest {

    @Test
    void addWatchedAndLiked() {
        Catalog catalog = BaseCatalogTest.getCatalog();
        User alice = new User();
        Film amelie = catalog.findByTitle("Amelie");
        Film terminator = catalog.findByTitle("The Terminator");
        assertEquals(0, amelie.totalWatched());
        assertEquals(0, amelie.totalLiked());
        assertEquals(0, terminator.totalWatched());
        assertEquals(0, terminator.totalLiked());
        alice.addWatched(amelie);
        assertEquals(1, amelie.totalWatched());
        assertEquals(0, amelie.totalLiked());
        alice.addLiked(amelie);
        assertEquals(1, amelie.totalWatched());
        assertEquals(1, amelie.totalLiked());
        assertEquals(0, terminator.totalWatched());
        assertEquals(0, terminator.totalLiked());
    }

    @Test
    void testAddWatchedIdempotent() {
        Catalog catalog = BaseCatalogTest.getCatalog();
        User alice = new User();
        Film amelie = catalog.findByTitle("Amelie");
        assertEquals(0, amelie.totalWatched());
        alice.addWatched(amelie);
        alice.addWatched(amelie);
        assertEquals(1, amelie.totalWatched());
    }

    @Test
    void getRecommendationsNewReleases() {
        Catalog catalog = BaseCatalogTest.getCatalog();
        User alice = new User();
        alice.addWatched(catalog.findByTitle("The Martian"));
        assertEquals(Set.of(catalog.findByTitle("Oppenheimer"), catalog.findByTitle("Inception")),
                alice.getAllRecommendations(catalog, 3).get("New Releases"));
    }

    @Test
    void getRecommendationsByDirector() {
        Catalog catalog = BaseCatalogTest.getCatalog();
        User alice = new User();
        Film amelie = catalog.findByTitle("Amelie");
        alice.addWatched(amelie);
        alice.addLiked(amelie);
        assertEquals(
                Set.of(catalog.findByTitle("The City of Lost Children")),
                alice.getRecommendationsByDirector(catalog));
    }

    @Test
    void isLikedDirector() {
        Catalog catalog = BaseCatalogTest.getCatalog();
        User alice = new User();
        Film amelie = catalog.findByTitle("Amelie");
        alice.addWatched(amelie);
        alice.addLiked(amelie);
        assertTrue(alice.isLikedDirector("Jean-Pierre Jeunet"));
        assertFalse(alice.isLikedDirector("Ridley Scott"));
    }
}
