package Model;

import java.util.LinkedList;
import java.util.List;

public class Rout
{
/*    private static final int FIRSTWAY = 1;
    private static final int SECONDWAY = 2;
    private String nameFirstWay;
    private String nameSecondWay;*/
    private List<Stop> listStopsFirstWay;
    private List<Stop> listStopsSecondWay;
    private List<Timetable> listTimetableFirstWay;
    private List<Timetable> listTimetableSecondWay;
    private List<Timetable> listTimetableFirstWayWeek;
    private List<Timetable> listTimetableSecondWayWeek;

    public Rout()
    {
        listStopsFirstWay=new LinkedList<>();
        listStopsSecondWay=new LinkedList<>();
        listTimetableFirstWay=new LinkedList<>();
        listTimetableSecondWay=new LinkedList<>();
        listTimetableFirstWayWeek=new LinkedList<>();
        listTimetableSecondWayWeek=new LinkedList<>();
    }

    /*public Rout(List<Stop> listStopsFirstWay,
                List<Stop> listStopsSecondWay,
                List<Timetable> listTimetableFirstWay,
                List<Timetable> listTimetableSecondWay)
    {
        this.listStopsFirstWay=listStopsFirstWay;
        this.listStopsSecondWay=listStopsSecondWay;
        this.listStopsFirstWay=listStopsFirstWay;
        this.listStopsSecondWay=listStopsSecondWay;
        this.listTimetableFirstWay=listTimetableFirstWay;
        this.listTimetableSecondWay=listTimetableSecondWay;
        nameFirstWay=setNameWay(this.listStopsFirstWay);
        nameSecondWay=setNameWay(this.listStopsSecondWay);
    }
    */

    public List<Timetable> getListTimetable(WayType wayType)
    {
        switch (wayType)
        {
            case FIRSTWAY:
                return listTimetableFirstWay;
            case SECONDWAY:
                return listTimetableSecondWay;
        }
        return listTimetableFirstWay;
    }

    public String getName(WayType wayType)
    {
        switch (wayType)
        {
            case FIRSTWAY:
                return setNameWay(listStopsFirstWay);
            case SECONDWAY:
                return setNameWay(listStopsSecondWay);
        }
        return " ";
    }

    public List<Stop> getListStops(WayType wayType)
    {
        switch (wayType)
        {
            case FIRSTWAY:
                return listStopsFirstWay;
            case SECONDWAY:
                return listStopsSecondWay;
        }
        return listStopsFirstWay;
    }

    private String setNameWay(List<Stop> listStopsWay)
    {
        return listStopsWay.get(0).getName()+" - "+listStopsWay.get(listStopsWay.size()-1).getName();
    }
}