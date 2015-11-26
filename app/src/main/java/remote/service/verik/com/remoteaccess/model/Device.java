package remote.service.verik.com.remoteaccess.model;

import android.support.v4.app.Fragment;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import remote.service.verik.com.remoteaccess.DeviceDetailActivity;

public class Device {


    // extract from manu_product.h (zwavehandler-cli modue)

	public static String DEVICE_TYPE_Zwave_Door_Window_Sensor                   = "01020059"; /**/
	public static String DEVICE_TYPE_Zwave_AEOTEC_Door_Window_Sensor                  = "00860002001D";
	public static String DEVICE_TYPE_Zwave_FIBARO_Door_Window_Sensor                  = "010f07002000";/**/
	public static String DEVICE_TYPE_Zwave_ECOLINK_Door_Window_Sensor                  = "011f00010002";/**/
    public static String DEVICE_TYPE_Zwave_DWZ_Door_Window_Sensor                  = "014a00010002";/**/
	public static String DEVICE_TYPE_Zwave_Sensor_Multilevel_6                  = "008601020064"; /**/
	public static String DEVICE_TYPE_Zwave_Smart_Outlet                         = "49523031";/**/
	public static String DEVICE_TYPE_Zwave_Smart_Dimmer                         = "006349443031";/**/
	public static String DEVICE_TYPE_Zwave_Heavy_Duty_Smart_Switch              = "00860103004E";/**/
	public static String DEVICE_TYPE_Zwave_Led                                  = "00040001";/**/
	public static String DEVICE_TYPE_Zwave_Outlet_Lamp_Module                   = "44503030";/**/
	public static String DEVICE_TYPE_Zwave_Motion_Sensor_Ecolink                = "00010001";/**/
	public static String DEVICE_TYPE_Zwave_Sensor_Multilevel_Gen5               = "00860102004A";/**/
	public static String DEVICE_TYPE_Zwave_Siren_Alarm_Sensor                   = "008601040050";/**/
	public static String DEVICE_TYPE_Zwave_Aeotec_Smartdimmer                   = "008600030019";/**/
	public static String DEVICE_TYPE_Zwave_Smart_Switch                         = "4F503031";/**/
	public static String DEVICE_TYPE_Zwave_Door_Lock                            = "003b63495044";/**/
	public static String DEVICE_TYPE_Zwave_Hue_Bulb                             = "00020002";/**/

    // UPnP (WEMO) device
    public static String DEVICE_TYPE_UPNP_WEMO_INSIGHT = "insight";



//    {"00860103004E",      sizeof(HeavyDutySmartSwitch)/sizeof(config_associate_command),              HeavyDutySmartSwitch},
//    {"00860102004A",      sizeof(SensorMultilevelGen5)/sizeof(config_associate_command),              SensorMultilevelGen5},
//    {"011f00010001",      sizeof(HomeMotionSensor)/sizeof(config_associate_command),                  HomeMotionSensor},
//    {"014a00010001",      sizeof(HomeMotionSensor)/sizeof(config_associate_command),                  HomeMotionSensor},
//    {"008601020064",      sizeof(SensorMultilevelGen6)/sizeof(config_associate_command),              SensorMultilevelGen6},
//    {"014a00010002",      sizeof(DoorWinDowsSensor)/sizeof(config_associate_command),                 DoorWinDowsSensor},
//    {"011f00010002",      sizeof(DoorWinDowsSensor)/sizeof(config_associate_command),                 DoorWinDowsSensor},
//    {"003B63495044",      sizeof(DoorLock)/sizeof(config_associate_command),                          DoorLock},
//    {"008600030019",      sizeof(Smartdimmer)/sizeof(config_associate_command),                       Smartdimmer},
//    {"008601040050",      sizeof(Siren)/sizeof(config_associate_command),                             Siren},
//    {"0060000b0001",      sizeof(FloodSensor)/sizeof(config_associate_command),                       FloodSensor},
//    {"006344503030",      sizeof(Lampdimmeroutlet)/sizeof(config_associate_command),                  Lampdimmeroutlet},
//    {"006349523031",      sizeof(Lampdimmeroutlet)/sizeof(config_associate_command),                  Lampdimmeroutlet},
//    {"006349443031",      sizeof(Lampdimmeroutlet)/sizeof(config_associate_command),                  Lampdimmeroutlet},
//    {"010f08002001",      sizeof(fibaromotion)/sizeof(config_associate_command),                      fibaromotion},
//    {"010f07002000",      sizeof(fibarodoorwindow)/sizeof(config_associate_command),                  fibarodoorwindow},
//    {"00860002001D",      sizeof(aeotecdoorwindowsensor)/sizeof(config_associate_command),            aeotecdoorwindowsensor},
//


    // Product ID
    public static String MFG_ID_NOT_DEFINED_OR_UNDEFINED                = "FFFF"; //Notdefinedorun-defined
    public static String MFG_ID_2B_ELECTRONICS                          = "0028"; //2BElectronics
    public static String MFG_ID_2GIG_TECHNOLOGIES_INC                   = "009B"; //2gigTechnologiesInc.
    public static String MFG_ID_3E_TECHNOLOGIES                         = "002A"; //3eTechnologies
    public static String MFG_ID_A1_COMPONENTS                           = "0022"; //A-1Components
    public static String MFG_ID_ABILIA                                  = "0117"; //Abilia
    public static String MFG_ID_ACT_ADVANCED_CONTROL_TECHNOLOGIES       = "0001"; //ACT-AdvancedControlTechnologies
    public static String MFG_ID_AEON_LABS                               = "0086"; //AEONLabs
    public static String MFG_ID_AIRLINE_MECHANICAL_CO_LTD               = "0111"; //AirlineMechanicalCo.,Ltd.
    public static String MFG_ID_ALARMCOM                                = "0094"; //Alarm.com
    public static String MFG_ID_ASIA_HEADING                            = "0029"; //AsiaHeading
    public static String MFG_ID_ATECH                                   = "002B"; //Atech
    public static String MFG_ID_BALBOA_INSTRUMENTS                      = "0018"; //BalboaInstruments
    public static String MFG_ID_BENEXT                                  = "008A"; //BeNext
    public static String MFG_ID_BESAFER                                 = "002C"; //BeSafer
    public static String MFG_ID_BFT_SPA                                 = "014B"; //BFTS.p.A.
    public static String MFG_ID_BOCA_DEVICES                            = "0023"; //BocaDevices
    public static String MFG_ID_BROADBAND_ENERGY_NETWORKS_INC           = "002D"; //BroadbandEnergyNetworksInc.
    public static String MFG_ID_BULOGICS                                = "0026"; //BuLogics
    public static String MFG_ID_CAMEO_COMMUNICATIONS_INC                = "009C"; //CameoCommunicationsInc.
    public static String MFG_ID_CARRIER                                 = "002E"; //Carrier
    public static String MFG_ID_CASAWORKS                               = "000B"; //CasaWorks
    public static String MFG_ID_CHECKIT_SOLUTIONS_INC                   = "014E"; //Check-ItSolutionsInc.
    public static String MFG_ID_CHROMAGIC_TECHNOLOGIES_CORPORATION      = "0116"; //ChromagicTechnologiesCorporation
    public static String MFG_ID_COLOR_KINETICS_INCORPORATED             = "002F"; //ColorKineticsIncorporated
    public static String MFG_ID_COMPUTIME                               = "0140"; //Computime
    public static String MFG_ID_CONNECTED_OBJECT                        = "011B"; //ConnectedObject
    public static String MFG_ID_CONTROLTHINK_LC                         = "0019"; //ControlThinkLC
    public static String MFG_ID_CONVERGEX_LTD                           = "000F"; //ConvergeXLtd.
    public static String MFG_ID_COOPER_LIGHTING                         = "0079"; //CooperLighting
    public static String MFG_ID_COOPER_WIRING_DEVICES                   = "001A"; //CooperWiringDevices
    public static String MFG_ID_CORNUCOPIA_CORP                         = "012D"; //CornucopiaCorp
    public static String MFG_ID_COVENTIVE_TECHNOLOGIES_INC              = "009D"; //CoventiveTechnologiesInc.
    public static String MFG_ID_CYBERHOUSE                              = "0014"; //Cyberhouse
    public static String MFG_ID_CYBERTAN_TECHNOLOGY_INC                 = "0067"; //CyberTANTechnology,Inc.
    public static String MFG_ID_CYTECH_TECHNOLOGY_PRE_LTD               = "0030"; //CytechTechnologyPreLtd.
    public static String MFG_ID_DANFOSS                                 = "0002"; //Danfoss
    public static String MFG_ID_DEFACONTROLS_BV                         = "013F"; //DefacontrolsBV
    public static String MFG_ID_DESTINY_NETWORKS                        = "0031"; //DestinyNetworks
    public static String MFG_ID_DIEHL_AKO                               = "0103"; //DiehlAKO
    public static String MFG_ID_DIGITAL_5_INC                           = "0032"; //Digital5,Inc.
    public static String MFG_ID_DYNAQUIP_CONTROLS                       = "0132"; //DynaQuipControls
    public static String MFG_ID_ECOLINK                                 = "014A"; //Ecolink
    public static String MFG_ID_EKA_SYSTEMS                             = "0087"; //EkaSystems
    public static String MFG_ID_ELECTRONIC_SOLUTIONS                    = "0033"; //ElectronicSolutions
    public static String MFG_ID_ELGEV_ELECTRONICS_LTD                   = "0034"; //El-GevElectronicsLTD
    public static String MFG_ID_ELK_PRODUCTS_INC                        = "001B"; //ELKProducts,Inc.
    public static String MFG_ID_EMBEDIT_AS                              = "0035"; //EmbeditA/S
    public static String MFG_ID_ENBLINK_CO_LTD                          = "014D"; //EnblinkCo.Ltd
    public static String MFG_ID_EUROTRONICS                             = "0148"; //Eurotronics
    public static String MFG_ID_EVERSPRING                              = "0060"; //Everspring
    public static String MFG_ID_EVOLVE                                  = "0113"; //Evolve
    public static String MFG_ID_EXCEPTIONAL_INNOVATIONS                 = "0036"; //ExceptionalInnovations
    public static String MFG_ID_EXHAUSTO                                = "0004"; //Exhausto
    public static String MFG_ID_EXIGENT_SENSORS                         = "009F"; //ExigentSensors
    public static String MFG_ID_EXPRESS_CONTROLS                        = "001E"; //ExpressControls(formerRyherdVentures)
    public static String MFG_ID_FAKRO                                   = "0085"; //Fakro
    public static String MFG_ID_FIBARGROUP                              = "010F"; //Fibargroup
    public static String MFG_ID_FIBARO                                  = "2000"; //Fibaro
    public static String MFG_ID_FOARD_SYSTEMS                           = "0037"; //FoardSystems
    public static String MFG_ID_FOLLOWGOOD_TECHNOLOGY_COMPANY_LTD       = "0137"; //FollowGoodTechnologyCompanyLtd.
    public static String MFG_ID_FORTREZZ_LLC                            = "0084"; //FortrezZLLC
    public static String MFG_ID_FOXCONN                                 = "011D"; //Foxconn
    public static String MFG_ID_FROSTDALE                               = "0110"; //Frostdale
    public static String MFG_ID_GOOD_WAY_TECHNOLOGY_CO_LTD              = "0068"; //GoodWayTechnologyCo.,Ltd
    public static String MFG_ID_GREENWAVE_REALITY_INC                   = "0099"; //GreenWaveRealityInc.
    public static String MFG_ID_HITECH_AUTOMATION                       = "0017"; //HiTechAutomation
    public static String MFG_ID_HOLTEC_ELECTRONICS_BV                   = "013E"; //HoltecElectronicsBV
    public static String MFG_ID_HOME_AUTOMATED_INC                      = "005B"; //HomeAutomatedInc.
    public static String MFG_ID_HOME_AUTOMATED_LIVING                   = "000D"; //HomeAutomatedLiving
    public static String MFG_ID_HOME_AUTOMATION_EUROPE                  = "009A"; //HomeAutomationEurope
    public static String MFG_ID_HOME_DIRECTOR                           = "0038"; //HomeDirector
    public static String MFG_ID_HOMEMANAGEABLES_INC                     = "0070"; //Homemanageables,Inc.
    public static String MFG_ID_HOMEPRO                                 = "0050"; //Homepro
    public static String MFG_ID_HOMESCENARIO                            = "0162"; //HomeScenario
    public static String MFG_ID_HOMESEER_TECHNOLOGIES                   = "000C"; //HomeSeerTechnologies
    public static String MFG_ID_HONEYWELL                               = "0039"; //Honeywell
    public static String MFG_ID_HORSTMANN_CONTROLS_LIMITED              = "0059"; //HorstmannControlsLimited
    public static String MFG_ID_ICOM_TECHNOLOGY_BV                      = "0011"; //iCOMTechnologyb.v.
    public static String MFG_ID_INGERSOLL_RAND_SCHLAGE                  = "006C"; //IngersollRand(Schlage)
    public static String MFG_ID_INGERSOLL_RAND_ECOLINK                  = "011F"; //IngersollRand(FormerEcolink)
    public static String MFG_ID_INLON_SRL                               = "003A"; //InlonSrl
    public static String MFG_ID_INNOBAND_TECHNOLOGIES_INC               = "0141"; //InnobandTechnologies,Inc
    public static String MFG_ID_INNOVUS                                 = "0077"; //INNOVUS
    public static String MFG_ID_INTEL                                   = "0006"; //Intel
    public static String MFG_ID_INTELLICON                              = "001C"; //IntelliCon
    public static String MFG_ID_INTERMATIC                              = "0005"; //Intermatic
    public static String MFG_ID_INTERNET_DOM                            = "0013"; //InternetDom
    public static String MFG_ID_IR_SEC_SAFETY                           = "003B"; //IRSec.&Safety
    public static String MFG_ID_IWATSU                                  = "0123"; //IWATSU
    public static String MFG_ID_JASCO_PRODUCTS                          = "3031"; //JascoProducts
    public static String MFG_ID_KAMSTRUP_AS                             = "0091"; //KamstrupA/S
    public static String MFG_ID_LAGOTEK_CORPORATION                     = "0051"; //LagotekCorporation
    public static String MFG_ID_LEVITON                                 = "001D"; //Leviton
    public static String MFG_ID_LIFESTYLE_NETWORKS                      = "003C"; //LifestyleNetworks
    public static String MFG_ID_LINEAR_CORP                             = "014F"; //LinearCorp
    public static String MFG_ID_LIVING_STYLE_ENTERPRISES_LTD            = "013A"; //LivingStyleEnterprises,Ltd.
    public static String MFG_ID_LOGITECH                                = "007F"; //Logitech
    public static String MFG_ID_LOUDWATER_TECHNOLOGIES_LLC              = "0025"; //LoudwaterTechnologies,LLC
    public static String MFG_ID_LS_CONTROL                              = "0071"; //LSControl
    public static String MFG_ID_MARMITEK_BV                             = "003D"; //MarmitekBV
    public static String MFG_ID_MARTEC_ACCESS_PRODUCTS                  = "003E"; //MartecAccessProducts
    public static String MFG_ID_MB_TURN_KEY_DESIGN                      = "008F"; //MBTurnKeyDesign
    public static String MFG_ID_MERTEN                                  = "007A"; //Merten
    public static String MFG_ID_MITSUMI                                 = "0112"; //MITSUMI
    public static String MFG_ID_MONSTER_CABLE                           = "007E"; //MonsterCable
    public static String MFG_ID_MOTOROLA                                = "003F"; //Motorola
    public static String MFG_ID_MTC_MAINTRONIC_GERMANY                  = "0083"; //MTCMaintronicGermany
    public static String MFG_ID_NAPCO_SECURITY_TECHNOLOGIES_INC         = "0121"; //NapcoSecurityTechnologies,Inc.
    public static String MFG_ID_NORTHQ                                  = "0096"; //NorthQ
    public static String MFG_ID_NOVAR_ELECTRICAL_DEVICES_AND_SYSTEMS_EDS= "0040"; //NovarElectricalDevicesandSystems(EDS)
    public static String MFG_ID_OMNIMA_LIMITED                          = "0119"; //OmnimaLimited
    public static String MFG_ID_ONSITE_PRO                              = "014C"; //OnSitePro
    public static String MFG_ID_OPENPEAK_INC                            = "0041"; //OpenPeakInc.
    public static String MFG_ID_PHILIO_TECHNOLOGY_CORP                  = "013C"; //PhilioTechnologyCorp
    public static String MFG_ID_POLYCONTROL                             = "010E"; //Poly-control
    public static String MFG_ID_POWERLYNX                               = "0016"; //PowerLynx
    public static String MFG_ID_PRAGMATIC_CONSULTING_INC                = "0042"; //PragmaticConsultingInc.
    public static String MFG_ID_PULSE_TECHNOLOGIES_ASPALIS              = "005D"; //PulseTechnologies(Aspalis)
    public static String MFG_ID_QEES                                    = "0095"; //Qees
    public static String MFG_ID_QUBY                                    = "0130"; //Quby
    public static String MFG_ID_RADIO_THERMOSTAT_COMPANY_OF_AMERICA_RTC = "0098"; //RadioThermostatCompanyofAmerica(RTC)
    public static String MFG_ID_RARITAN                                 = "008E"; //Raritan
    public static String MFG_ID_REITZGROUPDE                            = "0064"; //Reitz-Group.de
    public static String MFG_ID_REMOTEC_TECHNOLOGY_LTD                  = "5254"; //RemotecTechnologyLtd
    public static String MFG_ID_RESIDENTIAL_CONTROL_SYSTEMS_INC_RCS     = "0010"; //ResidentialControlSystems,Inc.(RCS)
    public static String MFG_ID_RIMPORT_LTD                             = "0147"; //R-importLtd.
    public static String MFG_ID_RS_SCENE_AUTOMATION                     = "0065"; //RSSceneAutomation
    public static String MFG_ID_SAECO                                   = "0139"; //Saeco
    public static String MFG_ID_SAN_SHIH_ELECTRICAL_ENTERPRISE_CO_LTD   = "0093"; //SanShihElectricalEnterpriseCo.,Ltd.
    public static String MFG_ID_SANAV                                   = "012C"; //SANAV
    public static String MFG_ID_SCIENTIA_TECHNOLOGIES_INC               = "001F"; //ScientiaTechnologies,Inc.
    public static String MFG_ID_SECURE_WIRELESS                         = "011E"; //SecureWireless
    public static String MFG_ID_SELUXIT                                 = "0069"; //Seluxit
    public static String MFG_ID_SENMATIC_AS                             = "0043"; //SenmaticA/S
    public static String MFG_ID_SEQUOIA_TECHNOLOGY_LTD                  = "0044"; //SequoiaTechnologyLTD
    public static String MFG_ID_SIGMA_DESIGNS                           = "0000"; //SigmaDesigns
    public static String MFG_ID_SINE_WIRELESS                           = "0045"; //SineWireless
    public static String MFG_ID_SMART_PRODUCTS_INC                      = "0046"; //SmartProducts,Inc.
    public static String MFG_ID_SMK_MANUFACTURING_INC                   = "0102"; //SMKManufacturingInc.
    public static String MFG_ID_SOMFY                                   = "0047"; //Somfy
    public static String MFG_ID_SYLVANIA                                = "0009"; //Sylvania
    public static String MFG_ID_SYSTECH_CORPORATION                     = "0136"; //SystechCorporation
    public static String MFG_ID_TEAM_PRECISION_PCL                      = "0089"; //TeamPrecisionPCL
    public static String MFG_ID_TECHNIKU                                = "000A"; //Techniku
    public static String MFG_ID_TELL_IT_ONLINE                          = "0012"; //TellItOnline
    public static String MFG_ID_TELSEY                                  = "0048"; //Telsey
    public static String MFG_ID_THERE_CORPORATION                       = "010C"; //ThereCorporation
    public static String MFG_ID_TKB_HOME                                = "0118"; //TKBHome
    public static String MFG_ID_TKH_GROUP_EMINENT                       = "011C"; //TKHGroup/Eminent
    public static String MFG_ID_TRANE_CORPORATION                       = "008B"; //TraneCorporation
    public static String MFG_ID_TRICKLESTAR                             = "0066"; //TrickleStar
    public static String MFG_ID_TRICKLESTAR_LTD_EMPOWER_CONTROLS_LTD    = "006B"; //TricklestarLtd.(formerEmpowerControlsLtd.)
    public static String MFG_ID_TRIDIUM                                 = "0055"; //Tridium
    public static String MFG_ID_TWISTHINK                               = "0049"; //Twisthink
    public static String MFG_ID_UNIVERSAL_ELECTRONICS_INC               = "0020"; //UniversalElectronicsInc.
    public static String MFG_ID_VDA                                     = "010A"; //VDA
    public static String MFG_ID_VERO_DUCO                               = "0080"; //VeroDuco
    public static String MFG_ID_VIEWSONIC_CORPORATION                   = "005E"; //ViewSonicCorporation
    public static String MFG_ID_VIMAR_CRS                               = "0007"; //VimarCRS
    public static String MFG_ID_VISION_SECURITY                         = "0109"; //VisionSecurity
    public static String MFG_ID_VISUALIZE                               = "004A"; //Visualize
    public static String MFG_ID_WATT_STOPPER                            = "004B"; //WattStopper
    public static String MFG_ID_WAYNE_DALTON                            = "0008"; //WayneDalton
    public static String MFG_ID_WENZHOU_MTLC_ELECTRIC_APPLIANCES_COLTD  = "011A"; //WenzhouMTLCElectricAppliancesCo.,Ltd.
    public static String MFG_ID_WIDOM                                   = "0149"; //wiDom
    public static String MFG_ID_WILSHINE_HOLDING_CO_LTD                 = "012D"; //WilshineHoldingCo.,Ltd
    public static String MFG_ID_WINTOP                                  = "0097"; //Wintop
    public static String MFG_ID_WOODWARD_LABS                           = "004C"; //WoodwardLabs
    public static String MFG_ID_WRAP                                    = "0003"; //Wrap
    public static String MFG_ID_WUHAN_NWD_TECHNOLOGY_CO_LTD             = "012E"; //WuhanNWDTechnologyCo.,Ltd.
    public static String MFG_ID_XANBOO                                  = "004D"; //Xanboo
    public static String MFG_ID_ZDATA_LLC                               = "004E"; //Zdata,LLC.
    public static String MFG_ID_ZIPATO                                  = "0131"; //Zipato
    public static String MFG_ID_ZONOFF                                  = "0120"; //Zonoff
    public static String MFG_ID_ZWAVE_TECHNOLOGIA                       = "004F"; //Z-WaveTechnologia
    public static String MFG_ID_ZWAVEME                                 = "0115"; //Z-Wave.Me
    public static String MFG_ID_ZYKRONIX                                = "0021"; //Zykronix
    public static String MFG_ID_ZYXEL                                   = "0135"; //ZyXEL
    

    final public static String DoorWindowSensor = "01020059";
    final public static String DoorWindowSensor1 = "01020059";
    final public static String DoorWindowSensor2 = "01020059";
    final public static String DoorWindowSensor3 = "01020059";



    // UPnP RealName
    // ZWAVE ID
    public String id;

    //
    private String friendlyName;

    private boolean turnOn;
    private boolean available;
    public String type;
    private String capabilityID;
    public String serialNumber;


    // For rendering the UI

    public List<DeviceFragment> listFragment;

    public Device(){

    }

    public Device(String id, String friendlyName, boolean turnOn, boolean available, String device_type) {
        this.id = id;
        this.friendlyName = friendlyName;
        this.turnOn = turnOn;
        this.available = available;
        type = "zwave";
        capabilityID = "";

        if (device_type.contentEquals("zigbee"))
            type = "zigbee";
        else if (device_type.contentEquals("upnp"))
            type = "upnp";

        listFragment = new ArrayList<DeviceFragment>();


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
        return friendlyName;
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

    public void setName(String friendlyName) {
        this.friendlyName = friendlyName;
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
                ", friendlyName='" + friendlyName + '\'' +
                ", turnOn=" + turnOn +
                ", available=" + available +
                '}';
    }

    public static String getDeviceTypeFromSerial(String serial)
    {
        return serial.substring(serial.length() - 12, serial.length());
    }

    public static String getManuFromSerial(String serial)
    {
        return serial.substring(serial.length() - 16, serial.length() - 13);
    }


    public void showDetailActivity(View v) {


        DeviceDetailActivity.device = this;

        Intent intent1 = new Intent(v.getContext(), DeviceDetailActivity.class);

        v.getContext().startActivity(intent1);

    }

    public void Update(String property)
    {

    }




    // methods regarding to display
    public int getFragmentCount()
    {

        return listFragment.size();

    }

    public Fragment getFragment(int index)
    {
        if (index < listFragment.size())
            return listFragment.get(index);
        else
            return null;

    }


    ///
    public CharSequence getFragmentTitle(int position)
    {
        if (position < listFragment.size())
            return listFragment.get(position).getFragmentTitle();

        return null;

    }




}





