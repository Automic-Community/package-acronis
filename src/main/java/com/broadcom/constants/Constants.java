package com.broadcom.constants;

import com.broadcom.apdk.api.annotations.EnumValue;

/**
 * Class contains all the constants used in Automation engine java application.
 */
public final class Constants {
    /**
     * int constant for IO Buffer used to buffer the data.
     */
    public static final String HTTPS = "https";
    public static final String ISEMPTY = "The %s can't be empty or null";
    public static final String INVALID_INPUT_PARAMETER = "Invalid value for parameter [%s] : [%s]";
    public static final String REQUEST_BODY_ERROR = "Exception occured in preparation of request body : %s";
    public static final String REQ_ERROR_MESSAGE = "Exception occured while making request : [%s]";
    public static final String RES_ERROR_MESSAGE = "Exception occured while parsing the response : %s";
    public static final String ERROR_SKIPPING_CERT = "Exception occured while parsing the response : %s";
    public static final String ERROR_MESSAGE = null;
    public static final String ACTION_CATEGORY = "Ansible Tower";
    public static final String ACRONIS_VERSION = "2";
    public static final String UNABLE_TO_WRITE = "Unable to write at the given file path [%s]";
    public static final String IGNORE_HTTPERROR = "IGNORE-HTTPERROR";
    public static final String API = "api";
    public static final String TENANTS = "tenants";
    public static final String VERSION = "version";
    public static final String USERS = "users";
    public static final String TENANT_ID = "tenant_id";
    public static final String USER_ID = "id";
    public static final String LOGIN = "login";
    public static final String EMAIL = "email";
    public static final String FIRST_NAME = "firstname";
    public static final String LAST_NAME = "lastname";
    public static final String CONTACT = "contact";
    public static final String BLANK_INPUT_PARAMETER_ERROR_MESSAGE = "All input parameter [%s, %s, %s] cannot left "
            + "blank.";

    /** EMAIL_VALIATION_REGEX used to validate the email. */
    public static final String EMAIL_VALIATION_REGEX = "^(.+)@(.+)$";

    public enum Kind {
        @EnumValue("Customer") CUSTOMER,
        @EnumValue("Partner") PARTNER,
        @EnumValue("Folder") FOLDER,
        @EnumValue("Unit") UNIT;

        @Override
        public String toString() {
            return name().toLowerCase();
        }
    }

    private Constants() {
    }

}