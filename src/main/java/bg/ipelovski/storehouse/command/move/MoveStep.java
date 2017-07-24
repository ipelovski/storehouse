package bg.ipelovski.storehouse.command.move;

import bg.ipelovski.storehouse.Container;
import bg.ipelovski.storehouse.StackOfContainers;

import java.util.Optional;

public class MoveStep {

    private StackOfContainers from, to;

    public MoveStep(StackOfContainers from, StackOfContainers to) {
        this.from = from;
        this.to = to;
    }

    public StackOfContainers getFrom() {
        return from;
    }

    public StackOfContainers getTo() {
        return to;
    }

    public void make() {
        Optional<Container> container = from.removeTopContainer();
        container.ifPresent(b -> to.addContainer(b));
    }
}
