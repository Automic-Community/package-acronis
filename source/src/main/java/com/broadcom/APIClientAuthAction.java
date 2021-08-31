package com.broadcom;

import org.apache.commons.lang3.StringUtils;

import com.broadcom.apdk.api.annotations.ActionInputParam;
import com.broadcom.apdk.api.annotations.PromptSet;
import com.broadcom.constants.Constants;
import com.broadcom.exceptions.AcronisException;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;

@PromptSet(name = "CLIENT_CREDENTIALS", title = "Client Credentials")
public abstract class APIClientAuthAction extends EndpointAction {

	@ActionInputParam(required = true, tooltip = "Provide the client id to authenticate to the acronis. E.g. 748fa2a7-9efc-44a3-af3c-9c457e21a920", name = "UC4RB_AC_CLIENT_ID", label = "Client Id")
	private String clientId;

	@ActionInputParam(password = true, required = true, tooltip = "Provide the client secret to authenticate to the acronis. E.g. 7d9691b747c94c09bf4f2c4c4c029040", name = "UC4RB_AC_CLIENT_SECRET", label = "Client Secret")
	private String clientSecret;

	@Override
	public void execute() throws AcronisException {
		validateInputs();
		client.addFilter(new HTTPBasicAuthFilter(clientId, clientSecret));
		executeSpecific();
	}

	private void validateInputs() throws AcronisException {
		if (StringUtils.isEmpty(clientId)) {
			String msg = String.format(Constants.ISEMPTY, "Client Id");
			throw new AcronisException(msg);
		}

		if (StringUtils.isEmpty(clientSecret)) {
			String msg = String.format(Constants.ISEMPTY, "Client Secret");
			throw new AcronisException(msg);
		}
	}

	/**
	 * Method to execute the action.
	 * 
	 * @throws AcronisException
	 */
	protected abstract void executeSpecific() throws AcronisException;
}