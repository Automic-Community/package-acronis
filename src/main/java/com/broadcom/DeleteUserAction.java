package com.broadcom;

import javax.json.JsonObject;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import com.broadcom.apdk.api.annotations.Action;
import com.broadcom.apdk.api.annotations.ActionInputParam;
import com.broadcom.apdk.api.annotations.ActionOutputParam;
import com.broadcom.constants.Constants;
import com.broadcom.exceptions.AcronisException;
import com.broadcom.util.CommonUtil;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

@Action(name = "DELETE_USER", title = "Delete User", path = "ACRONIS")
public class DeleteUserAction extends AbstractAcronisAction {

	@ActionInputParam(name = "UC4RB_AC_USER_ID" , tooltip="Provide the userid Of the User for deletion",required=true)
	String Userid;
	
	@ActionInputParam(name = "UC4RB_AC_USER_VERSION" , tooltip="Provide the version Of the User for deletion",required=true)
	int Userversion;
	
	
	@ActionOutputParam(name = "UC4RB_AC_USER_STATUS")
	Boolean UserStatus;
	

	@Override
	public void run() {
		execute();
	}

	@Override
	protected void executeSpecific() throws AcronisException {
		
		String Status=validateInputs();
		if (Status == "Valid");
		{
		ClientResponse response = null;
		try {
			WebResource webResource = client.resource(url);
			webResource = webResource.path("api").path(version).path("users").path(Userid);
			LOGGER.info("Calling url: " + webResource.getURI());
			response = webResource.queryParam("version", String.valueOf(Userversion)).accept(MediaType.APPLICATION_JSON).delete(ClientResponse.class);
		} catch (Exception e) {
			String msg = String.format(Constants.REQ_ERROR_MESSAGE, url, e.getMessage());
			throw new AcronisException(msg, e);
		}
	}
	}
	private String validateInputs() {
		if(!Userid.isEmpty())
		{
		return "Valid";
		}
		else
			{
			return "Invalid";
			}
	}

	@Override
	protected String getActionName() {
		return "Delete User";
	}
}
