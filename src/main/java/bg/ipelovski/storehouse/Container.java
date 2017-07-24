package bg.ipelovski.storehouse;

public class Container implements Locatable {

    public static final int MAX_FLUID_STORAGE = 50;
    private String name;
    private Location location;
    private int fluid = 0;

    public Container(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public int getFluid() {
        return fluid;
    }

    public void setFluid(int fluid) {
        if (fluid < 0) {
            throw new RuntimeException("Fluid cannot be negative " + fluid);
        }
        if (fluid > MAX_FLUID_STORAGE) {
            throw new RuntimeException("Fluid is too much " + fluid + " max is " + MAX_FLUID_STORAGE);
        }
        this.fluid = fluid;
    }

    @Override
    public String toString() {
        return String.format("Container %1s @ %2s", name, location);
    }
}
