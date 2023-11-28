package com.catalog.controller;

import com.alibaba.fastjson.JSONObject;
import com.catalog.dto.UserDTO;
import com.definesys.mpaas.common.http.Response;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.List;

@RestController
@ResponseBody
@Slf4j
@Api(tags = "登陆Controller")
@Configuration
public class LoginController {
    @Value("${ruoyi.login.url}")
    private String ruoyiurl;

    @PostMapping("/login")
    public JSONObject createUser(@RequestBody UserDTO user) {
        log.debug("【Creating User】Getting Request: {}", user);
        JSONObject retJson = new JSONObject();
        try {
            if("admin".equals(user.getUsername())&&"888888".equals(user.getPassword())){
                retJson.put("code", 200);
                retJson.put("msg", "登录成功");
                retJson.put("role", "editor");
            } else if("test".equals(user.getUsername())&&"888888".equals(user.getPassword())) {
                retJson.put("code", 200);
                retJson.put("msg", "登录成功");
                retJson.put("role", "view");
            }
            return retJson;
        } catch (Exception e) {
            e.printStackTrace();
            retJson.put("code", 40002);
            retJson.put("msg", "Unknown Error");
            return retJson;
        }
    }

    @GetMapping("/getUserDetail")
    public Response getAccount(@RequestParam  String username) throws IOException {
        JSONObject object = new JSONObject();
        try {
            CloseableHttpClient httpClient = HttpClients.createDefault();
            RequestConfig requestConfig = RequestConfig.custom()
                    .setSocketTimeout(300 * 1000)
                    .setConnectTimeout(300 * 1000)
                    .build();
            HttpPost post = new HttpPost(ruoyiurl+username);
            CloseableHttpResponse response = httpClient.execute(post);
            String content = EntityUtils.toString(response.getEntity());
            object = JSONObject.parseObject(content);
        } catch (SocketTimeoutException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Response.ok().data(object);
    }


}
