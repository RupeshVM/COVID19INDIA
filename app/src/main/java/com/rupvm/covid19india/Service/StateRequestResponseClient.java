package com.rupvm.covid19india.Service;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.rupvm.covid19india.Covid19IndiaApplication;
import com.rupvm.covid19india.ICallBacks.IResponseCallBack;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class StateRequestResponseClient implements Response.ErrorListener, Response.Listener<JSONObject> {


    public static List<StateWiseDataResponse> stateWiseDataResponseList = new ArrayList<>();
    public IResponseCallBack iResponseCallBack;
    private JsonObjectRequest jsonObjectRequest;

    public static List<StateWiseDataResponse> getStateWiseDataResponseList() {
        return stateWiseDataResponseList;
    }



    public static void setStateWiseDataResponseList(List<StateWiseDataResponse> stateWiseDataResponseList) {
        StateRequestResponseClient.stateWiseDataResponseList = stateWiseDataResponseList;
    }

    public IResponseCallBack getiResponseCallBack() {
        return iResponseCallBack;
    }

    public void setiResponseCallBack(IResponseCallBack iResponseCallBack) {
        this.iResponseCallBack = iResponseCallBack;
    }

    public void callWebService(String url) {
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        Covid19IndiaApplication.getInstance().addToRequestQueue(jsonObjectRequest, StateRequestResponseClient.class.getName());
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        iResponseCallBack.onFail(error, "Network issue");
    }

    @Override
    public void onResponse(JSONObject response) {
        iResponseCallBack.onSuccess(null, response,null, jsonObjectRequest);
    }

    public static class StateWiseDataResponse {
        @JsonProperty("stateName")
        private String stateName;
        @JsonProperty("districtData")
        private List<DistrictDatum> districtData = null;

        @JsonProperty("stateName")
        public String getStateName() {
            return stateName;
        }

        @JsonProperty("stateName")
        public void setStateName(String stateName) {
            this.stateName = stateName;
        }

        @JsonProperty("districtData")
        public List<DistrictDatum> getDistrictData() {
            return districtData;
        }

        @JsonProperty("districtData")
        public void setDistrictData(List<DistrictDatum> districtData) {
            this.districtData = districtData;
        }
    }


    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonPropertyOrder({
            "districtName",
            "notes",
            "active",
            "confirmed",
            "recovered",
            "deceased",
            "delta"
    })
    public static class DistrictDatum {

        @JsonProperty("districtName")
        private String districtName;
        @JsonProperty("notes")
        private String notes;
        @JsonProperty("active")
        private Integer active;
        @JsonProperty("confirmed")
        private Integer confirmed;
        @JsonProperty("recovered")
        private Integer recovered;
        @JsonProperty("deceased")
        private Integer deceased;
        @JsonProperty("delta")
        private Delta delta;

        @JsonProperty("districtName")
        public String getDistrictName() {
            return districtName;
        }

        @JsonProperty("districtName")
        public void setDistrictName(String districtName) {
            this.districtName = districtName;
        }

        @JsonProperty("notes")
        public String getNotes() {
            return notes;
        }

        @JsonProperty("notes")
        public void setNotes(String notes) {
            this.notes = notes;
        }

        @JsonProperty("active")
        public Integer getActive() {
            return active;
        }

        @JsonProperty("active")
        public void setActive(Integer active) {
            this.active = active;
        }

        @JsonProperty("confirmed")
        public Integer getConfirmed() {
            return confirmed;
        }

        @JsonProperty("confirmed")
        public void setConfirmed(Integer confirmed) {
            this.confirmed = confirmed;
        }

        @JsonProperty("recovered")
        public Integer getRecovered() {
            return recovered;
        }

        @JsonProperty("recovered")
        public void setRecovered(Integer recovered) {
            this.recovered = recovered;
        }

        @JsonProperty("deceased")
        public Integer getDeceased() {
            return deceased;
        }

        @JsonProperty("deceased")
        public void setDeceased(Integer deceased) {
            this.deceased = deceased;
        }

        @JsonProperty("delta")
        public Delta getDelta() {
            return delta;
        }

        @JsonProperty("delta")
        public void setDelta(Delta delta) {
            this.delta = delta;
        }

    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonPropertyOrder({
            "confirmed",
            "deceased",
            "recovered"
    })
    public static class Delta {

        @JsonProperty("confirmed")
        private Integer confirmed;
        @JsonProperty("deceased")
        private Integer deceased;
        @JsonProperty("recovered")
        private Integer recovered;

        @JsonProperty("confirmed")
        public Integer getConfirmed() {
            return confirmed;
        }

        @JsonProperty("confirmed")
        public void setConfirmed(Integer confirmed) {
            this.confirmed = confirmed;
        }

        @JsonProperty("deceased")
        public Integer getDeceased() {
            return deceased;
        }

        @JsonProperty("deceased")
        public void setDeceased(Integer deceased) {
            this.deceased = deceased;
        }

        @JsonProperty("recovered")
        public Integer getRecovered() {
            return recovered;
        }

        @JsonProperty("recovered")
        public void setRecovered(Integer recovered) {
            this.recovered = recovered;
        }
    }
}