package com.visenze.usertrackingsdk;

import android.content.Context;

import java.util.HashMap;

/**
 * Created by visenze on 26/2/16.
 */
public class UserEventTracker {
    private HashMap<String, String> initParams;

    /**
     * volley http instance
     */
    private HttpInstance httpInstance;

    private String endPoint = "http://track.visenze.com/__aq.gif";

    /**
     * Create a user event tracker
     * @param context
     * @param initParams An init mapping and it must contain at least one mapping
     */
    public UserEventTracker(Context context, HashMap<String, String> initParams) throws Exception {
        if(initParams.size() < 1) {
            throw new Exception("Invalid initParams");
        }
        this.initParams = initParams;
        this.httpInstance = HttpInstance.getInstance(context.getApplicationContext());
        UIDManager.initUIDManager(context);
    }

    /**
     * Create a user event tracker
     * @param context
     * @param initParams An init mapping
     * @param endPoint The end point for receiving tracking statistics
     */
    public UserEventTracker(Context context, HashMap<String, String> initParams, String endPoint) {
        this.initParams = initParams;
        this.httpInstance = HttpInstance.getInstance(context.getApplicationContext());
        this.endPoint = endPoint;
        UIDManager.initUIDManager(context);
    }

    public void track(TrackingParams trackParams) {
        httpInstance.addGetRequestToQueueWithoutResponse(endPoint, trackParams.toMap(initParams));
    }
}

