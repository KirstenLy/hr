package hr.meteor.ru.meteorjob.ui.retrofit.services;

import java.util.List;

import hr.meteor.ru.meteorjob.ui.beans.TestTasksRetrofit;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface MeteorService {
    @Multipart
    @POST("manager")
    Call<ResultJson> postManagerData(@Part("Data") RequestBody json,
                                     @Part List<MultipartBody.Part> files);

    @Multipart
    @POST("developer")
    Call<ResultJson> postDeveloperData(@Part("Data") RequestBody json,
                                       @Part List<MultipartBody.Part> resultList);

    @POST("web")
    Call<ResultJson> postWebTestTask(@Query("Email") String email);

    @POST("android")
    Call<ResultJson> postAndroidTestTask(@Query("Email") String email);

    @GET("misc")
    Call<ResultJson<TestTasksRetrofit>> getTestTasks();


//    @Multipart
//    @POST("developer")
//    Call<ResultJson> postDeveloperData(@Part("Data") RequestBody json,
//                                       @Part MultipartBody.Part fileOne,
//                                       @Part MultipartBody.Part fileTwo);

//    @Part List<MultipartBody.Part> files

}