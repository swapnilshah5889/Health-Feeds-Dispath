package com.fluvina.hummnew.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.fluvina.hummnew.Activities.MainActivity;
import com.fluvina.hummnew.Custom.OnSwipeTouchListener;
import com.fluvina.hummnew.Fragments.DashboardHummFeedFragment;
import com.fluvina.hummnew.Model.DashBoardDataClassMain;
import com.fluvina.hummnew.R;
import com.fluvina.hummnew.Utilities.MobiConstants;
import com.fluvina.hummnew.Utilities.MobiUtility;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerCallback;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import jp.wasabeef.glide.transformations.BlurTransformation;

public class InfoSliderAdapter extends RecyclerView.Adapter {
    private final Context context;
    private final Fragment fragment;
    private final ArrayList<DashBoardDataClassMain.DashBoardDataClass> arrayList;

    String TAG = "DashboardPagerHISAdapterNewVertical2 :--> ";
    private LayoutInflater mInflater;

    private final int HIS_PAGE_TYPE_IMAGE_TEXT = 0;
    private final int HIS_PAGE_TYPE_VIDEO_TEXT = 1;
    private final int HIS_PAGE_TYPE_IMAGE = 2;
    private final int HIS_PAGE_TYPE_VIDEO = 3;
    private final int HIS_PAGE_TYPE_TEXT = 4;
    private final int HIS_PAGE_TYPE_CARD = 5;
    private final int HIS_PAGE_TYPE_TRANSPARENT = 6;

    public InfoSliderAdapter(Context context, Fragment fragment, ArrayList<DashBoardDataClassMain.DashBoardDataClass> arrayList) {
        this.context = context;
        this.fragment = fragment;
        this.arrayList = arrayList;
        setHasStableIds(true);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView;
        switch (viewType) {
            case HIS_PAGE_TYPE_IMAGE_TEXT:
                itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.vertical_viewpager_image_text_item, parent, false);
                return new InfoSliderAdapter.ImageTextViewHolder(itemView);

            case HIS_PAGE_TYPE_VIDEO_TEXT:
                itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.vertical_viewpager_video_text_item, parent, false);
                return new InfoSliderAdapter.VideoTextViewHolder(itemView);

            case HIS_PAGE_TYPE_IMAGE:
                itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.vertical_viewpager_image_full_item, parent, false);
                return new InfoSliderAdapter.ImageViewHolder(itemView);

            case HIS_PAGE_TYPE_VIDEO:
                itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.vertical_viewpager_video_full_item, parent, false);
                return new InfoSliderAdapter.VideoViewHolder(itemView);

            case HIS_PAGE_TYPE_TEXT:
                itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.vertical_viewpager_text_item, parent, false);
                return new InfoSliderAdapter.TextViewHolder(itemView);

            case HIS_PAGE_TYPE_CARD:
                itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.vertical_viewpager_card_image_item, parent, false);
                return new InfoSliderAdapter.CardImageViewHolder(itemView);

            case HIS_PAGE_TYPE_TRANSPARENT:
                itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.vertical_viewpager_transparent_image_item, parent, false);
                return new InfoSliderAdapter.TransparentImageViewHolder(itemView);

            default:
                itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.vertical_viewpager_text_item, parent, false);
                return new InfoSliderAdapter.TextViewHolder(itemView);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        DashBoardDataClassMain.DashBoardDataClass dataClass = arrayList.get(position);
/*
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams)(() holder).vMainLayout.getLayoutParams();
        marginLayoutParams.leftMargin = GetInfoSliderPageMargin(context);
        marginLayoutParams.rightMargin = GetInfoSliderPageMargin(context);
        */
        if (holder instanceof InfoSliderAdapter.ImageTextViewHolder) {
            try {
                ((InfoSliderAdapter.ImageTextViewHolder) holder).imageView.setVisibility(View.VISIBLE);
                Glide.with(context).load(dataClass.getImageName()).into(((InfoSliderAdapter.ImageTextViewHolder) holder).imageView);
                ((InfoSliderAdapter.ImageTextViewHolder) holder).txtTitle.setText(dataClass.getTitle());
                ((InfoSliderAdapter.ImageTextViewHolder) holder).txtDescription.setText(dataClass.getDescription());

                if (((InfoSliderAdapter.ImageTextViewHolder) holder).vMainLayout != null) {
                    ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) ((ImageTextViewHolder) holder).vMainLayout.getLayoutParams();
                    marginLayoutParams.leftMargin = GetInfoSliderPageMargin(context);
                    marginLayoutParams.rightMargin = GetInfoSliderPageMargin(context);
                    ((InfoSliderAdapter.ImageTextViewHolder) holder).vMainLayout.setTag("linear" + position);
                    ((InfoSliderAdapter.ImageTextViewHolder) holder).vMainLayout.setOnTouchListener(new OnSwipeTouchListener(context) {

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

        //VideoTextViewHolder
        else if (holder instanceof InfoSliderAdapter.VideoTextViewHolder) {
            try {
                if (dataClass.getVideoType() == MobiConstants.HIS_VIDEO_TYPE_YOUTUBE) {

                    ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) ((VideoTextViewHolder) holder).vMainLayout.getLayoutParams();
                    marginLayoutParams.leftMargin = GetInfoSliderPageMargin(context);
                    marginLayoutParams.rightMargin = GetInfoSliderPageMargin(context);
                    ((InfoSliderAdapter.VideoTextViewHolder) holder).youtubePlayerView.setVisibility(View.VISIBLE);

                    ((MainActivity) context).getLifecycle().addObserver(((InfoSliderAdapter.VideoTextViewHolder) holder).youtubePlayerView);
                    ((InfoSliderAdapter.VideoTextViewHolder) holder).youtubePlayerView.getPlayerUiController().showFullscreenButton(false);
                    ((InfoSliderAdapter.VideoTextViewHolder) holder).youtubePlayerView.getPlayerUiController().setFullScreenButtonClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //Toast.makeText(context, "Full Screen Clicked", Toast.LENGTH_SHORT).show();
                        }
                    });
                    /*((InfoSliderAdapter.VideoTextViewHolder) holder).youtubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
                        @Override
                        public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                            String videoId = MobiUtility.extractYTId(dataClass.getVideoUrl());//"9iIX4PBplAY";
                            youTubePlayer.cueVideo(videoId, 0);
                            //youTubePlayer.loadVideo(videoId, 0);
                            dataClass.setYouTubePlayer(youTubePlayer);
                        }
                    });*/
                    ((InfoSliderAdapter.VideoTextViewHolder) holder).youtubePlayerView.getYouTubePlayerWhenReady(new YouTubePlayerCallback() {
                        @Override
                        public void onYouTubePlayer(@NotNull YouTubePlayer youTubePlayer) {
                            String videoId = MobiUtility.extractYTId(dataClass.getVideoUrl());//"9iIX4PBplAY";
                            youTubePlayer.cueVideo(videoId, 0);
                            //youTubePlayer.loadVideo(videoId, 0);
                            dataClass.setYouTubePlayer(youTubePlayer);
                        }
                    });
                    dataClass.setPlayerView(((InfoSliderAdapter.VideoTextViewHolder) holder).youtubePlayerView);

                } else if (dataClass.getVideoType() == MobiConstants.HIS_VIDEO_TYPE_WEBVIEW) {

                    ((InfoSliderAdapter.VideoTextViewHolder) holder).youtubePlayerView.setVisibility(View.GONE);

                } else {
                    ((InfoSliderAdapter.VideoTextViewHolder) holder).youtubePlayerView.setVisibility(View.GONE);
                }

                ((InfoSliderAdapter.VideoTextViewHolder) holder).txtTitle.setText(dataClass.getTitle());
                ((InfoSliderAdapter.VideoTextViewHolder) holder).txtDescription.setText(dataClass.getDescription());

                if (((InfoSliderAdapter.VideoTextViewHolder) holder).vMainLayout != null) {
                    ((InfoSliderAdapter.VideoTextViewHolder) holder).vMainLayout.setTag("linear" + position);
                    ((InfoSliderAdapter.VideoTextViewHolder) holder).vMainLayout.setOnTouchListener(new OnSwipeTouchListener(context) {

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

        //ImageViewHolder
        else if (holder instanceof InfoSliderAdapter.ImageViewHolder) {
            try {
                Glide.with(context).load(dataClass.getImageName())
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .skipMemoryCache(false)
                        .into(((InfoSliderAdapter.ImageViewHolder) holder).imageView);

                Glide.with(context).load(dataClass.getImageName())
                        .apply(RequestOptions.bitmapTransform(new BlurTransformation(MobiConstants.BLUR_RADIUS, MobiConstants.BLUR_SAMPLING)))
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .skipMemoryCache(false)
                        .into(((InfoSliderAdapter.ImageViewHolder) holder).backgroundImage);

                if (((InfoSliderAdapter.ImageViewHolder) holder).vMainLayout != null) {
                    /*ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams)((ImageViewHolder) holder).vMainLayout.getLayoutParams();
                    marginLayoutParams.leftMargin = GetInfoSliderPageMargin(context);
                    marginLayoutParams.rightMargin = GetInfoSliderPageMargin(context);*/
                    ((InfoSliderAdapter.ImageViewHolder) holder).vMainLayout.setTag("linear" + position);
                    ((InfoSliderAdapter.ImageViewHolder) holder).vMainLayout.setOnTouchListener(new OnSwipeTouchListener(context) {

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

        //VideoViewHolder
        else if (holder instanceof InfoSliderAdapter.VideoViewHolder) {
            try {
                if (dataClass.getVideoType() == MobiConstants.HIS_VIDEO_TYPE_YOUTUBE) {
                    ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) ((VideoViewHolder) holder).vMainLayout.getLayoutParams();
                    marginLayoutParams.leftMargin = GetInfoSliderPageMargin(context);
                    marginLayoutParams.rightMargin = GetInfoSliderPageMargin(context);
                    ((InfoSliderAdapter.VideoViewHolder) holder).youtubePlayerView.setVisibility(View.VISIBLE);

                    ((MainActivity) context).getLifecycle().addObserver(((InfoSliderAdapter.VideoViewHolder) holder).youtubePlayerView);
                    ((InfoSliderAdapter.VideoViewHolder) holder).youtubePlayerView.getPlayerUiController().showFullscreenButton(false);
                    ((InfoSliderAdapter.VideoViewHolder) holder).youtubePlayerView.getPlayerUiController().setFullScreenButtonClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //Toast.makeText(context, "Full Screen Clicked", Toast.LENGTH_SHORT).show();
                        }
                    });
                    /*((InfoSliderAdapter.VideoViewHolder) holder).youtubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
                        @Override
                        public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                            String videoId = MobiUtility.extractYTId(dataClass.getVideoUrl());//"9iIX4PBplAY";
                            youTubePlayer.cueVideo(videoId, 0);
                            //youTubePlayer.loadVideo(videoId, 0);
                            dataClass.setYouTubePlayer(youTubePlayer);
                        }
                    });*/
                    ((InfoSliderAdapter.VideoViewHolder) holder).youtubePlayerView.getYouTubePlayerWhenReady(new YouTubePlayerCallback() {
                        @Override
                        public void onYouTubePlayer(@NotNull YouTubePlayer youTubePlayer) {
                            String videoId = MobiUtility.extractYTId(dataClass.getVideoUrl());//"9iIX4PBplAY";
                            youTubePlayer.cueVideo(videoId, 0);
                            //youTubePlayer.loadVideo(videoId, 0);
                            dataClass.setYouTubePlayer(youTubePlayer);
                        }
                    });
                    dataClass.setPlayerView(((InfoSliderAdapter.VideoViewHolder) holder).youtubePlayerView);

                } else if (dataClass.getVideoType() == MobiConstants.HIS_VIDEO_TYPE_WEBVIEW) {
                    ((InfoSliderAdapter.VideoViewHolder) holder).youtubePlayerView.setVisibility(View.GONE);
                } else {
                    ((InfoSliderAdapter.VideoViewHolder) holder).youtubePlayerView.setVisibility(View.GONE);
                }

                if (((InfoSliderAdapter.VideoViewHolder) holder).vMainLayout != null) {
                    ((InfoSliderAdapter.VideoViewHolder) holder).vMainLayout.setTag("linear" + position);
                    ((InfoSliderAdapter.VideoViewHolder) holder).vMainLayout.setOnTouchListener(new OnSwipeTouchListener(context) {

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

        //TextViewHolder
        else if (holder instanceof InfoSliderAdapter.TextViewHolder) {
            try {
                ((InfoSliderAdapter.TextViewHolder) holder).txtTitle.setText(dataClass.getTitle());
                ((InfoSliderAdapter.TextViewHolder) holder).txtDescription.setText(dataClass.getDescription());

                if (((InfoSliderAdapter.TextViewHolder) holder).vMainLayout != null) {
                    ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) ((TextViewHolder) holder).vMainLayout.getLayoutParams();
                    marginLayoutParams.leftMargin = GetInfoSliderPageMargin(context);
                    marginLayoutParams.rightMargin = GetInfoSliderPageMargin(context);
                    ((InfoSliderAdapter.TextViewHolder) holder).vMainLayout.setTag("linear" + position);
                    ((InfoSliderAdapter.TextViewHolder) holder).vMainLayout.setOnTouchListener(new OnSwipeTouchListener(context) {

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

        //Card Image Holder
        else if (holder instanceof InfoSliderAdapter.CardImageViewHolder) {
            try {
                Glide.with(context).load(dataClass.getImageName())
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .skipMemoryCache(false)
                        .into(((CardImageViewHolder) holder).ivCard);
                if (((InfoSliderAdapter.CardImageViewHolder) holder).vMainLayout != null) {
                    /*ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams)((ImageViewHolder) holder).vMainLayout.getLayoutParams();
                    marginLayoutParams.leftMargin = GetInfoSliderPageMargin(context);
                    marginLayoutParams.rightMargin = GetInfoSliderPageMargin(context);*/
                    ((InfoSliderAdapter.CardImageViewHolder) holder).vMainLayout.setTag("linear" + position);
                    ((InfoSliderAdapter.CardImageViewHolder) holder).vMainLayout.setOnTouchListener(new OnSwipeTouchListener(context) {

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

        //Transparent Image Holder
        else if (holder instanceof InfoSliderAdapter.TransparentImageViewHolder) {
            try {
                Glide.with(context).load(dataClass.getImageName())
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .skipMemoryCache(false)
                        .into(((TransparentImageViewHolder) holder).ivCard);

                if (((InfoSliderAdapter.TransparentImageViewHolder) holder).vMainLayout != null) {
                    /*ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams)((ImageViewHolder) holder).vMainLayout.getLayoutParams();
                    marginLayoutParams.leftMargin = GetInfoSliderPageMargin(context);
                    marginLayoutParams.rightMargin = GetInfoSliderPageMargin(context);*/
                    ((InfoSliderAdapter.TransparentImageViewHolder) holder).vMainLayout.setTag("linear" + position);
                    ((InfoSliderAdapter.TransparentImageViewHolder) holder).vMainLayout.setOnTouchListener(new OnSwipeTouchListener(context) {

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
    }


    @Override
    public int getItemCount() {
        return arrayList.size();
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
            backgroundImage = itemView.findViewById(R.id.backgroundImage);
            vMainLayout = itemView.findViewById(R.id.vMainLayout);
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

    public class CardImageViewHolder extends RecyclerView.ViewHolder {

        ImageView ivCard;
        LinearLayout vMainLayout;

        CardImageViewHolder(View itemView) {
            super(itemView);
            ivCard = (ImageView) itemView.findViewById(R.id.ivCard);
            vMainLayout = (LinearLayout) itemView.findViewById(R.id.vMainLayout);
        }
    }

    public class TransparentImageViewHolder extends RecyclerView.ViewHolder {

        ImageView ivCard;
        LinearLayout vMainLayout;

        TransparentImageViewHolder(View itemView) {
            super(itemView);
            ivCard = (ImageView) itemView.findViewById(R.id.ivTransparentImage);
            vMainLayout = (LinearLayout) itemView.findViewById(R.id.vMainLayout);
        }
    }

    @Override
    public long getItemId(int position) {
        return arrayList.get(position).hashCode();
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

        //Full Image
        else if (pageType == MobiConstants.HIS_PAGE_TYPE_IMAGE) {
            return HIS_PAGE_TYPE_IMAGE;
        }

        //Full Video
        else if (pageType == MobiConstants.HIS_PAGE_TYPE_VIDEO) {
            return HIS_PAGE_TYPE_VIDEO;
        }

        //Full Text
        else if (pageType == MobiConstants.HIS_PAGE_TYPE_TEXT) {
            return HIS_PAGE_TYPE_TEXT;
        }

        //Card Image
        else if (pageType == MobiConstants.HIS_PAGE_TYPE_INFO_SLIDER_CARD) {
            return HIS_PAGE_TYPE_CARD;
        }

        //Transparent Image
        else if (pageType == MobiConstants.HIS_PAGE_TYPE_INFO_SLIDER_TRANSPARENT) {
            return HIS_PAGE_TYPE_TRANSPARENT;
        }

        return HIS_PAGE_TYPE_TEXT;
        //return dataClass.getPageType();
        //return super.getItemViewType(position);
    }

    public int GetInfoSliderPageMargin(Context context) {
        return context.getResources().getDimensionPixelOffset(R.dimen._40sdp);
    }

    public int GetpageMargin(Context context) {
        return context.getResources().getDimensionPixelOffset(R.dimen._15sdp);
    }

    public int GetpageOffset(Context context) {
        return context.getResources().getDimensionPixelOffset(R.dimen._20sdp);
    }
}

