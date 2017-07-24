package bg.ipelovski.storehouse.command.move;

import bg.ipelovski.storehouse.Locatable;
import bg.ipelovski.storehouse.Location;
import bg.ipelovski.storehouse.Storage;

public abstract class BaseMover implements Mover {

    protected Storage storage;
    protected Locatable from;
    protected Locatable to;
    protected Location fromLocation;
    protected Location toLocation;

    protected BaseMover(Storage storage, Locatable from, Locatable to) {
        this.storage = storage;
        this.from = from;
        this.to = to;
        fromLocation = from.getLocation();
        toLocation = to.getLocation();
    }

    @Override
    public abstract void move();

    @Override
    public abstract void moveBack();
}