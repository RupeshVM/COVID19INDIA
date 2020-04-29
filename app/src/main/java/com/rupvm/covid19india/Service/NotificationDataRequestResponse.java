package com.rupvm.covid19india.Service;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.rupvm.covid19india.Constants.ILiterals;
import com.rupvm.covid19india.Covid19IndiaApplication;
import com.rupvm.covid19india.ICallBacks.IResponseCallBack;

import org.json.JSONArray;
import org.json.JSONObject;

public class NotificationDataRequestResponse implements Response.ErrorListener, Response.Listener<JSONArray> {
    private JsonArrayRequest jsonObjectRequest;
    private IResponseCallBack iResponseCallBack;

    public IResponseCallBack getiResponseCallBack() {
        return iResponseCallBack;
    }

    public void setiResponseCallBack(IResponseCallBack iResponseCallBack) {
        this.iResponseCallBack = iResponseCallBack;
    }

    public void CallWebService(String URL) {
        jsonObjectRequest = new JsonArrayRequest(Request.Method.GET, URL, null, this, this);
        Covid19IndiaApplication.getInstance().addToRequestQueue(jsonObjectRequest, CovideDataRequestResponse.class.getName());
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        iResponseCallBack.onFail(error, ILiterals.SOME_THING_WENT_WRONG);
    }

    @Override
    public void onResponse(JSONArray response) {
        iResponseCallBack.onSuccess(null, null,response, null);
    }


    public static class NotificationResponse {

        @JsonProperty("update")
        private String update;
        @JsonProperty("timestamp")
        private Integer timestamp;

        @JsonProperty("update")
        public String getUpdate() {
            return update;
        }

        @JsonProperty("update")
        public void setUpdate(String update) {
            this.update = update;
        }

        @JsonProperty("timestamp")
        public Integer getTimestamp() {
            return timestamp;
        }

        @JsonProperty("timestamp")
        public void setTimestamp(Integer timestamp) {
            this.timestamp = timestamp;
        }
    }
}
