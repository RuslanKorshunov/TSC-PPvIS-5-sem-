package View;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

class TransportPanel extends JPanel
{
    private final int DIAMETER=12;
    private final int DISTANCE = DIAMETER + 5;
    private final int WIDTH=225;
    private final int HEIGHT=350;
    private final int XCOOR = 50;
    private List<String> path;

    TransportPanel()
    {
        path=new ArrayList<>();
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
    }

    TransportPanel(List<String> path)
    {
        this.path=path;
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
    }

    public void paintComponent(Graphics g)
    {
        Graphics2D graphics2D=(Graphics2D)g;
        graphics2D.setColor(Color.white);

        graphics2D.fillRect(0 ,0, WIDTH, HEIGHT);
        int fontSize=10;
        int yCoorForOvals=15;
        int yCoorForTrans=15+ DISTANCE /2+fontSize;
        //System.out.println(yCoorForOvals);
        for(int i=path.size()-1; i>=0; i--)
        {
            String string=path.get(i);
            if(!string.contains("авт.") && !string.contains("трам.") && !string.contains("трол.") && !string.contains("мет."))
            {
                graphics2D.setColor(Color.RED);
                graphics2D.fillOval(XCOOR, yCoorForOvals, DIAMETER, DIAMETER);
                graphics2D.setColor(Color.black);
                graphics2D.drawString(path.get(i), XCOOR+DIAMETER, yCoorForOvals+DIAMETER);
                yCoorForOvals+= DISTANCE;
            }
            else
            {
                graphics2D.setColor(Color.BLACK);
                graphics2D.drawString(path.get(i), 2, yCoorForTrans);
                yCoorForTrans+= DISTANCE;
            }
        }
    }
}

