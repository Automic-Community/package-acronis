package com.broadcom.constants;

/**
 * Constant class containing messages to describe Exception Scenarios.
 * 
 */
public final class ExceptionConstants {

    // General Errors
    public static final String GENERIC_ERROR_MSG = "System Error [%s]";
    // Certificate errors
    public static final String INVALID_KEYSTORE = "Invalid KeyStore.";
    public static final String SSLCONTEXT_ERROR = "Unable to build secured context.";
    // I/O errors

    public static final String UNABLE_TO_WRITEFILE = "Error writing file ";
    public static final String UNABLE_TO_CLOSE_STREAM = "Error while closing stream";
    public static final String UNABLE_TO_FLUSH_STREAM = "Error while flushing stream";
    // CLI option errors
    public static final String INVALID_ARGS = "Improper Args. Possible cause : %s";
    // Validation errors
    public static final String UNABLE_TO_WRITE_FILE = "Unable to write into file [%s]";
    public static final String INVALID_ARCHIVE_TYPE = "Archive type [%s] not valid ";
    public static final String EMPTY_BUILD_NUMBER_STATUS = "Provide either a build id or build status";
    public static final String INVALID_JSON_FILE = "Invalid input for mapping";
    public static final String INVALID_INPUT_PARAMETER = "Invalid value for parameter [%s] : [%s]";
    public static final String INVALID_BUILD_NUMBER = "Invalid Build id [%s]";
    public static final String INVALID_USERNAME_PASSWORD = "Invalid UserName/Password : [%s]/[***]";

    private ExceptionConstants() {
    }

}
