package Controller;

import Model.GraphModel.Edge;
import Model.GraphModel.Graph;
import Model.Transport;
import Model.Stop;
import Model.Timetable;
import Model.TransportType;
import Model.WayType;

import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;

public class Operations
{
    List<Transport> listBuses;
    List<Transport> listTrams;
    List<Transport> listTrolleybuses;
    List<Transport> listMetro;
    List<Transport> listTransports;
    List<Stop> listStopsThisRout;
    List<Stop> listAllStops;
    TransportType transportType;
    WayType wayType;
    OpenParser openParser;
    Transport currentTransport;
    Graph graph;

    public Operations()
    {
        listBuses=new LinkedList<>();
        listTrams=new LinkedList<>();
        listTrolleybuses=new LinkedList<>();
        listMetro=new LinkedList<>();
        listTransports=new LinkedList<>();
        listStopsThisRout=new LinkedList<>();
        listAllStops=new LinkedList<>();

        openParser=new OpenParser(this);
        openParser.openFile("XML-files/Stops.xml", 0);
        openParser.openFile("XML-files/Transports.xml", 1);
    }

    public List<Transport> getListTransports(TransportType transportType)
    {
        listTransports.clear();
        listStopsThisRout.clear();
        this.transportType=transportType;
        switch(transportType)
        {
            case Bus:
                listTransports.addAll(listBuses);
                break;
            case Tram:
                listTransports.addAll(listTrams);
                break;
            case Trolleybus:
                listTransports.addAll(listTrolleybuses);
                break;
            case Metro:
                listTransports.addAll(listMetro);
                break;
        }
        return listTransports;
    }

    public List<Stop> getListStops(TransportType transportType, WayType wayType, int indexOfTransport)
    {
        this.transportType=transportType;
        this.wayType=wayType;
        currentTransport=listTransports.get(indexOfTransport);
        if(currentTransport.getRout().getListStops(wayType).isEmpty())
        {
            String fileName="XML-files/"+transportType+"/"+currentTransport.getNumber()+"/";
            if(wayType==WayType.FIRSTWAY)
                fileName+="StopsFirstWay.xml";
            else
                fileName+="StopsSecondWay.xml";
            openParser.openFile(fileName, 2);
        }
        return currentTransport.getRout().getListStops(wayType);
    }

    public Timetable getTimetable(WayType wayType, int indexOfStop)
    {
        if(currentTransport.getRout().getListTimetable(wayType).isEmpty())
        {
            String fileName="XML-files/"+transportType+"/"+currentTransport.getNumber()+"/";
            if(wayType==WayType.FIRSTWAY)
                fileName+="TimetableFirstWay.xml";
            else
                fileName+="TimetableSecondWay.xml";
            openParser.openFile(fileName, 3);
        }
        return currentTransport.getRout().getListTimetable(wayType).get(indexOfStop);
    }

    public String getNameWay(TransportType transportType, WayType wayType, int indexOfTransport)
    {
        this.transportType=transportType;
        switch(transportType)
        {
            case Bus:
                return listBuses.get(indexOfTransport).getRout().getName(wayType);
            case Tram:
                return listTrams.get(indexOfTransport).getRout().getName(wayType);
            case Trolleybus:
                return listTrolleybuses.get(indexOfTransport).getRout().getName(wayType);
            case Metro:
                return listMetro.get(indexOfTransport).getRout().getName(wayType);
        }
        return " ";
    }

    public String[] getListNameStops()
    {
        int size=listAllStops.size();
        String[] listNames=new String[size];
        for(int i=0; i<size; i++)
            listNames[i]=listAllStops.get(i).getName();
        return listNames;
    }
/////////////////////////////////////////
    private void createGraph()
    {
        graph=new Graph();
        graph.addNewNodes(listAllStops);

        for(TransportType transportType: TransportType.values())
            for(WayType wayType: WayType.values())
                if(transportType!=TransportType.NULL)
                {
                    getListTransports(transportType);
                    for(Transport transport:listTransports)
                        graph.addNewEdges(transport, getListStops(transportType, wayType, listTransports.indexOf(transport)));
                }
    }

    public List<Stop> findPath(int beginIndex, int endIndex)
    {
        createGraph();
        Stop begin=listAllStops.get(beginIndex);
        Stop end=listAllStops.get(endIndex);

        Queue<Stop> waitingStops=new LinkedBlockingQueue<>();
        List<Stop> testedStops=new ArrayList<>();
        List<Stop> path=new ArrayList<>();
        Map<Stop, Stop> parents=new HashMap<>();
        parents.put(begin, begin);
        path.add(end);

        waitingStops.add(begin);
        boolean answer=breadthFirstSearch(waitingStops.poll(), end, waitingStops, testedStops, parents);

        if(answer!=true)
            path.clear();
        else
        {
            Stop parent=parents.get(end);
            while(!parent.equals(begin))
            {
                path.add(parent);
                Stop newParent=parents.get(parent);
                parent=newParent;
            }
            path.add(begin);
        }
        return path;
    }

    private boolean breadthFirstSearch(Stop currentStop,
                                       Stop end,
                                       Queue<Stop> waitingStops,
                                       List<Stop> testedStops,
                                       Map<Stop, Stop> parents)
    {
        boolean answer;
        testedStops.add(currentStop);
        if(currentStop.equals(end))
            return true;
        for(Stop stop: graph.getListStops(currentStop))
        {
            if(!testedStops.contains(stop))
            {
                parents.putIfAbsent(stop, currentStop);
                waitingStops.add(stop);
            }
        }
        if(waitingStops.isEmpty())
            return false;
        answer=breadthFirstSearch(waitingStops.poll(),
                                    end,
                                    waitingStops,
                                    testedStops,
                                    parents);
        return answer;
    }

    public List<String> getInformationAbout(List<Stop> path)
    {
        List<List<Transport>> listTransportsForPath=new ArrayList<>();
        for (int i = 0; i < path.size() - 1; i++)
        {
            Edge edge = new Edge(path.get(i+1), path.get(i));
            List<Transport> list = graph.getListTransportBy(edge);
            listTransportsForPath.add(list);
        }
        List<Integer> listWayLength=new ArrayList<>();
        List<Transport> listNecessaryTransports=new ArrayList<>();
        for(int i=0; i<listTransportsForPath.size(); i++)
        {
            int size=listTransportsForPath.get(i).size();
            int indexOfMax=0;
            int lengthOfMaxWay=1;
            for(int j=0; j<size; j++)
            {
                int numberOfList=i+1;
                int currentLength=1;
                while((j+numberOfList)<listTransportsForPath.size()
                        && listTransportsForPath.get(numberOfList).contains(listTransportsForPath.get(i).get(j)))
                {
                    numberOfList++;
                    currentLength++;
                }
                if(currentLength>lengthOfMaxWay)
                {
                    lengthOfMaxWay=currentLength;
                    indexOfMax=j;
                }
            }
            listWayLength.add(lengthOfMaxWay);
            listNecessaryTransports.add(listTransportsForPath.get(i).get(indexOfMax));
            i+=(lengthOfMaxWay-1);
        }
        List<String> listResult=new ArrayList<>();
        int indexOfTransport=0;
        listResult.add(path.get(indexOfTransport).getName());
        for(int i=0; i<listWayLength.size(); i++)
        {
            listResult.add(getTransportTypeInRussian(listNecessaryTransports.get(i).getTransportType())+listNecessaryTransports.get(i).getNumber());
            indexOfTransport+=(listWayLength.get(i));
            listResult.add(path.get(indexOfTransport).getName());
        }
        return listResult;
    }

    private String getTransportTypeInRussian(TransportType transportType)
    {
        String string="";
        switch (transportType)
        {
            case Bus:
                string="авт.";
                break;
            case Tram:
                string="трам.";
                break;
            case Trolleybus:
                string="трол.";
                break;
            case Metro:
                string="мет.";
                break;
        }
        return string;
    }

    public void exitFromProgram()
    {
        System.exit(0);
    }
}