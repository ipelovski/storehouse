package bg.ipelovski.storehouse.command.move;

import bg.ipelovski.storehouse.Locatable;
import bg.ipelovski.storehouse.StackOfContainers;
import bg.ipelovski.storehouse.Storage;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SameStackMover extends BaseMover {

    private List<MoveStep> steps = new ArrayList<>();

    public SameStackMover(Storage storage, Locatable from, Locatable to) {
        super(storage, from, to);
    }

    @Override
    public void move() {
        List<MoveStep> tempSteps = new ArrayList<>();
        Optional<StackOfContainers> maybeTallestStack =
                storage.findTallestAvailableStack(to.getLocation().getStack());
        StackOfContainers tallestStack = maybeTallestStack.orElseThrow(
                () -> new RuntimeException("No stack found."));
        while (!from.getLocation().isTop()) {
            StackOfContainers stack = findAvailableStack(tallestStack);
            tempSteps.add(makeStep(fromLocation.getStack(), stack));
        }
        makeStep(fromLocation.getStack(), tallestStack);
        while (tempSteps.size() > 0) {
            MoveStep step = tempSteps.remove(tempSteps.size() - 1);
            makeStep(step.getTo(), step.getFrom());
        }
        makeStep(tallestStack, fromLocation.getStack());
    }

    @Override
    public void moveBack() {
        while (steps.size() > 0) {
            MoveStep step = steps.remove(steps.size() - 1);
            new MoveStep(step.getTo(), step.getFrom()).make();
        }
    }

    private StackOfContainers findAvailableStack(StackOfContainers exclude) {
        for (StackOfContainers stack : storage.getStacks()) {
            if (stack != fromLocation.getStack()
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
