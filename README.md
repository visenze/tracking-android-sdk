# tracking-android-sdk
ViSearch User Action Tracking SDK (Android)

## How to use this SDK
1. Create a `UserEventTracker` object
2. Create a `TrackingParams` object containing tracking information
3. Use the `UserEventTracker` object to track the `TrackingParams` object

Example:
```java
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Initialize a new UserEventTracker object with your app key
        String appKey = "Your_app_key";
        UserEventTracker userEventTracker = new UserEventTracker(this, appKey);

        // Wrap the information you want to track into a TrackingParams object
        String action = "purchase";
        String requestId = "Your_requestid";
        TrackingParams trackingParams = new TrackingParams(action, requestId);

        // Use UserEventTracker to track this action
        userEventTracker.track(trackingParams);
    }
}
```

## Note to developer: 
### User permission
- You need to add permission `<uses-permission android:name="android.permission.INTERNET" />` to the file AndroidManifest.xml in order use this SDK

### Class `TrackingParams`
- You can add custom parameters for tracking. 

  For example:
  ```java
        // Create new custom parameters
        HashMap<String, String> customParams = new HashMap<>();
        customParams.put("Your_custom_parameter", "Value");
        
        // Add the custom parameter
        TrackingParams trackingParams = new TrackingParams(action, requestId, customParams);

        userEventTracker.track(trackingParams);
  ```
- You can also set the user ID generated from your platform (e.g. account ID of the user)  

  For example:
  ```java
        trackingParams.setCuid("Your_cuid");
  ```  
- Don't include space in the name of the key for you custom tracking parameters in `TrackingParams` class. 

### Class `UserEventTracker`
- You can set your own endpoint for tracking. If it is not specified, the default endpoint http://track.visenze.com/__aq.gif will be used.

  For example:
  ```java
  UserEventTracker userEventTracker = new UserEventTracker(this, appKey, "Your endpoint");
  ```

