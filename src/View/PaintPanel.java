package View;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

class PaintPanel extends JPanel
{
    private static final int DIAMETER=12;
    private static final int RADIUS=DIAMETER/2;
    private static final int WIDTH=300;
    private static final int HEIGHT=500;
    public static final int XCOOR = 20;
    private List<String> path;

    PaintPanel()
    {
        path=new ArrayList<>();
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
    }

    PaintPanel(List<String> path)
    {
        this.path=path;
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
    }

    public void paintComponent(Graphics g)
    {
        Graphics2D graphics2D=(Graphics2D)g;
        graphics2D.setColor(Color.white);

        graphics2D.fillRect(0,0, WIDTH, HEIGHT);
        int yCoor=HEIGHT/2-Math.round(path.size()/2)*18;
        for(int i=path.size()-1; i>=0; i--)
        {
            graphics2D.setColor(Color.red);
            graphics2D.fillOval(XCOOR, yCoor, DIAMETER, DIAMETER);
            graphics2D.setColor(Color.black);
            graphics2D.drawString(path.get(i), XCOOR+DIAMETER, yCoor+DIAMETER);
            yCoor+=18;
        }
    }
}
