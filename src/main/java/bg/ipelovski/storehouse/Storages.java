package bg.ipelovski.storehouse;

import bg.ipelovski.storehouse.command.Decision;
import bg.ipelovski.storehouse.command.fill.BottomFillDecision;
import bg.ipelovski.storehouse.command.fill.TopFillDecision;
import bg.ipelovski.storehouse.command.move.DirectMoveDecision;
import bg.ipelovski.storehouse.command.move.IndirectMoveDecision;
import bg.ipelovski.storehouse.command.move.SameStackMoveDecision;

public class Storages {

    public static <T extends Decision<?, ?>> void loadDecisions(Class<? extends T> ...classes) {
        for (Class<? extends T> tClass : classes) {
            try {
                Class.forName(tClass.getCanonicalName());
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static Storage build() {
        return build(5, 5);
    }

    public static Storage build(int maxStacksCount, int maxStackSize) {

        Storage storage = new Storage(maxStacksCount, maxStackSize);

        loadDecisions(
                DirectMoveDecision.class,
                IndirectMoveDecision.class,
                SameStackMoveDecision.class,
                TopFillDecision.class,
                BottomFillDecision.class
        );

        return storage;
    }
}
