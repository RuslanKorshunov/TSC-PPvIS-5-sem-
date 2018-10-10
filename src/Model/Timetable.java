package Model;

import java.util.LinkedList;
import java.util.List;

public class Timetable
{
    List<String> timetable;

    public Timetable()
    {
        timetable=new LinkedList<>();
        for(int i=0; i<=23; i++)
        {
            String timeString=i<10?" "+i+": ":i+": ";
            timetable.add(timeString);
        }
    }

    public void addNewTime(String hour, String minute)
    {
        String newTime=timetable.get(Integer.parseInt(hour))+":"+minute;
        timetable.set(Integer.parseInt(hour), newTime);
    }

    public String getTime(int hour)
    {
        return timetable.get(hour);
    }
}
