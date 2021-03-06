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
	public static final String PRICING = "pricing";
	public static final String TRUE_FALSE = "The value for %s should be true or false";
	public static final String CLIENTS = "clients";
	public static final String TENANT_VERSION_MISMATCH = "Tenant version does not match";
	public static final String TOKEN = "token";
	public static final String IDP = "idp";

	/** EMAIL_VALIATION_REGEX used to validate the email. */
	public static final String EMAIL_VALIATION_REGEX = "^(.+)@(.+)$";
	public static final String ACCESS_POLICIES = "access_policies";



	public enum Kind {
		@EnumValue("Customer")
		CUSTOMER, @EnumValue("Partner")
		PARTNER, @EnumValue("Folder")
		FOLDER, @EnumValue("Unit")
		UNIT;

		@Override
		public String toString() {
			return name().toLowerCase();
		}
	}

    public enum Role {
        @EnumValue("PARTNER_ADMIN") partner_admin,
        @EnumValue("HCI_ADMIN") hci_admin,
        @EnumValue("COMPANY_ADMIN") company_admin,
        @EnumValue("UNIT_ADMIN") unit_admin,
        @EnumValue("BACKUP_USER") backup_user,
        @EnumValue("SYNC_SHARE_ADMIN") sync_share_admin,
        @EnumValue("SYNC_SHARE_USER") sync_share_user,
        @EnumValue("NOTARY_ADMIN") notary_admin,
        @EnumValue("NOTARY_USER") notary_user;

    }

    private Constants() {
    }

}