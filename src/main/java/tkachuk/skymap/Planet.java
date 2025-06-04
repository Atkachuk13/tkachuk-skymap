package tkachuk.skymap;

public class Planet
{
    public final String name;
    public final double azimuth;
    public final double altitude;

    public Planet(String name, double azimuth, double altitude)
    {
        this.name = name;
        this.azimuth = azimuth;
        this.altitude = altitude;
    }
}
