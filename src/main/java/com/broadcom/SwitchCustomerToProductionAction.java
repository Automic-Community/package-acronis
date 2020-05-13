package com.broadcom;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

import javax.json.JsonObject;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.StringUtils;

import com.broadcom.apdk.api.annotations.Action;
import com.broadcom.apdk.api.annotations.ActionInputParam;
import com.broadcom.apdk.api.annotations.ActionOutputParam;
import com.broadcom.constants.Constants;
import com.broadcom.exceptions.AcronisException;
import com.broadcom.util.CommonUtil;
import com.broadcom.util.ConsoleWriter;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

/**
 * This action will switch customer to production.
 * 
 * @author ashishkumar09
 *
 */
@Action(name = "SWITCH_CUSTOMER_TO_PRODUCTION", title = "Switch Customer to Production", path = "ACRONIS")
public class SwitchCustomerToProductionAction extends AbstractAcronisAction  {
	
	/** tenantId. */
	@ActionInputParam(required = true, name = "UC4RB_AC_TENANT_ID", label = "Tenant Id", tooltip = "Provide the tenant id that you want to switch to production. E.g. 9b81bf31-0c04-4f6e-8ebe-56900a3f9e76")
	String tenantId;
	
	/** tenantMode. */
	@ActionOutputParam(name = "UC4RB_AC_TENANT_MODE")
	String tenantMode;
	
	/** tenantMode. */
	@ActionOutputParam(name = "UC4RB_AC_TENANT_CURRENCY")
	String currency;

	/** tenantNewVersion. */
	@ActionOutputParam(name = "UC4RB_AC_TENANT_NEW_VERSION")
	Long tenantNewVersion;

	
	@Override
	protected void executeSpecific() throws AcronisException {
		validateInputs();
		ClientResponse response = null;
		Map<String, Object> request = createRequest();
		try {			
			WebResource webResource = client.resource(url);
			webResource = webResource.path(Constants.API).path(version).path(Constants.TENANTS).path(tenantId).path("pricing");
			ConsoleWriter.writeln("Calling url: " + webResource.getURI());
			ConsoleWriter.writeln("Request: " + request);			
			response = webResource.type(MediaType.APPLICATION_JSON).put(ClientResponse.class, request);			
			prepareOutput(CommonUtil.jsonObjectResponse(response.getEntityInputStream()));			
		} catch (Exception ex) {
			String msg = String.format(Constants.REQ_ERROR_MESSAGE, url);
			LOGGER.log(Level.SEVERE, ex.toString(), ex);
			throw new AcronisException(msg, ex);
		}
	}
	
	private void prepareOutput(JsonObject jsonObjectResponse) {
		// write response to console
		ConsoleWriter.writeln("Response: \n" + CommonUtil.jsonPrettyPrinting(jsonObjectResponse));
		tenantNewVersion = jsonObjectResponse.getJsonNumber(Constants.VERSION).longValue();
		tenantMode=jsonObjectResponse.getString("mode");
		currency=jsonObjectResponse.isNull("curreny")?"":jsonObjectResponse.getString("curreny");
	}

	private void validateInputs() throws AcronisException {
		if (StringUtils.isEmpty(tenantId)) {
			String msg = String.format(Constants.ISEMPTY, "Tenant Id");
			throw new AcronisException(msg);
		}
	}
	
	/**
	 * Validates the inputs and creates the request
	 * 
	 * @return map with string keys and object type values
	 * @throws AcronisException
	 */
	private Map<String, Object> createRequest() throws AcronisException {	
		ClientResponse response = null;
		WebResource webResource = client.resource(url);
		webResource = webResource.path("api").path(version).path("tenants").path(tenantId).path("pricing");
		LOGGER.info("Calling url: " + webResource.getURI());
		ConsoleWriter.writeln("Calling url: " + webResource.getURI());
		
		webResource.accept(MediaType.APPLICATION_JSON);
		response = webResource.get(ClientResponse.class);
		JsonObject jsonResponse = CommonUtil.jsonObjectResponse(response.getEntityInputStream());
		ConsoleWriter.writeln("Response: " + CommonUtil.jsonPrettyPrinting(jsonResponse));
			
		if(jsonResponse.getString("mode").equalsIgnoreCase("production")) {
			String msg = String.format(Constants.REQ_ERROR_MESSAGE, " Tenant Mode is "+ jsonResponse.getString("mode"));
			throw new AcronisException(msg);
		}	
		Map<String, Object> request = new HashMap<>();
		request.put(Constants.VERSION, jsonResponse.getJsonNumber(Constants.VERSION).longValue());
		request.put("mode", "production");		
		return request;
	}
	
	@Override
	protected String getActionName() {
		// TODO Auto-generated method stub
		return "Switch Customer to Production";
	}
}
