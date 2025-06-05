package tkachuk.skymap;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface AstronomyService
{
    @GET("api/v2/bodies/positions")
    Single<AstronomyResponse> getPosition(
            @Header("Authorization") String authHeader,
            @Query("longitude") double longitude,
            @Query("latitude") double latitude,
            @Query("elevation") double elevation,
            @Query("from_date") String fromDate,
            @Query("to_date") String toDate,
            @Query("time") String time,
            @Query("bodies") String bodies
    );
}
