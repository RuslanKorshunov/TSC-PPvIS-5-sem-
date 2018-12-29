package Model;

import java.util.LinkedList;
import java.util.List;

public class Transport
{
    private String number;
    private TransportType transportType;
    private List<Stop> listStopsFirstWay;
    private List<Stop> listStopsSecondWay;
    private List<Timetable> listTimetablesFirstWay;
    private List<Timetable> listTimetablesSecondWay;

    public Transport()
    {
        number="";
        transportType=TransportType.NULL;
        listStopsFirstWay=new LinkedList<>();
        listStopsSecondWay=new LinkedList<>();
        listTimetablesFirstWay =new LinkedList<>();
        listTimetablesSecondWay =new LinkedList<>();
    }

    public void setNumber(String number)
    {
        this.number=number;
    }

    public void setTransportType(TransportType transportType)
    {
        this.transportType=transportType;
    }

    public String getNumber()
    {
        return number;
    }

    public TransportType getTransportType()
    {
        return transportType;
    }

    public List<Timetable> getListTimetables(WayType wayType)
    {
        switch (wayType)
        {
            case FirstWay:
                return listTimetablesFirstWay;
            case SecondWay:
                return listTimetablesSecondWay;
        }
        return listTimetablesFirstWay;
    }

    public String getName(WayType wayType)
    {
        switch (wayType)
        {
            case FirstWay:
                return setNameWay(listStopsFirstWay);
            case SecondWay:
                return setNameWay(listStopsSecondWay);
        }
        return " ";
    }

    public List<Stop> getListStops(WayType wayType)
    {
        switch (wayType)
        {
            case FirstWay:
                return listStopsFirstWay;
            case SecondWay:
                return listStopsSecondWay;
        }
        return listStopsFirstWay;
    }

    private String setNameWay(List<Stop> listStopsWay)
    {
        return listStopsWay.get(0).getName()+" - "+listStopsWay.get(listStopsWay.size()-1).getName();
    }
}