package bg.ipelovski.storehouse.command.fill;

import bg.ipelovski.storehouse.Container;
import bg.ipelovski.storehouse.Locatable;
import bg.ipelovski.storehouse.StackOfContainers;
import bg.ipelovski.storehouse.Storage;
import bg.ipelovski.storehouse.command.Decision;

import java.util.Optional;

public class BottomFillDecision implements Decision<FillCommand, Filler> {

    public static void init() {
        FillCommand.addDecision(new BottomFillDecision());
    }

    @Override
    public boolean test(FillCommand command) {
        Storage storage = command.getStorage();
        Locatable target = storage.find(command.getTarget());
        if (!target.getLocation().isTop()) {
            Optional<StackOfContainers> maybeTallestStack =
                    storage.findTallestAvailableStack(target.getLocation().getStack());
            StackOfContainers tallestStack = maybeTallestStack.orElseThrow(
                    () -> new RuntimeException("No stack found."));
            int availableSpace = storage.availableSpace(target.getLocation().getStack(), tallestStack);
            return availableSpace > target.getLocation().offset();
        } else {
            return false;
        }
    }

    @Override
    public Filler get(FillCommand command) {
        Storage storage = command.getStorage();
        Locatable target = storage.find(command.getTarget());
        if (target instanceof Container) {
            return new BottomFiller(storage, (Container) target);
        } else {
            throw new RuntimeException("Expected a container to fill but found " + target);
        }
    }
}