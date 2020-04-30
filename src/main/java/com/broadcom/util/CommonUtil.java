package com.broadcom.util;

import java.io.InputStream;

import com.broadcom.constants.Constants;
import com.broadcom.exceptions.AcronisException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.ClientResponse;

/**
 * Utility class
 * 
 */
public final class CommonUtil {

	private CommonUtil() {
	}

	public static JsonNode getRootNode(ClientResponse response) throws AcronisException {
		InputStream is = response.getEntityInputStream();
		ObjectMapper mapper = new ObjectMapper();
		JsonNode rootNode;
		try {
			rootNode = mapper.readTree(is);
		} catch (Exception e) {
			String msg = String.format(Constants.RES_ERROR_MESSAGE, e.getMessage());
			throw new AcronisException(msg, e);
		}
		return rootNode;
	}

	
	
	 
}