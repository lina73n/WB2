package by.your_film_list.controller;

/**
 * JspPage is an enumeration class that defines the JSP pages used in the application.
 * It provides the names of the JSP pages as constants and a method to retrieve the name of a page.
 */
public enum JspPage {
    ANIME_LIST("/anime_list.jsp"),
    LOGIN_PAGE("/login.jsp"),
    REGISTER_PAGE("/register.jsp"),
    ERROR_PAGE("/error.jsp"),
    ANIME("/anime_page.jsp"),
    PROFILE("/profile.jsp"),
    ADD_ANIME("/add_anime.jsp");

    private final String name;

    /**
     * Constructs a JspPage enum constant with the specified name.
     *
     * @param name the name of the JSP page
     */
    JspPage(String name) {
        this.name = name;
    }

    /**
     * Returns the name of the JSP page.
     *
     * @return the name of the JSP page
     */
    public String getName() {
        return name;
    }
}
