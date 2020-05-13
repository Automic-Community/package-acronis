package com.broadcom;

import org.apache.commons.lang3.StringUtils;

import com.broadcom.apdk.api.annotations.Action;
import com.broadcom.apdk.api.annotations.ActionInputParam;
import com.broadcom.constants.Constants;
import com.broadcom.exceptions.AcronisException;
import com.broadcom.helper.TokenHelper;
import com.broadcom.util.ConsoleWriter;
import com.sun.jersey.api.client.WebResource;

/**
 * This action will get the token.
 * 
 * @author vishalkumar03
 *
 */
@Action(name = "ACTIVATE_USER", title = "Activate User", path = "ACRONIS")
public class ActivateUserAction extends APIClientAuthAction {

	@ActionInputParam(label = "User Id", name = "UC4RB_AC_USER_ID", required = true, tooltip = "Provide the user id to get it activate E.g. 98431f36-6bcc-4ed5-8020-5ad0abd24a9f")
	String userId;

	@ActionInputParam(label = "User Password", name = "UC4RB_AC_USER_PWD", required = true, password = true, tooltip = "Provide the password for the user.")
	String newPassword;

	@Override
	protected void executeSpecific() throws AcronisException {
		validateInputs();
		try {
			String accessToken = getToken();
			WebResource webResource = client.resource(url);
			webResource.header("Authorization", "Bearer " + accessToken);
			webResource = webResource.path(Constants.API).path(version).path(Constants.USERS).path(userId)
					.path("password");
			LOGGER.info("Calling url: " + webResource.getURI());
			ConsoleWriter.writeln("Calling url: " + webResource.getURI());
		} catch (Exception e) {
			String msg = String.format(Constants.REQ_ERROR_MESSAGE, url);
			LOGGER.warning(e.getMessage());
			throw new AcronisException(msg, e);
		}
	}

	private String getToken() {
		WebResource webResource = client.resource(url);
		webResource = webResource.path(Constants.API).path(version).path(Constants.IDP).path(Constants.TOKEN);
		LOGGER.info("Calling url: " + webResource.getURI());
		ConsoleWriter.writeln("Calling url: " + webResource.getURI());
		return TokenHelper.getToken(webResource);
	}

	private void validateInputs() throws AcronisException {

		if (StringUtils.isEmpty(userId)) {
			String msg = String.format(Constants.ISEMPTY, "user Id");
			throw new AcronisException(msg);
		}

		if (StringUtils.isEmpty(newPassword)) {
			String msg = String.format(Constants.ISEMPTY, "New Password");
			throw new AcronisException(msg);
		}
	}

	@Override
	protected String getActionName() {
		return "Activate User";
	}
}