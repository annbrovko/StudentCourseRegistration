package sample.models;

public class CboxResource {
    private int ID;
    private String name;

    // create a CboxResource with id and name attributes
    public CboxResource(int id, String name) {
        this.ID = id;
        this.name = name;
    }

    // getters for the ids and names in the comboboxes
    public int getID() { return this.ID; }
    public String getName() { return this.name; }
}
