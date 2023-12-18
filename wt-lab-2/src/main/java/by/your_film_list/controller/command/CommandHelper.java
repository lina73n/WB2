package by.your_film_list.controller.command;

import by.your_film_list.controller.command.impl.*;
import java.util.HashMap;
import java.util.Map;

/**
 * CommandHelper is a class that provides a mapping between command names and their respective Command implementations.
 * It contains a collection of commands stored in a HashMap,
 * where the command names are the keys and the Command objects are the values.
 * The class follows the Singleton design pattern, ensuring that only one instance of CommandHelper exists.
 * It also provides a method to retrieve the Command object based on a given command name.
 */
public final class CommandHelper {
    private static final CommandHelper instance = new CommandHelper();
    private static final Map<CommandName, Command> commands = new HashMap<>() {{
        put(CommandName.ANIME_LIST, new FilmListCommand());
        put(CommandName.ANIME, new ShowAnimeCommand());
        put(CommandName.NO_SUCH_COMMAND, new NoSuchCommand());
        put(CommandName.REGISTER, new RegisterCommand());
        put(CommandName.LOGIN, new LoginCommand());
        put(CommandName.DO_LOGIN, new DoLoginCommand());
        put(CommandName.DO_REGISTER, new DoRegisterCommand());
        put(CommandName.PROFILE, new ProfileCommand());
        put(CommandName.LOGOUT, new LogoutCommand());
        put(CommandName.ADD_REVIEW, new AddReviewCommand());
        put(CommandName.ADD_ANIME, new AddFilmCommand());
        put(CommandName.DO_ADD_ANIME, new DoAddAnimeCommand());
        put(CommandName.CHANGE_LANGUAGE, new ChangeLanguageCommand());
        put(CommandName.BAN, new BanCommand());
        put(CommandName.UNBAN, new UnbanCommand());
        put(CommandName.PREV_ANIME, new PrevAnimeCommand());
        put(CommandName.NEXT_ANIME, new NextAnimeCommand());
    }};

    public static CommandHelper getInstance() {
        return instance;
    }

    /**
     * Retrieves the Command object based on the given command name.
     * If the command name is not found, returns the Command object for the NO_SUCH_COMMAND name.
     * @param commandName the name of the command
     * @return the Command object associated with the command name
     */
    public Command getCommand(String commandName) {
        Command command;
        CommandName name = null;
        try {
            name = CommandName.valueOf(commandName.toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        if (name != null) {
            command = commands.get(name);
        } else {
            command = commands.get(CommandName.NO_SUCH_COMMAND);
        }
        return command;
    }
}
