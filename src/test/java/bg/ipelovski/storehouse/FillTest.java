package bg.ipelovski.storehouse;

import bg.ipelovski.storehouse.command.CommandHistory;
import bg.ipelovski.storehouse.command.fill.FillCommand;
import junit.framework.TestCase;

public class FillTest extends TestCase {

    public void testShouldFillTopContainer() throws Exception {
        Storage storage = Storages.build();
        CommandHistory history = new CommandHistory();

        storage.addContainer("AA", 0);
        storage.addContainer("AB", 0);
        storage.addContainer("AC", 0);
        storage.addContainer("BB", 1);
        storage.addContainer("CC", 2);
        storage.addContainer("DD", 3);

        FillCommand fillCommand = new FillCommand(storage, "AC", 10, history);
        fillCommand.execute();

        Container containerFrom = (Container)storage.find("AC");

        assertEquals("Container AC should contain fluid", 10,
                containerFrom.getFluid());
    }

    public void testShouldFillBottomContainer() throws Exception {
        Storage storage = Storages.build();
        CommandHistory history = new CommandHistory();

        storage.addContainer("AA", 0);
        storage.addContainer("AB", 0);
        storage.addContainer("AC", 0);
        storage.addContainer("BB", 1);
        storage.addContainer("CC", 2);
        storage.addContainer("DD", 3);

        FillCommand fillCommand = new FillCommand(storage, "AA", 10, history);
        fillCommand.execute();

        Container containerFrom = (Container)storage.find("AA");

        assertEquals("Container AA should contain fluid", 10,
                containerFrom.getFluid());
    }

    public void testNotEnoughSpace() throws Exception {
        Storage storage = Storages.build(2, 2);
        CommandHistory history = new CommandHistory();

        storage.addContainer("AA", 0);
        storage.addContainer("AB", 0);
        storage.addContainer("BA", 1);
        storage.addContainer("BB", 1);

        FillCommand fillCommand = new FillCommand(storage, "AA", 10, history);
        RuntimeException exception = null;
        try {
            fillCommand.execute();
        } catch (RuntimeException e) {
            exception = e;
        }
        assertNotNull("Exception should be thrown", exception);
        assertEquals("No filled should be found", "No filler found", exception.getMessage());
    }

    public void testUndoRedo() throws Exception {
        Storage storage = Storages.build();
        CommandHistory history = new CommandHistory();

        storage.addContainer("AA", 0);
        storage.addContainer("AB", 0);
        storage.addContainer("AC", 0);
        storage.addContainer("BB", 1);
        storage.addContainer("CC", 2);
        storage.addContainer("DD", 3);

        FillCommand fillCommand = new FillCommand(storage, "AC", 10, history);
        fillCommand.execute();

        Container containerFrom = (Container)storage.find("AC");

        assertEquals("Container AC should contain fluid", 10,
                containerFrom.getFluid());

        fillCommand.undo();

        assertEquals("Container AC should contain no fluid", 0,
                containerFrom.getFluid());

        fillCommand.execute();

        assertEquals("Container AC should contain fluid", 10,
                containerFrom.getFluid());
    }
}
