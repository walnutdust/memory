import java.lang.Exception;
import java.util.Map;
import java.util.stream.*;

/**
 * This class is designed to represent a custom Exception field that is
 * extensible.
 */
public class CustomException extends Exception {
    private static final long serialVersionUID = 1L;
    // Map containing conversion between error codes to error messages
    static Map<Integer, String> errorMessages = Stream.of(new Object[][] {
            // 10x - Input errors
            { 101, "Unable to parse input." }, { 102, "String has too many spaces." },

            // 20x - Invalid element/indices
            { 201, "Invalid Row number." }, { 202, "Invalid Column number." }, { 203, "Card is already open." }, })
            .collect(Collectors.toMap(p -> (Integer) p[0], p -> (String) p[1]));

    // Holds the error code of the error.
    private int errorCode;

    /**
     * Constructor of CustomException that takes in an error code.
     * 
     * @param errorCode - int representing the error code.
     * @post - creates a CustomException with the error message that corresponds to
     *       the code.
     */
    public CustomException(int errorCode) {
        super(errorMessages.get(errorCode));
        this.errorCode = errorCode;
    }

    /**
     * Overrides the toString() method.
     *
     * @post returns a string with the error message.
     */
    public String toString() {
        return "Error: " + this.getMessage() + "\nError Code:" + this.errorCode;
    }
}