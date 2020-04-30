package com.broadcom;

import com.broadcom.apdk.api.annotations.Action;
import com.broadcom.apdk.api.annotations.ActionInputParam;
import com.broadcom.apdk.api.annotations.ActionOutputParam;
import com.broadcom.constants.Constants.Gender;
import com.broadcom.exceptions.AcronisException;

@Action(name = "DEMO_ACTION", title = "Demo Action", path = "ACRONIS")
public class DemoAction extends AbstractAcronisAction {

	@ActionInputParam(required = true, name = "UC4RB_NAME", tooltip = "tooltip")
	String yourname = "vijendra";

	@ActionInputParam(required = true, password = true, label = "New Password")
	String password;

	@ActionInputParam
	Integer age = 25;

	@ActionInputParam
	Boolean isHealthy = true;

	@ActionInputParam
	Gender gender = Gender.M;

	@ActionOutputParam
	String greeting;

	@ActionOutputParam
	String address;

	@Override
	public void run() {
		greeting = "Hello " + yourname + ", Your age is: " + age;
		LOGGER.info("Assigned \"" + greeting + "\" to output parameter \"greeting\".");
	}

	@Override
	protected void executeSpecific() throws AcronisException {
		// TODO Auto-generated method stub

	}

	@Override
	protected String getActionName() {
		// TODO Auto-generated method stub
		return null;
	}

}
