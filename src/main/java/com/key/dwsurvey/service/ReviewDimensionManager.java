package com.key.dwsurvey.service;

import com.key.common.service.BaseService;
import com.key.dwsurvey.entity.ReviewDimension;

import java.util.List;

/**
 * Created by jielao on 2017/8/8.
 */
public interface ReviewDimensionManager extends BaseService<ReviewDimension, String> {

    public List<ReviewDimension> findDetails(String reviewId);

    public ReviewDimension findByReview(String reviewId, String dimensionId);

    public List<ReviewDimension> findByInvestigate(String userId, String reviewId);

    public List<ReviewDimension> findByInvestigateList(String userId);

    public List<ReviewDimension> findDetailsByOther(String reviewId, String examinee);

    public List<ReviewDimension> findDetailsByHaveOwn(String reviewId, String examinee);

    public ReviewDimension findDetailsByAll(String examinee, String investigate, String reviewId);

}
