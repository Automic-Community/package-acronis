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
import com.broadcom.apdk.api.annotations.PromptSet;
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
@PromptSet(name = "SWITCH_CUSTOMER_TO_PRODUCTION", title = "Switch Customer to Production")
public class SwitchCustomerToProductionAction extends AbstractAcronisAction  {
	
	/** tenantId. */
	@ActionInputParam(required = true, name = "UC4RB_AC_TENANT_ID", label = "Tenant Id", tooltip = "Provide the tenant id that you want to switch to production. E.g. 9b81bf31-0c04-4f6e-8ebe-56900a3f9e76")
	String tenantId;
	
	/** tenantMode. */
	@ActionOutputParam(name = "UC4RB_AC_TENANT_MODE")
	String tenantMode;
	
	/** currencyVersion. */
	@ActionOutputParam(name = "UC4RB_AC_CURRENCY_VERSION")
	Long currencyVersion;
	
	private boolean isModeAlreadyProduction;
	
	public SwitchCustomerToProductionAction() {
		setDocumentation("= Action name =\r\n" + 
				"PCK.AUTOMIC_ACRONIS.PUB.ACTION.SWITCH_CUSTOMER_TO_PRODUCTION\r\n" + 
				"\r\n" + 
				"= General description =\r\n" + 
				"This action switch the customer to production mode.\r\n" + 
				"\r\n" + 
				"= Inputs =\r\n" + 
				"* Tenant Id*                : Provide the tenant id that you want to switch to production. E.g. 9b81bf31-0c04-4f6e-8ebe-56900a3f9e76\r\n" + 
				"\r\n" + 
				"= Failure Conditions =\r\n" + 
				"(none)\r\n" + 
				"\r\n" + 
				"= Behaviour =\r\n" + 
				"(none)\r\n" + 
				"\r\n" + 
				"= Return value =\r\n" + 
				"* UC4RB_AC_TENANT_MODE#        : Mode of the tenant.\r\n" + 
				"* UC4RB_AC_CURRENCY_VERSION#	: Version of the currency.\r\n" + 
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
	
	@Override
	protected void executeSpecific() throws AcronisException {
		validateInputs();
		ClientResponse response = null;
		Map<String, Object> request = null;
		try {			
			WebResource webResource = client.resource(url);
			webResource = webResource.path(Constants.API).path(version).path(Constants.TENANTS).path(tenantId).path(Constants.PRICING);
			request=createRequest(webResource);
			
			if(!isModeAlreadyProduction) {
			ConsoleWriter.writeln("Calling url: " + webResource.getURI());
			ConsoleWriter.writeln("Request: " + request);				
			response = webResource.type(MediaType.APPLICATION_JSON).put(ClientResponse.class, request);			
			prepareOutput(CommonUtil.jsonObjectResponse(response.getEntityInputStream()));
			}
		} catch (Exception ex) {
			String msg = String.format(Constants.REQ_ERROR_MESSAGE, url);
			LOGGER.log(Level.SEVERE, ex.toString(), ex);
			throw new AcronisException(msg, ex);
		}
	}
	
	private void prepareOutput(JsonObject jsonObjectResponse) {
		// write response to console
		ConsoleWriter.writeln("Response: \n" + CommonUtil.jsonPrettyPrinting(jsonObjectResponse));
		currencyVersion = jsonObjectResponse.getJsonNumber(Constants.VERSION).longValue();
		tenantMode=jsonObjectResponse.getString("mode");
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
	private Map<String, Object> createRequest(WebResource webResource) throws AcronisException {	
		
		ClientResponse response = webResource.accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
		JsonObject jsonResponse = CommonUtil.jsonObjectResponse(response.getEntityInputStream());
		LOGGER.info("Response: " + CommonUtil.jsonPrettyPrinting(jsonResponse));
			
		Map<String, Object> request = new HashMap<>();
		isModeAlreadyProduction=jsonResponse.getString("mode").equals("production")?true:false;
		
		if(isModeAlreadyProduction) {	
			ConsoleWriter.writeln("Customer Mode is already in production");		
			LOGGER.info("Customer Mode is already in production");
			prepareOutput(jsonResponse);	
		}else {
		request.put(Constants.VERSION, jsonResponse.getJsonNumber(Constants.VERSION).longValue());
		request.put("mode", "production");	
		}
		return request;
	}
	
	@Override
	protected String getActionName() {
		// TODO Auto-generated method stub
		return "Switch Customer to Production";
	}
}
