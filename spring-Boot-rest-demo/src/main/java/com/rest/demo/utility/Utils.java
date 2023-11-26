package com.rest.demo.utility;

import com.rest.demo.model.User;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Utils {
    static Logger logger = LoggerFactory.getLogger(Utils.class);

    public static String apiCall(User user, int id, String requestType) throws IOException {
        logger.info("Post Send config to NSO API processing" + requestType);
        String user_id = Integer.toString(id);
        if(id ==0){
            user_id="";
        }
        URL url = new URL(Constants.GET_USERS_API+user_id);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        StringBuffer response = new StringBuffer();
        try{
            conn.setRequestProperty("Authorization","Bearer "+" 9df15999-1019-46d0-864c-15737b82da2c");
            conn.setRequestProperty("Content-Type","application/json");
            conn.setRequestMethod(requestType);
            conn.setDoOutput(true);
            if(user != null){
                String jsonPayload = convertUserTOJson(user);
                try (OutputStream os = conn.getOutputStream()) {
                    byte[] input = jsonPayload.getBytes("utf-8");
                    os.write(input, 0, input.length);
                }
            }
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String output;
            while ((output = in.readLine()) != null) {
                response.append(output);
            }
            in.close();
            logger.info("Response code is: " + conn.getResponseCode());
            logger.info("Response:-" + response.toString());
        }catch (Exception e){
            e.printStackTrace();
            logger.error("Exception in apiCall "+e);
        }finally {
            conn.disconnect();
        }
        return response.toString();

    }

   /* public String callExternalApi(User user, int id, String requestType,Enum httpMethod) {
        logger.info("callExternalApi   ");
        String user_id = Integer.toString(id);
        if(id ==0){
            user_id="";
        }
        String url = Constants.GET_USERS_API+user_id;
        String requestJson = "";
        if(user != null) {
            requestJson = convertUserTOJson(user);
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization","Bearer "+" 9df15999-1019-46d0-864c-15737b82da2c");
        HttpEntity<Object> entity=new HttpEntity<Object>(requestJson,headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        logger.info("response   "+response);
        logger.info("response body   "+response.getBody());


        return response.getBody();
    }*/




    public static List<User> convertJsonToUserList(String response){
        ObjectMapper mapper = new ObjectMapper();
        List<User> jacksonList = new ArrayList<>();
        try {
            if(response != null){
                JSONObject jsonObject = new JSONObject(response);
                Integer data = jsonObject.getInt("page");
                JSONArray jsonArray = jsonObject.getJSONArray("data");
                logger.info("response object data"+data);
                logger.info("response user data"+jsonArray);
                jacksonList = mapper.readValue(jsonArray.toString(), new TypeReference<List<User>>(){});
                logger.info("response object "+jacksonList.size());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jacksonList;
    }

    public static User convertJsonToUser(String response){
        logger.info("convertJsonToUser method calling");
        ObjectMapper mapper = new ObjectMapper();
        User user = new User();
        try {
            if(response != null){
                user = mapper.readValue(response, User.class);
                logger.info("response object "+user);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return user;
    }

    public static String convertUserTOJson(User user){
        logger.info("convertUserTOJson method calling");
        ObjectMapper mapper = new ObjectMapper();
        String jsonStr = "";
        try {
            jsonStr = mapper.writeValueAsString(user);
            logger.info("JSON String of user "+jsonStr);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonStr;
    }
}
