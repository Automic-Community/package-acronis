package com.broadcom;

import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.StringUtils;

import com.broadcom.apdk.api.annotations.Action;
import com.broadcom.apdk.api.annotations.ActionInputParam;
import com.broadcom.apdk.api.annotations.ActionOutputParam;
import com.broadcom.constants.Constants;
import com.broadcom.exceptions.AcronisException;
import com.broadcom.util.ConsoleWriter;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

@Action(name = "DELETE_USER", title = "Delete User", path = "ACRONIS")
public class DeleteUserAction extends AbstractAcronisAction {

	@ActionInputParam(name = "UC4RB_AC_USER_ID", tooltip = "Provide the id of the user to be deleted E.g. 88b24185-9b91-43d1-aa2c-b94665adcade8", required = true, label = "User Id")
	String userId;

	@ActionInputParam(name = "UC4RB_AC_USER_VERSION", tooltip = "Provide the version of the user E.g. 158384848", required = true, label = "User Version")
	Long userVersion;

	@ActionOutputParam(name = "UC4RB_AC_USER_STATUS")
	Boolean userStatus;

	@Override
	protected void executeSpecific() throws AcronisException {

		validateInputs();
		try {
			WebResource webResource = client.resource(url);
			webResource = webResource.path("api").path(version).path("users").path(userId).queryParam("version",
					String.valueOf(userVersion));
			LOGGER.info("Calling url: " + webResource.getURI());
			ConsoleWriter.writeln("Calling url: " + webResource.getURI());
			webResource.delete(ClientResponse.class);
		} catch (Exception e) {
			String msg = String.format(Constants.REQ_ERROR_MESSAGE, url);
			LOGGER.warning(e.getMessage());
			throw new AcronisException(msg, e);
		}
	}

	private void validateInputs() throws AcronisException {
		if (StringUtils.isEmpty(userId)) {
			throw new AcronisException(String.format(Constants.ISEMPTY, "User Id"));
		}

		if (StringUtils.isEmpty(userId)) {
			throw new AcronisException(String.format(Constants.ISEMPTY, "User Version"));
		}
	}

	@Override
	protected String getActionName() {
		return "Delete User";
	}
}
