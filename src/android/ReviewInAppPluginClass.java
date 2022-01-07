package ReviewInAppPlugin;

import android.util.Log;

import com.google.android.play.core.review.ReviewInfo;
import com.google.android.play.core.review.ReviewManager;
import com.google.android.play.core.review.ReviewManagerFactory;
import com.google.android.play.core.tasks.Task;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
/**
 * This class echoes a string called from JavaScript.
 */
public class ReviewInAppPluginClass extends CordovaPlugin {

    String LOG="ReviewInAppPluginClass";

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if (action.equals("requestReview")) {
            String message = args.getString(0);
            this.requestReview(message, callbackContext);
            return true;
        }
        return false;
    }

    private void requestReview(String message, CallbackContext callbackContext) {

        ReviewManager manager = ReviewManagerFactory.create(cordova.getContext());

        Task<ReviewInfo> request = manager.requestReviewFlow();
        request.addOnCompleteListener(task -> {
           if(task.isComplete()){
               ReviewInfo reviewInfo = task.getResult();
               Log.d(LOG,reviewInfo.toString());
           }else{
               String reviewErrorCode =  task.getException().toString();
               Log.d(LOG,reviewErrorCode);
           }
        });
    }
}