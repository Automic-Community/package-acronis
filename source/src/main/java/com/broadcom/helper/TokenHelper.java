package com.broadcom.helper;

import javax.json.JsonObject;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import com.broadcom.util.CommonUtil;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;

/**
 * Helper class for getting the token.
 * 
 * @author vishalkumar03
 *
 */
public class TokenHelper {

	/**
	 * Get the token from the response.
	 * 
	 * @param webResource
	 * @return string type access token
	 */
	public static String getToken(WebResource webResource) {
		ClientResponse clientResponse = urlCall(webResource);
		JsonObject jsonObject = CommonUtil.jsonObjectResponse(clientResponse.getEntityInputStream());
		return jsonObject.getString("access_token");
	}

	/**
	 * Calling the get token url.
	 * 
	 * @param webResource
	 * @return client response object
	 */
	private static ClientResponse urlCall(WebResource webResource) {
		return webResource.accept(MediaType.MULTIPART_FORM_DATA_TYPE, MediaType.APPLICATION_JSON_TYPE)
				.post(ClientResponse.class, createRequest());
	}

	/**
	 * Create the request in form data type.
	 * 
	 * @return multivalued map with the data
	 */
	private static MultivaluedMap<String, String> createRequest() {
		MultivaluedMap<String, String> request = new MultivaluedMapImpl();
		request.add("grant_type", "client_credentials");
		return request;
	}
}
