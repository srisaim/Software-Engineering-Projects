package cs410.webfilmz;

/* Code for the back end of WebFilmz (a Netflix-like site)
 * Does not include front end, user authentication, etc.
 *
 * class User represents users (subscribers to the service)
 * class Film represents available films
 * class Catalog contains the list of all available films
 *
 * Public classes:
 *   Catalog must be populated by site, offers lookups
 *   User offers operations like watch, like, get recommendations
 *
 * Example: user Alice watches film "Memento" and likes it.
 *   Catalog catalog = ...;
 *   User alice = ...;
 *   Film memento = catalog.findByTitle("Memento");
 *   alice.addWatched(memento);
 *   alice.addLiked(memento);
 */
