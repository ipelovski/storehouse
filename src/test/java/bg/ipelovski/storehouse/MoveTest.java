package bg.ipelovski.storehouse;

import bg.ipelovski.storehouse.command.CommandHistory;
import bg.ipelovski.storehouse.command.move.MoveCommand;
import junit.framework.TestCase;

public class MoveTest extends TestCase {

    public void testShouldMoveContainerDirectly() throws Exception {
        Storage storage = Storages.build();
        CommandHistory history = new CommandHistory();

        storage.addContainer("AA", 0);
        storage.addContainer("AB", 0);
        storage.addContainer("AC", 0);
        storage.addContainer("BB", 1);
        storage.addContainer("CC", 2);
        storage.addContainer("DD", 3);

        MoveCommand moveCommand = new MoveCommand(storage, "AC", "CC", history);
        moveCommand.execute();

        Locatable containerFrom = storage.find("AC");
        Locatable containerTo = storage.find("CC");

        assertTrue("Container AC should be on top", containerFrom.getLocation().isTop());
        assertEquals("Container AC should be on stack 2", 2,
                containerFrom.getLocation().getStack().getId());
        assertFalse("Container CC should not be on top", containerTo.getLocation().isTop());
        assertEquals("Container CC should still be on stack 2", 2,
                containerTo.getLocation().getStack().getId());
    }

    public void testShouldMoveContainerIndirectly() throws Exception {
        Storage storage = Storages.build();
        CommandHistory history = new CommandHistory();

        storage.addContainer("AA", 0);
        storage.addContainer("AB", 0);
        storage.addContainer("AC", 0);
        storage.addContainer("BB", 1);
        storage.addContainer("CC", 2);
        storage.addContainer("DD", 3);

        MoveCommand moveCommand = new MoveCommand(storage, "AA", "CC", history);
        moveCommand.execute();

        Locatable containerFrom = storage.find("AA");
        Locatable containerTo = storage.find("CC");

        assertTrue("Container AA should be on top", containerFrom.getLocation().isTop());
        assertEquals("Container AA should be on stack 2", 2,
                containerFrom.getLocation().getStack().getId());
        assertFalse("Container CC should not be on top", containerTo.getLocation().isTop());
        assertEquals("Container CC should still be on stack 2", 2,
                containerTo.getLocation().getStack().getId());
    }

    public void testShouldMoveContainerUpInStack() throws Exception {
        Storage storage = Storages.build();
        CommandHistory history = new CommandHistory();

        storage.addContainer("AA", 0);
        storage.addContainer("AB", 0);
        storage.addContainer("AC", 0);
        storage.addContainer("BB", 1);
        storage.addContainer("CC", 2);
        storage.addContainer("DD", 3);

        MoveCommand moveCommand = new MoveCommand(storage, "AA", "AC", history);
        moveCommand.execute();

        Locatable containerFrom = storage.find("AA");
        Locatable containerTo = storage.find("AC");

        assertTrue("Container AA should be on top", containerFrom.getLocation().isTop());
        assertEquals("Container AA should be on stack 0", 0,
                containerFrom.getLocation().getStack().getId());
        assertFalse("Container AC should not be on top", containerTo.getLocation().isTop());
        assertEquals("Container AC should still be on stack 0", 0,
                containerTo.getLocation().getStack().getId());
    }

    public void testToIsNotOnTop() throws Exception {
        Storage storage = Storages.build();
        CommandHistory history = new CommandHistory();

        storage.addContainer("AA", 0);
        storage.addContainer("AB", 0);
        storage.addContainer("AC", 0);
        storage.addContainer("BA", 1);
        storage.addContainer("BB", 1);
        storage.addContainer("CC", 2);
        storage.addContainer("DD", 3);

        MoveCommand moveCommand = new MoveCommand(storage, "AA", "BA", history);
        RuntimeException exception = null;
        try {
            moveCommand.execute();
        } catch (RuntimeException e) {
            exception = e;
        }
        assertNotNull("Exception should be thrown", exception);
        assertEquals("No mover should be found", "The 'to' container should be on top.", exception.getMessage());
    }

    public void testNotEnoughSpace() throws Exception {
        Storage storage = Storages.build(2, 2);
        CommandHistory history = new CommandHistory();

        storage.addContainer("AA", 0);
        storage.addContainer("AB", 0);
        storage.addContainer("BB", 1);

        MoveCommand moveCommand = new MoveCommand(storage, "AA", "BB", history);
        RuntimeException exception = null;
        try {
            moveCommand.execute();
        } catch (RuntimeException e) {
            exception = e;
        }
        assertNotNull("Exception should be thrown", exception);
        assertEquals("No mover should be found", "No mover found", exception.getMessage());
    }
}
