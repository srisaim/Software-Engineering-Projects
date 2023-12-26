package cs410.webfilmz;

/* Express preferences about films (directors and genres, specifically)
 */
public interface ILikeFilm {
    // Do we like the given director or genre?
    public boolean isLikedDirector(String director);
    public boolean isLikedGenre(String genre);
}
