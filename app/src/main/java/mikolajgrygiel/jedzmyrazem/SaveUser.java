package mikolajgrygiel.jedzmyrazem;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.loopj.android.http.PersistentCookieStore;

import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import mikolajgrygiel.jedzmyrazem.enums.RestApiUrl;

/*
*
* SaveUser.java
* Class responsible for sending JSON request to save user data (register a new user).
 */
public class SaveUser {

    //region Properties

    // JSON Node names
    private static final String TAG_holder = "user";
    private static final String TAG_username = "username";
    private static final String TAG_email = "email";
    private static final String TAG_phone = "phone";
    private static final String TAG_password = "password";
    private static final String TAG_password_confirmation = "password_confirmation";

    Activity activity;

    private class Send extends AsyncTask<String, Void, String> {


        @Override
        protected String doInBackground(String... args) {
            // Creating service handler class instance
            ServiceHandler sh = new ServiceHandler();

            JSONObject json = new JSONObject();
            JSONObject userJson = new JSONObject();
            try {
                userJson.put(TAG_username, args[0]);
                userJson.put(TAG_email, args[1]);
                userJson.put(TAG_phone, args[2]);
                userJson.put(TAG_password, args[3]);
                userJson.put(TAG_password_confirmation, args[4]);
                json.put(TAG_holder, userJson);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(RestApiUrl.SIGN_UP.getUrl(), ServiceHandler.POST, json, null);

            Log.d("ANSWEAR SAVE_USER: ", jsonStr);

            return jsonStr;
        }




        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            try {
                JSONObject jsonObject = new JSONObject(result);
                if (jsonObject.has("username")){
                    editor = pref.edit();
                    editor.putString("logged", "true").apply();
                    Toast.makeText(activity.getApplicationContext(), activity.getResources().getString(R.string.register_registeredOk), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(activity, SearchActivity.class);
                    activity.startActivity(intent);
                } else {
                    Toast.makeText(activity.getApplicationContext(), activity.getResources().getString(R.string.register_registeredNotOk), Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(activity, RegistrationActivity.class);
                    activity.startActivity(intent);
                }

            } catch (JSONException e) {
                Toast.makeText(activity.getApplicationContext(), activity.getResources().getString(R.string.register_registeredNotOk), Toast.LENGTH_LONG).show();
                Intent intent = new Intent(activity, RegistrationActivity.class);
                activity.startActivity(intent);
                e.printStackTrace();
            }
        }

    }
    //endregion

    //region Constructor & init
    public SaveUser(Activity activity, String username, String email, String phone, String password, String password_confirmation){
        this.activity = activity;
        pref = activity.getSharedPreferences("testapp", Context.MODE_PRIVATE);
        cookieStore = new PersistentCookieStore(activity);
        httpClient.setCookieStore(cookieStore);
        new Send().execute(username, email, phone, password, password_confirmation);
    }
    public SaveUser(){

    }
    //endregion


    public final static DefaultHttpClient httpClient = new DefaultHttpClient();
    PersistentCookieStore cookieStore;
    SharedPreferences pref;
    SharedPreferences.Editor editor;

}
