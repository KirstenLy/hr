package hr.meteor.ru.meteorjob.ui.retrofit.services;

import hr.meteor.ru.meteorjob.ui.beans.DeveloperData;
import hr.meteor.ru.meteorjob.ui.beans.ManagerData;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface MeteorService {
    @POST("vacancy/manager/")
    Call<ManagerData> postManagerData(@Body ManagerData managerData);

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