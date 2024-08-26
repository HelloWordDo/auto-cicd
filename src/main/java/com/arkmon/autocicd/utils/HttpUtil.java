package com.arkmon.autocicd.utils;


import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Slf4j
public class HttpUtil {

    public static String execCurl(String[] cmds){
        ProcessBuilder process = new ProcessBuilder(cmds);
        Process p;
        try {
            p = process.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            StringBuilder builder = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
                builder.append(System.getProperty("line.separator"));
            }
            return builder.toString();

        } catch (IOException e) {
            System.out.print("error");
            e.printStackTrace();
        }
        return null;
    }

    public static void close(CloseableHttpResponse rsp){
        if(!StringUtils.isEmpty(rsp)){
            try {
                EntityUtils.consume(rsp.getEntity());
                rsp.close();
            } catch (Exception e) {
                log.error(null,e);
            }
        }
    }

}
