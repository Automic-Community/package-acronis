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


@Action(name = "UPDATE_USER_PASSWORD", title = "Update User Password", path = "ACRONIS")
@PromptSet(name = "UPDATE_USER_PASSWORD", title = "Update User Password")
public class UpdateUserPasswordAction extends APIClientAuthAction {

	@ActionInputParam(label = "User Id", name = "UC4RB_AC_USER_ID", required = true, tooltip = "Provide the user id. E.g. 98431f36-6bcc-4ed5-8020-5ad0abd24a9f")
	String userId;

	@ActionInputParam(label = "User Password", name = "UC4RB_AC_USER_PWD", required = true, password = true, tooltip = "Provide the new password you want to update.")
	String newPassword;

	@Override
	protected void executeSpecific() throws AcronisException {
		validateInputs();
		Map<String, Object> request = createRequest();
		try {
			String accessToken = getToken();
			WebResource webResource = client.resource(url);
			webResource.header("Authorization", "Bearer " + accessToken);
			webResource = webResource.path(Constants.API).path(version).path(Constants.USERS).path(userId)
					.path("password");
			Builder builder = webResource.header("Authorization", "Bearer " + accessToken);
			LOGGER.info("Calling url: " + webResource.getURI());
			ConsoleWriter.writeln("Calling url: " + webResource.getURI());
			builder.type(MediaType.APPLICATION_JSON_TYPE).post(ClientResponse.class, request);
		} catch (Exception e) {
			String msg = String.format(Constants.REQ_ERROR_MESSAGE, url);
			LOGGER.warning(e.getMessage());
			throw new AcronisException(msg, e);
		}
	}

	private Map<String, Object> createRequest() {
		Map<String, Object> request = new HashMap<>();
		request.put("password", newPassword);
		return request;
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
		return "Update User Password";
	}
}