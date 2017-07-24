package bg.ipelovski.storehouse;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StackOfContainers {

    public static final int MAX_CONTAINERS_COUNT = 5;
    private final int id;
    private List<Container> containers = new ArrayList<>(MAX_CONTAINERS_COUNT);
    private PlaceHolder placeHolder = new PlaceHolder(new Location(this, null, -1));

    public StackOfContainers(int id) {
        this.id = id;
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
        return MAX_CONTAINERS_COUNT - containers.size();
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
        if (containers.size() > 1) { // do not remove the bottom container
            Container topContainer = containers.remove(containers.size() - 1);
            topContainer.setLocation(null);
            return Optional.of(topContainer);
        } else {
            return Optional.empty();
        }
    }
}
