package bg.ipelovski.storehouse.command.move;

import bg.ipelovski.storehouse.Locatable;
import bg.ipelovski.storehouse.Location;
import bg.ipelovski.storehouse.Storage;

public class DirectMover extends BaseMover {

    public DirectMover(Storage storage, Locatable from, Locatable to) {
        super(storage, from, to);
    }

    @Override
    public void move() {
        innerMove(fromLocation, toLocation);
    }

    @Override
    public void moveBack() {
        innerMove(toLocation, fromLocation);
    }

    private void innerMove(Location from, Location to) {
        new MoveStep(from.getStack(), to.getStack()).make();
    }
}
