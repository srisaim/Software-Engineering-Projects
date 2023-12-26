package cs410.webfilmz;

/*
 *
 * DO NOT MAKE ANY CHANGES TO THIS FILE
 *
 * Additional Film tests are probably not necessary, but if you want
 * to make them, create a separate FilmTest.java file for them.
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
}