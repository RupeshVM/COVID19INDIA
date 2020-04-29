package com.rupvm.covid19india.ICallBacks;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.rupvm.covid19india.Service.StateRequestResponseClient;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public interface IResponseCallBack {
    void onSuccess(List<StateRequestResponseClient.StateWiseDataResponse> stateWiseDataResponseList, JSONObject jsonObject, JSONArray jsonArray, JsonObjectRequest jsonObjectRequest);
    void onFail(VolleyError error, String errorMessage);
}
