package by.your_film_list.controller;

import by.your_film_list.controller.command.CommandName;

/**
 * This enum class represents the redirect addresses used in the application.
 * Each redirect address is associated with a specific command name.
 * The redirect addresses are used to redirect the user to different pages based on the result of a command execution.
 * The addresses are constructed using a base address and the corresponding command name in lowercase.
 */
public enum RedirectAddress {
    ERROR(CommandName.NO_SUCH_COMMAND.name()),
    LOGIN_SUCCESS(CommandName.ANIME_LIST.name()),
    LOGIN_FAILED(CommandName.LOGIN.name()),
    REGISTRATION_SUCCESS(CommandName.ANIME_LIST.name()),
    REGISTRATION_FAILED(CommandName.REGISTER.name()),
    ANIME_ADD_SUCCESS(CommandName.ADD_ANIME.name()),
    ANIME_ADD_FAILED(CommandName.ADD_ANIME.name()),
    REVIEW_ADD_SUCCESS(CommandName.ANIME.name()),
    PROFILE(CommandName.PROFILE.name()),
    ANIME_LIST(CommandName.ANIME_LIST.name())
    ;

    private static final String BASE_ADDRESS = "/your_anime_list/controller?command=";

    private final String address;

    /**
     * Constructs a RedirectAddress with the given address.
     * The address is constructed by appending the given address to the base address.
     * @param address the address to be appended to the base address
     */
    RedirectAddress(String address) {
        this.address = BASE_ADDRESS + address.toLowerCase();
    }

    /**
     * Returns the full address.
     * @return the full address
     */
    public String getAddress() {
        return address;
    }
}
