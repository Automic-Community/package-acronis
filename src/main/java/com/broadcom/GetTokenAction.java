package com.broadcom;

import com.broadcom.apdk.api.annotations.Action;
import com.broadcom.apdk.api.annotations.ActionOutputParam;
import com.broadcom.constants.Constants;
import com.broadcom.exceptions.AcronisException;
import com.broadcom.helper.TokenHelper;
import com.broadcom.util.ConsoleWriter;
import com.sun.jersey.api.client.WebResource;

/**
 * This action will get the token.
 * 
 * @author vishalkumar03
 *
 */
@Action(name = "GET_TOKEN", title = "Get Token", path = "ACRONIS")
public class GetTokenAction extends APIClientAuthAction {

	@ActionOutputParam(name = "UC4RB_AC_ACCESS_TOKEN")
	String accessToken;

	@Override
	protected void executeSpecific() throws AcronisException {
		try {
			WebResource webResource = client.resource(url);
			webResource = webResource.path(Constants.API).path(version).path(Constants.IDP).path(Constants.TOKEN);
			LOGGER.info("Calling url: " + webResource.getURI());
			ConsoleWriter.writeln("Calling url: " + webResource.getURI());
			accessToken = TokenHelper.getToken(webResource);
		} catch (Exception e) {
			String msg = String.format(Constants.REQ_ERROR_MESSAGE, url);
			LOGGER.warning(e.getMessage());
			throw new AcronisException(msg, e);
		}
	}

	@Override
	protected String getActionName() {
		return "Get Token";
	}
}