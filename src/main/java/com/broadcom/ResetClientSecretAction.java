package com.broadcom;

import javax.json.JsonObject;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.StringUtils;

import com.broadcom.apdk.api.annotations.Action;
import com.broadcom.apdk.api.annotations.ActionInputParam;
import com.broadcom.apdk.api.annotations.ActionOutputParam;
import com.broadcom.constants.Constants;
import com.broadcom.exceptions.AcronisException;
import com.broadcom.util.CommonUtil;
import com.broadcom.util.ConsoleWriter;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

/**
 * This action will reset the client secret.
 * 
 * @author ashishkumar09
 *
 */
@Action(name = "RESET_CLIENT_SECRET", title = "Reset Client Secret", path = "ACRONIS")
public class ResetClientSecretAction extends AbstractAcronisAction {

	/** client id. */
	@ActionInputParam(required = true, name = "UC4RB_AC_CLIENT_ID", tooltip = "Provide the client id to reset the secret. E.g. 91933cdf-7542-4457-92ed-39399d979208", label = "Client Id")
	String clientId;

	/** client secret. */
	@ActionOutputParam(name = "UC4RB_AC_CLIENT_SECRET")
	String clientSecret;

	@Override
	protected void executeSpecific() throws AcronisException {
		validateInputs();
		ClientResponse response = null;
		try {
			WebResource webResource = client.resource(url);
			webResource = webResource.path(Constants.API).path(version).path(Constants.CLIENTS).path(clientId).path(Constants.RESET_SECRET);
			LOGGER.info("Calling url: " + webResource.getURI());
			ConsoleWriter.writeln("Calling url: " + webResource.getURI());
			response = webResource.type(MediaType.APPLICATION_JSON).post(ClientResponse.class);
		} catch (Exception e) {
			String msg = String.format(Constants.REQ_ERROR_MESSAGE, url);
			LOGGER.warning(e.getMessage());
			throw new AcronisException(msg, e);
		}
		prepareOutput(CommonUtil.jsonObjectResponse(response.getEntityInputStream()));
	}

	private void prepareOutput(JsonObject jsonObjectResponse) {
		// write response to console
		ConsoleWriter.writeln(CommonUtil.jsonPrettyPrinting(jsonObjectResponse));
		clientSecret = jsonObjectResponse.getString("client_secret");
	}

	
	private void validateInputs() throws AcronisException {
		if (StringUtils.isEmpty(clientId)) {
			throw new AcronisException(String.format(Constants.ISEMPTY, "Client Id"));
		}
	}

	@Override
	protected String getActionName() {
		return "Reset Client Secret";
	}
}
