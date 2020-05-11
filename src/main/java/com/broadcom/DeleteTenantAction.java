package com.broadcom;

import javax.json.JsonObject;

import org.apache.commons.lang3.StringUtils;

import com.broadcom.apdk.api.annotations.Action;
import com.broadcom.apdk.api.annotations.ActionInputParam;
import com.broadcom.constants.Constants;
import com.broadcom.exceptions.AcronisException;
import com.broadcom.exceptions.NotFoundRuntimeException;
import com.broadcom.helper.DisableHelper;
import com.broadcom.helper.GetHelper;
import com.broadcom.util.CommonUtil;
import com.broadcom.util.ConsoleWriter;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

@Action(name = "DELETE_TENANT", title = "Delete Tenant", path = "ACRONIS")
public class DeleteTenantAction extends AbstractAcronisAction {

	@ActionInputParam(required = true, name = "UC4RB_AC_TENANT_ID", tooltip = "Provide the name of the tenant to be deleted. E.g. d540ac7f-2e8b-4451-a1cc-18ee9586af69", label = "Tenant Id")
	String tenantId;

	@ActionInputParam(required = true, name = "UC4RB_AC_FAIL", tooltip = "", label = "Fail if non-existing")
	Boolean fail = true;
	
	boolean isEnabled;

	@Override
	protected void executeSpecific() throws AcronisException {
		validateInputs();
		try {
			WebResource webResource = client.resource(url);
			webResource = webResource.path(Constants.API).path(version).path(Constants.TENANTS).path(tenantId);
			Long tenantVersion = getCurrentVersion(webResource);
			if (isEnabled) {
				tenantVersion = disableTenant(webResource, tenantVersion);
			}
			webResource = webResource.queryParam(Constants.VERSION, String.valueOf(tenantVersion));
			LOGGER.info("Calling url: " + webResource.getURI());
			ConsoleWriter.writeln("Calling url: " + webResource.getURI());
			webResource.delete(ClientResponse.class);
		} catch (NotFoundRuntimeException e) {
			if (fail) {
				String msg = String.format(Constants.REQ_ERROR_MESSAGE, url);
				LOGGER.warning(e.getMessage());
				throw new AcronisException(msg, e);
			} else {
				ConsoleWriter.writeln("INFO:  Tenant does not exist.");
			}
		} catch (Exception e) {
			String msg = String.format(Constants.REQ_ERROR_MESSAGE, url);
			LOGGER.warning(e.getMessage());
			throw new AcronisException(msg, e);
		}
	}

	
	/**
	 * Calls the get tenant API to get the current version 
	 * 
	 * @param webResource
	 * 
	 * @return  version
	 */
	private Long getCurrentVersion(WebResource webResource) {
		LOGGER.info("Calling url: " + webResource.getURI());
		ClientResponse clientResponse = GetHelper.urlCall(webResource);
		JsonObject jsonResponseObject = CommonUtil.jsonObjectResponse(clientResponse.getEntityInputStream());
		isEnabled = jsonResponseObject.getBoolean("enabled");
		return jsonResponseObject.getJsonNumber(Constants.VERSION).longValue();
	}

	/**
	 * Disable the tenant and update the current version value.
	 * 
	 * @param version, webResource
	 * 
	 * @return updated version
	 */
	private Long disableTenant(WebResource webResource, Long version) {
		LOGGER.info("Calling url: " + webResource.getURI());
		ClientResponse clientResponse = DisableHelper.urlCall(version, webResource);
		JsonObject jsonResponseObject = CommonUtil.jsonObjectResponse(clientResponse.getEntityInputStream());
		return jsonResponseObject.getJsonNumber(Constants.VERSION).longValue();
	}

	private void validateInputs() throws AcronisException {
		if (StringUtils.isEmpty(tenantId)) {
			throw new AcronisException(String.format(Constants.ISEMPTY, "Tenant Id"));
		}
	}
	
	@Override
	protected String getActionName() {
		return "Delete Tenant";
	}
}
