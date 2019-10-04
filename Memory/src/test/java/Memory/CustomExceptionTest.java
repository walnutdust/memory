package Memory;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Unit test for the CustomException Class.
 */
public class CustomExceptionTest {
    /**
     * Test CustomExceptions error codes are initialized properly.
     */
    @Test
    public void testCustomExceptionsErrorCode() {
        CustomException ce1 = new CustomException(101);
        CustomException ce2 = new CustomException(202);
        CustomException ce3 = new CustomException(506);

        assertEquals(101, ce1.getErrorCode());
        assertEquals(202, ce2.getErrorCode());
        assertEquals(506, ce3.getErrorCode());
    }

    /**
     * Test CustomExceptions error messages are initialized properly.
     */
    @Test
    public void testCustomExceptionsMessage() {
        CustomException ce1 = new CustomException(101);
        CustomException ce2 = new CustomException(202);
        CustomException ce3 = new CustomException(506);

        assertEquals("Unable to parse input.", ce1.getMessage());
        assertEquals("Invalid Column number.", ce2.getMessage());
        assertEquals("", ce3.getMessage());
    }
}
