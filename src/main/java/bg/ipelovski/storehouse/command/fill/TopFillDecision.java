package bg.ipelovski.storehouse.command.fill;

import bg.ipelovski.storehouse.Container;
import bg.ipelovski.storehouse.Locatable;
import bg.ipelovski.storehouse.Storage;
import bg.ipelovski.storehouse.command.Decision;

public class TopFillDecision implements Decision<FillCommand, Filler> {

    public static void init() {
        FillCommand.addDecision(new TopFillDecision());
    }

    @Override
    public boolean test(FillCommand command) {
        Storage storage = command.getStorage();
        Locatable target = storage.find(command.getTarget());
        return target.getLocation().isTop();
    }

    @Override
    public Filler get(FillCommand command) {
        Storage storage = command.getStorage();
        Locatable target = storage.find(command.getTarget());
        if (target instanceof Container) {
            return new TopFiller((Container) target);
        } else {
            throw new RuntimeException("Expected a container to fill but found " + target);
        }
    }
}