package com.broadcom;

import java.util.HashMap;
import java.util.Map;

import javax.json.JsonObject;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.StringUtils;

import com.broadcom.apdk.api.annotations.Action;
import com.broadcom.apdk.api.annotations.ActionInputParam;
import com.broadcom.apdk.api.annotations.ActionOutputParam;
import com.broadcom.constants.Constants;
import com.broadcom.exceptions.AcronisException;
import com.broadcom.util.CommonUtil;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

/**
 * This action will update a tenant.
 * 
 * @author vishalkumar03
 *
 */
@Action(name = "UPDATE_TENANT", title = "Update Tenant", path = "ACRONIS")
public class UpdateTenantAction extends AbstractAcronisAction {

	@ActionInputParam(required = true, name = "UC4RB_AC_TENANT_ID", label = "Tenant Id", tooltip = "Provide the tenant id that you want to update. E.G. 9b81bf31-0c04-4f6e-8ebe-56900a3f9e76")
	String tenantId;

	@ActionInputParam(required = true, name = "UC4RB_AC_TENANT_VERSION", label = "Current Version", tooltip = "Version should be same as the tenant that you want to update. E.G. 1")
	Integer currentVersion = 1;

	@ActionInputParam(name = "UC4RB_AC_TENANT_NAME", label = "Tenant Name", tooltip = "Provide the tenant name if you want to update. E.G. Test")
	String tenantName;

	@ActionInputParam(name = "UC4RB_AC_ENABLED", label = "Enabled", tooltip = "Enable or Disable the tenant. E.G. true")
	Boolean enabled = true;

	@ActionInputParam(name = "UC4RB_AC_FIRST_NAME", label = "First Name", tooltip = "Provide the first name if you want to update. E.G. Vishal")
	String firstName;

	@ActionInputParam(name = "UC4RB_AC_LAST_NAME", label = "Last Name", tooltip = "Provide the last name if you want to update. E.G. Kumar")
	String lastName;

	@ActionInputParam(name = "UC4RB_AC_EMAIL", label = "Email", tooltip = "Provide the email if you want to update. E.G. test@gmail.com")
	String email;

	@ActionOutputParam(name = "UC4RB_AC_NEW_VERSION")
	Integer newVersion;

	@Override
	public void run() {
		execute();
	}

	@Override
	protected void executeSpecific() throws AcronisException {
		Map<String, Object> request = createRequest();
		ClientResponse response = null;
		try {
			WebResource webResource = client.resource(url);
			webResource = webResource.path(Constants.API).path(version).path(Constants.TENANTS).path(tenantId);
			System.out.println("Calling url: " + webResource.getURI());
			System.out.println("Request: " + request);

			response = webResource.type(MediaType.APPLICATION_JSON).put(ClientResponse.class, request);
			JsonObject jsonObject = CommonUtil.jsonObjectResponse(response.getEntityInputStream());
			newVersion = jsonObject.getInt(Constants.VERSION);
		} catch (Exception e) {
			String msg = String.format(Constants.REQ_ERROR_MESSAGE, url, e.getMessage());
			// LOGGER.error(msg, e);
			throw new AcronisException(msg);
		}
		// return new ActionResult(true, "The action executed Sucessfully \n");
	}

	/**
	 * Validates the inputs and creates the request
	 * 
	 * @return map with string keys and object type values
	 * @throws AcronisException
	 */
	private Map<String, Object> createRequest() throws AcronisException {
		Map<String, Object> request = new HashMap<>();
		request.put(Constants.VERSION, currentVersion);
		request.put("enabled", enabled);
		if (StringUtils.isEmpty(tenantId)) {
			String msg = String.format(Constants.ISEMPTY, "Tenant Id");
			// _log.error(msg);
			throw new AcronisException(msg);
		}

		if (!StringUtils.isEmpty(tenantName)) {
			request.put("tenantName", tenantName);
		}

		Map<String, Object> contactRequest = new HashMap<>();
		if (!StringUtils.isEmpty(firstName)) {
			contactRequest.put("firstname", firstName);
		}
		if (!StringUtils.isEmpty(lastName)) {
			contactRequest.put("lastname", lastName);
		}
		if (!StringUtils.isEmpty(email)) {
			contactRequest.put("email", email);
		}

		if (!contactRequest.isEmpty()) {
			request.put("contact", contactRequest);
		}
		return request;
	}

	@Override
	protected String getActionName() {
		return "Update Tenant";
	}

}
