package com.edelivery.edeliveryserver.business;
import java.security.SecureRandom;
import java.security.Security;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;










public class SecurityBs {

	
	 // trusting all certificate 
	/* public static void doTrustToCertificates() throws Exception {
	        Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
	        TrustManager[ ] certs = new TrustManager[ ] {
	                new X509TrustManager() {
	                    public X509Certificate[] getAcceptedIssuers() {
	                        return null;
	                    }
	                    public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {}
	                    public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {}
	                }
	        };
	        

	        SSLContext sc = SSLContext.getInstance("SSL");
	        sc.init(null, trustAllCerts, new SecureRandom());
	        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
	        HostnameVerifier hv = new HostnameVerifier() {
	            public boolean verify(String urlHostName, SSLSession session) {
	                if (!urlHostName.equalsIgnoreCase(session.getPeerHost())) {
	                    System.out.println("Warning: URL host '" + urlHostName + "' is different to SSLSession host '" + session.getPeerHost() + "'.");
	                }
	                return true;
	            }
	        };
	        HttpsURLConnection.setDefaultHostnameVerifier(hv);
	    }*/
	
	public static void trust(){
		/*
		SSLContext context = SSLContext.getInstance("SSL");
				context.init(null, new TrustManager[] {
				  new X509TrustManager {
				    void checkClientTrusted(X509Certificate[] chain, String authType) {}
				    void checkServerTrusted(X509Certificate[] chain, String authType) {}
				    void getAcceptedIssuers() { return null; }
				  }
				}, null);
		*/
	}
}
