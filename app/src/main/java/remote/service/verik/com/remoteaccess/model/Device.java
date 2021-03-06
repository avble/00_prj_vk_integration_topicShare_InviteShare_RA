package remote.service.verik.com.remoteaccess.model;

/**
 * Created by congngale on 7/16/15.
 */
public class Device {

    private int id;
    private String name;
    private boolean turnOn;
    private boolean available;

    public Device(){

    }

    public Device(int id, String name, boolean turnOn, boolean available) {
        this.id = id;
        this.name = name;
        this.turnOn = turnOn;
        this.available = available;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isTurnOn() {
        return turnOn;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTurnOn(boolean turnOn) {
        this.turnOn = turnOn;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    @Override
    public String toString() {
        return "Device{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", turnOn=" + turnOn +
                ", available=" + available +
                '}';
    }
}
