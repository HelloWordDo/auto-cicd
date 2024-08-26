package com.arkmon.autocicd.utils;

/**
 * 与Jenkins交互工具类
 *
 * @author X.J
 * @date 2021/2/26
 */
public class JenkinsUtil {

    public static String callJenkinsApi(String jenkinsUrl) {
        String[] buildCmd = {"curl", "-X", "POST", jenkinsUrl};
        return HttpUtil.execCurl(buildCmd);
    }
}
