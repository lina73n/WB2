package by.your_film_list.bean;

/**
 * AnimeReview is a record class that represents an anime review.
 * It contains the properties' id, userId, userLogin, animeId, rate, and comment.
 */
public record AnimeReview(int id, int userId, String userLogin, int animeId, float rate, String comment) {
}
