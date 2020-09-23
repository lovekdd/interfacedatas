package lengj.interfacedata.demo.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lengj.interfacedata.demo.entity.InterfaceDataEntity;
import lengj.interfacedata.demo.service.InterfaceDataRequest;
import lengj.interfacedata.demo.util.StmFunc;
import lengj.interfacedata.demo.util.StrFunc;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.stereotype.Service;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * 接口数据数据请求实现类
 *@author lengj
 *@since 2020年9月23日
 */
@Service
public class InterfaceDataRequestImpl implements InterfaceDataRequest {

    @Override
    public String send(InterfaceDataEntity ide) throws Exception {
        if(!StrFunc.isNull(ide.getUrl())){
            String param = ide.getParams();
            JSONArray array = JSONArray.parseArray(param);
            //头部参数
            HashMap<String,String> dsHeaderParams = new HashMap();
            //非头部参数
            HashMap<String,String> dsNormalParams = new HashMap();
            for(int i=0;i<array.size();i++){
                JSONObject info = array.getJSONObject(i);
                Set<String> keys = info.keySet();
                for(String key:keys){
                    if(StrFunc.parseBoolean(info.get("isHeader").toString(),false)){
                        if(key!="isHeader"){
                            dsHeaderParams.put(key,info.get(key).toString());
                        }
                    }else{
                        if(key!="isHeader"){
                            dsNormalParams.put(key,info.get(key).toString());
                        }
                    };
                }

            }
            SSLContext sslcontext = createIgnoreVerifySSL();
            Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory> create().register(
                    "http", PlainConnectionSocketFactory.INSTANCE).register("https",
                    new SSLConnectionSocketFactory(sslcontext, new NoopHostnameVerifier())).build();
            PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
            HttpClients.custom().setConnectionManager(connManager);
            //创建自定义的httpclient对象
            CloseableHttpClient hc = HttpClients.custom().setConnectionManager(connManager).build();
            try {
                String queryUrl = this.getFullUrl(ide.getUrl(), dsNormalParams);
                if (ide.getMethod()== "GET") {
                    HttpGet hg = new HttpGet(queryUrl);
                    if (dsHeaderParams != null) {
                        for (String key:dsHeaderParams.keySet()) {
                            hg.addHeader(key, dsHeaderParams.get(key));
                        }
                    }
                    try {
                        try {
                            return this.getExecuteResult(hc, hg, ide.getDstEncoding());
                        } catch (Exception ex) {
                            ex.printStackTrace();
                            return null;
                        }
                    } finally {
                        hg.completed();
                    }
                } else {
                    HttpPost hp = new HttpPost(queryUrl);
                    if (dsHeaderParams != null) {
                        for (String key:dsHeaderParams.keySet()) {
                            hp.addHeader(key, dsHeaderParams.get(key));
                        }
                    }
                    try {
                        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
                        hp.setEntity(new UrlEncodedFormEntity(nvps, ide.getDstEncoding()));
                        try {
                            return this.getExecuteResult(hc, hp, ide.getDstEncoding());
                        } catch (Exception e) {
                            e.printStackTrace();
                            return null;
                        }
                    } finally {
                        hp.completed();
                    }
                }
            } finally {
                hc.close();
            }

        }
        return  null;
    }

    /**
     * 绕过验证
     *
     * @return
     * @throws NoSuchAlgorithmException
     * @throws KeyManagementException
     */
    public static SSLContext createIgnoreVerifySSL() throws NoSuchAlgorithmException, KeyManagementException {
        SSLContext sc = SSLContext.getInstance("SSLv3");

        // 实现一个X509TrustManager接口，用于绕过验证，不用修改里面的方法
        X509TrustManager trustManager = new X509TrustManager() {
            @Override
            public void checkClientTrusted(java.security.cert.X509Certificate[] paramArrayOfX509Certificate,
                                           String paramString) throws CertificateException {
            }

            @Override
            public void checkServerTrusted(java.security.cert.X509Certificate[] paramArrayOfX509Certificate,
                                           String paramString) throws CertificateException {
            }

            @Override
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return null;
            }
        };

        sc.init(null, new TrustManager[] { trustManager }, null);
        return sc;
    }
    private String getFullUrl(String url, HashMap<String,String> paramStr) {
        if (StrFunc.isNull(url)) {
            return null;
        }
        if (StrFunc.isNull(paramStr.toString())) {
            return url;
        }
        StringBuffer str =new StringBuffer();
        for (String key:paramStr.keySet()) {
            str.append(key+'='+paramStr.get(key)+'&');
        }
        if (url.indexOf('?') > -1) {
            return url + str.toString();
        }
        return url + "?" + str.toString();
    }

    private String getExecuteResult(CloseableHttpClient hc, HttpRequestBase req, String dstEncoding) throws Exception {
        CloseableHttpResponse pq = hc.execute(req);
        String result = null;
        try {
            HttpEntity entity = pq.getEntity();
            if (entity == null) {
                throw new RuntimeException("请求到的数据为空");
            }
            InputStream contentStm = entity.getContent();
            if (contentStm == null) {
                throw new RuntimeException("请求到的数据为空");
            }
            if(StrFunc.compareStrIgnoreBlankAndCase(dstEncoding, StrFunc.UTF8)) {
                result = StmFunc.stm2Str(contentStm, StrFunc.UTF8);
            }else if (StrFunc.compareStrIgnoreBlankAndCase(dstEncoding, StrFunc.GBK)) {
                result = StmFunc.stm2Str(contentStm, StrFunc.GBK);
            }
            System.out.println(result);
            return result;
        } finally {
            pq.close();
        }
    }
}
