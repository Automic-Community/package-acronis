package com.broadcom.filter;

import javax.json.JsonObject;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.StringUtils;

import com.broadcom.constants.Constants;
import com.broadcom.exceptions.AcronisRuntimeException;
import com.broadcom.exceptions.NotFoundRuntimeException;
import com.broadcom.util.CommonUtil;
import com.broadcom.util.ConsoleWriter;
import com.sun.jersey.api.client.ClientRequest;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.filter.ClientFilter;

/**
 * This class acts as a filter and intercept the response to validate it.
 *
 */

public class GenericResponseFilter extends ClientFilter {

	private static final int HTTP_SUCCESS_START = 200;
	private static final int HTTP_SUCCESS_END = 299;

	private static final int NOT_FOUND_404 = 404;

	private static final String RESPONSE_CODE = "Response Code [%s]";
	private static final String RESPONSE_MSG = RESPONSE_CODE + " Message : [%s]";

	@Override
	public ClientResponse handle(ClientRequest request) {

		boolean ignoreHttpError = (request.getHeaders().remove(Constants.IGNORE_HTTPERROR) != null);

		ClientResponse response = getNext().handle(request);
		String msg = null;
		if (response.getClientResponseStatus() != null
				&& StringUtils.isNotEmpty(response.getClientResponseStatus().getReasonPhrase())) {
			msg = String.format(RESPONSE_MSG, response.getStatus(),
					response.getClientResponseStatus().getReasonPhrase());
		} else {
			msg = String.format(RESPONSE_CODE, response.getStatus());
		}
		if (!(response.getStatus() >= HTTP_SUCCESS_START && response.getStatus() <= HTTP_SUCCESS_END)) {

			ConsoleWriter.writeln(CommonUtil.formatErrorMessage(msg));
			MediaType contentType = response.getType();
			if (ignoreHttpError) {
				return response;
			} else if (contentType != null && contentType.isCompatible(MediaType.APPLICATION_JSON_TYPE)) {
				JsonObject jsonResponse = CommonUtil.jsonObjectResponse(response.getEntityInputStream());
				String responseMsg = CommonUtil.jsonPrettyPrinting(jsonResponse);
				if (response.getStatus() == NOT_FOUND_404) {
					throw new NotFoundRuntimeException(responseMsg);
				} else {
					throw new AcronisRuntimeException(responseMsg);
				}
			} else {
				String errorMsg = response.getEntity(String.class);
				throw new AcronisRuntimeException(errorMsg);
			}

		} else {
			ConsoleWriter.writeln(msg);
		}
		return response;
	}
}
