package com.key.dwsurvey.service;

import com.key.common.plugs.page.Page;
import com.key.common.service.BaseService;
import com.key.common.utils.excel.ExceluptempRetVo;
import com.key.dwsurvey.entity.DegreeFeedbackItem;
import com.key.dwsurvey.entity.DegreeFeedbackProject;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

/**
 * 根据 最底层对象，得到此对象所在的目录结构
 * Created by jielao on 2017/7/19.
 * @return
 */
public interface DegreeFeedbackProjectManager extends BaseService<DegreeFeedbackProject, String> {

    public DegreeFeedbackProject getFeedback(String id);

    public Page<DegreeFeedbackProject> findByUser(Page<DegreeFeedbackProject> page, DegreeFeedbackProject degreeFeedbackProject);

    public DegreeFeedbackProject getFeedbackByUser(String id, String userId);

    public void getFeedbackDetail(String id, DegreeFeedbackProject directory);

    public List<DegreeFeedbackProject> findByUserList();

    public ExceluptempRetVo expenseData(MultipartFile file, String paras) throws Exception;

    public List<DegreeFeedbackItem> reader(File filePath);
}
