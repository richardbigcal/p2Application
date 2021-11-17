package com.jose.p2system;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.google.android.material.snackbar.Snackbar;
import com.jose.p2system.fields.UserProfileField;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RegisterActivity extends AppCompatActivity {

    AwesomeValidation awesomeValidation;
    private EditText dt_Name, dt_Email, dt_Phone, dt_Address, dt_Password, dt_Confirm;
    private TextView tv_login;
    private Button btnRegister;
    private ProgressBar loading;
    private static String URL_REGISTER = "http://192.168.0.14/p2system/register.php";

    public static String TAG = "ACT_REGISTER";

    private static String BASE_URL = "http://192.168.42.108/app_connection/app-connection-p2p/development/"; //android phone ko
    //    private static String BASE_URL = "http://192.168.100.105/app_connection/app-connection-p2p/development/"; //android emulator ko
    URL url;
    String status_code, status_message;
    RelativeLayout parentLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().hide();

        parentLayout = findViewById(R.id.parentLayout);
        loading = findViewById(R.id.loading);
        dt_Name = findViewById(R.id.dt_Name);
        dt_Email = findViewById(R.id.dt_Email);
        dt_Phone = findViewById(R.id.dt_Phone);
        dt_Address = findViewById(R.id.dt_Address);
        dt_Password = findViewById(R.id.dt_Password);
        dt_Confirm = findViewById(R.id.dt_Confirm);
        btnRegister = findViewById(R.id.btnRegister);

        tv_login = findViewById(R.id.tv_login);
        tv_login.setOnClickListener(view -> clickLogin());

        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        awesomeValidation.addValidation(this, R.id.dt_Password, ".{6,}", R.string.wrongpassword);
        awesomeValidation.addValidation(this, R.id.dt_Confirm, R.id.dt_Password, R.string.passwordisnotcorrect);

        btnRegister.setOnClickListener(view -> {

            if (awesomeValidation.validate()) {

            } else {
                Toast.makeText(getApplicationContext(), "Register Error ", Toast.LENGTH_SHORT).show();
            }

//            register();
            callRegister();
        });


    }

    private void clickLogin() {
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);
        Toast.makeText(RegisterActivity.this, "Login Form!", Toast.LENGTH_SHORT).show();
    }

    private void register() {

        final String name = this.dt_Name.getText().toString().trim();
        final String email = this.dt_Email.getText().toString().trim();
        final String phone = this.dt_Phone.getText().toString().trim();
        final String address = this.dt_Address.getText().toString().trim();
        final String password = this.dt_Password.getText().toString().trim();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_REGISTER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");

                            if (success.equals("1")) {
                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                startActivity(intent);
                                Toast.makeText(RegisterActivity.this, "Register Success!", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(RegisterActivity.this, "Register Failed! ", Toast.LENGTH_SHORT).show();

                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error instanceof NetworkError) {
                        } else if (error instanceof ServerError) {
                        } else if (error instanceof AuthFailureError) {
                        } else if (error instanceof ParseError) {
                        } else if (error instanceof TimeoutError) {
                            Toast.makeText(RegisterActivity.this, "Oops. Timeout error!", Toast.LENGTH_LONG).show();
                        }
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("name", name);
                params.put("email", email);
                params.put("phone", phone);
                params.put("address", address);

                params.put("password", password);
                return params;
            }
        };


        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }


    // * 6a657373696b61795048 * //
    // CALL AFTER FINISH REGISTRATION INPUT FORM
    // OKHTTPCLIENT METHOD CONNECTION

    public void callRegister() {

        final String name = this.dt_Name.getText().toString().trim();
        final String email = this.dt_Email.getText().toString().trim();
        final String phone = this.dt_Phone.getText().toString().trim();
        final String address = this.dt_Address.getText().toString().trim();
        final String password = this.dt_Password.getText().toString().trim();


        try {
            url = new URL(BASE_URL + "crud/register.php");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        OkHttpClient client = new OkHttpClient();
        final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

        // JSON REQUEST BODY
        JSONObject paramObject = new JSONObject();
        try {
            paramObject.put("user_fullname", name);
            paramObject.put("user_email", email);
            paramObject.put("user_password", password);
            paramObject.put("user_number", phone);
            paramObject.put("user_address", address);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody body = RequestBody.create(JSON, paramObject.toString());
        okhttp3.Request request = new okhttp3.Request.Builder()
                .url(url)
                .post(body)
                .build();

        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
//                Toast.makeText(RegisterActivity.this, "onFailure", Toast.LENGTH_SHORT).show();
                Log.e(TAG, "onFailure" + e);
                new Handler(Looper.getMainLooper()).postDelayed(() -> {
                    Snackbar snackbar = Snackbar.make(parentLayout, "onFailure " + e, Snackbar.LENGTH_LONG).setAction("", view -> {
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

                    //INTENT
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(intent);

                }, 2000);
            }

            @Override
            public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {

                String jsonData = response.body().string();

                try {
                    JSONObject mJsonObject = new JSONObject(jsonData);
                    Log.d(TAG, "jsonObject" + mJsonObject);

                    status_code = mJsonObject.getString("status_code");
                    status_message = mJsonObject.getString("status_message");
                    Log.d(TAG, "STATUS CODE + MESSAGE : " + status_code + status_message);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (response.isSuccessful()) {
                    Log.d(TAG, "RESPONSE SUCCESS");
                    new Handler(Looper.getMainLooper()).postDelayed(() -> {
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

                        //INTENT
                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                        startActivity(intent);

                    }, 2000);


                } else {
                    switch (response.code()) {
                        case 406: //
                            new Handler(Looper.getMainLooper()).postDelayed(() -> {
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
                            }, 2000);
                            break;
                        case 404:
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
                            new Handler(Looper.getMainLooper()).postDelayed(() -> {
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
                            }, 2000);
                            break;
                        default:
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
    }
}
