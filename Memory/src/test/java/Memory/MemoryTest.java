package Memory;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Unit test for the Memory Class.
 */
public class MemoryTest {
    /**
     * Test that input coordiantes are parsed properly.
     */
    @Test
    public void testParseCoordinates() {
        Memory m = new Memory();

        try {
            assertArrayEquals(new int[] { 0, 0 }, m.parseCoordinate("a1"));
            assertArrayEquals(new int[] { 12, 3 }, m.parseCoordinate("13d"));
        } catch (Exception e) {
            // Reports false if an exception occurs
            assertTrue(false);
        }

        // Raise exception if input is out of bounds.
        try {
            assertArrayEquals(new int[] { 0, 0 }, m.parseCoordinate("a11823"));
        } catch (CustomException e) {
            // Reports false if an exception occurs
            assertTrue(true);
            assertEquals(201, e.getErrorCode());
        }
    }

}
