package com.fluvina.hummnew.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.fluvina.hummnew.Model.DashBoardDataClassMain;
import com.fluvina.hummnew.R;

import java.util.ArrayList;

public class InshortVaccineAdapter extends RecyclerView.Adapter<InshortVaccineAdapter.MyViewHolder> {

    private final ArrayList<DashBoardDataClassMain.VaccineClass> mDataset;
    Context mContext;

    public InshortVaccineAdapter(Context context, ArrayList<DashBoardDataClassMain.VaccineClass> myDataset) {
        mContext = context;
        mDataset = myDataset;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.inshorts_vaccine_list_item_layout, parent, false);
        MyViewHolder holder = new MyViewHolder(itemView);

        return holder;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvNumber, tvVaccineName, tvVaccineDesc;

        public MyViewHolder(View itemView) {
            super(itemView);

            tvVaccineName = itemView.findViewById(R.id.tvVaccineName);
            tvNumber = itemView.findViewById(R.id.tvNumber);
            tvVaccineDesc = itemView.findViewById(R.id.tvVaccineDesc);
        }

    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        DashBoardDataClassMain.VaccineClass item = mDataset.get(position);
        holder.tvNumber.setText((position + 1) + "");

        if (item.getDoses().size()>0) {
            holder.tvVaccineName.setText(item.getDoses().get(0).getDetails_key());
            holder.tvVaccineDesc.setText(item.getDoses().get(0).getDoseage_name());
        }
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}

