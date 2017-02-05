package davidgarcia.regis.blank;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A placeholder fragment containing a simple view.
 */
public class ThingDetailActivityFragment extends Fragment {

    public ThingDetailActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Set root view
        View rootView =  inflater.inflate(R.layout.fragment_thing_detail, container, false);

        // Grab intent. We will create the intent in a second, but know in the future you will have
        // created it and passed it to the activity when you start it. Then your fragment will call
        // the activity and ask for the intent.
        Intent intent = getActivity().getIntent();

        // Grab the string extra called "item" from the intent...again your future self knows what this is.
        String item = intent.getStringExtra("item");

        //Find the TextView you created and set the text using the string we got from the intent
        TextView thingDetailTextView = (TextView) rootView.findViewById((R.id.detail_text));
        thingDetailTextView.setText(item);

        return rootView;
    }
}// Fragment Class End
