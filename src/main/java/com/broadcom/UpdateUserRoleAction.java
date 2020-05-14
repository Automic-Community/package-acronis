package com.broadcom;

import com.broadcom.apdk.api.annotations.Action;
import com.broadcom.apdk.api.annotations.ActionInputParam;
import com.broadcom.constants.Constants;
import com.broadcom.exceptions.AcronisException;
import com.broadcom.util.CommonUtil;
import com.broadcom.util.ConsoleWriter;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import org.apache.commons.lang3.StringUtils;

import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.Map;

/**
 * This class used to update or assign a role to user.
 */
@Action(name = "UPDATE_USER_ROLE", title = "Update User Role", path = "ACRONIS")
public class UpdateUserRoleAction extends AbstractAcronisAction {

    @ActionInputParam(required = true, name = "UC4RB_AC_USER_ID", tooltip = "Provide the user id to fetch the details"
            + ". E.g. d540ac7f-2e8b-4451-a1cc-18ee9586af69", label = "User Id")
    private String userId;

    @ActionInputParam(name = "UC4RB_AC_ID", required = true, tooltip = "Provide the any valid UUID string. E.g: User ID"
            + "6f2e420b-bd8c-4ade-b3bb-4942d7c89032", label = "ID")
    private String Id;

    @ActionInputParam(name = "UC4RB_AC_ISSUER_ID", required = true, tooltip =
            "Provide the any valid UUID string. E.g: User ID"
                    + "6f2e420b-bd8c-4ade-b3bb-4942d7c89032", label = "Issuer ID")
    private String issuerId;

    @ActionInputParam(name = "UC4RB_AC_TENANT_ID", required = true, tooltip =
            "Provide the UUID of the tenant. E.g: User ID"
                    + "6f2e420b-bd8c-4ade-b3bb-4942d7c89032", label = "Tenant ID")
    private String tenantId;

    @ActionInputParam(name = "UC4RB_AC_TRUSTEE_ID", required = true, tooltip =
            "Provide the UUID of the user account. E.g: User ID"
                    + "6f2e420b-bd8c-4ade-b3bb-4942d7c89032", label = "Trustee ID")
    private String trusteeId;

    @ActionInputParam(name = "UC4RB_AC_ROLE_ID", required = true, tooltip = "Provide the role to be assigned. E.g: "
            + "Compay admin", label = "Role ID")
    Constants.Role roleId = Constants.Role.company_admin;

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
            response = webResource.type(MediaType.APPLICATION_JSON).post(ClientResponse.class, request);
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
        if (StringUtils.isBlank(Id)) {
            String msg = String.format(Constants.ISEMPTY, "Id");
            throw new AcronisException(msg);
        }
        if (StringUtils.isBlank(issuerId)) {
            String msg = String.format(Constants.ISEMPTY, "Issuer ID");
            throw new AcronisException(msg);
        }

        if (StringUtils.isBlank(tenantId)) {
            String msg = String.format(Constants.ISEMPTY, "Tenant ID");
            throw new AcronisException(msg);
        }

        if (StringUtils.isBlank(trusteeId)) {
            String msg = String.format(Constants.ISEMPTY, "Trustee ID");
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
        request.put("id", Id);
        request.put("issuer_id", issuerId);
        request.put("tenant_id", tenantId);
        request.put("trustee_id", trusteeId);
        request.put("trustee_type", "user");
        request.put("role_id", roleId.toString());
        request.put("version", 0);
        return request;
    }

    @Override
    protected String getActionName() {
        return "Update User Role";
    }

}
