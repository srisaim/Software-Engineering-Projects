package cs410.webfilmz;

/* Represents a film, including both its immutable
 * intrinsic information and its mutable viewing history.
 * Used by User and Catalog, updated by User; created by Catalog.
 */
public class Film {
    private final String title;
    private final String director;
    private final String genre;
    private final int releaseYear;

    private int totalWatched = 0;
    private int totalLiked = 0;

    Film(String title, String director, String genre,
                 int releaseYear) {
        this.title = title;
        this.director = director;
        this.genre = genre;
        this.releaseYear = releaseYear;
    }

    // Get field values
    public String title() { return title; }
    public String director() { return director; }
    public String genre() { return genre; }
    public int releaseYear() { return releaseYear; }
    public int totalWatched() { return totalWatched; }
    public int totalLiked() { return totalLiked; }

    // Update watched/liked counters, respectively
    public void incrementWatched(int toAdd) {
        totalWatched = totalWatched + toAdd;
    }
    public void incrementLiked(int toAdd) {
        totalLiked = totalLiked + toAdd;
    }
}
