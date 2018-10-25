package View;

import Model.Stop;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

class StopPanel extends JPanel
{
    private final int DIAMETER=12;
    private final int DISTANCE = DIAMETER+5;
    private final int WIDTH=300;
    private final int HEIGHT=500;
    private final int XCOOR = 20;
    private List<Stop> path;

    StopPanel()
    {
        path=new ArrayList<>();
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
    }

    StopPanel(List<Stop> path)
    {
        this.path=path;
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
    }

    public void paintComponent(Graphics g)
    {
        Graphics2D graphics2D=(Graphics2D)g;
        graphics2D.setColor(Color.white);

        graphics2D.fillRect(0,0, WIDTH, HEIGHT);
        int yCoor=15;
        for(int i=path.size()-1; i>=0; i--)
        {
            graphics2D.setColor(Color.red);
            graphics2D.fillOval(XCOOR, yCoor, DIAMETER, DIAMETER);
            graphics2D.setColor(Color.black);
            graphics2D.drawString(path.get(i).getName(), XCOOR+DIAMETER, yCoor+DIAMETER);
            yCoor+=DISTANCE;
        }
    }
}
