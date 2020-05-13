package com.broadcom;

import java.net.URI;
import java.net.URISyntaxException;

import org.apache.commons.lang3.StringUtils;

import com.broadcom.apdk.api.annotations.ActionInputParam;
import com.broadcom.config.HttpClientConfig;
import com.broadcom.constants.Constants;
import com.broadcom.exceptions.AcronisException;
import com.broadcom.exceptions.AcronisRuntimeException;
import com.broadcom.filter.GenericResponseFilter;
import com.broadcom.util.ConsoleWriter;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;

public abstract class APIClientAuthAction extends EndpointAction {

	@ActionInputParam(required = true, tooltip = "Provide the client id to authenticate to the acronis. E.g. 748fa2a7-9efc-44a3-af3c-9c457e21a920", name = "UC4RB_AC_CLIENT_ID", label = "Client Id")
	private String clientId;

	@ActionInputParam(password = true, required = true, tooltip = "Provide the client secret to authenticate to the acronis. E.g. 7d9691b747c94c09bf4f2c4c4c029040", name = "UC4RB_AC_CLIENT_SECRET", label = "Client Secret")
	private String clientSecret;

	/**
	 * Client
	 */
	protected transient Client client;
	protected URI url;

	@Override
	public void run() {
		execute();
	}

	public void execute() {
		try {
			prepareInputParameters();
			client = HttpClientConfig.getClient(url.getScheme(), true);
			client.addFilter(new HTTPBasicAuthFilter(clientId, clientSecret));
			client.addFilter(new GenericResponseFilter());
			executeSpecific();
		} catch (Exception exception) {
			String errorMsg = String.format("Error occured during execution of action [%s]", getActionName());
			LOGGER.warning("Exception: " + exception.getMessage());
			throw new AcronisRuntimeException(errorMsg, exception);
		} finally {
			ConsoleWriter.flush();
			if (client != null) {
				client.destroy();
			}
		}
	}

	private void prepareInputParameters() throws AcronisException {
		validateInputs();
		try {
			url = new URI(endpoint);
		} catch (URISyntaxException e) {
			String msg = String.format(Constants.INVALID_INPUT_PARAMETER, "Acronis REST API Endpoint", endpoint);
			throw new AcronisException(msg);
		}
	}

	private void validateInputs() throws AcronisException {

		if (StringUtils.isEmpty(endpoint)) {
			String msg = String.format(Constants.ISEMPTY, "Endpoint");
			throw new AcronisException(msg);
		}

		if (StringUtils.isEmpty(clientId)) {
			String msg = String.format(Constants.ISEMPTY, "Client Id");
			throw new AcronisException(msg);
		}

		if (StringUtils.isEmpty(version)) {
			String msg = String.format(Constants.ISEMPTY, "Version");
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

	protected abstract String getActionName();
}
