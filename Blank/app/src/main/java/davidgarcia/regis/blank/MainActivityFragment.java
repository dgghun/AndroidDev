package davidgarcia.regis.blank;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    private List<String> thingList = null;
    private ListView thingsListView = null;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Store the inflated view in rootView, will be returned on last line of onCreateView
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        // Initialize thingsList with values from strings.xml
        thingList = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.things_list)));

        // Initialize thinsListView to the ListView from rootView (our Fragment)
        thingsListView = (ListView) rootView.findViewById(R.id.list_of_things);

        // Create the ArrayAdapter with a type of string, this of course could be some other
        // type. Since we are using strings we want to use simple_list_item_1
        // that expects a single string, we give it a type of string
        ArrayAdapter<String> thingsArrayAdapter = new ArrayAdapter<String>(rootView.getContext(), android.R.layout.simple_list_item_1, thingList);

        // Set the ArrayAdapter on the ListView
        thingsListView.setAdapter(thingsArrayAdapter);

        // Create a click/tap handler for when someone taps something in the list
        thingsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // Get the item from the list
                String item = (String) thingsListView.getItemAtPosition(position);
                //For now we will toast it and show what was selected
                //Toast.makeText(view.getContext(), "Pos"+position+" ListItem:"+item, Toast.LENGTH_SHORT).show();

                // Put intent instead of toast to start new activity
                Intent intent = new Intent(view.getContext(), ThingDetailActivity.class);
                // Put the sting extra in...
                intent.putExtra("item", item);
                // Start activity
                startActivity(intent);


            }
        });
        // return the rootView from onCreateView
        return rootView;
    }
}// Fragment Class end
