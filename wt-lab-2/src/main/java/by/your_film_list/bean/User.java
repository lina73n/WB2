package by.your_film_list.bean;

/**
 * The User class represents a user in the system.
 */
public class User {
    /**
     * The ID of the user.
     */
    private final int id;
    /**
     * The login of the user.
     */
    private final String login;
    /**
     * The privilege role of the user.
     */
    private final UserPrivilegeRole userPrivilegeRole;
    /**
     * The status value of the user.
     */
    private final float statusValue;
    /**
     * Indicates whether the user is banned or not.
     */
    private boolean isBanned;

    /**
     * Constructs a User object with the specified properties.
     *
     * @param id                the ID of the user
     * @param login             the login of the user
     * @param userPrivilegeRole the privilege role of the user
     * @param statusValue       the status value of the user
     * @param isBanned          true if the user is banned, false otherwise
     */
    public User(int id, String login, UserPrivilegeRole userPrivilegeRole, float statusValue, boolean isBanned) {
        this.id = id;
        this.login = login;
        this.userPrivilegeRole = userPrivilegeRole;
        this.statusValue = statusValue;
        this.isBanned = isBanned;
    }

    /**
     * Returns the privilege role of the user.
     *
     * @return the privilege role
     */
    public UserPrivilegeRole getUserPrivilegeRole() {
        return userPrivilegeRole;
    }

    /**
     * Returns the ID of the user.
     *
     * @return the ID
     */
    public int getId() {
        return id;
    }

    /**
     * Returns the status value of the user.
     *
     * @return the status value
     */
    public float getStatusValue() {
        return statusValue;
    }

    /**
     * Returns the login of the user.
     *
     * @return the login
     */
    public String getLogin() {
        return login;
    }

    /**
     * Checks if the user is banned.
     *
     * @return true if the user is banned, false otherwise
     */
    public boolean isBanned() {
        return isBanned;
    }
}
