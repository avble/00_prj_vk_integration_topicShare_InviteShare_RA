package remote.service.verik.com.remoteaccess.zwave.cmdClass;

/**
 * Created by huyle on 11/26/15.
 */
public interface IcmdAssociation {

    int setNode(String id, String groupID, String nodeID);
    int getNode(String id, String groupID);
    int associationNodeRemove(String id, String groupID, String nodeID);
}
