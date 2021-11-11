package com.jose.p2system;

import androidx.appcompat.app.AppCompatActivity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;


public class AdoptionActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private DatePickerDialog datePickerDialog;
    private Button dateButton, btnNext;
    EditText et_name, et_age, et_address, et_phone, et_email, et_occupation, et_alter,
            et_relationship, et_mobile,
            Q4answer,Q5answer,Q12answer,Q13answer,Q14answer,Q15answer,Q16answer,Q17answer;

    RadioButton rdb1,rdb2,rdb3,rdb6,rdb7,rdb8,rdb9,rdb10,rdb11;
    RadioGroup rdbGQ1,rdbGQ2,rdbGQ3,rdbGQ6,rdbGQ7,rdbGQ8,rdbGQ9,rdbGQ10,rdbGQ11;

    SessionManager sessionManager;
    String getId;
    private static String URL_READ = "http://192.168.0.14/p2system/read_detail.php";
    private static final String TAG = AdoptionActivity.class.getSimpleName();
    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adoption);
        getSupportActionBar().hide();

        sessionManager = new SessionManager(this);
        sessionManager.checkLogin();

        HashMap<String, String> user = sessionManager.getUserDetail();
        getId = user.get(sessionManager.ID);

        Spinner spinner = findViewById(R.id.spinnerStatus);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.AdoptionGenders, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        dateButton = findViewById(R.id.dateButton);
        dateButton.setText(getTodayDate());
        btnNext = findViewById(R.id.btnNext);



        //Assign Variables
        et_name = findViewById(R.id.dt_Name);
        et_age = findViewById(R.id.et_age);
        et_address = findViewById(R.id.dt_Address);
        et_phone = findViewById(R.id.dt_Phone);
        et_email = findViewById(R.id.dt_Email);
        et_occupation = findViewById(R.id.et_occupation);
        et_alter = findViewById(R.id.et_alter);
        et_relationship = findViewById(R.id.et_relationship);
        et_mobile = findViewById(R.id.et_mobile);

        //RadioGroup
        rdbGQ1 = findViewById(R.id.rdbGQ1);
        rdbGQ2 = findViewById(R.id.rdbGQ2);
        rdbGQ3 = findViewById(R.id.rdbGQ3);
        rdbGQ6 = findViewById(R.id.rdbGQ6);
        rdbGQ7 = findViewById(R.id.rdbGQ7);
        rdbGQ8 = findViewById(R.id.rdbGQ8);
        rdbGQ9 = findViewById(R.id.rdbGQ9);
        rdbGQ10 = findViewById(R.id.rdbGQ10);
        rdbGQ11 = findViewById(R.id.rdbGQ11);

        Q4answer = findViewById(R.id.Q4answer);
        Q5answer = findViewById(R.id.Q5answer);
        Q12answer = findViewById(R.id.Q12answer);
        Q13answer = findViewById(R.id.Q13answer);
        Q14answer = findViewById(R.id.Q14answer);
        Q15answer = findViewById(R.id.Q15answer);
        Q16answer = findViewById(R.id.Q16answer);
        Q17answer = findViewById(R.id.Q17answer);


        //Create the Dialog here
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.custom_dialog);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.custom_dialog_background));
        }
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false); //Optional
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation; //Setting the animations to dialog

        Button yes = dialog.findViewById(R.id.btn_Yes);
        Button not_yet = dialog.findViewById(R.id.btn_Not_yet);


        // Picker
       initdatePicker();

       yes.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               int selectId1 = rdbGQ1.getCheckedRadioButtonId();
               rdb1 = findViewById(selectId1);
               int selectId2 = rdbGQ2.getCheckedRadioButtonId();
               rdb2 = findViewById(selectId2);
               int selectId3 = rdbGQ3.getCheckedRadioButtonId();
               rdb3 = findViewById(selectId3);
               int selectId6 = rdbGQ6.getCheckedRadioButtonId();
               rdb6 = findViewById(selectId6);
               int selectId7 = rdbGQ7.getCheckedRadioButtonId();
               rdb7= findViewById(selectId7);
               int selectId8 = rdbGQ8.getCheckedRadioButtonId();
               rdb8 = findViewById(selectId8);
               int selectId9 = rdbGQ9.getCheckedRadioButtonId();
               rdb9 = findViewById(selectId9);
               int selectId10 = rdbGQ10.getCheckedRadioButtonId();
               rdb10 = findViewById(selectId10);
               int selectId11 = rdbGQ11.getCheckedRadioButtonId();
               rdb11 = findViewById(selectId11);




               String date = dateButton.getText().toString();
               String name = et_name.getText().toString();
               String age = et_age.getText().toString();
               String address = et_address.getText().toString();
               String phone = et_phone.getText().toString();
               String email = et_email.getText().toString();
               String occupation= et_occupation.getText().toString();
               String status = spinner.getSelectedItem().toString();
               String alternate_contact = et_alter.getText().toString();
               String relationship = et_relationship.getText().toString();
               String mobile = et_mobile.getText().toString();


               String Q1 = rdb1.getText().toString();
               String Q2 = rdb2.getText().toString();
               String Q3 = rdb3.getText().toString();
               String Q4 = Q4answer.getText().toString();
               String Q5 = Q5answer.getText().toString();
               String Q6 = rdb6.getText().toString();
               String Q7 = rdb7.getText().toString();
               String Q8 = rdb8.getText().toString();
               String Q9 = rdb9.getText().toString();
               String Q10 = rdb10.getText().toString();
               String Q11 = rdb11.getText().toString();
               String Q12 = Q12answer.getText().toString();
               String Q13 = Q13answer.getText().toString();
               String Q14 = Q14answer.getText().toString();
               String Q15 = Q15answer.getText().toString();
               String Q16 = Q16answer.getText().toString();
               String Q17 = Q17answer.getText().toString();


               String type = "reg";
               BackgroudTask backgroudTask = new BackgroudTask(getApplicationContext());
               backgroudTask.execute(type,date, name, age, address, phone, email, occupation,status, alternate_contact,relationship,
                       mobile,Q1,Q2,Q3,Q4,Q5,Q6,Q7,Q8,Q9,Q10,Q11,Q12,Q13,Q14,Q15,Q16,Q17);

               Intent intent = new Intent(AdoptionActivity.this, ScheduleActivity.class);
               startActivity(intent);
               Toast.makeText(AdoptionActivity.this, "Done", Toast.LENGTH_SHORT).show();
               dialog.dismiss();
           }
       });

        not_yet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AdoptionActivity.this, "Canceled", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });


       btnNext.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               dialog.show();


           }
       });



    }
    private void getUserDetail() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_READ,
                new Response.Listener<String>() {
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

                                    et_name.setText(strName);
                                    et_email.setText(strEmail);
                                    et_phone.setText(strPhone);
                                    et_address.setText(strAddress);

                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressDialog.dismiss();
                            Toast.makeText(AdoptionActivity.this, "Error Read Detail! ", Toast.LENGTH_SHORT).show();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(AdoptionActivity.this, "Error Read Detail! ", Toast.LENGTH_SHORT).show();
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
    @Override
    protected void onResume(){
        super.onResume();
        getUserDetail();
    }

    private String getTodayDate() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
             int month = cal.get(Calendar.MONTH);
            month = month + 1;
            int day = cal.get(Calendar.DAY_OF_MONTH);

              return  makeDateString(day, month, year);

          }

          private void initdatePicker() {

             DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
                @Override
               public void onDateSet(DatePicker view, int year, int month, int day) {
                     month = month + 1;
               String date = makeDateString(day, month, year);
                  dateButton.setText(date);


               }
          };
              Calendar cal = Calendar.getInstance();
              int year = cal.get(Calendar.YEAR);
              int month = cal.get(Calendar.MONTH);
               int day = cal.get(Calendar.DAY_OF_MONTH);

              int style = AlertDialog.THEME_HOLO_LIGHT;

             datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
              datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
          }

           private String makeDateString(int day, int month, int year) {

              return getMonthFormat(month) + " " + day + " " + year;


           }

          private String getMonthFormat(int month) {

              if(month == 1)
                   return "JAN";
              if(month == 2)
                  return "FEB";
              if(month == 3)
               return "MAR";
              if(month == 4)
                 return "APR";
               if(month == 5)
               return "MAY";
                if(month == 6)
               return "JUN";
           if(month == 7)
                 return "JUL";
             if(month == 8)
                 return "AUG";
             if(month == 9)
                return "SEP";
            if(month == 10)
                return "OCT";
            if(month == 11)
                return "NOV";
             if(month == 12)
                  return "DEC";
          ///    Default
             return "JAN";


          }

           public void openDatePicker(View view){

              datePickerDialog.show();

          }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}