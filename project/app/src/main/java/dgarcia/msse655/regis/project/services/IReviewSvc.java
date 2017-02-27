package dgarcia.msse655.regis.project.services;
import java.util.*;

import dgarcia.msse655.regis.project.domain.Review;

/**
 * Created by david on 2/26/2017.
 */

public interface IReviewSvc {

    public Review create(Review review);
    public List<Review> retrieveAllReviews();
    public Review update(Review review);
    public Review delete(Review review);

}// END OF IReviewSvc
