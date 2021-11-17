package com.jose.p2system;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

public class LoginActivity extends AppCompatActivity {

    public static String TAG = "ACT_LOGIN";
    private EditText ed_email, ed_password;
    private Button btnLogin;
    private TextView et_register;
    private ProgressBar loading;
    private CheckBox remember;
    //    private static String URL_LOGIN = "http://192.168.0.14/p2system/login.php";
    private static String URL_LOGIN = "";
    private static String BASE_URL = "http://192.168.42.108/app_connection/app-connection-p2p/development/"; //android phone ko
//    private static String BASE_URL = "http://192.168.100.105/app_connection/app-connection-p2p/development/"; //android emulator ko
//    private static String BASE_URL = "http:/10.0.2.2/app_connection/app-connection-p2p/development/"; //

    URL url;
    SessionManager sessionManager;
    BottomSheetDialog bottomSheetDialog;
    Handler mHandler;
    String status_code, status_message, token_auth;
    RelativeLayout parentLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        sessionManager = new SessionManager(this);

        ed_email = findViewById(R.id.ed_email);
        ed_password = findViewById(R.id.ed_password);
        btnLogin = findViewById(R.id.btnLogin);
        et_register = findViewById(R.id.et_register);
        remember = findViewById(R.id.remember);
        loading = findViewById(R.id.loading);


        parentLayout = findViewById(R.id.parentLayout);

        SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
        String checkbox = preferences.getString("remember", "");

        if (checkbox.equals("true")) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        } else if (checkbox.equals("false")) {
            Toast.makeText(this, "Please Sign In", Toast.LENGTH_SHORT).show();
        }

        btnLogin.setOnClickListener(view -> {
            String mEmail = ed_email.getText().toString().trim();
            String mPass = ed_password.getText().toString().trim();

            if (!mEmail.isEmpty() || !mPass.isEmpty()) {
//            if (!mEmail.isEmpty()) {
//                Login(mEmail, mPass);


                // * 6a657373696b61795048 * //
                // OKHTTPCLIENT METHOD CONNECTION

                ConnectivityManager connectivityManager = ((ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE));
                //CHECK IF CONNECTED TO INTERNET WIFI/DATA
                if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED || connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED) {
                    try {

                        url = new URL(BASE_URL + "crud/login.php");
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }

                    OkHttpClient client = new OkHttpClient();
                    final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

                    JSONObject jsonObject = new JSONObject();

                    try {
                        jsonObject.put("user_email", mEmail);
                        jsonObject.put("user_password", mPass);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    RequestBody body = RequestBody.create(JSON, jsonObject.toString());
                    okhttp3.Request request = new okhttp3.Request.Builder()
                            .url(url)
                            .post(body)
                            .build();

                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
//                            Toast.makeText(LoginActivity.this, "onFailure", Toast.LENGTH_SHORT).show();
                            Log.e(TAG, "onFailure: Failed to upload data to server " + e);

                        }

                        @Override
                        public void onResponse(Call call, okhttp3.Response response) throws IOException {

                            String jsonData = response.body().string();

                            try {
                                JSONObject mJsonObject = new JSONObject(jsonData);

                                status_code = mJsonObject.getString("status_code");
                                status_message = mJsonObject.getString("status_message");
                                Log.d(TAG, "STATUS : " + status_code + status_message);

                                token_auth = mJsonObject.getString("token_auth");

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            if (response.isSuccessful()) {

                                Log.d(TAG, "TOKEN" + token_auth);

                                // SHARED PREFERENCES THE TOKEN ONCE INPUT CREDENTIALS ARE CORRECT IN LOGIN FORM
                                SharedPreferences sharedPreferences = getSharedPreferences("sharedPref", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("data_token", token_auth);
                                editor.commit();

                                //INTENT
                                Intent intent = new Intent(LoginActivity.this, HomeScreenActivity.class);
                                startActivity(intent);

                            } else { //IF RESPONSE STATUS CODE IS UNSUCCESSFUL
                                switch (response.code()) {
                                    case 401:
                                        Log.d(TAG, status_code);

                                        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
//                                                Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_LONG).show();
                                                Snackbar snackbar = Snackbar.make(parentLayout, status_message, Snackbar.LENGTH_LONG).setAction("", view -> {

                                                });
                                                View view = snackbar.getView();
                                                FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) view.getLayoutParams();
                                                params.gravity = Gravity.TOP;
                                                view.setLayoutParams(params);
                                                view.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.color_error));
                                                snackbar.setActionTextColor(Color.WHITE); // CHANGING MESSAGE TEXT COLOR

                                                // CHANGING ACTION BUTTON TEXT COLOR
                                                View sbView = snackbar.getView();
                                                TextView textView = sbView.findViewById(com.google.android.material.R.id.snackbar_text);
                                                textView.setTextColor(Color.WHITE);

                                                snackbar.show();
                                            }
                                        }, 2000);
                                        break;
                                    case 404:
//                                        Toast.makeText(LoginActivity.this, "not found", Toast.LENGTH_SHORT).show();
                                        new Handler(Looper.getMainLooper()).postDelayed(() -> {
                                            Snackbar snackbar = Snackbar.make(parentLayout, "not found", Snackbar.LENGTH_LONG).setAction("", view -> {

                                            });
                                            View view = snackbar.getView();
                                            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) view.getLayoutParams();
                                            params.gravity = Gravity.TOP;
                                            view.setLayoutParams(params);
                                            view.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.color_error));
                                            snackbar.setActionTextColor(Color.WHITE); // CHANGING MESSAGE TEXT COLOR

                                            // CHANGING ACTION BUTTON TEXT COLOR
                                            View sbView = snackbar.getView();
                                            TextView textView = sbView.findViewById(com.google.android.material.R.id.snackbar_text);
                                            textView.setTextColor(Color.WHITE);

                                            snackbar.show();
                                        }, 2000);
                                        break;
                                    case 500:
//                                        Toast.makeText(LoginActivity.this, "server broken", Toast.LENGTH_SHORT).show();
                                        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                Snackbar snackbar = Snackbar.make(parentLayout, "server broken", Snackbar.LENGTH_LONG).setAction("", view -> {

                                                });
                                                View view = snackbar.getView();
                                                FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) view.getLayoutParams();
                                                params.gravity = Gravity.TOP;
                                                view.setLayoutParams(params);
                                                view.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.color_error));
                                                snackbar.setActionTextColor(Color.WHITE); // CHANGING MESSAGE TEXT COLOR

                                                // CHANGING ACTION BUTTON TEXT COLOR
                                                View sbView = snackbar.getView();
                                                TextView textView = sbView.findViewById(com.google.android.material.R.id.snackbar_text);
                                                textView.setTextColor(Color.WHITE);

                                                snackbar.show();
                                            }
                                        }, 2000);
                                        break;
                                    default:
//                                        Toast.makeText(LoginActivity.this, "unknown error", Toast.LENGTH_SHORT).show();
                                        new Handler(Looper.getMainLooper()).postDelayed(() -> {
                                            Snackbar snackbar = Snackbar.make(parentLayout, "unknown error", Snackbar.LENGTH_LONG).setAction("", view12 -> {

                                            });
                                            View view12 = snackbar.getView();
                                            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) view12.getLayoutParams();
                                            params.gravity = Gravity.TOP;
                                            view12.setLayoutParams(params);
                                            view12.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.color_error));
                                            snackbar.setActionTextColor(Color.WHITE); // CHANGING MESSAGE TEXT COLOR

                                            // CHANGING ACTION BUTTON TEXT COLOR
                                            View sbView = snackbar.getView();
                                            TextView textView = sbView.findViewById(com.google.android.material.R.id.snackbar_text);
                                            textView.setTextColor(Color.WHITE);

                                            snackbar.show();
                                        }, 2000);
                                        break;
                                }
                            }
                        }
                    });
                } else {
                    Snackbar snackbar = Snackbar.make(parentLayout, "No internet connection", Snackbar.LENGTH_LONG).setAction("RETRY", view1 -> {

                    });
                    View view11 = snackbar.getView();
                    FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) view11.getLayoutParams();
                    params.gravity = Gravity.TOP;
                    view11.setLayoutParams(params);
                    view11.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.color_error));
                    snackbar.setActionTextColor(Color.WHITE); // CHANGING MESSAGE TEXT COLOR

                    // CHANGING ACTION BUTTON TEXT COLOR
                    View sbView = snackbar.getView();
                    TextView textView = sbView.findViewById(com.google.android.material.R.id.snackbar_text);
                    textView.setTextColor(Color.WHITE);

                    snackbar.show();
                }


            } else {
                ed_email.setError("Please insert email");
            }
//            ed_password.setError("Please insert password");
        });


        remember.setOnCheckedChangeListener((compoundButton, b) -> {
            if (compoundButton.isChecked()) {
                SharedPreferences preferences1 = getSharedPreferences("checkbox", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences1.edit();
                editor.putString("remember", "true");
                editor.apply();
                Toast.makeText(LoginActivity.this, "Checked", Toast.LENGTH_SHORT).show();

            } else if (!compoundButton.isChecked()) {
                SharedPreferences preferences1 = getSharedPreferences("checkbox", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences1.edit();
                editor.putString("remember", "false");
                editor.apply();
                Toast.makeText(LoginActivity.this, "Unchecked", Toast.LENGTH_SHORT).show();
            }
        });

        et_register.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });

    }


    private void Login(String email, String password) {


        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_LOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("login");

                            if (success.equals("1")) {
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject object = jsonArray.getJSONObject(i);

                                    String name = object.getString("name").trim();
                                    String email = object.getString("email").trim();
                                    String phone = object.getString("phone").trim();
                                    String address = object.getString("address").trim();
                                    String id = object.getString("id").trim();

                                    sessionManager.createSession(name, email, phone, address, id);

                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    intent.putExtra("name", name);
                                    intent.putExtra("email", email);
                                    intent.putExtra("phone", phone);
                                    intent.putExtra("address", address);
                                    startActivity(intent);


                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();

                            Toast.makeText(LoginActivity.this, "Login failed! Please check your Email/Password! ", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(LoginActivity.this, "Login timeout! ", Toast.LENGTH_SHORT).show();
                    }
                }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                params.put("password", password);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }


    public void callLogin() {

        ConnectivityManager connectivityManager = ((ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE));


        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED || connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED) {

        }
    }


}