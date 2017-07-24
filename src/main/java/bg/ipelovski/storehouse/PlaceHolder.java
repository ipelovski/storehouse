package bg.ipelovski.storehouse;

public class PlaceHolder implements Locatable {

    private Location location;

    public PlaceHolder(Location location) {
        this.location = location;
    }

    @Override
    public Location getLocation() {
        return location;
    }
}
