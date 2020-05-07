package com.broadcom;

import javax.json.JsonObject;

import org.apache.commons.lang3.StringUtils;

import com.broadcom.apdk.api.annotations.Action;
import com.broadcom.apdk.api.annotations.ActionInputParam;
import com.broadcom.apdk.api.annotations.ActionOutputParam;
import com.broadcom.constants.Constants;
import com.broadcom.exceptions.AcronisException;
import com.broadcom.helper.GetUserHelper;
import com.broadcom.util.CommonUtil;
import com.broadcom.util.ConsoleWriter;
import com.sun.jersey.api.client.ClientResponse;

@Action(name = "GET_USER", title = "Get User", path = "ACRONIS")
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
			response = GetUserHelper.urlCall(userId, client, url, new String[] { Constants.API, version, "users" });
//			response = webResource.accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
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
}
