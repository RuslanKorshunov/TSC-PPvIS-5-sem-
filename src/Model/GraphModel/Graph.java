package Model.GraphModel;

import Model.Stop;
import Model.Transport;

import java.util.*;

public class Graph
{
    private Map<Stop, List<Stop>> stopListMap;
    private Map<Edge, List<Transport>> edgeListMap;

    public Graph()
    {
        stopListMap=new HashMap<>();
        edgeListMap=new HashMap<>();
    }

    private void addNewEdge(Transport transport, Edge edge)
    {
        boolean choice=false;
        for(Map.Entry<Edge, List<Transport>> entry: edgeListMap.entrySet())
            if(edge.equals(entry.getKey()))
            {
                if(!edgeListMap.get(entry.getKey()).contains(transport))
                    edgeListMap.get(entry.getKey()).add(transport);
                choice=true;
                break;
            }
        if(!choice)
        {
            List<Transport> list=new ArrayList<>();
            list.add(transport);
            edgeListMap.put(edge, list);
        }
        addNewNode(edge.getFirstStop());
        addNewNode(edge.getSecondStop());
        if(!stopListMap.get(edge.getFirstStop()).contains(edge.getSecondStop()))
            stopListMap.get(edge.getFirstStop()).add(edge.getSecondStop());
    }

    public void addNewEdges(Transport transport, List<Stop> listStop)
    {
        for(int i=0; i<listStop.size()-1; i++)
        {
            Edge edge=new Edge(listStop.get(i), listStop.get(i+1));
            addNewEdge(transport, edge);
        }
    }

    private void addNewNode(Stop stop)
    {
        if(!stopListMap.containsKey(stop))
            stopListMap.put(stop, new ArrayList<Stop>());
    }

    public void addNewNodes(List<Stop> list)
    {
        for(Stop stop: list)
            addNewNode(stop);
    }

    public List<Stop> getListStops(Stop stop)
    {
        return stopListMap.get(stop);
    }

    public List<Transport> getListTransportBy(Edge edge)
    {
        List<Transport> list=new ArrayList<>();
        String s1=edge.getFirstStop().getName();
        String s2=edge.getSecondStop().getName();
/*        if(edgeListMap.containsKey(edge))
            list=edgeListMap.get(edge);*/
        int i=0;
        for(Map.Entry<Edge, List<Transport>> entry: edgeListMap.entrySet())
        {
            String s3=entry.getKey().getFirstStop().getName();
            String s4=entry.getKey().getSecondStop().getName();
            if (entry.getKey().equals(edge))
                list = entry.getValue();
            i++;
        }
        return list;
    }

    public void showGraph()
    {
        for(Map.Entry<Stop, List<Stop>> entry: stopListMap.entrySet())
        {
            System.out.print(entry.getKey().getName()+": ");
            for(int i=0; i<entry.getValue().size(); i++)
                System.out.print(entry.getValue().get(i).getName()+" | ");
            System.out.print("\n");
        }
    }

    public void showEdges()
    {
        System.out.print(edgeListMap.size());
        for(Map.Entry<Edge, List<Transport>> entry: edgeListMap.entrySet())
        {
            System.out.print(entry.getKey().getFirstStop().getName()+"->"+entry.getKey().getSecondStop().getName()+": ");
            for(Transport transport:entry.getValue())
                System.out.print(transport.getTransportType()+" "+transport.getNumber()+", ");
            System.out.print("\n");
        }
    }
}