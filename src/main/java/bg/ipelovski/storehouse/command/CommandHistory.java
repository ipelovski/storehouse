package bg.ipelovski.storehouse.command;

import java.util.ArrayList;
import java.util.List;

public class CommandHistory {

    private List<Command> commands = new ArrayList<>();

    public void add(Command command) {
        commands.add(command);
    }

    public Command getLast() {
        if (commands.size() > 0) {
            return commands.get(commands.size() - 1);
        } else {
            return null;
        }
    }

    public Command undoLast() {
        if (commands.size() > 0) {
            getLast().undo();
            return commands.remove(commands.size() - 1);
        } else {
            return null;
        }
    }
}
