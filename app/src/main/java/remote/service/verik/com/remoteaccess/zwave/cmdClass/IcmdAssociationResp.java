package remote.service.verik.com.remoteaccess.zwave.cmdClass;

/**
 * Created by huyle on 11/26/15.
 */
public interface IcmdAssociationResp {

    void onAssociationGetResp(String groupIdentifier, String maxNode, String nodeFlow);


}
