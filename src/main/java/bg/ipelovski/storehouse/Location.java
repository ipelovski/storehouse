package bg.ipelovski.storehouse;

public class Location {

    private StackOfContainers stack;
    private Container container;
    private int index;

    public Location(StackOfContainers stack, Container container, int index) {
        this.stack = stack;
        this.container = container;
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public StackOfContainers getStack() {
        return stack;
    }

    public boolean isTop() {
        return stack.isTop(container);
    }

    public int offset() {
        return stack.getContainers().size() - 1 - index;
    }

    @Override
    public String toString() {
        return String.format("%1s/%2s", index, stack.getId());
    }
}
