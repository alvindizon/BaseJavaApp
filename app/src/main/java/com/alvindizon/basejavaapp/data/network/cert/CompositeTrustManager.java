package com.alvindizon.basejavaapp.data.network.cert;

import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

/**
 * This class is composed of the default trust manager + additional trust managers that will be created
 * from certificates. This class was created due to the possibility of the API endpoint using a
 * self-signed certificate.
 * Based from https://gist.github.com/HughJeffner/6eac419b18c6001aeadb and
 * https://stackoverflow.com/questions/27562666/programmatically-add-a-certificate-authority-while-keeping-android-system-ssl-ce
 */
public class CompositeTrustManager implements X509TrustManager {

    private final List<X509TrustManager> trustManagerList;

    public CompositeTrustManager(KeyStore keyStore) {
        this.trustManagerList = new ArrayList<>();
        trustManagerList.add(getDefaultTrustManager());
        trustManagerList.add(getTrustManager(keyStore));
    }

    @Override
    public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        for (X509TrustManager trustManager : trustManagerList) {
            try {
                trustManager.checkClientTrusted(chain, authType);
                return;
            } catch (CertificateException e) {

            }
        }
        throw new CertificateException("None of the TrustManagers trust this certificate chain");
    }

    @Override
    public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        for (X509TrustManager trustManager : trustManagerList) {
            try {
                trustManager.checkServerTrusted(chain, authType);
                return;
            } catch (CertificateException e) {

            }
        }
        throw new CertificateException("None of the TrustManagers trust this certificate chain");
    }

    @Override
    public X509Certificate[] getAcceptedIssuers() {
        List<X509Certificate> certificates = new ArrayList<>();
        for (X509TrustManager trustManager : trustManagerList) {
            certificates.addAll(Arrays.asList(trustManager.getAcceptedIssuers()));
        }
        X509Certificate[] a = new X509Certificate[certificates.size()];
        return certificates.toArray(a);
    }

    private X509TrustManager getDefaultTrustManager() {
        return getTrustManager(null);
    }

    private X509TrustManager getTrustManager(KeyStore keyStore) {
        return getTrustManager(TrustManagerFactory.getDefaultAlgorithm(), keyStore);
    }

    private X509TrustManager getTrustManager(String algorithm, KeyStore keyStore) {
        TrustManagerFactory factory;

        try {
            factory = TrustManagerFactory.getInstance(algorithm);
            factory.init(keyStore);
            TrustManager[] trustManagers = factory.getTrustManagers();
            return (X509TrustManager) trustManagers[0];
        } catch (NoSuchAlgorithmException | KeyStoreException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void addTrustManager(KeyStore keyStore) {
        trustManagerList.add(getTrustManager(keyStore));
    }
}
