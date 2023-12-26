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

    // Maximum acceptable rating for recommendations.
    private final Rating limitRating;

    public User(Rating limitRating) {
        this.watched = new HashSet<>();
        this.liked = new HashSet<>();
        this.likedDirectors = new HashSet<>();
        this.likedGenres = new HashSet<>();
        this.limitRating = limitRating;
    }

    public User() {
        this(Rating.R);
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

    // Creating a helper method which filters a set of films based on a given rating rat.
    // It initializes an empty set to store films that pass the filter.
    // For each film in the input set, it checks if the film's rating is appropriate for rating rat.
    // If the check passes, then the film is added to the filtered set.
    // It then returns the filtered set of films.
    private Set<Film> filterFilmsByRating(Set<Film> films, Rating rat) {
        Set<Film> ff = new HashSet<>();
        for (Film film : films) {
            if (film.rating().isAppropriateFor(rat)) {
                ff.add(film);
            }
        }
        return ff;
    }

    // This method collects various film recommendations for a user into a single map.
    // New Releases obtains recommendations based on recent releases and filters them by user's
    // rating limit and watched films.
    // Favorite Genres gets films of user's favorite genres and filters them by user's rating.
    // Most Watched gets the most watched films and then filters them based on user’s rating limit and watched films.
    // Most Liked collects the most liked films and filters them by user’s rating limit and watched films.
    // Favorite Directors gets recommendations of films by the user's favorite directors.
    // The filtered sets are then combined into a map with their respective categories as keys, and returned.
    public Map<String, Set<Film>> getAllRecommendations(Catalog catalog, int initialGenericRecsCount) {
        Set<Film> newReleases = filterFilmsByRating(catalog.getRecommendationsByYear(initialGenericRecsCount), this.limitRating);
        newReleases.removeAll(watched);

        Set<Film> favoriteGenres = filterFilmsByRating(getRecommendationsByGenre(catalog), this.limitRating);

        Set<Film> mostWatched = filterFilmsByRating(catalog.getRecommendationsMostWatched(initialGenericRecsCount), this.limitRating);
        mostWatched.removeAll(watched);

        Set<Film> mostLiked = filterFilmsByRating(catalog.getRecommendationsMostLiked(initialGenericRecsCount), this.limitRating);
        mostLiked.removeAll(watched);

        return Map.of(
                "New Releases", newReleases,
                "Favorite Directors", getRecommendationsByDirector(catalog),
                "Favorite Genres", favoriteGenres,
                "Most Watched", mostWatched,
                "Most Liked", mostLiked
        );
    }

    // The getRecommendationsByDirector method gets film recommendations based on liked directors.
    // It fetches recommendations from the Catalog then filters them based on the user's rating limit.
    // After filtering films based on rating, it then removes films that the user has already watched.
    // This ensures that the user gets suggestions for films that they haven't seen yet and is within their rating limit.
    public Set<Film> getRecommendationsByDirector(Catalog catalog) {
        Set<Film> rec = filterFilmsByRating(catalog.getRecommendationsByDirector(this), this.limitRating);
        rec.removeAll(watched);
        return rec;
    }

    // The getRecommendationsByGenre method gets film recommendations based on user-preferred genres.
    // It gets these recommendations from the Catalog and filters them according to the user's rating preferences.
    // After applying the rating filter, it then removes the films that the user has already watched.
    // This ensures that the user receives recommendations of unseen films that also align with their rating limits.
    public Set<Film> getRecommendationsByGenre(Catalog catalog) {
        Set<Film> rec = filterFilmsByRating(catalog.getRecommendationsByGenre(this), this.limitRating);
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
