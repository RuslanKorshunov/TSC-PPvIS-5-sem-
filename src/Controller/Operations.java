package Controller;

import Model.Transport;
import Model.Stop;
import Model.Timetable;
import Model.TransportType;
import Model.WayType;
import java.util.LinkedList;
import java.util.List;

public class Operations
{
    List<Transport> listBuses;
    List<Transport> listTrams;
    List<Transport> listTrolleybuses;
    List<Transport> listMetro;
    List<Transport> listTransports;
    List<Stop> listAllStops;
    List<Stop> listStopsThisRout;
    Timetable timetable;
    TransportType transportType;
    WayType wayType;
    OpenParser openParser;
    Transport currentTransport;

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
        listAllStops=new LinkedList<>();
        listStopsThisRout=new LinkedList<>();

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
        //listStopsThisRout.clear();
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

    //исправить
    public Timetable getTimetable(WayType wayType, int indexOfStop)
    {
        if(currentTransport.getRout().getListTimetable(wayType).isEmpty())
        {
            String fileName="XML-files/"+transportType+"/"+currentTransport.getNumber()+"/";
            if(wayType==WayType.FIRSTWAY)
                fileName+="TimetableFirstWay.xml";
            else
                fileName+="TimetableSecondWay.xml";
            System.out.println(fileName);
            openParser.openFile(fileName, 3);
        }
        //timetable=listTransports.get(indexOfTransport).getRout().getListTimetable(wayType).get(indexOfStop);
        /*for(int i=0; i<24; i++)
            System.out.println(timetable.getTime(i));*/
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

    public void exitFromProgram()
    {
        System.exit(0);
    }
}