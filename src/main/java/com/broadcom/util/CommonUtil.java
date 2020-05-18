package com.broadcom.util;

import java.io.InputStream;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonWriter;
import javax.json.JsonWriterFactory;
import javax.json.stream.JsonGenerator;

/**
 * Utility class
 * 
 */
public final class CommonUtil {

	private CommonUtil() {
	}

	/**
	 * Method to format error message in the format "ERROR | message"
	 *
	 * @param message
	 * @return formatted message
	 */
	public static final String formatErrorMessage(final String message) {
		final StringBuilder sb = new StringBuilder();
		sb.append("ERROR").append(" | ").append(message);
		return sb.toString();
	}

	/**
	 * Method to convert a stream into Json object
	 * 
	 * @param is input stream
	 * @return {@link JsonObject}
	 */
	public static final JsonObject jsonObjectResponse(InputStream is) {
		return Json.createReader(is).readObject();

	}

	/**
	 * Method to convert a stream into JSON Array
	 * 
	 * @param is input stream
	 * @return {@link JsonArray}
	 */
	public static final JsonArray jsonArrayResponse(InputStream is) {
		return Json.createReader(is).readArray();

	}

	/**
	 * Method to beautify the json and write on the console
	 * 
	 * @param jsonObj
	 */
	public static final String jsonPrettyPrinting(JsonObject jsonObj) {
		StringWriter stringWriter = new StringWriter();

		Map<String, Boolean> config = new HashMap<>();
		config.put(JsonGenerator.PRETTY_PRINTING, true);

		JsonWriterFactory writerFactory = Json.createWriterFactory(config);
		JsonWriter jsonWriter = writerFactory.createWriter(stringWriter);
		jsonWriter.writeObject(jsonObj);
		jsonWriter.close();

		return stringWriter.toString();
	}

	/**
	 * Method to beautify the json and write on the console
	 * 
	 * @param jsonObj
	 */
	public static final String jsonPrettyPrinting(JsonArray jsonObj) {
		StringWriter stringWriter = new StringWriter();

		Map<String, Boolean> config = new HashMap<>();
		config.put(JsonGenerator.PRETTY_PRINTING, true);

		JsonWriterFactory writerFactory = Json.createWriterFactory(config);
		JsonWriter jsonWriter = writerFactory.createWriter(stringWriter);
		jsonWriter.writeArray(jsonObj);
		jsonWriter.close();

		return stringWriter.toString();
	}
}