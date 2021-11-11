package com.jose.p2system;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.telecom.Call;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.net.URL;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    AwesomeValidation awesomeValidation;
    private EditText dt_Name, dt_Email,dt_Phone, dt_Address, dt_Password,dt_Confirm;
    private TextView tv_login;
    private Button btnRegister;
    private ProgressBar loading;
    private static String URL_REGISTER = "http://192.168.0.14/p2system/register.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().hide();

        loading = findViewById(R.id.loading);
        dt_Name = findViewById(R.id.dt_Name);
        dt_Email = findViewById(R.id.dt_Email);
        dt_Phone  = findViewById(R.id.dt_Phone);
        dt_Address = findViewById(R.id.dt_Address);
        dt_Password = findViewById(R.id.dt_Password);
        dt_Confirm = findViewById(R.id.dt_Confirm);
        btnRegister = findViewById(R.id.btnRegister);

        tv_login = findViewById(R.id.tv_login);
        tv_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickLogin();
            }
        });

        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        awesomeValidation.addValidation(this,R.id.dt_Password,".{6,}",R.string.wrongpassword);
        awesomeValidation.addValidation(this,R.id.dt_Confirm,R.id.dt_Password ,R.string.passwordisnotcorrect);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(awesomeValidation.validate()){

                } else {
                    Toast.makeText(getApplicationContext(), "Register Error ", Toast.LENGTH_SHORT).show();
                }

                register();
            }
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
                        try{
                            JSONObject jsonObject = new JSONObject  (response);
                            String success = jsonObject.getString("success");

                            if (success.equals("1")) {
                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                startActivity(intent);
                                Toast.makeText(RegisterActivity.this, "Register Success!", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(RegisterActivity.this, "Register Failed! " , Toast.LENGTH_SHORT).show();

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
                            Toast.makeText(RegisterActivity.this, "Oops. Timeout error!" , Toast.LENGTH_LONG).show();
                        }
            }
        })


        {
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


}
