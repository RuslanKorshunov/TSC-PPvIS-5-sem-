import Controller.Operations;
import Model.GraphModel.Edge;
import Model.GraphModel.Graph;
import Model.Stop;
import Model.Transport;
import View.MainWindow;

import java.util.LinkedList;
import java.util.List;

public class Main
{

    public static void main(String[]args)
    {
        List<Transport> listBuses=new LinkedList<>();
        List<Transport> listTrams=new LinkedList<>();
        List<Transport> listTrolleybus=new LinkedList<>();
        List<Transport> listMetro=new LinkedList<>();
        //addTransports(listBuses, listTrams);
        Operations operations=new Operations(listBuses, listTrams, listTrolleybus, listMetro);
        MainWindow window=new MainWindow(operations);
        //createGraph();
    }

    static void createGraph()
    {
        /*Graph graph=new Graph();
        Stop first=new Stop("01");
        Stop second=new Stop("02");
        Stop third=new Stop("03");
        Stop fourth=new Stop("04");
        Stop fifth=new Stop("05");
        Stop sixth=new Stop("06");
        Stop seventh=new Stop("07");
        Stop eighth=new Stop("08");
        Stop ninth=new Stop("09");
        Stop tenth=new Stop("10");
        Stop eleventh=new Stop("11");
        graph.addNewEdge(new Edge(first, second));
        graph.addNewEdge(new Edge(second, third));
        graph.addNewEdge(new Edge(third, fourth));
        graph.addNewEdge(new Edge(fourth, fifth));
        graph.addNewEdge(new Edge(fifth, fourth));
        graph.addNewEdge(new Edge(fourth, sixth));
        graph.addNewEdge(new Edge(sixth, seventh));
        //graph.addNewEdge(new Edge(seventh, first));
        graph.addNewEdge(new Edge(sixth, eighth));
        graph.addNewEdge(new Edge(eighth, ninth));
        graph.addNewEdge(new Edge(ninth, tenth));
        graph.addNewEdge(new Edge(tenth, sixth));
        graph.addNewEdge(new Edge(sixth, third));
        graph.addNewEdge(new Edge(fifth, eleventh));
        graph.addNewEdge(new Edge(tenth, eleventh));
        graph.findPath(first, tenth);
        graph.findPath(seventh, fifth);
        graph.findPath(fifth, seventh);*/
        //graph.showGraph();
    }

    /*public static void addTransports(List<Bus> listBuses, List<Tram> listTram) {
        List<Stop> listStops1 = new LinkedList<>();
        List<Stop> listStops2 = new LinkedList<>();
        String[] nameOfTheStops1 = {"ДС Веснянка", "Нарочанская", "Минск-Арена",
                "Университет физкультуры", "Радужная", "Крупцы",
                "Футбольный манеж", "Площадь Государственного флага", "Республиканский центр тенниса",
                "Комсомольское озеро", "Гвардейская", "гостиница Юбилейная",
                "Замчище", "Площадь Свободы", "Ленина", "Кирова"};
        for (int i = 0; i < nameOfTheStops1.length; i++)
            listStops1.add(new Stop(nameOfTheStops1[i]));
        String[] nameOfTheStops2 = {"Кирова", "Вокзал", "Площадь Независимости",
                "Володарского", "Площадь Свободы", "Дворец Спорта",
                "гостиница Юбилейная", "Музей истории ВОВ", "Гвардейская",
                "Комсомольское озеро", "Республиканский центр тенниса", "Футбольный манеж",
                "Крупцы", "Радужная", "Университет физкультуры", "Минск-Арена",
                "Посёлок Пионерский", "Нарочанская", "ДС Веснянка"};
        for (int i = 0; i < nameOfTheStops2.length; i++)
            listStops2.add(new Stop(nameOfTheStops2[i]));
        List<Timetable> listTimetableFirstWay = new LinkedList<>();
        List<Timetable> listTimetableSecondWay = new LinkedList<>();
        int plus=0;
        for (int i = 0; i < listStops1.size(); i++)
        {
            Timetable timetable=new Timetable();
            for(int h=0; h<24; h++)
                for(int m=0; m<=59; m+=(15+plus))
                    timetable.addNewTime(h, m);
            listTimetableFirstWay.add(timetable);
            plus+=1;
        }
        for (int i = 0; i < listStops2.size(); i++)
        {
            Timetable timetable=new Timetable();
            for(int h=0; h<24; h++)
                for(int m=0; m<=59; m+=(25+plus))
                    timetable.addNewTime(h, m);
            listTimetableSecondWay.add(timetable);
            plus-=2;
        }
        Rout routOfBus1=new Rout(listStops1, listStops2, listTimetableFirstWay, listTimetableSecondWay);
        Bus bus1=new Bus("1", routOfBus1);
        listBuses.add(bus1);
    }
    */
}