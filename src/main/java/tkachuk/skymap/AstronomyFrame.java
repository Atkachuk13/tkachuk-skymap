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

        AstronomyService service = new AstronomyServiceFactory().getService();
        AstronomyController controller = new AstronomyController(panel);
        controller.display();

        // manual location
        controller.setLocation(40.7142, -74.0059);
        controller.display();
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
