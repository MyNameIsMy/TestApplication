package projects.suchushin.org.testapplication.web;

import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class ServiceHolder {
    private static Service service;

    public static Service getService() {
        if (service == null){
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://permission-web-service.herokuapp.com/")
                    .addConverterFactory(JacksonConverterFactory.create())
                    .build();
            service = retrofit.create(Service.class);
        }
        return service;
    }
}
