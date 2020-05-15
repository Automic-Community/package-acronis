package com.broadcom.helper;

import javax.ws.rs.core.MediaType;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class GetHelper {

	public static ClientResponse urlCall(WebResource webResource) {
		return webResource.accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
	}
}
