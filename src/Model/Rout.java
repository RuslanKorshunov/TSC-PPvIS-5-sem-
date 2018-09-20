package Model;

import java.util.List;

public class Rout
{
    private String nameFirstWay;
    private String nameSecondWay;
    private List<Stop> listStopsFirstWay;
    private List<Stop> listStopsSecondWay;
    private List<Timetable> listTimetableFirstWay;
    private List<Timetable> listTimetableSecondWay;

    public Rout(List<Stop> listStopsFirstWay, List<Stop> listStopsSecondWay, List<Timetable> listTimetableFirstWay, List<Timetable> listTimetableSecondWay)
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

    public List<Timetable> getListTimetable(int codeWay)
    {
        switch (codeWay)
        {
            case 1:
                return listTimetableFirstWay;
            case 2:
                return listTimetableSecondWay;
        }
        return listTimetableFirstWay;
    }

    public String getName(int codeWay)
    {
        switch (codeWay)
        {
            case 1:
                return nameFirstWay;
            case 2:
                return nameSecondWay;
        }
        return nameFirstWay;
    }

    public List<Stop> getListStops(int codeWay)
    {
        switch (codeWay)
        {
            case 1:
                return listStopsFirstWay;
            case 2:
                return listStopsSecondWay;
        }
        return listStopsFirstWay;
    }

    /*public List<Timetable> getListTimetableFirstWay()
    {
        return listTimetableFirstWay;
    }

    public List<Timetable> getListTimetableSecondWay()
    {
        return listTimetableSecondWay;
    }*/

/*    public String getNameFirstWay()
    {
        return nameFirstWay;
    }

    public String getNameSecondWay()
    {
        return nameSecondWay;
    }*/

/*    public List<Stop> getListStopsFirstWay()
    {
        return listStopsFirstWay;
    }

    public List<Stop> getListStopsSecondWay()
    {
        return listStopsSecondWay;
    }*/

    private String setNameWay(List<Stop> listStopsWay)
    {
        return listStopsWay.get(0).getName()+" - "+listStopsWay.get(listStopsWay.size()-1).getName();
    }
}