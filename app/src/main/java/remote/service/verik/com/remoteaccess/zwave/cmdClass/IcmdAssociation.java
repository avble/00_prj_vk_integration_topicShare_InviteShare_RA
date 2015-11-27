package remote.service.verik.com.remoteaccess.zwave.cmdClass;

/**
 * Created by huyle on 11/26/15.
 */
public interface IcmdAssociation {

    int associationSetNode(String id, String groupID, String nodeID);
    int associationGetNode(String id, String groupID);
    int associationRemoveNode(String id, String groupID, String nodeID);
}
