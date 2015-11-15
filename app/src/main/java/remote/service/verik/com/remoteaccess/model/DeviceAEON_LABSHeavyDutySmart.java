package remote.service.verik.com.remoteaccess.model;

/**
 * Created by huyle on 11/12/15.
 */
public class DeviceAEON_LABSHeavyDutySmart extends  Device {
    final public static String klass_SENSOR_MULTILEVEL = "SENSOR_MULTILEVEL";
    final public static String type_SENSOR_MULTILEVEL_TEMP = "TEMP";
    final public static String type_SENSOR_MULTILEVEL_HUMI = "HUMI";

    final public static String klass_METER = "METER";
    final public static String type_METER_POWER = "POWER";

    public DeviceAEON_LABSHeavyDutySmart(String id, String name, boolean turnOn, boolean available, String device_type)
    {
        super(id, name, turnOn, available, device_type);
    }

}
