package sample.models;

public class CboxResource {
    private int ID;
    private String name;

    public CboxResource(int id, String name) {
        this.ID = id;
        this.name = name;
    }

    public int getID() { return this.ID; }
    public String getName() { return this.name; }
}
