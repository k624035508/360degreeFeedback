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
</head>
<body>
<div id="wrap">
    <input type="hidden" id="id" name="id" value="${review.id }">
    <form id="surveyForm" action="${ctx }/feedback!save.action" method="post" >
        <input type="hidden" id="reviewId" name="reviewId" value="${review.id }" />
        <input type="hidden" id="dimensionId" name="dimensionId" value="${feedbackDimension.id }" />
        <div id="dw_body" style="padding-top:10px;">
            <div id="dw_body_content">
                <div class="dwSurveyHeader">
                    <div id="dwSurveyTitle" class="noLogoImg">
                        <div id="dwSurveyName" class="editAble dwSvyName">${review.name }</div>
                    </div>
                    <div id="dwAnswer">
                        <div class="answer-name"><span>评分人</span><input type="text" readonly value="${user.name}"></div>
                        <div class="answer-name"><span>部门</span><input type="text" readonly value="${user.department}"></div>
                        <div class="answer-name">
                                <span>选择待评分用户</span>
                                <select name="answerOwnerId">
                                    <c:forEach items="${examineeList}" var="userList">
                                        <option value="${userList.id}">${userList.name}</option>
                                    </c:forEach>
                                </select>

                        </div>
                    </div>
                </div>
                <div id="dwSurveyQuContent" style="min-height: 300px;">
                    <div id="dwSurveyQuContentBg">
                        <ul id="dwSurveyQuContentAppUl">
                            <c:forEach items="${feedbackProject.questions}" var="en">
                                <li class="li_surveyQuItemBody">
                                    <div class="surveyQuItemBody">
                                        <div class="initLine"></div>
                                        <div class="quInputCase" style="display: none;">
                                            <input type="hidden" name="quType" value="DEGREEFEEDBACK" >
                                            <input type="hidden" name="quId" value="${en.id }">
                                            <input type="hidden" name="orderById" value="${en.orderById }"/>
                                        </div>
                                        <div class="surveyQuItem">
                                            <div class="surveyQuItemContent">
                                                <div class="quCoItem">
                                                    <table class="feedbackTable">
                                                        <tr><th style="width:10%;">评估项目</th><th style="width:10%;">评分内容</th><th style="width:70%;">评估内容说明</th><th style="width:10%;">评价</th></tr>
                                                        <c:forEach items="${en.degreeFeedbackItems}" var="item">
                                                            <tr class="feedbackTableTr">
                                                                <td class="feedbackTableEditTd">
                                                                    <label class="editAble quCoOptionEdit">${item.name}</label>
                                                                    <div class="quItemInputCase">
                                                                        <input type="hidden" name="qu_${en.quType }_${item.id}" value="item_qu_${en.quType}_${item.id}_" />
                                                                    </div>
                                                                </td>
                                                                <td>
                                                                    <ul class="quItemItemEditUl">
                                                                        <c:forEach items="${item.itemList}" var="itemItem">
                                                                            <li class="quItemItemEditLi">
                                                                                <label class="editAble quItemItemEdit">${itemItem.name}</label>
                                                                                <div class="quItemInputCase">
                                                                                    <input type="hidden" name="quItemItemId" value="${itemItem.id}" />
                                                                                    <input type="hidden" name="quItemItemSaveTag" value="1">
                                                                                </div>
                                                                            </li>
                                                                        </c:forEach>
                                                                    </ul>
                                                                </td>
                                                                <td>
                                                                    <ul class="quItemItemEditUl">
                                                                        <c:forEach items="${item.itemList}" var="itemItem">
                                                                            <li class="quItemItemEditLi">
                                                                                ${itemItem.description}
                                                                            </li>
                                                                        </c:forEach>
                                                                    </ul>
                                                                </td>
                                                                <td>
                                                                    <ul class="quItemItemEditUl">
                                                                        <c:forEach items="${item.itemList}" var="itemItem">
                                                                        <li class="quItemItemEditLi">
                                                                            <select name="item_qu_${en.quType }_${item.id}_${itemItem.id }">
                                                                                <option>1</option><option>2</option><option>3</option><option>4</option><option>5</option>
                                                                            </select>
                                                                        </li>
                                                                        </c:forEach>
                                                                    </ul>
                                                                </td>
                                                            </tr>
                                                        </c:forEach>
                                                    </table>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </li>
                            </c:forEach>
                            <li class="li_surveyQuItemBody">
                                <div class="surveyQuItemBody">
                                    <div class="surveyQuItem">
                                        <input type="hidden" class="quType" value="submitSurveyBtn">
                                        <div class="surveyQuItemContent" style="padding-top: 12px;height: 30px;min-height: 30px;">
                                            <%--<a href="#" id="submitReview" class="sbtn24 sbtn24_0 submitSurvey" >提&nbsp;交</a>&nbsp;&nbsp;--%>
                                            <c:if test="${examineeList.size()!=0}">
                                                <input type="submit" value="提交" />
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
