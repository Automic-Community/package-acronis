package com.broadcom.helper;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.core.MediaType;

import com.broadcom.constants.Constants;
import com.broadcom.exceptions.AcronisException;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class DisableHelper {

	public static ClientResponse urlCall(Long currentVersion, WebResource webResource) {
		return webResource.type(MediaType.APPLICATION_JSON).put(ClientResponse.class, createRequest(currentVersion));
	}

	/**
	 * Validates the inputs and creates the request
	 * 
	 * @return map with string keys and object type values
	 * @throws AcronisException
	 */
	private static Map<String, Object> createRequest(Long currentVersion) {

		Map<String, Object> request = new HashMap<>();
		request.put(Constants.VERSION, currentVersion);
		request.put("enabled", false);

		return request;
	}
}
