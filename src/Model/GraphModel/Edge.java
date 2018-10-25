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

    @Override
    public boolean equals(Object obj)
    {
        if(obj==this)
            return true;
        if(obj==null || obj.getClass()!=this.getClass())
            return false;
        Edge edge=(Edge) obj;
        if(firstStop==edge.firstStop && secondStop==edge.secondStop)
            return true;
        return false;
    }
}
