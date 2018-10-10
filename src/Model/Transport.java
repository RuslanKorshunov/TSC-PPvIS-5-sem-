package Model;

import Model.Rout;
import Model.TransportType;

public class Transport
{
    /*private static final int FIRSTWAY = 1;
    private static final int SECONDWAY = 2;*/
    String number;
    Rout rout;
    TransportType transportType;

    public Transport()
    {
        number="";
        rout=new Rout();
        transportType=TransportType.NULL;
    }

    public Transport(String number, Rout rout, TransportType transportType)
    {
        this.number=number;
        this.rout=rout;
        this.transportType=transportType;
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

    public Rout getRout()
    {
        return rout;
    }

    public TransportType getTransportType()
    {
        return transportType;
    }
}