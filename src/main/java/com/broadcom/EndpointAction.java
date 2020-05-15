package com.broadcom;

import java.net.URI;
import java.net.URISyntaxException;

import org.apache.commons.lang3.StringUtils;

import com.broadcom.apdk.api.BaseAction;
import com.broadcom.apdk.api.annotations.ActionInputParam;
import com.broadcom.config.HttpClientConfig;
import com.broadcom.constants.Constants;
import com.broadcom.exceptions.AcronisException;
import com.broadcom.exceptions.AcronisRuntimeException;
import com.broadcom.filter.GenericResponseFilter;
import com.broadcom.util.ConsoleWriter;
import com.sun.jersey.api.client.Client;

public abstract class EndpointAction extends BaseAction {

	@ActionInputParam(required = true, tooltip = "Provide the endpoint to connect to the acronis. E.g. https://sg-cloud.acronis.com", name = "UC4RB_AC_ENDPOINT", label = "Endpoint")
	protected String endpoint;

	@ActionInputParam(required = true, tooltip = "Provide the version for the Acronis API. E.g. 2", name = "UC4RB_AC_API_VERSION", label = "Version")
	protected String version = Constants.ACRONIS_VERSION;

	/**
	 * Client
	 */
	protected transient Client client;
	protected URI url;

	@Override
	public void run() {
		start();
	}

	public void start() {
		try {
			prepareInputParameters();
			client = HttpClientConfig.getClient(url.getScheme(), true);
			client.addFilter(new GenericResponseFilter());
			execute();
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

		if (StringUtils.isEmpty(version)) {
			String msg = String.format(Constants.ISEMPTY, "Version");
			throw new AcronisException(msg);
		}
	}

	/**
	 * Method to execute the action.
	 * 
	 * @throws AcronisException
	 */
	protected abstract void execute() throws AcronisException;

	protected abstract String getActionName();
}
