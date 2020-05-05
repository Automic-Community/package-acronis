package com.broadcom;

import com.broadcom.apdk.api.annotations.Action;
import com.broadcom.apdk.api.annotations.ActionInputParam;
import com.broadcom.apdk.api.annotations.ActionOutputParam;
import com.broadcom.constants.Constants;
import com.broadcom.exceptions.AcronisException;
import com.broadcom.util.CommonUtil;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import javax.json.JsonObject;
import javax.ws.rs.core.MediaType;
import java.util.logging.Level;

/**
 * This class use to fetch the Customer Tenant info by using tenant id.
 */
@Action(name = "GET_CUSTOMER_TENANT", title = "Get Customer Tenant", path = "ACRONIS")
public class GetCustomerTenantAction extends AbstractAcronisAction {

    /** customerTenantId. */
    @ActionInputParam(name = "UC4RB_AC_CUSTOMER_TENANT_ID", label = "Customer Tenant Id", required = true, tooltip =
            "Provide the customer tenant id.E.g: 6f2e420b-bd8c-4ade-b3bb-4942d7c89032")
    String customerTenantId;

    /** tenantName. */
    @ActionOutputParam(name = "UC4RB_AC_TENANT_NAME")
    String tenantName;

    /** tenantVersion. */
    @ActionOutputParam(name = "UC4RB_AC_TENANT_VERSION")
    Integer tenantVersion;

    /** tenantEnabled. */
    @ActionOutputParam(name = "UC4RB_AC_TENANT_ENABLED")
    Boolean tenantEnabled;

    /** tenantParentId. */
    @ActionOutputParam(name = "UC4RB_AC_TENANT_PARENT_ID")
    String tenantParentId;

    /** tenantKind. */
    @ActionOutputParam(name = "UC4RB_AC_TENANT_KIND")
    String tenantKind;

    /**
     * This Method used to perform Get Customer Tenant action specific operations.
     *
     * @throws AcronisException
     */
    @Override
    protected void executeSpecific() throws AcronisException {
        ClientResponse response = null;
        try {
            WebResource webResource = client.resource(url);
            webResource = webResource.path("api").path(version).path("tenants").path(customerTenantId);
            LOGGER.info("Calling url: " + webResource.getURI());
            webResource.accept(MediaType.APPLICATION_JSON);
            response = webResource.get(ClientResponse.class);
            prepareOutput(CommonUtil.jsonObjectResponse(response.getEntityInputStream()));
        } catch (Exception ex) {
            String msg = String.format(Constants.REQ_ERROR_MESSAGE, url, ex.getMessage());
            LOGGER.log(Level.SEVERE, ex.toString(), ex);
            // throw new AcronisException(msg, e);
        }
    }

    private void prepareOutput(JsonObject jsonObjectResponse) {
        // write response to console
        System.out.println(CommonUtil.jsonPrettyPrinting(jsonObjectResponse));
        tenantName = jsonObjectResponse.getString("name");
        tenantParentId = jsonObjectResponse.getString("parent_id");
        tenantEnabled = jsonObjectResponse.getBoolean("enabled");
        tenantKind = jsonObjectResponse.getString("kind");
        tenantVersion = jsonObjectResponse.getInt("version");
    }

    /**
     * @return Action Name
     */
    @Override
    protected String getActionName() {
        return "Get Customer Tenant";
    }

}
