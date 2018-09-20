package Controller;

import Model.*;
import java.util.LinkedList;
import java.util.List;

public class Operations
{
    private List<Bus> listBuses;
    private List<Tram> listTrames;
    private List<Trolleybus> listTrolleybuses;
    private List<Metro> listMetro;
    private List<Transport> listTransports;
    private List<Stop> listStops;
    private Timetable timetable;

    public Operations(List<Bus> listBuses, List<Tram> listTrams, List<Trolleybus> listTrolleybuses, List<Metro> listMetro)
    {
        this.listBuses=listBuses;
        this.listTrames =listTrams;
        this.listTrolleybuses=listTrolleybuses;
        this.listMetro=listMetro;
        listTransports=new LinkedList<>();
        listStops=new LinkedList<>();
    }

    public List<Transport> getListTransports(int code)
    {
        listTransports.clear();
        listStops.clear();
        switch(code)
        {
            case 1:
                listTransports.addAll(listBuses);
                break;
            case 2:
                listTransports.addAll(listTrames);
                break;
            case 3:
                listTransports.addAll(listTrolleybuses);
                break;
            case 4:
                listTransports.addAll(listMetro);
                break;
        }
        return listTransports;
    }

    public List<Stop> getListStops(int codeTransport, int codeChangeWay, int indexOfTransport)
    {
        listStops.clear();
        switch(codeTransport)
        {
            case 1:
                listStops.addAll(listBuses.get(indexOfTransport).getRout().getListStops(codeChangeWay));
                break;
            case 2:
                listStops.addAll(listTrames.get(indexOfTransport).getRout().getListStops(codeChangeWay));
                break;
            case 3:
                listStops.addAll(listTrolleybuses.get(indexOfTransport).getRout().getListStops(codeChangeWay));
                break;
            case 4:
                listStops.addAll(listMetro.get(indexOfTransport).getRout().getListStops(codeChangeWay));
                break;
        }
        return listStops;
    }

    //исправить
    public Timetable getTimetable(int codeWay, int indexOfTransport, int indexOfStop)
    {

        timetable=listTransports.get(indexOfTransport).getRout().getListTimetable(codeWay).get(indexOfStop);
        for(int i=0; i<24; i++)
            System.out.println(timetable.getTime(i));
        return timetable;
    }

    public String getNameWay(int codeTransport, int codeChangeWay, int indexOfTransport)
    {
        switch(codeTransport)
        {
            case 1:
                return listBuses.get(indexOfTransport).getRout().getName(codeChangeWay);
            case 2:
                return listTrames.get(indexOfTransport).getRout().getName(codeChangeWay);
            case 3:
                return listTrolleybuses.get(indexOfTransport).getRout().getName(codeChangeWay);
            case 4:
                return listMetro.get(indexOfTransport).getRout().getName(codeChangeWay);
        }
        return " ";
    }

    public void exitFromProgram()
    {
        System.exit(0);
    }
}