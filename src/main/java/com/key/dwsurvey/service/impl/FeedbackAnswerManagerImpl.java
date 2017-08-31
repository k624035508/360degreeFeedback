package com.key.dwsurvey.service.impl;

import com.key.common.base.entity.User;
import com.key.common.plugs.page.Page;
import com.key.common.plugs.page.PropertyFilter;
import com.key.common.service.BaseServiceImpl;
import com.key.common.utils.excel.XLSExportUtil;
import com.key.common.utils.parsehtml.HtmlUtil;
import com.key.dwsurvey.dao.FeedbackAnswerDao;
import com.key.dwsurvey.entity.*;
import com.key.dwsurvey.service.*;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by jielao on 2017/8/2.
 */

@Service
public class FeedbackAnswerManagerImpl extends BaseServiceImpl<FeedbackAnswer, String> implements FeedbackAnswerManager {

    @Autowired
    private FeedbackAnswerDao feedbackAnswerDao;

    @Autowired
    private FeedbackReviewManager feedbackReviewManager;

    @Autowired
    private QuestionManager questionManager;

    @Autowired
    private UserManager userManager;

    @Autowired
    private AnFeedbackItemManager anFeedbackItemManager;

    @Autowired
    private FeedbackDimensionManager feedbackDimensionManager;


    @Override
    public void setBaseDao(){ this.baseDao = feedbackAnswerDao; }

    @Override
    public void saveAnswer(FeedbackAnswer feedbackAnswer, Map<String, Map<String, Object>> quMaps){
        feedbackAnswerDao.saveAnswer(feedbackAnswer, quMaps);
    }

    @Override
    public List<FeedbackAnswer> findAnswerListByReview(String reviewId){
        Page<FeedbackAnswer> page = new Page<FeedbackAnswer>();
        List<Criterion> criterionList = new ArrayList<>();
        criterionList.add(Restrictions.eq("reviewId", reviewId));
        page = feedbackAnswerDao.findPageList(page, criterionList);
        return page.getResult();
    }

    public List<FeedbackAnswer> findAnswerByAnswerUser(String reviewId, String answerUser){
        Page<FeedbackAnswer> page = new Page<FeedbackAnswer>();
        List<Criterion> criterionList = new ArrayList<>();
        criterionList.add(Restrictions.eq("reviewId", reviewId));
        criterionList.add(Restrictions.eq("answerUser", answerUser));
        page = feedbackAnswerDao.findPageList(page, criterionList);
        return page.getResult();
    }

    public List<FeedbackAnswer> findHaveReviewUser(String reviewId, String answerUser, String ownerId){
        Page<FeedbackAnswer> page = new Page<FeedbackAnswer>();
        List<Criterion> criterionList = new ArrayList<>();
        criterionList.add(Restrictions.eq("reviewId", reviewId));
        criterionList.add(Restrictions.eq("answerUser", answerUser));
        criterionList.add(Restrictions.eq("ownerId", ownerId));
        page = feedbackAnswerDao.findPageList(page, criterionList);
        return page.getResult();
    }

    public List<FeedbackAnswer> findReviewOwnerAndDimension(String reviewId, String ownerId, String dimensionId){
        Page<FeedbackAnswer> page = new Page<FeedbackAnswer>();
        List<Criterion> criterionList = new ArrayList<>();
        criterionList.add(Restrictions.eq("reviewId", reviewId));
        criterionList.add(Restrictions.eq("ownerId", ownerId));
        criterionList.add(Restrictions.eq("dimensionId", dimensionId));
        page = feedbackAnswerDao.findPageList(page, criterionList);
        return page.getResult();
    }

    public String exportXLS(String reviewId, String examineeId, String savePath) throws Exception{
        String basepath = reviewId + "";
        String urlPath = "/file/" + basepath + "/";// 下载所用的地址
        String path = urlPath.replace("/", File.separator);// 文件系统路径
        // File.separator +
        // "file" +
        // File.separator+basepath
        // + File.separator;
        savePath = savePath + path;
        File file = new File(savePath);
        if (!file.exists())
            file.mkdirs();

        FeedbackReview feedbackReview = feedbackReviewManager.get(reviewId);
        User examinee = userManager.get(examineeId);
        String examineeName = examinee.getName();
        String feedbackReviewName = feedbackReview.getName();
        String fileName = examineeName + "_" + feedbackReviewName + "_exportReview.xls";
        XLSExportUtil exportUtil = new XLSExportUtil(fileName, savePath);

        List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
        filters.add(new PropertyFilter("EQS_reviewId", reviewId));
        filters.add(new PropertyFilter("EQS_ownerId", examineeId));
        Page<FeedbackAnswer> page = new Page<FeedbackAnswer>();
        page.setPageSize(100);
        try{
            page = findPage(page, filters);
            int totalPage = page.getTotalPage();
            List<FeedbackAnswer> answers = page.getResult();

            //得到题标题
            List<Question> questions = questionManager.findDetails(feedbackReview.getFeedbackDemoId(), "2");
            // 生成表格的列标题
            exportXLSTitle(exportUtil, questions);

            for(int i = 1; i <= totalPage; i++){
                for (int j = 0; j < answers.size(); j++) {
                    FeedbackAnswer feedbackAnswer = answers.get(j);
                    // 得到详细结果
                    exportUtil.createRow((j + 1) + ((i - 1) * 100));
                    exportXLSRow(exportUtil, questions, feedbackAnswer);
                }
                page.setPageNo(i);
                page = findPage(page, filters);
                answers = page.getResult();
            }
            exportUtil.exportXLS();
        }catch (Exception e) {
            e.printStackTrace();
        }
        fileName = URLEncoder.encode(fileName, "utf-8");
        return urlPath + fileName;
    }

    public void exportXLSTitle(XLSExportUtil exportUtil, List<Question> questions){
        exportUtil.createRow(0);
        int cellIndex = 0;
        exportUtil.setCell(cellIndex++,  "评分人");
        exportUtil.setCell(cellIndex++,  "评分维度");

        int quNum=0;
        for (Question question : questions) {
            List<DegreeFeedbackItem> degreeFeedbackItems = question.getDegreeFeedbackItems();
            for(DegreeFeedbackItem degreeFeedbackItem: degreeFeedbackItems) {
                for (DegreeFeedbackItemItem degreeFeedbackItemItem : degreeFeedbackItem.getItemList()) {
                    String optionName = degreeFeedbackItemItem.getName();
                    optionName = HtmlUtil.removeTagFromText(optionName);
                    exportUtil.setCell(cellIndex++, optionName);
                }
            }
        }
    }

    public void exportXLSRow(XLSExportUtil exportUtil, List<Question> questions, FeedbackAnswer feedbackAnswer){
        int cellIndex = 0;
        User answerUser = userManager.get(feedbackAnswer.getAnswerUser());
        exportUtil.setCell(cellIndex++, answerUser.getName());
        exportUtil.setCell(cellIndex++, feedbackDimensionManager.get(feedbackAnswer.getDimensionId()).getName());
        for (Question question: questions){
            List<DegreeFeedbackItem> degreeFeedbackItems = question.getDegreeFeedbackItems();
            for(DegreeFeedbackItem degreeFeedbackItem: degreeFeedbackItems) {
                for (DegreeFeedbackItemItem degreeFeedbackItemItem : degreeFeedbackItem.getItemList()) {
                    AnFeedbackItem anFeedbackItem = anFeedbackItemManager.findFeedbackItemAnswer(feedbackAnswer.getId(), degreeFeedbackItemItem.getId()).get(0);
                    exportUtil.setCell(cellIndex++, anFeedbackItem.getAnswerScore());
                }
            }
        }
    }

}
