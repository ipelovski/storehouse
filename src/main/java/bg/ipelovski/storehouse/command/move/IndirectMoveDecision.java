package bg.ipelovski.storehouse.command.move;

import bg.ipelovski.storehouse.Locatable;
import bg.ipelovski.storehouse.Storage;
import bg.ipelovski.storehouse.command.Decision;

public class IndirectMoveDecision implements Decision<MoveCommand, Mover> {

    public static void init() {
        MoveCommand.addDecision(new IndirectMoveDecision());
    }

    @Override
    public boolean test(MoveCommand command) {
        Storage storage = command.getStorage();
        Locatable from = storage.find(command.getFrom());
        Locatable to = storage.find(command.getTo());
        if (from.getLocation().getStack() != to.getLocation().getStack()) {
            int availableSpace = storage.availableSpace(to.getLocation().getStack());
            if (availableSpace > from.getLocation().offset()) {
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
        return new IndirectMover(storage, from, to);
    }
}