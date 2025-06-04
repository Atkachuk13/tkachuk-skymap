package tkachuk.skymap;

import com.andrewoid.apikeys.ApiKey;
import org.junit.jupiter.api.Test;

import java.util.Base64;

import static org.junit.jupiter.api.Assertions.*;

class AstronomyServiceTest
{
    @Test
    void getPosition()
    {
        // given
        AstronomyService service = new AstronomyServiceFactory().getService();
        ApiKey appId = new ApiKey("applicationId");
        ApiKey appSecret = new ApiKey("applicationSecret");
        String concat = appId.get() + ":" + appSecret.get();
        String keys = "Basic " + Base64.getEncoder().encodeToString(
                concat.getBytes());

        // when
        AstronomyResponse response = service.getPosition(
                keys, -74.0059, 40.7142, 0,
                "2025-06-04", "2025-06-04", "17:30:00", "sun"
        ).blockingGet();

        // then
        assertNotNull(response);
        assertNotNull(response.data);
        assertNotNull(response.data.table);
        assertNotNull(response.data.table.rows);
        assertTrue(response.data.table.rows.length > 0);
        assertNotNull(response.data.table.rows[0].cells);
        assertTrue(response.data.table.rows[0].cells.length > 0);

        AstronomyResponse.Cell cell = response.data.table.rows[0].cells[0];
        assertNotNull(cell.position);
        assertNotNull(cell.position.horizontal);

        double altitude = cell.position.horizontal.altitude.degrees;
        double azimuth = cell.position.horizontal.azimuth.degrees;

        assertTrue(altitude >= -90 && altitude <= 90);
        assertTrue(azimuth >= 0 && azimuth <= 360);

    }
}