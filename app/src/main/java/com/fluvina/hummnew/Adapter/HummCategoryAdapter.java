package com.fluvina.hummnew.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fluvina.hummnew.Model.HummCategoryModel;
import com.fluvina.hummnew.R;

import java.util.List;

public class HummCategoryAdapter extends RecyclerView.Adapter<HummCategoryAdapter.MyViewHolder> {

    Context context;
    List<HummCategoryModel.Category_list> categoryList;

    public HummCategoryAdapter(Context context, List<HummCategoryModel.Category_list> categoryList) {
        this.context = context;
        this.categoryList = categoryList;
        setHasStableIds(true);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.category_section_item_layout, parent, false);
        return new HummCategoryAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        try{
            HummCategoryModel.Category_list category = categoryList.get(position);
            holder.tvCategoryName.setText(category.getName());
            HummSubCategoryAdapter adapter = new HummSubCategoryAdapter(context,category.getHashtag_ordering_json(), category.getName());
            holder.rvSubcategories.setAdapter(adapter);

        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView tvCategoryName;
        RecyclerView rvSubcategories;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCategoryName = itemView.findViewById(R.id.tvCategoryName);
            rvSubcategories = itemView.findViewById(R.id.rvSubcategories);
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
