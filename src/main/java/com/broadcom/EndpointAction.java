package com.broadcom;

import com.broadcom.apdk.api.BaseAction;
import com.broadcom.apdk.api.annotations.ActionInputParam;
import com.broadcom.constants.Constants;

public abstract class EndpointAction extends BaseAction {

	@ActionInputParam(required = true, tooltip = "Provide the endpoint to connect to the acronis. E.g. https://sg-cloud.acronis.com", name = "UC4RB_AC_ENDPOINT", label = "Endpoint")
	protected String endpoint;
	
	@ActionInputParam(required = true, tooltip = "Provide the version for the Acronis API. E.g. 2", name = "UC4RB_AC_API_VERSION", label = "Version")
	protected String version = Constants.ACRONIS_VERSION;
}
