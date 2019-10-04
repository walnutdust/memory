package Memory;

import java.util.*;

/**
 * This class is designed for a game of Memory (also known as Concentration).
 */
public class Memory {

    // Number of playing cards in a deck.
    public static final int CARDS_PER_DECK = 52;
    // Number of rows in state.
    public static final int NUM_ROWS = 13;

    // Number of Decks in play
    private int numDecks = 1;

    // Variables holding the number of rows and columns in the state.
    // Defaults to variables for one deck.
    private int numRows = NUM_ROWS;
    private int numCols = 4 * numDecks;

    // A 2-D Matrix of Cards which hold the state of the game.
    private Card[][] state = new Card[numRows][numCols];

    // An integer array holding the currently selected indices.
    private int[] currSelected = { -1, -1 };

    // The currently selected cards, if any;
    private Card selectedCard = null;
    private Card otherSelectedCard = null;

    // An integer representing the id of the player in turn..
    private int currPlayerInGame = 0;

    // An integer representing the number of players in the game.
    private int numPlayers = 1;

    // A boolean deciding whether or not the game should proceed in color-blind
    // mode.
    private boolean colorblindMode = false;

    // An integer counter for the number of remaining unopened cards.
    private int unopenedCards = 52;

    // enums to represent the possible game state options.
    enum Game_State {
        INTRODUCTION, END_GAME, HELP, IN_GAME, POST_GAME, OPTIONS;
    }

    // Holds the current game state of the instance.
    private Game_State gameState = Game_State.INTRODUCTION;

    // Keeps track of the scores of the players.
    private int[] scores;

    public static final Scanner scanner = new Scanner(System.in);

    /**
     * Constructs an instance of a Memory game.
     *
     * @post returns an instance of the Memory game.
     */
    public Memory() {
    }

    /**
     * Overrides the toString() method to return a string representation of the
     * state, with uncovered cards' suits and values, and hides covered cards with a
     * [].
     *
     * @post returns a string corresponding to the suit of the card.
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        // Column Headers
        for (int i = 0; i < numCols; i++) {
            sb.append("\t").append((char) ('A' + i));
        }

        for (int i = 0; i < numRows; i++) {
            sb.append("\n" + (i + 1) + "\t"); // Row headers

            for (int j = 0; j < numCols; j++) {
                if (state[i][j] != null) {
                    if (state[i][j].getFlipped()) // Display card value if flipped.
                        sb.append(state[i][j]);
                    else
                        sb.append("[]");
                } else {
                    sb.append("X");
                }

                sb.append("\t");
            }
        }

        return sb.toString();
    }

    /**
     * Resets the state of the game - reset instance variables, shuffles cards, and
     * assigns them.
     */
    private void resetState() {
        // Reset variables
        unopenedCards = CARDS_PER_DECK * numDecks;
        numCols = numDecks * 4;
        currPlayerInGame = 0;
        currSelected = new int[] { -1, -1 };
        selectedCard = null;
        otherSelectedCard = null;
        state = new Card[numRows][numCols];
        scores = new int[numPlayers];

        // Shuffles the cards.
        ArrayList<Integer> cards = new ArrayList<Integer>();
        for (int i = 0; i < CARDS_PER_DECK * numDecks; i++) {
            cards.add(i);
        }
        Collections.shuffle(cards);

        // Card assignment
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                state[i][j] = new Card(cards.get(i * numCols + j));
            }
        }
    }

    /**
     * An input handler to read in the input and direct them to the separate
     * functions.
     * 
     * @param input - The input to be handled.
     * @pre input is a valid string.
     */
    private void inputHandler(String input) throws CustomException {
        String handledInput = input.toLowerCase();

        switch (handledInput) {
        case "q":
            gameState = Game_State.POST_GAME;
            break;
        default:
            try {
                parseInputs(handledInput);
            } catch (CustomException e) {
                throw e;
            }
        }
    }

    /**
     * Parses the input to obtain the card coordinates.
     * 
     * @param input - The input to be handled.
     * @pre input is a valid string.
     */
    public int[] parseCoordinate(String input) throws CustomException {
        int column = 0, row = 0;

        // This if-else statement allows the user to input either 'a1' or '1a'.
        // It does so by checking to find where the column is being declared through a
        // simple check for the letter. The remainder of the string is then parsed to an
        // integer.
        try {
            if (Character.isAlphabetic(input.charAt(0))) {
                row = Integer.parseInt(input.substring(1)) - 1;
                column = (int) (input.charAt(0) - 'a');

            } else if (Character.isAlphabetic(input.charAt(input.length() - 1))) {
                row = Integer.parseInt(input.substring(0, input.length() - 1)) - 1;
                column = (int) (input.charAt(input.length() - 1) - 'a');

            } else {
                // If character cannot be found at the start or at the end, throw exception.
                throw new CustomException(101);
            }

        } catch (Exception e) {
            throw new CustomException(101);
        }

        if ((0 > row) || (row >= numRows)) {
            throw new CustomException(201);
        }
        if ((0 > column) || (column >= numCols)) {
            throw new CustomException(202);
        }

        return new int[] { row, column };
    }

    /**
     * Parses the input to obtain the card the user wishes to open.
     * 
     * @param input - The input to be handled.
     * @pre input is a valid string.
     */
    private void parseInputs(String input) throws CustomException {
        // If there is a space, it is not a valid input - throw an error informing the
        // user.
        if (input.indexOf(" ") > 1) {
            throw new CustomException(102);
        }

        int[] cardCoordinates = parseCoordinate(input);

        // If the card is already open, throw an exception.
        if (state[cardCoordinates[0]][cardCoordinates[1]] == null) {
            throw new CustomException(203);
        }

        // If the card is already selected, throw an exception.
        if (cardCoordinates[0] == currSelected[0] && cardCoordinates[1] == currSelected[1]) {
            throw new CustomException(203);
        }

        // If there are no currently selected cards, get the coordinates of the selected
        // card and then the card itself.
        if (selectedCard == null) {
            currSelected = cardCoordinates;
            selectedCard = state[cardCoordinates[0]][cardCoordinates[1]];
            selectedCard.flipOver();
            System.out.println(this);
        } else {
            // Otherwise, get the selected card, print out the new state.
            otherSelectedCard = state[cardCoordinates[0]][cardCoordinates[1]];
            otherSelectedCard.flipOver();
            System.out.println(this);

            // Since two cards are selected we can compare to see if they match.
            if (selectedCard.doesMatchCard(otherSelectedCard, colorblindMode)) {
                System.out.println("Cards Match!");

                // Clears the two cards from the state
                state[currSelected[0]][currSelected[1]] = null;
                state[cardCoordinates[0]][cardCoordinates[1]] = null;

                // Increments the player's score
                scores[currPlayerInGame]++;

                // Updates the number of unopened cards.
                unopenedCards -= 2;
            } else {
                System.out.println("Cards do not Match!");

                // Covers the selected cards since they do not match.
                selectedCard.cover();
                otherSelectedCard.cover();

                // Updates the current player in game.
                currPlayerInGame = (currPlayerInGame + 1) % numPlayers;
            }

            currSelected = new int[] { -1, -1 };
            selectedCard = null;
            otherSelectedCard = null;
        }
    }

    /**
     * Clears the terminal screen.
     */
    private void clearScreen() {
        System.out.print("\033[H\033[2J");
    }

    /**
     * Prints the introduction to the game, and directs the user to the different
     * menus.
     */
    private void printIntroduction() {
        clearScreen();
        System.out.println("=========================================================\n"
                + "    M    M   EEEE    M    M    OOOO    RRRR    Y    Y    \n"
                + "    MM  MM   E       MM  MM   O    O   R   R   Y    Y    \n"
                + "    M MM M   EEEE    M MM M   O    O   RRRR     Y  Y     \n"
                + "    M    M   E       M    M   O    O   R  R       Y      \n"
                + "    M    M   EEEE    M    M    OOOO    R   R      Y      \n"
                + "=========================================================\n");
        System.out.println("Welcome to the game of Memory.\n");

        while (gameState == Game_State.INTRODUCTION) {
            System.out.println("\t1. [N]ew Game.");
            System.out.println("\t2. [H]elp Menu.");
            System.out.println("\t3. [O]ptions.");
            System.out.println("\n\t0. [Q]uit Game.\n");
            System.out.println("Enter your input:");

            String input = scanner.nextLine().trim().toLowerCase();
            switch (input) {
            case "n":
            case "1":
            case "1.":
                gameState = Game_State.IN_GAME;
                break;
            case "h":
            case "2":
            case "2.":
                gameState = Game_State.HELP;
                break;
            case "o":
            case "3":
            case "3.":
                gameState = Game_State.OPTIONS;
                break;
            case "q":
            case "0":
            case "0.":
                gameState = Game_State.END_GAME;
                break;
            default:
                System.out.println("Invalid input.");
                break;
            }
        }
    }

    /**
     * Prints the help to the game.
     */
    private void helpState() {
        clearScreen();
        ;
        System.out.println("Help");
        System.out.println("\n=========================================================\n");
        System.out.println("Memory is a game where players try to match covered cards."
                + "The game ends when all the cards are open, and the player with the most"
                + " pairs win the game. Matching is done between two cards of equal color "
                + "(diamonds and hearts or spades and clubs) and equal value.\n\n In the "
                + "colorblind mode, only the values are considered.");
        System.out.println("\nPress any key to go back to the main menu.");
        scanner.nextLine();
        gameState = Game_State.INTRODUCTION;
    }

    /**
     * Prints the winning player(s) based on the number of sets opened.
     * 
     * @post Prints the names of the winners.
     */
    private void printWinners() {
        int max = 0;
        ArrayList<Integer> winners = new ArrayList<Integer>();

        // Get the maximum score.
        for (int i = 0; i < numPlayers; i++) {
            if (scores[i] > max)
                max = scores[i];
        }

        // Gets the players who attained that score.
        for (int i = 0; i < numPlayers; i++) {
            if (scores[i] == max)
                winners.add(i);
        }

        StringBuilder sb = new StringBuilder();
        sb.append("Congratuations to:\n\n");
        for (int i = 0; i < winners.size(); i++) {
            sb.append("\t\tPlayer ").append(winners.get(i) + 1).append("\n");
        }
        sb.append("\nfor winning with ").append(max).append(" sets.\n\n");

        System.out.println(sb.toString());
    }

    /**
     * Handles the post-game state. Checks if the user wishes to play a new game,
     * exit, or return to the introduction.
     */
    private void postGameState() {
        clearScreen();

        while (gameState == Game_State.POST_GAME) {
            System.out.println("The game has ended!");
            printWinners();
            System.out.println("Do you want to play a new game (Y), exit (N), or return to the introduction (R)?");

            String input = scanner.nextLine().trim().toLowerCase();
            switch (input) {
            case "y":
                gameState = Game_State.IN_GAME;
                break;
            case "n":
                gameState = Game_State.END_GAME;
                break;
            case "r":
                gameState = Game_State.INTRODUCTION;
                break;
            default:
                clearScreen();
                System.out.println("Invalid input.\n");
            }
        }
    }

    /**
     * Handles the in-game state. Checks if the user wishes to play a new game,
     * exit, or return to the introduction.
     */
    private void inGameState() {
        resetState();
        clearScreen();
        System.out.println(this);

        while (gameState == Game_State.IN_GAME) {
            System.out.println("\nIt is Player " + (currPlayerInGame + 1) + "\'s turn.");
            System.out.println("Please enter the card you wish to open next (e.g. a1 or d13)");
            try {
                String input = scanner.nextLine();
                clearScreen();
                inputHandler(input);

                System.out.println("\nYou opened card " + input);

                if (unopenedCards == 0) {
                    gameState = Game_State.POST_GAME;
                }
            } catch (CustomException e) {
                System.out.println(e);
                System.out.println(this);
            }

        }
    }

    /**
     * Handles the options for the game. Prints all the options available to the
     * player and their current values, and allows the player to toggle them if
     * necessary, or return to the introduction.
     */
    private void optionsState() {
        clearScreen();

        while (gameState == Game_State.OPTIONS) {
            System.out.println("Options");
            System.out.println("\n=========================================================\n");
            System.out.println("1.\tColor-blind Mode:\t\t" + colorblindMode);
            System.out.println("2.\tNumber of Players:\t\t" + numPlayers);
            System.out.println("3.\tNumber of Decks (max 3):\t" + numDecks);

            System.out.println("\nEnter the option number that you wish to toggle, or enter b to go back.");
            String input = scanner.nextLine().trim().toLowerCase();
            switch (input) {
            case "b":
                gameState = Game_State.INTRODUCTION;
                break;
            case "1":
                colorblindMode = !colorblindMode;
                clearScreen();
                System.out.println("Successfully toggled color-blind mode\n");
                break;
            case "2":
                System.out.println("Input the new value for Number of players:");
                while (true) {
                    String newValue = scanner.nextLine().trim().toLowerCase();
                    if (newValue == "b") {
                        gameState = Game_State.INTRODUCTION;
                        break;
                    }
                    try {
                        int newNumPlayers = Integer.parseInt(newValue);
                        if (newNumPlayers > 0) {
                            numPlayers = newNumPlayers;
                            clearScreen();
                            break;
                        }
                    } catch (Exception e) {
                    }
                    System.out.println("Invalid new value.");
                    System.out.println("Please input the new value for number of players:");
                }
                break;
            case "3":
                System.out.println("Input the new value for Number of Decks (up to 3):");
                while (true) {
                    String newValue = scanner.nextLine().trim().toLowerCase();
                    try {
                        int newNumDecks = Integer.parseInt(newValue);
                        if (newNumDecks > 0 && newNumDecks <= 3) {
                            numDecks = newNumDecks;
                            clearScreen();
                            break;
                        }
                    } catch (Exception e) {

                    }
                    System.out.println("Invalid new value.");
                    System.out.println("Input the new value for Number of Decks (up to 3):");
                }

                break;
            default:
                clearScreen();
                System.out.println("Invalid input.\n");
                break;
            }
        }
    }

    /**
     * Runs the game instance, performining input and state management.
     */
    public void runGame() {
        while (gameState != Game_State.END_GAME) {
            switch (gameState) {
            // State 1: Help menu
            case HELP:
                // Print help.
                helpState();
                break;
            // State 2: In the game.
            case IN_GAME:
                inGameState();
                break;
            // State 3: Post game.
            case POST_GAME:
                postGameState();
                break;
            // State 4: Introducton: Prints introductory text with options
            case INTRODUCTION:
                printIntroduction();
                break;
            // State 5: Options.
            case OPTIONS:
                optionsState();
                break;
            default:
                System.out.println("Error detected, quitting game...");
            }
        }

        System.out.println("Thank you for playing! Have a great day!");

        scanner.close();
    }
}
