package com.broadcom;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.json.JsonObject;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.StringUtils;

import com.broadcom.apdk.api.annotations.Action;
import com.broadcom.apdk.api.annotations.ActionInputParam;
import com.broadcom.apdk.api.annotations.ActionOutputParam;
import com.broadcom.constants.Constants;
import com.broadcom.exceptions.AcronisException;
import com.broadcom.helper.GetTenantHelper;
import com.broadcom.util.CommonUtil;
import com.broadcom.util.ConsoleWriter;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

/**
 * This action updates a tenant.
 * 
 * @author vishalkumar03
 *
 */
@Action(name = "UPDATE_TENANT", title = "Update Tenant", path = "ACRONIS")
public class UpdateTenantAction extends AbstractAcronisAction {

	@ActionInputParam(required = true, name = "UC4RB_AC_TENANT_ID", label = "Tenant Id", tooltip = "Provide the tenant id that you want to update. E.g. 9b81bf31-0c04-4f6e-8ebe-56900a3f9e76")
	String tenantId;

	@ActionInputParam(name = "UC4RB_AC_TENANT_VERSION", label = "Current Version", tooltip = "Version should be same as the tenant that you want to update. E.g. 1")
	Long currentVersion;

	@ActionInputParam(name = "UC4RB_AC_TENANT_NAME", label = "Tenant Name", tooltip = "Provide the tenant name if you want to update. E.g. Test")
	String tenantName;

	@ActionInputParam(name = "UC4RB_AC_ENABLED", label = "Enabled", tooltip = "Enable or Disable the tenant. E.g. true")
	Boolean enabled;

	@ActionInputParam(name = "UC4RB_AC_FIRST_NAME", label = "First Name", tooltip = "Provide the first name if you want to update. E.g. Vishal")
	String firstName;

	@ActionInputParam(name = "UC4RB_AC_LAST_NAME", label = "Last Name", tooltip = "Provide the last name if you want to update. E.g. Kumar")
	String lastName;

	@ActionInputParam(name = "UC4RB_AC_EMAIL", label = "Email", tooltip = "Provide the email if you want to update. E.G. test@gmail.com")
	String email;

	@ActionOutputParam(name = "UC4RB_AC_NEW_VERSION")
	Long newVersion;

	@Override
	protected void executeSpecific() throws AcronisException {
		ClientResponse response = null;
		Map<String, Object> request = createRequest();
		try {
			WebResource webResource = client.resource(url);
			webResource = webResource.path(Constants.API).path(version).path(Constants.TENANTS).path(tenantId);
			ConsoleWriter.writeln("Calling url: " + webResource.getURI());
			ConsoleWriter.writeln("Request: " + request);

			response = webResource.type(MediaType.APPLICATION_JSON).put(ClientResponse.class, request);
			JsonObject jsonResponse = CommonUtil.jsonObjectResponse(response.getEntityInputStream());
			ConsoleWriter.writeln("Response: " + CommonUtil.jsonPrettyPrinting(jsonResponse));
			newVersion = jsonResponse.getJsonNumber(Constants.VERSION).longValue();
		} catch (Exception e) {
			String msg = String.format(Constants.REQ_ERROR_MESSAGE, url);
			LOGGER.warning(e.getMessage());
			throw new AcronisException(msg, e);
		}
	}

	/**
	 * Validates the inputs and creates the request
	 * 
	 * @return map with string keys and object type values
	 * @throws AcronisException
	 */
	private Map<String, Object> createRequest() throws AcronisException {

		if (StringUtils.isEmpty(tenantId)) {
			String msg = String.format(Constants.ISEMPTY, "Tenant Id");
			throw new AcronisException(msg);
		}

		Map<String, Object> request = new HashMap<>();
		request.put("enabled", enabled);

		if (Objects.isNull(currentVersion)) {
			getCurrentVersion();
		}
		request.put(Constants.VERSION, currentVersion);

		if (StringUtils.isNotEmpty(tenantName)) {
			request.put("tenantName", tenantName);
		}

		Map<String, Object> contactRequest = new HashMap<>();
		if (StringUtils.isNotEmpty(firstName)) {
			contactRequest.put("firstname", firstName);
		}
		if (StringUtils.isNotEmpty(lastName)) {
			contactRequest.put("lastname", lastName);
		}
		if (StringUtils.isNotEmpty(email)) {
			contactRequest.put("email", email);
		}

		if (!contactRequest.isEmpty()) {
			request.put("contact", contactRequest);
		}
		return request;
	}

	/**
	 * Calls the get tenant API to get the current version only if user doesn't
	 * provide the current version.
	 * 
	 * @throws AcronisException
	 */
	private void getCurrentVersion() throws AcronisException {
		try {
			ClientResponse response = GetTenantHelper.urlCall(tenantId, client, url,
					new String[] { Constants.API, version, "tenants" });
			JsonObject jsonObject = CommonUtil.jsonObjectResponse(response.getEntityInputStream());
			currentVersion = jsonObject.getJsonNumber(Constants.VERSION).longValue();
		} catch (Exception e) {
			String msg = String.format(Constants.REQ_ERROR_MESSAGE, url);
			LOGGER.warning(e.getMessage());
			throw new AcronisException(msg, e);
		}
	}

	@Override
	protected String getActionName() {
		return "Update Tenant";
	}

}
