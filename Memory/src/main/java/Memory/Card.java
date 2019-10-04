package Memory;

/**
 * This class is designed to represent playing cards. Cards have a Suit and a
 * corresponding value, and implement an overrided toString funciton to display
 * its value.
 */
public class Card {
    // Instance variables holding the Suit and value of the card
    private Suit suit;
    private int value;

    // Instance variable determining if the card is flipped.
    private boolean isFlipped;

    // Enum created to represent the suit of the card, chosen because
    // enums are best suited to represent nominal data.
    enum Suit {
        CLUBS, DIAMONDS, HEARTS, SPADES;

        /**
         * Overrides the toString() method to display a shortened string representation
         * of the suit.
         *
         * @post returns a string corresponding to the suit of the card.
         */
        public String toString() {
            switch (this) {
            case CLUBS:
                return "C";
            case DIAMONDS:
                return "\u001B[31mD\u001B[0m"; // Colored printing of the character
            case HEARTS:
                return "\u001B[31mH\u001B[0m";
            case SPADES:
                return "S";
            default:
                return "Error in converting suits";
            }
        }

        /**
         * Determines if the suit is of a 'red' color (hearts/ diamonds).
         *
         * @post returns a boolean determining if the suit is either HEARTS or DIAMONDS.
         */
        public boolean isRed() {
            if (this == CLUBS || this == SPADES)
                return false;
            else
                return true;
        }
    };

    /**
     * Constructor which constructs a card based on an integer input (0 - 51),
     * assigning the suit depending based on the closest multiple to 13, and the
     * value depending on the modulus of the value by 13.
     *
     * @param number - number, when modded 52, representing one of the 52 cards.
     * @post returns a string corresponding to the card.
     */
    public Card(int number) {
        // This step is taken in order to allow the game to be extended beyond a single
        // deck, since we can now pass in 'shuffled' numbers from say 0 to 103 for two
        // decks, and this will create the appropriate cards.
        int safeNumber = number % 52;

        Suit[] values = Suit.values();
        this.suit = values[safeNumber / 13];
        this.value = safeNumber % 13 + 1;
    }

    /**
     * Overrides the toString() method to display a shortened string representation
     * of the card in the form of (value) + (suit).
     *
     * @post returns a string corresponding to the card.
     */
    public String toString() {
        // If the value is between 2 - 10, no special conversion of the value is
        // required.
        if (value <= 10 && value > 1) {
            return value + "" + suit;
        }

        switch (value) {
        case 11:
            return "J" + suit;
        case 12:
            return "Q" + suit;
        case 13:
            return "K" + suit;
        case 1:
            return "A" + suit;
        default:
            return "Error";
        }
    }

    /**
     * Accessor that returns the value of the card.
     *
     * @post returns an int corresponding to the value of the card.
     */
    public int getValue() {
        return value;
    }

    /**
     * Accessor that returns a boolean representing if the card is flipped.
     *
     * @post returns a boolean representing if the card is flipped.
     */
    public boolean getFlipped() {
        return isFlipped;
    }

    /**
     * Mutator that covers a card.
     * 
     * @post Card is not flipped.
     */
    public void cover() {
        this.isFlipped = false;
    }

    /**
     * Mutator that flips a card.
     * 
     * @post Card is flipped.
     */
    public void flipOver() {
        this.isFlipped = true;
    }

    /**
     * Accessor that returns the suit of the card.
     *
     * @post returns a Suit corresponding to the suit of the card.
     */
    public Suit getSuit() {
        return suit;
    }

    /**
     * Compares this card with another card to check if the colors of their suits
     * match.
     *
     * @param otherCard - a Card to be checked against.
     * @pre otherCard is a valid Card
     * @post returns a boolean corresponding to whether the two cards have the same
     *       color.
     */
    public boolean colorsMatch(Card otherCard) {
        if (otherCard == null)
            return false;

        if (this.suit.isRed() == otherCard.suit.isRed()) {
            return true;
        }

        return false;
    }

    /**
     * Checks if this card can form a match with otherCard.
     *
     * @param otherCard    - a Card to be checked against.
     * @param isColorBlind - performs color blind matching (i.e. matching only the
     *                     values).
     * @pre otherCard is a valid Card
     * @post returns a boolean corresponding to whether the two cards can be
     *       considered to match.
     */
    public boolean doesMatchCard(Card otherCard, boolean isColorBlind) {
        if (otherCard == null)
            return false;

        if (this.value == otherCard.getValue()) {
            if (!isColorBlind) {
                return this.colorsMatch(otherCard);
            }
            return true;
        }

        return false;
    }
}