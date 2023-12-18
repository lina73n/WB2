package by.your_film_list.bean;

/**
 * Represents an anime with its properties such as ID, name, author name, rating, image path, and year.
 */
public class Anime {
    public static final int ID_STUB = -1;
    public static final float RATING_STUB = 6.0f;

    private final int id;
    private final String name;
    private final String authorName;
    private final float rating;
    private final String imagePath;
    private final int year;
    private final String description;

    /**
     * Constructs an Anime object with the specified properties.
     *
     * @param id         the ID of the anime
     * @param name       the name of the anime
     * @param authorName the name of the author of the anime
     * @param rating     the rating of the anime
     * @param imagePath  the path to the image of the anime
     * @param year       the year the anime was released
     */
    public Anime(int id, String name, String authorName,
                 float rating, String imagePath, int year, String description) {
        this.id = id;
        this.name = name;
        this.authorName = authorName;
        this.rating = rating;
        this.imagePath = imagePath;
        this.year = year;
        this.description = description;
    }

    /**
     * Returns the ID of the anime.
     *
     * @return the ID of the anime
     */
    public int getId() {
        return id;
    }

    /**
     * Returns the name of the anime.
     *
     * @return the name of the anime
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the name of the author of the anime.
     *
     * @return the name of the author of the anime
     */
    public String getAuthorName() {
        return authorName;
    }

    /**
     * Returns the rating of the anime.
     *
     * @return the rating of the anime
     */
    public float getRating() {
        return rating;
    }

    /**
     * Returns the image path of the anime.
     *
     * @return the image path of the anime
     */
    public String getImagePath() {
        return imagePath;
    }

    /**
     * Returns the year the anime was released.
     *
     * @return the year the anime was released
     */
    public int getYear() {
        return year;
    }

    /**
     * Returns the description of the anime.
     *
     * @return anime description.
     */
    public String getDescription() {
        return description;
    }
}
