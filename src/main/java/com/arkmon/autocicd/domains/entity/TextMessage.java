package com.arkmon.autocicd.domains.entity;

import io.micrometer.core.instrument.util.StringUtils;
import lombok.Data;
import utils.JsonUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class TextMessage {
    private String text;
    private List<String> atMobiles;
    private boolean atAll;

    public TextMessage(String text) {
        this.text = text;
    }
    public String toJsonString() {
        Map<String, Object> items = new HashMap<>();
        items.put("msgtype", "text");

        Map<String, String> textContent = new HashMap<>();
        if (StringUtils.isBlank(text)) {
            throw new IllegalArgumentException("text should not be blank");
        }
        textContent.put("content", text);
        items.put("text", textContent);

        Map<String, Object> atItems = new HashMap<>();
        if (atMobiles != null && !atMobiles.isEmpty()) {
            atItems.put("atMobiles", atMobiles);
        }
        if (atAll) {
            atItems.put("isAtAll", atAll);
        }
        items.put("at", atItems);

        return JsonUtil.jsonObj2Str(items);
    }
}
