package com.broadcom;

import java.util.HashMap;
import java.util.Map;

import javax.json.JsonObject;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.StringUtils;

import com.broadcom.apdk.api.annotations.Action;
import com.broadcom.apdk.api.annotations.ActionInputParam;
import com.broadcom.apdk.api.annotations.ActionOutputParam;
import com.broadcom.apdk.api.annotations.PromptSet;
import com.broadcom.constants.Constants;
import com.broadcom.exceptions.AcronisException;
import com.broadcom.util.CommonUtil;
import com.broadcom.util.ConsoleWriter;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

@Action(name = "CREATE_CLIENT", title = "Create Client", path = "ACRONIS")
@PromptSet(name = "CREATE_CLIENT", title = "Create Client")
public class CreateClientAction extends AbstractAcronisAction {

	@ActionInputParam(label = "Client Name", name = "UC4RB_AC_CLIENT_NAME", tooltip = "Provide the name of the client to be created. E.g. API.Client")
	String clientName;

	@ActionInputParam(label = "Parent Id", name = "UC4RB_AC_PARENT_ID", required = true, tooltip = "Provide the parent id of the client to be created. E.g. 2720cf58-d084-4b22-a284-4a4564fe1e4d")
	String parentId;

	@ActionOutputParam(name = "UC4RB_AC_CLIENT_ID")
	String clientId;

	@ActionOutputParam(name = "UC4RB_AC_CLIENT_SECRET", password = true)
	String clientSecret;

	@Override
	protected void executeSpecific() throws AcronisException {
		validateInputs();
		Map<String, Object> request = createRequest();
		ClientResponse response = null;
		try {
			WebResource webResource = client.resource(url);
			webResource = webResource.path(Constants.API).path(version).path(Constants.CLIENTS).queryParam("grant_type",
					"password");
			LOGGER.info("Calling url: " + webResource.getURI());
			ConsoleWriter.writeln("Calling url: " + webResource.getURI());
			ConsoleWriter.writeln("Request Body: " + request.toString());
			response = webResource.type(MediaType.APPLICATION_JSON).post(ClientResponse.class, request);
		} catch (Exception e) {
			String msg = String.format(Constants.REQ_ERROR_MESSAGE, url);
			LOGGER.warning(e.getMessage());
			throw new AcronisException(msg, e);
		}
		prepareOutput(CommonUtil.jsonObjectResponse(response.getEntityInputStream()));
	}

	/**
	 * Creates the request
	 * 
	 * @return map with string keys and object type values
	 */
	private Map<String, Object> createRequest() {
		Map<String, Object> request = new HashMap<>();
		request.put("type", "agent");
		request.put("tenant_id", parentId);
		request.put("token_endpoint_auth_method", "client_secret_basic");

		Map<String, Object> data = new HashMap<>();
		if (StringUtils.isNotEmpty(clientName)) {
			data.put("client_name", clientName);
		} else {
			data.put("client_name", "API.Client");
		}
		request.put("data", data);

		return request;
	}

	private void prepareOutput(JsonObject jsonObjectResponse) {
		// write response to console
		ConsoleWriter.writeln("Response: " + CommonUtil.jsonPrettyPrinting(jsonObjectResponse));
		clientId = jsonObjectResponse.getString("client_id");
		clientSecret = jsonObjectResponse.getString("client_secret");
	}

	private void validateInputs() throws AcronisException {
		if (StringUtils.isEmpty(parentId)) {
			throw new AcronisException(String.format(Constants.ISEMPTY, "Parent Id"));
		}
	}

	@Override
	protected String getActionName() {
		return "Create Client";
	}
	
	public CreateClientAction() {
		setDocumentation("= Action name =\r\n" + 
				"PCK.AUTOMIC_ACRONIS.PUB.ACTION.CREATE_CLIENT\r\n" + 
				"\r\n" + 
				"= General description =\r\n" + 
				"This action creates a new client.\r\n" + 
				"\r\n" + 
				"= Inputs =\r\n" + 
				"* Tenant Id*	            : Provide the parent id of the client to be created. E.g. 2720cf58-d084-4b22-a284-4a4564fe1e4d\r\n" + 
				"* Client Name*              : Provide the name of the client to be created. E.g. API.Client	\r\n" + 
				"\r\n" + 
				"= Failure Conditions =\r\n" + 
				"(none)\r\n" + 
				"\r\n" + 
				"= Behaviour =\r\n" + 
				"(none)\r\n" + 
				"\r\n" + 
				"= Return value =\r\n" + 
				"* UC4RB_AC_CLIENT_ID#       : A variable containing the client id.\r\n" + 
				"* UC4RB_AC_CLIENT_SECRET#	: A variable containing the client secret.\r\n" + 
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