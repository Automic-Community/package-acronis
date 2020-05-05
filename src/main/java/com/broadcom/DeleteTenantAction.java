package com.broadcom;

import com.broadcom.apdk.api.annotations.Action;
import com.broadcom.apdk.api.annotations.ActionInputParam;
import com.broadcom.constants.Constants;
import com.broadcom.exceptions.AcronisException;
import com.broadcom.util.ConsoleWriter;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

@Action(name = "DELETE_TENANT", title = "Delete Teant", path = "ACRONIS")
public class DeleteTenantAction extends AbstractAcronisAction {

	@ActionInputParam(required = true, name = "UC4RB_AC_TENANT_ID", tooltip = "Provide the name of the tenant to be deleted. E.g. d540ac7f-2e8b-4451-a1cc-18ee9586af69", label = "Tenant Id")
	String tenantId;

	@ActionInputParam(required = true, name = "UC4RB_AC_TENANT_VERSION", tooltip = "Provide tenant version. E.g. 2", label = "Tenant Version")
	String tenantVersion;

	@Override
	protected void executeSpecific() throws AcronisException {
		try {
			WebResource webResource = client.resource(url);
			webResource = webResource.path("api").path(version).path("tenants").path(tenantId).queryParam("version",
					tenantVersion);
			LOGGER.info("Calling url: " + webResource.getURI());
			ConsoleWriter.writeln("Calling url: " + webResource.getURI());
			webResource.delete(ClientResponse.class);
		} catch (Exception e) {
			String msg = String.format(Constants.REQ_ERROR_MESSAGE, url);
			LOGGER.warning(e.getMessage());
			throw new AcronisException(msg, e);
		}
	}

	@Override
	protected String getActionName() {
		return "Delete Tenant";
	}
}
