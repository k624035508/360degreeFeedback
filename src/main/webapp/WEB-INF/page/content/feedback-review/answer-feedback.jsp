<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/common/taglibs.jsp" %>
<html>
<head>
    <title>绩效测评填写</title>
    <link href="${ctx }/js/plugs/jquery-ui-1.10.3.custom/css/mycss/jquery-ui-1.10.3.custom.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="${ctx }/js/plugs/jquery-ui-1.10.3.custom/js/jquery-1.10.1.js"></script>
    <script type="text/javascript" src="${ctx }/js/plugs/jquery-ui-1.10.3.custom/js/jquery-ui-1.10.3.custom.js"></script>
    <script type="text/javascript" src="${ctx }/js/plugs/colpick-jQuery/js/colpick.js"></script>
    <link href="${ctx }/js/plugs/uploadify-v3.1/uploadify.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="${ctx }/js/plugs/uploadify-v3.1/jquery.uploadify-3.1.js"></script>
    <script type="text/javascript" src="${ctx }/js/dw/uploadify.js"></script>
    <link href="${ctx }/css/preview-dev.css" rel="stylesheet" type="text/css" />
    <link rel="stylesheet" href="${ctx }/js/plugs/colpick-jQuery/css/colpick.css" type="text/css"/>
    <link href="${ctx}/js/plugs/validate/jquery.validate.css" type="text/css" rel="stylesheet" />
    <script src="${ctx}/js/plugs/validate/jquery.validate.js" type="text/javascript"></script>
    <script type="text/javascript" src="${ctx }/js/dw/dw-answer-feedback.js"></script>
</head>
<body>
<div id="wrap">
    <input type="hidden" id="id" name="id" value="${review.id }">
    <form id="feedbackAnswerForm" action="${ctx }/feedback!save.action" method="post" >
        <input type="hidden" id="reviewId" name="reviewId" value="${review.id }" />
        <div id="dw_body" style="padding-top:10px;">
            <div id="dw_body_content">
                <div class="dwSurveyHeader">
                    <div id="dwSurveyTitle" class="noLogoImg">
                        <div id="dwSurveyName" class="editAble dwSvyName">${review.name }</div>
                    </div>
                    <div id="dwAnswer">
                        <div class="answer-name"><span>评分人</span><input type="text" readonly value="${user.name}"></div>
                        <div class="answer-name"><span>部门</span><input type="text" readonly value="${user.departmentName}"></div>
                    </div>
                </div>
                <div id="dwSurveyQuContent" style="min-height: 300px;">
                    <div id="dwSurveyQuContentBg">
                        <ul id="dwSurveyQuContentAppUl">
                            <li class="li_surveyQuItemBody">
                                <div class="surveyQuItemBody">
                                    <div class="surveyQuDesc">
                                        ${feedbackProject.feedbackDetail.feedbackNote}
                                    </div>
                                    <c:forEach items="${feedbackProject.questions}" var="en">
                                        <c:forEach items="${en.degreeFeedbackItems}" var="items" varStatus="key">
                                            <c:forEach items="${examineeList}" var="userList2">
                                                <input type="hidden" name="qu_${userList2.id}_${items.id}" value="item_qu_${userList2.id}_${items.id}_" />
                                            </c:forEach>
                                            <c:forEach items="${items.itemList}" var="itemItem" varStatus="keyItem">
                                                <div class="surveyQuFeedbackItem">
                                                    <div class="div_title_question_all">
                                                        <div class="div_topic_question"><!--${key.index*fn:length(items.itemList)+keyItem.count}-->${keyItem.count}、</div>
                                                        <div class="div_title_question">
                                                            <div class="div_title_question_title">${itemItem.name}</div>
                                                            <div class="div_title_question_description">${itemItem.description}</div>
                                                        </div>
                                                    </div>
                                                    <div class="div_table_radio_question">
                                                        <table>
                                                            <tr><th></th>
                                                                <c:if test="${feedbackProject.feedbackDetail.paramScore eq 5}"><th>1</th><th>2</th><th>3</th><th>4</th><th>5</th></c:if>
                                                                <c:if test="${feedbackProject.feedbackDetail.paramScore eq 10}"><th>1</th><th>2</th><th>3</th><th>4</th><th>5</th><th>6</th><th>7</th><th>8</th><th>9</th><th>10</th></c:if>
                                                            </tr>
                                                            <c:forEach items="${examineeList}" var="userList">
                                                                <tr class="feedbackOptionTr">
                                                                    <td>${userList.name}</td>
                                                                    <c:forEach begin="1" end="${feedbackProject.feedbackDetail.paramScore}" varStatus="countNum">
                                                                        <td>
                                                                            <input type="radio" name="item_qu_${userList.id}_${items.id}_${itemItem.id}" value="${countNum.count}"
                                                                                    <%--<c:if test="${itemValue_8ab29f505d9bd4b1015d9bd940060000_4028b8815e4f5e6c015e4f68fafe000d eq countNum.count}">checked</c:if>--%>
                                                                            />
                                                                        </td>
                                                                        <%--${itemValue_8ab29f505d9bd4b1015d9bd940060000_4028b8815e4f5e6c015e4f68fafe000d}--%>
                                                                    </c:forEach>
                                                                </tr>
                                                            </c:forEach>
                                                        </table>
                                                    </div>
                                                </div>
                                            </c:forEach>
                                        </c:forEach>
                                    </c:forEach>
                                </div>
                            </li>
                            <li class="li_surveyQuItemBody">
                                <div class="surveyQuItemBody">
                                    <div class="surveyQuItem">
                                        <input type="hidden" class="quType" value="submitSurveyBtn">
                                        <div class="surveyQuItemContent" style="padding-top: 20px; min-height: 30px;">
                                            <%--<a href="#" id="submitReview" class="sbtn24 sbtn24_0 submitSurvey" >提&nbsp;交</a>&nbsp;&nbsp;--%>
                                            <c:if test="${examineeList.size()!=0}">
                                                <input type="button" class="answerPost" value="提交" />
                                                <input type="button" class="answerPostDraft" value="保存草稿" />
                                                <input type="hidden" name="answerStatus" class="answerStatus" value="">
                                            </c:if>
                                                <c:if test="${examineeList.size()==0}">
                                                    <input type="button" value="提交" onclick='alert("请选择待评分用户");' />
                                                </c:if>
                                        </div>
                                    </div>
                                </div>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </form>
</div>
</body>
</html>
