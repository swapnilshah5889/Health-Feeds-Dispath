package com.fluvina.hummnew.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.fluvina.hummnew.Model.HummCategoryModel;
import com.fluvina.hummnew.R;
import com.fluvina.hummnew.interfaces.RecyclerItemClick;

import java.util.List;

import jp.wasabeef.glide.transformations.BlurTransformation;

public class HummInsightsAdapter extends RecyclerView.Adapter<HummInsightsAdapter.MyViewHolder> {

    Context context;
    List<HummCategoryModel.In_sights_list> insightList;
    RecyclerItemClick itemClick;

    public HummInsightsAdapter(Context context, List<HummCategoryModel.In_sights_list> insightList, RecyclerItemClick itemClick) {
        this.context = context;
        this.insightList = insightList;
        this.itemClick = itemClick;
        setHasStableIds(true);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.insights_item_layout, parent, false);
        return new HummInsightsAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        try{
            if(position==0) {
//                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) holder.llSubCatContainer.getLayoutParams();
//                params.setMargins((int) context.getResources().getDimension(R.dimen._10sdp), 0, (int) context.getResources().getDimension(R.dimen._10sdp), 0);
//                holder.llSubCatContainer.setLayoutParams(params);

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );
                params.setMargins((int) context.getResources().getDimension(R.dimen._10sdp), 0, (int) context.getResources().getDimension(R.dimen._10sdp), 0);
                holder.llInsightContainer.setLayoutParams(params);
            }
            Glide.with(context).load(insightList.get(position).getImageName())
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).skipMemoryCache(false)
                    .into(holder.ivInsight);

            Glide.with(context).load(insightList.get(position).getImageName())
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).skipMemoryCache(false)
                    .transform(new BlurTransformation())
                    .into(holder.ivInsightBkg);

            holder.llInsightContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    itemClick.itemClick(position);
                }
            });

        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return insightList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView ivInsight, ivInsightBkg;
        LinearLayout llInsightContainer;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ivInsight= itemView.findViewById(R.id.ivInsight);
            ivInsightBkg= itemView.findViewById(R.id.ivInsightBkg);
            llInsightContainer= itemView.findViewById(R.id.llInsightContainer);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
