package com.spark;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.taglibs.standard.extra.spath.Token;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.atomic.AtomicBoolean;

public class HuaweiCBS {

    private static AtomicBoolean answer_block = new AtomicBoolean(false);

    /**
     *Get answer to the passed in question
     * @param question question in string
     * @return the answer from Huawei cloud
     */
    public static String getAnswer(String question) {
        String token = TokenManager.getToken();
        while(answer_block.get() == true);
        try {
            answer_block.set(true);
            URL url = new URL("https://cbs-ext.cn-north-4.myhuaweicloud.com/v1/082ee40b3f0026142f9cc0077217c378/qabots/b66b221d-5cd1-485d-b44b-ceace6d9dde5/chat");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.addRequestProperty("Content-Type", "application/json");
            connection.addRequestProperty("X-Auth-Token", token);

            String body = "{\"question\": \"" + question + " \"}";

            OutputStreamWriter osw = new OutputStreamWriter(connection.getOutputStream(), "UTF-8");
            osw.append(body);
            osw.flush();
            InputStream is = connection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String s = br.readLine();
            osw.close();
            is.close();
            connection.disconnect();
            answer_block.set(false);
            System.out.println(s);
//            JSONObject json = JSONObject.parseObject(s);
//            System.out.println(json.getString("qabot_answers"));
            String after_ans = s.substring(s.lastIndexOf("\"answer\":\"") + 10, s.length());
            String answer = after_ans.substring(0, after_ans.indexOf("\",\""));
            if (s.lastIndexOf("\"answer\":\"") == -1) {
                return "I don't have an answer, try to rephrase your question? :(";
            }


            return answer;
        } catch (Exception e) {
            answer_block.set(false);
            e.printStackTrace();
            return "an error has occured, please try again";
        }
    }

}
