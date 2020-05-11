package com.broadcom;

import com.broadcom.apdk.api.annotations.Action;
import com.broadcom.apdk.api.annotations.ActionInputParam;
import com.broadcom.apdk.api.annotations.ActionOutputParam;
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

/**
 * This class used to enable the user.
 */
@Action(name = "ENABLE_USER", title = "Enable User", path = "ACRONIS")
public class EnableUserAction extends AbstractAcronisAction {
    @ActionInputParam(name = "UC4RB_AC_USER_ID", label = "User ID", required = true, tooltip = "Provide the User Id.E"
            + ".g: 9b0df710-fcef-4c09-9c95-831764dc2c99")
    private String userId;

    @ActionInputParam(name = "UC4RB_AC_USER_VERSION", tooltip = "Provide the user current version. E.g: 2", label =
            "Current Version")
    private String oldVersion;

    @ActionOutputParam(name = "UC4RB_AC_NEW_USER_VERSION")
    private long newVersion;

    @Override
    protected void executeSpecific() throws AcronisException {
        ClientResponse response = null;
        Map<String, Object> request = createRequest();
        try {
            WebResource webResource = client.resource(url);
            webResource = webResource.path(Constants.API).path(version).path(Constants.USERS).path(userId);
            if (StringUtils.isBlank(oldVersion)) {
                this.newVersion = getVersion(webResource);
            } else {
                this.newVersion = Long.parseLong(oldVersion);
            }
            request.put("version", this.newVersion);
            ConsoleWriter.writeln("Received User version: " + newVersion);
            ConsoleWriter.writeln("Calling url: " + webResource.getURI());
            response = webResource.type(MediaType.APPLICATION_JSON).put(ClientResponse.class, request);
            prepareOutput(CommonUtil.jsonObjectResponse(response.getEntityInputStream()));
        } catch (Exception e) {
            String msg = String.format(Constants.REQ_ERROR_MESSAGE, url);
            LOGGER.warning(e.getMessage());
            throw new AcronisException(msg, e);
        }
    }

    private long getVersion(WebResource webResource) {
        ClientResponse response = webResource.type(MediaType.APPLICATION_JSON).get(ClientResponse.class);
        JsonObject jsonResponse = CommonUtil.jsonObjectResponse(response.getEntityInputStream());
        ConsoleWriter.writeln("Response: " + CommonUtil.jsonPrettyPrinting(jsonResponse));
        return jsonResponse.getJsonNumber(Constants.VERSION).longValue();
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

        if (StringUtils.isNoneBlank(oldVersion) && !StringUtils.isNumeric(oldVersion)) {
            String msg = String.format(Constants.INVALID_INPUT_PARAMETER, "Current Version", oldVersion);
            throw new AcronisException(msg);
        }

        Map<String, Object> request = new HashMap<>();
        request.put("enabled", true);
        return request;
    }

    @Override
    protected String getActionName() {
        return "Enable User";
    }

}
