package com.fluvina.hummnew.Model;

public class HummFeedModel {
    String feed_id;
    String feed_type;

    public HummFeedModel(String feed_id, String feed_type) {
        this.feed_id = feed_id;
        this.feed_type = feed_type;
    }

    public String getfeed_id() {
        return feed_id;
    }

    public void setfeed_id(String feed_id) {
        this.feed_id = feed_id;
    }

    public String getType() {
        return feed_type;
    }

    public void setType(String feed_type) {
        this.feed_type = feed_type;
    }
}
