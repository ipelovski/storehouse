package bg.ipelovski.storehouse.command.move;

import bg.ipelovski.storehouse.Locatable;
import bg.ipelovski.storehouse.StackOfContainers;
import bg.ipelovski.storehouse.Storage;
import bg.ipelovski.storehouse.command.Decision;

import java.util.Optional;

public class SameStackMoveDecision implements Decision<MoveCommand, Mover> {

    static {
        MoveCommand.addDecision(new SameStackMoveDecision());
    }

    @Override
    public boolean test(MoveCommand command) {
        Storage storage = command.getStorage();
        Locatable from = storage.find(command.getFrom());
        Locatable to = storage.find(command.getTo());
        if (from.getLocation().getStack() == to.getLocation().getStack()) {
            Optional<StackOfContainers> maybeTallestStack = storage.findTallestAvailableStack(
                    to.getLocation().getStack());
            StackOfContainers tallestStack = maybeTallestStack.orElseThrow(
                    () -> new RuntimeException("No stack found."));
            int availableSpace = storage.availableSpace(to.getLocation().getStack(), tallestStack);
            if (availableSpace >= from.getLocation().offset()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Mover get(MoveCommand command) {
        Storage storage = command.getStorage();
        Locatable from = storage.find(command.getFrom());
        Locatable to = storage.find(command.getTo());
        return new SameStackMover(storage, from, to);
    }
}