package bg.ipelovski.storehouse;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StackOfContainers {

    private final int id;
    private int maxStackSize;
    private List<Container> containers;
    private PlaceHolder placeHolder = new PlaceHolder(new Location(this, null, -1));

    public StackOfContainers(int id, int maxStackSize) {
        this.id = id;
        this.maxStackSize = maxStackSize;
        containers = new ArrayList<>(maxStackSize);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return "Stack " + id;
    }

    public PlaceHolder getPlaceHolder() {
        return placeHolder;
    }

    public List<Container> getContainers() {
        return containers;
    }

    public boolean isTop(Container container) {
        return container.getLocation().getIndex() == containers.size() - 1;
    }

    public int availableSpace() {
        return maxStackSize - containers.size();
    }

    public boolean hasAvailableSpace() {
        return availableSpace() > 0;
    }

    public void addContainer(Container container) {
        if (!hasAvailableSpace()) {
            throw new RuntimeException("Not enough space.");
        }
        containers.add(container);
        container.setLocation(new Location(this, container, containers.size() - 1));
    }

    public Optional<Container> removeTopContainer() {
        if (containers.size() > 0) {
            Container topContainer = containers.remove(containers.size() - 1);
            topContainer.setLocation(null);
            return Optional.of(topContainer);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public String toString() {
        return getName();
    }
}
