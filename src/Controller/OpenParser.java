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
            String stopName="";

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
                    stopName=new String(ch, start, length);
                    bName=false;
                }
            }

            public void endElement(String uri, String localName, String qName) throws SAXException
            {
                if(qName.equalsIgnoreCase("Stop"))
                {
                    if(code==READLISTOFSTOPS)
                        operations.listAllStops.add(new Stop(stopName));
                    if(code==READLISTOFSTOPSCURRENTWAY)
                    {
                        Stop currentStop=new Stop("");
                        for(Stop stop:operations.listAllStops)
                            if (stop.getName().equals(stopName))
                                currentStop = stop;
                        operations.currentTransport.getRout().getListStops(operations.wayType).add(currentStop);
                    }
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

    public boolean openFile(String fileName, int code)
    {
        try
        {
            SAXParserFactory saxParserFactory=SAXParserFactory.newInstance();
            SAXParser saxParser=saxParserFactory.newSAXParser();

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