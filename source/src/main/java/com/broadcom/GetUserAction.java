package com.broadcom;

import javax.json.JsonObject;

import org.apache.commons.lang3.StringUtils;

import com.broadcom.apdk.api.annotations.Action;
import com.broadcom.apdk.api.annotations.ActionInputParam;
import com.broadcom.apdk.api.annotations.ActionOutputParam;
import com.broadcom.apdk.api.annotations.PromptSet;
import com.broadcom.constants.Constants;
import com.broadcom.exceptions.AcronisException;
import com.broadcom.helper.GetHelper;
import com.broadcom.util.CommonUtil;
import com.broadcom.util.ConsoleWriter;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

@Action(name = "GET_USER", title = "Get User", path = "ACRONIS")
@PromptSet(name = "GET_USER", title = "Get User")
public class GetUserAction extends AbstractAcronisAction {

	@ActionInputParam(required = true, name = "UC4RB_AC_USER_ID", tooltip = "Provide the user id to fetch the details. E.g. d540ac7f-2e8b-4451-a1cc-18ee9586af69", label = "User Id")
	String userId;

	@ActionOutputParam(name = "UC4RB_AC_TENANT_ID")
	String tenantId;

	@ActionOutputParam(name = "UC4RB_AC_USER_VERSION")
	Integer userVersion;

	@ActionOutputParam(name = "UC4RB_AC_ACTIVATED")
	Boolean activated;

	@ActionOutputParam(name = "UC4RB_AC_ENABLED")
	Boolean enabled;

	@Override
	protected void executeSpecific() throws AcronisException {
		ClientResponse response = null;
		try {
			validateInputs();
			WebResource webResource = client.resource(url);
			webResource = webResource.path(Constants.API).path(version).path(Constants.USERS).path(userId);
			LOGGER.info("Calling url: " + webResource.getURI());
			ConsoleWriter.writeln("Calling url: " + webResource.getURI());
			response = GetHelper.urlCall(webResource);
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
		tenantId = jsonObjectResponse.getString("tenant_id");
		userVersion = jsonObjectResponse.getInt("version");
		activated = jsonObjectResponse.getBoolean("activated");
		enabled = jsonObjectResponse.getBoolean("enabled");
	}

	private void validateInputs() throws AcronisException {
		if (StringUtils.isEmpty(userId)) {
			throw new AcronisException(String.format(Constants.ISEMPTY, "User Id"));
		}
	}

	@Override
	protected String getActionName() {
		return "Get User";
	}
	
	public GetUserAction() {
		setDocumentation("= Action name =\r\n" + 
				"PCK.AUTOMIC_ACRONIS.PUB.ACTION.GET_USER\r\n" + 
				"\r\n" + 
				"= General description =\r\n" + 
				"This action get the user by userId.\r\n" + 
				"\r\n" + 
				"= Inputs =\r\n" + 
				"* User Id*	             : Provide the user id to get the details. E.g. 2720cf58-d084-4b22-a284-4a4564fe1e4d\r\n" + 
				"\r\n" + 
				"= Failure Conditions =\r\n" + 
				"If the user does not exists with the input User Id. \r\n" + 
				"\r\n" +
				"= Return value =\r\n" + 
				" UC4RB_AC_TENANT_ID#      : Id of the newly created tenant.\r\n" + 
				" UC4RB_AC_USER_VERSION#   : Version of the newly created tenant.\r\n" + 
				" UC4RB_AC_ACTIVATED#	   : A variable containing the user status.\r\n" + 
				" UC4RB_AC_ENABLED#	       : A variable containing the user status..\r\n" + 
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
