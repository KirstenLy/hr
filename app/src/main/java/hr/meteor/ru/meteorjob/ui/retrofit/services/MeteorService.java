package hr.meteor.ru.meteorjob.ui.retrofit.services;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface MeteorService {
    @Multipart
    @POST("manager")
    Call<ResultJson> postManagerData(@Part("Data") RequestBody json,
                                     @Part MultipartBody.Part file);

    @Multipart
    @POST("developer")
    Call<ResultJson> postDeveloperData(@Part("Data") RequestBody json,
                                       @Part MultipartBody.Part fileOne,
                                       @Part MultipartBody.Part fileTwo);


//    @Multipart
//    @POST("developer")
//    Call<ResultJson> postDeveloperData(@Part("Data") RequestBody json,
//                                       @Part MultipartBody.Part fileOne,
//                                       @Part MultipartBody.Part fileTwo);

//    @Part List<MultipartBody.Part> files

}