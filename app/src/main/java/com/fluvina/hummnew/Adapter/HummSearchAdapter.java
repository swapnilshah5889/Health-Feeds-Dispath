package com.fluvina.hummnew.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.fluvina.hummnew.Model.HummSearchModel;
import com.fluvina.hummnew.R;
import com.fluvina.hummnew.Utilities.MobiConstants;
import com.fluvina.hummnew.interfaces.RecyclerItemActionClick;

import java.util.List;

public class HummSearchAdapter extends RecyclerView.Adapter<HummSearchAdapter.MyViewHolder> {

    Context context;
    List<HummSearchModel.Data> searchList;
    RecyclerItemActionClick itemActionClick;

    public HummSearchAdapter(Context context, List<HummSearchModel.Data> searchList,RecyclerItemActionClick itemActionClick) {
        this.context = context;
        this.searchList = searchList;
        this.itemActionClick = itemActionClick;
        setHasStableIds(true);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.search_humm_item_layout, parent, false);
        return new HummSearchAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        try{
            HummSearchModel.Data searchItem = searchList.get(position);

            //If first item then give some padding on the top
            if(position==0) {
                holder.llSearchTopPadding.setVisibility(View.VISIBLE);
            }
            //If last item then give some padding at the end
            else if(position == searchList.size()-1){
                holder.llSearchBottomPadding.setVisibility(View.VISIBLE);
            }


            Glide.with(context).load(searchItem.getImageName())
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).skipMemoryCache(false)
                    .into(holder.ivSearch);

            holder.tvSearchDate.setText(searchItem.getCreated_on());
            holder.tvSearchTitle.setText(searchItem.getTitle());

            holder.llSearchHummContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    itemActionClick.itemActionClick(position, MobiConstants.RECYCLER_ITEM_CLICK);
                }
            });

        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return searchList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        LinearLayout llSearchTopPadding, llSearchBottomPadding, llSearchHummContainer;
        TextView tvSearchTitle, tvSearchDate;
        ImageView ivSearch;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            llSearchHummContainer= itemView.findViewById(R.id.llSearchHummContainer);
            llSearchTopPadding= itemView.findViewById(R.id.llSearchTopPadding);
            llSearchBottomPadding= itemView.findViewById(R.id.llSearchBottomPadding);
            tvSearchTitle= itemView.findViewById(R.id.tvSearchTitle);
            ivSearch= itemView.findViewById(R.id.ivSearch );
            tvSearchDate= itemView.findViewById(R.id.tvSearchDate);
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
