package bg.ipelovski.storehouse.command.move;

import bg.ipelovski.storehouse.Storage;
import bg.ipelovski.storehouse.command.Command;
import bg.ipelovski.storehouse.command.CommandHistory;
import bg.ipelovski.storehouse.command.Decision;

import java.util.ArrayList;
import java.util.List;

public class MoveCommand implements Command {

    private static class DecisionMaker {

        private List<Decision<MoveCommand, Mover>> decisions = new ArrayList<>();

        private Mover get(MoveCommand command) {
            for (Decision<MoveCommand, Mover> decision : decisions) {
                if (decision.test(command)) {
                    return decision.get(command);
                }
            }
            throw new RuntimeException("No mover found");
        }

        private void add(Decision<MoveCommand, Mover> decision) {
            decisions.add(decision);
        }
    }

    private static final DecisionMaker decisionMaker = new DecisionMaker();

    private Storage storage;
    private String from;
    private String to;
    private CommandHistory history;
    private Mover mover;
    private boolean moved = false;

    public MoveCommand(Storage storage, String from, String to, CommandHistory history) {
        this.storage = storage;
        this.from = from;
        this.to = to;
        this.history = history;
    }

    public static void addDecision(Decision<MoveCommand, Mover> decision) {
        decisionMaker.add(decision);
    }

    public Storage getStorage() {
        return storage;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    @Override
    public void execute() {
        if (moved) {
            throw new RuntimeException("Already moved.");
        }
        mover = decisionMaker.get(this);
        moved = true;
        mover.move();
        history.add(this);
    }

    @Override
    public void undo() {
        if (history.getLast() != this) {
            throw new RuntimeException("This command is not the last one: " + this);
        }
        mover.moveBack();
        moved = false;
        mover = null;
    }

    @Override
    public String toString() {
        return String.format("Move command from %1s to %2s", from, to);
    }
}
