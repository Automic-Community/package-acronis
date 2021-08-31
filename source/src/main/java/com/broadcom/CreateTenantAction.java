package com.broadcom;

import java.util.HashMap;
import java.util.Iterator;
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
import com.broadcom.apdk.api.annotations.PromptSet;
import com.broadcom.constants.Constants;
import com.broadcom.constants.Constants.Kind;
import com.broadcom.exceptions.AcronisException;
import com.broadcom.util.CommonUtil;
import com.broadcom.util.ConsoleWriter;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

@Action(name = "CREATE_TENANT", title = "Create Tenant", path = "ACRONIS")
@PromptSet(name = "CREATE_TENANT", title = "Create Tenant")
public class CreateTenantAction extends AbstractAcronisAction {

	@ActionInputParam(label = "Tenant Name", name = "UC4RB_AC_TENANT_NAME", tooltip = "Provide the name of the tenant to be created. E.g. New Tenant", required = true)
	String tenantName;

	@ActionInputParam(label = "Parent Id", name = "UC4RB_AC_PARENT_ID", tooltip = "Provide the parent id of the tenant to be created. E.g. 2720cf58-d084-4b22-a284-4a4564fe1e4d", required = true)
	String parentId;

	@ActionInputParam(label = "Tenant Kind", name = "UC4RB_AC_TENANT_KIND", tooltip = "Provide the kind of tenant to be created.")
	Kind tenantKind = Kind.CUSTOMER;

	@ActionInputParam(label = "Email", name = "UC4RB_AC_EMAIL", tooltip = "Provide the email of the tenant administrator. E.g. test@gmail.com")
	String email;
	
	@ActionInputParam(label = "First Name", name = "UC4RB_AC_FIRST_NAME", tooltip = "Provide the first name of the tenant administrator. E.g. Vishal")
	String firstName;

	@ActionInputParam(label = "Last Name", name = "UC4RB_AC_LAST_NAME", tooltip = "Provide the last name of the tenant administrator. E.g. Kumar")
	String lastName;

	@ActionOutputParam(name = "UC4RB_AC_TENANT_ID")
	String tenantId;

	@ActionOutputParam(name = "UC4RB_AC_TENANT_VERSION")
	Long tenantVersion;

	@Override
	protected void executeSpecific() throws AcronisException {
		validateInputs();
		Map<String, Object> request = createRequest();
		JsonObject requestObject = ((JsonObjectBuilder) getJsonObject(request)).build();
		ClientResponse response = null;
		try {
			WebResource webResource = client.resource(url);
			webResource = webResource.path(Constants.API).path(version).path(Constants.TENANTS);
			LOGGER.info("Calling url: " + webResource.getURI());
			ConsoleWriter.writeln("Calling url: " + webResource.getURI());
			ConsoleWriter.writeln("Request Body: " + CommonUtil.jsonPrettyPrinting(requestObject));
			response = webResource.type(MediaType.APPLICATION_JSON).post(ClientResponse.class, request);
		} catch (Exception e) {
			String msg = String.format(Constants.REQ_ERROR_MESSAGE, url);
			LOGGER.warning(e.getMessage());
			throw new AcronisException(msg, e);
		}
		prepareOutput(CommonUtil.jsonObjectResponse(response.getEntityInputStream()));
	}
	
	@SuppressWarnings("unchecked")
	private Object getJsonObject(Map<String, Object> request) {
		JsonObjectBuilder builder = Json.createObjectBuilder();
		Iterator<Entry<String, Object>> it = request.entrySet().iterator();
		while (it.hasNext()) {
			Entry<String, Object> pair = it.next();
			Object value = pair.getValue();
			if (value instanceof Map<?, ?>) {
				value = getJsonObject((Map<String, Object>) value);
			}

			if (value instanceof String) {
				builder.add((String) pair.getKey(), value.toString());
			} else {
				builder.add((String) pair.getKey(), (JsonObjectBuilder) value);
			}
		}
		return builder;
	}

	/**
	 * Creates the request
	 * 
	 * @return map with string keys and object type values
	 * @throws AcronisException
	 */
	private Map<String, Object> createRequest() {
		Map<String, Object> request = new HashMap<>();
		request.put("name", tenantName);
		request.put("parent_id", parentId);
		request.put("kind", tenantKind.toString());

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
	public CreateTenantAction() {
		setDocumentation("= Action name =\r\n" + 
				"PCK.AUTOMIC_ACRONIS.PUB.ACTION.CREATE_TENANT\r\n" + 
				"\r\n" + 
				"= General description =\r\n" + 
				"This action creates a tenant in Acronis.\r\n" + 
				"\r\n" + 
				"= Inputs =\r\n" + 
				"* Tenant Name*	            : Provide the name of the tenant to be created. E.g. New Tenant\r\n" + 
				"* Parent Id*                : Provide the parent id of the tenant to be created. E.g. 2720cf58-d084-4b22-a284-4a4564fe1e4d\r\n" + 
				"* Tenant Kind				: Provide the kind of tenant to be created. Default - Customer\r\n" + 
				"* Email			            : Provide the email of the tenant administrator. E.g. test@gmail.com\r\n" + 
				"* First Name                : Provide the first name of the tenant administrator. E.g. Vishal\r\n" + 
				"* Last Name					: Provide the last name of the tenant administrator. E.g. Kumar\r\n" + 
				"\r\n" + 
				"= Failure Conditions =\r\n" + 
				"(none)\r\n" + 
				"\r\n" + 
				"= Behaviour =\r\n" + 
				"Email required when First Name and/or Last Name is/are provided.\r\n" + 
				"\r\n" + 
				"= Return value =\r\n" + 
				"* UC4RB_AC_TENANT_ID#       : Id of the newly created tenant.\r\n" + 
				"* UC4RB_AC_TENANT_VERSION#	: Version of the tenant.\r\n" + 
				"\r\n" + 
				"= Outputs =\r\n" + 
				"* Return code is 0 in case of success.\r\n" + 
				"* Return code is non-zero in case of failure.\r\n" + 
				"\r\n" + 
				"= Rollback =\r\n" + 
				"(none)\r\n" + 
				"\r\n" + 
				"= Logging =\r\n" + 
				"AE logs will be displayed in the AE report.");
	}
}
