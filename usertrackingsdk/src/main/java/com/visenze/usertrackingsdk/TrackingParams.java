package com.visenze.usertrackingsdk;

import java.util.HashMap;
import java.util.Map;

/**
 Parameters you want to track in a user event
 */
public class TrackingParams {
    private String action;
    private String requestId;
    private String cuid;

    // The mapping of customized tracking parameters
    // and their corresponding value
    private Map<String, String> customParams;

    /**
     * Setup the compulsory tracking parameters
     * @param action The action of the user event you want to track,
     *               e.g. "add-to-wish-list", "view", "click"
     * @param requestId The request id of the current search provided
     *                  by ViSenze search service
     */
    public TrackingParams(String action, String requestId) {
        this.action = action;
        this.requestId = requestId;
        customParams = new HashMap<String, String>();
    }

    /**
     * Setup the compulsory tracking parameters
     * @param action The action of the user event you want to track,
     *               e.g. "add-to-wish-list", "view", "click"
     * @param requestId The request id of the current search provided
     *                  by ViSenze search service
     * @param customParams The mapping of customized tracking parameters
     *                  and their corresponding value
     */
    public TrackingParams(String action, String requestId, HashMap<String, String> customParams) {
        this.action = action;
        this.requestId = requestId;
        this.customParams = customParams;
    }

    /**
     * Set the user ID generated from your platform, e.g. account ID of the user
     * @param cuid
     */
    public void setCuid(String cuid) {
        this.cuid = cuid;
    }

    /**
     * Set The mapping of customized tracking parameters
     * and their corresponding value
     * @param customParams
     */
    public void setCustomParams(Map<String, String> customParams) {
        this.customParams = customParams;
    }

    /**
     * Transform the TrackingParams object to a key value pair
     * containing all the specified parameters and their corresponding value
     * @param accessKey
     * @return a mapping represents all the parameters
     */
    public Map<String, String> toMap(String accessKey) {
        Map<String, String> map = new HashMap<>();
        map.put("cid", accessKey);

        if (cuid != null) {
            map.put("cuid", cuid);
        }

        if (requestId != null) {
            map.put("reqid", requestId);
        }

        if (action != null) {
            map.put("action", action);
        }

        map.putAll(customParams);

        return map;
    }
}
