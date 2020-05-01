package com.broadcom;

import javax.ws.rs.core.MediaType;

import com.broadcom.apdk.api.annotations.Action;
import com.broadcom.apdk.api.annotations.ActionInputParam;
import com.broadcom.apdk.api.annotations.ActionOutputParam;
import com.broadcom.constants.Constants;
import com.broadcom.constants.Constants.Kind;
import com.broadcom.exceptions.AcronisException;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

@Action(name = "CREATE_TENANT", title = "Create Tenant", path = "ACRONIS")
public class CreateTenantAction extends AbstractAcronisAction {

	@ActionInputParam(name = "UC4RB_AC_TENANT_NAME")
	String tenantName;
	
	@ActionInputParam(name = "UC4RB_AC_PARENT_TENANT_ID")
	String parentTenantId;

	@ActionInputParam(name = "UC4RB_AC_TENANT_TYPE")
	Kind tenantKind = Kind.CUSTOMER;

	@ActionOutputParam(name = "UC4RB_AC_TENANT_ID")
	String tenantId;

	@ActionOutputParam(name = "UC4RB_AC_TENANT_VERSION")
	Long tenantVersion;

	@Override
	public void run() {
		execute();
	}

	@Override
	protected void executeSpecific() throws AcronisException {
		validateInputs();
		ClientResponse response = null;
		try {
			WebResource webResource = client.resource(url);
			webResource = webResource.path("api").path(version).path("tenants");
			LOGGER.info("Calling url: " + webResource.getURI());
			response = webResource.accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
		} catch (Exception e) {
			String msg = String.format(Constants.REQ_ERROR_MESSAGE, url, e.getMessage());
			// LOGGER.error(msg, e);
			// throw new AcronisException(msg, e);
		}
		// return new ActionResult(true, "The action executed Sucessfully \n");
	}

	private void validateInputs() {

	}

	@Override
	protected String getActionName() {
		return "Create Tenant";
	}
}
