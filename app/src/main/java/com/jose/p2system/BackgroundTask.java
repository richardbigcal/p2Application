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

public class BackgroundTask extends AsyncTask<String,String,String> {

    Context context;
    BackgroundTask(Context ctx){
        this.context=ctx;
    }

    @Override
    protected String doInBackground(String... strings) {
        String type = strings[0];
        String firstname = strings[1];
        String lastname = strings[2];
        String email = strings[3];
        String phone = strings[4];
        String report = strings[5];
        String breed = strings[6];
        String gender = strings[7];
        String pet_description = strings[8];

        String regURL="http://192.168.0.14/p2system/UploadImage.php";
        if(type.equals("reg")){
            try{
                URL url = new URL(regURL);
                try{
                    HttpURLConnection httpURLConnection=(HttpURLConnection)url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream outputStream=httpURLConnection.getOutputStream();
                    OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream, "UTF-8");
                    BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
                    String insert_data = URLEncoder.encode("firstname","UTF-8")+"="+URLEncoder.encode(firstname,"UTF-8")+
                            "&"+URLEncoder.encode("lastname", "UTF-8")+"="+URLEncoder.encode(lastname, "UTF-8")+
                            "&"+URLEncoder.encode("email", "UTF-8")+"="+URLEncoder.encode(email, "UTF-8")+
                            "&"+URLEncoder.encode("phone", "UTF-8")+"="+URLEncoder.encode(phone, "UTF-8")+
                            "&"+URLEncoder.encode("report", "UTF-8")+"="+URLEncoder.encode(report, "UTF-8")+
                            "&"+URLEncoder.encode("breed","UTF-8")+"="+URLEncoder.encode(breed, "UTF-8")+
                            "&"+URLEncoder.encode("gender","UTF-8")+"="+URLEncoder.encode(gender, "UTF-8")+
                            "&"+URLEncoder.encode("pet_description", "UTF-8")+"="+URLEncoder.encode(pet_description,"UTF-8");


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
