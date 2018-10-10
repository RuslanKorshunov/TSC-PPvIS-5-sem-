package Controller;

import Model.*;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.*;

class OpenParser
{
    private static final int READLISTOFSTOPS = 0;
    private static final int READlISTSOFTRANSPORTS = 1;
    private static final int READLISTOFSTOPSCURRENTWAY = 2;
    public static final int READTIMETABLE = 3;
    private Operations operations;

    OpenParser(Operations operations)
    {
        this.operations=operations;
    }

    private DefaultHandler getDefaultHandlerForStops(int code)
    {
        DefaultHandler defaultHandler=new DefaultHandler()
        {
            boolean bStop;
            boolean bName;
            Stop stop;

            public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException
            {
                if(qName.equalsIgnoreCase("Stop"))
                    bStop=true;
                if(qName.equalsIgnoreCase("Name"))
                    bName=true;
            }

            public void characters(char ch[], int start, int length)
            {
                if(bName)
                {
                    stop=new Stop(new String(ch, start, length));
                    bName=false;
                }
            }

            public void endElement(String uri, String localName, String qName) throws SAXException
            {
                if(qName.equalsIgnoreCase("Stop"))
                {
                    if(code==READLISTOFSTOPS)
                        operations.listAllStops.add(stop);
                    if(code==READLISTOFSTOPSCURRENTWAY)
                        operations.currentTransport.getRout().getListStops(operations.wayType).add(stop);
                }

            }
        };
        return defaultHandler;
    }

    private DefaultHandler getDefaultHandlerForTransports()
    {
        DefaultHandler defaultHandler=new DefaultHandler()
        {
            boolean bNumber;
            boolean bBus;
            boolean bMetro;
            boolean bTram;
            boolean bTrolleybus;
            Transport bus;
            Transport metro;
            Transport tram;
            Transport trolleybus;

            public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException
            {
                if(qName.equalsIgnoreCase("Bus"))
                {
                    bus=new Transport();
                    bus.setTransportType(TransportType.Bus);
                    bBus=true;
                }
                if(qName.equalsIgnoreCase("Metro"))
                {
                    metro=new Transport();
                    metro.setTransportType(TransportType.Metro);
                    bMetro=true;
                }
                if(qName.equalsIgnoreCase("Tram"))
                {
                    tram=new Transport();
                    tram.setTransportType(TransportType.Tram);
                    bTram=true;
                }
                if(qName.equalsIgnoreCase("Trolleybus"))
                {
                    trolleybus=new Transport();
                    trolleybus.setTransportType(TransportType.Trolleybus);
                    bTrolleybus=true;
                }
                if(qName.equalsIgnoreCase("Number")) bNumber=true;
            }

            public void characters(char ch[], int start, int length)
            {
                if(bNumber)
                {
                    String number=new String(ch, start, length);
                    if(bBus)
                    {
                        bus.setNumber(number);
                        bBus=false;
                    }
                    if(bMetro)
                    {
                        metro.setNumber(number);
                        bMetro=false;
                    }
                    if(bTram)
                    {
                        tram.setNumber(number);
                        bTram=false;
                    }
                    if(bTrolleybus)
                    {
                        trolleybus.setNumber(number);
                        bTrolleybus=false;
                    }
                    bNumber=false;
                }
            }

            public void endElement(String uri, String localName, String qName) throws SAXException
            {
                if(qName.equalsIgnoreCase("Bus")) operations.listBuses.add(bus);
                if(qName.equalsIgnoreCase("Metro")) operations.listMetro.add(metro);
                if(qName.equalsIgnoreCase("Tram")) operations.listTrams.add(tram);
                if(qName.equalsIgnoreCase("Trolleybus")) operations.listTrolleybuses.add(trolleybus);
            }
        };
        return defaultHandler;
    }

    private DefaultHandler getDefaultHandlerForTimetable()
    {
        DefaultHandler defaultHandler=new DefaultHandler()
        {
            boolean bTimetable;
            boolean bTime;
            boolean bHour;
            boolean bMinute;
            Timetable timetable;
            String hour;
            String minute;

            public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException
            {
                if(qName.equalsIgnoreCase("Timetable"))
                {
                    timetable=new Timetable();
                    bTimetable=true;
                }
                if(qName.equalsIgnoreCase("Time")) bTime=true;
                if(qName.equalsIgnoreCase("Hour"))
                {
                    hour="";
                    bHour=true;
                }
                if(qName.equalsIgnoreCase("Minute"))
                {
                    minute="";
                    bMinute=true;
                }
            }

            public void characters(char ch[], int start, int length)
            {
                if(bHour)
                {
                    hour = new String(ch, start, length);
                    bHour=false;
                }
                if(bMinute)
                {
                    minute = new String(ch, start, length);
                    bMinute=false;
                }
            }

            public void endElement(String uri, String localName, String qName) throws SAXException
            {
                if(qName.equalsIgnoreCase("Time"))
                    timetable.addNewTime(hour, minute);
                if(qName.equalsIgnoreCase("Timetable"))
                    operations.currentTransport.getRout().getListTimetable(operations.wayType).add(timetable);
            }
        };
        return defaultHandler;
    }
/*
    private DefaultHandler getDefaultHandlerForListStops()
    {
        DefaultHandler defaultHandler=new DefaultHandler()
        {
            boolean bStop=false;
            boolean bName=false;
            boolean bFirstWay=false;
            boolean bSecondWay=false;
            //Stop stop;
            List<Stop> listFirstWay;
            List<Stop> listSecondWay;

            public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException
            {
                if(qName.equalsIgnoreCase("Stop")) bStop=true;
                if(qName.equalsIgnoreCase("FirstWay"))
                {
                    listFirstWay=new LinkedList<>();
                    bFirstWay=true;
                }
                if(qName.equalsIgnoreCase("SecondWay"))
                {
                    listSecondWay=new LinkedList<>();
                    bSecondWay=true;
                }
                if(qName.equalsIgnoreCase("Name")) bName=true;
            }

            public void characters(char ch[], int start, int length)
            {
                *//**//*if(bStop)
                    bStop=false;*//**//*
                if(bName)
                {
                    String name=new String(ch, start, length);
                    for(Stop stop:operations.getListAllStops())
                    {
                        if(bFirstWay)
                        {
                            listFirstWay.add(stop);
                            break;
                        }
                        if(bSecondWay)
                        {
                            listSecondWay.add(stop);
                            break;
                        }
                    }
                    //bName=false;
                }
                *//**//*if(bFirstWay)
                    bFirstWay=false;
                if(bSecondWay)
                    bSecondWay=false;*//**//*
            }

            public void endElement(String uri, String localName, String qName) throws SAXException
            {
                System.out.println("FirstWay");
                for(Stop stop:listFirstWay)
                    System.out.println(stop.getName());
                System.out.println("SecondWay");
                for(Stop stop:listSecondWay)
                    System.out.println(stop.getName());
            }
        };
        return defaultHandler;
    }

*/
    public boolean openFile(String fileName, int code)
    {
        try
        {
            SAXParserFactory saxParserFactory=SAXParserFactory.newInstance();
            SAXParser saxParser=saxParserFactory.newSAXParser();

            /*DefaultHandler defaultHandler=new DefaultHandler()
            {
                boolean bSNP=false;
                boolean bCast=false;
                boolean bPosition=false;
                boolean bTitles=false;
                boolean bView=false;
                boolean bDischarge=false;
                //Sportsman sportsman;

                public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException
                {
                    if(qName.equalsIgnoreCase("Sportsman"))
                        sportsman=new Sportsman();
                    if(qName.equalsIgnoreCase("SNP"))
                        bSNP=true;
                    if(qName.equalsIgnoreCase("Cast"))
                        bCast=true;
                    if(qName.equalsIgnoreCase("Position"))
                        bPosition=true;
                    if(qName.equalsIgnoreCase("Titles"))
                        bTitles=true;
                    if(qName.equalsIgnoreCase("View"))
                        bView=true;
                    if(qName.equalsIgnoreCase("Discharge"))
                        bDischarge=true;
                }

                public void endElement(String uri, String localName, String qName) throws SAXException
                {
                    if(qName.equalsIgnoreCase("Sportsman"))
                        menu.getOperations().addSportsman(sportsman);
                }

                public void characters(char ch[], int start, int length)
                {
                    if(bSNP)
                    {
                        sportsman.setSNP(new String(ch, start, length));
                        bSNP=false;
                    }
                    if(bCast)
                    {
                        sportsman.setCast(new String(ch, start, length));
                        bCast=false;
                    }
                    if(bPosition)
                    {
                        sportsman.setPosition(new String(ch, start, length));
                        bPosition=false;
                    }
                    if(bTitles)
                    {
                        sportsman.setTitles(Integer.parseInt(new String(ch, start, length)));
                        bTitles=false;
                    }
                    if(bView)
                    {
                        sportsman.setView(new String(ch, start, length));
                        bView=false;
                    }
                    if(bDischarge)
                    {
                        sportsman.setDischarge(new String(ch, start, length));
                        bDischarge=false;
                    }
                }

            };*/
            DefaultHandler defaultHandler=new DefaultHandler();
            switch (code)
            {
                case READLISTOFSTOPS:
                    defaultHandler=getDefaultHandlerForStops(READLISTOFSTOPS);
                    break;
                case READlISTSOFTRANSPORTS:
                    defaultHandler=getDefaultHandlerForTransports();
                    break;
                case READLISTOFSTOPSCURRENTWAY:
                    defaultHandler=getDefaultHandlerForStops(READLISTOFSTOPSCURRENTWAY);
                    break;
                case READTIMETABLE:
                    defaultHandler=getDefaultHandlerForTimetable();
                    break;
            }
            File file=new File(fileName);
            InputStream inputStream=new FileInputStream(file);
            Reader reader=new InputStreamReader(inputStream, "UTF-8");

            InputSource inputSource=new InputSource(reader);
            inputSource.setEncoding("UTF-8");

            saxParser.parse(inputSource, defaultHandler);
            return true;
        }
        catch(Exception e)
        {
            return false;
        }
    }
}