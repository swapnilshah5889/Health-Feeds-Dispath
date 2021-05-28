package com.fluvina.hummnew.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HummSearchModel {


    @SerializedName("data")
    private List<Data> data;
    @SerializedName("total_records")
    private int total_records;
    @SerializedName("msg")
    private String msg;
    @SerializedName("status")
    private boolean status;

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    public int getTotal_records() {
        return total_records;
    }

    public void setTotal_records(int total_records) {
        this.total_records = total_records;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public static class Data {
        @SerializedName("FeedCategoryType")
        private int FeedCategoryType;
        @SerializedName("Slider")
        private List<String> Slider;
        @SerializedName("IsSlider")
        private String IsSlider;
        @SerializedName("Created_on")
        private String Created_on;
        @SerializedName("PageType")
        private int PageType;
        @SerializedName("DetailUrl")
        private String DetailUrl;
        @SerializedName("Tags")
        private String Tags;
        @SerializedName("ThumbName")
        private String ThumbName;
        @SerializedName("ImageName")
        private String ImageName;
        @SerializedName("VideoEmbededCode")
        private String VideoEmbededCode;
        @SerializedName("IsVideoUrl")
        private String IsVideoUrl;
        @SerializedName("VideoUrl")
        private String VideoUrl;
        @SerializedName("VideoType")
        private int VideoType;
        @SerializedName("MediaType")
        private String MediaType;
        @SerializedName("Description")
        private String Description;
        @SerializedName("SortDescription")
        private String SortDescription;
        @SerializedName("Title")
        private String Title;
        @SerializedName("Id")
        private int Id;

        public int getFeedCategoryType() {
            return FeedCategoryType;
        }

        public void setFeedCategoryType(int FeedCategoryType) {
            this.FeedCategoryType = FeedCategoryType;
        }

        public List<String> getSlider() {
            return Slider;
        }

        public void setSlider(List<String> Slider) {
            this.Slider = Slider;
        }

        public String getIsSlider() {
            return IsSlider;
        }

        public void setIsSlider(String IsSlider) {
            this.IsSlider = IsSlider;
        }

        public String getCreated_on() {
            return Created_on;
        }

        public void setCreated_on(String Created_on) {
            this.Created_on = Created_on;
        }

        public int getPageType() {
            return PageType;
        }

        public void setPageType(int PageType) {
            this.PageType = PageType;
        }

        public String getDetailUrl() {
            return DetailUrl;
        }

        public void setDetailUrl(String DetailUrl) {
            this.DetailUrl = DetailUrl;
        }

        public String getTags() {
            return Tags;
        }

        public void setTags(String Tags) {
            this.Tags = Tags;
        }

        public String getThumbName() {
            return ThumbName;
        }

        public void setThumbName(String ThumbName) {
            this.ThumbName = ThumbName;
        }

        public String getImageName() {
            return ImageName;
        }

        public void setImageName(String ImageName) {
            this.ImageName = ImageName;
        }

        public String getVideoEmbededCode() {
            return VideoEmbededCode;
        }

        public void setVideoEmbededCode(String VideoEmbededCode) {
            this.VideoEmbededCode = VideoEmbededCode;
        }

        public String getIsVideoUrl() {
            return IsVideoUrl;
        }

        public void setIsVideoUrl(String IsVideoUrl) {
            this.IsVideoUrl = IsVideoUrl;
        }

        public String getVideoUrl() {
            return VideoUrl;
        }

        public void setVideoUrl(String VideoUrl) {
            this.VideoUrl = VideoUrl;
        }

        public int getVideoType() {
            return VideoType;
        }

        public void setVideoType(int VideoType) {
            this.VideoType = VideoType;
        }

        public String getMediaType() {
            return MediaType;
        }

        public void setMediaType(String MediaType) {
            this.MediaType = MediaType;
        }

        public String getDescription() {
            return Description;
        }

        public void setDescription(String Description) {
            this.Description = Description;
        }

        public String getSortDescription() {
            return SortDescription;
        }

        public void setSortDescription(String SortDescription) {
            this.SortDescription = SortDescription;
        }

        public String getTitle() {
            return Title;
        }

        public void setTitle(String Title) {
            this.Title = Title;
        }

        public int getId() {
            return Id;
        }

        public void setId(int Id) {
            this.Id = Id;
        }
    }
}
