package tkachuk.skymap;

import javax.swing.*;

public class AstronomyFrame extends JFrame
{
    public AstronomyFrame()
    {
        setTitle("Sky Map");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(900, 500);

        SkyPanel panel = new SkyPanel();
        add(panel);

        AstronomyService astronomyService = new AstronomyServiceFactory().getService();
        GeocodingService geocodingService = new GeocodingServiceFactory().getService();
        AstronomyController controller = new AstronomyController(astronomyService, geocodingService, panel);
        controller.display();

        panel.setSearchListener(e ->
        {
            String location = panel.getSearchText();
            controller.search(location);
        });
    }

    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(() ->
        {
            AstronomyFrame frame = new AstronomyFrame();
            frame.setVisible(true);
        });
    }
}
