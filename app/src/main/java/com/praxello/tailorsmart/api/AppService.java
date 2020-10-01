package com.praxello.tailorsmart.api;

import com.praxello.tailorsmart.BuildConfig;
import com.praxello.tailorsmart.model.AllData;
import com.praxello.tailorsmart.model.AppointmentData;
import com.praxello.tailorsmart.model.MeasurementData;
import com.praxello.tailorsmart.model.OrderData;
import com.praxello.tailorsmart.model.UserData;
import com.praxello.tailorsmart.model.UserResponse;
import com.praxello.tailorsmart.model.VacancyData;

import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;
import retrofit2.http.QueryMap;

public interface AppService {
    @FormUrlEncoded
    @POST(BuildConfig.API_PATH + "regpushnotification.php")
    Call<UserData> registerpushnotification(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST(BuildConfig.API_PATH + "customerlogin.php")
    Call<UserData> login(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST(BuildConfig.API_PATH + "customersignup.php")
    Call<UserData> customersignup(@FieldMap Map<String, String> params);

    @GET(BuildConfig.API_PATH + "forgetpassword.php")
    Call<UserData> forgetpassword(@QueryMap Map<String, String> params);

    @FormUrlEncoded
    @POST(BuildConfig.API_PATH + "updatecustomer.php")
    Call<UserData> updatecustomer(@FieldMap Map<String, String> params);

    @GET(BuildConfig.API_PATH + "getproducts.php")
    Call<AllData> getproducts();

    @FormUrlEncoded
    @POST(BuildConfig.API_PATH + "customerorders.php")
    Call<OrderData> customerorders(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST(BuildConfig.API_PATH + "getappointment.php")
    Call<AppointmentData> getappointment(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST(BuildConfig.API_PATH + "takeappointment.php")
    Call<UserResponse> takeappointment(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST(BuildConfig.API_PATH + "updateappointment.php")
    Call<UserResponse> updateappointment(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST(BuildConfig.API_PATH +"createorder.php")
    Call<UserData> createorder(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST(BuildConfig.API_PATH + "savefeedback.php")
    Call<UserData> savefeedback(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST(BuildConfig.API_PATH + "contactus.php")
    Call<UserResponse> contactus(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST(BuildConfig.API_FOLDER + "reorderproduct.php")
    Call<UserResponse> reorderproduct(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST(BuildConfig.API_FOLDER + "mymeasurements.php")
    Call<MeasurementData> mymeasurements(@FieldMap Map<String, String> params);

    @GET(BuildConfig.API_FOLDER + "getvacancy.php")
    Call<VacancyData> getvacancy();

    @FormUrlEncoded
    @POST(BuildConfig.API_FOLDER + "requestforalteration.php")
    Call<UserResponse> requestforalteration(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST(BuildConfig.API_FOLDER + "deleteorderitem.php")
    Call<UserResponse> deleteorderitem(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST(BuildConfig.API_PATH + "refermaster.php")
    Call<UserResponse> refermaster(@FieldMap Map<String, String> params);

    @Multipart
    @POST(BuildConfig.API_FOLDER + "uploadimage.php")
    Call<ResponseBody> uploadimage(@PartMap Map<String, RequestBody> params);
}
