package si.fri.rso.projekt.deliverer.models;

public class Deliverer {

    private int delivererID;
    private String firstName;
    private String lastName;

    public Deliverer(int delivererID, String firstName, String lastName) {
        this.delivererID = delivererID;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public int getDelivererID() {
        return delivererID;
    }

    public void setDelivererID(int delivererID) {
        this.delivererID = delivererID;
    }


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
