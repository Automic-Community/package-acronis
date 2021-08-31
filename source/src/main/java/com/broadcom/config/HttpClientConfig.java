package com.broadcom.config;

/**
 * This class is used to create HTTP Client using specified input parameters. Subsequently client can be used to invoke
 * HTTP operations on resources.
 */

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import com.broadcom.constants.Constants;
import com.broadcom.exceptions.AcronisException;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.client.urlconnection.HTTPSProperties;

/**
 * This class is used to instantiate HTTP Client required by action(s).
 *
 */
public final class HttpClientConfig {

	private HttpClientConfig() {
	}

	public static Client getClient(String protocol, boolean skipSSLValidation) throws AcronisException {
		ClientConfig config = new DefaultClientConfig();
		if (Constants.HTTPS.equalsIgnoreCase(protocol) && skipSSLValidation) {
			config.getProperties().put(HTTPSProperties.PROPERTY_HTTPS_PROPERTIES, skipValidation());
		}
		config.getProperties().put(ClientConfig.PROPERTY_FOLLOW_REDIRECTS, true);
		return Client.create(config);
	}

	private static HTTPSProperties skipValidation() throws AcronisException {

		TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
			public java.security.cert.X509Certificate[] getAcceptedIssuers() {
				return null;
			}

			public void checkClientTrusted(X509Certificate[] certs, String authType) {
			}

			public void checkServerTrusted(X509Certificate[] certs, String authType) {
			}
		} };

		// Create all-trusting host name verifier
		HostnameVerifier allHostsValid = new HostnameVerifier() {
			public boolean verify(String hostname, SSLSession session) {
				return true;
			}
		};
		// Install the all-trusting trust manager
		try {
			SSLContext sc = SSLContext.getInstance("SSL");
			sc.init(null, trustAllCerts, new java.security.SecureRandom());
			HTTPSProperties props = new HTTPSProperties(allHostsValid, sc);
			return props;
		} catch (KeyManagementException | NoSuchAlgorithmException e) {
			// log.error(e);
			throw new AcronisException("Error skipping the certificate validation");
		}
	}

}
