package com.fluvina.hummnew.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.fluvina.hummnew.Adapter.HummCategoryAdapter;
import com.fluvina.hummnew.Adapter.HummInsightsAdapter;
import com.fluvina.hummnew.Custom.CustomLoaderDialog;
import com.fluvina.hummnew.Model.HummCategoryModel;
import com.fluvina.hummnew.R;
import com.fluvina.hummnew.Utilities.MobiBroadcaster;
import com.fluvina.hummnew.Utilities.MobiConstants;
import com.fluvina.hummnew.Utilities.MobiLogger;
import com.fluvina.hummnew.Utilities.MobiUtility;
import com.fluvina.hummnew.interfaces.RecyclerItemClick;
import com.fluvina.hummnew.network.ApiClient;
import com.fluvina.hummnew.network.ApiService;
import com.fluvina.hummnew.network.Apis;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HummFilterActivity extends AppCompatActivity {

    NestedScrollView nsvHummFilter;
    ImageView ivShowAllFeeds;
    LinearLayout llSearchHumm;
    RecyclerView rvCategories, rvInsights;
    LinearLayout llInsightsContainer;
    List<HummCategoryModel.Category_list> categoryList;
    List<HummCategoryModel.In_sights_list> insightList;
    HummCategoryAdapter categoryAdapter;
    HummInsightsAdapter insightsAdapter;
    TextView tvViewAllInsights;

    //Toolbar
    LinearLayout llToolbarLeft;
    TextView tvToolbarTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_humm_filter);

        InitObjects();

        LocalBroadcastManager.getInstance(this).registerReceiver(subCatClicked,
                new IntentFilter(MobiBroadcaster.CategoryFilter));

        LocalBroadcastManager.getInstance(this).registerReceiver(searchClicked,
                new IntentFilter(MobiBroadcaster.SearchFeeds));
    }

    private void InitObjects() {
        try {
            llToolbarLeft = findViewById(R.id.llToolbarLeft);
            tvToolbarTitle = findViewById(R.id.tvToolbarTitle);
            llSearchHumm = findViewById(R.id.llSearchHumm);
            rvCategories = findViewById(R.id.rvCategories);
            rvInsights = findViewById(R.id.rvInsights);
            llInsightsContainer = findViewById(R.id.llInsightsContainer);
            nsvHummFilter = findViewById(R.id.nsvHummFilter);
            tvViewAllInsights = findViewById(R.id.tvViewAllInsights);
            ivShowAllFeeds = findViewById(R.id.ivShowAllFeeds);
            nsvHummFilter.setVisibility(View.GONE);

            tvToolbarTitle.setText(R.string.title_humm_filter);

            categoryList = new ArrayList<>();
            insightList = new ArrayList<>();
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            rvCategories.setLayoutManager(layoutManager);

            if(MobiUtility.isInternetOn(this)){
                FetchFilterCategories();
            }
            else{
                Toast.makeText(this, getString(R.string.toast_no_internet_connection), Toast.LENGTH_SHORT).show();
            }

            llToolbarLeft.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onBackPressed();
                }
            });

            llSearchHumm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(HummFilterActivity.this,HummSearchActivity.class);
                    startActivity(i);
                    overridePendingTransition(MobiConstants.ANIM_IN, MobiConstants.ANIM_OUT);
                }
            });

            tvViewAllInsights.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    MobiBroadcaster.Mobi_ShowInsights();
                    onBackPressed();
                }
            });

            ivShowAllFeeds.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    MobiBroadcaster.Mobi_ResetHumm();
                    onBackPressed();
                }
            });

        }
        catch (Exception e){e.printStackTrace();}
    }

    BroadcastReceiver subCatClicked = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                String message = intent.getStringExtra(MobiBroadcaster.CategoryFilter_Tag);
                if (message != null) {
                    onBackPressed();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    BroadcastReceiver searchClicked = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                String message = intent.getStringExtra(MobiBroadcaster.SearchFeeds_Tag);
                if (message != null) {
                    onBackPressed();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    RecyclerItemClick itemClick = new RecyclerItemClick() {
        @Override
        public void itemClick(int position) {
            MobiBroadcaster.Mobi_ShowParticularInsights(insightList.get(position).getId()+"");
            onBackPressed();
        }
    };

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(MobiConstants.ANIM_IN,MobiConstants.ANIM_OUT);
    }

    //API Calls

    private void FetchFilterCategories() {
        CustomLoaderDialog customLoaderDialog = new CustomLoaderDialog(this);
        try {
            customLoaderDialog.showDialog();
            LinkedHashMap<String, String> params = new LinkedHashMap<>();


            params.put("action", MobiConstants.ACTION_humm_search_screen_data);
            params.put("stuff", MobiConstants.STUFF);
            params.put("app_type", MobiConstants.APP_TYPE);
            MobiLogger.printmsg("Api : " + Apis.BASE_URL + Apis.patient_api);
            MobiLogger.printMap(params);

            ApiService service = ApiClient.getRetrofitInstance().create(ApiService.class);
            Call<ResponseBody> call = service.callPatientApi(params);

            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    customLoaderDialog.dismissDialog();
                    try {
                        String strResponse = response.body().string();
                        MobiLogger.printmsg("response : " + strResponse);
                        Gson gson = new Gson();
                        HummCategoryModel temp = gson.fromJson(strResponse,HummCategoryModel.class);
                        if(temp.getStatus()){
                            categoryList = temp.getData().getCategory_list();
                            categoryAdapter = new HummCategoryAdapter(HummFilterActivity.this,categoryList);
                            rvCategories.setAdapter(categoryAdapter);
                            insightList = temp.getData().getIn_sights_list();
                            insightsAdapter = new HummInsightsAdapter(HummFilterActivity.this,insightList, itemClick);
                            rvInsights.setAdapter(insightsAdapter);
                            nsvHummFilter.setVisibility(View.VISIBLE);
                            Glide.with(HummFilterActivity.this).load(temp.getData().getAll_feed_img())
                                    .diskCacheStrategy(DiskCacheStrategy.ALL).skipMemoryCache(false)
                                    .into(ivShowAllFeeds);
                        }
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }

                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    customLoaderDialog.dismissDialog();
                    MobiLogger.printmsg(t.getMessage());
                }
            });
        }
        catch (Exception e){
            e.printStackTrace();
            customLoaderDialog.dismissDialog();
        }
    }

}