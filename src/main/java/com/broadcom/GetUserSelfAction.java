package com.broadcom;

import javax.ws.rs.core.MediaType;

import com.broadcom.apdk.api.annotations.Action;
import com.broadcom.apdk.api.annotations.ActionOutputParam;
import com.broadcom.constants.Constants;
import com.broadcom.exceptions.AcronisException;
import com.broadcom.util.CommonUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

@Action(name = "GET_USER_SELF", title = "Get User Self", path = "ACRONIS")
public class GetUserSelfAction extends AbstractAcronisAction {
	
	
	@ActionOutputParam(name = "UC4RB_AC_TENANT_ID")
	String tenantID;

	@Override
	public void run() {
		super.execute();
	}

	@Override
	protected void executeSpecific() throws AcronisException {
		validateInputs();
		ClientResponse response = null;
		try {
			WebResource webResource = client.resource(url);
			webResource = webResource.path("api").path(version).path("users").path("me");
			LOGGER.info("Calling url " + webResource.getURI());
			response = webResource.accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
		} catch (Exception e) {
			String msg = String.format(Constants.REQ_ERROR_MESSAGE, url, e.getMessage());
		//	LOGGER.error(msg, e);
			// throw new AcronisException(msg, e);
		}
		processResponse(response);
		// return new ActionResult(true, "The action executed Sucessfully \n");
	}

	private void processResponse(ClientResponse response) throws AcronisException {
		JsonNode rootNode = CommonUtil.getRootNode(response);
		LOGGER.info(rootNode.asText());
		
		tenantID = rootNode.get("tenant_id").asText();
		
	}

	private void validateInputs() {

	}

	
	@Override
	protected String getActionName() {
		return "Get User Self";
	}
}
