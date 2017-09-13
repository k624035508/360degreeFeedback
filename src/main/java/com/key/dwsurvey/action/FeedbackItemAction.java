package com.key.dwsurvey.action;

import com.key.common.QuType;
import com.key.common.utils.web.Struts2Utils;
import com.key.dwsurvey.entity.DegreeFeedbackItem;
import com.key.dwsurvey.entity.DegreeFeedbackItemItem;
import com.key.dwsurvey.entity.FeedbackReview;
import com.key.dwsurvey.entity.Question;
import com.key.dwsurvey.service.FeedbackItemItemManager;
import com.key.dwsurvey.service.FeedbackItemManager;
import com.key.dwsurvey.service.FeedbackReviewManager;
import com.key.dwsurvey.service.QuestionManager;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.convention.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 评分选项 action
 * Created by jielao on 2017/7/24.
 */

@Namespaces({@Namespace("/design")})
@InterceptorRefs({@InterceptorRef("paramsPrepareParamsStack")})
@Results({})
@AllowedMethods({"ajaxSave", "ajaxDelete", "ajaxDeleteItem"})
public class FeedbackItemAction extends ActionSupport {

    @Autowired
    private QuestionManager questionManager;
    @Autowired
    private FeedbackItemManager feedbackItemManager;
    @Autowired
    private FeedbackItemItemManager feedbackItemItemManager;
    @Autowired
    private FeedbackReviewManager feedbackReviewManager;

    public String ajaxSave() throws Exception{
        HttpServletRequest request = Struts2Utils.getRequest();
        HttpServletResponse response = Struts2Utils.getResponse();
        try{
            //已经发布考核表不能修改
            String belongId = request.getParameter("belongId");
            List<FeedbackReview> feedbackReviewList = feedbackReviewManager.findByDemoId(belongId);
            if (feedbackReviewList.size() != 0) {
                response.getWriter().write("havePublish");
            } else {
                Question entity = ajaxBuildSaveOption(request);
                questionManager.save(entity);
                String resultJson = buildResultJson(entity);
                response.getWriter().write(resultJson);
                //返回各部分ID
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write("error");
        }
        return null;
    }

    private Question ajaxBuildSaveOption(HttpServletRequest request) throws UnsupportedEncodingException{
        String quId = request.getParameter("quId");
        String beongId = request.getParameter("belongId");
        String quTitle=request.getParameter("quTitle");
        String orderById=request.getParameter("orderById");
        String isRequired=request.getParameter("isRequired");
        String tag=request.getParameter("tag");
        //hv 1水平显示 2垂直显示
        String hv=request.getParameter("hv");
        //randOrder 选项随机排列
        String randOrder=request.getParameter("randOrder");
        String cellCount=request.getParameter("cellCount");
        String paramInt01=request.getParameter("paramInt01");//最小分
        String paramInt02=request.getParameter("paramInt02");//最大分

        if("".equals(quId)){
            quId = null;
        }
        Question entity = questionManager.getModel(quId);
        entity.setBelongId(beongId);
        if(quTitle!=null){
            quTitle=URLDecoder.decode(quTitle,"utf-8");
            entity.setQuTitle(quTitle);
        }
        entity.setOrderById(Integer.parseInt(orderById));
        entity.setTag(Integer.parseInt(tag));
        entity.setQuType(QuType.DEGREEFEEDBACK);
        //参数
        isRequired=(isRequired==null || "".equals(isRequired))?"0":isRequired;
        hv=(hv==null || "".equals(hv))?"0":hv;
        randOrder=(randOrder==null || "".equals(randOrder))?"0":randOrder;
        cellCount=(cellCount==null || "".equals(cellCount))?"0":cellCount;
        paramInt01=(paramInt01==null || "".equals(paramInt01))?"0":paramInt01;
        entity.setIsRequired(Integer.parseInt(isRequired));
        entity.setHv(Integer.parseInt(hv));
        entity.setRandOrder(Integer.parseInt(randOrder));
        entity.setCellCount(Integer.parseInt(cellCount));
        entity.setParamInt01(Integer.parseInt(paramInt01));
        entity.setParamInt02(100);
        //quOption
        Map<String, Object> optionIdMap = WebUtils.getParametersStartingWith(request, "optionId_");
        List<DegreeFeedbackItem> list = new ArrayList<DegreeFeedbackItem>();
        for(String key: optionIdMap.keySet()){
            String optionId = optionIdMap.get(key).toString();
            String optionNameValue = request.getParameter("optionValue_" + key);
            //quOptionItem
            DegreeFeedbackItem degreeFeedbackItem = new DegreeFeedbackItem();
            if("".equals(optionId)){
                optionId=null;
            }
            degreeFeedbackItem.setId(optionId);
            optionNameValue= URLDecoder.decode(optionNameValue,"utf-8");
            degreeFeedbackItem.setName(optionNameValue);
            degreeFeedbackItem.setOrderById(Integer.parseInt(key));
            List<DegreeFeedbackItemItem> itemItems = new ArrayList<DegreeFeedbackItemItem>();
            Map<String, Object> optionItemIdMap = WebUtils.getParametersStartingWith(request, "optionItemId_" + key + "_");
            for(String itemKey: optionItemIdMap.keySet()){
                String optionItemId = optionItemIdMap.get(itemKey).toString();
                String optionItemNameValue = request.getParameter("optionItemValue_" + key + "_" + itemKey);
                String optionItemDescValue = request.getParameter("optionItemDesc_" + key + "_" + itemKey);
                DegreeFeedbackItemItem itemItem  = new DegreeFeedbackItemItem();
                if("".equals(optionItemId)){
                    optionItemId=null;
                }
                itemItem.setId(optionItemId);
                optionItemNameValue= URLDecoder.decode(optionItemNameValue,"utf-8");
                optionItemDescValue = URLDecoder.decode(optionItemDescValue, "utf-8");
                itemItem.setName(optionItemNameValue);
                itemItem.setDescription(optionItemDescValue);
                itemItem.setOrderById(Integer.parseInt(itemKey));
                itemItems.add(itemItem);
            }
            degreeFeedbackItem.setItemList(itemItems);
            list.add(degreeFeedbackItem);
        }
        entity.setDegreeFeedbackItems(list);
        return entity;
    }

    public static String buildResultJson(Question entity){
        //{id:'null',quItems:[{id:'null',title:'null'},{id:'null',title:'null'}]}
        StringBuffer strBuf = new StringBuffer();
        //{id:'',quItems:[{id:'',title:''},{id:'',title:''}]}
        strBuf.append("{id:'").append(entity.getId());
        strBuf.append("',orderById:");
        strBuf.append(entity.getOrderById());
        strBuf.append(",quItems:[");
        List<DegreeFeedbackItem> degreeFeedbackItems = entity.getDegreeFeedbackItems();
        for(DegreeFeedbackItem degreeFeedbackItem : degreeFeedbackItems){
            strBuf.append("{id:'").append(degreeFeedbackItem.getId());
            strBuf.append("',title:'").append(degreeFeedbackItem.getOrderById()).append("'},");
        }
        int strLen=strBuf.length();
        if(strBuf.lastIndexOf(",")==(strLen-1)){
//			strBuf.substring(0, strLen-1);
            strBuf.replace(strLen-1, strLen, "");
        }
        strBuf.append("]}");
        System.out.println(strBuf.toString());
        return strBuf.toString();
    }

    public String ajaxDelete() throws Exception{
        HttpServletRequest request = Struts2Utils.getRequest();
        HttpServletResponse response = Struts2Utils.getResponse();
        try{
            String quItemId = request.getParameter("quItemId");
            feedbackItemManager.ajaxDelete(quItemId);
            response.getWriter().write("true");
        }catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write("error");
        }
        return null;
    }

    public String ajaxDeleteItem() throws Exception{
        HttpServletRequest request = Struts2Utils.getRequest();
        HttpServletResponse response = Struts2Utils.getResponse();
        try{
            String quItemItemId = request.getParameter("quItemItemId");
            feedbackItemItemManager.ajaxDelete(quItemItemId);
            response.getWriter().write("true");
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write("error");
        }
        return null;
    }
}
