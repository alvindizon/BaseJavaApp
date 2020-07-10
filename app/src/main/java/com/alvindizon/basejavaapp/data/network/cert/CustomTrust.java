package com.alvindizon.basejavaapp.data.network.cert;

import android.content.Context;

import androidx.annotation.RawRes;

import com.alvindizon.basejavaapp.BuildConfig;

import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Collection;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class CustomTrust {

    private final Context context;
    private SSLSocketFactory sslSocketFactory;
    private X509TrustManager trustManager;
    private HostnameVerifier hostnameVerifier;
    private KeyStore keyStore;
    private int keyStoreIndex = 0;

    public CustomTrust(Context context) {
        this.context = context;
    }


    public SSLSocketFactory getSslSocketFactory() {
        return sslSocketFactory;
    }

    public X509TrustManager getTrustManager() {
        return trustManager;
    }

    public HostnameVerifier getHostnameVerifier() {
        return hostnameVerifier;
    }

    /**
     * This method builds a trust manager that trusts the default CA certs loaded + additional certs that are specified.
     * Based on https://github.com/square/okhttp/blob/master/samples/guide/src/main/java/okhttp3/recipes/CustomTrust.java
     *
     * @param rawResId Resource ID of PEM files (stored in raw folder)  that will be added to list of trusted certificates.
     */
    public void addCertificates(@RawRes int... rawResId) {
        try {
            // Put the certificates from the specified file to the key store.
            for (int fileName : rawResId) {
                try (InputStream inputStream = context.getResources().openRawResource(fileName)) {
                    addCertificateFromStream(inputStream);
                } catch (Exception e) {
                    String message = "Error adding certificate to keystore.";
                    e.printStackTrace();
                    throw new RuntimeException(message);
                }
            }

            // add certificate to existing list
            ((CompositeTrustManager) trustManager).addTrustManager(keyStore);

            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, new TrustManager[]{trustManager}, null);
            sslSocketFactory = sslContext.getSocketFactory();
            // do not verify hostname
            hostnameVerifier = (hostname, session) -> true;

        } catch (Exception e) {
            String message = "Error during creation of trust manager";
            e.printStackTrace();
            throw new RuntimeException(message);
        }
    }

    private void addCertificateFromStream(InputStream in)
            throws GeneralSecurityException {
        CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
        Collection<? extends Certificate> certificates = certificateFactory.generateCertificates(in);
        if (certificates.isEmpty()) {
            throw new IllegalArgumentException("expected non-empty set of trusted certificates");
        }

        for (Certificate certificate : certificates) {
            String certificateAlias = Integer.toString(keyStoreIndex++);
            keyStore.setCertificateEntry(certificateAlias, certificate);
        }
    }

    private KeyStore newEmptyKeyStore(char[] password) throws GeneralSecurityException {
        try {
            String keyStoreType = KeyStore.getDefaultType();

            KeyStore keyStore = KeyStore.getInstance(keyStoreType);
            keyStore.load(null, password);
            return keyStore;
        } catch (IOException e) {
            throw new AssertionError(e);
        }
    }

    /**
     * This method builds a trust manager that trusts all certificates, and a hostname verifier that
     * does not verify if the server name listed by the certificate matches the actual hostname returned by the host.
     * As its name suggests, using this is unsafe and should only be used for debugging purposes.
     */
    public void buildUnsafeCustomTrust() {
        TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager() {
                    @Override
                    public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                    }

                    @Override
                    public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                    }

                    @Override
                    public X509Certificate[] getAcceptedIssuers() {
                        return new X509Certificate[]{};
                    }
                }
        };
        SSLContext trustAllSslContext;
        try {
            trustAllSslContext = SSLContext.getInstance("SSL");
            trustAllSslContext.init(null, trustAllCerts, new SecureRandom());
        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            throw new RuntimeException(e);
        }
        sslSocketFactory = trustAllSslContext.getSocketFactory();
        trustManager = (X509TrustManager) trustAllCerts[0];
        // do not verify hostname
        hostnameVerifier = (hostname, session) -> true;
    }

    /**
     *  Initialize default sslSocketFactory and trust manager (includes default certificates)
     */
    public void initializeDefaultCerts() {
        try {
            char[] password = BuildConfig.CustomTrustKeystorePwd.toCharArray();
            if (keyStore == null) {
                keyStore = newEmptyKeyStore(password);
            }

            // Use keystore to build an X509 trust manager.
            KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(
                    KeyManagerFactory.getDefaultAlgorithm());
            keyManagerFactory.init(keyStore, password);

            if (trustManager == null) {
                // create a new CompositeTrustManager that accepts system CA certs
                trustManager = new CompositeTrustManager(keyStore);
            }

            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, new TrustManager[]{trustManager}, null);
            sslSocketFactory = sslContext.getSocketFactory();

            hostnameVerifier = HttpsURLConnection.getDefaultHostnameVerifier();

        } catch (Exception e) {
            String message = "Error during initialization";
            e.printStackTrace();
            throw new RuntimeException(message);
        }
    }
}
