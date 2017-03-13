package dgarcia.msse655.regis.project.adapters;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringDef;
import android.widget.ArrayAdapter;
import java.text.DateFormat;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import dgarcia.msse655.regis.project.R;
import dgarcia.msse655.regis.project.domain.Review;

/**
 * Created by David_Garcia on 3/7/2017.
 * Adapter for Review objects onto views for lists
 * Example: http://www.learn-android.com/2011/11/22/lots-of-lists-custom-adapter/3/
 */
public final class ReviewAdapter extends ArrayAdapter<Review> {

    private final int reviewItemLayoutResource;

    public ReviewAdapter(final Context context, final int reviewItemLayoutResource) {
        super(context, 0);
        this.reviewItemLayoutResource = reviewItemLayoutResource;
    }

    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {

        // We need to get the best view (re-used if possible) and then
        // retrieve its corresponding ViewHolder, which optimizes lookup efficiency
        final View view = getWorkingView(convertView);
        final ViewHolder viewHolder = getViewHolder(view);
        final Review review = getItem(position);

        //Set the title
        viewHolder.titleView.setText(review.getReviewTitle());

        //Setting subtitle view requires some formatting
        final String subTitle = String.format("%s - %s", review.getReviewDate(), review.getReviewParagraph());
        viewHolder.subTitleView.setText(subTitle);

        // Set the image icon
        viewHolder.imageView.setImageResource(review.getReviewImageID());

        return view;
    }

    private View getWorkingView(final View convertView) {
        // The workingView is basically just the convertView re-used if possible
        // or inflated new if not possible
        View workingView = null;

        if(null == convertView) {
            final Context context = getContext();
            final LayoutInflater inflater = (LayoutInflater)context.getSystemService
                    (Context.LAYOUT_INFLATER_SERVICE);

            workingView = inflater.inflate(reviewItemLayoutResource, null);
        } else {
            workingView = convertView;
        }

        return workingView;
    }

    private ViewHolder getViewHolder(final View workingView) {
        // The viewHolder allows us to avoid re-looking up view references
        // Since views are recycled, these references will never change
        final Object tag = workingView.getTag();
        ViewHolder viewHolder = null;

        if(null == tag || !(tag instanceof ViewHolder)) {
            viewHolder = new ViewHolder();

            viewHolder.titleView = (TextView) workingView.findViewById(R.id.review_title);
            viewHolder.subTitleView = (TextView) workingView.findViewById(R.id.review_subtitle);
            viewHolder.imageView = (ImageView) workingView.findViewById(R.id.review_icon);

            workingView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) tag;
        }

        return viewHolder;
    }

    /**
     * ViewHolder allows us to avoid re-looking up view references
     * Since views are recycled, these references will never change
     */
    private static class ViewHolder {
        public TextView titleView;
        public TextView subTitleView;
        public ImageView imageView;
    }

}
