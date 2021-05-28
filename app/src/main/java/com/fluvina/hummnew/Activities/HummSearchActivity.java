package com.fluvina.hummnew.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.fluvina.hummnew.Adapter.HummSearchAdapter;
import com.fluvina.hummnew.Custom.CustomLoaderDialog;
import com.fluvina.hummnew.Custom.EndlessRecyclerScrollListener;
import com.fluvina.hummnew.Model.HummSearchModel;
import com.fluvina.hummnew.R;
import com.fluvina.hummnew.Utilities.MobiBroadcaster;
import com.fluvina.hummnew.Utilities.MobiConstants;
import com.fluvina.hummnew.Utilities.MobiLogger;
import com.fluvina.hummnew.Utilities.MobiUtility;
import com.fluvina.hummnew.interfaces.RecyclerItemActionClick;
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

public class HummSearchActivity extends AppCompatActivity {


    RecyclerView rvHummSearch;
    int page = 1;
    EditText etSearchHumm;
    LinearLayout llToolbarLeft, llSearchHumm;
    String query = "";

    List<HummSearchModel.Data> searchList;
    EndlessRecyclerScrollListener endlessScrollListener;
    HummSearchAdapter searchAdapter;
    LinearLayout llHummSearchContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_humm_search);

        InitObject();
    }

    private void InitObject() {
        try {
            rvHummSearch = findViewById(R.id.rvHummSearch);
            etSearchHumm = findViewById(R.id.etSearchHumm);
            llSearchHumm = findViewById(R.id.llSearchHumm);
            llToolbarLeft = findViewById(R.id.llToolbarLeft);
            llHummSearchContainer = findViewById(R.id.llHummSearchContainer);
            searchList = new ArrayList<>();
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            rvHummSearch.setLayoutManager(layoutManager);

            etSearchHumm.requestFocus();
            MobiUtility.openSoftKeyboard(this,llHummSearchContainer);

            endlessScrollListener = new EndlessRecyclerScrollListener(layoutManager) {
                @Override
                public void onLoadMore(int pg, int totalItemsCount, RecyclerView view) {
                    page++;
                    if(MobiUtility.isInternetOn(HummSearchActivity.this)){
                        SearchHummFeeds();
                    }
                    else{
                        Toast.makeText(HummSearchActivity.this, getString(R.string.toast_no_internet_connection), Toast.LENGTH_SHORT).show();
                    }
                }
            };

            rvHummSearch.addOnScrollListener(endlessScrollListener);

            llToolbarLeft.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onBackPressed();
                }
            });


            etSearchHumm.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View view, int i, KeyEvent keyEvent) {
                    if(i == KeyEvent.KEYCODE_ENTER || i == KeyEvent.KEYCODE_SEARCH){
                        callSearchFeeds();
                        return true;
                    }

                    return false;
                }
            });

            llSearchHumm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    callSearchFeeds();
                }
            });
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }


    private void callSearchFeeds(){
        try {
            page = 1;
            endlessScrollListener.resetState();
            query = etSearchHumm.getText().toString().toLowerCase().trim();
            if(MobiUtility.isInternetOn(HummSearchActivity.this)){
                SearchHummFeeds();
            }
            else{
                Toast.makeText(HummSearchActivity.this, getString(R.string.toast_no_internet_connection), Toast.LENGTH_SHORT).show();
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    RecyclerItemActionClick itemActionClick = new RecyclerItemActionClick() {
        @Override
        public void itemActionClick(int position, String action) {
            try {

                HummSearchModel.Data searchItem = searchList.get(position);
                String temp = searchItem.getId()+":"+query;
                MobiBroadcaster.Mobi_HummSearchFeed(temp);
                onBackPressed();

            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
    };

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(MobiConstants.ANIM_IN,MobiConstants.ANIM_OUT);
    }

    //API Calls

    private void SearchHummFeeds() {
        CustomLoaderDialog customLoaderDialog = new CustomLoaderDialog(this);

        if(query.length()>0) {
            try {
                customLoaderDialog.showDialog();
                LinkedHashMap<String, String> params = new LinkedHashMap<>();

                if(page == 1){
                    searchList = new ArrayList<>();
                }

                params.put("action", MobiConstants.ACTION_humm_search_keyword);
                params.put("page", "" + page);
                params.put("search_keyword", query);
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
                        MobiUtility.closeSoftkeyboard(HummSearchActivity.this,etSearchHumm);
                        try {
                            String strResponse = response.body().string();
                            MobiLogger.printmsg("response : " + strResponse);
                            Gson gson = new Gson();
                            HummSearchModel temp = gson.fromJson(strResponse,HummSearchModel.class);

                            if(temp.getStatus()){
                                if(page == 1){

                                    //Search Data available
                                    if(temp.getData().size()>0) {
                                        searchList = temp.getData();
                                        searchAdapter = new HummSearchAdapter(HummSearchActivity.this, searchList, itemActionClick);
                                        rvHummSearch.setAdapter(searchAdapter);
                                    }

                                    //No Data
                                    else{

                                    }

                                }

                                else{
                                    if(temp.getData().size()>0) {
                                        searchList.addAll(temp.getData());
                                    }
                                }
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        try {
                            if(searchAdapter!=null){
                                searchAdapter.notifyDataSetChanged();
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
            } catch (Exception e) {
                e.printStackTrace();
                customLoaderDialog.dismissDialog();
            }
        }
    }
}