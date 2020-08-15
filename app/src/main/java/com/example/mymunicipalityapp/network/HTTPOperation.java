package com.example.mymunicipalityapp.network;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface HTTPOperation {
    //The endpoints are defined inside of an interface using special retrofit annotations to encode details about the parameters and request method.


    @GET("typesOfcomplaints?token=h2jhXbekMEyaIa4lUpLUg1kgZTiIxw1i2nTHoaloPAkezPHVHtFPRq2Q14Rt")
    Call<ResponseBody> getTypesOfComplaints();

    @GET("municipalities?token=h2jhXbekMEyaIa4lUpLUg1kgZTiIxw1i2nTHoaloPAkezPHVHtFPRq2Q14Rt")
    Call<ResponseBody> getMunicipalities();

    @GET("typesOfSuggestions?token=h2jhXbekMEyaIa4lUpLUg1kgZTiIxw1i2nTHoaloPAkezPHVHtFPRq2Q14Rt")
    Call<ResponseBody> getTypesOfSuggestions();

    @Headers("Accept:application/json")
    @FormUrlEncoded
    @POST("complaints/search?token=h2jhXbekMEyaIa4lUpLUg1kgZTiIxw1i2nTHoaloPAkezPHVHtFPRq2Q14Rt")
    Call<ResponseBody> getComplaintsState(@Field("national_id") String id, @Field("phone") String phone);

    @Headers("Accept:application/json")
    @FormUrlEncoded
    @POST("suggestions/search?token=h2jhXbekMEyaIa4lUpLUg1kgZTiIxw1i2nTHoaloPAkezPHVHtFPRq2Q14Rt")
    Call<ResponseBody> getSuggestionsState(@Field("national_id") String national_id,@Field("phone") String phone);



    //Add Complaint API
    @Headers("Accept:application/json")
    @Multipart
    @POST("complaints/add?token=h2jhXbekMEyaIa4lUpLUg1kgZTiIxw1i2nTHoaloPAkezPHVHtFPRq2Q14Rt")
    Call<ResponseBody> AddCompalint(@Part("name") RequestBody name,@Part("phone") RequestBody phone ,@Part("national_id") RequestBody national_id   , @Part("email") RequestBody email   ,  @Part("type") RequestBody type_compalint_ID, @Part("municipal") RequestBody type_municiplty_ID , @Part("details") RequestBody details ,  @Part("longitude") RequestBody longitude  ,  @Part("latitude") RequestBody latitude );


    @Headers("Accept:application/json")
    @Multipart
    @POST("complaints/add?token=h2jhXbekMEyaIa4lUpLUg1kgZTiIxw1i2nTHoaloPAkezPHVHtFPRq2Q14Rt")
    Call<ResponseBody> AddCompalint(@Part("name") RequestBody name,@Part("phone") RequestBody phone ,@Part("national_id") RequestBody national_id   , @Part("email") RequestBody email   ,  @Part("type") RequestBody type_compalint_ID, @Part("municipal") RequestBody type_municiplty_ID , @Part("details") RequestBody details ,  @Part("longitude") RequestBody longitude  ,  @Part("latitude") RequestBody latitude , @Part MultipartBody.Part image,  @Part("img") RequestBody requestBody);

    @Headers("Accept:application/json")
    @Multipart
    @POST("complaints/add?token=h2jhXbekMEyaIa4lUpLUg1kgZTiIxw1i2nTHoaloPAkezPHVHtFPRq2Q14Rt")
    Call<ResponseBody> AddCompalint(@Part("name") RequestBody name,@Part("phone") RequestBody phone ,@Part("national_id") RequestBody national_id   , @Part("email") RequestBody email   ,  @Part("type") RequestBody type_compalint_ID, @Part("municipal") RequestBody type_municiplty_ID ,  @Part("longitude") RequestBody longitude  ,  @Part("latitude") RequestBody latitude, @Part MultipartBody.Part file, @Part("file") RequestBody requestBody , @Part("details") RequestBody details );

    @Headers("Accept:application/json")
    @Multipart
    @POST("complaints/add?token=h2jhXbekMEyaIa4lUpLUg1kgZTiIxw1i2nTHoaloPAkezPHVHtFPRq2Q14Rt")
    Call<ResponseBody> AddCompalint(@Part("name") RequestBody name,@Part("phone") RequestBody phone ,@Part("national_id") RequestBody national_id   , @Part("email") RequestBody email   ,  @Part("type") RequestBody type_compalint_ID, @Part("municipal") RequestBody type_municiplty_ID , @Part("details") RequestBody details ,  @Part("longitude") RequestBody longitude  ,  @Part("latitude") RequestBody latitude , @Part MultipartBody.Part image, @Part("img") RequestBody imgrequestBody , @Part MultipartBody.Part file, @Part("file") RequestBody filerequestBody);





    //Add Suggestion API
    @Headers("Accept:application/json")
    @Multipart
    @POST("suggestions/add?token=h2jhXbekMEyaIa4lUpLUg1kgZTiIxw1i2nTHoaloPAkezPHVHtFPRq2Q14Rt")
    Call<ResponseBody> AddSuggestion(@Part("name") RequestBody name,@Part("phone") RequestBody phone ,  @Part("national_id") RequestBody national_id ,  @Part("email") RequestBody email , @Part("type") RequestBody type_suggestion_ID, @Part("municipal") RequestBody type_municiplty_ID ,@Part("details") RequestBody details );


    @Headers("Accept:application/json")
    @Multipart
    @POST("suggestions/add?token=h2jhXbekMEyaIa4lUpLUg1kgZTiIxw1i2nTHoaloPAkezPHVHtFPRq2Q14Rt")
    Call<ResponseBody> AddSuggestion(@Part("name") RequestBody name,@Part("phone") RequestBody phone ,  @Part("national_id") RequestBody national_id ,  @Part("email") RequestBody email , @Part("type") RequestBody type_suggestion_ID, @Part("municipal") RequestBody type_municiplty_ID ,@Part("details") RequestBody details, @Part MultipartBody.Part file, @Part("img") RequestBody requestBody);

    @Headers("Accept:application/json")
    @Multipart
    @POST("suggestions/add?token=h2jhXbekMEyaIa4lUpLUg1kgZTiIxw1i2nTHoaloPAkezPHVHtFPRq2Q14Rt")
    Call<ResponseBody> AddSuggestion(@Part("name") RequestBody name,@Part("phone") RequestBody phone ,  @Part("national_id") RequestBody national_id ,  @Part("email") RequestBody email , @Part("type") RequestBody type_suggestion_ID, @Part("municipal") RequestBody type_municiplty_ID , @Part MultipartBody.Part file, @Part("file") RequestBody requestBody ,@Part("details") RequestBody details);

    @Headers("Accept:application/json")
    @Multipart
    @POST("suggestions/add?token=h2jhXbekMEyaIa4lUpLUg1kgZTiIxw1i2nTHoaloPAkezPHVHtFPRq2Q14Rt")
    Call<ResponseBody> AddSuggestion(@Part("name") RequestBody name,@Part("phone") RequestBody phone ,  @Part("national_id") RequestBody national_id ,  @Part("email") RequestBody email , @Part("type") RequestBody type_suggestion_ID, @Part("municipal") RequestBody type_municiplty_ID ,@Part("details") RequestBody details , @Part MultipartBody.Part image, @Part("img") RequestBody imgrequestBody , @Part MultipartBody.Part file, @Part("file") RequestBody filerequestBody);

}
