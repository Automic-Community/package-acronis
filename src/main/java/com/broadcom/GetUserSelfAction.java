package com.broadcom;

import javax.json.JsonObject;

import com.broadcom.apdk.api.annotations.Action;
import com.broadcom.apdk.api.annotations.ActionOutputParam;
import com.broadcom.apdk.api.annotations.PromptSet;
import com.broadcom.constants.Constants;
import com.broadcom.exceptions.AcronisException;
import com.broadcom.helper.GetHelper;
import com.broadcom.util.CommonUtil;
import com.broadcom.util.ConsoleWriter;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

@Action(name = "GET_USER_SELF", title = "Get User Self", path = "ACRONIS")
@PromptSet(name = "GET_USER_SELF", title = "Get User Self")
public class GetUserSelfAction extends AbstractAcronisAction {

	@ActionOutputParam(name = "UC4RB_AC_TENANT_ID")
	String tenantId;

	@Override
	protected void executeSpecific() throws AcronisException {
		ClientResponse response = null;
		try {
			WebResource webResource = client.resource(url);
			webResource = webResource.path(Constants.API).path(version).path(Constants.USERS).path("me");
			LOGGER.info("Calling url: " + webResource.getURI());
			ConsoleWriter.writeln("Calling url: " + webResource.getURI());
			response = GetHelper.urlCall(webResource);
		} catch (Exception e) {
			String msg = String.format(Constants.REQ_ERROR_MESSAGE, url);
			throw new AcronisException(msg, e);
		}
		prepareOutput(CommonUtil.jsonObjectResponse(response.getEntityInputStream()));
	}

	private void prepareOutput(JsonObject jsonObjectResponse) {
		// write response to console
		ConsoleWriter.writeln("Response: " + CommonUtil.jsonPrettyPrinting(jsonObjectResponse));
		tenantId = jsonObjectResponse.getString("tenant_id");
	}

	@Override
	protected String getActionName() {
		return "Get User Self";
	}
}
