package remote.service.verik.com.remoteaccess.model;

/**
 * Created by congngale on 7/16/15.
 */
public class Device {

    private String id;
    private String name;
    private boolean turnOn;
    private boolean available;
    public String type;
    private String capabilityID;

    public Device(){

    }

    public Device(String id, String name, boolean turnOn, boolean available, String device_type) {
        this.id = id;
        this.name = name;
        this.turnOn = turnOn;
        this.available = available;
        type = "zwave";
        capabilityID = "";

        if (device_type.contentEquals("zigbee"))
            type = "zigbee";
        else if (device_type.contentEquals("upnp"))
            type = "upnp";


    }

    public void setCapabilityID(String capability_ID)
    {
        capabilityID = capability_ID;
    }

    public String getCapabilityID()
    {
        return capabilityID;
    }

    public String getId() {
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

    public void setId(String id) {
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
