package cs410.webfilmz;

/*
 *
 * DO NOT MAKE ANY CHANGES TO THIS FILE
 *
 * Add your own tests to CatalogTest.java.
 *
 */

import org.junit.jupiter.api.Test;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class BaseCatalogTest {
    // Only use this catalog if the test does not modify it or the films it contains
    final Catalog catalog = getCatalog();

    // Makes a known small catalog for testing
    static Catalog getCatalog() {
        Catalog catalog = new Catalog();
        catalog.add("The Terminator", "James Cameron", "SciFi", 1984, Rating.R);
        catalog.add("The Princess Bride", "Rob Reiner", "Romance", 1987, Rating.PG);
        catalog.add("The City of Lost Children", "Jean-Pierre Jeunet", "SciFi", 1995, Rating.R);
        catalog.add("Toy Story", "John Lasseter", "Comedy", 1995, Rating.G);
        catalog.add("Titanic", "James Cameron", "Romance", 1997, Rating.PG13);
        catalog.add("Memento", "Christopher Nolan", "Thriller", 2000, Rating.R);
        catalog.add("Amelie", "Jean-Pierre Jeunet", "Romance", 2001, Rating.R);
        catalog.add("Inception", "Christopher Nolan", "SciFi", 2010, Rating.PG13);
        catalog.add("The Martian", "Ridley Scott", "SciFi", 2015, Rating.PG13);
        catalog.add("Oppenheimer", "Christopher Nolan", "Bio" ,2023, Rating.R);
        return catalog;
    }

    @Test
    void findFilmByTitle() {
        assertEquals("Memento", catalog.findByTitle("Memento").title());
        assertThrows(RuntimeException.class,
                () -> catalog.findByTitle("The Great Mouse Detective"));
    }

    @Test
    void getRecommendationsByYear() {
        assertEquals(
                Set.of(catalog.findByTitle("Oppenheimer"), catalog.findByTitle("The Martian")),
                catalog.getRecommendationsByYear(2));
    }

    @Test
    void getRecommendationsMostLiked() {
        Catalog catalog = getCatalog();
        Film amelie = catalog.findByTitle("Amelie");
        Film memento = catalog.findByTitle("Memento");
        amelie.incrementLiked(10);
        memento.incrementLiked(4);
        assertEquals(Set.of(amelie, memento),
                catalog.getRecommendationsMostLiked(2));
        assertEquals(Set.of(amelie),
                catalog.getRecommendationsMostLiked(1));
    }

    // Represents preference for a single director, no genres
    private class JustLikesOneDirector implements ILikeFilm {
        private String likedDirector;
        JustLikesOneDirector(String likedDirector) { this.likedDirector = likedDirector; }

        // Do any of the films the user liked have the given director/genre?
        public boolean isLikedDirector(String director) {
            return this.likedDirector.equals(director);
        }
        public boolean isLikedGenre(String genre) {
            return false;
        }
    }

    @Test
    void getPseudoPersonalRecommendationsByDirector() {
        String likedDirector = "James Cameron";
        assertEquals(
                Set.of(catalog.findByTitle("The Terminator"), catalog.findByTitle("Titanic")),
                catalog.getRecommendationsByDirector(
                        new JustLikesOneDirector(likedDirector))
        );
    }

    @Test
    void getPseudoPersonalRecommendationsByDirector2() {
        String likedDirector = "James Cameron";
        assertEquals(
                Set.of(catalog.findByTitle("The Terminator"), catalog.findByTitle("Titanic")),
                catalog.getRecommendationsByDirector(
                        // This is called an "anonymous class expression"
                        new ILikeFilm() {
                            @Override
                            public boolean isLikedDirector(String director) {
                                return likedDirector.equals(director);
                            }

                            @Override
                            public boolean isLikedGenre(String genre) {
                                return false;
                            }
                        }
                ));
    }
}
