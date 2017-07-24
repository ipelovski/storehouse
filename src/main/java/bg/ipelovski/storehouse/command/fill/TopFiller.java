package bg.ipelovski.storehouse.command.fill;

import bg.ipelovski.storehouse.Container;

public class TopFiller implements Filler {

    private Container target;

    public TopFiller(Container target) {
        this.target = target;
    }

    @Override
    public int fill(int fluid) {
        if (fluid > 0) {
            int availableSpace = Container.MAX_FLUID_STORAGE - target.getFluid();
            if (fluid > availableSpace) {
                target.setFluid(target.getFluid() + availableSpace);
                return availableSpace;
            } else {
                target.setFluid(target.getFluid() + fluid);
                return fluid;
            }
        } else {
            fluid = -fluid;
            int availableFluid = target.getFluid();
            if (fluid > availableFluid) {
                target.setFluid(0);
                return availableFluid;
            } else {
                target.setFluid(target.getFluid() - fluid);
                return fluid;
            }
        }
    }
}