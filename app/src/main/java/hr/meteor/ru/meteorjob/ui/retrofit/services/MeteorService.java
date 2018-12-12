package hr.meteor.ru.meteorjob.ui.retrofit.services;

import java.util.LinkedHashMap;

import hr.meteor.ru.meteorjob.ui.beans.DeveloperData;
import hr.meteor.ru.meteorjob.ui.beans.ManagerData;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface MeteorService {
    @Multipart
    @POST("vacancy/manager/")
    Call<ManagerData> postManagerData(
            @FieldMap LinkedHashMap<String, String> managerData,
            @Part MultipartBody.Part file);

    @POST("vacancy/developer/")
    Call<DeveloperData> postDeveloperData(@Body DeveloperData developerData);
}

//    @POST("vacancy/manager/")
//    Call<ManagerData> postManagerData(@Field("is_skilled") boolean isSkilled,
//                                      @Field("name") String name,
//                                      @Field("phone") String phone,
//                                      @Field("email") String email,
//                                      @Field("answer") String answer,
//                                      @Field("comment") String comment);