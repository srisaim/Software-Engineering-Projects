package cs410.webfilmz;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/* Represents a user (subscriber to the service), including
 * their watch/like history.
 * Responsible for maintaining watch/like history, expressing
 * film preferences, and getting personalized recommendations.
 * Refers to and updates Film.
 * Relies on Catalog to implement recommendations.
 */
public class User implements ILikeFilm {
    /* Sets of films watched and liked, respectively.
     * Uses Set to avoid recording watch/like multiple times.
     */
    // Defining variables likedDirectors and likedGenres based on user preference.
    private final Set<Film> watched;
    private final Set<Film> liked;
    private final Set<String> likedDirectors;
    private final Set<String> likedGenres;

    // Initialization likedDirectors and likedGenres using Hashsets, which avoid duplicates.
    public User() {
        watched = new HashSet<>();
        liked = new HashSet<>();
        likedDirectors = new HashSet<>();
        likedGenres = new HashSet<>();
    }

    // Record that the user watched the given film,
    // updating film if not already in Set.
    public void addWatched(Film film) {
        if (watched.add(film)) {
            film.incrementWatched(1);
        }
    }

    // Record that the user liked the given film,
    // updating film if not already in Set.
    // It updates the liked count in the Film object and caches the director and genre of film.
    public void addLiked(Film film) {
        if (liked.add(film)) {
            film.incrementLiked(1);
            cacheLikedDirectorAndGenre(film);
        }
    }

    // For getting all recommendations for a user based on various things.
    // For new releases recommendations, HashSet newReleases is created with films from the catalog, using the method
    // getRecommendationsByYear. This returns films released recently, based on year.
    // For favorite genres recommendations, creating a Set of Film objects named favoriteGenres and initializing it
    // with the value of getRecommendationsByGenre(catalog), which returns genre films the user liked.
    // For most watched recommendations, HashSet named mostWatched is created with films from the catalog, using
    // getRecommendationsMostWatched. This method returns films that are the most watched.
    // For most liked recommendations, HashSet is filled with films that are the most liked following the catalog,
    // using the getRecommendationsMostLiked method.
    // Finally, the created sets of recommendations are combined into a single Map and is returned.
    public Map<String, Set<Film>> getAllRecommendations(Catalog catalog, int initialGenericRecsCount) {
        Set<Film> newReleases = new HashSet<>(catalog.getRecommendationsByYear(initialGenericRecsCount));
        newReleases.removeAll(watched);

        Set<Film> favoriteGenres = getRecommendationsByGenre(catalog);

        Set<Film> mostWatched = new HashSet<>(catalog.getRecommendationsMostWatched(initialGenericRecsCount));
        mostWatched.removeAll(watched);

        Set<Film> mostLiked = new HashSet<>(catalog.getRecommendationsMostLiked(initialGenericRecsCount));
        mostLiked.removeAll(watched);

        return Map.of(
                "New Releases", newReleases,
                "Favorite Directors", getRecommendationsByDirector(catalog),
                "Favorite Genres", favoriteGenres,
                "Most Watched", mostWatched,
                "Most Liked", mostLiked
        );
    }

    // The getRecommendationsByDirector is responsible for getting film recommendations based on liked directors.
    // It fetches recommendations from the Catalog by passing the User object (this).
    // It then removes any film that the user has already watched from the recommendations, making sure the user gets
    // suggested films that they haven't seen yet.
    public Set<Film> getRecommendationsByDirector(Catalog catalog) {
        Set<Film> rec = catalog.getRecommendationsByDirector(this);
        rec.removeAll(watched);
        return rec;
    }

    // The getRecommendationsByGenre gets film recommendations based on genres that the user has liked.
    // It gets these recommendations from the Catalog, passing the User object to the Catalog’s method
    // to get films of genres that the user has liked.
    // It then removes any films that the user has already watched, making sure that the recommendations are films
    // the user haven't seen yet.
    // Finally, it returns the set of recommended films, which have been filtered to exclude the watched ones.
    public Set<Film> getRecommendationsByGenre(Catalog catalog) {
        Set<Film> rec = catalog.getRecommendationsByGenre(this);
        rec.removeAll(watched);
        return rec;
    }

    // For caching or saving the director and genre of films that the user liked.
    // This adds the director and genre of the liked film to corresponding sets for directors and genres.
    private void cacheLikedDirectorAndGenre(Film film) {
        likedDirectors.add(film.director());
        likedGenres.add(film.genre());
    }

    // Opposite of cacheLikedDirectorAndGenre, meaning it removes the disliked film's data.
    // Removing the film, director, and genre from the corresponding sets.
    private void cacheUnlikedDirectorAndGenre(Film film) {
        liked.remove(film);
        likedDirectors.remove(film.director());
        likedGenres.remove(film.genre());
    }

    // Do any of the films the user liked have the given
    // director/genre?
    // Checks if any liked films have the given director or genre.
    // The isLikedDirector returns a boolean showing whether the director exists in the user’s liked directors’ set.
    // The isLikedGenre returns a boolean showing whether the genre exists in the user’s liked genres’ set.
    public boolean isLikedDirector(String director) {
        return likedDirectors.contains(director);
    }
    public boolean isLikedGenre(String genre) {
        return likedGenres.contains(genre);
    }
}
