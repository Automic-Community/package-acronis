package com.broadcom.helper;

import java.net.URI;

import javax.ws.rs.core.MediaType;

import com.broadcom.util.ConsoleWriter;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class GetUserHelper {

	public static ClientResponse urlCall(String userId, Client client, URI url, String... paths) {
		WebResource webResource = client.resource(url);
		for (int i = 0; i < paths.length; i++) {
			webResource = webResource.path(paths[i]);
		}
		webResource = webResource.path(userId);
		ConsoleWriter.writeln("Calling url: " + webResource.getURI());
		return webResource.accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
	}
}
