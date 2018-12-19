package si.fri.rso.projekt.deliverer.models;

public class Deliverer {

    private int delivererID;
    private int queueID;
    private String name;

    public Deliverer(int delivererID, int queueID, String name) {
        this.delivererID = delivererID;
        this.queueID = queueID;
        this.name = name;
    }

    public int getDelivererID() {
        return delivererID;
    }

    public void setDelivererID(int delivererID) {
        this.delivererID = delivererID;
    }

    public int getQueueID() {
        return queueID;
    }

    public void setQueueID(int queueID) {
        this.queueID = queueID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
