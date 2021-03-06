package com.onion.android.app.plex.data.model.ads;


public class AdRetriever {

    private String videoId;

    private String publisherId;

    private long cubPoint;

    public AdRetriever() {
    }

    public AdRetriever(String videoId, String publisherId, long cubPoint) {
        this.videoId = videoId;
        this.publisherId = publisherId;
        this.cubPoint = cubPoint;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(String publisherId) {
        this.publisherId = publisherId;
    }

    public long getCubPoint() {
        return cubPoint;
    }

    public void setCubPoint(long cubPoint) {
        this.cubPoint = cubPoint;
    }
}
