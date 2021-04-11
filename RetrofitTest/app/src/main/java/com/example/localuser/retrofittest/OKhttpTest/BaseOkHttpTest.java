package com.example.localuser.retrofittest.OKhttpTest;

import android.util.Log;

import com.example.localuser.retrofittest.Configs.LogConfigs;
import com.example.localuser.retrofittest.MyApplication;
import com.example.localuser.retrofittest.OKhttpTest.Cookie.CookiesManager;
import com.example.localuser.retrofittest.OKhttpTest.Cookie2.CookieJarImpl;
import com.example.localuser.retrofittest.OKhttpTest.Cookie2.PersistentCookieStore2;
import com.example.localuser.retrofittest.R;
import com.example.localuser.retrofittest.Utils.AppUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.CookieHandler;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;

public abstract class BaseOkHttpTest {
    protected String TAG = LogConfigs.TAG_PREFIX_OKHTTP+getClass().getSimpleName();
    protected OkHttpClient mOkHttpClient;

    public BaseOkHttpTest(boolean https)
    {
        Log.d(TAG,"BaseOkHttpTest()");
        if(https) {
            try {
                mOkHttpClient = getHttpsOkHttpClient();
                Log.d(TAG, "mOkHttpClient = " + mOkHttpClient);
            } catch (Exception e) {
                e.printStackTrace();
                Log.e(TAG, "", e);
            }
        }else{
            mOkHttpClient = getHttpOkHttpClient();
        }
    }

    public OkHttpClient getHttpsOkHttpClient() throws IOException, CertificateException, KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
        // 从assets中加载证书，取到证书的输入流
        InputStream is = MyApplication.getInstance().getResources().openRawResource(R.raw.my12306);
        // 证书工厂
        CertificateFactory cf = CertificateFactory.getInstance("X.509");
        Certificate ca = cf.generateCertificate(is);

        // 加载证书到密钥库中
        String keyStoreType = KeyStore.getDefaultType();
        KeyStore keyStore = KeyStore.getInstance(keyStoreType);
        keyStore.load(null);
        keyStore.setCertificateEntry("cert", ca);

        // 加载密钥库到信任管理器
        String algorithm = TrustManagerFactory.getDefaultAlgorithm();
        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(algorithm);
        trustManagerFactory.init(keyStore);
        TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();

        if (trustManagers.length != 1 || !(trustManagers[0] instanceof X509TrustManager)) {
            throw new IllegalStateException("Unexpected default trust managers:"
                             + Arrays.toString(trustManagers));
        }
        X509TrustManager trustManager = (X509TrustManager) trustManagers[0];
        // 用 TrustManager 初始化一个 SSLContext
        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, trustManagers, null);

        return new OkHttpClient.Builder()
                .readTimeout(5000, TimeUnit.MILLISECONDS)
                .sslSocketFactory(sslContext.getSocketFactory(),trustManager)
//                .hostnameVerifier(new HostnameVerifier() {
//                    @Override
//                    public boolean verify(String s, SSLSession sslSession) {
//                        return true;
//                    }
//                })
                .build();
    }

    private OkHttpClient getHttpOkHttpClient(){
//        return new OkHttpClient.Builder()
//                .readTimeout(5000, TimeUnit.MILLISECONDS)
//                .cookieJar(new CookiesManager())
//                .build();
        return new OkHttpClient.Builder()
                .readTimeout(5000, TimeUnit.MILLISECONDS)
                .cookieJar(new CookieJarImpl(new PersistentCookieStore2(MyApplication.getInstance())))
                .build();
    }
}
