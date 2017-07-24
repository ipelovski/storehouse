package bg.ipelovski.storehouse.command.fill;

import bg.ipelovski.storehouse.Storage;
import bg.ipelovski.storehouse.command.Command;
import bg.ipelovski.storehouse.command.CommandHistory;
import bg.ipelovski.storehouse.command.Decision;

import java.util.ArrayList;
import java.util.List;

public class FillCommand implements Command {

    private static class DecisionMaker {
        List<Decision<FillCommand, Filler>> decisions = new ArrayList<>();
        Filler get(FillCommand command) {
            for (Decision<FillCommand, Filler> decision : decisions) {
                if (decision.test(command)) {
                    return decision.get(command);
                }
            }
            throw new RuntimeException("No filler found");
        }

        private void add(Decision<FillCommand, Filler> decision) {
            decisions.add(decision);
        }
    }

    private static final DecisionMaker decisionMaker = new DecisionMaker();
//    static {
//        decisionMaker.decisions.add(new TopFillDecision());
//        decisionMaker.decisions.add(new BottomFillDecision());
//    }

    private Storage storage;
    private String target;
    private int fluid;
    private CommandHistory history;
    private Filler filler;
    private boolean filled = false;
    private int filledFluid = 0;

    public FillCommand(Storage storage, String target, int fluid, CommandHistory history) {
        this.storage = storage;
        this.target = target;
        this.fluid = fluid;
        this.history = history;
    }

    public static void addDecision(Decision<FillCommand, Filler> decision) {
        decisionMaker.add(decision);
    }

    public Storage getStorage() {
        return storage;
    }

    public String getTarget() {
        return target;
    }

    @Override
    public void execute() {
        if (filled) {
            throw new RuntimeException("Already filled.");
        }
        filler = decisionMaker.get(this);
        filled = true;
        filledFluid = filler.fill(fluid);
        history.add(this);
    }

    @Override
    public void undo() {
        if (history.getLast() != this) {
            throw new RuntimeException("This command is not the last one: " + this);
        }
        filler.fill(-filledFluid);
        filled = false;
        filledFluid = 0;
        filler = null;
    }
}