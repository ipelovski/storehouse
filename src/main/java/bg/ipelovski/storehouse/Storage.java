package bg.ipelovski.storehouse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Storage {

    public static final int MAX_STACKS_COUNT = 6;

    private List<StackOfContainers> stacks = new ArrayList<>(MAX_STACKS_COUNT);

    {
        for (int i = 0; i < MAX_STACKS_COUNT; i++) {
            stacks.add(i, new StackOfContainers(i));
        }
    }

    public List<StackOfContainers> getStacks() {
        return stacks;
    }

    public Locatable find(String name) {
        for (StackOfContainers stack : stacks) {
            if (stack.getName() == name) {
                return stack.getPlaceHolder();
            }
            for (Container container : stack.getContainers()) {
                if (container.getName() == name) {
                    return container;
                }
            }
        }
        throw new RuntimeException("No container with name " + name);
    }

    public boolean addContainer(String name) {
        for (StackOfContainers stack : stacks) {
            if (stack.hasAvailableSpace()) {
                stack.addContainer(new Container(name));
                return true;
            }
        }
        return false;
    }


    public boolean addContainer(String name, int stackId) {
        for (StackOfContainers stack : stacks) {
            if (stack.getId() == stackId && stack.hasAvailableSpace()) {
                stack.addContainer(new Container(name));
                return true;
            }
        }
        return false;
    }

    public int availableSpace(StackOfContainers ...exclude) {
        int sum = 0;
        for (StackOfContainers stack : stacks) {
            if (Arrays.stream(exclude).noneMatch(s -> s == stack)) {
                sum += stack.availableSpace();
            }
        }
        return sum;
    }

    public Optional<StackOfContainers> findTallestAvailableStack(StackOfContainers exclude) {
        int max = -1;
        int index = -1;
        for (int i = 0; i < stacks.size(); i++) {
            StackOfContainers stack = stacks.get(i);
            int containersCount = stack.getContainers().size();
            if (stack != exclude && containersCount > max
                    && containersCount < StackOfContainers.MAX_CONTAINERS_COUNT) {
                max = containersCount;
                index = i;
            }
        }
        if (index > -1) {
            return Optional.of(stacks.get(index));
        } else {
            return Optional.empty();
        }
    }

    public void print() {
        for (int i = StackOfContainers.MAX_CONTAINERS_COUNT - 1; i >= 0; i--) {
            for (StackOfContainers stack : stacks) {
                if (stack.getContainers().size() > i) {
                    Container container = stack.getContainers().get(i);
                    System.out.print(String.format("|%1s%2$5s",
                            container.getName(),
                            String.format("(%1s)", container.getFluid())));
                } else {
                    System.out.print(String.format("|%1$7s", ""));
                }
            }
            System.out.println("|");
        }
        for (int i = 0; i < stacks.size() * 8; i++) {
            System.out.print("-");
        }
        System.out.println("-");
        for (StackOfContainers stack : stacks) {
            System.out.print(String.format("|%1s", stack.getName().substring(0, 7)));
        }
        System.out.println("|");
    }
}
