//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package cn.jiuling.vehicleinfosys2.util;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class HttpUtils {

    private static Logger logger = Logger.getLogger(HttpUtils.class);
    private static final String USER_AGENT = "Mozilla/5.0 Firefox/26.0";
    private static final int TIMEOUT_SECONDS = 120;
    private static final int POOL_SIZE = 120;
    private static CloseableHttpClient httpclient;

    public HttpUtils() {
    }

    /**
     * 初始化一个httpclient
     */
    public void initApacheHttpClient() {
        RequestConfig defaultRequestConfig = RequestConfig.custom().setSocketTimeout(TIMEOUT_SECONDS * 1000).setConnectTimeout(TIMEOUT_SECONDS * 1000).build();
        httpclient = HttpClients.custom().setUserAgent(USER_AGENT).setMaxConnTotal(POOL_SIZE).setMaxConnPerRoute(POOL_SIZE).setDefaultRequestConfig(defaultRequestConfig).build();
    }

    /**
     * 销毁httpclient
     */
    public void destroyApacheHttpClient() {
        try {
            httpclient.close();
        } catch (IOException e) {
            logger.error("httpclient close fail", e);
        }

    }

    /**
     * 下载图片
     * @param imageUrl 图片地址
     * @param destFileName 图片本地保存地址
     * @throws ClientProtocolException
     * @throws IOException
     */
    public void fetchContent(String imageUrl, String destFileName) throws ClientProtocolException, IOException {
        HttpGet httpget = new HttpGet(imageUrl);
        httpget.setHeader("Referer", "http://www.google.com");
        logger.info("执行请求 " + httpget.getURI());
        CloseableHttpResponse response = httpclient.execute(httpget);

        try {
            HttpEntity entity = response.getEntity();
            if (response.getStatusLine().getStatusCode() >= 400) {
                throw new IOException("响应异常, 错误代码： " + response.getStatusLine().getStatusCode() + " : 图片地址" + imageUrl);
            }

            if (entity != null) {
                InputStream input = entity.getContent();
                File file = new File(destFileName.substring(0, destFileName.lastIndexOf("\\") + 1));
                //创建路径
                if (!file.exists() && !file.isDirectory()) {
                    file.mkdirs();
                }

                FileOutputStream output = new FileOutputStream(new File(destFileName));
                IOUtils.copy(input, output);
                output.flush();
                input.close();
                output.close();
            }
        } finally {
            response.close();
        }

    }

    /**
     * http上传
     * @param url 上传地址
     * @param dataXML 要上传的数据格式
     * @return
     */
    public String doPost(String url, String dataXML) {
        HttpPost httpPost = new HttpPost(url);
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("action", "sendVehicleInfo "));
        params.add(new BasicNameValuePair("data", dataXML));

        try {
            httpPost.setEntity(new UrlEncodedFormEntity(params,"UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String ret = "error";
        CloseableHttpResponse httpResponse;
        try {
            logger.info("执行请求 " + httpPost.getURI());
            httpResponse = httpclient.execute(httpPost);
            if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                ret = EntityUtils.toString(httpResponse.getEntity(),"UTF-8");
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ret;
    }

    public static void main(String[] args) throws IOException {
        HttpUtils imageDownloader = new HttpUtils();
        imageDownloader.initApacheHttpClient();
        System.out.println(imageDownloader.doPost("cbd", "abc"));
//        String imageUrl = "http://localhost:8081/upload/201572914151597_1047673738-big.png";
//        String destFileName = "D:\\VlprDataSrc\\841\\todo\\0\\183920045车牌.jpg";
//        imageDownloader.fetchContent(imageUrl, destFileName);
        imageDownloader.destroyApacheHttpClient();
    }
}
