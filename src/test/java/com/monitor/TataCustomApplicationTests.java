package com.monitor;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.DigestUtils;

import java.io.IOException;

@SpringBootTest
class TataCustomApplicationTests {

    @Test
    void contextLoads() {

        final long timestamp = System.currentTimeMillis();
        final String appKey = "nOACedqY4X";
        final String appSecret = "1PtKatMQ5Du3WHpHk9oW8GJf6izJYZIX";
        String key = appSecret + appKey + timestamp;
        // 签名生成的两种方式之一，在有appuid入参的情况下，需要将appuid也加入计算。
        final String sign = DigestUtils.md5DigestAsHex(key.getBytes());

        final StringBuilder apiBuilder = new StringBuilder();
        apiBuilder.append("https://openapi.kujiale.com/v2/designeroc/gallery/list")
                // 鉴权的参数作为URL Query Param
                .append("?appkey=").append(appKey)
                .append("&timestamp=").append(timestamp)
                .append("&sign=").append(sign);
        // 构造文档约定的HTTP POST方法
        final HttpPost postRequest = new HttpPost(apiBuilder.toString());
        // 设置文档中约定的Content-Type为请求的header
        postRequest.setHeader("Content-Type", "application/json;charset=utf-8");
        // 将JSON字符串设置为文档中约定的Request Body
        final StringEntity entity = new StringEntity("{\n" +
                "\t\"start\":0,\n" +
                "\t\"orderType\":0,\n" +
                "\t\"tagIds\":[\n" +
                "\t\t\"3FO4K4VYR28A\",\"3FO4K4VYR5KB\"\n" +
                "\t],\n" +
                "\t\"galleryName\":\"搜索用的图库名称\",\n" +
                "\t\"num\":10\n" +
                "\t\n" +
                "}", "UTF-8");
        postRequest.setEntity(entity);
        HttpEntity resultEntity = null;
        try {
            // 执行请求并获取返回值
            final HttpClient httpClient = new DefaultHttpClient();
            final HttpResponse response = httpClient.execute(postRequest);
            resultEntity = response.getEntity();
            // 获取返回值
            final String result = EntityUtils.toString(resultEntity, "UTF-8");
            // 打印返回值
            System.out.println(result);
        } catch (final ClientProtocolException e) {
            e.printStackTrace();
            return;
        } catch (final IOException e) {
            e.printStackTrace();
            return;
        } finally {
            // 资源释放
            postRequest.abort();
            try {
                EntityUtils.consume(resultEntity);
            } catch (final IOException e) {
                e.printStackTrace();
            }
        }
    }

}
