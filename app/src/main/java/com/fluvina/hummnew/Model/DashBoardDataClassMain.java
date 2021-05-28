package com.fluvina.hummnew.Model;

import android.view.View;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;

import java.util.ArrayList;

public class DashBoardDataClassMain {

    @Expose
    @SerializedName("data")
    private ArrayList<DashBoardDataClass> data;
    @Expose
    @SerializedName("total_records")
    private int total_records;
    @Expose
    @SerializedName("message")
    private String message;
    @Expose
    @SerializedName("status")
    private boolean status;

    public ArrayList<DashBoardDataClass> getData() {
        return data;
    }

    public void setData(ArrayList<DashBoardDataClass> data) {
        this.data = data;
    }

    public int getTotal_records() {
        return total_records;
    }

    public void setTotal_records(int total_records) {
        this.total_records = total_records;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public class DashBoardDataClass {
        @Expose
        @SerializedName("PageType")
        private int PageType;
        @Expose
        @SerializedName("DetailUrl")
        private String DetailUrl;
        @Expose
        @SerializedName("Tags")
        private String Tags;
        @Expose
        @SerializedName("ThumbName")
        private String ThumbName;
        @Expose
        @SerializedName("ImageName")
        private String ImageName;
        @Expose
        @SerializedName("VideoEmbededCode")
        private String VideoEmbededCode;
        @Expose
        @SerializedName("IsVideoUrl")
        private String IsVideoUrl;
        @Expose
        @SerializedName("VideoUrl")
        private String VideoUrl;
        @Expose
        @SerializedName("VideoType")
        private int VideoType;
        @Expose
        @SerializedName("MediaType")
        private String MediaType;
        @Expose
        @SerializedName("Description")
        private String Description;
        @Expose
        @SerializedName("SortDescription")
        private String SortDescription;
        @Expose
        @SerializedName("Title")
        private String Title;
        @Expose
        @SerializedName("Id")
        private int Id;

        View playerView;
        YouTubePlayer youTubePlayer;

        @Expose
        @SerializedName("IsSurvey")
        int IsSurvey;
        @Expose
        @SerializedName("Question")
        String Question;
        @Expose
        @SerializedName("Options")
        ArrayList<DashboardQuestions> Options;
        @Expose
        @SerializedName("QuestionType")
        String QuestionType;
        @Expose
        @SerializedName("ImageType")
        int ImageType;
        @Expose
        @SerializedName("CorrectOption")
        String CorrectOption;
        @Expose
        @SerializedName("isUserSelectedOption")
        int isUserSelectedOption;
        @Expose
        @SerializedName("IsMedicineConfirmationSurvey")
        int IsMedicineConfirmationSurvey;
        @Expose
        @SerializedName("IsAdvanceInstruction")
        int IsAdvanceInstruction;
        @Expose
        @SerializedName("BackgroundColor")
        String BackgroundColor;
        @Expose
        @SerializedName("Created_on")
        String Created_on;
        @Expose
        @SerializedName("CompleteImage")
        String CompleteImage;
        @Expose
        @SerializedName("CompleteType")
        String CompleteType;
        @Expose
        @SerializedName("IsSlider")
        String IsSlider;
        @Expose
        @SerializedName("Slider")
        private ArrayList<DashBoardDataClass> Slider;

        @Expose
        @SerializedName("FeedCategoryType")
        private String FeedCategoryType;

        @Expose
        @SerializedName("font_color")
        private String font_color;

        public String getFont_color() {
            return font_color;
        }

        public void setFont_color(String font_color) {
            this.font_color = font_color;
        }

        public String getFeedCategoryType() {
            return FeedCategoryType;
        }

        public void setFeedCategoryType(String feedCategoryType) {
            FeedCategoryType = feedCategoryType;
        }

        public String getIsSlider() {
            return IsSlider;
        }

        public void setIsSlider(String isSlider) {
            IsSlider = isSlider;
        }

        public ArrayList<DashBoardDataClass> getSlider() {
            return Slider;
        }

        public void setSlider(ArrayList<DashBoardDataClass> slider) {
            Slider = slider;
        }
        @Expose
        @SerializedName("ChildName")
        String ChildName;
        @Expose
        @SerializedName("ChildGender")
        String ChildGender;
        @Expose
        @SerializedName("ChildDob")
        String ChildDob;
        @Expose
        @SerializedName("VaccineList")
        private ArrayList<VaccineClass> VaccineList;

        public int getIsMedicineConfirmationSurvey() {
            return IsMedicineConfirmationSurvey;
        }

        public void setIsMedicineConfirmationSurvey(int isMedicineConfirmationSurvey) {
            IsMedicineConfirmationSurvey = isMedicineConfirmationSurvey;
        }

        public int getIsAdvanceInstruction() {
            return IsAdvanceInstruction;
        }

        public void setIsAdvanceInstruction(int isAdvanceInstruction) {
            IsAdvanceInstruction = isAdvanceInstruction;
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

        public View getPlayerView() {
            return playerView;
        }

        public void setPlayerView(View playerView) {
            this.playerView = playerView;
        }

        public YouTubePlayer getYouTubePlayer() {
            return youTubePlayer;
        }

        public void setYouTubePlayer(YouTubePlayer youTubePlayer) {
            this.youTubePlayer = youTubePlayer;
        }

        public int getIsSurvey() {
            return IsSurvey;
        }

        public void setIsSurvey(int isSurvey) {
            IsSurvey = isSurvey;
        }

        public String getQuestion() {
            return Question;
        }

        public void setQuestion(String question) {
            Question = question;
        }

        public ArrayList<DashboardQuestions> getOptions() {
            return Options;
        }

        public void setOptions(ArrayList<DashboardQuestions> options) {
            Options = options;
        }

        public String getQuestionType() {
            return QuestionType;
        }

        public void setQuestionType(String questionType) {
            QuestionType = questionType;
        }

        public int getImageType() {
            return ImageType;
        }

        public void setImageType(int imageType) {
            ImageType = imageType;
        }

        public String getCorrectOption() {
            return CorrectOption;
        }

        public void setCorrectOption(String correctOption) {
            CorrectOption = correctOption;
        }

        public int getIsUserSelectedOption() {
            return isUserSelectedOption;
        }

        public void setIsUserSelectedOption(int isUserSelectedOption) {
            this.isUserSelectedOption = isUserSelectedOption;
        }

        public String getBackgroundColor() {
            return BackgroundColor;
        }

        public void setBackgroundColor(String backgroundColor) {
            BackgroundColor = backgroundColor;
        }

        public String getCreated_on() {
            return Created_on;
        }

        public void setCreated_on(String created_on) {
            Created_on = created_on;
        }

        public String getCompleteImage() {
            return CompleteImage;
        }

        public void setCompleteImage(String completeImage) {
            CompleteImage = completeImage;
        }

        public String getCompleteType() {
            return CompleteType;
        }

        public void setCompleteType(String completeType) {
            CompleteType = completeType;
        }

        public String getChildName() {
            return ChildName;
        }

        public void setChildName(String childName) {
            ChildName = childName;
        }

        public String getChildGender() {
            return ChildGender;
        }

        public void setChildGender(String childGender) {
            ChildGender = childGender;
        }

        public String getChildDob() {
            return ChildDob;
        }

        public void setChildDob(String childDob) {
            ChildDob = childDob;
        }

        public ArrayList<VaccineClass> getVaccineList() {
            return VaccineList;
        }

        public void setVaccineList(ArrayList<VaccineClass> vaccineList) {
            VaccineList = vaccineList;
        }
    }

    public class DashboardQuestions {
        @Expose
        @SerializedName("option")
        int option;
        @Expose
        @SerializedName("value")
        String value;
        @Expose
        @SerializedName("AveragePercentage")
        float AveragePercentage;
        @Expose
        @SerializedName("SelectedOption")
        int SelectedOption;

        public int getOption() {
            return option;
        }

        public void setOption(int option) {
            this.option = option;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public float getAveragePercentage() {
            return AveragePercentage;
        }

        public void setAveragePercentage(float averagePercentage) {
            AveragePercentage = averagePercentage;
        }

        public int getSelectedOption() {
            return SelectedOption;
        }

        public void setSelectedOption(int selectedOption) {
            SelectedOption = selectedOption;
        }
    }

    public class VaccineClass {
        @Expose
        @SerializedName("vacc_dur")
        String vacc_dur;
        @Expose
        @SerializedName("actual_date")
        String actual_date;
        @Expose
        @SerializedName("track_value")
        String track_value;
        @Expose
        @SerializedName("taken_on")
        String taken_on;
        @Expose
        @SerializedName("doses")
        private ArrayList<Dosage> doses;

        public String getVacc_dur() {
            return vacc_dur;
        }

        public void setVacc_dur(String vacc_dur) {
            this.vacc_dur = vacc_dur;
        }

        public String getActual_date() {
            return actual_date;
        }

        public void setActual_date(String actual_date) {
            this.actual_date = actual_date;
        }

        public String getTrack_value() {
            return track_value;
        }

        public void setTrack_value(String track_value) {
            this.track_value = track_value;
        }

        public String getTaken_on() {
            return taken_on;
        }

        public void setTaken_on(String taken_on) {
            this.taken_on = taken_on;
        }

        public ArrayList<Dosage> getDoses() {
            return doses;
        }

        public void setDoses(ArrayList<Dosage> doses) {
            this.doses = doses;
        }
    }

    public class Dosage {
        @Expose
        @SerializedName("doseage_name")
        String doseage_name;
        @Expose
        @SerializedName("details_key")
        String details_key;
        @Expose
        @SerializedName("due_date")
        String due_date;
        @Expose
        @SerializedName("vanish_flag")
        String vanish_flag;
        @Expose
        @SerializedName("catchup_flag")
        boolean catchup_flag;
        @Expose
        @SerializedName("taken")
        boolean taken;

        public String getDoseage_name() {
            return doseage_name;
        }

        public void setDoseage_name(String doseage_name) {
            this.doseage_name = doseage_name;
        }

        public String getDetails_key() {
            return details_key;
        }

        public void setDetails_key(String details_key) {
            this.details_key = details_key;
        }

        public String getDue_date() {
            return due_date;
        }

        public void setDue_date(String due_date) {
            this.due_date = due_date;
        }

        public String getVanish_flag() {
            return vanish_flag;
        }

        public void setVanish_flag(String vanish_flag) {
            this.vanish_flag = vanish_flag;
        }

        public boolean isCatchup_flag() {
            return catchup_flag;
        }

        public void setCatchup_flag(boolean catchup_flag) {
            this.catchup_flag = catchup_flag;
        }

        public boolean isTaken() {
            return taken;
        }

        public void setTaken(boolean taken) {
            this.taken = taken;
        }
    }
}
