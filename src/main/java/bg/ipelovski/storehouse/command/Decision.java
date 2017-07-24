package bg.ipelovski.storehouse.command;

import bg.ipelovski.storehouse.command.Command;

public interface Decision <T extends Command, O> {
    boolean test(T command);
    O get(T command);
}
