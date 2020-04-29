package com.rupvm.covid19india.Service;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.rupvm.covid19india.Constants.ILiterals;
import com.rupvm.covid19india.Covid19IndiaApplication;
import com.rupvm.covid19india.ICallBacks.IResponseCallBack;

import org.json.JSONObject;

import java.util.List;

public class CovideDataRequestResponse implements Response.ErrorListener, Response.Listener<JSONObject> {

    private IResponseCallBack iResponseCallBack;
    private JsonObjectRequest jsonObjectRequest;

    public CovideDataRequestResponse() {
    }

    public IResponseCallBack getiResponseCallBack() {
        return iResponseCallBack;
    }

    public void setiResponseCallBack(IResponseCallBack iResponseCallBack) {
        this.iResponseCallBack = iResponseCallBack;
    }

    public void CallWebService(String URL) {
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, URL, null, this, this);
        Covid19IndiaApplication.getInstance().addToRequestQueue(jsonObjectRequest, CovideDataRequestResponse.class.getName());
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        iResponseCallBack.onFail(error, ILiterals.SOME_THING_WENT_WRONG);
    }

    @Override
    public void onResponse(JSONObject response) {
        iResponseCallBack.onSuccess(null, response,null, jsonObjectRequest);
    }


    public static class DataResponse {
        public DataResponse() {
        }

        @JsonProperty("cases_time_series")
        private List<Cases_time_series> cases_time_series = null;
        @JsonProperty("statewise")
        private List<Statewise> statewise = null;
        @JsonProperty("tested")
        private List<Tested> tested = null;

        @JsonProperty("cases_time_series")
        public List<Cases_time_series> getCases_time_series() {
            return cases_time_series;
        }

        @JsonProperty("cases_time_series")
        public void setCases_time_series(List<Cases_time_series> cases_time_series) {
            this.cases_time_series = cases_time_series;
        }

        @JsonProperty("statewise")
        public List<Statewise> getStatewise() {
            return statewise;
        }

        @JsonProperty("statewise")
        public void setStatewise(List<Statewise> statewise) {
            this.statewise = statewise;
        }

        @JsonProperty("tested")
        public List<Tested> getTested() {
            return tested;
        }

        @JsonProperty("tested")
        public void setTested(List<Tested> tested) {
            this.tested = tested;
        }

    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonPropertyOrder({
            "active",
            "confirmed",
            "deaths",
            "deltaconfirmed",
            "deltadeaths",
            "deltarecovered",
            "lastupdatedtime",
            "recovered",
            "state",
            "statecode",
            "statenotes"
    })
    public static class Statewise {
        public Statewise() {
        }

        @JsonProperty("active")
        private String active;
        @JsonProperty("confirmed")
        private String confirmed;
        @JsonProperty("deaths")
        private String deaths;
        @JsonProperty("deltaconfirmed")
        private String deltaconfirmed;
        @JsonProperty("deltadeaths")
        private String deltadeaths;
        @JsonProperty("deltarecovered")
        private String deltarecovered;
        @JsonProperty("lastupdatedtime")
        private String lastupdatedtime;
        @JsonProperty("recovered")
        private String recovered;
        @JsonProperty("state")
        private String state;
        @JsonProperty("statecode")
        private String statecode;
        @JsonProperty("statenotes")
        private String statenotes;

        @JsonProperty("active")
        public String getActive() {
            return active;
        }

        @JsonProperty("active")
        public void setActive(String active) {
            this.active = active;
        }

        @JsonProperty("confirmed")
        public String getConfirmed() {
            return confirmed;
        }

        @JsonProperty("confirmed")
        public void setConfirmed(String confirmed) {
            this.confirmed = confirmed;
        }

        @JsonProperty("deaths")
        public String getDeaths() {
            return deaths;
        }

        @JsonProperty("deaths")
        public void setDeaths(String deaths) {
            this.deaths = deaths;
        }

        @JsonProperty("deltaconfirmed")
        public String getDeltaconfirmed() {
            return deltaconfirmed;
        }

        @JsonProperty("deltaconfirmed")
        public void setDeltaconfirmed(String deltaconfirmed) {
            this.deltaconfirmed = deltaconfirmed;
        }

        @JsonProperty("deltadeaths")
        public String getDeltadeaths() {
            return deltadeaths;
        }

        @JsonProperty("deltadeaths")
        public void setDeltadeaths(String deltadeaths) {
            this.deltadeaths = deltadeaths;
        }

        @JsonProperty("deltarecovered")
        public String getDeltarecovered() {
            return deltarecovered;
        }

        @JsonProperty("deltarecovered")
        public void setDeltarecovered(String deltarecovered) {
            this.deltarecovered = deltarecovered;
        }

        @JsonProperty("lastupdatedtime")
        public String getLastupdatedtime() {
            return lastupdatedtime;
        }

        @JsonProperty("lastupdatedtime")
        public void setLastupdatedtime(String lastupdatedtime) {
            this.lastupdatedtime = lastupdatedtime;
        }

        @JsonProperty("recovered")
        public String getRecovered() {
            return recovered;
        }

        @JsonProperty("recovered")
        public void setRecovered(String recovered) {
            this.recovered = recovered;
        }

        @JsonProperty("state")
        public String getState() {
            return state;
        }

        @JsonProperty("state")
        public void setState(String state) {
            this.state = state;
        }

        @JsonProperty("statecode")
        public String getStatecode() {
            return statecode;
        }

        @JsonProperty("statecode")
        public void setStatecode(String statecode) {
            this.statecode = statecode;
        }

        @JsonProperty("statenotes")
        public String getStatenotes() {
            return statenotes;
        }

        @JsonProperty("statenotes")
        public void setStatenotes(String statenotes) {
            this.statenotes = statenotes;
        }

    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonPropertyOrder({
            "dailyconfirmed",
            "dailydeceased",
            "dailyrecovered",
            "date",
            "totalconfirmed",
            "totaldeceased",
            "totalrecovered"
    })
    public static class Cases_time_series {
        public Cases_time_series() {
        }

        @JsonProperty("dailyconfirmed")
        private String dailyconfirmed;
        @JsonProperty("dailydeceased")
        private String dailydeceased;
        @JsonProperty("dailyrecovered")
        private String dailyrecovered;
        @JsonProperty("date")
        private String date;
        @JsonProperty("totalconfirmed")
        private String totalconfirmed;
        @JsonProperty("totaldeceased")
        private String totaldeceased;
        @JsonProperty("totalrecovered")
        private String totalrecovered;

        @JsonProperty("dailyconfirmed")
        public String getDailyconfirmed() {
            return dailyconfirmed;
        }

        @JsonProperty("dailyconfirmed")
        public void setDailyconfirmed(String dailyconfirmed) {
            this.dailyconfirmed = dailyconfirmed;
        }

        @JsonProperty("dailydeceased")
        public String getDailydeceased() {
            return dailydeceased;
        }

        @JsonProperty("dailydeceased")
        public void setDailydeceased(String dailydeceased) {
            this.dailydeceased = dailydeceased;
        }

        @JsonProperty("dailyrecovered")
        public String getDailyrecovered() {
            return dailyrecovered;
        }

        @JsonProperty("dailyrecovered")
        public void setDailyrecovered(String dailyrecovered) {
            this.dailyrecovered = dailyrecovered;
        }

        @JsonProperty("date")
        public String getDate() {
            return date;
        }

        @JsonProperty("date")
        public void setDate(String date) {
            this.date = date;
        }

        @JsonProperty("totalconfirmed")
        public String getTotalconfirmed() {
            return totalconfirmed;
        }

        @JsonProperty("totalconfirmed")
        public void setTotalconfirmed(String totalconfirmed) {
            this.totalconfirmed = totalconfirmed;
        }

        @JsonProperty("totaldeceased")
        public String getTotaldeceased() {
            return totaldeceased;
        }

        @JsonProperty("totaldeceased")
        public void setTotaldeceased(String totaldeceased) {
            this.totaldeceased = totaldeceased;
        }

        @JsonProperty("totalrecovered")
        public String getTotalrecovered() {
            return totalrecovered;
        }

        @JsonProperty("totalrecovered")
        public void setTotalrecovered(String totalrecovered) {
            this.totalrecovered = totalrecovered;
        }

    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonPropertyOrder({
            "individualstestedperconfirmedcase",
            "positivecasesfromsamplesreported",
            "samplereportedtoday",
            "source",
            "testpositivityrate",
            "testsconductedbyprivatelabs",
            "testsperconfirmedcase",
            "totalindividualstested",
            "totalpositivecases",
            "totalsamplestested",
            "updatetimestamp"
    })
    public static class Tested {
        public Tested() {
        }

        @JsonProperty("individualstestedperconfirmedcase")
        private String individualstestedperconfirmedcase;
        @JsonProperty("positivecasesfromsamplesreported")
        private String positivecasesfromsamplesreported;
        @JsonProperty("samplereportedtoday")
        private String samplereportedtoday;
        @JsonProperty("source")
        private String source;
        @JsonProperty("testpositivityrate")
        private String testpositivityrate;
        @JsonProperty("testsconductedbyprivatelabs")
        private String testsconductedbyprivatelabs;
        @JsonProperty("testsperconfirmedcase")
        private String testsperconfirmedcase;
        @JsonProperty("totalindividualstested")
        private String totalindividualstested;
        @JsonProperty("totalpositivecases")
        private String totalpositivecases;
        @JsonProperty("totalsamplestested")
        private String totalsamplestested;
        @JsonProperty("updatetimestamp")
        private String updatetimestamp;

        @JsonProperty("individualstestedperconfirmedcase")
        public String getIndividualstestedperconfirmedcase() {
            return individualstestedperconfirmedcase;
        }

        @JsonProperty("individualstestedperconfirmedcase")
        public void setIndividualstestedperconfirmedcase(String individualstestedperconfirmedcase) {
            this.individualstestedperconfirmedcase = individualstestedperconfirmedcase;
        }

        @JsonProperty("positivecasesfromsamplesreported")
        public String getPositivecasesfromsamplesreported() {
            return positivecasesfromsamplesreported;
        }

        @JsonProperty("positivecasesfromsamplesreported")
        public void setPositivecasesfromsamplesreported(String positivecasesfromsamplesreported) {
            this.positivecasesfromsamplesreported = positivecasesfromsamplesreported;
        }

        @JsonProperty("samplereportedtoday")
        public String getSamplereportedtoday() {
            return samplereportedtoday;
        }

        @JsonProperty("samplereportedtoday")
        public void setSamplereportedtoday(String samplereportedtoday) {
            this.samplereportedtoday = samplereportedtoday;
        }

        @JsonProperty("source")
        public String getSource() {
            return source;
        }

        @JsonProperty("source")
        public void setSource(String source) {
            this.source = source;
        }

        @JsonProperty("testpositivityrate")
        public String getTestpositivityrate() {
            return testpositivityrate;
        }

        @JsonProperty("testpositivityrate")
        public void setTestpositivityrate(String testpositivityrate) {
            this.testpositivityrate = testpositivityrate;
        }

        @JsonProperty("testsconductedbyprivatelabs")
        public String getTestsconductedbyprivatelabs() {
            return testsconductedbyprivatelabs;
        }

        @JsonProperty("testsconductedbyprivatelabs")
        public void setTestsconductedbyprivatelabs(String testsconductedbyprivatelabs) {
            this.testsconductedbyprivatelabs = testsconductedbyprivatelabs;
        }

        @JsonProperty("testsperconfirmedcase")
        public String getTestsperconfirmedcase() {
            return testsperconfirmedcase;
        }

        @JsonProperty("testsperconfirmedcase")
        public void setTestsperconfirmedcase(String testsperconfirmedcase) {
            this.testsperconfirmedcase = testsperconfirmedcase;
        }

        @JsonProperty("totalindividualstested")
        public String getTotalindividualstested() {
            return totalindividualstested;
        }

        @JsonProperty("totalindividualstested")
        public void setTotalindividualstested(String totalindividualstested) {
            this.totalindividualstested = totalindividualstested;
        }

        @JsonProperty("totalpositivecases")
        public String getTotalpositivecases() {
            return totalpositivecases;
        }

        @JsonProperty("totalpositivecases")
        public void setTotalpositivecases(String totalpositivecases) {
            this.totalpositivecases = totalpositivecases;
        }

        @JsonProperty("totalsamplestested")
        public String getTotalsamplestested() {
            return totalsamplestested;
        }

        @JsonProperty("totalsamplestested")
        public void setTotalsamplestested(String totalsamplestested) {
            this.totalsamplestested = totalsamplestested;
        }

        @JsonProperty("updatetimestamp")
        public String getUpdatetimestamp() {
            return updatetimestamp;
        }

        @JsonProperty("updatetimestamp")
        public void setUpdatetimestamp(String updatetimestamp) {
            this.updatetimestamp = updatetimestamp;
        }

    }


}
