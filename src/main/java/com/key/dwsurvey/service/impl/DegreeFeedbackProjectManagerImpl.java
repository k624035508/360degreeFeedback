package com.key.dwsurvey.service.impl;

import com.key.common.base.entity.User;
import com.key.common.base.service.AccountManager;
import com.key.common.plugs.page.Page;
import com.key.common.service.BaseServiceImpl;
import com.key.common.utils.excel.ExceluptempRetVo;
import com.key.common.utils.excel.ReadExcelUtil;
import com.key.dwsurvey.dao.DegreeFeedbackProjectDao;
import com.key.dwsurvey.entity.DegreeFeedbackItem;
import com.key.dwsurvey.entity.DegreeFeedbackItemItem;
import com.key.dwsurvey.entity.DegreeFeedbackProject;
import com.key.dwsurvey.entity.FeedbackDetail;
import com.key.dwsurvey.service.DegreeFeedbackProjectManager;

import com.key.dwsurvey.service.FeedbackDetailManager;
import jxl.Sheet;
import jxl.Workbook;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by jielao on 2017/7/20.
 */

@Service("DegreeFeedbackProjectManager")
public class DegreeFeedbackProjectManagerImpl extends BaseServiceImpl<DegreeFeedbackProject, String> implements DegreeFeedbackProjectManager {

    @Autowired
    private DegreeFeedbackProjectDao degreeFeedbackProjectDao;

    @Autowired
    private AccountManager accountManager;

    @Autowired
    private FeedbackDetailManager feedbackDetailManager;

    @Override
    public void setBaseDao() { this.baseDao = degreeFeedbackProjectDao; }

    @Override
    public DegreeFeedbackProject getFeedback(String id){
        if(id==null || "".equals(id)){
            return new DegreeFeedbackProject();
        }
        DegreeFeedbackProject directory = get(id);
        getFeedbackDetail(id,directory);
        return  directory;
    }

    public void getFeedbackDetail(String id, DegreeFeedbackProject directory){
        FeedbackDetail feedbackDetail = null;
        if(feedbackDetail==null){
            feedbackDetail = feedbackDetailManager.getByFeedbackId(id);
        }
        if(feedbackDetail==null){
            feedbackDetail = new FeedbackDetail();
        }
        directory.setFeedbackDetail(feedbackDetail);
    }

    @Transactional
    @Override
    public void save(DegreeFeedbackProject d){
        User user = accountManager.getCurUser();
        String userId = d.getUserId();
        String id = d.getId();
        if(id==null){
            d.setUserId(user.getId());
            userId = d.getUserId();
        }
        if(userId!=null && (userId.equals(user.getId()) || user.getId().equals("1"))){
            degreeFeedbackProjectDao.save(d);
            FeedbackDetail feedbackDetailTemp = d.getFeedbackDetail();
            FeedbackDetail feedbackDetail = feedbackDetailManager.getByFeedbackId(id);
            if(feedbackDetail != null){
                if(feedbackDetailTemp != null){
                    feedbackDetail.setOwnWeight(feedbackDetailTemp.getOwnWeight());
                    feedbackDetail.setTopWeight(feedbackDetailTemp.getTopWeight());
                    feedbackDetail.setMiddleWeight(feedbackDetailTemp.getMiddleWeight());
                    feedbackDetail.setBottomWeight(feedbackDetailTemp.getBottomWeight());
                }
            } else {
                feedbackDetail = new FeedbackDetail();
                feedbackDetail.setFeedbackNote("非常感谢您的参与！如有涉及个人信息，我们将严格保密。");
                feedbackDetail.setOwnWeight("0");
                feedbackDetail.setTopWeight("0");
                feedbackDetail.setMiddleWeight("0");
                feedbackDetail.setBottomWeight("0");
            }
            feedbackDetail.setDirId(id);
            feedbackDetailManager.save(feedbackDetail);
        }
    }

    @Transactional
    @Override
    public void delete(String id){
        DegreeFeedbackProject feedParent = get(id);
        feedParent.setVisibility(0);
        degreeFeedbackProjectDao.save(feedParent);
    }

    @Override
    public Page<DegreeFeedbackProject> findByUser(Page<DegreeFeedbackProject> page, DegreeFeedbackProject entity){
        User user = accountManager.getCurUser();
        if(user!=null && !user.getId().equals("1")){
            List<Criterion> criterionList = new ArrayList<>();
            criterionList.add(Restrictions.eq("userId", user.getId()));
            criterionList.add(Restrictions.eq("visibility", 1));

            if(entity!=null){
                Integer feedbackState = entity.getFeedbackState();
                if(feedbackState!=null && !"".equals(feedbackState)){
                    criterionList.add(Restrictions.eq("feedbackState", feedbackState));
                }
                String feedbackName = entity.getName();
                if(feedbackName!=null && !"".equals(feedbackName)){
                    criterionList.add(Restrictions.like("feedbackName", "%"+feedbackName+"%"));
                }
            }
            page.setOrderBy("createDate");
            page.setOrderDir("desc");
            page = degreeFeedbackProjectDao.findPageList(page, criterionList);
        }
        else if(user!=null && user.getId().equals("1")){
            List<Criterion> criterionList = new ArrayList<>();
            criterionList.add(Restrictions.eq("visibility", 1));

            if(entity!=null){
                Integer feedbackState = entity.getFeedbackState();
                if(feedbackState!=null && !"".equals(feedbackState)){
                    criterionList.add(Restrictions.eq("feedbackState", feedbackState));
                }
                String feedbackName = entity.getName();
                if(feedbackName!=null && !"".equals(feedbackName)){
                    criterionList.add(Restrictions.like("feedbackName", "%"+feedbackName+"%"));
                }
            }
            page.setOrderBy("createDate");
            page.setOrderDir("desc");
            page = degreeFeedbackProjectDao.findPageList(page, criterionList);
        }
        return page;
    }

    public DegreeFeedbackProject getFeedbackByUser(String id, String userId){
        DegreeFeedbackProject feedback = get(id);
        if(userId.equals(feedback.getUserId()) || userId.equals("1")){
            getFeedbackDetail(id, feedback);
            return feedback;
        }
        return null;
    }

    public List<DegreeFeedbackProject> findByUserList(){
        User user = accountManager.getCurUser();
        List<DegreeFeedbackProject> list = new ArrayList<DegreeFeedbackProject>();
        if(user!=null) {
            list = degreeFeedbackProjectDao.findByUserId(user.getId());
        }
        return list;
    }

    public ExceluptempRetVo expenseData(MultipartFile file, String paras) throws Exception{
        ExceluptempRetVo exceluptempRetVo = new ExceluptempRetVo();
        if(file == null || file.isEmpty() == true){
            exceluptempRetVo.setSuccessflag(0);
            exceluptempRetVo.setRetError("文件为空！");
            return exceluptempRetVo;
        }
        String fileExt = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        if(!fileExt.toUpperCase().equals(".XLS")){
            exceluptempRetVo.setSuccessflag(0);
            exceluptempRetVo.setRetError("上传文件不是Excel文件类型！");
            return  exceluptempRetVo;
        }
        Workbook workbook = Workbook.getWorkbook(file.getInputStream());
        Sheet sheet = workbook.getSheet(0);
        JSONArray jsonArray = new JSONArray();
        String jsonData = "";
        String dataCheck = "";
        JSONObject json = new JSONObject();
        json.accumulate("applyer", "姓名1");
        jsonArray.add(json);

        if(!"".equals(dataCheck)){
            exceluptempRetVo.setSuccessflag(0);
            exceluptempRetVo.setRetError(dataCheck);
            exceluptempRetVo.setRetjson("");
            return exceluptempRetVo;
        }
        jsonData = jsonArray.toString();
        workbook.close();
        String[] retParaArr = new String[1];
        exceluptempRetVo.setSuccessflag(1);
        exceluptempRetVo.setRetParaArr(retParaArr);
        exceluptempRetVo.setRetError("");
        exceluptempRetVo.setRetjson(jsonData);
        return exceluptempRetVo;
    }


    public List<DegreeFeedbackItem> reader(File filePath) {
        try {
            POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(filePath));
            HSSFWorkbook wb = new HSSFWorkbook(fs);
            HSSFSheet sheet = wb.getSheetAt(0);
            List<DegreeFeedbackItem> degreeFeedbackItemList = new ArrayList<>();
            List<DegreeFeedbackItemItem> degreeFeedbackItemItemList = new ArrayList<>();
            List<Integer> arrays = new ArrayList<>();
            for(int i = 0; i < sheet.getLastRowNum() + 1; i++) {  //行
                HSSFRow row = sheet.getRow(i);
                //添加itemId
                DegreeFeedbackItemItem degreeFeedbackItemItem = new DegreeFeedbackItemItem();
                degreeFeedbackItemItem.setId(null);
                degreeFeedbackItemItem.setName(ReadExcelUtil.getCellStringValue(row.getCell(1)));
                degreeFeedbackItemItem.setDescription(ReadExcelUtil.getCellStringValue(row.getCell(2)));
                degreeFeedbackItemItemList.add(degreeFeedbackItemItem);
                if(ReadExcelUtil.getCellStringValue(row.getCell(0)) != " "){
                    DegreeFeedbackItem degreeFeedbackItem = new DegreeFeedbackItem();
                    degreeFeedbackItem.setId(null);
                    degreeFeedbackItem.setName(ReadExcelUtil.getCellStringValue(row.getCell(0)));
                    degreeFeedbackItemList.add(degreeFeedbackItem);
                    arrays.add(i);
                }
            }
            arrays.add(sheet.getLastRowNum()+1);
            for(Integer key = 0; key < arrays.size()-1; key++){
                Integer belong = arrays.get(key+1) - arrays.get(key);
                List<DegreeFeedbackItemItem> degreeFeedbackItemItemList2 = new ArrayList<>();
                for(Integer k = 0; k < belong; k++){
                    degreeFeedbackItemItemList.get(k+arrays.get(key)).setOrderById(k);
                    degreeFeedbackItemItemList2.add(degreeFeedbackItemItemList.get(k+arrays.get(key)));
                }
                degreeFeedbackItemList.get(key).setItemList(degreeFeedbackItemItemList2);
                degreeFeedbackItemList.get(key).setOrderById(key);
            }
            System.out.println(arrays);
            return degreeFeedbackItemList;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
