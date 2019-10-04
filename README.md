<h1 align="center">
  Memory
</h1>

Garett Tok, 2019

This code repository is a project to implement the game Memory in Java. Rules for the game are as follows:

1. Cards (default 52, or one deck) are placed in a grid, face down.
1. Players take turn to flip over two cards at a time to form a match (same value and same color).
1. If there is a match, the cards are removed from the game, and the player is allowed to go again.
1. Otherwise, the cards are set face down in their original positions.
1. The game ends when all the cards have been opened. The player with the most number of open pairs win.

### Features:

1. Any number of players!
2. Multiple decks (up to 3, can be easily modified to take more)
3. Colored output to help players distinguish the cards better.
4. Color-blind mode (colors don't matter, only values do!)

## Languages/Frameworks

Java.

## Set-up

Pre-requisites: Maven, JDK 8.

To run the program, simply:

1. Clone this repository.
1. Run `cd Memory`.
1. Run `mvn package`.
1. Run `java -cp target/Memory-1.jar Memory.App`.

Testing the code can be done by running `mvn test`.

## Discussion

This project was made with Java in order to allow for the possibility of future extension via adding other card games. The broad architecture can be thought of as follows: A game engine (`Memory`), Cards (`Card`), and custom exception for violation of rules (`CustomException`).

The choice of an Object Oriented Programming Language was intentional to reflect how we normally think of and play card games - users interacting with real card objects, with a central game engine that controls for the rules and input/output handling. The `Card` and `CustomException` classes are designed for reusability as consistent frameworks for other card games to be implemented.

Testing is done with JUnit.

## Screenshots

### Main Menu

![Main Menu](https://github.com/walnutdust/memory/blob/master/Screenshots/mainMenu.png 'Main Menu')

### In Game

![In Game](https://github.com/walnutdust/memory/blob/master/Screenshots/inGame.png 'In Game')

### Options

![Options](https://github.com/walnutdust/memory/blob/master/Screenshots/options.png 'Options')
