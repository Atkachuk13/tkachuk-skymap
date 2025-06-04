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
    private final AstronomyService service;
    private final SkyPanel panel;
    private final String keys;
    private double latitude = 40.7142;
    private double longitude = -74.0059;

    public AstronomyController(SkyPanel panel)
    {
        this.service = new AstronomyServiceFactory().getService();
        this.panel = panel;

        ApiKey appId = new ApiKey("applicationId");
        ApiKey appSecret = new ApiKey("applicationSecret");
        String concat = appId.get() + ":" + appSecret.get();
        this.keys = "Basic " + Base64.getEncoder().encodeToString(
                concat.getBytes());
    }

    public void setLocation(double latitude, double longitude)
    {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public void display()
    {
        String date = LocalDate.now().toString();
        String time = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));

        Disposable disposable = service.getPosition(
                        keys, longitude, latitude, 0,
                        date, date, time,
                        "sun, moon, mercury, venus, mars, jupiter, saturn, uranus, neptune, pluto"
                )
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.from(SwingUtilities::invokeLater))
                .subscribe(
                        this::handleResponse,
                        Throwable::printStackTrace);
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
    }

}
