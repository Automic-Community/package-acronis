package com.broadcom;

import java.util.Objects;

import javax.json.JsonObject;

import org.apache.commons.lang3.StringUtils;

import com.broadcom.apdk.api.annotations.Action;
import com.broadcom.apdk.api.annotations.ActionInputParam;
import com.broadcom.constants.Constants;
import com.broadcom.exceptions.AcronisException;
import com.broadcom.helper.GetUserHelper;
import com.broadcom.util.CommonUtil;
import com.broadcom.util.ConsoleWriter;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

@Action(name = "DELETE_USER", title = "Delete User", path = "ACRONIS")
public class DeleteUserAction extends AbstractAcronisAction {

	@ActionInputParam(name = "UC4RB_AC_USER_ID", tooltip = "Provide the id of the user to be deleted. E.g. 88b24185-9b91-43d1-aa2c-b94665adcade8", required = true, label = "User Id")
	String userId;

	@ActionInputParam(name = "UC4RB_AC_USER_VERSION", tooltip = "Version should be same as the user that you want to delete. E.g. 1", label = "Current Version")
	Long currentVersion;

	@Override
	protected void executeSpecific() throws AcronisException {
		validateInputs();

		try {
			WebResource webResource = client.resource(url);
			webResource = webResource.path(Constants.API).path(version).path("users").path(userId)
					.queryParam(Constants.VERSION, String.valueOf(currentVersion));
			LOGGER.info("Calling url: " + webResource.getURI());
			ConsoleWriter.writeln("Calling url: " + webResource.getURI());
			webResource.delete(ClientResponse.class);
		} catch (Exception e) {
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

		if (Objects.isNull(currentVersion))
			getCurrentVersion();

	}

	/**
	 * Calls the get user API to get the current version only if user doesn't
	 * provide the current version.
	 * 
	 * @throws AcronisException
	 */
	private void getCurrentVersion() throws AcronisException {
		try {
			ClientResponse response = GetUserHelper.urlCall(userId, client, url,
					new String[] { Constants.API, version, "users" });
			JsonObject jsonObject = CommonUtil.jsonObjectResponse(response.getEntityInputStream());
			currentVersion = jsonObject.getJsonNumber(Constants.VERSION).longValue();
		} catch (Exception e) {
			String msg = String.format(Constants.REQ_ERROR_MESSAGE, url);
			LOGGER.warning(e.getMessage());
			throw new AcronisException(msg, e);
		}
	}

	@Override
	protected String getActionName() {
		return "Delete User";
	}
}
