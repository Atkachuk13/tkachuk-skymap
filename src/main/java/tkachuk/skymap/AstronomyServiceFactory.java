package tkachuk.skymap;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class AstronomyServiceFactory
{
    public AstronomyService getService()
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.astronomyapi.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();

        return retrofit.create(AstronomyService.class);
    }
}
