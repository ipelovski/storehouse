package bg.ipelovski.storehouse;

import bg.ipelovski.storehouse.command.CommandHistory;
import bg.ipelovski.storehouse.command.fill.BottomFillDecision;
import bg.ipelovski.storehouse.command.fill.FillCommand;
import bg.ipelovski.storehouse.command.fill.TopFillDecision;
import bg.ipelovski.storehouse.command.move.DirectMoveDecision;
import bg.ipelovski.storehouse.command.move.IndirectMoveDecision;
import bg.ipelovski.storehouse.command.move.MoveCommand;
import bg.ipelovski.storehouse.command.move.SameStackMoveDecision;

public class Application {

    public static void main(String[] args) {

        Storage storage = new Storage();
        CommandHistory history = new CommandHistory();

        DirectMoveDecision.init();
        IndirectMoveDecision.init();
        SameStackMoveDecision.init();

        TopFillDecision.init();
        BottomFillDecision.init();
        
        System.out.println("Initial:");
        storage.addContainer("AA", 0);
        storage.addContainer("AB", 0);
        storage.addContainer("AC", 0);
        storage.addContainer("BB", 1);
        storage.addContainer("CC", 2);
        storage.addContainer("DD", 3);
        storage.print();

        System.out.println("Direct move:");
        MoveCommand moveCommand = new MoveCommand(storage, "AC", "CC", history);
        moveCommand.execute();
//        history.undoLast();
        storage.print();

        System.out.println("Direct move undo:");
        moveCommand.undo();
        storage.print();

        System.out.println("Indirect move:");
        moveCommand = new MoveCommand(storage,"AA", "CC", history);
        moveCommand.execute();
//        history.undoLast();
        storage.print();

        System.out.println("Indirect move undo:");
        moveCommand.undo();
        storage.print();

        System.out.println("Same stack move:");
        moveCommand = new MoveCommand(storage, "AA", "AC", history);
        moveCommand.execute();
//        history.undoLast();
        storage.print();

        System.out.println("Same stack move undo:");
        moveCommand.undo();
        storage.print();

        System.out.println("Top fill:");
        FillCommand fillCommand = new FillCommand(storage, "AC", 10, history);
        fillCommand.execute();
//        history.undoLast();
        storage.print();

        System.out.println("Top fill undo:");
        fillCommand.undo();
        storage.print();

        System.out.println("Bottom fill:");
        fillCommand = new FillCommand(storage, "AA", 10, history);
        fillCommand.execute();
//        history.undoLast();
        storage.print();

        System.out.println("Bottom fill undo:");
        fillCommand.undo();
        storage.print();
    }
}
