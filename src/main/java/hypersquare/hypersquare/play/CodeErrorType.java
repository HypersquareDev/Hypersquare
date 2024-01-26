package hypersquare.hypersquare.play;

public enum CodeErrorType {
    INVALID_ACT("Invalid action", true),                              // Codeblock/Codeline data is invalid
    INVALID_EVENT("Invalid event", true),                             // Triggered event that doesn't exist
    INVALID_ARG("Invalid argument", true),
    MISSING_PARAM("Missing parameter", true),                         // Required parameter not specified
    INVALID_PARAM("Invalid parameter", true),                         // Valid type but invalid input
    CODEBLOCK_THROWN("Error thrown while executing an action", true), // Error while executing the codeblock itself
    INTERNAL_ERROR("Error thrown while executing code", true),        // Error while going through actions and triggering them
    LOW_MSPT("Low world MSPT while executing code", false),           // World is lagging

    ;

    public final String message;
    public final boolean sendLoc;
    CodeErrorType(String message, boolean sendLoc) {
        this.message = message;
        this.sendLoc = sendLoc;
    }
}
