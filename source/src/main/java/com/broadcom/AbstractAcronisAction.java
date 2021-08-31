package com.broadcom;

import org.apache.commons.lang3.StringUtils;

import com.broadcom.apdk.api.annotations.ActionInputParam;
import com.broadcom.apdk.api.annotations.PromptSet;
import com.broadcom.constants.Constants;
import com.broadcom.exceptions.AcronisException;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;

@PromptSet(name = "ACRONIS_CREDENTIALS", title = "Acronis Credentials")
public abstract class AbstractAcronisAction extends EndpointAction {

	@ActionInputParam(required = true, tooltip = "Provide the username to connect to the acronis. E.g. SP1089871", name = "UC4RB_AC_USERNAME", label = "Username")
	private String username;

	@ActionInputParam(password = true, required = true, tooltip = "Provide the password to connect to the acronis.", name = "UC4RB_AC_PASSWORD", label = "Password")
	private String password;

	@Override
	public void execute() throws AcronisException {
		validateInputs();
		client.addFilter(new HTTPBasicAuthFilter(username, password));
		executeSpecific();
	}

	private void validateInputs() throws AcronisException {
		if (StringUtils.isEmpty(username)) {
			String msg = String.format(Constants.ISEMPTY, "Username");
			throw new AcronisException(msg);
		}

		if (StringUtils.isEmpty(password)) {
			String msg = String.format(Constants.ISEMPTY, "Password");
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