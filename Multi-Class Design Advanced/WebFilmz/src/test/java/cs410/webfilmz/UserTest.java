package cs410.webfilmz;

import org.junit.jupiter.api.Test;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.*;

/*
 *
 * ADD YOUR User TESTS TO THIS FILE
 *
 */

public class UserTest {
    // Testing if the recommendation system correctly identifies and returns all suitable recommendations.
    @Test
    void testGetAllRecommendations() {
        Catalog cat = BaseCatalogTest.getCatalog();
        User sai = new User();
        Film TM = cat.findByTitle("The Martian");
        sai.addWatched(TM);
        assertEquals(Set.of(cat.findByTitle("Oppenheimer"), cat.findByTitle("Inception")),
                sai.getAllRecommendations(cat, 3).get("New Releases"));
    }

    // Testing if the recommendation system provides correct film recommendations based on watched and liked directors.
    @Test
    void testGetRecommendationsByDirector() {
        Catalog cat = BaseCatalogTest.getCatalog();
        User sai = new User();
        Film amelie = cat.findByTitle("Amelie");
        sai.addWatched(amelie);
        sai.addLiked(amelie);
        assertEquals(
                Set.of(cat.findByTitle("The City of Lost Children")),
                sai.getRecommendationsByDirector(cat));
    }

    // Testing if the recommendation system can return suitable recommendations with multiple films watched by user.
    @Test
    void testGetAllRecommendationsWithMultipleWatched() {
        Catalog cat = BaseCatalogTest.getCatalog();
        User sai = new User();
        sai.addWatched(cat.findByTitle("The Martian"));
        sai.addWatched(cat.findByTitle("Inception"));

        assertFalse(sai.getAllRecommendations(cat, 3).get("New Releases").isEmpty(),
                "Recommendations can't be empty with multiple films watched.");
    }

    // Testing the recommendation system's behavior when a user hasn't watched any films.
    // Making sure it still provides some recommendations.
    @Test
    void testGetAllRecommendationsWithNoWatched() {
        Catalog cat = BaseCatalogTest.getCatalog();
        User sai = new User();

        assertFalse(sai.getAllRecommendations(cat, 3).get("New Releases").isEmpty(),
                "Recommendations can't be empty with no films watched.");
    }

    // Testing the recommendation system's functionality in providing director based recommendations
    // when multiple films have been liked by the user.
    @Test
    void testGetRecommendationsByDirectorWithMultipleLiked() {
        Catalog cat = BaseCatalogTest.getCatalog();
        User sai = new User();
        Film amelie = cat.findByTitle("Amelie");
        Film city = cat.findByTitle("The City of Lost Children");
        sai.addWatched(amelie);
        sai.addWatched(city);
        sai.addLiked(amelie);
        sai.addLiked(city);

        assertTrue(sai.getRecommendationsByDirector(cat).isEmpty(),
                "Recommendations can't be empty with multiple films watched.");
    }

    // Testing the recommendation system’s response when no films are liked by the user.
    // Basically expecting no director based recommendations.
    @Test
    void testGetRecommendationsByDirectorWithNoLiked() {
        Catalog cat = BaseCatalogTest.getCatalog();
        User sai = new User();
        assertTrue(sai.getRecommendationsByDirector(cat).isEmpty());
    }

    // Testing whether the recommendation system provides the most watched films correctly.
    @Test
    void testGetMostWatchedRecommendations() {
        Catalog cat = BaseCatalogTest.getCatalog();
        User sai = new User();

        assertFalse(sai.getAllRecommendations(cat, 3).get("Most Watched").isEmpty(),
                "Most Watched recommendations can't be empty");
    }

    // Testing if the recommendation system accurately identifies and provides the most liked films.
    @Test
    void testGetMostLikedRecommendations() {
        Catalog cat = BaseCatalogTest.getCatalog();
        User sai = new User();

        assertFalse(sai.getAllRecommendations(cat, 3).get("Most Liked").isEmpty(),
                "Most Liked recommendations can't be empty.");
    }

    // Testing the recommendation system for providing suggestions based on the user’s favorite genres.
    @Test
    void testFavoriteGenresRecommendations() {
        Catalog cat = BaseCatalogTest.getCatalog();
        User sai = new User();

        Film genreFilm = cat.findByTitle("The Terminator");
        assertNotNull(genreFilm, "Film should be in the catalog");
        sai.addLiked(genreFilm);

        Set<Film> favGenreRecs = sai.getAllRecommendations(cat, 3).get("Favorite Genres");

        Film expectedRecommendation = cat.findByTitle("The Martian");
        assertNotNull(expectedRecommendation, "Film should be in the catalog");

        assertTrue(favGenreRecs.contains(expectedRecommendation),
                "Favorite Genres should include recommendations from the genres the user has liked.");
    }

    // Testing if the recommendation system uses the cached data effectively to provide recommendations.
    @Test
    void testRecommendationsUsingCachedData() {
        Catalog cat = BaseCatalogTest.getCatalog();
        User sai = new User();
        Film Terminator = cat.findByTitle("The Terminator");

        sai.addLiked(Terminator);

        assertFalse(sai.getRecommendationsByDirector(cat).isEmpty());
        assertFalse(sai.getRecommendationsByGenre(cat).isEmpty());
    }

    // Testing if the system correctly caches the director and genre information of films that a user liked.
    // When a film is added to the liked list, the director and genre of the film are correctly cached.
    @Test
    void testCorrectCachingOfLikedDirectorsAndGenres() {
        Catalog cat = BaseCatalogTest.getCatalog();
        User sai = new User();

        Film Terminator = cat.findByTitle("The Terminator");
        sai.addLiked(Terminator);

        assertTrue(sai.isLikedDirector("James Cameron"), "James Cameron should be a liked director.");
        assertTrue(sai.isLikedGenre("SciFi"), "SciFi should be a liked genre.");
    }

    // Testing the correctness of methods used to determine if a director or genre is liked by the user.
    // The isLikedDirector and isLikedGenre work correctly by returning true for liked directors and genres
    // and false for directors and genres that are not in the liked list.
    @Test
    void testIsLikedDirectorAndIsLikedGenreMethods() {
        Catalog cat = BaseCatalogTest.getCatalog();
        User sai = new User();

        Film Terminator = cat.findByTitle("The Terminator");
        sai.addLiked(Terminator);

        assertTrue(sai.isLikedDirector("James Cameron"), "User should like James Cameron as a director.");
        assertTrue(sai.isLikedGenre("SciFi"), "User should like SciFi as a genre.");

        assertFalse(sai.isLikedDirector("Christopher Nolan"), "User has not liked any film by Christopher Nolan.");
        assertFalse(sai.isLikedGenre("Romance"), "User has not liked any Romance film.");
    }

    // Testing to ensure that it filters out movies based on the user's rating recommendations.
    // This test ensures that all recommended films are within the user’s set rating limit,
    // making sure the recommendations are appropriate.
    @Test
    void testFilterRecommendationsByRating() {
        Catalog cat = BaseCatalogTest.getCatalog();
        User sai = new User(Rating.PG13); // setting user rating limit to PG13

        Set<Film> recommendations = sai.getAllRecommendations(cat, 3).get("New Releases");

        for (Film film : recommendations) {
            assertTrue(film.rating().isAppropriateFor(Rating.PG13));
        }
    }

    // Testing to ensure that in the absence of a user-specified rating, the system defaults to a safe PG-13 rating.
    // This test confirms that the recommendations will include PG-13-rated films when there is no specific rating
    // preference specified by the user.
    @Test
    void testDefaultUserRating() {
        User sai = new User();

        Set<Film> recommendations = sai.getAllRecommendations(BaseCatalogTest.getCatalog(), 3).get("New Releases");
        boolean containsRRatedFilm = recommendations.stream().anyMatch(f -> f.rating().equals(Rating.PG13));

        assertTrue(containsRRatedFilm, "The default user rating should be PG-13 as well as the recommended films.");
    }

    // Testing the effectiveness of the recommendation system’s rating-based filtering using all categories of film recommendations.
    // This test checks that each category, like "New Releases" or "Most Liked," are following the user's specified rating preference.
    // The test ensures that the system maintains a consistent application of rating filters across all aspects of film recommendations.
    @Test
    void testFilteringOnAllRecommendationTypes() {
        Catalog cat = BaseCatalogTest.getCatalog();
        User sai = new User(Rating.PG13);

        String[] categories = {"New Releases", "Favorite Directors", "Favorite Genres", "Most Watched", "Most Liked"};

        for (String c : categories) {
            Set<Film> recommendations = sai.getAllRecommendations(cat, 3).get(c);
            for (Film film : recommendations) {
                assertTrue(film.rating().isAppropriateFor(Rating.PG13),
                        "All films in " + c + " should be appropriate for PG13.");
            }
        }
    }
}
