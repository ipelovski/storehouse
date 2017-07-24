package bg.ipelovski.storehouse.command.fill;

import bg.ipelovski.storehouse.Container;
import bg.ipelovski.storehouse.Location;
import bg.ipelovski.storehouse.StackOfContainers;
import bg.ipelovski.storehouse.Storage;
import bg.ipelovski.storehouse.command.move.MoveStep;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BottomFiller implements Filler {

    private List<MoveStep> steps = new ArrayList<>();
    private Storage storage;
    private Container target;
    private Location targetLocation;

    public BottomFiller(Storage storage, Container target) {
        this.storage = storage;
        this.target = target;
        this.targetLocation = target.getLocation();
    }

    @Override
    public int fill(int fluid) {
        List<MoveStep> tempSteps = new ArrayList<>();
        Optional<StackOfContainers> maybeTallestStack = storage.findTallestAvailableStack(
                targetLocation.getStack());
        StackOfContainers tallestStack = maybeTallestStack.orElseThrow(
                () -> new RuntimeException("No stack found."));
        while (!target.getLocation().isTop()) {
            StackOfContainers stack = findAvailableStack(tallestStack);
            tempSteps.add(makeStep(targetLocation.getStack(), stack));
        }
        TopFiller filler = new TopFiller(target);
        int filledFluid = filler.fill(fluid);
        while (tempSteps.size() > 0) {
            MoveStep step = tempSteps.remove(tempSteps.size() - 1);
            makeStep(step.getTo(), step.getFrom());
        }
        return filledFluid;
    }

    private StackOfContainers findAvailableStack(StackOfContainers exclude) {
        for (StackOfContainers stack : storage.getStacks()) {
            if (stack != target.getLocation().getStack()
                    && stack != exclude
                    && stack.hasAvailableSpace()) {
                return stack;
            }
        }
        throw new RuntimeException("No available stacks found.");
    }

    // TODO duplicate
    private MoveStep makeStep(StackOfContainers from, StackOfContainers to) {
        MoveStep step = new MoveStep(from, to);
        steps.add(step);
        step.make();
        return step;
    }
}