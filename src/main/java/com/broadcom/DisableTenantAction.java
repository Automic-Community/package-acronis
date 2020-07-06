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
import com.broadcom.apdk.api.annotations.PromptSet;
import com.broadcom.constants.Constants;
import com.broadcom.exceptions.AcronisException;
import com.broadcom.helper.GetHelper;
import com.broadcom.util.CommonUtil;
import com.broadcom.util.ConsoleWriter;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

/**
 * This action disable a tenant.
 * 
 * @author vishalkumar03
 *
 */
@Action(name = "DISABLE_TENANT", title = "Disable Tenant", path = "ACRONIS")
@PromptSet(name = "DISABLE_TENANT", title = "Disable Tenant")
public class DisableTenantAction extends AbstractAcronisAction {

	@ActionInputParam(required = true, name = "UC4RB_AC_TENANT_ID", label = "Tenant Id", tooltip = "Provide the tenant id that you want to update. E.g. 9b81bf31-0c04-4f6e-8ebe-56900a3f9e76")
	String tenantId;

	@ActionInputParam(name = "UC4RB_AC_TENANT_VERSION", label = "Current Version", tooltip = "Version should be same as the tenant that you want to update. E.g. 1")
	Long currentVersion;

	@ActionOutputParam(name = "UC4RB_AC_NEW_VERSION")
	Long newVersion;

	boolean enabled;

	@Override
	protected void executeSpecific() throws AcronisException {
		ClientResponse response = null;
		Map<String, Object> request = createRequest();
		if (enabled) {
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
		} else {
			newVersion = currentVersion;
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

		getCurrentVersion();

		Map<String, Object> request = new HashMap<>();
		request.put(Constants.VERSION, currentVersion);
		request.put("enabled", false);

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
			WebResource webResource = client.resource(url);
			webResource = webResource.path(Constants.API).path(version).path(Constants.TENANTS).path(tenantId);
			LOGGER.info("Calling url: " + webResource.getURI());
			ClientResponse clientResponse = GetHelper.urlCall(webResource);
			JsonObject jsonResponseObject = CommonUtil.jsonObjectResponse(clientResponse.getEntityInputStream());
			Long responseVersion = jsonResponseObject.getJsonNumber(Constants.VERSION).longValue();
			if (Objects.nonNull(currentVersion) && !currentVersion.equals(responseVersion)) {
				throw new AcronisException(Constants.TENANT_VERSION_MISMATCH);
			}

			currentVersion = responseVersion;
			enabled = jsonResponseObject.getBoolean("enabled");
		} catch (Exception e) {
			String msg = String.format(Constants.REQ_ERROR_MESSAGE, url);
			LOGGER.warning(e.getMessage());
			throw new AcronisException(msg, e);
		}
	}

	@Override
	protected String getActionName() {
		return "Disable Tenant";
	}

}
