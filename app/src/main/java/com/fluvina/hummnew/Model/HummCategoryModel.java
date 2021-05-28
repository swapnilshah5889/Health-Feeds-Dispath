package com.fluvina.hummnew.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HummCategoryModel {

    @SerializedName("data")
    private Data data;
    @SerializedName("msg")
    private String msg;
    @SerializedName("status")
    private boolean status;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
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
        @SerializedName("in_sights_list")
        private List<In_sights_list> in_sights_list;
        @SerializedName("category_list")
        private List<Category_list> category_list;
        @SerializedName("all_feed_img")
        private String all_feed_img;

        public String getAll_feed_img() {
            return all_feed_img;
        }

        public void setAll_feed_img(String all_feed_img) {
            this.all_feed_img = all_feed_img;
        }

        public List<In_sights_list> getIn_sights_list() {
            return in_sights_list;
        }

        public void setIn_sights_list(List<In_sights_list> in_sights_list) {
            this.in_sights_list = in_sights_list;
        }

        public List<Category_list> getCategory_list() {
            return category_list;
        }

        public void setCategory_list(List<Category_list> category_list) {
            this.category_list = category_list;
        }
    }

    public static class In_sights_list {
        @SerializedName("FeedCategoryType")
        private int FeedCategoryType;
        @SerializedName("Slider")
        private List<Slider> Slider;
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

        public List<Slider> getSlider() {
            return Slider;
        }

        public void setSlider(List<Slider> Slider) {
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

    public static class Slider {
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

    public static class Category_list {
        @SerializedName("hashtag_ordering_json")
        private List<Hashtag_ordering_json> hashtag_ordering_json;
        @SerializedName("name")
        private String name;
        @SerializedName("id")
        private String id;

        public List<Hashtag_ordering_json> getHashtag_ordering_json() {
            return hashtag_ordering_json;
        }

        public void setHashtag_ordering_json(List<Hashtag_ordering_json> hashtag_ordering_json) {
            this.hashtag_ordering_json = hashtag_ordering_json;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }

    public static class Hashtag_ordering_json {
        @SerializedName("tag_icon_url")
        private String tag_icon_url;
        @SerializedName("tag_icon_image")
        private String tag_icon_image;
        @SerializedName("sub_category_name")
        private String sub_category_name;
        @SerializedName("ordering_no")
        private int ordering_no;
        @SerializedName("feed_hashtag_id")
        private int feed_hashtag_id;

        public String getSub_category_name() {
            return sub_category_name;
        }

        public void setSub_category_name(String sub_category_name) {
            this.sub_category_name = sub_category_name;
        }

        public String getTag_icon_url() {
            return tag_icon_url;
        }

        public void setTag_icon_url(String tag_icon_url) {
            this.tag_icon_url = tag_icon_url;
        }

        public String getTag_icon_image() {
            return tag_icon_image;
        }

        public void setTag_icon_image(String tag_icon_image) {
            this.tag_icon_image = tag_icon_image;
        }

        public int getOrdering_no() {
            return ordering_no;
        }

        public void setOrdering_no(int ordering_no) {
            this.ordering_no = ordering_no;
        }

        public int getFeed_hashtag_id() {
            return feed_hashtag_id;
        }

        public void setFeed_hashtag_id(int feed_hashtag_id) {
            this.feed_hashtag_id = feed_hashtag_id;
        }
    }
}
