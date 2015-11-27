package remote.service.verik.com.remoteaccess.model;

/**
 * Created by huyle on 11/26/15.
 */

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.text.Html;
import android.text.Spanned;
import android.widget.ArrayAdapter;

import remote.service.verik.com.remoteaccess.R;

/**
 * This fragment displays the history information for a client
 *
 */
public class HistoryFragment extends ListFragment {

    String clientHandle = null;
    public ArrayAdapter<Spanned> arrayAdapter = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Pull history information out of bundle

//        clientHandle = getArguments().getString("handle");


        //Initialise the arrayAdapter, view and add data
        arrayAdapter = new ArrayAdapter<Spanned>(getActivity(), R.layout.list_view_text_view_history);



        setListAdapter(arrayAdapter);

    }

    /**
     * Updates the data displayed to match the current history
     */
    public void refresh() {
        if (arrayAdapter != null) {
            arrayAdapter.clear();

            arrayAdapter.notifyDataSetChanged();
        }

    }

}
