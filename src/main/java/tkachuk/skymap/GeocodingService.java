package tkachuk.skymap;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GeocodingService
{
    @GET("geo/1.0/direct")
    Single<GeocodingResponse[]> getGeolocation(
            @Query("q") String location,
            @Query("appid") String apiKey,
            @Query("limit") int limit
    );
}
