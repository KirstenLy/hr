package hr.meteor.ru.meteorjob.ui.retrofit.services;

import java.util.Map;

import hr.meteor.ru.meteorjob.ui.beans.DeveloperData;
import hr.meteor.ru.meteorjob.ui.beans.ManagerData;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;

public interface MeteorService {
    @Multipart
    @POST("manager")
    Call<ResultJson> postManagerData(@Req String params,
                                     @Part MultipartBody.Part file);

    @POST("developer")
    Call<DeveloperData> postDeveloperData(@Body DeveloperData developerData);
}