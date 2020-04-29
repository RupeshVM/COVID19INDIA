package com.rupvm.covid19india.Activity;

import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rupvm.covid19india.Constants.ILiterals;
import com.rupvm.covid19india.Constants.NetworkUtils;
import com.rupvm.covid19india.Constants.Utils;
import com.rupvm.covid19india.CustomView.MyTextView;
import com.rupvm.covid19india.ICallBacks.IResponseCallBack;
import com.rupvm.covid19india.R;
import com.rupvm.covid19india.Service.NotificationDataRequestResponse;
import com.rupvm.covid19india.Service.StateRequestResponseClient;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class NotificationActivity extends AppCompatActivity implements IResponseCallBack {

    private RecyclerView recyclerView;
    private List<NotificationDataRequestResponse.NotificationResponse> notificationResponseList = new ArrayList<>();
    private NotificationAdapter notificationAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.notification_act));

        recyclerView = findViewById(R.id.notificationRecyclerView);
        notificationAdapter = new NotificationAdapter(notificationResponseList);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(notificationAdapter);
        notificationAdapter.notifyDataSetChanged();

        if (NetworkUtils.isConnected(getApplicationContext())) {

            NotificationDataRequestResponse notificationDataRequestResponse = new NotificationDataRequestResponse();
            notificationDataRequestResponse.CallWebService(ILiterals.LOG_URL);
            notificationDataRequestResponse.setiResponseCallBack(this);
        }else{
            Utils.showMessage(getApplicationContext(),"No network connection");
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }

    @Override
    public void onSuccess(List<StateRequestResponseClient.StateWiseDataResponse> stateWiseDataResponseList, JSONObject jsonObject, JSONArray jsonArray, JsonObjectRequest jsonObjectRequest) {
        if (jsonArray != null) {
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                NotificationDataRequestResponse.NotificationResponse[] notificationResponses = objectMapper.readValue(jsonArray.toString(), NotificationDataRequestResponse.NotificationResponse[].class);
                List<NotificationDataRequestResponse.NotificationResponse> notificationResponseList = Arrays.asList(notificationResponses);
                notificationAdapter = new NotificationAdapter(notificationResponseList);
                layoutManager = new LinearLayoutManager(this);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(notificationAdapter);
                notificationAdapter.notifyDataSetChanged();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onFail(VolleyError error, String errorMessage) {
        Utils.showMessage(getApplicationContext(), errorMessage);
    }

    public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder> {
        List<NotificationDataRequestResponse.NotificationResponse> notificationResponseList;

        public NotificationAdapter(List<NotificationDataRequestResponse.NotificationResponse> notificationResponseList) {
            this.notificationResponseList = notificationResponseList;
        }

        @NonNull
        @Override
        public NotificationAdapter.NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View listItem = layoutInflater.inflate(R.layout.notification_row, parent, false);
            NotificationViewHolder viewHolder = new NotificationViewHolder(listItem);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull NotificationAdapter.NotificationViewHolder holder, int position) {
            NotificationDataRequestResponse.NotificationResponse notificationResponse = notificationResponseList.get(position);
            holder.notificationText.setText("" + notificationResponse.getUpdate());
            holder.textTimestamp.setText("" + getDate(notificationResponse.getTimestamp()));
        }

        private String getDate(long time) {
            Calendar cal = Calendar.getInstance(Locale.ENGLISH);
            cal.setTimeInMillis(time * 1000);
            String date = DateFormat.format("dd-MM-yyyy", cal).toString();
            return date;
        }

        @Override
        public int getItemCount() {
            return notificationResponseList.size();
        }

        public class NotificationViewHolder extends RecyclerView.ViewHolder {
            private MyTextView notificationText;
            private MyTextView textTimestamp;

            public NotificationViewHolder(View view) {
                super(view);
                notificationText = view.findViewById(R.id.notificationText);
                textTimestamp = view.findViewById(R.id.textTimestamp);
            }


        }
    }
}
