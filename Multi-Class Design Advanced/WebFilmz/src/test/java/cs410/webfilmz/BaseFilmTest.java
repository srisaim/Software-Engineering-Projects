package cs410.webfilmz;

/*
 *
 * DO NOT MAKE ANY CHANGES TO THIS FILE
 *
 * Add your own tests to FilmTest.java.
 *
 */

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BaseFilmTest {

    @Test
    void testTotals() {
        Catalog catalog = BaseCatalogTest.getCatalog();
        Film terminator = catalog.findByTitle("The Terminator");
        assertEquals(0, terminator.totalWatched());
        assertEquals(0, terminator.totalLiked());
        terminator.incrementWatched(2);
        terminator.incrementLiked(1);
        assertEquals(2, terminator.totalWatched());
        assertEquals(1, terminator.totalLiked());
        terminator.incrementWatched(12);
        terminator.incrementLiked(10);
        assertEquals(14, terminator.totalWatched());
        assertEquals(11, terminator.totalLiked());
    }

    @Test
    void testRating() {
        Catalog catalog = BaseCatalogTest.getCatalog();
        Film terminator = catalog.findByTitle("The Terminator"); // R
        Film toystory = catalog.findByTitle("Toy Story"); // G
        // bleh...
        assertTrue(terminator.rating().isAppropriateFor(Rating.R));
        assertFalse(terminator.rating().isAppropriateFor(Rating.PG13));
        assertTrue(toystory.rating().isAppropriateFor(Rating.R));
        assertTrue(toystory.rating().isAppropriateFor(Rating.G));
    }
}