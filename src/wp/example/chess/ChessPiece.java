package wp.example.chess;

public class ChessPiece {
    public static int count = 1;

    public int id;
    public String type;
    public String name;
    public String color;


    public ChessPiece(String type, String name, String color) {
        this.id = count;
        this.type = type;
        this.name = name;
        this.color = color;
        count++;
    }
    public int getId() { return id; }

    public String getType() { return type; }

    public String getName() { return name; }

    public String getColor() { return color; }
}

