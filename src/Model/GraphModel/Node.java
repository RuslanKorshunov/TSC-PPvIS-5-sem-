package Model.GraphModel;

import Model.Stop;

public class Node
{
    private Stop stop;

    public Node(Stop stop)
    {
        this.stop=stop;
    }

    public String getNodeName()
    {
        return stop.getName();
    }
}
