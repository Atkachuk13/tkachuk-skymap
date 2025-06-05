package tkachuk.skymap;

import com.andrewoid.apikeys.ApiKey;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

import javax.swing.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class AstronomyController
{
    private final AstronomyService astronomyService;
    private final GeocodingService geocodingService;
    private final SkyPanel panel;
    private final String keys;
    private double latitude = 40.7142;
    private double longitude = -74.0059;

    public AstronomyController(AstronomyService astronomyService, GeocodingService geocodingService, SkyPanel panel)
    {
        this.astronomyService = astronomyService;
        this.geocodingService = geocodingService;
        this.panel = panel;

        ApiKey appId = new ApiKey("applicationId");
        ApiKey appSecret = new ApiKey("applicationSecret");
        String concat = appId.get() + ":" + appSecret.get();
        this.keys = "Basic " + Base64.getEncoder().encodeToString(
                concat.getBytes());
    }

    public void fetchPlanetPositions(String planets)
    {
        String date = LocalDate.now().toString();
        String time = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));

        Disposable disposable = astronomyService.getPosition(
                        keys, longitude, latitude, 0,
                        date, date, time,
                        planets
                )
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.from(SwingUtilities::invokeLater))
                .subscribe(
                        this::handleResponse,
                        Throwable::printStackTrace);
    }

    public void search(String locationName)
    {
        if (locationName == null || locationName.isEmpty()) return;

        String apiKey = new ApiKey("weathermapKey").get();
        geocodingService.getGeolocation(locationName, apiKey, 1)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.from(SwingUtilities::invokeLater))
                .subscribe(results ->
                {
                    if (results.length > 0)
                    {
                        this.latitude = results[0].lat;
                        this.longitude = results[0].lon;
                        fetchPlanetPositions("sun,moon,mars,venus,jupiter,saturn,uranus,neptune,pluto,mercury");
                    } else
                    {
                        JOptionPane.showMessageDialog(panel, "Location not found: " + locationName);
                    }
                }, Throwable::printStackTrace);
    }

    public void display()
    {
        fetchPlanetPositions("sun,moon,mars,venus,jupiter,saturn,uranus,neptune,pluto,mercury");
    }

    private void handleResponse(AstronomyResponse response)
    {
        List<Planet> planets = new ArrayList<>();
        if (response.data != null && response.data.table != null && response.data.table.rows != null)
        {
            for (AstronomyResponse.Row row : response.data.table.rows)
            {
                if (row.cells != null)
                {
                    for (AstronomyResponse.Cell cell : row.cells)
                    {
                        if (cell.position != null && cell.position.horizontal != null)
                        {
                            String name = cell.name;
                            double azimuth = cell.position.horizontal.azimuth.degrees;
                            double altitude = cell.position.horizontal.altitude.degrees;

                            planets.add(new Planet(name, azimuth, altitude));

                        }
                    }
                }
            }
        }

        panel.setPlanets(planets);
        panel.repaint();
    }

}
