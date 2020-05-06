package com.broadcom;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.StringUtils;

import com.broadcom.apdk.api.annotations.Action;
import com.broadcom.apdk.api.annotations.ActionInputParam;
import com.broadcom.apdk.api.annotations.ActionOutputParam;
import com.broadcom.constants.Constants;
import com.broadcom.constants.Constants.Kind;
import com.broadcom.exceptions.AcronisException;
import com.broadcom.util.CommonUtil;
import com.broadcom.util.ConsoleWriter;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

@Action(name = "CREATE_TENANT", title = "Create Tenant", path = "ACRONIS")
public class CreateTenantAction extends AbstractAcronisAction {

	@ActionInputParam(label = "Tenant Name", name = "UC4RB_AC_TENANT_NAME", tooltip = "Provide the name of the tenant to be created. E.g. New Tenant", required = true)
	String tenantName;

	@ActionInputParam(label = "Parent Id", name = "UC4RB_AC_PARENT_ID", tooltip = "Provide the parent id of the tenant to be created. E.g. 2720cf58-d084-4b22-a284-4a4564fe1e4d", required = true)
	String parentId;

	@ActionInputParam(label = "Tenant Kind", name = "UC4RB_AC_TENANT_TYPE", tooltip = "Provide the kind of the tenant to be created.")
	Kind tenantKind = Kind.CUSTOMER;

	@ActionOutputParam(name = "UC4RB_AC_TENANT_ID")
	String tenantId;

	@ActionOutputParam(name = "UC4RB_AC_TENANT_VERSION")
	Long tenantVersion;

	@Override
	protected void executeSpecific() throws AcronisException {
		validateInputs();
		Map<String, String> request = createRequest();
		JsonObjectBuilder builder = Json.createObjectBuilder();
		for (Entry<String, String> entry : request.entrySet()) {
			builder.add(entry.getKey(), entry.getValue());
		}
		JsonObject jsonObjectRequest = builder.build();
		ClientResponse response = null;
		try {
			WebResource webResource = client.resource(url);
			webResource = webResource.path("api").path(version).path("tenants");
			LOGGER.info("Calling url: " + webResource.getURI());
			ConsoleWriter.writeln("Calling url: " + webResource.getURI());
			ConsoleWriter.writeln("Request Body: " + CommonUtil.jsonPrettyPrinting(jsonObjectRequest));
			response = webResource.type(MediaType.APPLICATION_JSON).post(ClientResponse.class, request);
		} catch (Exception e) {
			String msg = String.format(Constants.REQ_ERROR_MESSAGE, url);
			LOGGER.warning(e.getMessage());
			throw new AcronisException(msg, e);
		}
		prepareOutput(CommonUtil.jsonObjectResponse(response.getEntityInputStream()));
	}

	/**
	 * Creates the request
	 * 
	 * @return map with string keys and object type values
	 * @throws AcronisException
	 */
	private Map<String, String> createRequest() throws AcronisException {
		Map<String, String> request = new HashMap<>();
		request.put("name", tenantName);
		request.put("parent_id", parentId);
		request.put("kind", tenantKind.toString());
		return request;
	}

	private void prepareOutput(JsonObject jsonObjectResponse) {
		// write response to console
		ConsoleWriter.writeln("Response: " + CommonUtil.jsonPrettyPrinting(jsonObjectResponse));
		tenantId = jsonObjectResponse.getString("id");
		tenantVersion = jsonObjectResponse.getJsonNumber(Constants.VERSION).longValue();
	}

	private void validateInputs() throws AcronisException {
		if (StringUtils.isEmpty(tenantName)) {
			throw new AcronisException(String.format(Constants.ISEMPTY, "Tenant Name"));
		}
		if (StringUtils.isEmpty(parentId)) {
			throw new AcronisException(String.format(Constants.ISEMPTY, "Parent Tenant Id"));
		}
	}

	@Override
	protected String getActionName() {
		return "Create Tenant";
	}
}
