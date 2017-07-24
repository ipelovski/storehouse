package bg.ipelovski.storehouse.command.move;

import bg.ipelovski.storehouse.Locatable;
import bg.ipelovski.storehouse.Storage;
import bg.ipelovski.storehouse.command.Decision;

public class DirectMoveDecision implements Decision<MoveCommand, Mover> {

    static {
        MoveCommand.addDecision(new DirectMoveDecision());
    }

    @Override
    public boolean test(MoveCommand command) {
        Storage storage = command.getStorage();
        Locatable from = storage.find(command.getFrom());
        Locatable to = storage.find(command.getTo());
        return from.getLocation().getStack() != to.getLocation().getStack()
                && to.getLocation().getStack().hasAvailableSpace()
                && from.getLocation().isTop();
    }

    @Override
    public Mover get(MoveCommand command) {
        Storage storage = command.getStorage();
        Locatable from = storage.find(command.getFrom());
        Locatable to = storage.find(command.getTo());
        return new DirectMover(storage, from, to);
    }
}