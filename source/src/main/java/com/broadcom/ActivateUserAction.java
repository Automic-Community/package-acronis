package com.broadcom;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.StringUtils;

import com.broadcom.apdk.api.annotations.Action;
import com.broadcom.apdk.api.annotations.ActionInputParam;
import com.broadcom.apdk.api.annotations.PromptSet;
import com.broadcom.constants.Constants;
import com.broadcom.exceptions.AcronisException;
import com.broadcom.helper.TokenHelper;
import com.broadcom.util.ConsoleWriter;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.WebResource.Builder;

/**
 * This action will get the token.
 * 
 * @author vishalkumar03
 *
 */
@Action(name = "ACTIVATE_USER", title = "Activate User", path = "ACRONIS")
@PromptSet(name = "ACTIVATE_USER", title = "Activate User")
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
			webResource = webResource.path(Constants.API).path(version).path(Constants.USERS).path(userId)
					.path("password");
			Builder builder = webResource.header("Authorization", "Bearer " + accessToken);
			LOGGER.info("Calling url: " + webResource.getURI());
			ConsoleWriter.writeln("Calling url: " + webResource.getURI());
			builder.type(MediaType.APPLICATION_JSON_TYPE).post(ClientResponse.class, createRequest());
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

	private Map<String, String> createRequest() {
		Map<String, String> request = new HashMap<>(1);
		request.put("password", newPassword);
		return request;
	}

	@Override
	protected String getActionName() {
		return "Activate User";
	}
	
	public ActivateUserAction() {
		setDocumentation("= Action name =\r\n" + 
				"PCK.AUTOMIC_ACRONIS.PUB.ACTION.ACTIVATE_USER\r\n" + 
				"\r\n" + 
				"= General description =\r\n" + 
				"This action activates an acronis user.\r\n" + 
				"\r\n" + 
				"= Inputs =\r\n" + 
				"* User Id*	                  : Provide the user id that you want to activate. E.g. 9b81bf31-0c04-4f6e-8ebe-56900a3f9e76                          \r\n" + 
				"* User Password*              : Provide the password for the user. 	\r\n" + 
				"\r\n" + 
				"= Failure Conditions =\r\n" + 
				"(none)\r\n" + 
				"\r\n" + 
				"= Behaviour =\r\n" + 
				"(none)\r\n" + 
				"\r\n" + 
				"= Return value =\r\n" + 
				"(none)\r\n" + 
				"\r\n" + 
				"= Outputs =\r\n" + 
				"* Return code is 0 in case of success.\r\n" + 
				"* Return code is non-zero in case of failure.\r\n" + 
				"\r\n" + 
				"= Rollback =\r\n" + 
				"(none)\r\n" + 
				"\r\n" + 
				"= Logging =\r\n" + 
				"AE logs will be displayed in the AE report.");
	}
}