package dgarcia.msse655.regis.project.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import dgarcia.msse655.regis.project.R;


public class ReviewIconSpinnerAdapter extends BaseAdapter {

    private Context context;
    private int reviewIcons[];
    private LayoutInflater inflater;
    //String iconNames; //Stores icon names

    public ReviewIconSpinnerAdapter(Context applicationContext, int[] icons){ //would add String[] iconNames argument for names
        this.context = applicationContext;
        this.reviewIcons = icons;
        //this.iconNames = iconNames;
        inflater = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return reviewIcons.length;
    }

    @Override
    public Object getItem(int i) {
        return reviewIcons[i];
    }

    @Override
    public long getItemId(int i) {
        return reviewIcons[i];
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup){
        view = inflater.inflate(R.layout.custom_spinner_layout, null);
        ImageView icon = (ImageView) view.findViewById(R.id.customSpinner_imageView);
        //TextView iconNames = (TextView) view.findViewById(R.id.customSpinner_textView);
        icon.setImageResource(reviewIcons[i]);
        //iconNames.setText(iconNames[i]);
        return view;
    }

}
