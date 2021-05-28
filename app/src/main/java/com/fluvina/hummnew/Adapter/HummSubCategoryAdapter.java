package com.fluvina.hummnew.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.fluvina.hummnew.Model.HummCategoryModel;
import com.fluvina.hummnew.R;
import com.fluvina.hummnew.Utilities.MobiBroadcaster;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class HummSubCategoryAdapter extends RecyclerView.Adapter<HummSubCategoryAdapter.MyViewHolder> {

    Context context;
    List<HummCategoryModel.Hashtag_ordering_json> subCategoryList;
    String catPos;

    public HummSubCategoryAdapter(Context context, List<HummCategoryModel.Hashtag_ordering_json> subCategoryList, String catPos) {
        this.context = context;
        this.subCategoryList = subCategoryList;
        this.catPos = catPos;
        setHasStableIds(true);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.category_item_layout, parent, false);
        return new HummSubCategoryAdapter.MyViewHolder(itemView);
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
                holder.llSubCatContainer.setLayoutParams(params);
            }
            holder.tvSubTitle.setText(subCategoryList.get(position).getSub_category_name());
            Glide.with(context).load(subCategoryList.get(position).getTag_icon_url())
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).skipMemoryCache(false)
                    .into(holder.civCategoryIcon);

            holder.llSubCatContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    MobiBroadcaster.Mobi_HummCategoryFilter(catPos+":"+subCategoryList.get(position).getSub_category_name());
                }
            });
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return subCategoryList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        CircleImageView civCategoryIcon;
        TextView tvSubTitle;
        LinearLayout llSubCatContainer;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            civCategoryIcon= itemView.findViewById(R.id.civCategoryIcon);
            tvSubTitle= itemView.findViewById(R.id.tvSubTitle);
            llSubCatContainer= itemView.findViewById(R.id.llSubCatContainer);
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
