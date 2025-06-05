package tkachuk.skymap;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ArrayList;

public class SkyPanel extends JPanel
{
    private List<Planet> planets = new ArrayList<>();
    private JTextField searchBar;
    private JButton searchButton;

    public SkyPanel()
    {
        setBackground(Color.BLACK);
        setLayout(new BorderLayout());

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchBar = new JTextField(20);
        searchButton = new JButton("Search");

        searchPanel.add(searchBar);
        searchPanel.add(searchButton);

        add(searchPanel, BorderLayout.NORTH);
    }

    public void setSearchListener(ActionListener listener)
    {
        searchButton.addActionListener(listener);
    }

    public String getSearchText()
    {
        return searchBar.getText().trim();
    }

    public void setPlanets(List<Planet> planets)
    {
        this.planets = planets;
        repaint();
    }

    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        // lines
        int width = getWidth();
        int height = getHeight();
        int squareWidth = width / 4;
        int squareHeight = height / 2;

        g.setColor(Color.YELLOW);
        for (int i = 1; i < 4; i++)
        {
            g.drawLine(i * squareWidth, 0, i * squareWidth, height);
        }
        g.drawLine(0, squareHeight, width, squareHeight);

        // directions
        for (int row = 0; row < 2; row++)
        {
            for (int col = 0; col < 4; col++)
            {
                int x = col * squareWidth;
                int y = row * squareHeight;

                if (row == 1)
                {
                    String[] bottomBoxes = {"South", "West", "North", "East"};
                    g.setColor(Color.YELLOW);
                    g.drawString(bottomBoxes[col], x + 5, y + squareHeight - 5);
                }

                if (row == 0 && col == 2)
                {
                    g.setColor(Color.YELLOW);
                    g.drawString("Horizon", x + 5, y + squareHeight - 5);
                }
            }
        }

        // planets
        if (planets != null)
        {
            for (Planet p : planets)
            {
                if (p.altitude < 0)
                {
                    continue;
                }
                Point pt = convertToScreen(p.azimuth, p.altitude, width, height);
                g.setColor(Color.WHITE);
                g.fillOval(pt.x - 5, pt.y - 5, 10, 10);
                g.setColor(Color.RED);
                g.drawString(p.name, pt.x + 8, pt.y - 8);
            }
        }
    }

    public Point convertToScreen(double azimuth, double altitude, int width, int height)
    {
        int centerX = width / 2;
        int centerY = height / 2;
        int maxR = Math.min(width, height) / 2 - 50;

        double radius = maxR * (90 - altitude) / 90.0;

        double angle = Math.toRadians(azimuth - 90);

        int x = (int) (centerX + radius * Math.cos(angle));
        int y = (int) (centerY + radius * Math.sin(angle));

        return new Point(x, y);

    }
}
