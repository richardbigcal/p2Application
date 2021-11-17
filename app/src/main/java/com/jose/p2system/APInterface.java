package com.jose.p2system;

import com.jose.p2system.fields.UserProfileField;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;

public interface APInterface {

    @FormUrlEncoded
    @POST("upload_document.php")
    Call<ResponsePOJO> uploadDocument(
            @Field("PDF") String encodedPDF
    );


    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @POST("crud/register.php")
    Call<UserProfileField> doRegister(@Body String body);

    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @GET("crud/getProfileData.php")
    Call<UserProfileField> doGetProfileData(@HeaderMap Map<String, String> headers);


}