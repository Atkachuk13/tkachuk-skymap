package tkachuk.skymap;

import com.andrewoid.apikeys.ApiKey;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GeocodingServiceTest
{
    @Test
    void getGeolocation()
    {
        // given
        GeocodingService service = new GeocodingServiceFactory().getService();
        ApiKey apiKey = new ApiKey("weathermapKey");
        String key = apiKey.get();

        // when
        GeocodingResponse[] response = service.getGeolocation("Manhattan",
                key, 5).blockingGet();

        // then
        assertNotNull(response);
        assertTrue(response.length > 0);
        assertEquals("Manhattan", response[0].name);
    }
}
