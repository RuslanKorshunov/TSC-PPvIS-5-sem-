package Model;

import java.util.LinkedList;
import java.util.List;

public class Timetable
{
    private List<String> listTimetable;

    public Timetable()
    {
        listTimetable=new LinkedList<>();
        for(int i=0; i<=23; i++)
        {
            String timeString=i<10?" "+i+": ":i+": ";
            listTimetable.add(timeString);
        }
    }

    public void addNewTime(String hour, String minute)
    {
        String newTime=listTimetable.get(Integer.parseInt(hour))+minute;
        listTimetable.set(Integer.parseInt(hour), newTime);
    }

    public String getTime(int hour)
    {
        return listTimetable.get(hour);
    }
}
