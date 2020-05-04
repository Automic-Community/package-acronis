package com.broadcom;

import javax.json.JsonObject;
import javax.ws.rs.core.MediaType;

import com.broadcom.apdk.api.annotations.Action;
import com.broadcom.apdk.api.annotations.ActionInputParam;
import com.broadcom.apdk.api.annotations.ActionOutputParam;
import com.broadcom.constants.Constants;
import com.broadcom.exceptions.AcronisException;
import com.broadcom.util.CommonUtil;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

@Action(name = "GET_USER", title = "Get User", path = "ACRONIS")
public class GetUserAction extends AbstractAcronisAction {

	@ActionOutputParam(name = "UC4RB_AC_TENANT_ID")
	String tenantId;
	
	@ActionInputParam(required = true , name = "UC4RB_AC_USER_ID" ,  tooltip = "Provide the user id to fetch the details. E.g. d540ac7f-2e8b-4451-a1cc-18ee9586af69", label = "User Id")
	String userId;

	@Override
	protected void executeSpecific() throws AcronisException {
		ClientResponse response = null;
		try {
			WebResource webResource = client.resource(url);
			webResource = webResource.path("api").path(version).path("users").path(userId);
			LOGGER.info("Calling url: " + webResource.getURI());
			response = webResource.accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
		} catch (Exception e) {
			String msg = String.format(Constants.REQ_ERROR_MESSAGE, url, e.getMessage());
			throw new AcronisException(msg, e);
		}
		prepareOutput(CommonUtil.jsonObjectResponse(response.getEntityInputStream()));
	}

	private void prepareOutput(JsonObject jsonObjectResponse) {
		// write response to console
		System.out.println(CommonUtil.jsonPrettyPrinting(jsonObjectResponse));
		tenantId = jsonObjectResponse.getString("tenant_id");
	}

	@Override
	protected String getActionName() {
		return "Get User";
	}
}
