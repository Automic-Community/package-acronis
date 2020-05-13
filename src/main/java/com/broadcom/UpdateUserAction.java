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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This action class used to update the existing user contact info.
 */
@Action(name = "UPDATE_USER", title = "Update User Contact Info", path = "ACRONIS")
public class UpdateUserAction extends AbstractAcronisAction {

    @ActionInputParam(name = "UC4RB_AC_USER_ID", required = true, tooltip = "Provide the User ID. E.g: "
            + "6f2e420b-bd8c-4ade-b3bb-4942d7c89032", label = "User ID")
    private String userId;

    @ActionInputParam(name = "UC4RB_AC_USER_VERSION", tooltip = "Provide the user current version. E.g: 2", label =
            "Current Version")
    private String oldVersion;

    @ActionInputParam(name = "UC4RB_AC_USER_EMAIL", tooltip = "Provide contact email of the user. E.g: "
            + "johndoe@newmail.net", label = "Email")
    private String email;

    @ActionInputParam(name = "UC4RB_AC_USER_FIRST_NAME", tooltip = "Provide the user fisrt name. E.g: foo", label =
            "First Name")
    private String userFirstName;

    @ActionInputParam(name = "UC4RB_AC_USER_LAST_NAME", tooltip = "Provide the user last name. E.g: bar", label =
            "Last Name")
    private String userLastName;

    @ActionOutputParam(name = "UC4RB_AC_NEW_USER_VERSION")
    private long newVersion;

    /**
     * <ul>This method perform the tasks:
     * <li>Call the Acronis update api.</li>
     * <li>Update the user contact info.</li>
     * <li>Throws the exception in case of error./li>
     * </ul>
     *
     * @throws AcronisException
     */
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
        LOGGER.info("Response: " + CommonUtil.jsonPrettyPrinting(jsonResponse));
        return jsonResponse.getJsonNumber(Constants.VERSION).longValue();
    }

    private void prepareOutput(JsonObject jsonObjectResponse) {
        // write response to console
        ConsoleWriter.writeln("Response: " + CommonUtil.jsonPrettyPrinting(jsonObjectResponse));
        newVersion = jsonObjectResponse.getJsonNumber(Constants.VERSION).longValue();
    }

    /**
     * This method used to validate the email.
     *
     * @param email
     * @return
     */
    private boolean validateEmail(String email) {
        if (StringUtils.isBlank(email)) {
            return true;
        }
        Pattern pattern = Pattern.compile(Constants.EMAIL_VALIATION_REGEX);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
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

        if (!validateEmail(email)) {
            String msg = String.format(Constants.INVALID_INPUT_PARAMETER, "Email", email);
            throw new AcronisException(msg);
        }

        if (StringUtils.isNotBlank(oldVersion) && !StringUtils.isNumeric(oldVersion)) {
            String msg = String.format(Constants.INVALID_INPUT_PARAMETER, "Current Version", oldVersion);
            throw new AcronisException(msg);
        }

        Map<String, Object> request = new HashMap<>();
        Map<String, Object> contactRequest = new HashMap<>();
        if (StringUtils.isNotEmpty(userFirstName)) {
            contactRequest.put(Constants.FIRST_NAME, userFirstName);
        }
        if (StringUtils.isNotEmpty(userLastName)) {
            contactRequest.put(Constants.LAST_NAME, userLastName);
        }
        if (StringUtils.isNotEmpty(email)) {
            contactRequest.put("email", email);
        }

        if (contactRequest.isEmpty()) {
            String msg = String.format(Constants.BLANK_INPUT_PARAMETER_ERROR_MESSAGE, "Email", "First Name",
                    "Last Name");
            throw new AcronisException(msg);
        } else {
            request.put("contact", contactRequest);
        }
        return request;
    }

    @Override
    protected String getActionName() {
        return "Update User Contact Info";
    }

}
