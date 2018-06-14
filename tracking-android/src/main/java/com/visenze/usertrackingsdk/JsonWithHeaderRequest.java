package com.visenze.usertrackingsdk;


import com.android.volley.Cache;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * Created by visenze on 29/2/16.
 */
public class JsonWithHeaderRequest extends JsonObjectRequest {

    public JsonWithHeaderRequest(int method, String url, JSONObject jsonRequest,
                                 Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {

        super(method, url, jsonRequest, listener, errorListener);
        setRetryPolicy(new DefaultRetryPolicy(HttpInstance.TIME_OUT_FOR_ID, 0, 1));
    }

    public JsonWithHeaderRequest(String url, JSONObject jsonRequest,
                                 Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        super(url, jsonRequest, listener, errorListener);
    }

    @Override
    protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
        try {
            String parse = HttpHeaderParser.parseCharset(response.headers);
            byte[] data = response.data;
            String jsonString = new String(data, parse);
            JsonWithHeaderRequest.customParseHeader(response);

            JSONObject result = new JSONObject(jsonString);
            Cache.Entry headerParse = HttpHeaderParser.parseCacheHeaders(response);
            return Response.success(result, headerParse);
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JSONException je) {
            return Response.error(new ParseError(je));
        }
    }

    private static void customParseHeader(NetworkResponse response) {
        Map headers = response.headers;
        if (headers.containsKey("Set-Cookie")) {
            String value = (String)headers.get("Set-Cookie");
            String[] cv = value.split(";");
            String[] uid = JsonWithHeaderRequest.parseUid(cv);
            if (uid.length > 0) {
                UIDManager.updateUidFromCookie(uid[1]);
            }
        }
    }

    private static String[] parseUid(String[] cv) {
        String[] uid = new String[0];
        for (String v : cv) {
            if (v.startsWith("uid")) {
                uid = v.split("=");
                break;
            }
        }
        return uid;
    }
}

