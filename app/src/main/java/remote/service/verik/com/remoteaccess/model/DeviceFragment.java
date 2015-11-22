package remote.service.verik.com.remoteaccess.model;

import android.support.v4.app.Fragment;

/**
 * Created by huyle on 11/21/15.
 */
public class DeviceFragment extends Fragment {

    public String fragmentTitle;

    public DeviceFragment()
    {
        super();
    }

    public void setTitle(String title)
    {
        fragmentTitle = title;

    }

    public String getFragmentTitle()
    {
        return fragmentTitle;
    }


}
