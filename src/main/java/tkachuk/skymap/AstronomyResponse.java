package tkachuk.skymap;

public class AstronomyResponse
{
    public Data data;

    public class Data
    {
        public Table table;
    }

    public class Table
    {
        public Row[] rows;
    }

    public class Row
    {
        public Cell[] cells;
    }

    public class Cell
    {
        public String name;
        public Position position;
    }

    public class Position
    {
        public Horizontal horizontal;
    }

    public class Horizontal
    {
        public Value altitude;
        public Value azimuth;
    }

    public class Value
    {
        public double degrees;
    }
}
