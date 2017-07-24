package bg.ipelovski.storehouse.command;

public interface Command {
    void execute();
    void undo();
}
