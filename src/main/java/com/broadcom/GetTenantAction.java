package com.broadcom;

import java.util.logging.Level;

import javax.json.JsonObject;

import org.apache.commons.lang3.StringUtils;

import com.broadcom.apdk.api.annotations.Action;
import com.broadcom.apdk.api.annotations.ActionInputParam;
import com.broadcom.apdk.api.annotations.ActionOutputParam;
import com.broadcom.constants.Constants;
import com.broadcom.exceptions.AcronisException;
import com.broadcom.helper.GetHelper;
import com.broadcom.util.CommonUtil;
import com.broadcom.util.ConsoleWriter;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

/**
 * This action fetches the tenant info by using tenant id.
 */
@Action(name = "GET_TENANT", title = "Get Tenant", path = "ACRONIS")
public class GetTenantAction extends AbstractAcronisAction {

	/** tenantId. */
	@ActionInputParam(name = "UC4RB_AC_TENANT_ID", label = "Tenant Id", required = true, tooltip = "Provide the Id of the tenant. E.g. 6f2e420b-bd8c-4ade-b3bb-4942d7c89032")
	String tenantId;

	/** tenantName. */
	@ActionOutputParam(name = "UC4RB_AC_TENANT_NAME")
	String tenantName;

	/** tenantVersion. */
	@ActionOutputParam(name = "UC4RB_AC_TENANT_VERSION")
	Integer tenantVersion;

	/** tenantEnabled. */
	@ActionOutputParam(name = "UC4RB_AC_ENABLED")
	Boolean tenantEnabled;

	/** tenantParentId. */
	@ActionOutputParam(name = "UC4RB_AC_PARENT_TENANT_ID")
	String parentTenantId;

	/** tenantKind. */
	@ActionOutputParam(name = "UC4RB_AC_TENANT_KIND")
	String tenantKind;

	/**
	 * This Method used to perform Get Tenant action specific operations.
	 *
	 * @throws AcronisException
	 */
	@Override
	protected void executeSpecific() throws AcronisException {
		validateInputs();
		ClientResponse response = null;
		try {
			WebResource webResource = client.resource(url);
			webResource = webResource.path(Constants.API).path(version).path(Constants.TENANTS).path(tenantId);
			LOGGER.info("Calling url: " + webResource.getURI());
			ConsoleWriter.writeln("Calling url: " + webResource.getURI());
			response = GetHelper.urlCall(webResource);
			prepareOutput(CommonUtil.jsonObjectResponse(response.getEntityInputStream()));
		} catch (Exception ex) {
			String msg = String.format(Constants.REQ_ERROR_MESSAGE, url);
			LOGGER.log(Level.SEVERE, ex.toString(), ex);
			throw new AcronisException(msg, ex);
		}
	}

	private void validateInputs() throws AcronisException {
		if (StringUtils.isEmpty(tenantId)) {
			String msg = String.format(Constants.ISEMPTY, "Tenant Id");
			throw new AcronisException(msg);
		}
	}

	private void prepareOutput(JsonObject jsonObjectResponse) {
		// write response to console
		ConsoleWriter.writeln("Response: \n" + CommonUtil.jsonPrettyPrinting(jsonObjectResponse));
		tenantName = jsonObjectResponse.getString("name");
		parentTenantId = jsonObjectResponse.getString("parent_id");
		tenantEnabled = jsonObjectResponse.getBoolean("enabled");
		tenantKind = jsonObjectResponse.getString("kind");
		tenantVersion = jsonObjectResponse.getInt("version");
	}

	/**
	 * @return Action Name
	 */
	@Override
	protected String getActionName() {
		return "Get Tenant";
	}

}
