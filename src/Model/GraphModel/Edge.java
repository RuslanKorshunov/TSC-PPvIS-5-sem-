package Model.GraphModel;

import Model.Stop;

public class Edge
{
    private Stop firstStop;
    private Stop secondStop;

    public Edge(Stop firstStop, Stop secondStop)
    {
        this.firstStop=firstStop;
        this.secondStop=secondStop;
    }

    public Stop getFirstStop() {
        return firstStop;
    }

    public Stop getSecondStop() {
        return secondStop;
    }
}
