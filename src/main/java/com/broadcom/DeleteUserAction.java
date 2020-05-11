package com.broadcom;

import javax.json.JsonObject;

import org.apache.commons.lang3.StringUtils;

import com.broadcom.apdk.api.annotations.Action;
import com.broadcom.apdk.api.annotations.ActionInputParam;
import com.broadcom.constants.Constants;
import com.broadcom.exceptions.AcronisException;
import com.broadcom.exceptions.NotFoundRuntimeException;
import com.broadcom.helper.DisableHelper;
import com.broadcom.helper.GetHelper;
import com.broadcom.util.CommonUtil;
import com.broadcom.util.ConsoleWriter;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

@Action(name = "DELETE_USER", title = "Delete User", path = "ACRONIS")
public class DeleteUserAction extends AbstractAcronisAction {

	@ActionInputParam(name = "UC4RB_AC_USER_ID", tooltip = "Provide the id of the user to be deleted. E.g. 88b24185-9b91-43d1-aa2c-b94665adcade8", required = true, label = "User Id")
	String userId;

	@ActionInputParam(name = "UC4RB_AC_FAIL", tooltip = "Specifies if the Job should fail in case of non-existing user E.g. true/false", required = true, label = "Fail if non-existing")
	Boolean failIfNotExist = true;

	boolean isEnabled;

	@Override
	protected void executeSpecific() throws AcronisException {
		validateInputs();
		try {
			WebResource webResource = client.resource(url);
			webResource = webResource.path(Constants.API).path(version).path(Constants.USERS).path(userId);
			Long currentVersion = getCurrentVersion(webResource);
			if (isEnabled) {
				currentVersion = disableUser(webResource, currentVersion);
			}

			webResource = webResource.queryParam(Constants.VERSION, String.valueOf(currentVersion));
			LOGGER.info("Calling url: " + webResource.getURI());
			ConsoleWriter.writeln("Calling url: " + webResource.getURI());
			webResource.delete(ClientResponse.class);
		} catch (Exception e) {
			if (e instanceof NotFoundRuntimeException && !failIfNotExist) {
				return;
			}
			String msg = String.format(Constants.REQ_ERROR_MESSAGE, url);
			LOGGER.warning(e.getMessage());
			throw new AcronisException(msg, e);
		}
	}

	/**
	 * Validates the input provided by the user.
	 * 
	 * @throws AcronisException
	 */
	private void validateInputs() throws AcronisException {
		if (StringUtils.isEmpty(userId))
			throw new AcronisException(String.format(Constants.ISEMPTY, "User Id"));
	}

	/**
	 * Calls the get user API to get the current version
	 * 
	 * @param webResource
	 * 
	 * @return version
	 */
	private Long getCurrentVersion(WebResource webResource) {
		LOGGER.info("Calling url: " + webResource.getURI());
		ClientResponse clientResponse = GetHelper.urlCall(webResource);
		JsonObject jsonResponseObject = CommonUtil.jsonObjectResponse(clientResponse.getEntityInputStream());
		isEnabled = jsonResponseObject.getBoolean("enabled");
		return jsonResponseObject.getJsonNumber(Constants.VERSION).longValue();
	}

	/**
	 * Disable the user and update the current version value.
	 * 
	 * @return updated version
	 */
	private Long disableUser(WebResource webResource, Long currentVersion) {
		LOGGER.info("Calling url: " + webResource.getURI());
		ClientResponse clientResponse = DisableHelper.urlCall(currentVersion, webResource);
		JsonObject jsonResponseObject = CommonUtil.jsonObjectResponse(clientResponse.getEntityInputStream());
		return jsonResponseObject.getJsonNumber(Constants.VERSION).longValue();
	}

	@Override
	protected String getActionName() {
		return "Delete User";
	}
}
