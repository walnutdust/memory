package Memory;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Unit test for the Card Class.
 */
public class CardTest {
    /**
     * Test that card values are what we expected.
     */
    @Test
    public void testCardValues() {
        Card c1 = new Card(0); // Ace of CLUBS
        Card c2 = new Card(42); // 4 of SPADES
        Card c3 = new Card(489); // 9 of DIAMONDS

        assertEquals(1, c1.getValue());
        assertEquals(4, c2.getValue());
        assertEquals(9, c3.getValue());
    }

    /**
     * Test that card suits are wgat we expected.
     */
    @Test
    public void testCardSuits() {
        Card c1 = new Card(0); // Ace of CLUBS
        Card c2 = new Card(42); // 4 of SPADES
        Card c3 = new Card(489); // 9 of DIAMONDS

        assertEquals(Card.Suit.CLUBS, c1.getSuit());
        assertEquals(Card.Suit.SPADES, c2.getSuit());
        assertEquals(Card.Suit.DIAMONDS, c3.getSuit());
    }

    /**
     * Test that card suits colors are expected
     */
    @Test
    public void testCardSuitColors() {
        Card c1 = new Card(0); // Ace of CLUBS
        Card c2 = new Card(42); // 4 of SPADES
        Card c3 = new Card(489); // 9 of DIAMONDS

        assertFalse(c1.getSuit().isRed());
        assertFalse(c2.getSuit().isRed());
        assertTrue(c3.getSuit().isRed());
    }

    /**
     * Test the string representation of the card.
     */
    @Test
    public void testCardToString() {
        Card c1 = new Card(0); // Ace of CLUBS
        Card c2 = new Card(42); // 4 of SPADES
        Card c3 = new Card(489); // 9 of DIAMONDS

        assertEquals("AC", c1.toString());
        assertEquals("4S", c2.toString());
        assertEquals("9\u001B[31mD\u001B[0m", c3.toString());
    }

    /**
     * Test the card flipping.
     */
    @Test
    public void testCardFlipping() {
        Card c1 = new Card(0); // Ace of CLUBS
        assertFalse(c1.getFlipped());

        // Nothing should happen since it was covered to begin with
        c1.cover();
        assertFalse(c1.getFlipped());

        // Card should now be flipped
        c1.flipOver();
        assertTrue(c1.getFlipped());
    }

    /**
     * Test if two cards match in non-color-blind mode.
     */
    @Test
    public void testCardMatchingWithColor() {
        Card c1 = new Card(0); // Ace of CLUBS
        Card c2 = new Card(39); // Ace of SPADES
        Card c3 = new Card(481); // Ace of DIAMONDS

        assertTrue(c1.doesMatchCard(c2, false));
        assertFalse(c1.doesMatchCard(c3, false));
    }

    /**
     * Test if two cards match in color-blind mode.
     */
    @Test
    public void testCardMatchingWithoutColor() {
        Card c1 = new Card(0); // Ace of CLUBS
        Card c2 = new Card(39); // Ace of SPADES
        Card c3 = new Card(481); // Ace of DIAMONDS

        assertTrue(c1.doesMatchCard(c2, true));
        assertTrue(c1.doesMatchCard(c3, true));
    }
}
