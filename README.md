<h1 align="center">
  Memory
</h1>

Garett Tok, 2019

An implementation of the card game Memory in Java. At the start, a certain number of cards will be flipped upside down, and players take turn to flip open pairs of cards to form matches (same value and same color). If there is a match, the cards are removed from the game. Otherwise, the cards are covered once again. The player with the most number of opened pairs win the game!

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

## Screenshots

### Main Menu

![Main Menu](https://github.com/walnutdust/memory/blob/master/Screenshots/mainMenu.png 'Main Menu')

### In Game

![In Game](https://github.com/walnutdust/memory/blob/master/Screenshots/inGame.png 'In Game')

### Options

![Options](https://github.com/walnutdust/memory/blob/master/Screenshots/options.png 'Options')
