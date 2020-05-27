package com.broadcom;

import com.broadcom.apdk.api.annotations.Action;
import com.broadcom.apdk.api.annotations.ActionInputParam;
import com.broadcom.constants.Constants;
import com.broadcom.constants.Constants.Role;
import com.broadcom.exceptions.AcronisException;
import com.broadcom.util.CommonUtil;
import com.broadcom.util.ConsoleWriter;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import org.apache.commons.lang3.StringUtils;

import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class used to update or assign a role to user.
 */
@Action(name = "UPDATE_USER_ROLE", title = "Update User Role", path = "ACRONIS")
public class UpdateUserRoleAction extends AbstractAcronisAction {

    @ActionInputParam(required = true, name = "UC4RB_AC_USER_ID", tooltip = "Provide the user id to fetch the details"
            + ". E.g. d540ac7f-2e8b-4451-a1cc-18ee9586af69", label = "User Id")
    private String userId;

    @ActionInputParam(name = "UC4RB_AC_ROLE_ID", tooltip = "Provide the role to be assigned. E.g: "
            + "Company admin", label = "Role ID")
    Role roleId = Role.company_admin;

    @Override
    protected void executeSpecific() throws AcronisException {
        Map<String, Object> request = createRequest();
        ClientResponse response = null;
        try {
            WebResource webResource = client.resource(url);
            webResource = webResource.path(Constants.API).path(version).path(Constants.USERS).path(userId).path(
                    Constants.ACCESS_POLICIES);
            LOGGER.info("Calling url: " + webResource.getURI());
            ConsoleWriter.writeln("Calling url: " + webResource.getURI());
            ConsoleWriter.writeln("Request Body: " + request);
            response = webResource.type(MediaType.APPLICATION_JSON).put(ClientResponse.class, request);
            LOGGER.info("Response: " + CommonUtil.jsonObjectResponse(response.getEntityInputStream()));
        } catch (Exception e) {
            String msg = String.format(Constants.REQ_ERROR_MESSAGE, url);
            LOGGER.warning(e.getMessage());
            throw new AcronisException(msg, e);
        }
    }

    /**
     * This method validate the all required fields.
     *
     * @throws AcronisException
     */
    private void validateInputs() throws AcronisException {

        if (StringUtils.isBlank(userId)) {
            String msg = String.format(Constants.ISEMPTY, "User Id");
            throw new AcronisException(msg);
        }

        if (roleId == null) {
            String msg = String.format(Constants.ISEMPTY, "Role");
            throw new AcronisException(msg);
        }

    }

    /**
     * This method create the request body.
     *
     * @return request body
     * @throws AcronisException
     */
    private Map<String, Object> createRequest() throws AcronisException {
        validateInputs();
        Map<String, Object> request = new HashMap<>();
        request.put("id", userId);
        request.put("issuer_id", userId);
        request.put("tenant_id", userId);
        request.put("trustee_id", userId);
        request.put("trustee_type", "user");
        request.put("role_id", roleId.toString());
        request.put("version", 0);

        List<Map<String, Object>> itemList = new ArrayList<>();
        itemList.add(request);
        Map<String, Object> req = new HashMap<>();
        req.put("items", itemList);
        return req;
    }

    @Override
    protected String getActionName() {
        return "Update User Role";
    }

}
