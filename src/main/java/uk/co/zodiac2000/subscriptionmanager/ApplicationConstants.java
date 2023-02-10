package uk.co.zodiac2000.subscriptionmanager;

/**
 * Class that defines static constants that are used throughout the application.
 */
public final class ApplicationConstants {

    private ApplicationConstants() { }

    /**
     * Defines the maximum safe length of a string representation of a long. Used for validation of command DTOs
     * where a string representation of a long (typically an id) is used. A long can hold positive integers smaller
     * than ~ 9,223,372,036,850,000,000 which is 19 digits, but to ensure the value can be converted to a long only
     * values less than 1,000,000,000,000,000,000 are accepted. This should be more than sufficient for representing
     * ids over the lifetime of the application.
     */
    public static final int MAX_LONG_DIGITS = 18;
}
