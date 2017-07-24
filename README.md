# A Storehouse

A container is represented by instances of the `Container` class. Each container is placed on a stack of containers (`StackOfContainers`). All stacks are inside a storage (`Storage`).

Adding containers to the storage is done with
```
storage.addContainer(/*name:*/ "AA", /*stack id:*/ 0);
```

After the storage is filled with some containers the robot can move containers using the following commands:
```
MoveCommand moveCommand = new MoveCommand(storage, /*from:*/ "AC", /*to:*/ "CC", history);
moveCommand.execute();
```
The history (an instance of `CommandHistory`) maintains a stack of commands and can be used to undo latest commands.


After a command is issued it checks for available strategies that can fulfill the command.
Each strategy is implemented with a pair of classes. One, named a decision and implementing the `Decision<>` interface, checks if it knows whether it can fulfill the command, and another class that makes the necessary actions to fulfill the command.
For example the `DirectMoveDecision` checks if the first container is already on top of its stack so it can be moved directly on top of the second container. An instance of the `DirectMover` class actually carries the command and performs the necessary moves (in this case only one move).

The move command and its strategies are placed in the package `bg.ipelovski.storehouse.command.move`, and the fill command and its strategies are placed in the package `bg.ipelovski.storehouse.command.fill`.

Usages of the commands can be found in the `Application` class and inside the few test classes.

Disclaimer: The current implementation of the storehouse contains bugs and certainly can be improved in lot of ways, but it runs in its current rudimentary implementation.