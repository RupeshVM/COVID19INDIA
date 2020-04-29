package com.rupvm.covid19india.Activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rupvm.covid19india.Constants.ILiterals;
import com.rupvm.covid19india.Constants.Utils;
import com.rupvm.covid19india.R;
import com.rupvm.covid19india.Service.StateRequestResponseClient;

import java.util.List;

public class DistrictActivity extends AppCompatActivity {

    private RecyclerView districtRecyclerView;
    private List<StateRequestResponseClient.DistrictDatum> districtDatumList;
    private RecyclerView.LayoutManager layoutManager;
    private boolean isDataAvailable = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_district);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String stateName = getIntent().getStringExtra(ILiterals.INTENT_STATE_NAME);
        getSupportActionBar().setTitle(stateName);
        districtRecyclerView = findViewById(R.id.districtRecyclerView);

        if (StateRequestResponseClient.getStateWiseDataResponseList() != null) {
            for (StateRequestResponseClient.StateWiseDataResponse stateWiseDataResponse : StateRequestResponseClient.getStateWiseDataResponseList()) {
                if (stateName.equals(stateWiseDataResponse.getStateName())) {
                    districtDatumList = stateWiseDataResponse.getDistrictData();
                    isDataAvailable = true;
                }
            }
        }
        if (isDataAvailable) {
            DistrictAdapter districtAdapter = new DistrictAdapter(districtDatumList);
            layoutManager = new LinearLayoutManager(this);
            districtRecyclerView.setLayoutManager(layoutManager);
            districtRecyclerView.setAdapter(districtAdapter);
            districtAdapter.notifyDataSetChanged();
        } else {
            Utils.showMessage(getApplicationContext(), "No Data Found");
        }

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public class DistrictAdapter extends RecyclerView.Adapter<DistrictAdapter.DistrictViewHolder> {
        private List<StateRequestResponseClient.DistrictDatum> districtDatumList;


        public DistrictAdapter(List<StateRequestResponseClient.DistrictDatum> districtDatumList) {
            this.districtDatumList = districtDatumList;
        }

        @Override
        public DistrictAdapter.DistrictViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View listItem = layoutInflater.inflate(R.layout.state_layout, parent, false);
            DistrictViewHolder viewHolder = new DistrictViewHolder(listItem);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull DistrictAdapter.DistrictViewHolder holder, int position) {
            StateRequestResponseClient.DistrictDatum stateWiseDataResponse = districtDatumList.get(position);
            holder.stateNameText.setText("" + stateWiseDataResponse.getDistrictName());
            holder.confirmedText.setText("" + stateWiseDataResponse.getConfirmed());
            holder.activeText.setText("" + stateWiseDataResponse.getActive());
            holder.recoveredText.setText("" + stateWiseDataResponse.getRecovered());
            holder.deceasedText.setText("" + stateWiseDataResponse.getDeceased());

            holder.deltaConfirmText.setText("[+" + stateWiseDataResponse.getDelta().getConfirmed() + "]");
            holder.deltaRecoveredText.setText("[+" + stateWiseDataResponse.getDelta().getRecovered() + "]");
            holder.deltaDeceasedText.setText("[+" + stateWiseDataResponse.getDelta().getDeceased() + "]");

        }

        @Override
        public int getItemCount() {
            return districtDatumList.size();
        }

        public class DistrictViewHolder extends RecyclerView.ViewHolder {
            private AppCompatTextView stateNameText;
            private AppCompatTextView deltaConfirmText;
            private AppCompatTextView confirmedText;
            private AppCompatTextView deltaActiveText;
            private AppCompatTextView activeText;
            private AppCompatTextView deltaRecoveredText;
            private AppCompatTextView recoveredText;
            private AppCompatTextView deltaDeceasedText;
            private AppCompatTextView deceasedText;
            private CardView cardView;


            public DistrictViewHolder(View view) {
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
