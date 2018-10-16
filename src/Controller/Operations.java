package Controller;

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

    public Operations(List<Transport> listBuses,
                      List<Transport> listTrams,
                      List<Transport> listTrolleybuses,
                      List<Transport> listMetro)
    {
        this.listBuses=listBuses;
        this.listTrams =listTrams;
        this.listTrolleybuses=listTrolleybuses;
        this.listMetro=listMetro;

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
                        graph.addNewEdges(getListStops(transportType, wayType, listTransports.indexOf(transport)));
                }
    }

    public List<String> findPath(int beginIndex, int endIndex)
    {
        createGraph();
        Stop begin=listAllStops.get(beginIndex);
        Stop end=listAllStops.get(endIndex);

        Queue<Stop> waitingStops=new LinkedBlockingQueue<>();
        List<Stop> testedStops=new ArrayList<>();
        List<String> path=new ArrayList<>();
        Map<Stop, Stop> parents=new HashMap<>();
        parents.put(begin, begin);
        path.add(end.getName());

        waitingStops.add(begin);
        boolean answer=breadthFirstSearch(waitingStops.poll(), end, waitingStops, testedStops, parents);

        /*System.out.println(answer+"\n//////////////////");*/
        if(answer!=true)
            path.clear();
        else
        {
            Stop parent=parents.get(end);
            while(!parent.equals(begin))
            {
                path.add(parent.getName());
                Stop newParent=parents.get(parent);
                parent=newParent;
            }
            path.add(begin.getName());
            /*for(String stop: path)
                System.out.print(stop+"<-");*/
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
        //String name=currentStop.getName();
        testedStops.add(currentStop);
        if(currentStop.equals(end))
            return true;
        for(Stop stop: graph.getListStops(currentStop))
        {
            //String otherName=stop.getName();
            if(!testedStops.contains(stop))
            {
                parents.putIfAbsent(stop, currentStop);
                //String othername2=stop.getName();
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

    public void exitFromProgram()
    {
        System.exit(0);
    }
}