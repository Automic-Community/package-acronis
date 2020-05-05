package com.broadcom;

import com.broadcom.exceptions.AcronisException;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import javax.ws.rs.core.MediaType;
import java.io.ByteArrayInputStream;
import java.net.URI;

/**
 * This class Test the functionality of {@link GetCustomerTenantAction}
 */
public class GetCustomerTenantActionTest {
    private Client client;
    private WebResource webResource;
    private ClientResponse response;
    private WebResource.Builder builder;

    @InjectMocks
    private GetCustomerTenantAction customerTenantAction;

    @BeforeEach
    public void setupMock() {
        client = Mockito.mock(Client.class);
        webResource = Mockito.mock(WebResource.class);
        response = Mockito.mock(ClientResponse.class);
        builder = Mockito.mock(WebResource.Builder.class);
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void executeSpecificTest() throws AcronisException {
        Mockito.when(client.resource(Mockito.any(URI.class))).thenReturn(webResource);
        Mockito.when(webResource.path(Mockito.anyString())).thenReturn(webResource);
        Mockito.when(webResource.accept(MediaType.APPLICATION_JSON)).thenReturn(builder);
        Mockito.when(webResource.get(ClientResponse.class)).thenReturn(response);
        Mockito.when(response.getEntityInputStream()).thenReturn(new ByteArrayInputStream(mockJsonData().getBytes()));
        customerTenantAction.customerTenantId = "6f2e420b-bd8c-4ade-b3bb-4942d7c89032";
        customerTenantAction.executeSpecific();

        Mockito.verify(client, Mockito.times(1)).resource(Mockito.any(URI.class));
        Mockito.verify(webResource, Mockito.times(1)).path("api");
        Mockito.verify(webResource, Mockito.times(1)).path("tenants");
        Mockito.verify(webResource, Mockito.times(1)).path("6f2e420b-bd8c-4ade-b3bb-4942d7c89032");
        Mockito.verify(webResource, Mockito.times(1)).path("2");
        Mockito.verify(webResource, Mockito.times(4)).path(Mockito.anyString());
        Mockito.verify(webResource, Mockito.times(1)).accept(MediaType.APPLICATION_JSON);
        Mockito.verify(webResource, Mockito.times(1)).get(ClientResponse.class);

        Mockito.eq(customerTenantAction.tenantEnabled);
        Assertions.assertEquals("cust001", customerTenantAction.tenantName);
        Assertions.assertEquals(1, customerTenantAction.tenantVersion);
        Assertions.assertEquals("79100dc2-757a-4261-a9a8-dac20a112e00", customerTenantAction.tenantParentId);
        Assertions.assertEquals("customer", customerTenantAction.tenantKind);

    }

    private String mockJsonData() {
        return "{\n" + "    \"enabled\": true,\n" + "    \"brand_id\": 1,\n"
                + "    \"id\": \"6f2e420b-bd8c-4ade-b3bb-4942d7c89032\",\n"
                + "    \"brand_uuid\": \"774a58d7-6ffd-11ea-b46d-001c42cdee0a\",\n" + "    \"internal_tag\": null,\n"
                + "    \"name\": \"cust001\",\n" + "    \"version\": 1,\n" + "    \"customer_id\": null,\n"
                + "    \"ancestral_access\": true,\n" + "    \"customer_type\": \"default\",\n" + "    \"contact\": {\n"
                + "        \"state\": null,\n" + "        \"city\": null,\n" + "        \"zipcode\": null,\n"
                + "        \"address2\": null,\n" + "        \"phone\": null,\n" + "        \"address1\": null,\n"
                + "        \"country\": null,\n" + "        \"firstname\": \"\",\n" + "        \"lastname\": \"\",\n"
                + "        \"email\": \"\"\n" + "    },\n" + "    \"owner_id\": null,\n"
                + "    \"language\": \"en-US\",\n" + "    \"update_lock\": {\n" + "        \"enabled\": false,\n"
                + "        \"owner_id\": null\n" + "    },\n"
                + "    \"parent_id\": \"79100dc2-757a-4261-a9a8-dac20a112e00\",\n"
                + "    \"default_idp_id\": \"11111111-1111-1111-1111-111111111111\",\n"
                + "    \"kind\": \"customer\",\n" + "    \"has_children\": true\n" + "}";
    }

}
