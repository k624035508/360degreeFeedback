package com.key.dwsurvey.action;

import com.key.common.base.action.CrudActionSupport;
import com.key.common.base.entity.User;
import com.key.common.plugs.page.Page;
import com.key.common.plugs.page.PropertyFilter;
import com.key.common.utils.StringUtils;
import com.key.common.utils.web.Struts2Utils;
import com.key.dwsurvey.entity.*;
import com.key.dwsurvey.service.*;
import net.sf.json.JSONObject;
import org.apache.commons.collections.map.HashedMap;
import org.apache.struts2.convention.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * Created by jielao on 2017/7/31.
 */

@Namespaces({@Namespace("/review/design")})
@InterceptorRefs({ @InterceptorRef("paramsPrepareParamsStack") })
@Results({
        @Result(name=CrudActionSupport.SUCCESS, location = "/WEB-INF/page/content/feedback-review/review-list.jsp", type = Struts2Utils.DISPATCHER),
        @Result(name=CrudActionSupport.INPUT, location = "/WEB-INF/page/content/feedback-review/feedback-review-create.jsp", type = Struts2Utils.DISPATCHER),
        @Result(name=CrudActionSupport.RELOAD, location = "/review/design/feedback-review.action", type = Struts2Utils.REDIRECT)
})
@AllowedMethods({"checkExamineeUn"})
public class FeedbackReviewAction extends CrudActionSupport<FeedbackReview, String> {

    @Autowired
    private FeedbackReviewManager feedbackReviewManager;

    @Autowired
    private DepartmentManager departmentManager;

    @Autowired
    private DegreeFeedbackProjectManager degreeFeedbackProjectManager;

    @Autowired
    private FeedbackDimensionManager feedbackDimensionManager;

    @Autowired
    private UserManager userManager;

    @Autowired
    private ReviewDimensionManager reviewDimensionManager;

    @Autowired
    private FeedbackAnswerManager feedbackAnswerManager;


    @Override
    public String list() throws Exception{
        page=feedbackReviewManager.findPageByUser(page,entity);
        return SUCCESS;
    }

    @Override
    public String input() throws Exception{
        HttpServletRequest request = Struts2Utils.getRequest();
        Page<Department> pageDepartment = new Page<Department>();
        List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
        List<Department> departmentList =  departmentManager.findAll(pageDepartment, filters);
        request.getSession().setAttribute("departmentList", departmentList);
        List<FeedbackDimension> dimensionList = feedbackDimensionManager.findAll(new Page<FeedbackDimension>(), new ArrayList<PropertyFilter>());
        request.getSession().setAttribute("dimensionList", dimensionList);
        Page<DegreeFeedbackProject> pageFeedback = new Page<DegreeFeedbackProject>();
        pageFeedback = degreeFeedbackProjectManager.findByUser(pageFeedback, new DegreeFeedbackProject());
        List<DegreeFeedbackProject> degreeFeedbackProjectList = pageFeedback.getResult();
        for(DegreeFeedbackProject degreeFeedbackProject: degreeFeedbackProjectList){
            degreeFeedbackProjectManager.getFeedbackDetail(degreeFeedbackProject.getId(), degreeFeedbackProject);
        }
        request.getSession().setAttribute("feedbackDemo", degreeFeedbackProjectList);
        //根据examinee获取对应investigate
        if(entity.getExaminee() != null && entity.getExaminee() != "") {
            String[] selectExaminees = entity.getExaminee().split("&"); //被评人
            String[] selectTop = new String[selectExaminees.length];  //上评
            String[] selectMiddle = new String[selectExaminees.length]; //同级
            String[] selectBottom = new String[selectExaminees.length]; //下级
            List<ReviewUser> reviewUsers = new ArrayList<ReviewUser>();
            for(int k = 0; k < selectExaminees.length; k++) {
                ReviewUser reviewUser = new ReviewUser();
                User selectExaminee = userManager.get(selectExaminees[k]);
                reviewUser.setExaminee(selectExaminee.getName());
                selectExaminees[k] = selectExaminee.getName();
                List<ReviewDimension> reviewDimensionList = reviewDimensionManager.findDetailsByOther(id, selectExaminee.getId());
                for(ReviewDimension reviewDimension: reviewDimensionList) {
                    String[] selectInvestigates = reviewDimension.getInvestigate().split("&");
                    for (int p = 0; p < selectInvestigates.length; p++) {
                        selectInvestigates[p] = userManager.get(selectInvestigates[p]).getName();
                    }
                    switch (reviewDimension.getDimensionId()){
                        case "2":  //上级
                            selectTop[k] = String.join("&", selectInvestigates);
                            reviewUser.setInvestigateTop(selectTop[k]);
                            break;
                        case "3": // 同级
                            selectMiddle[k] = String.join("&", selectInvestigates);
                            reviewUser.setInvestigateMiddle(selectMiddle[k]);
                            break;
                        case "4":  //下级
                            selectBottom[k] = String.join("&", selectInvestigates);
                            reviewUser.setInvestigateBottom(selectBottom[k]);
                            break;
                    }
                }
                reviewUsers.add(reviewUser);
            }
            request.setAttribute("examinee", String.join("\n", selectExaminees));
            if(selectTop[0] != null && selectTop[0] != "") {
                request.setAttribute("investigateTop", String.join("\n", selectTop));
            }
            if(selectMiddle[0] != null && selectMiddle[0] != "") {
                request.setAttribute("investigateMiddle", String.join("\n", selectMiddle));
            }
            if(selectBottom[0] != null && selectBottom[0] != "") {
                request.setAttribute("investigateBottom", String.join("\n", selectBottom));
            }
            request.setAttribute("reviewDimensionList", reviewDimensionManager.findDetailsByHaveOwn(id, entity.getExaminee().split("\\|")[0]));
            request.setAttribute("reviewUsers", reviewUsers);
        }
        //已有人回答就不能修改考核表
        List<FeedbackAnswer> feedbackAnswerList = feedbackAnswerManager.findAnswerListByReview(id);
        if(feedbackAnswerList.size()!=0){
            request.setAttribute("msg", "该考核已有用户填写，已无法修改");
        }
        return INPUT;
    }

    @Override
    public String save() throws Exception{
        HttpServletRequest request = Struts2Utils.getRequest();
        String name = request.getParameter("name");
        String feedbackDemoId = request.getParameter("feedbackDemoId");
        Date createDate = new Date();
        FeedbackReview feedbackReview = new FeedbackReview();
        if (id!=null){
            feedbackReview = feedbackReviewManager.getModel(id);
        }
        feedbackReview.setName(name);
        feedbackReview.setFeedbackDemoId(feedbackDemoId);
        feedbackReview.setCreateDate(createDate);
        feedbackReview.setFinishDate(createDate);
        feedbackReview.setPublishStatus("1");
        //保存权重维度选择
        List<ReviewDimension> reviewDimensionList = new ArrayList<>();
        String mapString = request.getParameter("examineeValue");
        String ownWeight = request.getParameter("dimension_1_weight");
        JSONObject jasonObject = JSONObject.fromObject(mapString);
        Map<String, Map<String, Object>> userMap = (Map) jasonObject;
        List<String> reviewExamineeList = new ArrayList<String>();
        for(String key: userMap.keySet()) {
            Map<String, Object> userMapItem = userMap.get(key);
            User examineeUser = userManager.findByUserName(key);
            reviewExamineeList.add(examineeUser.getId());
            if (ownWeight != "" && !ownWeight.equals("0")){
                ReviewDimension ownDimension = new ReviewDimension();
                ownDimension.setDimensionId("1");
                ownDimension.setWeight(ownWeight);
                ownDimension.setInvestigate(examineeUser.getId());
                ownDimension.setExaminee(examineeUser.getId());
                reviewDimensionList.add(ownDimension);
            }
            for (String keyItem : userMapItem.keySet()) {
                String[] userNames = keyItem.split("&");
                String[] userIds = new String[userNames.length];
                for(int k = 0; k < userNames.length; k++){
                    String userId = userManager.findByUserName(userNames[k]).getId();
                    userIds[k] = userId;
                }
                String keyDimension = userMapItem.get(keyItem).toString();
                ReviewDimension reviewDimension = new ReviewDimension();
                String weight = request.getParameter("dimension_" + keyDimension + "_weight");
                reviewDimension.setDimensionId(keyDimension);
                reviewDimension.setWeight(weight);
                reviewDimension.setInvestigate(String.join("&", userIds));
                reviewDimension.setExaminee(examineeUser.getId());
                reviewDimensionList.add(reviewDimension);
            }
        }
        String[] reviewExamineeString = new String[reviewExamineeList.size()];
        for(int p = 0; p<reviewExamineeList.size(); p++){
            reviewExamineeString[p] = reviewExamineeList.get(p);
        }
        feedbackReview.setExaminee(String.join("&", reviewExamineeString));
        feedbackReview.setReviewDimensionList(reviewDimensionList);
        //删除之前保存的记录
        List<ReviewDimension> deleteList = reviewDimensionManager.findDetails(id);
        for(ReviewDimension deleteEntity: deleteList){
            reviewDimensionManager.delete(deleteEntity);
        }
        feedbackReviewManager.save(feedbackReview);
        return RELOAD;
    }

    public void checkExamineeUn() throws Exception{
        HttpServletRequest request = Struts2Utils.getRequest();
        HttpServletResponse response = Struts2Utils.getResponse();
        String mapString = request.getParameter("examineeValue");
        JSONObject jasonObject = JSONObject.fromObject(mapString);
        Map<String, Map<String, Object>> userMap = (Map) jasonObject;
        StringBuffer result = new StringBuffer();
        StringBuffer nohaveName = new StringBuffer();
        for(String key: userMap.keySet()) {
            Map<String, Object> userMapItem = userMap.get(key);
            User examineeUser = userManager.findNicknameUn(null, key);
            if(examineeUser==null){
                nohaveName.append(key + " ");
            }
            for (String keyItem : userMapItem.keySet()) {
                String[] userNames = keyItem.split("&");
                for(int k = 0; k < userNames.length; k++){
                    User investigateUser = userManager.findNicknameUn(null, userNames[k]);
                    if (investigateUser == null){
                        nohaveName.append(userNames[k] + " ");
                    }
                }
            }
        }
        if(StringUtils.isBlank(nohaveName)){
            result.append("{result:true}");
        } else{
            result.append("{result:false,name:'");
            result.append(nohaveName);
            result.append("'}");
        }
        response.setHeader("Content-Type", "text/plain;charset=UTF-8");
        response.getWriter().write(result.toString());
    }

    @Override
    protected void prepareModel() throws Exception{
        entity = feedbackReviewManager.getModel(id);
    }

    public void prepareExecute() throws Exception {
        prepareModel();
    }
}
