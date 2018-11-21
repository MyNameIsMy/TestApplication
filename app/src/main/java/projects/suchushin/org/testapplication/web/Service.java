package projects.suchushin.org.testapplication.web;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Service {
    @GET("/permit")
    Call<WebPermission> permit();
}
