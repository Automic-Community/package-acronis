package com.broadcom;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.json.JsonObject;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.StringUtils;

import com.broadcom.apdk.api.annotations.Action;
import com.broadcom.apdk.api.annotations.ActionInputParam;
import com.broadcom.apdk.api.annotations.ActionOutputParam;
import com.broadcom.constants.Constants;
import com.broadcom.exceptions.AcronisException;
import com.broadcom.util.CommonUtil;
import com.broadcom.util.ConsoleWriter;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

/**
 * This class used to create a user.
 */
@Action(title = "Create User", name = "CREATE_USER", path = "ACRONIS")
public class CreateUserAction extends AbstractAcronisAction {

    @ActionInputParam(name = "UC4RB_AC_TENANT_ID", required = true, tooltip = "Provide the Tenant ID. E.g: "
            + "6f2e420b-bd8c-4ade-b3bb-4942d7c89032", label = "Tenant ID")
    private String tenantId;

    @ActionInputParam(name = "UC4RB_AC_USER_LOGIN", required = true, tooltip =
            "Provide login of the user account. E.g: " + "JohnDoe@gmail.com", label = "Login")
    private String login;

    @ActionInputParam(name = "UC4RB_AC_USER_EMAIL", required = true, tooltip =
            "Provide contact email of the user. E.g: " + "JohnDoe@gmail.com", label = "Email")
    private String email;

    @ActionInputParam(name = "UC4RB_AC_USER_FIRST_NAME", tooltip = "Provide the user first name. E.g: John", label =
            "First Name")
    private String userFirstName;

    @ActionInputParam(name = "UC4RB_AC_USER_LAST_NAME", tooltip = "Provide the user last name. E.g: Doe", label =
            "Last Name")
    private String userLastName;

    @ActionOutputParam(name = "UC4RB_AC_USER_ID")
    private String userId;

    @ActionOutputParam(name = "UC4RB_AC_USER_VERSION")
    private long newVersion;

    @Override
    protected void executeSpecific() throws AcronisException {
        ClientResponse response = null;
        Map<String, Object> request = createRequest();
        try {
            WebResource webResource = client.resource(url);
            webResource = webResource.path(Constants.API).path(version).path(Constants.USERS);
            ConsoleWriter.writeln("Calling url: " + webResource.getURI());
            ConsoleWriter.writeln("Request: " + request);

            response = webResource.type(MediaType.APPLICATION_JSON).post(ClientResponse.class, request);
            prepareOutput(CommonUtil.jsonObjectResponse(response.getEntityInputStream()));

        } catch (Exception e) {
            String msg = String.format(Constants.REQ_ERROR_MESSAGE, url);
            LOGGER.warning(e.getMessage());
            throw new AcronisException(msg, e);
        }
    }

    private void prepareOutput(JsonObject jsonObjectResponse) {
        // write response to console
        ConsoleWriter.writeln("Response: " + CommonUtil.jsonPrettyPrinting(jsonObjectResponse));
        userId = jsonObjectResponse.getString(Constants.USER_ID);
        newVersion = jsonObjectResponse.getJsonNumber(Constants.VERSION).longValue();
    }

    @Override
    protected String getActionName() {
        return "Create User";
    }

    private boolean validateEmail(String email) {
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

        if (StringUtils.isEmpty(tenantId)) {
            String msg = String.format(Constants.ISEMPTY, "Tenant Id");
            throw new AcronisException(msg);
        }

        if (!validateEmail(email)) {
            String msg = String.format(Constants.INVALID_INPUT_PARAMETER, "Email", email);
            throw new AcronisException(msg);
        }

        if (StringUtils.isEmpty(login)) {
            throw new AcronisException(String.format(Constants.ISEMPTY, Constants.LOGIN));
        }

        Map<String, Object> request = new HashMap<>();
        request.put(Constants.TENANT_ID, tenantId);
        request.put(Constants.LOGIN, login);

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

        if (!contactRequest.isEmpty()) {
            request.put("contact", contactRequest);
        }
        return request;
    }

}
