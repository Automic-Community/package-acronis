package com.broadcom;

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
import org.apache.commons.lang3.StringUtils;

import javax.json.JsonObject;
import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * This class used to enable the user.
 */
@Action(name = "ENABLE_USER", title = "Enable User", path = "ACRONIS")
@PromptSet(name = "ENABLE_USER", title = "Enable User")
public class EnableUserAction extends AbstractAcronisAction {
    @ActionInputParam(name = "UC4RB_AC_USER_ID", label = "User ID", required = true, tooltip = "Provide the User Id.E"
            + ".g: 9b0df710-fcef-4c09-9c95-831764dc2c99")
    private String userId;

    @ActionInputParam(name = "UC4RB_AC_USER_VERSION", tooltip = "Provide the user current version. E.g: 2", label =
            "Current Version")
    private String oldVersion;

    @ActionOutputParam(name = "UC4RB_AC_NEW_USER_VERSION")
    private long newVersion;

    private boolean enabled;

    @Override
    protected void executeSpecific() throws AcronisException {
        ClientResponse response = null;
        Map<String, Object> request = createRequest();
        if (!enabled) {
            try {
                WebResource webResource = client.resource(url);
                webResource = webResource.path(Constants.API).path(version).path(Constants.USERS).path(userId);
                ConsoleWriter.writeln("Calling url: " + webResource.getURI());
                response = webResource.type(MediaType.APPLICATION_JSON).put(ClientResponse.class, request);
                prepareOutput(CommonUtil.jsonObjectResponse(response.getEntityInputStream()));
            } catch (Exception e) {
                String msg = String.format(Constants.REQ_ERROR_MESSAGE, url);
                LOGGER.warning(e.getMessage());
                throw new AcronisException(msg, e);
            }
        }
    }

    /**
     * Calls the get tenant API to get the current version only if user doesn't
     * provide the current version.
     *
     * @throws AcronisException
     */
    private void getCurrentVersion() throws AcronisException {
        try {
            WebResource webResource = client.resource(url);
            webResource = webResource.path(Constants.API).path(version).path(Constants.USERS).path(userId);
            LOGGER.info("Calling url: " + webResource.getURI());
            ClientResponse clientResponse = webResource.type(MediaType.APPLICATION_JSON).get(ClientResponse.class);
            JsonObject jsonResponseObject = CommonUtil.jsonObjectResponse(clientResponse.getEntityInputStream());
            Long responseVersion = jsonResponseObject.getJsonNumber(Constants.VERSION).longValue();
            if (Objects.nonNull(oldVersion) && !oldVersion.equals(responseVersion)) {
                throw new AcronisException(Constants.TENANT_VERSION_MISMATCH);
            }

            newVersion = responseVersion;
            enabled = jsonResponseObject.getBoolean("enabled");
        } catch (Exception e) {
            String msg = String.format(Constants.REQ_ERROR_MESSAGE, url);
            LOGGER.warning(e.getMessage());
            throw new AcronisException(msg, e);
        }
    }

    private void prepareOutput(JsonObject jsonObjectResponse) {
        // write response to console
        ConsoleWriter.writeln("Response: " + CommonUtil.jsonPrettyPrinting(jsonObjectResponse));
        newVersion = jsonObjectResponse.getJsonNumber(Constants.VERSION).longValue();
    }

    /**
     * Validates the inputs and creates the request
     *
     * @return map with string keys and object type values
     * @throws AcronisException
     */
    private Map<String, Object> createRequest() throws AcronisException {

        if (StringUtils.isEmpty(userId)) {
            String msg = String.format(Constants.ISEMPTY, "User Id");
            throw new AcronisException(msg);
        }

        if (StringUtils.isNotBlank(oldVersion) && !StringUtils.isNumeric(oldVersion)) {
            String msg = String.format(Constants.INVALID_INPUT_PARAMETER, "Current Version", oldVersion);
            throw new AcronisException(msg);
        }
        getCurrentVersion();
        Map<String, Object> request = new HashMap<>();
        request.put("version", this.newVersion);
        request.put("enabled", true);
        return request;
    }

    @Override
    protected String getActionName() {
        return "Enable User";
    }
	
	public EnableUserAction() {
		setDocumentation("= Action name =\r\n" + 
				"PCK.AUTOMIC_ACRONIS.PUB.ACTION.ENABLE_USER\r\n" + 
				"\r\n" + 
				"= General description =\r\n" + 
				"This action used to enable the user.\r\n" + 
				"\r\n" + 
				"= Inputs =\r\n" + 
				"* User Id                : Provide the Id of the User. E.g. 6f2e420b-bd8c-4ade-b3bb-4942d7c89032\r\n" +
				"  Current Version        : Provide the version for the user that you want to update. E.g. 2\r\n" +
				"\r\n" + 
				"= Failure Conditions =\r\n" + 
				" If the User does not exists with the input User Id\r\n" + 
				"\r\n" + 
				"= Return value =\r\n" + 
				" UC4RB_AC_NEW_USER_VERSION#  : Version of the User.\r\n" + 
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

}
