package com.rupvm.covid19india.Activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rupvm.covid19india.Constants.ILiterals;
import com.rupvm.covid19india.Constants.NetworkUtils;
import com.rupvm.covid19india.Constants.Utils;
import com.rupvm.covid19india.CustomView.MyEditText;
import com.rupvm.covid19india.CustomView.MyTextView;
import com.rupvm.covid19india.ICallBacks.IAdapterOnClick;
import com.rupvm.covid19india.ICallBacks.IResponseCallBack;
import com.rupvm.covid19india.R;
import com.rupvm.covid19india.Service.CovideDataRequestResponse;
import com.rupvm.covid19india.Service.StateRequestResponseClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class MainScreenActivity extends AppCompatActivity implements IResponseCallBack, IAdapterOnClick {

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("android.net.conn.CONNECTIVITY_CHANGE")) {
                if (NetworkUtils.isConnected(MainScreenActivity.this)) {
                    // new CallWebServiceAsync().execute();
                    new CallCovidDataWebServiceAsync().execute();
                    findViewById(R.id.internet_connection_layout).setVisibility(View.GONE);
                    findViewById(R.id.main_linear_layout).setVisibility(View.VISIBLE);
                }
            }
            if (intent.getAction().equals("android.net.wifi.WIFI_STATE_CHANGE")) {
                if (NetworkUtils.isConnected(MainScreenActivity.this)) {
                    // new CallWebServiceAsync().execute();
                    new CallCovidDataWebServiceAsync().execute();
                    findViewById(R.id.internet_connection_layout).setVisibility(View.GONE);
                    findViewById(R.id.main_linear_layout).setVisibility(View.VISIBLE);
                }
            }
        }
    };


    private RecyclerView recyclerView;


    private RecyclerView.LayoutManager layoutManager;
    private StateWiseRecyclerAdapter stateWiseRecyclerAdapter;
    private List<CovideDataRequestResponse.Statewise> statewiseList = new ArrayList<CovideDataRequestResponse.Statewise>();
    private List<CovideDataRequestResponse.Statewise> searchStateWiseList = new ArrayList<CovideDataRequestResponse.Statewise>();
    private int countText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_screen_activity);


        recyclerView = findViewById(R.id.recyclerView);

        layoutManager = new LinearLayoutManager(this);
        stateWiseRecyclerAdapter = new StateWiseRecyclerAdapter(statewiseList, 0);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(stateWiseRecyclerAdapter);

        ImageView notificationImageView = findViewById(R.id.notificationIcon);
        notificationImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainScreenActivity.this, NotificationActivity.class);
                startActivity(intent);
            }
        });


        ImageView notificationRefresh = findViewById(R.id.refreshIcon);
        notificationRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (NetworkUtils.isConnected(getApplicationContext())) {
                    findViewById(R.id.internet_connection_layout).setVisibility(View.GONE);
                    findViewById(R.id.main_linear_layout).setVisibility(View.VISIBLE);
                    new CallCovidDataWebServiceAsync().execute();
                    new CallWebServiceAsync().execute();
                } else {
                    findViewById(R.id.internet_connection_layout).setVisibility(View.VISIBLE);
                    findViewById(R.id.main_linear_layout).setVisibility(View.GONE);
                    Utils.showMessage(getApplicationContext(), "No network");
                }

            }
        });

        MyEditText myEditText = findViewById(R.id.editSearch);

        myEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                searchStateWiseList.clear();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                countText = s.length();
                if (s.length() > 0) {
                    for (CovideDataRequestResponse.Statewise statewise : statewiseList) {
                        if (statewise.getState().toLowerCase().contains(s.toString().toLowerCase())) {
                            searchStateWiseList.add(statewise);
                        }
                    }
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 0) {

                    stateWiseRecyclerAdapter = new StateWiseRecyclerAdapter(statewiseList, 0);
                    stateWiseRecyclerAdapter.setiAdapterOnClick(MainScreenActivity.this);
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setAdapter(stateWiseRecyclerAdapter);
                    stateWiseRecyclerAdapter.notifyDataSetChanged();
                } else {
                    stateWiseRecyclerAdapter = new StateWiseRecyclerAdapter(searchStateWiseList, 1);
                    stateWiseRecyclerAdapter.setiAdapterOnClick(MainScreenActivity.this);
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setAdapter(stateWiseRecyclerAdapter);
                    stateWiseRecyclerAdapter.notifyDataSetChanged();
                }


            }
        });


        if (NetworkUtils.isConnected(getApplicationContext())) {

            findViewById(R.id.internet_connection_layout).setVisibility(View.GONE);
            findViewById(R.id.main_linear_layout).setVisibility(View.VISIBLE);
            new CallCovidDataWebServiceAsync().execute();
            new CallWebServiceAsync().execute();
        } else {
            findViewById(R.id.internet_connection_layout).setVisibility(View.VISIBLE);
            findViewById(R.id.main_linear_layout).setVisibility(View.GONE);
            Utils.showMessage(getApplicationContext(), "No network");
        }

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        intentFilter.addAction("android.net.wifi.WIFI_STATE_CHANGE");
        registerReceiver(broadcastReceiver, intentFilter);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
    }

    @Override
    public void onSuccess(List<StateRequestResponseClient.StateWiseDataResponse> stateWiseDataResponseList, JSONObject jsonObject, JSONArray jsonArray, JsonObjectRequest jsonObjectRequest) {
        if (jsonObjectRequest.getTag().equals(CovideDataRequestResponse.class.getName())) {
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                CovideDataRequestResponse.DataResponse dataResponse = objectMapper.readValue(jsonObject.toString(), CovideDataRequestResponse.DataResponse.class);

                MyTextView latstTimeUpdateText = findViewById(R.id.lastUpdateTimeText);
                latstTimeUpdateText.setText("Last Update: " + dataResponse.getStatewise().get(0).getLastupdatedtime() + " IST");

                statewiseList = dataResponse.getStatewise();
                stateWiseRecyclerAdapter = new StateWiseRecyclerAdapter(statewiseList, 0);
                stateWiseRecyclerAdapter.setiAdapterOnClick(MainScreenActivity.this);
                recyclerView.setAdapter(stateWiseRecyclerAdapter);
                stateWiseRecyclerAdapter.notifyDataSetChanged();


            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            new ParseResponseAsync().execute(jsonObject);
        }

    }

    @Override
    public void onFail(VolleyError error, String errorMessage) {
        Utils.showMessage(getApplicationContext(), "Something went wrong");
    }

    @Override
    public void stateOnclickListener(String stateName, int position) {
        Intent intent = new Intent(this, DistrictActivity.class);
        intent.putExtra(ILiterals.INTENT_STATE_NAME, stateName);
        intent.putExtra(ILiterals.INTENT_POSITION, position);
        startActivity(intent);
    }


    public class CallCovidDataWebServiceAsync extends AsyncTask<String, String, JSONObject> {

        @Override
        protected JSONObject doInBackground(String... strings) {
            JSONObject jsonObject = null;

            CovideDataRequestResponse covideDataRequestResponse = new CovideDataRequestResponse();
            covideDataRequestResponse.CallWebService(ILiterals.STATE_DATA_COUNT_URL);
            covideDataRequestResponse.setiResponseCallBack(MainScreenActivity.this);
            return jsonObject;
        }
    }

    /**
     * Call web service async task
     * Getting json response array
     * Formatting response
     */
    public class CallWebServiceAsync extends AsyncTask<String, String, String> {
        JSONArray responseArray;
        String URL;

        @Override
        protected String doInBackground(String... strings) {
            URL = ILiterals.STATE_WISE_URL;
            StateRequestResponseClient stateRequestResponseClient = new StateRequestResponseClient();
            stateRequestResponseClient.callWebService(URL);
            stateRequestResponseClient.setiResponseCallBack(MainScreenActivity.this);
            return null;
        }
    }

    public class ParseResponseAsync extends AsyncTask<JSONObject, String, JSONArray> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected JSONArray doInBackground(JSONObject... jsonObjects) {
            String[] stateArray = ILiterals.STATE_ARRAY;
            String[][] districtArray = ILiterals.DISTRICT_ARRAY;
            JSONArray newJsonArray = new JSONArray();

            int confirmedCount = 0;
            int confirmedDeltaCount = 0;
            try {
                for (int i = 0; i < stateArray.length; i++) {
                    JSONObject newJsonRequest = new JSONObject();
                    newJsonRequest.put("stateName", stateArray[i]);
                    JSONArray stateArrayObject = new JSONArray();
                    JSONObject stateJson = (JSONObject) jsonObjects[0].get(stateArray[i]);
                    JSONObject districtDataJson = (JSONObject) stateJson.get("districtData");
                    String[] subArray = districtArray[i];
                    for (int j = 0; j < subArray.length; j++) {
                        if (districtDataJson.toString().contains(subArray[j])) {
                            JSONObject districtJsonObject = (JSONObject) districtDataJson.get(subArray[j]);
                            JSONObject newDistrictJsonObject = new JSONObject();
                            newDistrictJsonObject.put("districtName", subArray[j]);
                            newDistrictJsonObject.put("notes", districtJsonObject.get("notes"));

                            newDistrictJsonObject.put("active", districtJsonObject.get("active"));
                            newDistrictJsonObject.put("confirmed", districtJsonObject.get("confirmed"));
                            newDistrictJsonObject.put("recovered", districtJsonObject.get("recovered"));
                            newDistrictJsonObject.put("deceased", districtJsonObject.get("deceased"));

                            newDistrictJsonObject.put("delta", districtJsonObject.get("delta"));

                            confirmedCount = confirmedCount + districtJsonObject.getInt("confirmed");

                            JSONObject deltaJsonObject = (JSONObject) districtJsonObject.get("delta");
                            confirmedDeltaCount = confirmedDeltaCount + deltaJsonObject.getInt("confirmed");
                            stateArrayObject.put(newDistrictJsonObject);

                            newJsonRequest.put("districtData", stateArrayObject);

                        }

                    }
                    newJsonArray.put(newJsonRequest);

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return newJsonArray;
        }

        @Override
        protected void onPostExecute(JSONArray jsonArray) {

            ObjectMapper objectMapper = new ObjectMapper();
            try {
                List<StateRequestResponseClient.StateWiseDataResponse> stateWiseDataResponseList = Arrays.asList(objectMapper.readValue(jsonArray.toString(), StateRequestResponseClient.StateWiseDataResponse[].class));
                StateRequestResponseClient.setStateWiseDataResponseList(stateWiseDataResponseList);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public class StateWiseRecyclerAdapter extends RecyclerView.Adapter<StateWiseRecyclerAdapter.SateWiseViewHolder> {
        private List<CovideDataRequestResponse.Statewise> statewiseList;
        private int type = 0;

        private IAdapterOnClick iAdapterOnClick;


        public StateWiseRecyclerAdapter(List<CovideDataRequestResponse.Statewise> statewiseList, int type) {
            this.statewiseList = statewiseList;
            this.type = type;
        }

        public IAdapterOnClick getiAdapterOnClick() {
            return iAdapterOnClick;
        }

        public void setiAdapterOnClick(IAdapterOnClick iAdapterOnClick) {
            this.iAdapterOnClick = iAdapterOnClick;
        }

        @NonNull
        @Override
        public StateWiseRecyclerAdapter.SateWiseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View listItem = layoutInflater.inflate(R.layout.state_layout, parent, false);
            SateWiseViewHolder viewHolder = new SateWiseViewHolder(listItem);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull StateWiseRecyclerAdapter.SateWiseViewHolder holder, final int position) {
            final CovideDataRequestResponse.Statewise stateWiseDataResponse = statewiseList.get(position);

            if (stateWiseDataResponse.getState().equals("Total")) {
                holder.stateNameText.setText("INDIA(All States)");

            } else {
                holder.stateNameText.setText("" + stateWiseDataResponse.getState());

            }
            holder.confirmedText.setText("" + stateWiseDataResponse.getConfirmed());
            holder.activeText.setText("" + stateWiseDataResponse.getActive());
            holder.recoveredText.setText("" + stateWiseDataResponse.getRecovered());
            holder.deceasedText.setText("" + stateWiseDataResponse.getDeaths());

            holder.deltaConfirmText.setText("[+" + stateWiseDataResponse.getDeltaconfirmed() + "]");
            holder.deltaRecoveredText.setText("[+" + stateWiseDataResponse.getDeltarecovered() + "]");
            holder.deltaDeceasedText.setText("[+" + stateWiseDataResponse.getDeltadeaths() + "]");

            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (type == 0) {
                        if (position != 0) {
                            iAdapterOnClick.stateOnclickListener(stateWiseDataResponse.getState(), position);
                        }
                    } else {
                        iAdapterOnClick.stateOnclickListener(stateWiseDataResponse.getState(), position);
                    }

                }
            });
        }

        @Override
        public int getItemCount() {

            return statewiseList.size();
        }

        public class SateWiseViewHolder extends RecyclerView.ViewHolder {
            private MyTextView stateNameText;
            private MyTextView deltaConfirmText;
            private MyTextView confirmedText;
            private MyTextView deltaActiveText;
            private MyTextView activeText;
            private MyTextView deltaRecoveredText;
            private MyTextView recoveredText;
            private MyTextView deltaDeceasedText;
            private MyTextView deceasedText;
            private CardView cardView;


            public SateWiseViewHolder(View view) {
                super(view);
                stateNameText = view.findViewById(R.id.stateNameText);
                cardView = view.findViewById(R.id.stateCardView);
                deltaConfirmText = view.findViewById(R.id.deltaConfirmText);
                confirmedText = view.findViewById(R.id.confirmedText);
                deltaActiveText = view.findViewById(R.id.deltaActiveText);
                activeText = view.findViewById(R.id.activeText);
                deltaRecoveredText = view.findViewById(R.id.deltaRecoveredText);
                recoveredText = view.findViewById(R.id.recoveredText);
                deltaDeceasedText = view.findViewById(R.id.deltaDeceasedText);
                deceasedText = view.findViewById(R.id.deceasedText);
            }
        }
    }

}
