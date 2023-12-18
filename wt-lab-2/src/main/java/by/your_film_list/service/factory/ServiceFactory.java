package by.your_film_list.service.factory;

import by.your_film_list.service.AnimeService;
import by.your_film_list.service.ImageService;
import by.your_film_list.service.ReviewService;
import by.your_film_list.service.UserService;
import by.your_film_list.service.impl.AnimeServiceImpl;
import by.your_film_list.service.impl.ImageServiceImpl;
import by.your_film_list.service.impl.ReviewServiceImpl;
import by.your_film_list.service.impl.UserServiceImpl;

/**
 * The ServiceFactory class is responsible for creating instances of various service classes.
 * It follows the Singleton design pattern, ensuring that only one instance of the factory is created.
 */
public final class ServiceFactory {
    /**
     * The singleton instance of the ServiceFactory.
     */
    private static final ServiceFactory instance = new ServiceFactory();

    /**
     * The instance of the AnimeService.
     */
    private final AnimeService animeService = new AnimeServiceImpl();

    /**
     * The instance of the UserService.
     */
    private final UserService userService = new UserServiceImpl();

    /**
     * The instance of the ReviewService.
     */
    private final ReviewService reviewService = new ReviewServiceImpl();

    /**
     * The instance of the ImageService.
     */
    private final ImageService imageService = new ImageServiceImpl();

    /**
     * Private constructor to prevent external instantiation of the ServiceFactory class.
     */
    private ServiceFactory() {
    }

    /**
     * Returns the instance of the ServiceFactory.
     *
     * @return the ServiceFactory instance
     */
    public static ServiceFactory getInstance() {
        return instance;
    }

    /**
     * Returns the instance of the AnimeService.
     *
     * @return the AnimeService instance
     */
    public AnimeService getAnimeService() {
        return animeService;
    }

    /**
     * Returns the instance of the UserService.
     *
     * @return the UserService instance
     */
    public UserService getUserService() {
        return userService;
    }

    /**
     * Returns the instance of the ReviewService.
     *
     * @return the ReviewService instance
     */
    public ReviewService getReviewService() {
        return reviewService;
    }

    /**
     * Returns the instance of the ImageService.
     *
     * @return the ImageService instance
     */
    public ImageService getImageService() {
        return imageService;
    }
}
