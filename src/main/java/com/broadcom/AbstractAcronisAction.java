package com.broadcom;

import java.net.URI;
import java.net.URISyntaxException;

import org.apache.commons.lang3.StringUtils;

import com.broadcom.apdk.api.BaseAction;
import com.broadcom.apdk.api.annotations.ActionInputParam;
import com.broadcom.config.HttpClientConfig;
import com.broadcom.constants.Constants;
import com.broadcom.exceptions.AcronisException;
import com.broadcom.filter.GenericResponseFilter;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;

public abstract class AbstractAcronisAction extends BaseAction {

	@ActionInputParam(required = true, tooltip = "", name = "UC4RB_AC_ENDPOINT", label = "Endpoint")
	private String endpoint;

	@ActionInputParam(required = true, tooltip = "", name = "UC4RB_AC_VERSION", label = "Version")
	protected String version = Constants.ACRONIS_VERSION;

	@ActionInputParam(required = true, tooltip = "", name = "UC4RB_AC_USERNAME", label = "Username")
	private String username;

	@ActionInputParam(password = true, required = true, tooltip = "", name = "UC4RB_AC_PASSWORD", label = "Password")
	private String password;

	/**
	 * Client
	 */
	protected transient Client client;
	protected URI url;

	public void execute() {
		try {
			prepareInputParameters();
			client = HttpClientConfig.getClient(url.getScheme(), true);
			client.addFilter(new HTTPBasicAuthFilter(username, password));
			client.addFilter(new GenericResponseFilter());
			executeSpecific();
		} catch (Exception exception) {
			String errorMsg = String.format("Error occured during execution of action [%s]", getActionName());
			// _log.error(errorMsg, exception);
			// return new ActionResult(false, "The " + getActionName() + " failed \n" +
			// exception.getMessage(), exception);
		} finally {
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
			String msg = String.format(Constants.ISEMPTY, "Acronis REST API Endpoint");
			// _log.error(msg);
			throw new AcronisException(msg);
		}

		if (StringUtils.isEmpty(username)) {
			String msg = String.format(Constants.ISEMPTY, "Username");
			// _log.error(msg);
			throw new AcronisException(msg);
		}

		if (StringUtils.isEmpty(password)) {
			String msg = String.format(Constants.ISEMPTY, "password");
			// _log.error(msg);
			throw new AcronisException(msg);
		}

	}

	/**
	 * Method to execute the action.
	 * 
	 * @throws AutomicException
	 */
	protected abstract void executeSpecific() throws AcronisException;

	protected abstract String getActionName();
}
