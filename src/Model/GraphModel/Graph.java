package Model.GraphModel;

import Model.Stop;

import java.util.*;

public class Graph
{
    Map<Stop, List<Stop>> lines;

    public Graph()
    {
        lines=new HashMap<>();
    }

    public void addNewEdge(Edge edge)
    {
        addNewNode(edge.getFirstStop());
        addNewNode(edge.getSecondStop());
        //List<Stop> listStops= lines.get(edge.getFirstStop());
        if(!lines.get(edge.getFirstStop()).contains(edge.getSecondStop()))
            lines.get(edge.getFirstStop()).add(edge.getSecondStop());
    }

    public void addNewEdges(List<Stop> listStop)
    {
        for(int i=0; i<listStop.size()-1; i++)
            addNewEdge(new Edge(listStop.get(i), listStop.get(i+1)));
    }

    private void addNewNode(Stop stop)
    {
        if(!lines.containsKey(stop))
            lines.put(stop, new ArrayList<Stop>());
    }

    public void addNewNodes(List<Stop> list)
    {
        for(Stop stop: list)
            addNewNode(stop);
    }

    public List<Stop> getListStops(Stop stop)
    {
        return lines.get(stop);
    }

    public void showGraph()
    {
        for(Map.Entry<Stop, List<Stop>> entry: lines.entrySet())
        {
            System.out.print(entry.getKey().getName()+": ");
            for(int i=0; i<entry.getValue().size(); i++)
                System.out.print(entry.getValue().get(i).getName()+" | ");
            System.out.print("\n");
        }
    }
}