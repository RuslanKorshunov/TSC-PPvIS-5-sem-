package Model;

public class Transport
{
    String number;
    Rout rout;

    public Transport() {}

    public Transport(String number, Rout rout)
    {
        this.number=number;
        this.rout=rout;
    }

    public String getNumber()
    {
        return number;
    }

    public Rout getRout()
    {
        return rout;
    }
}