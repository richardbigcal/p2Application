package com.jose.p2system;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class BackgroudTask extends AsyncTask<String,String,String> {

    Context context;
    BackgroudTask(Context ctx){
        this.context=ctx;
    }

    @Override
    protected String doInBackground(String... strings) {

        String type = strings[0];
        String date = strings[1];
        String name = strings[2];
        String age = strings[3];
        String address = strings[4];
        String phone = strings[5];
        String email = strings[6];
        String occupation = strings[7];
        String status = strings[8];
        String alternate_contact = strings[9];
        String relationship = strings[10];
        String mobile = strings[11];


///     EditText Answer
        String Q1 = strings[12];
        String Q2 = strings[13];
        String Q3 = strings[14];
        String Q4 = strings[15];
        String Q5 = strings[16];
        String Q6 = strings[17];
        String Q7 = strings[18];
        String Q8 = strings[19];
        String Q9 = strings[20];
        String Q10 = strings[21];
        String Q11 = strings[22];
        String Q12 = strings[23];
        String Q13 = strings[24];
        String Q14 = strings[25];
        String Q15 = strings[26];
        String Q16 = strings[27];
        String Q17 = strings[28];
        String regURL="http://192.168.0.14/p2system/personal_info.php";
        if(type.equals("reg")){
            try{
                URL url = new URL(regURL);
                try{
                    HttpURLConnection httpURLConnection=(HttpURLConnection)url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);                   OutputStream outputStream=httpURLConnection.getOutputStream();
                    OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream, "UTF-8");
                    BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
                    String insert_data = URLEncoder.encode("date","UTF-8")+"="+URLEncoder.encode(date,"UTF-8")+
                            "&"+URLEncoder.encode("name", "UTF-8")+"="+URLEncoder.encode(name, "UTF-8")+
                            "&"+URLEncoder.encode("age", "UTF-8")+"="+URLEncoder.encode(age, "UTF-8")+
                            "&"+URLEncoder.encode("address", "UTF-8")+"="+URLEncoder.encode(address, "UTF-8")+
                            "&"+URLEncoder.encode("phone", "UTF-8")+"="+URLEncoder.encode(phone, "UTF-8")+
                            "&"+URLEncoder.encode("email","UTF-8")+"="+URLEncoder.encode(email, "UTF-8")+
                            "&"+URLEncoder.encode("occupation", "UTF-8")+"="+URLEncoder.encode(occupation,"UTF-8")+
                            "&"+URLEncoder.encode("status", "UTF-8")+"="+URLEncoder.encode(status,"UTF-8")+
                            "&"+URLEncoder.encode("alternate_contact", "UTF-8")+"="+URLEncoder.encode(alternate_contact, "UTF-8")+
                            "&"+URLEncoder.encode("relationship", "UTF-8")+"="+URLEncoder.encode(relationship, "UTF-8")+
                            "&"+URLEncoder.encode("mobile", "UTF-8")+"="+URLEncoder.encode(mobile, "UTF-8")+
                            "&"+URLEncoder.encode("Q1", "UTF-8")+"="+URLEncoder.encode(Q1, "UTF-8")+
                            "&"+URLEncoder.encode("Q2", "UTF-8")+"="+URLEncoder.encode(Q2, "UTF-8")+
                            "&"+URLEncoder.encode("Q3", "UTF-8")+"="+URLEncoder.encode(Q3, "UTF-8")+
                            "&"+URLEncoder.encode("Q4", "UTF-8")+"="+URLEncoder.encode(Q4, "UTF-8")+
                            "&"+URLEncoder.encode("Q5", "UTF-8")+"="+URLEncoder.encode(Q5, "UTF-8")+
                            "&"+URLEncoder.encode("Q6", "UTF-8")+"="+URLEncoder.encode(Q6, "UTF-8")+
                            "&"+URLEncoder.encode("Q7", "UTF-8")+"="+URLEncoder.encode(Q7, "UTF-8")+
                            "&"+URLEncoder.encode("Q8", "UTF-8")+"="+URLEncoder.encode(Q8, "UTF-8")+
                            "&"+URLEncoder.encode("Q9", "UTF-8")+"="+URLEncoder.encode(Q9, "UTF-8")+
                            "&"+URLEncoder.encode("Q10", "UTF-8")+"="+URLEncoder.encode(Q10, "UTF-8")+
                            "&"+URLEncoder.encode("Q11", "UTF-8")+"="+URLEncoder.encode(Q11, "UTF-8")+
                            "&"+URLEncoder.encode("Q12", "UTF-8")+"="+URLEncoder.encode(Q12, "UTF-8")+
                            "&"+URLEncoder.encode("Q13", "UTF-8")+"="+URLEncoder.encode(Q13, "UTF-8")+
                            "&"+URLEncoder.encode("Q14", "UTF-8")+"="+URLEncoder.encode(Q14, "UTF-8")+
                            "&"+URLEncoder.encode("Q15", "UTF-8")+"="+URLEncoder.encode(Q15, "UTF-8")+
                            "&"+URLEncoder.encode("Q16", "UTF-8")+"="+URLEncoder.encode(Q16, "UTF-8")+
                            "&"+URLEncoder.encode("Q17", "UTF-8")+"="+URLEncoder.encode(Q17, "UTF-8");


                    bufferedWriter.write(insert_data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    InputStream inputStream=httpURLConnection.getInputStream();
                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "ISO-8859-1");
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                    String result = "";
                    String line = "";

                    StringBuilder stringBuilder = new StringBuilder();
                    while ((line=bufferedReader.readLine())!=null){
                        stringBuilder.append(line).append("\n");
                    }
                    result = stringBuilder.toString();
                    bufferedReader.close();
                    inputStream.close();

                    httpURLConnection.disconnect();
                    return result;

                } catch (IOException e) {
                    e.printStackTrace();
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }





        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String s) {

        Toast.makeText(context, s, Toast.LENGTH_LONG).show();
     //   super.onPostExecute(s);
    }
}
