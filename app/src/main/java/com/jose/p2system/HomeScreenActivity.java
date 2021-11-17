package com.jose.p2system;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;
import com.jose.p2system.fields.UserProfileField;
import com.jose.p2system.fields.UserProfileInfoField;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeScreenActivity extends AppCompatActivity {

    private static final String TAG = HomeScreenActivity.class.getSimpleName();
    private TextView dt_Name, dt_Email, dt_Phone, dt_Address;
    private Button btn_Continue, btn_photo_upload;
    SessionManager sessionManager;
    String getId;
    private static String URL_READ = "http://192.168.0.14/p2system/read_detail.php";
    private static String URL_EDIT = "http://192.168.0.14/p2system/edit_detail.php";
    private static String URL_UPLOAD = "http://192.168.0.14/p2system/upload.php";
    private Menu action;
    private Bitmap bitmap;
    CircleImageView profile_image;


    //
    public static String TAG_STATIC = "ACT_HOMESCREEN";
    String data_token;
    private static String BASE_URL = "http://192.168.42.108/app_connection/app-connection-p2p/development/"; //android phone ko
    //    private static String BASE_URL = "http://192.168.100.105/app_connection/app-connection-p2p/development/"; //android emulator ko
RelativeLayout parentLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);


        //
        SharedPreferences sharedPreference = this.getApplicationContext().getSharedPreferences("sharedPref", Context.MODE_PRIVATE);
        data_token = sharedPreference.getString("data_token", "");
        Log.d(TAG_STATIC, data_token);

        getProfileData();


        //HIDE PANSAMANTALA
//        sessionManager = new SessionManager(this);
//        sessionManager.checkLogin();

        parentLayout = findViewById(R.id.parentLayout);

        btn_photo_upload = findViewById(R.id.btn_photo);

        dt_Name = findViewById(R.id.dt_Name);
        dt_Email = findViewById(R.id.dt_Email);
        dt_Phone = findViewById(R.id.dt_Phone);
        dt_Address = findViewById(R.id.dt_Address);


        btn_Continue = findViewById(R.id.btn_Continue);

        profile_image = findViewById(R.id.profile_image);



        //HIDE PANSAMANTALA
//        HashMap<String, String> user = sessionManager.getUserDetail();
//        getId = user.get(sessionManager.ID);

        btn_Continue.setOnClickListener(view -> {

            //HIDE PANSAMANTALA
//            SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
//            SharedPreferences.Editor editor = preferences.edit();
//            editor.putString("remember", "false");
//            editor.apply();
//
//            sessionManager.logout();
//            Intent intent = new Intent(HomeScreenActivity.this, LoginActivity.class);
//            startActivity(intent);
//            finish();

        });
        btn_photo_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseFile();
            }
        });

    }

    private void getUserDetail() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_READ,
                new Response.Listener<String>() {
                    @SuppressLint("ResourceAsColor")
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        Log.i(TAG, response.toString());
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("read");

                            if (success.equals("1")) {
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject object = jsonArray.getJSONObject(i);

                                    String strName = object.getString("name").trim();
                                    String strEmail = object.getString("email").trim();
                                    String strPhone = object.getString("phone").trim();
                                    String strAddress = object.getString("address").trim();

                                    dt_Name.setText(strName);
                                    dt_Email.setText(strEmail);
                                    dt_Phone.setText(strPhone);
                                    dt_Address.setText(strAddress);

                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressDialog.dismiss();
                            Toast.makeText(HomeScreenActivity.this, "Error Read Detail! ", Toast.LENGTH_SHORT).show();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(HomeScreenActivity.this, "Error Read Detail! ", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id", getId);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    // * 6a657373696b61795048 * //
    // RETROFIT METHOD CONNECTION

    public void getProfileData() {
        Log.d(TAG, "running --");

        //WE WILL GET THE PROFILE DATA FROM SERVER USING RETROFIT
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APInterface apInterface = retrofit.create(APInterface.class);

        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", " " + data_token);
        Call<UserProfileField> call = apInterface.doGetProfileData(headers);
        call.enqueue(new Callback<UserProfileField>() {

            @Override
            public void onResponse(Call<UserProfileField> call, retrofit2.Response<UserProfileField> response) {

                String status_code = response.body().getStatus_code();
                String status_message = response.body().getStatus_message();
                Log.d(TAG_STATIC, "STATUS CODE & MESSAGE : " + status_code + status_message);

                UserProfileInfoField metadataField = response.body().getUserProfileInfoField();
                String fullname = metadataField.getUser_fullname();
                String email = metadataField.getUser_email();
                String mobileno = metadataField.getUser_mobileno();
                String address = metadataField.getUser_address();

                if (response.isSuccessful()) {
                    Log.d(TAG, "RESPONSE SUCCESS");

                    dt_Name.setText(fullname);
                    dt_Name.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.black));

                    dt_Email.setText(email);
                    dt_Email.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.black));

                    dt_Phone.setText(mobileno);
                    dt_Phone.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.black));

                    dt_Address.setText(address);
                    dt_Address.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.black));


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

            @Override
            public void onFailure(Call<UserProfileField> call, Throwable t) {

            }
        });


    }


    @Override
    protected void onResume() {
        super.onResume();
        getUserDetail();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_action, menu);

        action = menu;
        action.findItem(R.id.menu_save).setVisible(false);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_edit:

                dt_Name.setFocusableInTouchMode(true);
                dt_Email.setFocusableInTouchMode(true);

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(dt_Name, InputMethodManager.SHOW_IMPLICIT);

                action.findItem(R.id.menu_edit).setVisible(false);
                action.findItem(R.id.menu_save).setVisible(true);

                return true;

            case R.id.menu_save:

                SaveEditDetail();

                action.findItem(R.id.menu_edit).setVisible(true);
                action.findItem(R.id.menu_save).setVisible(false);

                dt_Name.setFocusableInTouchMode(false);
                dt_Email.setFocusableInTouchMode(false);

                dt_Phone.setFocusableInTouchMode(false);
                dt_Address.setFocusableInTouchMode(false);


                dt_Name.setFocusable(false);
                dt_Email.setFocusable(false);

                dt_Phone.setFocusable(false);
                dt_Address.setFocusable(false);

                return true;

            default:

                return super.onOptionsItemSelected(item);

        }
    }

    //save
    private void SaveEditDetail() {

        final String name = this.dt_Name.getText().toString().trim();
        final String email = this.dt_Email.getText().toString().trim();
        final String phone = this.dt_Phone.getText().toString().trim();
        final String address = this.dt_Address.getText().toString().trim();


        final String id = getId;

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Saving...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_EDIT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");

                            if (success.equals("1")) {
                                Toast.makeText(HomeScreenActivity.this, "Success!", Toast.LENGTH_SHORT).show();
                                sessionManager.createSession(name, email, phone, address, id);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressDialog.dismiss();
                            Toast.makeText(HomeScreenActivity.this, "Error ", Toast.LENGTH_SHORT).show();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(HomeScreenActivity.this, "Error ", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("name", name);
                params.put("email", email);
                params.put("phone", phone);
                params.put("address", address);

                params.put("id", id);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);


    }

    private void chooseFile() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            try {

                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                profile_image.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }

            UploadPicture(getId, getStringImage(bitmap));

        }
    }

    private void UploadPicture(final String id, final String photo) {

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Uploading...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_UPLOAD,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        Log.i(TAG, response.toString());
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");

                            if (success.equals("1")) {
                                Toast.makeText(HomeScreenActivity.this, "Success!", Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressDialog.dismiss();
                            Toast.makeText(HomeScreenActivity.this, "Try Again!", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(HomeScreenActivity.this, "Try Again!", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id", id);
                params.put("photo", photo);

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);


    }

    public String getStringImage(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);

        byte[] imageByteArray = byteArrayOutputStream.toByteArray();
        String encodedImage = Base64.encodeToString(imageByteArray, Base64.DEFAULT);

        return encodedImage;
    }


}
