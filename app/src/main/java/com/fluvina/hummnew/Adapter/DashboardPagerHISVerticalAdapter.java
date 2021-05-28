package com.fluvina.hummnew.Adapter;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.fluvina.hummnew.Activities.MainActivity;
import com.fluvina.hummnew.Custom.CarouselEffectTransformer;
import com.fluvina.hummnew.Custom.CarouselViewPager;
import com.fluvina.hummnew.Custom.OnSwipeTouchListener;
import com.fluvina.hummnew.Custom.ProgressBarAnimation;
import com.fluvina.hummnew.Fragments.DashboardHummFeedFragment;
import com.fluvina.hummnew.Model.DashBoardDataClassMain;
import com.fluvina.hummnew.R;
import com.fluvina.hummnew.Utilities.MobiConstants;
import com.fluvina.hummnew.Utilities.MobiLogger;
import com.fluvina.hummnew.Utilities.MobiUtility;
import com.fluvina.hummnew.interfaces.RecyclerItemActionClick;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerCallback;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import jp.wasabeef.glide.transformations.BlurTransformation;

import static androidx.viewpager2.widget.ViewPager2.ORIENTATION_HORIZONTAL;

public class DashboardPagerHISVerticalAdapter extends RecyclerView.Adapter {

    private final Context context;
    private final Fragment fragment;
    private final ArrayList<DashBoardDataClassMain.DashBoardDataClass> arrayList;
    RecyclerItemActionClick onItemClick;

    String TAG = "DashboardPagerHISAdapterNewVertical2 :--> ";
    private LayoutInflater mInflater;

    private final int HIS_PAGE_TYPE_IMAGE_TEXT = 0;
    private final int HIS_PAGE_TYPE_VIDEO_TEXT = 1;
    private final int HIS_PAGE_TYPE_IMAGE = 2;
    private final int HIS_PAGE_TYPE_VIDEO = 3;
    private final int HIS_PAGE_TYPE_TEXT = 4;
    private final int HIS_PAGE_TYPE_SURVEY_IMAGE_TEXT = 5;
    private final int HIS_PAGE_TYPE_SURVEY_VIDEO_TEXT = 6;
    private final int HIS_PAGE_TYPE_SURVEY_TEXT = 7;
    private final int HIS_PAGE_TYPE_QUIZ_IMAGE_TEXT = 8;
    private final int HIS_PAGE_TYPE_QUIZ_VIDEO_TEXT = 9;
    private final int HIS_PAGE_TYPE_QUIZ_TEXT = 10;
    private final int HIS_PAGE_TYPE_MEDICINE_CONFIRMATION = 11;
    private final int HIS_PAGE_TYPE_NORMAL_INSTRUCTION = 12;
    private final int HIS_PAGE_TYPE_ADVANCE_INSTRUCTION = 13;
    private final int HIS_PAGE_TYPE_VACCINE_CONFIRMATION = 14;
    private final int HIS_PAGE_TYPE_FOOD_REMINDER = 15;
    private final int HIS_PAGE_TYPE_GROWTH_DEVELOPMENT = 16;
    private final int HIS_PAGE_TYPE_VACCINE_REMINDER = 17;
    private final int HIS_PAGE_TYPE_INFO_SLIDER = 18;
    LinearLayout.LayoutParams params;
    int width;
    int actualHeight;

    public DashboardPagerHISVerticalAdapter(Context context, ArrayList<DashBoardDataClassMain.DashBoardDataClass> arrayList, Fragment fragment, RecyclerItemActionClick onItemClick) {
        this.context = context;
        this.fragment = fragment;
        this.arrayList = arrayList;
        this.onItemClick = onItemClick;
        setHasStableIds(true);

        try {
            DisplayMetrics displayMetrics = new DisplayMetrics();
            ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            width = (int) (displayMetrics.widthPixels * MobiConstants.HUMM_FULL_WIDTH_SHARE);
            actualHeight = (int) (displayMetrics.heightPixels * MobiConstants.HUMM_FULL_WIDTH_SHARE);
            double screenRatio = (double) actualHeight / (double) width;
            MobiLogger.printmsg("Screen Ratio : " + (screenRatio) + " | Height : " + actualHeight + " | Width : " + width);
            int temp = (int) (width * MobiConstants.HUMM_FULL_IMAGE_RATIO);
            int height = 0;
            if (temp > actualHeight)
                height = (int) (actualHeight * MobiConstants.HUMM_FULL_WIDTH_SHARE);
            else
                height = temp;

            params = new LinearLayout.LayoutParams(width, height);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView;
        switch (viewType) {
            case HIS_PAGE_TYPE_IMAGE_TEXT:
                itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.vertical_viewpager_image_text_item, parent, false);
                return new ImageTextViewHolder(itemView);

            case HIS_PAGE_TYPE_VIDEO_TEXT:
                itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.vertical_viewpager_video_text_item, parent, false);
                return new VideoTextViewHolder(itemView);

            case HIS_PAGE_TYPE_IMAGE:
                itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.vertical_viewpager_image_full_item, parent, false);
                return new ImageViewHolder(itemView);

            case HIS_PAGE_TYPE_VIDEO:
                itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.vertical_viewpager_video_full_item, parent, false);
                return new VideoViewHolder(itemView);

            case HIS_PAGE_TYPE_TEXT:
                itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.vertical_viewpager_text_item, parent, false);
                return new TextViewHolder(itemView);

            case HIS_PAGE_TYPE_SURVEY_IMAGE_TEXT:
                itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.vertical_viewpager_survey_image_item, parent, false);
                return new SurveyImageViewHolder(itemView);

            case HIS_PAGE_TYPE_SURVEY_VIDEO_TEXT:
                itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.vertical_viewpager_survey_video_item, parent, false);
                return new SurveyVideoViewHolder(itemView);

            case HIS_PAGE_TYPE_SURVEY_TEXT:
                itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.vertical_viewpager_survey_text_item, parent, false);
                return new SurveyTextViewHolder(itemView);

            case HIS_PAGE_TYPE_QUIZ_IMAGE_TEXT:
                itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.vertical_viewpager_long_quiz_image_item, parent, false);
                return new QuizImageViewHolder(itemView);

            case HIS_PAGE_TYPE_QUIZ_VIDEO_TEXT:
                itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.vertical_viewpager_long_quiz_video_item, parent, false);
                return new QuizVideoViewHolder(itemView);

            case HIS_PAGE_TYPE_QUIZ_TEXT:
                itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.vertical_viewpager_long_quiz_text_item, parent, false);
                return new QuizTextViewHolder(itemView);

            case HIS_PAGE_TYPE_MEDICINE_CONFIRMATION:
                itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.vertical_viewpager_medicine_confirmation_item, parent, false);
                return new MedicineConfirmationViewHolder(itemView);

            case HIS_PAGE_TYPE_NORMAL_INSTRUCTION:
                itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.vertical_viewpager_advance_instruction_item, parent, false);
                return new NormalInstructionViewHolder(itemView);

            case HIS_PAGE_TYPE_ADVANCE_INSTRUCTION:
                itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.vertical_viewpager_advance_instruction_item, parent, false);
                return new AdvanceInstructionViewHolder(itemView);


            case HIS_PAGE_TYPE_VACCINE_CONFIRMATION:
                itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.vertical_viewpager_vaccination_confirmation_item, parent, false);
                return new VaccineConfirmationViewHolder(itemView);

            case HIS_PAGE_TYPE_VACCINE_REMINDER:
                itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.vertical_viewpager_full_carousal_view_item, parent, false);
                return new VaccineInformationViewHolder(itemView);

            case HIS_PAGE_TYPE_FOOD_REMINDER:
                itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.vertical_viewpager_food_info_item, parent, false);
                return new FoodInformationViewHolder(itemView);

            case HIS_PAGE_TYPE_INFO_SLIDER:
                itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.vertical_viewpager_info_slider_item, parent, false);
                return new InfoSliderViewHolder(itemView);

            default:
                itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.vertical_viewpager_image_text_item, parent, false);
                return new ImageTextViewHolder(itemView);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        DashBoardDataClassMain.DashBoardDataClass dataClass = arrayList.get(position);

        //ImageTextViewHolder
        if (holder instanceof ImageTextViewHolder) {
            try {

                ((ImageTextViewHolder) holder).imageView.setLayoutParams(new LinearLayout.LayoutParams(width, width));

                ((ImageTextViewHolder) holder).imageView.setVisibility(View.VISIBLE);
                Glide.with(context).load(dataClass.getImageName())
                        .diskCacheStrategy(DiskCacheStrategy.ALL).skipMemoryCache(false)
                        .transform(new CenterCrop(), new RoundedCorners((int) context.getResources().getDimension(R.dimen._5sdp)))
                        .into(((ImageTextViewHolder) holder).imageView);
                ((ImageTextViewHolder) holder).txtTitle.setText(dataClass.getTitle());
                ((ImageTextViewHolder) holder).txtDescription.setText(dataClass.getDescription());

                setTouchListener(((ImageTextViewHolder) holder).vMainLayout, position);
                setViewClickListener(((ImageTextViewHolder) holder).imageView, "Full Image View", position);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        //VideoTextViewHolder
        else if (holder instanceof VideoTextViewHolder) {
            try {
                if (dataClass.getVideoType() == MobiConstants.HIS_VIDEO_TYPE_YOUTUBE) {

                    ((VideoTextViewHolder) holder).youtubePlayerView.setVisibility(View.VISIBLE);

                    ((MainActivity) context).getLifecycle().addObserver(((VideoTextViewHolder) holder).youtubePlayerView);
                    ((VideoTextViewHolder) holder).youtubePlayerView.getPlayerUiController().showFullscreenButton(false);
                    ((VideoTextViewHolder) holder).youtubePlayerView.getPlayerUiController().setFullScreenButtonClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //Toast.makeText(context, "Full Screen Clicked", Toast.LENGTH_SHORT).show();
                        }
                    });
                    /*((VideoTextViewHolder) holder).youtubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
                        @Override
                        public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                            String videoId = MobiUtility.extractYTId(dataClass.getVideoUrl());//"9iIX4PBplAY";
                            youTubePlayer.cueVideo(videoId, 0);
                            //youTubePlayer.loadVideo(videoId, 0);
                            dataClass.setYouTubePlayer(youTubePlayer);
                        }
                    });*/
                    ((VideoTextViewHolder) holder).youtubePlayerView.getYouTubePlayerWhenReady(new YouTubePlayerCallback() {
                        @Override
                        public void onYouTubePlayer(@NotNull YouTubePlayer youTubePlayer) {
                            String videoId = MobiUtility.extractYTId(dataClass.getVideoUrl());//"9iIX4PBplAY";
                            youTubePlayer.cueVideo(videoId, 0);
                            //youTubePlayer.loadVideo(videoId, 0);
                            dataClass.setYouTubePlayer(youTubePlayer);
                        }
                    });
                    dataClass.setPlayerView(((VideoTextViewHolder) holder).youtubePlayerView);

                } else if (dataClass.getVideoType() == MobiConstants.HIS_VIDEO_TYPE_WEBVIEW) {

                    ((VideoTextViewHolder) holder).youtubePlayerView.setVisibility(View.GONE);

                } else {
                    ((VideoTextViewHolder) holder).youtubePlayerView.setVisibility(View.GONE);
                }

                ((VideoTextViewHolder) holder).txtTitle.setText(dataClass.getTitle());
                ((VideoTextViewHolder) holder).txtDescription.setText(dataClass.getDescription());

                setTouchListener(((VideoTextViewHolder) holder).vMainLayout, position);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        //ImageViewHolder
        else if (holder instanceof ImageViewHolder) {
            try {
                Glide.with(context).load(dataClass.getImageName())
                        .diskCacheStrategy(DiskCacheStrategy.ALL).skipMemoryCache(false)

                        .into(((ImageViewHolder) holder).imageView);

                Glide.with(context).load(dataClass.getImageName())
                        .apply(RequestOptions.bitmapTransform(new BlurTransformation(MobiConstants.BLUR_RADIUS, MobiConstants.BLUR_SAMPLING)))
                        .diskCacheStrategy(DiskCacheStrategy.ALL).skipMemoryCache(false)

                        .into(((ImageViewHolder) holder).backgroundImage);

                setTouchListener(((ImageViewHolder) holder).vMainLayout, position);
//                setViewClickListener(((ImageViewHolder) holder).imageView, "Full Image View", position);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        //VideoViewHolder
        else if (holder instanceof VideoViewHolder) {
            try {
                if (dataClass.getVideoType() == MobiConstants.HIS_VIDEO_TYPE_YOUTUBE) {

                    ((VideoViewHolder) holder).youtubePlayerView.setVisibility(View.VISIBLE);

                    ((MainActivity) context).getLifecycle().addObserver(((VideoViewHolder) holder).youtubePlayerView);
                    ((VideoViewHolder) holder).youtubePlayerView.getPlayerUiController().showFullscreenButton(false);
                    ((VideoViewHolder) holder).youtubePlayerView.getPlayerUiController().setFullScreenButtonClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //Toast.makeText(context, "Full Screen Clicked", Toast.LENGTH_SHORT).show();
                        }
                    });
                    /*((VideoViewHolder) holder).youtubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
                        @Override
                        public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                            String videoId = MobiUtility.extractYTId(dataClass.getVideoUrl());//"9iIX4PBplAY";
                            youTubePlayer.cueVideo(videoId, 0);
                            //youTubePlayer.loadVideo(videoId, 0);
                            dataClass.setYouTubePlayer(youTubePlayer);
                        }
                    });*/
                    ((VideoViewHolder) holder).youtubePlayerView.getYouTubePlayerWhenReady(new YouTubePlayerCallback() {
                        @Override
                        public void onYouTubePlayer(@NotNull YouTubePlayer youTubePlayer) {
                            String videoId = MobiUtility.extractYTId(dataClass.getVideoUrl());//"9iIX4PBplAY";
                            youTubePlayer.cueVideo(videoId, 0);
                            //youTubePlayer.loadVideo(videoId, 0);
                            dataClass.setYouTubePlayer(youTubePlayer);
                        }
                    });
                    dataClass.setPlayerView(((VideoViewHolder) holder).youtubePlayerView);

                } else if (dataClass.getVideoType() == MobiConstants.HIS_VIDEO_TYPE_WEBVIEW) {
                    ((VideoViewHolder) holder).youtubePlayerView.setVisibility(View.GONE);
                } else {
                    ((VideoViewHolder) holder).youtubePlayerView.setVisibility(View.GONE);
                }

                setTouchListener(((VideoViewHolder) holder).vMainLayout, position);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        //TextViewHolder
        else if (holder instanceof TextViewHolder) {
            try {
                ((TextViewHolder) holder).txtTitle.setText(dataClass.getTitle());
                ((TextViewHolder) holder).txtDescription.setText(dataClass.getDescription());

                setTouchListener(((TextViewHolder) holder).vMainLayout, position);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        //SurveyImageViewHolder
        else if (holder instanceof SurveyImageViewHolder) {
            try {
                final ArrayList<DashBoardDataClassMain.DashboardQuestions> options = dataClass.getOptions();

                ((SurveyImageViewHolder) holder).rlQuestion.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        ((SurveyImageViewHolder) holder).rlQuestion.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                        int height = ((SurveyImageViewHolder) holder).rlQuestion.getHeight();
                        RelativeLayout.LayoutParams parms = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height / 2);
                        parms.addRule(RelativeLayout.ABOVE, R.id.rlBottomQuiz);
                        ((SurveyImageViewHolder) holder).llHalfLayout.setLayoutParams(parms);
                    }
                });

                //aq.id(txtQuestion).text(dataClass.getQuestion());
                ((SurveyImageViewHolder) holder).txtQuestion.setText(dataClass.getQuestion());
                //aq.id(imageView).image(dataClass.getThumbName());
                ((SurveyImageViewHolder) holder).rlTopMedia.setVisibility(View.GONE);
                ((SurveyImageViewHolder) holder).imageViewBack.setVisibility(View.GONE);

                if (dataClass.getImageType() == 2) {
                    ((SurveyImageViewHolder) holder).rlTopMedia.setVisibility(View.GONE);
                    ((SurveyImageViewHolder) holder).imageViewBack.setVisibility(View.VISIBLE);
                    //aq.id(imageViewBack).image(dataClass.getThumbName());
                    Glide.with(context).load(dataClass.getImageName())
                            .diskCacheStrategy(DiskCacheStrategy.ALL).skipMemoryCache(false)
                            .into(((SurveyImageViewHolder) holder).imageViewBack);
                    //linearContent.setBackgroundColor(context.getResources().getColor(R.color.transparent_white));
                    //linearContent.setGravity(Gravity.CENTER);
                } else {
                    ((SurveyImageViewHolder) holder).rlTopMedia.setVisibility(View.VISIBLE);
                    ((SurveyImageViewHolder) holder).imageViewBack.setVisibility(View.GONE);
                    //aq.id(imageView).image(dataClass.getThumbName());
                    Glide.with(context).load(dataClass.getImageName())
                            .diskCacheStrategy(DiskCacheStrategy.ALL).skipMemoryCache(false)
                            .into(((SurveyImageViewHolder) holder).imageView);
                    //linearContent.setBackgroundColor(context.getResources().getColor(R.color.transparent));
                    //linearContent.setGravity(Gravity.TOP);
                }
                final ArrayList<ProgressBar> progressBarList = new ArrayList<>();

                if (options.size() == 2) {
                    ((SurveyImageViewHolder) holder).optionLayout1.setVisibility(View.VISIBLE);
                    ((SurveyImageViewHolder) holder).optionLayout2.setVisibility(View.VISIBLE);
                    ((SurveyImageViewHolder) holder).optionLayout3.setVisibility(View.GONE);
                    ((SurveyImageViewHolder) holder).optionLayout4.setVisibility(View.GONE);
                } else if (options.size() == 3) {
                    ((SurveyImageViewHolder) holder).optionLayout1.setVisibility(View.VISIBLE);
                    ((SurveyImageViewHolder) holder).optionLayout2.setVisibility(View.VISIBLE);
                    ((SurveyImageViewHolder) holder).optionLayout3.setVisibility(View.VISIBLE);
                    ((SurveyImageViewHolder) holder).optionLayout4.setVisibility(View.GONE);
                } else {
                    ((SurveyImageViewHolder) holder).optionLayout1.setVisibility(View.VISIBLE);
                    ((SurveyImageViewHolder) holder).optionLayout2.setVisibility(View.VISIBLE);
                    ((SurveyImageViewHolder) holder).optionLayout3.setVisibility(View.VISIBLE);
                    ((SurveyImageViewHolder) holder).optionLayout4.setVisibility(View.VISIBLE);
                }

                for (int i = 0; i < options.size(); i++) {

                    final ProgressBar pbAnswer;
                    TextView tvAnswer;
                    TextView tvAvgPercentage;
                    ProgressBar seekBar;

                    if (i == 0) {
                        pbAnswer = ((SurveyImageViewHolder) holder).pbAnswer1;
                        tvAnswer = ((SurveyImageViewHolder) holder).tvAnswer1;
                        tvAvgPercentage = ((SurveyImageViewHolder) holder).tvAvgPercentage1;
                        seekBar = ((SurveyImageViewHolder) holder).seekBar1;
                    } else if (i == 1) {
                        pbAnswer = ((SurveyImageViewHolder) holder).pbAnswer2;
                        tvAnswer = ((SurveyImageViewHolder) holder).tvAnswer2;
                        tvAvgPercentage = ((SurveyImageViewHolder) holder).tvAvgPercentage2;
                        seekBar = ((SurveyImageViewHolder) holder).seekBar2;
                    } else if (i == 2) {
                        pbAnswer = ((SurveyImageViewHolder) holder).pbAnswer3;
                        tvAnswer = ((SurveyImageViewHolder) holder).tvAnswer3;
                        tvAvgPercentage = ((SurveyImageViewHolder) holder).tvAvgPercentage3;
                        seekBar = ((SurveyImageViewHolder) holder).seekBar3;
                    } else {
                        pbAnswer = ((SurveyImageViewHolder) holder).pbAnswer4;
                        tvAnswer = ((SurveyImageViewHolder) holder).tvAnswer4;
                        tvAvgPercentage = ((SurveyImageViewHolder) holder).tvAvgPercentage4;
                        seekBar = ((SurveyImageViewHolder) holder).seekBar4;
                    }

                    pbAnswer.setProgress(0);
                    pbAnswer.setId(options.get(i).getOption());
                    tvAnswer.setText(options.get(i).getValue());

                    if (dataClass.getIsUserSelectedOption() == 0) {
                        pbAnswer.setClickable(true);
                        final int answerPosition = i;
                        pbAnswer.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (fragment instanceof DashboardHummFeedFragment) {
                                    ((DashboardHummFeedFragment) fragment).saveSurveyData(position, answerPosition);
                                }
                            }
                        });
                    } else {
                        pbAnswer.setClickable(false);
                        if (options.get(i).getSelectedOption() == 1) {
                            tvAnswer.setTextColor(context.getResources().getColor(R.color.inshort_color));
                            tvAvgPercentage.setTextColor(context.getResources().getColor(R.color.inshort_color));
                            pbAnswer.setProgress(100);
                            pbAnswer.setProgressDrawable(context.getResources().getDrawable(R.drawable.progress_quiz_correct_drawable));
                            seekBar.setProgressDrawable(context.getResources().getDrawable(R.drawable.progress_survey_drawable_selected_percentage_new));
                        } else {
                            tvAnswer.setTextColor(context.getResources().getColor(R.color.white));
                            tvAvgPercentage.setTextColor(context.getResources().getColor(R.color.white));
                            pbAnswer.setProgress(0);
                            pbAnswer.setProgressDrawable(context.getResources().getDrawable(R.drawable.progress_survey_drawable_quiz_new));
                            seekBar.setProgressDrawable(context.getResources().getDrawable(R.drawable.progress_survey_drawable_percentage_new));
                        }

                        // For display random percentage
                        //options.get(i).setAveragePercentage(new Random().nextInt(100));

                        tvAvgPercentage.setVisibility(View.VISIBLE);
                        seekBar.setVisibility(View.VISIBLE);
                        seekBar.setProgress(Math.round(options.get(i).getAveragePercentage()));
                        tvAvgPercentage.setText(Math.round(options.get(i).getAveragePercentage()) + "%");
                        ProgressBarAnimation anim = new ProgressBarAnimation(seekBar, 0, Math.round(options.get(i).getAveragePercentage()));
                        anim.setDuration(1000);
                        seekBar.startAnimation(anim);
                        seekBar.setClickable(false);

                    }

                    progressBarList.add(pbAnswer);
                    //ll_questions.addView(answerView);
                }

                setTouchListener(((SurveyImageViewHolder) holder).vMainLayout, position);
                setViewClickListener(((SurveyImageViewHolder) holder).imageView, "Full Image View", position);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        //SurveyVideoViewHolder
        else if (holder instanceof SurveyVideoViewHolder) {
            try {
                final ArrayList<DashBoardDataClassMain.DashboardQuestions> options = dataClass.getOptions();

                ((SurveyVideoViewHolder) holder).txtQuestion.setText(dataClass.getQuestion());
                ((SurveyVideoViewHolder) holder).rlQuestion.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        ((SurveyVideoViewHolder) holder).rlQuestion.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                        int height = ((SurveyVideoViewHolder) holder).rlQuestion.getHeight();
                        RelativeLayout.LayoutParams parms = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height / 2);
                        parms.addRule(RelativeLayout.ABOVE, R.id.rlBottomQuiz);
                        ((SurveyVideoViewHolder) holder).llHalfLayout.setLayoutParams(parms);
                    }
                });

                if (dataClass.getVideoType() == MobiConstants.HIS_VIDEO_TYPE_YOUTUBE) {

                    ((SurveyVideoViewHolder) holder).youtubePlayerView.setVisibility(View.VISIBLE);

                    ((MainActivity) context).getLifecycle().addObserver(((SurveyVideoViewHolder) holder).youtubePlayerView);
                    ((SurveyVideoViewHolder) holder).youtubePlayerView.getPlayerUiController().showFullscreenButton(false);
                    ((SurveyVideoViewHolder) holder).youtubePlayerView.getPlayerUiController().setFullScreenButtonClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //Toast.makeText(context, "Full Screen Clicked", Toast.LENGTH_SHORT).show();
                        }
                    });
                    /*((SurveyVideoViewHolder) holder).youtubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
                        @Override
                        public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                            String videoId = MobiUtility.extractYTId(dataClass.getVideoUrl());//"9iIX4PBplAY";
                            youTubePlayer.cueVideo(videoId, 0);
                            //youTubePlayer.loadVideo(videoId, 0);
                            dataClass.setYouTubePlayer(youTubePlayer);
                        }
                    });*/
                    ((SurveyVideoViewHolder) holder).youtubePlayerView.getYouTubePlayerWhenReady(new YouTubePlayerCallback() {
                        @Override
                        public void onYouTubePlayer(@NotNull YouTubePlayer youTubePlayer) {
                            String videoId = MobiUtility.extractYTId(dataClass.getVideoUrl());//"9iIX4PBplAY";
                            youTubePlayer.cueVideo(videoId, 0);
                            //youTubePlayer.loadVideo(videoId, 0);
                            dataClass.setYouTubePlayer(youTubePlayer);
                        }
                    });
                    dataClass.setPlayerView(((SurveyVideoViewHolder) holder).youtubePlayerView);

                } else if (dataClass.getVideoType() == MobiConstants.HIS_VIDEO_TYPE_WEBVIEW) {
                    ((SurveyVideoViewHolder) holder).youtubePlayerView.setVisibility(View.GONE);
                } else {
                    ((SurveyVideoViewHolder) holder).youtubePlayerView.setVisibility(View.GONE);
                }

                final ArrayList<ProgressBar> progressBarList = new ArrayList<>();


                if (options.size() == 2) {
                    ((SurveyVideoViewHolder) holder).optionLayout1.setVisibility(View.VISIBLE);
                    ((SurveyVideoViewHolder) holder).optionLayout2.setVisibility(View.VISIBLE);
                    ((SurveyVideoViewHolder) holder).optionLayout3.setVisibility(View.GONE);
                    ((SurveyVideoViewHolder) holder).optionLayout4.setVisibility(View.GONE);
                } else if (options.size() == 3) {
                    ((SurveyVideoViewHolder) holder).optionLayout1.setVisibility(View.VISIBLE);
                    ((SurveyVideoViewHolder) holder).optionLayout2.setVisibility(View.VISIBLE);
                    ((SurveyVideoViewHolder) holder).optionLayout3.setVisibility(View.VISIBLE);
                    ((SurveyVideoViewHolder) holder).optionLayout4.setVisibility(View.GONE);
                } else {
                    ((SurveyVideoViewHolder) holder).optionLayout1.setVisibility(View.VISIBLE);
                    ((SurveyVideoViewHolder) holder).optionLayout2.setVisibility(View.VISIBLE);
                    ((SurveyVideoViewHolder) holder).optionLayout3.setVisibility(View.VISIBLE);
                    ((SurveyVideoViewHolder) holder).optionLayout4.setVisibility(View.VISIBLE);
                }

                for (int i = 0; i < options.size(); i++) {

                    final ProgressBar pbAnswer;
                    TextView tvAnswer;
                    TextView tvAvgPercentage;
                    ProgressBar seekBar;

                    if (i == 0) {
                        pbAnswer = ((SurveyVideoViewHolder) holder).pbAnswer1;
                        tvAnswer = ((SurveyVideoViewHolder) holder).tvAnswer1;
                        tvAvgPercentage = ((SurveyVideoViewHolder) holder).tvAvgPercentage1;
                        seekBar = ((SurveyVideoViewHolder) holder).seekBar1;
                    } else if (i == 1) {
                        pbAnswer = ((SurveyVideoViewHolder) holder).pbAnswer2;
                        tvAnswer = ((SurveyVideoViewHolder) holder).tvAnswer2;
                        tvAvgPercentage = ((SurveyVideoViewHolder) holder).tvAvgPercentage2;
                        seekBar = ((SurveyVideoViewHolder) holder).seekBar2;
                    } else if (i == 2) {
                        pbAnswer = ((SurveyVideoViewHolder) holder).pbAnswer3;
                        tvAnswer = ((SurveyVideoViewHolder) holder).tvAnswer3;
                        tvAvgPercentage = ((SurveyVideoViewHolder) holder).tvAvgPercentage3;
                        seekBar = ((SurveyVideoViewHolder) holder).seekBar3;
                    } else {
                        pbAnswer = ((SurveyVideoViewHolder) holder).pbAnswer4;
                        tvAnswer = ((SurveyVideoViewHolder) holder).tvAnswer4;
                        tvAvgPercentage = ((SurveyVideoViewHolder) holder).tvAvgPercentage4;
                        seekBar = ((SurveyVideoViewHolder) holder).seekBar4;
                    }

                    pbAnswer.setProgress(0);
                    pbAnswer.setId(options.get(i).getOption());
                    tvAnswer.setText(options.get(i).getValue());

                    if (dataClass.getIsUserSelectedOption() == 0) {
                        pbAnswer.setClickable(true);
                        final int answerPosition = i;
                        pbAnswer.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (fragment instanceof DashboardHummFeedFragment) {
                                    ((DashboardHummFeedFragment) fragment).saveSurveyData(position, answerPosition);
                                }
                            }
                        });
                    } else {
                        pbAnswer.setClickable(false);
                        if (options.get(i).getSelectedOption() == 1) {
                            tvAnswer.setTextColor(context.getResources().getColor(R.color.inshort_color));
                            tvAvgPercentage.setTextColor(context.getResources().getColor(R.color.inshort_color));
                            pbAnswer.setProgress(100);
                            pbAnswer.setProgressDrawable(context.getResources().getDrawable(R.drawable.progress_quiz_correct_drawable));
                            seekBar.setProgressDrawable(context.getResources().getDrawable(R.drawable.progress_survey_drawable_selected_percentage_new));
                        } else {
                            tvAnswer.setTextColor(context.getResources().getColor(R.color.white));
                            tvAvgPercentage.setTextColor(context.getResources().getColor(R.color.white));
                            pbAnswer.setProgress(0);
                            pbAnswer.setProgressDrawable(context.getResources().getDrawable(R.drawable.progress_survey_drawable_quiz_new));
                            seekBar.setProgressDrawable(context.getResources().getDrawable(R.drawable.progress_survey_drawable_percentage_new));
                        }

                        // For display random percentage
                        //options.get(i).setAveragePercentage(new Random().nextInt(100));

                        tvAvgPercentage.setVisibility(View.VISIBLE);
                        seekBar.setVisibility(View.VISIBLE);
                        seekBar.setProgress(Math.round(options.get(i).getAveragePercentage()));
                        tvAvgPercentage.setText(Math.round(options.get(i).getAveragePercentage()) + "%");
                        ProgressBarAnimation anim = new ProgressBarAnimation(seekBar, 0, Math.round(options.get(i).getAveragePercentage()));
                        anim.setDuration(1000);
                        seekBar.startAnimation(anim);
                        seekBar.setClickable(false);

                    }

                    progressBarList.add(pbAnswer);
                    //ll_questions.addView(answerView);
                }

                setTouchListener(((SurveyVideoViewHolder) holder).vMainLayout, position);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        //SurveyTextViewHolder
        else if (holder instanceof SurveyTextViewHolder) {
            try {
                final ArrayList<DashBoardDataClassMain.DashboardQuestions> options = dataClass.getOptions();

                ((SurveyTextViewHolder) holder).rlQuestion.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        ((SurveyTextViewHolder) holder).rlQuestion.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                        int height = ((SurveyTextViewHolder) holder).rlQuestion.getHeight();
                        RelativeLayout.LayoutParams parms = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height / 2);
                        parms.addRule(RelativeLayout.ABOVE, R.id.rlBottomQuiz);
                        ((SurveyTextViewHolder) holder).llHalfLayout.setLayoutParams(parms);
                    }
                });

                ((SurveyTextViewHolder) holder).txtQuestion.setText(dataClass.getQuestion());

                final ArrayList<ProgressBar> progressBarList = new ArrayList<>();

                if (options.size() == 2) {
                    ((SurveyTextViewHolder) holder).optionLayout1.setVisibility(View.VISIBLE);
                    ((SurveyTextViewHolder) holder).optionLayout2.setVisibility(View.VISIBLE);
                    ((SurveyTextViewHolder) holder).optionLayout3.setVisibility(View.GONE);
                    ((SurveyTextViewHolder) holder).optionLayout4.setVisibility(View.GONE);
                } else if (options.size() == 3) {
                    ((SurveyTextViewHolder) holder).optionLayout1.setVisibility(View.VISIBLE);
                    ((SurveyTextViewHolder) holder).optionLayout2.setVisibility(View.VISIBLE);
                    ((SurveyTextViewHolder) holder).optionLayout3.setVisibility(View.VISIBLE);
                    ((SurveyTextViewHolder) holder).optionLayout4.setVisibility(View.GONE);
                } else {
                    ((SurveyTextViewHolder) holder).optionLayout1.setVisibility(View.VISIBLE);
                    ((SurveyTextViewHolder) holder).optionLayout2.setVisibility(View.VISIBLE);
                    ((SurveyTextViewHolder) holder).optionLayout3.setVisibility(View.VISIBLE);
                    ((SurveyTextViewHolder) holder).optionLayout4.setVisibility(View.VISIBLE);
                }

                for (int i = 0; i < options.size(); i++) {

                    final ProgressBar pbAnswer;
                    TextView tvAnswer;
                    TextView tvAvgPercentage;
                    ProgressBar seekBar;

                    if (i == 0) {
                        pbAnswer = ((SurveyTextViewHolder) holder).pbAnswer1;
                        tvAnswer = ((SurveyTextViewHolder) holder).tvAnswer1;
                        tvAvgPercentage = ((SurveyTextViewHolder) holder).tvAvgPercentage1;
                        seekBar = ((SurveyTextViewHolder) holder).seekBar1;
                    } else if (i == 1) {
                        pbAnswer = ((SurveyTextViewHolder) holder).pbAnswer2;
                        tvAnswer = ((SurveyTextViewHolder) holder).tvAnswer2;
                        tvAvgPercentage = ((SurveyTextViewHolder) holder).tvAvgPercentage2;
                        seekBar = ((SurveyTextViewHolder) holder).seekBar2;
                    } else if (i == 2) {
                        pbAnswer = ((SurveyTextViewHolder) holder).pbAnswer3;
                        tvAnswer = ((SurveyTextViewHolder) holder).tvAnswer3;
                        tvAvgPercentage = ((SurveyTextViewHolder) holder).tvAvgPercentage3;
                        seekBar = ((SurveyTextViewHolder) holder).seekBar3;
                    } else {
                        pbAnswer = ((SurveyTextViewHolder) holder).pbAnswer4;
                        tvAnswer = ((SurveyTextViewHolder) holder).tvAnswer4;
                        tvAvgPercentage = ((SurveyTextViewHolder) holder).tvAvgPercentage4;
                        seekBar = ((SurveyTextViewHolder) holder).seekBar4;
                    }

                    pbAnswer.setProgress(0);
                    pbAnswer.setId(options.get(i).getOption());
                    tvAnswer.setText(options.get(i).getValue());

                    if (dataClass.getIsUserSelectedOption() == 0) {
                        pbAnswer.setClickable(true);
                        final int answerPosition = i;
                        pbAnswer.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (fragment instanceof DashboardHummFeedFragment) {
                                    ((DashboardHummFeedFragment) fragment).saveSurveyData(position, answerPosition);
                                }
                            }
                        });
                    } else {
                        pbAnswer.setClickable(false);
                        if (options.get(i).getSelectedOption() == 1) {
                            tvAnswer.setTextColor(context.getResources().getColor(R.color.inshort_color));
                            tvAvgPercentage.setTextColor(context.getResources().getColor(R.color.inshort_color));
                            pbAnswer.setProgress(100);
                            pbAnswer.setProgressDrawable(context.getResources().getDrawable(R.drawable.progress_quiz_correct_drawable));
                            seekBar.setProgressDrawable(context.getResources().getDrawable(R.drawable.progress_survey_drawable_selected_percentage_new));
                        } else {
                            tvAnswer.setTextColor(context.getResources().getColor(R.color.white));
                            tvAvgPercentage.setTextColor(context.getResources().getColor(R.color.white));
                            pbAnswer.setProgress(0);
                            pbAnswer.setProgressDrawable(context.getResources().getDrawable(R.drawable.progress_survey_drawable_quiz_new));
                            seekBar.setProgressDrawable(context.getResources().getDrawable(R.drawable.progress_survey_drawable_percentage_new));
                        }

                        // For display random percentage
                        //options.get(i).setAveragePercentage(new Random().nextInt(100));

                        tvAvgPercentage.setVisibility(View.VISIBLE);
                        seekBar.setVisibility(View.VISIBLE);
                        seekBar.setProgress(Math.round(options.get(i).getAveragePercentage()));
                        tvAvgPercentage.setText(Math.round(options.get(i).getAveragePercentage()) + "%");
                        ProgressBarAnimation anim = new ProgressBarAnimation(seekBar, 0, Math.round(options.get(i).getAveragePercentage()));
                        anim.setDuration(1000);
                        seekBar.startAnimation(anim);
                        seekBar.setClickable(false);

                    }

                    progressBarList.add(pbAnswer);
                    //ll_questions.addView(answerView);
                }

                setTouchListener(((SurveyTextViewHolder) holder).vMainLayout, position);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        //QuizImageViewHolder
        else if (holder instanceof QuizImageViewHolder) {
            try {
                final ArrayList<DashBoardDataClassMain.DashboardQuestions> options = dataClass.getOptions();

                ((QuizImageViewHolder) holder).rlQuestion.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        ((QuizImageViewHolder) holder).rlQuestion.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                        int height = ((QuizImageViewHolder) holder).rlQuestion.getHeight();
                        RelativeLayout.LayoutParams parms = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height / 2);
                        parms.addRule(RelativeLayout.ABOVE, R.id.rlBottomQuiz);
                        ((QuizImageViewHolder) holder).llHalfLayout.setLayoutParams(parms);
                    }
                });

                //aq.id(txtQuestion).text(dataClass.getQuestion());
                ((QuizImageViewHolder) holder).txtQuestion.setText(dataClass.getQuestion());
                //aq.id(imageView).image(dataClass.getThumbName());
                ((QuizImageViewHolder) holder).rlTopMedia.setVisibility(View.GONE);
                ((QuizImageViewHolder) holder).imageViewBack.setVisibility(View.GONE);

                if (dataClass.getImageType() == 2) {
                    ((QuizImageViewHolder) holder).rlTopMedia.setVisibility(View.GONE);
                    ((QuizImageViewHolder) holder).imageViewBack.setVisibility(View.VISIBLE);
                    //aq.id(imageViewBack).image(dataClass.getThumbName());
                    Glide.with(context).load(dataClass.getImageName())
                            .diskCacheStrategy(DiskCacheStrategy.ALL).skipMemoryCache(false)
                            .into(((QuizImageViewHolder) holder).imageViewBack);
                    ((QuizImageViewHolder) holder).rlCardMain.setBackgroundColor(context.getResources().getColor(R.color.transparent_white));
                    //linearContent.setGravity(Gravity.CENTER);
                } else {
                    ((QuizImageViewHolder) holder).rlTopMedia.setVisibility(View.VISIBLE);
                    ((QuizImageViewHolder) holder).imageViewBack.setVisibility(View.GONE);
                    //aq.id(imageView).image(dataClass.getThumbName());
                    Glide.with(context).load(dataClass.getImageName())
                            .diskCacheStrategy(DiskCacheStrategy.ALL).skipMemoryCache(false)
                            .into(((QuizImageViewHolder) holder).imageView);
                    ((QuizImageViewHolder) holder).rlCardMain.setBackgroundColor(context.getResources().getColor(R.color.transparent));
                    //linearContent.setGravity(Gravity.TOP);
                }
                final ArrayList<ProgressBar> progressBarList = new ArrayList<>();

                if (options.size() == 2) {
                    ((QuizImageViewHolder) holder).optionLayout1.setVisibility(View.VISIBLE);
                    ((QuizImageViewHolder) holder).optionLayout2.setVisibility(View.VISIBLE);
                    ((QuizImageViewHolder) holder).optionLayout3.setVisibility(View.GONE);
                    ((QuizImageViewHolder) holder).optionLayout4.setVisibility(View.GONE);
                } else if (options.size() == 3) {
                    ((QuizImageViewHolder) holder).optionLayout1.setVisibility(View.VISIBLE);
                    ((QuizImageViewHolder) holder).optionLayout2.setVisibility(View.VISIBLE);
                    ((QuizImageViewHolder) holder).optionLayout3.setVisibility(View.VISIBLE);
                    ((QuizImageViewHolder) holder).optionLayout4.setVisibility(View.GONE);
                } else {
                    ((QuizImageViewHolder) holder).optionLayout1.setVisibility(View.VISIBLE);
                    ((QuizImageViewHolder) holder).optionLayout2.setVisibility(View.VISIBLE);
                    ((QuizImageViewHolder) holder).optionLayout3.setVisibility(View.VISIBLE);
                    ((QuizImageViewHolder) holder).optionLayout4.setVisibility(View.VISIBLE);
                }

                for (int i = 0; i < options.size(); i++) {

                    final ProgressBar pbAnswer;
                    TextView tvAnswer;
                    TextView tvAvgPercentage;
                    ImageView ivAnswerType;

                    if (i == 0) {
                        pbAnswer = ((QuizImageViewHolder) holder).pbAnswer1;
                        tvAnswer = ((QuizImageViewHolder) holder).tvAnswer1;
                        tvAvgPercentage = ((QuizImageViewHolder) holder).tvAvgPercentage1;
                        ivAnswerType = ((QuizImageViewHolder) holder).ivAnswerType1;
                    } else if (i == 1) {
                        pbAnswer = ((QuizImageViewHolder) holder).pbAnswer2;
                        tvAnswer = ((QuizImageViewHolder) holder).tvAnswer2;
                        tvAvgPercentage = ((QuizImageViewHolder) holder).tvAvgPercentage2;
                        ivAnswerType = ((QuizImageViewHolder) holder).ivAnswerType2;
                    } else if (i == 2) {
                        pbAnswer = ((QuizImageViewHolder) holder).pbAnswer3;
                        tvAnswer = ((QuizImageViewHolder) holder).tvAnswer3;
                        tvAvgPercentage = ((QuizImageViewHolder) holder).tvAvgPercentage3;
                        ivAnswerType = ((QuizImageViewHolder) holder).ivAnswerType3;
                    } else {
                        pbAnswer = ((QuizImageViewHolder) holder).pbAnswer4;
                        tvAnswer = ((QuizImageViewHolder) holder).tvAnswer4;
                        tvAvgPercentage = ((QuizImageViewHolder) holder).tvAvgPercentage4;
                        ivAnswerType = ((QuizImageViewHolder) holder).ivAnswerType4;
                    }

                    pbAnswer.setProgress(0);
                    pbAnswer.setId(options.get(i).getOption());
                    tvAnswer.setText(options.get(i).getValue());

                    if (dataClass.getIsUserSelectedOption() == 0) {
                        pbAnswer.setClickable(true);
                        final int answerPosition = i;
                        pbAnswer.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (fragment instanceof DashboardHummFeedFragment) {
                                    ((DashboardHummFeedFragment) fragment).saveSurveyData(position, answerPosition);
                                }
                            }
                        });
                    } else {
                        pbAnswer.setClickable(false);
                        tvAvgPercentage.setVisibility(View.GONE);
                        if (options.get(i).getSelectedOption() == 1) {
                            ivAnswerType.setVisibility(View.VISIBLE);
                            if (String.valueOf(options.get(i).getOption()).equalsIgnoreCase(dataClass.getCorrectOption())) {

                                pbAnswer.setProgress(100);
                                pbAnswer.setProgressDrawable(context.getResources().getDrawable(R.drawable.progress_quiz_correct_drawable));
                                ivAnswerType.setImageResource(R.drawable.quiz_correct_icon);
                                tvAnswer.setTextColor(context.getResources().getColor(R.color.inshort_color));
                                ivAnswerType.setColorFilter(ContextCompat.getColor(context, R.color.inshort_color));
                            } else {
                                tvAnswer.setTextColor(context.getResources().getColor(R.color.white));
                                pbAnswer.setProgress(100);
                                pbAnswer.setProgressDrawable(context.getResources().getDrawable(R.drawable.progress_quiz_wrong_drawable));
                                ivAnswerType.setImageResource(R.drawable.quiz_wrong_icons);
                                ivAnswerType.setColorFilter(ContextCompat.getColor(context, R.color.white));
                            }

                        } else if (String.valueOf(options.get(i).getOption()).equalsIgnoreCase(dataClass.getCorrectOption())) {
                            ivAnswerType.setVisibility(View.VISIBLE);
                            pbAnswer.setProgress(100);
                            tvAnswer.setTextColor(context.getResources().getColor(R.color.inshort_color));
                            pbAnswer.setProgressDrawable(context.getResources().getDrawable(R.drawable.progress_quiz_correct_drawable));
                            ivAnswerType.setImageResource(R.drawable.quiz_correct_icon);
                            ivAnswerType.setColorFilter(ContextCompat.getColor(context, R.color.inshort_color));
                        } else {
                            tvAnswer.setTextColor(context.getResources().getColor(R.color.white));
                            pbAnswer.setProgress(0);
                            pbAnswer.setProgressDrawable(context.getResources().getDrawable(R.drawable.progress_survey_drawable_quiz_new));
                            ivAnswerType.setVisibility(View.GONE);
                        }

                    }

                    progressBarList.add(pbAnswer);
                    //ll_questions.addView(answerView);
                }

                setTouchListener(((QuizImageViewHolder) holder).vMainLayout, position);
                setViewClickListener(((QuizImageViewHolder) holder).imageView, "Full Image View", position);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        //QuizVideoViewHolder
        else if (holder instanceof QuizVideoViewHolder) {
            try {
                final ArrayList<DashBoardDataClassMain.DashboardQuestions> options = dataClass.getOptions();

                ((QuizVideoViewHolder) holder).txtQuestion.setText(dataClass.getQuestion());
                ((QuizVideoViewHolder) holder).rlQuestion.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        ((QuizVideoViewHolder) holder).rlQuestion.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                        int height = ((QuizVideoViewHolder) holder).rlQuestion.getHeight();
                        RelativeLayout.LayoutParams parms = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height / 2);
                        parms.addRule(RelativeLayout.ABOVE, R.id.rlBottomQuiz);
                        ((QuizVideoViewHolder) holder).llHalfLayout.setLayoutParams(parms);
                    }
                });

                if (dataClass.getVideoType() == MobiConstants.HIS_VIDEO_TYPE_YOUTUBE) {

                    ((QuizVideoViewHolder) holder).youtubePlayerView.setVisibility(View.VISIBLE);

                    ((MainActivity) context).getLifecycle().addObserver(((QuizVideoViewHolder) holder).youtubePlayerView);
                    ((QuizVideoViewHolder) holder).youtubePlayerView.getPlayerUiController().showFullscreenButton(false);
                    ((QuizVideoViewHolder) holder).youtubePlayerView.getPlayerUiController().setFullScreenButtonClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //Toast.makeText(context, "Full Screen Clicked", Toast.LENGTH_SHORT).show();
                        }
                    });
                    /*((QuizVideoViewHolder) holder).youtubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
                        @Override
                        public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                            String videoId = MobiUtility.extractYTId(dataClass.getVideoUrl());//"9iIX4PBplAY";
                            youTubePlayer.cueVideo(videoId, 0);
                            //youTubePlayer.loadVideo(videoId, 0);
                            dataClass.setYouTubePlayer(youTubePlayer);
                        }
                    });*/
                    ((QuizVideoViewHolder) holder).youtubePlayerView.getYouTubePlayerWhenReady(new YouTubePlayerCallback() {
                        @Override
                        public void onYouTubePlayer(@NotNull YouTubePlayer youTubePlayer) {
                            String videoId = MobiUtility.extractYTId(dataClass.getVideoUrl());//"9iIX4PBplAY";
                            youTubePlayer.cueVideo(videoId, 0);
                            //youTubePlayer.loadVideo(videoId, 0);
                            dataClass.setYouTubePlayer(youTubePlayer);
                        }
                    });
                    dataClass.setPlayerView(((QuizVideoViewHolder) holder).youtubePlayerView);

                } else if (dataClass.getVideoType() == MobiConstants.HIS_VIDEO_TYPE_WEBVIEW) {
                    ((QuizVideoViewHolder) holder).youtubePlayerView.setVisibility(View.GONE);
                } else {
                    ((QuizVideoViewHolder) holder).youtubePlayerView.setVisibility(View.GONE);
                }

                final ArrayList<ProgressBar> progressBarList = new ArrayList<>();

                if (options.size() == 2) {
                    ((QuizVideoViewHolder) holder).optionLayout1.setVisibility(View.VISIBLE);
                    ((QuizVideoViewHolder) holder).optionLayout2.setVisibility(View.VISIBLE);
                    ((QuizVideoViewHolder) holder).optionLayout3.setVisibility(View.GONE);
                    ((QuizVideoViewHolder) holder).optionLayout4.setVisibility(View.GONE);
                } else if (options.size() == 3) {
                    ((QuizVideoViewHolder) holder).optionLayout1.setVisibility(View.VISIBLE);
                    ((QuizVideoViewHolder) holder).optionLayout2.setVisibility(View.VISIBLE);
                    ((QuizVideoViewHolder) holder).optionLayout3.setVisibility(View.VISIBLE);
                    ((QuizVideoViewHolder) holder).optionLayout4.setVisibility(View.GONE);
                } else {
                    ((QuizVideoViewHolder) holder).optionLayout1.setVisibility(View.VISIBLE);
                    ((QuizVideoViewHolder) holder).optionLayout2.setVisibility(View.VISIBLE);
                    ((QuizVideoViewHolder) holder).optionLayout3.setVisibility(View.VISIBLE);
                    ((QuizVideoViewHolder) holder).optionLayout4.setVisibility(View.VISIBLE);
                }

                for (int i = 0; i < options.size(); i++) {

                    final ProgressBar pbAnswer;
                    TextView tvAnswer;
                    TextView tvAvgPercentage;
                    ImageView ivAnswerType;

                    if (i == 0) {
                        pbAnswer = ((QuizVideoViewHolder) holder).pbAnswer1;
                        tvAnswer = ((QuizVideoViewHolder) holder).tvAnswer1;
                        tvAvgPercentage = ((QuizVideoViewHolder) holder).tvAvgPercentage1;
                        ivAnswerType = ((QuizVideoViewHolder) holder).ivAnswerType1;
                    } else if (i == 1) {
                        pbAnswer = ((QuizVideoViewHolder) holder).pbAnswer2;
                        tvAnswer = ((QuizVideoViewHolder) holder).tvAnswer2;
                        tvAvgPercentage = ((QuizVideoViewHolder) holder).tvAvgPercentage2;
                        ivAnswerType = ((QuizVideoViewHolder) holder).ivAnswerType2;
                    } else if (i == 2) {
                        pbAnswer = ((QuizVideoViewHolder) holder).pbAnswer3;
                        tvAnswer = ((QuizVideoViewHolder) holder).tvAnswer3;
                        tvAvgPercentage = ((QuizVideoViewHolder) holder).tvAvgPercentage3;
                        ivAnswerType = ((QuizVideoViewHolder) holder).ivAnswerType3;
                    } else {
                        pbAnswer = ((QuizVideoViewHolder) holder).pbAnswer4;
                        tvAnswer = ((QuizVideoViewHolder) holder).tvAnswer4;
                        tvAvgPercentage = ((QuizVideoViewHolder) holder).tvAvgPercentage4;
                        ivAnswerType = ((QuizVideoViewHolder) holder).ivAnswerType4;
                    }

                    pbAnswer.setProgress(0);
                    pbAnswer.setId(options.get(i).getOption());
                    tvAnswer.setText(options.get(i).getValue());

                    if (dataClass.getIsUserSelectedOption() == 0) {
                        pbAnswer.setClickable(true);
                        final int answerPosition = i;
                        pbAnswer.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (fragment instanceof DashboardHummFeedFragment) {
                                    ((DashboardHummFeedFragment) fragment).saveSurveyData(position, answerPosition);
                                }
                            }
                        });
                    } else {
                        pbAnswer.setClickable(false);
                        tvAvgPercentage.setVisibility(View.GONE);
                        if (options.get(i).getSelectedOption() == 1) {
                            ivAnswerType.setVisibility(View.VISIBLE);
                            if (String.valueOf(options.get(i).getOption()).equalsIgnoreCase(dataClass.getCorrectOption())) {

                                tvAnswer.setTextColor(context.getResources().getColor(R.color.inshort_color));
                                pbAnswer.setProgress(100);
                                pbAnswer.setProgressDrawable(context.getResources().getDrawable(R.drawable.progress_quiz_correct_drawable));
                                ivAnswerType.setImageResource(R.drawable.quiz_correct_icon);
                                ivAnswerType.setColorFilter(ContextCompat.getColor(context, R.color.inshort_color));
                            } else {
                                tvAnswer.setTextColor(context.getResources().getColor(R.color.white));
                                pbAnswer.setProgress(100);
                                pbAnswer.setProgressDrawable(context.getResources().getDrawable(R.drawable.progress_quiz_wrong_drawable));
                                ivAnswerType.setImageResource(R.drawable.quiz_wrong_icons);
                                ivAnswerType.setColorFilter(ContextCompat.getColor(context, R.color.white));
                            }

                        } else if (String.valueOf(options.get(i).getOption()).equalsIgnoreCase(dataClass.getCorrectOption())) {
                            ivAnswerType.setVisibility(View.VISIBLE);
                            pbAnswer.setProgress(100);
                            tvAnswer.setTextColor(context.getResources().getColor(R.color.inshort_color));
                            pbAnswer.setProgressDrawable(context.getResources().getDrawable(R.drawable.progress_quiz_correct_drawable));
                            ivAnswerType.setImageResource(R.drawable.quiz_correct_icon);
                            ivAnswerType.setColorFilter(ContextCompat.getColor(context, R.color.inshort_color));
                        } else {
                            tvAnswer.setTextColor(context.getResources().getColor(R.color.white));
                            pbAnswer.setProgress(0);
                            pbAnswer.setProgressDrawable(context.getResources().getDrawable(R.drawable.progress_survey_drawable_quiz_new));
                            ivAnswerType.setVisibility(View.GONE);
                        }

                    }

                    progressBarList.add(pbAnswer);
                    //ll_questions.addView(answerView);
                }

                setTouchListener(((QuizVideoViewHolder) holder).vMainLayout, position);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        //QuizTextViewHolder
        else if (holder instanceof QuizTextViewHolder) {
            try {
                final ArrayList<DashBoardDataClassMain.DashboardQuestions> options = dataClass.getOptions();

                ((QuizTextViewHolder) holder).rlQuestion.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        ((QuizTextViewHolder) holder).rlQuestion.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                        int height = ((QuizTextViewHolder) holder).rlQuestion.getHeight();
                        RelativeLayout.LayoutParams parms = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height / 2);
                        parms.addRule(RelativeLayout.ABOVE, R.id.rlBottomQuiz);
                        ((QuizTextViewHolder) holder).llHalfLayout.setLayoutParams(parms);
                    }
                });

                ((QuizTextViewHolder) holder).txtQuestion.setText(dataClass.getQuestion());

                final ArrayList<ProgressBar> progressBarList = new ArrayList<>();

                if (options.size() == 2) {
                    ((QuizTextViewHolder) holder).optionLayout1.setVisibility(View.VISIBLE);
                    ((QuizTextViewHolder) holder).optionLayout2.setVisibility(View.VISIBLE);
                    ((QuizTextViewHolder) holder).optionLayout3.setVisibility(View.GONE);
                    ((QuizTextViewHolder) holder).optionLayout4.setVisibility(View.GONE);
                } else if (options.size() == 3) {
                    ((QuizTextViewHolder) holder).optionLayout1.setVisibility(View.VISIBLE);
                    ((QuizTextViewHolder) holder).optionLayout2.setVisibility(View.VISIBLE);
                    ((QuizTextViewHolder) holder).optionLayout3.setVisibility(View.VISIBLE);
                    ((QuizTextViewHolder) holder).optionLayout4.setVisibility(View.GONE);
                } else {
                    ((QuizTextViewHolder) holder).optionLayout1.setVisibility(View.VISIBLE);
                    ((QuizTextViewHolder) holder).optionLayout2.setVisibility(View.VISIBLE);
                    ((QuizTextViewHolder) holder).optionLayout3.setVisibility(View.VISIBLE);
                    ((QuizTextViewHolder) holder).optionLayout4.setVisibility(View.VISIBLE);
                }

                for (int i = 0; i < options.size(); i++) {

                    final ProgressBar pbAnswer;
                    TextView tvAnswer;
                    TextView tvAvgPercentage;
                    ImageView ivAnswerType;

                    if (i == 0) {
                        pbAnswer = ((QuizTextViewHolder) holder).pbAnswer1;
                        tvAnswer = ((QuizTextViewHolder) holder).tvAnswer1;
                        tvAvgPercentage = ((QuizTextViewHolder) holder).tvAvgPercentage1;
                        ivAnswerType = ((QuizTextViewHolder) holder).ivAnswerType1;
                    } else if (i == 1) {
                        pbAnswer = ((QuizTextViewHolder) holder).pbAnswer2;
                        tvAnswer = ((QuizTextViewHolder) holder).tvAnswer2;
                        tvAvgPercentage = ((QuizTextViewHolder) holder).tvAvgPercentage2;
                        ivAnswerType = ((QuizTextViewHolder) holder).ivAnswerType2;
                    } else if (i == 2) {
                        pbAnswer = ((QuizTextViewHolder) holder).pbAnswer3;
                        tvAnswer = ((QuizTextViewHolder) holder).tvAnswer3;
                        tvAvgPercentage = ((QuizTextViewHolder) holder).tvAvgPercentage3;
                        ivAnswerType = ((QuizTextViewHolder) holder).ivAnswerType3;
                    } else {
                        pbAnswer = ((QuizTextViewHolder) holder).pbAnswer4;
                        tvAnswer = ((QuizTextViewHolder) holder).tvAnswer4;
                        tvAvgPercentage = ((QuizTextViewHolder) holder).tvAvgPercentage4;
                        ivAnswerType = ((QuizTextViewHolder) holder).ivAnswerType4;
                    }

                    pbAnswer.setProgress(0);
                    pbAnswer.setId(options.get(i).getOption());
                    tvAnswer.setText(options.get(i).getValue());

                    if (dataClass.getIsUserSelectedOption() == 0) {
                        pbAnswer.setClickable(true);
                        final int answerPosition = i;
                        pbAnswer.setProgress(0);
                        pbAnswer.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (fragment instanceof DashboardHummFeedFragment) {
                                    ((DashboardHummFeedFragment) fragment).saveSurveyData(position, answerPosition);
                                }
                            }
                        });
                    } else {
                        pbAnswer.setClickable(false);
                        tvAvgPercentage.setVisibility(View.GONE);
                        if (options.get(i).getSelectedOption() == 1) {
                            ivAnswerType.setVisibility(View.VISIBLE);
                            if (String.valueOf(options.get(i).getOption()).equalsIgnoreCase(dataClass.getCorrectOption())) {
                                pbAnswer.setProgress(100);
                                pbAnswer.setProgressDrawable(context.getResources().getDrawable(R.drawable.progress_quiz_correct_drawable));
                                ivAnswerType.setImageResource(R.drawable.quiz_correct_icon);
                                tvAnswer.setTextColor(context.getResources().getColor(R.color.inshort_color));
                                ivAnswerType.setColorFilter(ContextCompat.getColor(context, R.color.inshort_color));
                            } else {
                                tvAnswer.setTextColor(context.getResources().getColor(R.color.white));
                                pbAnswer.setProgress(100);
                                pbAnswer.setProgressDrawable(context.getResources().getDrawable(R.drawable.progress_quiz_wrong_drawable));
                                ivAnswerType.setImageResource(R.drawable.quiz_wrong_icons);
                                tvAnswer.setTextColor(context.getResources().getColor(R.color.white));
                                ivAnswerType.setColorFilter(ContextCompat.getColor(context, R.color.white));
                            }

                        } else if (String.valueOf(options.get(i).getOption()).equalsIgnoreCase(dataClass.getCorrectOption())) {
                            ivAnswerType.setVisibility(View.VISIBLE);
                            pbAnswer.setProgress(100);
                            pbAnswer.setProgressDrawable(context.getResources().getDrawable(R.drawable.progress_quiz_correct_drawable));
                            ivAnswerType.setImageResource(R.drawable.quiz_correct_icon);
                            tvAnswer.setTextColor(context.getResources().getColor(R.color.inshort_color));
                            ivAnswerType.setColorFilter(ContextCompat.getColor(context, R.color.inshort_color));
                        } else {
                            tvAnswer.setTextColor(context.getResources().getColor(R.color.black));
                            pbAnswer.setProgress(0);
                            pbAnswer.setProgressDrawable(context.getResources().getDrawable(R.drawable.progress_survey_drawable_quiz_new));
                            ivAnswerType.setVisibility(View.GONE);
                        }

                    }

                    progressBarList.add(pbAnswer);
                    //ll_questions.addView(answerView);
                }

                setTouchListener(((QuizTextViewHolder) holder).vMainLayout, position);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        //MedicineConfirmationViewHolder
        else if (holder instanceof MedicineConfirmationViewHolder) {
            try {
                if (dataClass.getIsUserSelectedOption() == 0) {

                    ((MedicineConfirmationViewHolder) holder).cardConfirmationPending.setVisibility(View.VISIBLE);
                    ((MedicineConfirmationViewHolder) holder).cardConfirmationComplete.setVisibility(View.GONE);

                    if (dataClass.getId() == 1) {
                        //Morning
                        ((MedicineConfirmationViewHolder) holder).tvReminderTimePending.setText(R.string.morning_reminder);
                    } else if (dataClass.getId() == 2) {
                        ((MedicineConfirmationViewHolder) holder).tvReminderTimePending.setText(R.string.afternoon_reminder);
                    } else {
                        ((MedicineConfirmationViewHolder) holder).tvReminderTimePending.setText(R.string.evening_reminder);
                    }


                    if (dataClass.getMediaType().equalsIgnoreCase(MobiConstants.HIS_MEDIA_TYPE_GIF)) {
                        Glide.with(context)
                                .asGif()
                                .diskCacheStrategy(DiskCacheStrategy.ALL).skipMemoryCache(false)
                                .load(dataClass.getImageName())
                                /*.apply(new RequestOptions()
                                        .error(R.drawable.mom_child_bc)
                                        .placeholder(R.drawable.mom_child_bc))*/
                                .into(((MedicineConfirmationViewHolder) holder).ivContent);
                    } else {
                        Glide.with(context)
                                .load(dataClass.getImageName())
                                /*.apply(new RequestOptions()
                                        .error(R.drawable.mom_child_bc)
                                        .placeholder(R.drawable.mom_child_bc))*/
                                .diskCacheStrategy(DiskCacheStrategy.ALL).skipMemoryCache(false)
                                .into(((MedicineConfirmationViewHolder) holder).ivContent);
                    }

                    final ArrayList<DashBoardDataClassMain.DashboardQuestions> options = dataClass.getOptions();

                    if (options.size() >= 2) {
                        for (int i = 0; i < options.size(); i++) {

                            final int answerPosition = i;

                            if (i == 0) {

                                ((MedicineConfirmationViewHolder) holder).btnPositive.setId(options.get(i).getOption());
                                ((MedicineConfirmationViewHolder) holder).btnPositive.setText(options.get(i).getValue());
                                ((MedicineConfirmationViewHolder) holder).btnPositive.setTextColor(context.getResources().getColor(R.color.white));
                                //((MedicineConfirmationViewHolder) holder).btnPositive.getBackground().setColorFilter(context.getResources().getColor(R.color.cv_yello), PorterDuff.Mode.SRC_ATOP);

                                ((MedicineConfirmationViewHolder) holder).btnPositive.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        //Toast.makeText(context, options.get(answerPosition).getValue() + " Clicked", Toast.LENGTH_SHORT).show();
                                        if (dataClass.getIsUserSelectedOption() == 0) {
//                                            ((DashboardHummFeedFragment) fragment).saveMedConfirmation(position, answerPosition, dataClass.getId());
                                        } else {
                                            Toast.makeText(context, "You have already given your medicine taken confirmation..", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });

                            } else {
                                ((MedicineConfirmationViewHolder) holder).btnNegative.setId(options.get(i).getOption());
                                ((MedicineConfirmationViewHolder) holder).btnNegative.setText(options.get(i).getValue());
                                ((MedicineConfirmationViewHolder) holder).btnNegative.setTextColor(context.getResources().getColor(R.color.white));
                                //((MedicineConfirmationViewHolder) holder).btnNegative.getBackground().setColorFilter(context.getResources().getColor(R.color.logo_blue), PorterDuff.Mode.SRC_ATOP);

                                ((MedicineConfirmationViewHolder) holder).btnNegative.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        //Toast.makeText(context, options.get(answerPosition).getValue() + " Clicked", Toast.LENGTH_SHORT).show();
                                        if (dataClass.getIsUserSelectedOption() == 0) {
//                                            ((DashboardHummFeedFragment) fragment).saveMedConfirmation(position, answerPosition, dataClass.getId());
                                        } else {
                                            Toast.makeText(context, "You have already given your medicine taken confirmation..", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }
                        }
                    }

                } else {

                    ((MedicineConfirmationViewHolder) holder).cardConfirmationPending.setVisibility(View.GONE);
                    ((MedicineConfirmationViewHolder) holder).cardConfirmationComplete.setVisibility(View.VISIBLE);

                    if (dataClass.getId() == 1) {
                        //Morning
                        ((MedicineConfirmationViewHolder) holder).tvReminderTimeComplete.setText(R.string.morning_reminder);
                    } else if (dataClass.getId() == 2) {
                        ((MedicineConfirmationViewHolder) holder).tvReminderTimeComplete.setText(R.string.afternoon_reminder);
                    } else {
                        ((MedicineConfirmationViewHolder) holder).tvReminderTimeComplete.setText(R.string.evening_reminder);
                    }

                    if (dataClass.getCompleteType().equalsIgnoreCase(MobiConstants.HIS_MEDIA_TYPE_GIF)) {
                        Glide.with(context)
                                .asGif()
                                .load(dataClass.getCompleteImage())
                                /*.apply(new RequestOptions()
                                        .error(R.drawable.mom_child_bc)
                                        .placeholder(R.drawable.mom_child_bc))*/
                                .diskCacheStrategy(DiskCacheStrategy.ALL).skipMemoryCache(false)
                                .into(((MedicineConfirmationViewHolder) holder).ivConfirmationComplete);
                    } else {
                        Glide.with(context)
//                                .load(dataClass.getCompleteImage())
                                .load(dataClass.getCompleteImage())
                                /*.apply(new RequestOptions()
                                        .error(R.drawable.mom_child_bc)
                                        .placeholder(R.drawable.mom_child_bc))*/
                                .diskCacheStrategy(DiskCacheStrategy.ALL).skipMemoryCache(false)
                                .into(((MedicineConfirmationViewHolder) holder).ivConfirmationComplete);
                    }
                }
                setTouchListener(((MedicineConfirmationViewHolder) holder).vMainLayout, position);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        //NormalInstructionViewHolder
        else if (holder instanceof NormalInstructionViewHolder) {
            try {
                if (dataClass.getMediaType().equalsIgnoreCase(MobiConstants.HIS_MEDIA_TYPE_GIF)) {
                    Glide.with(context)
                            .asGif()
                            .load(dataClass.getImageName())
                            .diskCacheStrategy(DiskCacheStrategy.ALL).skipMemoryCache(false)
                            .into(((NormalInstructionViewHolder) holder).imageView);
                } else {
                    Glide.with(context)
                            .load(dataClass.getImageName())
                            .diskCacheStrategy(DiskCacheStrategy.ALL).skipMemoryCache(false)
                            .into(((NormalInstructionViewHolder) holder).imageView);
                }
                ((NormalInstructionViewHolder) holder).tvInstructionTitle.setText(dataClass.getTitle());
                ((NormalInstructionViewHolder) holder).tvInstruction.setText(dataClass.getSortDescription());
                int color = Color.  parseColor(dataClass.getBackgroundColor());
                ((NormalInstructionViewHolder) holder).llAdvanceInstructionBox.setBackgroundColor(color);


                try{
                    if(dataClass.getFont_color()!=null){
                        if(!dataClass.getFont_color().isEmpty() && !dataClass.getFont_color().equalsIgnoreCase("")){
                            int fontColor = Color.parseColor(dataClass.getFont_color());
                            ((NormalInstructionViewHolder) holder).tvInstructionTitle.setTextColor(fontColor);
                            ((NormalInstructionViewHolder) holder).tvInstruction.setTextColor(fontColor);


                        }
                        else{
                            ((NormalInstructionViewHolder) holder).tvInstructionTitle.setTextColor(context.getResources().getColor(R.color.black));
                            ((NormalInstructionViewHolder) holder).tvInstruction.setTextColor(context.getResources().getColor(R.color.black));
                        }
                    }
                    else{
                        ((NormalInstructionViewHolder) holder).tvInstructionTitle.setTextColor(context.getResources().getColor(R.color.black));
                        ((NormalInstructionViewHolder) holder).tvInstruction.setTextColor(context.getResources().getColor(R.color.black));
                    }
                }
                catch (Exception e){
                    e.printStackTrace();
                    ((NormalInstructionViewHolder) holder).tvInstructionTitle.setTextColor(context.getResources().getColor(R.color.black));
                    ((NormalInstructionViewHolder) holder).tvInstruction.setTextColor(context.getResources().getColor(R.color.black));
                }
                setTouchListener(((NormalInstructionViewHolder) holder).vMainLayout, position);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        //AdvanceInstructionViewHolder
        else if (holder instanceof AdvanceInstructionViewHolder) {
            try {
                if (dataClass.getMediaType().equalsIgnoreCase(MobiConstants.HIS_MEDIA_TYPE_GIF)) {
                    Glide.with(context)
                            .asGif()
                            .load(dataClass.getImageName())
                            /*.apply(new RequestOptions()
                                    .error(R.drawable.mom_child_bc)
                                    .placeholder(R.drawable.mom_child_bc))*/
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .skipMemoryCache(false)
                            .skipMemoryCache(true)
                            .into(((AdvanceInstructionViewHolder) holder).imageView);
                } else {
                    Glide.with(context)
                            .load(dataClass.getImageName())
                            /*.apply(new RequestOptions()
                                    .error(R.drawable.mom_child_bc)
                                    .placeholder(R.drawable.mom_child_bc))*/
                            .diskCacheStrategy(DiskCacheStrategy.ALL).skipMemoryCache(false)
                            .into(((AdvanceInstructionViewHolder) holder).imageView);
                }


                int color = Color.parseColor(dataClass.getBackgroundColor());
                ((AdvanceInstructionViewHolder) holder).llAdvanceInstructionBox.setBackgroundColor(color);
                ((AdvanceInstructionViewHolder) holder).tvInstructionTitle.setText(dataClass.getTitle());
                ((AdvanceInstructionViewHolder) holder).tvInstruction.setText(dataClass.getSortDescription());

                try{
                    if(dataClass.getFont_color()!=null){
                        if(!dataClass.getFont_color().isEmpty() && !dataClass.getFont_color().equalsIgnoreCase("")){
                            int fontColor = Color.parseColor(dataClass.getFont_color());
                            ((AdvanceInstructionViewHolder) holder).tvInstructionTitle.setTextColor(fontColor);
                            ((AdvanceInstructionViewHolder) holder).tvInstruction.setTextColor(fontColor);


                        }
                        else{
                            ((AdvanceInstructionViewHolder) holder).tvInstructionTitle.setTextColor(context.getResources().getColor(R.color.black));
                            ((AdvanceInstructionViewHolder) holder).tvInstruction.setTextColor(context.getResources().getColor(R.color.black));
                        }
                    }
                    else{
                        ((AdvanceInstructionViewHolder) holder).tvInstructionTitle.setTextColor(context.getResources().getColor(R.color.black));
                        ((AdvanceInstructionViewHolder) holder).tvInstruction.setTextColor(context.getResources().getColor(R.color.black));
                    }
                }
                catch (Exception e){
                    e.printStackTrace();
                    ((AdvanceInstructionViewHolder) holder).tvInstructionTitle.setTextColor(context.getResources().getColor(R.color.black));
                    ((AdvanceInstructionViewHolder) holder).tvInstruction.setTextColor(context.getResources().getColor(R.color.black));
                }

                setTouchListener(((AdvanceInstructionViewHolder) holder).vMainLayout, position);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        //VaccineConfirmationViewHolder
        else if (holder instanceof VaccineConfirmationViewHolder) {
            try {
                ((VaccineConfirmationViewHolder) holder).rvVaccineList.setLayoutManager(new LinearLayoutManager(context));

                String gender = "";
                if (dataClass.getChildGender().equalsIgnoreCase("male")) {
                    gender = context.getResources().getString(R.string.lbl_son).toLowerCase();
                } else {
                    gender = context.getResources().getString(R.string.lbl_daughter).toLowerCase();
                }
                ((VaccineConfirmationViewHolder) holder).tvContentText.setText(context.getResources().
                        getString(R.string.txt_first_step_to_secure_your_son_babo_s_future_is_to_vaccinate, gender, dataClass.getChildName()));

                ((VaccineConfirmationViewHolder) holder).tvChildName.setText(dataClass.getChildName());

                Glide.with(context)
                        .load(dataClass.getImageName())
                        .apply(new RequestOptions()
                                .error(R.drawable.bcg_vacc_location)
                                .placeholder(R.drawable.bcg_vacc_location))
                        .diskCacheStrategy(DiskCacheStrategy.ALL).skipMemoryCache(false)
                        .into(((VaccineConfirmationViewHolder) holder).ivBabyImage);

                ArrayList<DashBoardDataClassMain.VaccineClass> arrayList = new ArrayList<>();
                for (int i = 0; i < dataClass.getVaccineList().size(); i++) {
                    DashBoardDataClassMain.VaccineClass vaccineClass = dataClass.getVaccineList().get(i);
                    arrayList.add(vaccineClass);
                }
                InshortVaccineAdapter vaccineAdapter = new InshortVaccineAdapter(context, arrayList);
                ((VaccineConfirmationViewHolder) holder).rvVaccineList.setAdapter(vaccineAdapter);

                ((VaccineConfirmationViewHolder) holder).tvDone.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            ((DashboardHummFeedFragment) fragment).goToNextPage(position);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

                ((VaccineConfirmationViewHolder) holder).ivInjection1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {

                            ArrayList<ObjectAnimator> arrayListObjectAnimators = new ArrayList<ObjectAnimator>(); //ArrayList of ObjectAnimators

                            ObjectAnimator animView1 = ObjectAnimator.ofFloat(((VaccineConfirmationViewHolder) holder).llFirstLayout, View.TRANSLATION_X, 0, view.getRootView().getWidth());
                            arrayListObjectAnimators.add(animView1);
                            ObjectAnimator animView2 = ObjectAnimator.ofFloat(((VaccineConfirmationViewHolder) holder).llSecondLayout, View.TRANSLATION_X, -view.getRootView().getWidth(), 0);
                            arrayListObjectAnimators.add(animView2);

                            ObjectAnimator[] objectAnimators = arrayListObjectAnimators.toArray(new ObjectAnimator[arrayListObjectAnimators.size()]);
                            AnimatorSet animSetXY = new AnimatorSet();
                            animSetXY.playTogether(objectAnimators);
                            animSetXY.setDuration(500);
                            animSetXY.start();
                        } else {
                            ((VaccineConfirmationViewHolder) holder).llFirstLayout.setVisibility(View.GONE);
                            ((VaccineConfirmationViewHolder) holder).llSecondLayout.setVisibility(View.VISIBLE);
                            ((VaccineConfirmationViewHolder) holder).llThirdLayout.setVisibility(View.GONE);
                        }
                    }
                });

                ((VaccineConfirmationViewHolder) holder).tvWhyVaccine.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {

                            ArrayList<ObjectAnimator> arrayListObjectAnimators = new ArrayList<ObjectAnimator>(); //ArrayList of ObjectAnimators

                            ObjectAnimator animView1 = ObjectAnimator.ofFloat(((VaccineConfirmationViewHolder) holder).llSecondLayout, View.TRANSLATION_X, 0, view.getRootView().getWidth());
                            arrayListObjectAnimators.add(animView1);
                            ObjectAnimator animView2 = ObjectAnimator.ofFloat(((VaccineConfirmationViewHolder) holder).llThirdLayout, View.TRANSLATION_X, -view.getRootView().getWidth(), 0);
                            arrayListObjectAnimators.add(animView2);

                            ObjectAnimator[] objectAnimators = arrayListObjectAnimators.toArray(new ObjectAnimator[arrayListObjectAnimators.size()]);
                            AnimatorSet animSetXY = new AnimatorSet();
                            animSetXY.playTogether(objectAnimators);
                            animSetXY.setDuration(500);
                            animSetXY.start();
                        } else {
                            ((VaccineConfirmationViewHolder) holder).llFirstLayout.setVisibility(View.GONE);
                            ((VaccineConfirmationViewHolder) holder).llSecondLayout.setVisibility(View.GONE);
                            ((VaccineConfirmationViewHolder) holder).llThirdLayout.setVisibility(View.VISIBLE);
                        }
                    }
                });

                ((VaccineConfirmationViewHolder) holder).ivBackSecond.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {

                            ArrayList<ObjectAnimator> arrayListObjectAnimators = new ArrayList<ObjectAnimator>(); //ArrayList of ObjectAnimators

                            ObjectAnimator animView1 = ObjectAnimator.ofFloat(((VaccineConfirmationViewHolder) holder).llFirstLayout, View.TRANSLATION_X, view.getRootView().getWidth(), 0);
                            arrayListObjectAnimators.add(animView1);
                            ObjectAnimator animView2 = ObjectAnimator.ofFloat(((VaccineConfirmationViewHolder) holder).llSecondLayout, View.TRANSLATION_X, 0, -view.getRootView().getWidth());
                            arrayListObjectAnimators.add(animView2);

                            ObjectAnimator[] objectAnimators = arrayListObjectAnimators.toArray(new ObjectAnimator[arrayListObjectAnimators.size()]);
                            AnimatorSet animSetXY = new AnimatorSet();
                            animSetXY.playTogether(objectAnimators);
                            animSetXY.setDuration(500);
                            animSetXY.start();
                        } else {
                            ((VaccineConfirmationViewHolder) holder).llFirstLayout.setVisibility(View.VISIBLE);
                            ((VaccineConfirmationViewHolder) holder).llSecondLayout.setVisibility(View.GONE);
                            ((VaccineConfirmationViewHolder) holder).llThirdLayout.setVisibility(View.GONE);
                        }
                    }
                });

                ((VaccineConfirmationViewHolder) holder).ivBackThird.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {

                            ArrayList<ObjectAnimator> arrayListObjectAnimators = new ArrayList<ObjectAnimator>(); //ArrayList of ObjectAnimators

                            ObjectAnimator animView1 = ObjectAnimator.ofFloat(((VaccineConfirmationViewHolder) holder).llSecondLayout, View.TRANSLATION_X, view.getRootView().getWidth(), 0);
                            arrayListObjectAnimators.add(animView1);
                            ObjectAnimator animView2 = ObjectAnimator.ofFloat(((VaccineConfirmationViewHolder) holder).llThirdLayout, View.TRANSLATION_X, 0, -view.getRootView().getWidth());
                            arrayListObjectAnimators.add(animView2);

                            ObjectAnimator[] objectAnimators = arrayListObjectAnimators.toArray(new ObjectAnimator[arrayListObjectAnimators.size()]);
                            AnimatorSet animSetXY = new AnimatorSet();
                            animSetXY.playTogether(objectAnimators);
                            animSetXY.setDuration(500);
                            animSetXY.start();
                        } else {
                            ((VaccineConfirmationViewHolder) holder).llFirstLayout.setVisibility(View.GONE);
                            ((VaccineConfirmationViewHolder) holder).llSecondLayout.setVisibility(View.VISIBLE);
                            ((VaccineConfirmationViewHolder) holder).llThirdLayout.setVisibility(View.GONE);
                        }
                    }
                });

                setTouchListener(((VaccineConfirmationViewHolder) holder).vMainLayout, position);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        //FoodInformationViewHolder
        else if (holder instanceof FoodInformationViewHolder) {

            try {

                ((FoodInformationViewHolder) holder).vMainLayout.setBackgroundColor(Color.parseColor(dataClass.getBackgroundColor()));
                Glide.with(context).load(dataClass.getImageName())
                        .diskCacheStrategy(DiskCacheStrategy.ALL).skipMemoryCache(false)
                        .into(((FoodInformationViewHolder) holder).ivFoodInfoImage);
                ((FoodInformationViewHolder) holder).tvFoodInfoDescription.setText(dataClass.getDescription());

                setTouchListener(((FoodInformationViewHolder) holder).vMainLayout, position);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        //VaccineInformationViewHolder
        else if (holder instanceof VaccineInformationViewHolder) {

            try {
                ArrayList<String> carouselList = new ArrayList<>();

                carouselList.add("http://dev.mobihealth.in/img/food_devlopment/inshort_carousel_page_background.png");
                carouselList.add("http://dev.mobihealth.in/img/food_devlopment/56%20copy.png");
                carouselList.add("http://dev.mobihealth.in/img/food_devlopment/56%20copy%202.png");
                carouselList.add("http://dev.mobihealth.in/img/food_devlopment/56%20copy%203.png");

                CarouseHealthInShortAdapter pagerAdapter = new CarouseHealthInShortAdapter(context, carouselList);
                ((VaccineInformationViewHolder) holder).vpCarousel.setAdapter(pagerAdapter);
                ((VaccineInformationViewHolder) holder).vpCarousel.setClipChildren(false);
                ((VaccineInformationViewHolder) holder).vpCarousel.setPageMargin(context.getResources().getDimensionPixelOffset(R.dimen._15sdp));
                ((VaccineInformationViewHolder) holder).vpCarousel.setOffscreenPageLimit(5);
                ((VaccineInformationViewHolder) holder).vpCarousel.setPageTransformer(false, new CarouselEffectTransformer(context)); // Set transformer
                ((VaccineInformationViewHolder) holder).tbDots.setupWithViewPager(((VaccineInformationViewHolder) holder).vpCarousel, true);

                final int[] xPos = {0};
                final int[] selectedPosition = {0};

                ((VaccineInformationViewHolder) holder).vpCarousel.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                    }

                    @Override
                    public void onPageSelected(int position) {
                        try {
                            if (position > selectedPosition[0]) {
                                xPos[0] = xPos[0] - 50;
                                ObjectAnimator animView1 = ObjectAnimator.ofFloat(((VaccineInformationViewHolder) holder).ivWaterBoat, View.TRANSLATION_X, xPos[0]);
                                animView1.start();
                            } else {
                                xPos[0] = xPos[0] + 50;
                                ObjectAnimator animView1 = ObjectAnimator.ofFloat(((VaccineInformationViewHolder) holder).ivWaterBoat, View.TRANSLATION_X, xPos[0]);
                                animView1.start();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        selectedPosition[0] = position;
                    }

                    @Override
                    public void onPageScrollStateChanged(int state) {

                    }
                });

                setTouchListener(((VaccineInformationViewHolder) holder).vMainLayout, position);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //InfoSliderViewHolder
        else if (holder instanceof InfoSliderViewHolder) {

            try {

                if (dataClass.getIsSlider().equalsIgnoreCase("1") &&
                        !dataClass.getSlider().isEmpty()) {

                    ArrayList<DashBoardDataClassMain.DashBoardDataClass> data = dataClass.getSlider();

                    if (dataClass.getPageType() == MobiConstants.HIS_PAGE_TYPE_INFO_SLIDER_CARD ||
                            dataClass.getPageType() == MobiConstants.HIS_PAGE_TYPE_INFO_SLIDER_TRANSPARENT) {

                        Glide.with(context).load(dataClass.getImageName())
                                .diskCacheStrategy(DiskCacheStrategy.ALL).skipMemoryCache(false)

                                .into(((InfoSliderViewHolder) holder).ivSliderBackgroundImage);

                        //Card Image
                        if (dataClass.getPageType() == MobiConstants.HIS_PAGE_TYPE_INFO_SLIDER_CARD) {
                            for (int i = 0; i < data.size(); i++) {
                                data.get(i).setPageType(MobiConstants.HIS_PAGE_TYPE_INFO_SLIDER_CARD);
                            }
                        }

                        //Transparent Image
                        else if (dataClass.getPageType() == MobiConstants.HIS_PAGE_TYPE_INFO_SLIDER_TRANSPARENT) {
                            for (int i = 0; i < data.size(); i++) {
                                data.get(i).setPageType(MobiConstants.HIS_PAGE_TYPE_INFO_SLIDER_TRANSPARENT);
                            }
                        }
                    }

                    /*if (dataClass.getBackgroundColor() != null && dataClass.getBackgroundColor() != "")
                        ((InfoSliderViewHolder) holder).vMainLayout.setBackgroundColor(Color.parseColor(dataClass.getBackgroundColor()));
                    else {
                        ((InfoSliderViewHolder) holder).vMainLayout.setBackgroundResource(R.color.inshort_new_color);
                    }

                    int pageMargin = GetpageMargin(context);
                    int pageOffset = GetpageOffset(context);*/

//                    ((InfoSliderViewHolder) holder).vp_healthfeeds.setPageTransformer(new MarginPageTransformer(50));

                    InfoSliderAdapter adapter = new InfoSliderAdapter(context, fragment, data);
                    ((InfoSliderViewHolder) holder).vp_healthfeeds.setAdapter(adapter);
                    ((InfoSliderViewHolder) holder).vp_healthfeeds.setOffscreenPageLimit(3);
                    ((InfoSliderViewHolder) holder).vp_healthfeeds.setOrientation(ORIENTATION_HORIZONTAL);
                    ((InfoSliderViewHolder) holder).vp_healthfeeds.setCurrentItem(0);
                    new TabLayoutMediator(((InfoSliderViewHolder) holder).tl_healthfeeds_tabs, ((InfoSliderViewHolder) holder).vp_healthfeeds,
                            new TabLayoutMediator.TabConfigurationStrategy() {
                                @Override
                                public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                                    //tab.setText("Tab " + (position + 1));
                                }
                            }).attach();
                    /*((InfoSliderViewHolder) holder).vp_healthfeeds.setPageTransformer(new ViewPager2.PageTransformer() {
                        @Override
                        public void transformPage(@NonNull View page, float position) {

                            float myOffset = position * -(2 * pageOffset + pageMargin);
                            if (((InfoSliderViewHolder) holder).vp_healthfeeds.getOrientation() == ViewPager2.ORIENTATION_HORIZONTAL) {
                                if (ViewCompat.getLayoutDirection(((InfoSliderViewHolder) holder).vp_healthfeeds) == ViewCompat.LAYOUT_DIRECTION_RTL) {
                                    page.setTranslationX(-myOffset);
                                } else {
                                    page.setTranslationX(myOffset);
                                }
                            } else {
                                page.setTranslationY(myOffset);
                            }
                        }
                    });*/
                }
                setTouchListener(((InfoSliderViewHolder) holder).vMainLayout, position);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

    private void setViewClickListener(View view, String action, int position) {
        try {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClick.itemActionClick(position, action);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setTouchListener(LinearLayout vMainLayout, int position) {
        try {
            if (vMainLayout != null) {
                vMainLayout.setTag("linear" + position);
                vMainLayout.setOnTouchListener(new OnSwipeTouchListener(context) {
                    @Override
                    public void onClick() {
                        super.onClick();
                        // your on click here
                        if (fragment instanceof DashboardHummFeedFragment) {
                            if (arrayList.size() > 0) {
                                ((DashboardHummFeedFragment) fragment).toggleToolbar();
                            }
                        }
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public class ImageTextViewHolder extends RecyclerView.ViewHolder {

        TextView txtTitle, txtDescription;
        ImageView imageView;
        LinearLayout vMainLayout;

        ImageTextViewHolder(View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtDescription = itemView.findViewById(R.id.txtDescription);
            imageView = itemView.findViewById(R.id.imageView);
            vMainLayout = itemView.findViewById(R.id.vMainLayout);
        }
    }

    public class VideoTextViewHolder extends RecyclerView.ViewHolder {

        YouTubePlayerView youtubePlayerView;
        TextView txtTitle, txtDescription;
        LinearLayout vMainLayout;

        VideoTextViewHolder(View itemView) {
            super(itemView);
            youtubePlayerView = itemView.findViewById(R.id.youtube_player_view);
            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtDescription = itemView.findViewById(R.id.txtDescription);
            vMainLayout = itemView.findViewById(R.id.vMainLayout);
        }
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView, backgroundImage;
        LinearLayout vMainLayout;

        ImageViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.fullImageView);
            vMainLayout = itemView.findViewById(R.id.vMainLayout);
            backgroundImage = itemView.findViewById(R.id.backgroundImage);
        }
    }

    public class VideoViewHolder extends RecyclerView.ViewHolder {

        YouTubePlayerView youtubePlayerView;
        LinearLayout vMainLayout;

        VideoViewHolder(View itemView) {
            super(itemView);
            youtubePlayerView = itemView.findViewById(R.id.youtube_player_view);
            vMainLayout = itemView.findViewById(R.id.vMainLayout);
        }
    }

    public class TextViewHolder extends RecyclerView.ViewHolder {

        TextView txtTitle, txtDescription;
        LinearLayout vMainLayout;

        TextViewHolder(View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtDescription = itemView.findViewById(R.id.txtDescription);
            vMainLayout = itemView.findViewById(R.id.vMainLayout);
        }
    }

    public class SurveyImageViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        ImageView imageViewBack;
        RelativeLayout rlTopMedia;
        TextView txtQuestion;
        LinearLayout ll_questions;
        LinearLayout linearContent;
        RelativeLayout rlQuestion;
        LinearLayout llHalfLayout;
        LinearLayout vMainLayout;

        RelativeLayout optionLayout1, optionLayout2, optionLayout3, optionLayout4;
        ProgressBar pbAnswer1, pbAnswer2, pbAnswer3, pbAnswer4;
        TextView tvAnswer1, tvAnswer2, tvAnswer3, tvAnswer4;
        TextView tvAvgPercentage1, tvAvgPercentage2, tvAvgPercentage3, tvAvgPercentage4;
        ProgressBar seekBar1, seekBar2, seekBar3, seekBar4;

        SurveyImageViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            imageViewBack = itemView.findViewById(R.id.imageViewBack);
            rlTopMedia = itemView.findViewById(R.id.rlTopMedia);
            txtQuestion = itemView.findViewById(R.id.txtQuestion);
            ll_questions = itemView.findViewById(R.id.ll_questions);
            linearContent = itemView.findViewById(R.id.linearContent);
            rlQuestion = itemView.findViewById(R.id.rlQuestion);
            llHalfLayout = itemView.findViewById(R.id.llHalfLayout);

            optionLayout1 = itemView.findViewById(R.id.optionLayout1);
            pbAnswer1 = itemView.findViewById(R.id.pbAnswer1);
            tvAnswer1 = itemView.findViewById(R.id.tvAnswer1);
            tvAvgPercentage1 = itemView.findViewById(R.id.tvAvgPercentage1);
            seekBar1 = itemView.findViewById(R.id.seekbar1);

            optionLayout2 = itemView.findViewById(R.id.optionLayout2);
            pbAnswer2 = itemView.findViewById(R.id.pbAnswer2);
            tvAnswer2 = itemView.findViewById(R.id.tvAnswer2);
            tvAvgPercentage2 = itemView.findViewById(R.id.tvAvgPercentage2);
            seekBar2 = itemView.findViewById(R.id.seekbar2);

            optionLayout3 = itemView.findViewById(R.id.optionLayout3);
            pbAnswer3 = itemView.findViewById(R.id.pbAnswer3);
            tvAnswer3 = itemView.findViewById(R.id.tvAnswer3);
            tvAvgPercentage3 = itemView.findViewById(R.id.tvAvgPercentage3);
            seekBar3 = itemView.findViewById(R.id.seekbar3);

            optionLayout4 = itemView.findViewById(R.id.optionLayout4);
            pbAnswer4 = itemView.findViewById(R.id.pbAnswer4);
            tvAnswer4 = itemView.findViewById(R.id.tvAnswer4);
            tvAvgPercentage4 = itemView.findViewById(R.id.tvAvgPercentage4);
            seekBar4 = itemView.findViewById(R.id.seekbar4);
            vMainLayout = itemView.findViewById(R.id.vMainLayout);
        }
    }

    public class SurveyVideoViewHolder extends RecyclerView.ViewHolder {

        YouTubePlayerView youtubePlayerView;
        RelativeLayout rlTopMedia;
        TextView txtQuestion;
        LinearLayout ll_questions;
        RelativeLayout rlQuestion;
        LinearLayout llHalfLayout;
        LinearLayout vMainLayout;

        RelativeLayout optionLayout1, optionLayout2, optionLayout3, optionLayout4;
        ProgressBar pbAnswer1, pbAnswer2, pbAnswer3, pbAnswer4;
        TextView tvAnswer1, tvAnswer2, tvAnswer3, tvAnswer4;
        TextView tvAvgPercentage1, tvAvgPercentage2, tvAvgPercentage3, tvAvgPercentage4;
        ProgressBar seekBar1, seekBar2, seekBar3, seekBar4;

        SurveyVideoViewHolder(View itemView) {
            super(itemView);

            youtubePlayerView = itemView.findViewById(R.id.youtube_player_view);

            rlTopMedia = itemView.findViewById(R.id.rlTopMedia);
            txtQuestion = itemView.findViewById(R.id.txtQuestion);
            ll_questions = itemView.findViewById(R.id.ll_questions);
            rlQuestion = itemView.findViewById(R.id.rlQuestion);
            llHalfLayout = itemView.findViewById(R.id.llHalfLayout);

            optionLayout1 = itemView.findViewById(R.id.optionLayout1);
            pbAnswer1 = itemView.findViewById(R.id.pbAnswer1);
            tvAnswer1 = itemView.findViewById(R.id.tvAnswer1);
            tvAvgPercentage1 = itemView.findViewById(R.id.tvAvgPercentage1);
            seekBar1 = itemView.findViewById(R.id.seekbar1);

            optionLayout2 = itemView.findViewById(R.id.optionLayout2);
            pbAnswer2 = itemView.findViewById(R.id.pbAnswer2);
            tvAnswer2 = itemView.findViewById(R.id.tvAnswer2);
            tvAvgPercentage2 = itemView.findViewById(R.id.tvAvgPercentage2);
            seekBar2 = itemView.findViewById(R.id.seekbar2);

            optionLayout3 = itemView.findViewById(R.id.optionLayout3);
            pbAnswer3 = itemView.findViewById(R.id.pbAnswer3);
            tvAnswer3 = itemView.findViewById(R.id.tvAnswer3);
            tvAvgPercentage3 = itemView.findViewById(R.id.tvAvgPercentage3);
            seekBar3 = itemView.findViewById(R.id.seekbar3);

            optionLayout4 = itemView.findViewById(R.id.optionLayout4);
            pbAnswer4 = itemView.findViewById(R.id.pbAnswer4);
            tvAnswer4 = itemView.findViewById(R.id.tvAnswer4);
            tvAvgPercentage4 = itemView.findViewById(R.id.tvAvgPercentage4);
            seekBar4 = itemView.findViewById(R.id.seekbar4);
            vMainLayout = itemView.findViewById(R.id.vMainLayout);
        }
    }

    public class SurveyTextViewHolder extends RecyclerView.ViewHolder {


        ImageView imageViewBack;
        TextView txtQuestion;
        LinearLayout ll_questions;
        LinearLayout linearContent;
        RelativeLayout rlQuestion;
        LinearLayout llHalfLayout;
        LinearLayout vMainLayout;

        RelativeLayout optionLayout1, optionLayout2, optionLayout3, optionLayout4;
        ProgressBar pbAnswer1, pbAnswer2, pbAnswer3, pbAnswer4;
        TextView tvAnswer1, tvAnswer2, tvAnswer3, tvAnswer4;
        TextView tvAvgPercentage1, tvAvgPercentage2, tvAvgPercentage3, tvAvgPercentage4;
        ProgressBar seekBar1, seekBar2, seekBar3, seekBar4;

        SurveyTextViewHolder(View itemView) {
            super(itemView);
            imageViewBack = itemView.findViewById(R.id.imageViewBack);
            txtQuestion = itemView.findViewById(R.id.txtQuestion);
            ll_questions = itemView.findViewById(R.id.ll_questions);
            linearContent = itemView.findViewById(R.id.linearContent);
            rlQuestion = itemView.findViewById(R.id.rlQuestion);
            llHalfLayout = itemView.findViewById(R.id.llHalfLayout);

            optionLayout1 = itemView.findViewById(R.id.optionLayout1);
            pbAnswer1 = itemView.findViewById(R.id.pbAnswer1);
            tvAnswer1 = itemView.findViewById(R.id.tvAnswer1);
            tvAvgPercentage1 = itemView.findViewById(R.id.tvAvgPercentage1);
            seekBar1 = itemView.findViewById(R.id.seekbar1);

            optionLayout2 = itemView.findViewById(R.id.optionLayout2);
            pbAnswer2 = itemView.findViewById(R.id.pbAnswer2);
            tvAnswer2 = itemView.findViewById(R.id.tvAnswer2);
            tvAvgPercentage2 = itemView.findViewById(R.id.tvAvgPercentage2);
            seekBar2 = itemView.findViewById(R.id.seekbar2);

            optionLayout3 = itemView.findViewById(R.id.optionLayout3);
            pbAnswer3 = itemView.findViewById(R.id.pbAnswer3);
            tvAnswer3 = itemView.findViewById(R.id.tvAnswer3);
            tvAvgPercentage3 = itemView.findViewById(R.id.tvAvgPercentage3);
            seekBar3 = itemView.findViewById(R.id.seekbar3);

            optionLayout4 = itemView.findViewById(R.id.optionLayout4);
            pbAnswer4 = itemView.findViewById(R.id.pbAnswer4);
            tvAnswer4 = itemView.findViewById(R.id.tvAnswer4);
            tvAvgPercentage4 = itemView.findViewById(R.id.tvAvgPercentage4);
            seekBar4 = itemView.findViewById(R.id.seekbar4);
            vMainLayout = itemView.findViewById(R.id.vMainLayout);
        }
    }

    public class QuizImageViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        ImageView imageViewBack;
        RelativeLayout rlTopMedia;
        TextView txtQuestion;
        RelativeLayout rlCardMain;
        LinearLayout ll_questions;
        LinearLayout linearContent;
        RelativeLayout rlQuestion;
        LinearLayout llHalfLayout;
        LinearLayout vMainLayout;

        RelativeLayout optionLayout1, optionLayout2, optionLayout3, optionLayout4;
        ProgressBar pbAnswer1, pbAnswer2, pbAnswer3, pbAnswer4;
        TextView tvAnswer1, tvAnswer2, tvAnswer3, tvAnswer4;
        TextView tvAvgPercentage1, tvAvgPercentage2, tvAvgPercentage3, tvAvgPercentage4;
        ImageView ivAnswerType1, ivAnswerType2, ivAnswerType3, ivAnswerType4;

        QuizImageViewHolder(View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageView);
            imageViewBack = itemView.findViewById(R.id.imageViewBack);
            rlTopMedia = itemView.findViewById(R.id.rlTopMedia);
            txtQuestion = itemView.findViewById(R.id.txtQuestion);
            rlCardMain = itemView.findViewById(R.id.rlCardMain);
            ll_questions = itemView.findViewById(R.id.ll_questions);
            linearContent = itemView.findViewById(R.id.linearContent);
            rlQuestion = itemView.findViewById(R.id.rlQuestion);
            llHalfLayout = itemView.findViewById(R.id.llHalfLayout);

            optionLayout1 = itemView.findViewById(R.id.optionLayout1);
            pbAnswer1 = itemView.findViewById(R.id.pbAnswer1);
            tvAnswer1 = itemView.findViewById(R.id.tvAnswer1);
            tvAvgPercentage1 = itemView.findViewById(R.id.tvAvgPercentage1);
            ivAnswerType1 = itemView.findViewById(R.id.ivAnswerType1);

            optionLayout2 = itemView.findViewById(R.id.optionLayout2);
            pbAnswer2 = itemView.findViewById(R.id.pbAnswer2);
            tvAnswer2 = itemView.findViewById(R.id.tvAnswer2);
            tvAvgPercentage2 = itemView.findViewById(R.id.tvAvgPercentage2);
            ivAnswerType2 = itemView.findViewById(R.id.ivAnswerType2);

            optionLayout3 = itemView.findViewById(R.id.optionLayout3);
            pbAnswer3 = itemView.findViewById(R.id.pbAnswer3);
            tvAnswer3 = itemView.findViewById(R.id.tvAnswer3);
            tvAvgPercentage3 = itemView.findViewById(R.id.tvAvgPercentage3);
            ivAnswerType3 = itemView.findViewById(R.id.ivAnswerType3);

            optionLayout4 = itemView.findViewById(R.id.optionLayout4);
            pbAnswer4 = itemView.findViewById(R.id.pbAnswer4);
            tvAnswer4 = itemView.findViewById(R.id.tvAnswer4);
            tvAvgPercentage4 = itemView.findViewById(R.id.tvAvgPercentage4);
            ivAnswerType4 = itemView.findViewById(R.id.ivAnswerType4);
            vMainLayout = itemView.findViewById(R.id.vMainLayout);
        }
    }

    public class QuizVideoViewHolder extends RecyclerView.ViewHolder {

        YouTubePlayerView youtubePlayerView;
        RelativeLayout rlTopMedia;
        TextView txtQuestion;
        LinearLayout ll_questions;
        RelativeLayout rlQuestion;
        LinearLayout llHalfLayout;
        LinearLayout vMainLayout;

        RelativeLayout optionLayout1, optionLayout2, optionLayout3, optionLayout4;
        ProgressBar pbAnswer1, pbAnswer2, pbAnswer3, pbAnswer4;
        TextView tvAnswer1, tvAnswer2, tvAnswer3, tvAnswer4;
        TextView tvAvgPercentage1, tvAvgPercentage2, tvAvgPercentage3, tvAvgPercentage4;
        ImageView ivAnswerType1, ivAnswerType2, ivAnswerType3, ivAnswerType4;

        QuizVideoViewHolder(View itemView) {
            super(itemView);

            youtubePlayerView = itemView.findViewById(R.id.youtube_player_view);
            rlTopMedia = itemView.findViewById(R.id.rlTopMedia);
            txtQuestion = itemView.findViewById(R.id.txtQuestion);
            ll_questions = itemView.findViewById(R.id.ll_questions);
            rlQuestion = itemView.findViewById(R.id.rlQuestion);
            llHalfLayout = itemView.findViewById(R.id.llHalfLayout);

            optionLayout1 = itemView.findViewById(R.id.optionLayout1);
            pbAnswer1 = itemView.findViewById(R.id.pbAnswer1);
            tvAnswer1 = itemView.findViewById(R.id.tvAnswer1);
            tvAvgPercentage1 = itemView.findViewById(R.id.tvAvgPercentage1);
            ivAnswerType1 = itemView.findViewById(R.id.ivAnswerType1);

            optionLayout2 = itemView.findViewById(R.id.optionLayout2);
            pbAnswer2 = itemView.findViewById(R.id.pbAnswer2);
            tvAnswer2 = itemView.findViewById(R.id.tvAnswer2);
            tvAvgPercentage2 = itemView.findViewById(R.id.tvAvgPercentage2);
            ivAnswerType2 = itemView.findViewById(R.id.ivAnswerType2);

            optionLayout3 = itemView.findViewById(R.id.optionLayout3);
            pbAnswer3 = itemView.findViewById(R.id.pbAnswer3);
            tvAnswer3 = itemView.findViewById(R.id.tvAnswer3);
            tvAvgPercentage3 = itemView.findViewById(R.id.tvAvgPercentage3);
            ivAnswerType3 = itemView.findViewById(R.id.ivAnswerType3);

            optionLayout4 = itemView.findViewById(R.id.optionLayout4);
            pbAnswer4 = itemView.findViewById(R.id.pbAnswer4);
            tvAnswer4 = itemView.findViewById(R.id.tvAnswer4);
            tvAvgPercentage4 = itemView.findViewById(R.id.tvAvgPercentage4);
            ivAnswerType4 = itemView.findViewById(R.id.ivAnswerType4);
            vMainLayout = itemView.findViewById(R.id.vMainLayout);
        }
    }

    public class QuizTextViewHolder extends RecyclerView.ViewHolder {

        ImageView imageViewBack;
        TextView txtQuestion;
        LinearLayout ll_questions;
        LinearLayout linearContent;
        RelativeLayout rlQuestion;
        LinearLayout llHalfLayout;
        LinearLayout vMainLayout;

        RelativeLayout optionLayout1, optionLayout2, optionLayout3, optionLayout4;
        ProgressBar pbAnswer1, pbAnswer2, pbAnswer3, pbAnswer4;
        TextView tvAnswer1, tvAnswer2, tvAnswer3, tvAnswer4;
        TextView tvAvgPercentage1, tvAvgPercentage2, tvAvgPercentage3, tvAvgPercentage4;
        ImageView ivAnswerType1, ivAnswerType2, ivAnswerType3, ivAnswerType4;

        QuizTextViewHolder(View itemView) {
            super(itemView);

            imageViewBack = itemView.findViewById(R.id.imageViewBack);
            txtQuestion = itemView.findViewById(R.id.txtQuestion);
            ll_questions = itemView.findViewById(R.id.ll_questions);
            linearContent = itemView.findViewById(R.id.linearContent);
            rlQuestion = itemView.findViewById(R.id.rlQuestion);
            llHalfLayout = itemView.findViewById(R.id.llHalfLayout);

            optionLayout1 = itemView.findViewById(R.id.optionLayout1);
            pbAnswer1 = itemView.findViewById(R.id.pbAnswer1);
            tvAnswer1 = itemView.findViewById(R.id.tvAnswer1);
            tvAvgPercentage1 = itemView.findViewById(R.id.tvAvgPercentage1);
            ivAnswerType1 = itemView.findViewById(R.id.ivAnswerType1);

            optionLayout2 = itemView.findViewById(R.id.optionLayout2);
            pbAnswer2 = itemView.findViewById(R.id.pbAnswer2);
            tvAnswer2 = itemView.findViewById(R.id.tvAnswer2);
            tvAvgPercentage2 = itemView.findViewById(R.id.tvAvgPercentage2);
            ivAnswerType2 = itemView.findViewById(R.id.ivAnswerType2);

            optionLayout3 = itemView.findViewById(R.id.optionLayout3);
            pbAnswer3 = itemView.findViewById(R.id.pbAnswer3);
            tvAnswer3 = itemView.findViewById(R.id.tvAnswer3);
            tvAvgPercentage3 = itemView.findViewById(R.id.tvAvgPercentage3);
            ivAnswerType3 = itemView.findViewById(R.id.ivAnswerType3);

            optionLayout4 = itemView.findViewById(R.id.optionLayout4);
            pbAnswer4 = itemView.findViewById(R.id.pbAnswer4);
            tvAnswer4 = itemView.findViewById(R.id.tvAnswer4);
            tvAvgPercentage4 = itemView.findViewById(R.id.tvAvgPercentage4);
            ivAnswerType4 = itemView.findViewById(R.id.ivAnswerType4);
            vMainLayout = itemView.findViewById(R.id.vMainLayout);
        }
    }

    public class MedicineConfirmationViewHolder extends RecyclerView.ViewHolder {

        ImageView ivContent, ivConfirmationComplete;
        Button btnPositive;
        Button btnNegative;
        LinearLayout vMainLayout;
        CardView cardConfirmationPending, cardConfirmationComplete;
        TextView tvReminderTimePending, tvReminderTimeComplete;

        MedicineConfirmationViewHolder(View itemView) {
            super(itemView);
            ivContent = itemView.findViewById(R.id.ivContent);
            btnPositive = itemView.findViewById(R.id.btnCPositive);
            btnNegative = itemView.findViewById(R.id.btnCNegative);
            vMainLayout = itemView.findViewById(R.id.vMainLayout);
            cardConfirmationPending = itemView.findViewById(R.id.cardConfirmationPending);
            cardConfirmationComplete = itemView.findViewById(R.id.cardConfirmationComplete);
            ivConfirmationComplete = itemView.findViewById(R.id.ivConfirmationComplete);
            tvReminderTimePending = itemView.findViewById(R.id.tvReminderTimePending);
            tvReminderTimeComplete = itemView.findViewById(R.id.tvReminderTimeComplete);
        }
    }

    public class NormalInstructionViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        LinearLayout vMainLayout, llAdvanceInstructionBox, llAdvanceInstructionTitle;
        TextView tvInstruction, tvInstructionTitle;

        NormalInstructionViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.fullImageView);
            vMainLayout = itemView.findViewById(R.id.vMainLayout);
            tvInstruction = itemView.findViewById(R.id.tvInstruction);
            llAdvanceInstructionBox = itemView.findViewById(R.id.llAdvanceInstructionBox);
            tvInstructionTitle = itemView.findViewById(R.id.tvInstructionTitle);
            llAdvanceInstructionTitle = itemView.findViewById(R.id.llAdvanceInstructionTitle);
        }
    }

    public class AdvanceInstructionViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        LinearLayout vMainLayout, llAdvanceInstructionBox, llAdvanceInstructionTitle;
        TextView tvInstruction, tvInstructionTitle;

        AdvanceInstructionViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.fullImageView);
            vMainLayout = itemView.findViewById(R.id.vMainLayout);
            tvInstruction = itemView.findViewById(R.id.tvInstruction);
            llAdvanceInstructionBox = itemView.findViewById(R.id.llAdvanceInstructionBox);
            llAdvanceInstructionTitle = itemView.findViewById(R.id.llAdvanceInstructionTitle);
            tvInstructionTitle = itemView.findViewById(R.id.tvInstructionTitle);
        }
    }

    public class VaccineConfirmationViewHolder extends RecyclerView.ViewHolder {

        LinearLayout llFirstLayout;
        LinearLayout llSecondLayout;
        LinearLayout llThirdLayout;
        TextView tvWhyVaccine, tvDone;
        TextView tvContentText, tvChildName, tvAge2, tvActualDate2;
        ImageView ivBabyImage, ivInjection1, ivBackSecond, ivBackThird;
        RecyclerView rvVaccineList;
        LinearLayout vMainLayout;

        VaccineConfirmationViewHolder(View itemView) {
            super(itemView);
            llFirstLayout = itemView.findViewById(R.id.llFirstLayout);
            llSecondLayout = itemView.findViewById(R.id.llSecondLayout);
            llThirdLayout = itemView.findViewById(R.id.llThirdLayout);
            tvWhyVaccine = itemView.findViewById(R.id.tvWhyVaccine);
            tvDone = itemView.findViewById(R.id.tvDone);
            tvContentText = itemView.findViewById(R.id.tvContentText);
            tvChildName = itemView.findViewById(R.id.tvChildName);
            tvAge2 = itemView.findViewById(R.id.tvAge2);
            tvActualDate2 = itemView.findViewById(R.id.tvActualDate2);
            ivBabyImage = itemView.findViewById(R.id.ivBabyImage);
            ivInjection1 = itemView.findViewById(R.id.ivInjection1);
            ivBackSecond = itemView.findViewById(R.id.ivBackSecond);
            ivBackThird = itemView.findViewById(R.id.ivBackThird);
            rvVaccineList = itemView.findViewById(R.id.rvVaccineList);
            vMainLayout = itemView.findViewById(R.id.vMainLayout);
        }
    }

    public class VaccineInformationViewHolder extends RecyclerView.ViewHolder {

        CarouselViewPager vpCarousel;
        ImageView ivWaterBoat;
        TabLayout tbDots;
        LinearLayout vMainLayout;

        VaccineInformationViewHolder(View itemView) {
            super(itemView);
            vpCarousel = itemView.findViewById(R.id.vpCarousel);
            ivWaterBoat = itemView.findViewById(R.id.ivWaterBoat);
            tbDots = itemView.findViewById(R.id.tbDots);
            vMainLayout = itemView.findViewById(R.id.vMainLayout);
        }
    }

    public class FoodInformationViewHolder extends RecyclerView.ViewHolder {

        LinearLayout vMainLayout;
        ImageView ivFoodInfoImage;
        TextView tvFoodInfo, tvRemember, tvFoodInfoDescription;


        FoodInformationViewHolder(View itemView) {
            super(itemView);
            vMainLayout = itemView.findViewById(R.id.vMainLayout);
            ivFoodInfoImage = itemView.findViewById(R.id.ivFoodInfoImage);
            tvFoodInfo = itemView.findViewById(R.id.tvFoodInfo);
            tvRemember = itemView.findViewById(R.id.tvRemember);
            tvFoodInfoDescription = itemView.findViewById(R.id.tvFoodInfoDescription);
        }
    }

    public class InfoSliderViewHolder extends RecyclerView.ViewHolder {

        ViewPager2 vp_healthfeeds;
        TabLayout tl_healthfeeds_tabs;
        LinearLayout vMainLayout;
        ImageView ivSliderBackgroundImage, ivFixedTransparentBackground;

        public InfoSliderViewHolder(@NonNull View itemView) {
            super(itemView);

            vp_healthfeeds = itemView.findViewById(R.id.vp_healthfeeds);
            tl_healthfeeds_tabs = itemView.findViewById(R.id.tl_healthfeeds_tabs);
            vMainLayout = itemView.findViewById(R.id.ll_healthfeeds_container);
            ivSliderBackgroundImage = itemView.findViewById(R.id.ivSliderBackgroundImage);
            ivFixedTransparentBackground = itemView.findViewById(R.id.ivFixedTransparentBackground);

        }
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    @Override
    public int getItemViewType(int position) {
        DashBoardDataClassMain.DashBoardDataClass dataClass = arrayList.get(position);
        int pageType = dataClass.getPageType();


        //Image Text
        if (pageType == MobiConstants.HIS_PAGE_TYPE_IMAGE_TEXT) {
            return HIS_PAGE_TYPE_IMAGE_TEXT;
        }

        //Video Text
        else if (pageType == MobiConstants.HIS_PAGE_TYPE_VIDEO_TEXT) {
            return HIS_PAGE_TYPE_VIDEO_TEXT;
        }

        //Full image
        else if (pageType == MobiConstants.HIS_PAGE_TYPE_IMAGE) {
            return HIS_PAGE_TYPE_IMAGE;
        }

        //Full Video
        else if (pageType == MobiConstants.HIS_PAGE_TYPE_VIDEO) {
            return HIS_PAGE_TYPE_VIDEO;
        }

        //Full text
        else if (pageType == MobiConstants.HIS_PAGE_TYPE_TEXT) {
            return HIS_PAGE_TYPE_TEXT;
        }

        //Survey Quiz Image Text
        else if (pageType == MobiConstants.HIS_PAGE_TYPE_QUIZ_SURVEY_IMAGE_TEXT) {
            if (dataClass.getIsSurvey() == 1) {
                return HIS_PAGE_TYPE_SURVEY_IMAGE_TEXT;
            } else {
                return HIS_PAGE_TYPE_QUIZ_IMAGE_TEXT;
            }
        }

        //Survey Quiz Video Text
        else if (pageType == MobiConstants.HIS_PAGE_TYPE_QUIZ_SURVEY_VIDEO_TEXT) {
            if (dataClass.getIsSurvey() == 1) {
                return HIS_PAGE_TYPE_SURVEY_VIDEO_TEXT;
            } else {
                return HIS_PAGE_TYPE_QUIZ_VIDEO_TEXT;
            }
        }

        //Survey Quiz Text
        else if (pageType == MobiConstants.HIS_PAGE_TYPE_QUIZ_SURVEY_TEXT) {
            if (dataClass.getIsSurvey() == 1) {
                return HIS_PAGE_TYPE_SURVEY_TEXT;
            } else {
                return HIS_PAGE_TYPE_QUIZ_TEXT;
            }
        }

        //Medicine Confirmation
        else if (pageType == MobiConstants.HIS_PAGE_TYPE_MEDICINE_CONFIRMATION) {
            return HIS_PAGE_TYPE_MEDICINE_CONFIRMATION;
        }

        //Normal Instruction
        else if (pageType == MobiConstants.HIS_PAGE_TYPE_NORMAL_INSTRUCTION) {
            return HIS_PAGE_TYPE_NORMAL_INSTRUCTION;
        }

        //Advance Instruction
        else if (pageType == MobiConstants.HIS_PAGE_TYPE_ADVANCE_INSTRUCTION) {
            return HIS_PAGE_TYPE_ADVANCE_INSTRUCTION;
        }

        //Vaccine Confirmation
        else if (pageType == MobiConstants.HIS_PAGE_TYPE_VACCINE_CONFIRMATION) {
            return HIS_PAGE_TYPE_VACCINE_CONFIRMATION;
        }

        //Food Reminder
        else if (pageType == MobiConstants.HIS_PAGE_TYPE_FOOD_REMINDER) {
            return HIS_PAGE_TYPE_FOOD_REMINDER;
        }

        //Growth Development
        else if (pageType == MobiConstants.HIS_PAGE_TYPE_GROWTH_DEVELOPMENT) {
            return HIS_PAGE_TYPE_GROWTH_DEVELOPMENT;
        }

        //Vaccine Reminder
        else if (pageType == MobiConstants.HIS_PAGE_TYPE_VACCINE_REMINDER) {
            return HIS_PAGE_TYPE_VACCINE_REMINDER;
        }

        //Info slider
        else if (pageType == MobiConstants.HIS_PAGE_TYPE_INFO_SLIDER) {
            return HIS_PAGE_TYPE_INFO_SLIDER;
        }

        //Transparent Info Slider
        else if (pageType == MobiConstants.HIS_PAGE_TYPE_INFO_SLIDER_TRANSPARENT) {
            return HIS_PAGE_TYPE_INFO_SLIDER;
        }

        //Card Info Slider
        else if (pageType == MobiConstants.HIS_PAGE_TYPE_INFO_SLIDER_CARD) {
            return HIS_PAGE_TYPE_INFO_SLIDER;
        }

        return dataClass.getPageType();
        //return super.getItemViewType(position);
    }

    private Animation inFromLeftAnimation() {
        Animation inFromLeft = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, -1.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f);
        inFromLeft.setDuration(500);
        inFromLeft.setInterpolator(new AccelerateInterpolator());
        return inFromLeft;
    }

    private Animation outToRightAnimation() {
        Animation outtoRight = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, +1.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f);
        outtoRight.setDuration(500);
        outtoRight.setInterpolator(new AccelerateInterpolator());
        return outtoRight;
    }

    @Override
    public long getItemId(int position) {
        return arrayList.get(position).hashCode();
    }


    public int GetpageMargin(Context context) {
        return context.getResources().getDimensionPixelOffset(R.dimen._15sdp);
    }

    public int GetpageOffset(Context context) {
        return context.getResources().getDimensionPixelOffset(R.dimen._20sdp);
    }

}

