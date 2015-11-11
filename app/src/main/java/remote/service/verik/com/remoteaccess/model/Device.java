package remote.service.verik.com.remoteaccess.model;

/**
 * Created by congngale on 7/16/15.
 */
public class Device {

//
//    Manu_product_type_t product_type_id_t[] = {
//            {.product_type_name = "Z-wave Door/Windown Sensor",                 .product_type_id_number = 0x01020059}, /**/
//        {.product_type_name = "Z-wave Door/Windown Sensor",                 .product_type_id_number = 0x0002001D},
//        {.product_type_name = "Z-wave Sensor Multilevel 6",                 .product_type_id_number = 0x01020064}, /**/
//        {.product_type_name = "Z-wave Smart Outlet",                        .product_type_id_number = 0x49523031},/**/
//        {.product_type_name = "Z-wave Smart Dimmer",                        .product_type_id_number = 0x49443031},/**/
//        {.product_type_name = "Z-wave Heavy Duty Smart Switch",             .product_type_id_number = 0x0103004E},/**/
//        {.product_type_name = "Z-wave Led",                                 .product_type_id_number = 0x00040001},/**/
//        {.product_type_name = "Z-wave Outlet Lamp Module",                  .product_type_id_number = 0x44503030},/**/
//        {.product_type_name = "Z-wave Motion Sensor (Ecolink)",             .product_type_id_number = 0x00010001},/**/
//        {.product_type_name = "Z-wave Sensor Multilevel Gen5",              .product_type_id_number = 0x0102004A},/**/
//        {.product_type_name = "Z-wave Siren Alarm Sensor",                  .product_type_id_number = 0x01040050},/**/
//        {.product_type_name = "Z-wave Aeotec Smartdimmer",                  .product_type_id_number = 0x00030019},/**/
//        {.product_type_name = "Z-wave Door/Windown Fibaro Systems Sensor",  .product_type_id_number = 0x07002000},/**/
//        {.product_type_name = "Z-wave Door/Windown Sensor (DWZ)",           .product_type_id_number = 0x00010002},/**/
//        {.product_type_name = "Z-wave Smart Switch",                        .product_type_id_number = 0x4F503031},/**/
//        {.product_type_name = "Z-wave Door Lock",                           .product_type_id_number = 0x63495044},/**/
//        {.product_type_name = "Z-wave Hue Bulb",                            .product_type_id_number = 0x00020002},/**/
//        };
//

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
