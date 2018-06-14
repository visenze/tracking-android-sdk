package com.visenze.usertrackingsdk;

import android.content.Context;
import android.net.Uri;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.net.CookieHandler;
import java.net.CookieManager;
import java.util.HashMap;
import java.util.Map;


public class HttpInstance {
    /**
     * HTTP CONSTANT for Multipart Entity Uploading
     */
    public static final int TIME_OUT_FOR_ID = 5000;

    /**
     * http instance
     */
    private static HttpInstance     mInstance;

    /**
     * application context
     */
    private static Context          mContext;

    /**
     * request queue
     */
    private RequestQueue            mRequestQueue;

    /**
     * private constructor
     *
     * @param context application context
     */
    private HttpInstance(Context context) {
        CookieManager manager = new CookieManager();
        CookieHandler.setDefault(manager);
        mContext = context;
        mRequestQueue = getRequestQueue();
    }

    /**
     * singleton constructor
     *
     * @param context application context
     *
     * @return singleton instance
     */
    public static synchronized HttpInstance getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new HttpInstance(context);
        }

        return mInstance;
    }

    /**
     * request queue getter
     *
     * @return requestQueue
     */
    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(mContext.getApplicationContext());
        }

        return mRequestQueue;
    }

    public void addGetRequestToQueueWithoutResponse(
            final String url,
            Map<String, String> params) {
        if (null == params) {
            params = new HashMap<>();
        }

        Uri.Builder uri = new Uri.Builder();
        for (String s : params.keySet()) {
            uri.appendQueryParameter(s, params.get(s));
        }

        JsonWithHeaderRequest jsonObjectRequest = new JsonWithHeaderRequest(Request.Method.GET, url + uri.toString(),
                null,
                null,
                null)
        {
            //set auth information in header
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header = new HashMap<>();
                String uid = UIDManager.getUid();
                if (uid != null) {
                    header.put("Cookie", "uid=" + uid);
                }

                return header;
            }
        };

        jsonObjectRequest.setTag(mContext);
        getRequestQueue().add(jsonObjectRequest);

    }
}

