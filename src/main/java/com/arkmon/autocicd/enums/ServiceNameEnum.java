package com.arkmon.autocicd.enums;

import lombok.Getter;

/**
 * @author X.J
 * @date 2021/2/24
 */
@Getter
public enum ServiceNameEnum {

    ONLINE_MUSIC("0", "onlinemusic", "在线音乐"),
    WEATHER("1", "weather", "天气"),
    RESTAURANT("2", "restaurant", "餐厅美食"),
    FLIGHT("3", "flight", "航班"),
    TRAIN("4", "train", "火车"),
    SCENIC_SPOT("5", "scenicspot", "景点"),
    SHORT_VIDEO("6", "shortvideo", "短视频"),
    SHOW("7", "show", "演出"),
    MOVIE("8", "movie", "电影"),
    NEWS("9", "news", "新闻"),
    ONLINE_RADIO_ACCOUNT("10", "onlineradio-account", "在线电台账号"),
    ONLINE_RADIO_CONTENT("11", "onlineradio-content", "在线电台内容"),
    ONLINE_RADIO_GATEWAY("12", "onlineradio-gateway", "在线电台网关"),
    ONLINE_RADIO_HISTORY("13", "onlineradio-history", "在线电台历史"),
    ONLINE_RADIO_ORDER("14", "onlineradio-order", "在线电台订单"),
    ONLINE_RADIO_SEARCH("15", "onlineradio-search", "在线电台搜索"),
    ONLINE_RADIO_SYNCHRONIZE("16", "onlineradio-synchronize", "在线电台同步"),
    SMART_HOME("17", "smarthome", "智能家居"),
    USER_MANUAL("18", "usermanual", "用户手册"),
    VOICE_GAME("19", "voicegame", "语音游戏"),
    HOTEL("20", "hotel", "酒店"),
    GEO("21", "geo", "地理信息聚合服务"),
    MGS_FLOW("22", "msgflow", "消息流推送"),
    ;

    private String code;
    private String name;
    private String desc;

    ServiceNameEnum(String code, String name, String desc) {
        this.code = code;
        this.name = name;
        this.desc = desc;
    }
}
