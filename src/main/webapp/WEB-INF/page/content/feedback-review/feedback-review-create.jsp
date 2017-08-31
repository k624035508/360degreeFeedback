<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="${ctx }/js/plugs/bootstrap-3.3.0-dist/dist/css/bootstrap.css">
    <link href="${ctx }/js/plugs/jquery-ui-1.10.3.custom/css/mycss/jquery-ui-1.10.3.custom.css" rel="stylesheet" type="text/css" />
    <script src="${ctx }/js/plugs/bootstrap-3.3.0-dist/dist/js/bootstrap.js"></script>
    <link rel="stylesheet" type="text/css" href="${ctx}/css/user-selector.css">
    <script src="${ctx}/js/dw/feedback-review-create.js"></script>
    <link href="${ctx}/js/plugs/validate/jquery.validate.css" type="text/css" rel="stylesheet" />
    <script src="${ctx}/js/plugs/validate/jquery.validate.js" type="text/javascript"></script>
    <script src="${ctx}/js/plugs/validate/messages_cn.js" type="text/javascript"></script>
    <script src="${ctx }/js/plugs/validate/jquery.metadata.js" type="text/javascript"></script>
    <title>新建测评考核</title>
    <style type="text/css">
        .ac-input-td input,.ac-input-td select{
            padding: 4px! important;
            font-size: 14px;
        }
        .red-color{
            color: red;
        }
    </style>
    <script type="text/javascript">
        $(document).ready(function () {
            $(".textarea-submit").validate({
                rules:{
                    loginName:{},
                    email: {}
                }
            })
        })
    </script>
</head>
<body>
<div style="margin-top: 15px;">

</div>
<div style="clear: both;"></div>
<div id="dwBody" >
    <div id="dwBodyContent" class="bodyCenter" style="">

        <div id="dwBodyUser">
            <div class="surveyCollectMiddle">

                <form id="inputReviewForm" action="${ctx }/review/design/feedback-review!save.action" method="post" >
                    <input type="hidden" name="examineeValue" id="examineeValue" value="" />
                    <input type="hidden" name="id" value="${id }" >
                    <div class="surveyCollectMiddleContent">
                        <div style="padding: 25px 45px;overflow: auto;padding-top: 35px;">
                            <div style="border-bottom: 1px solid #DFDFDF;padding: 5px;color: #666565;">考核测评设计</div>
                            <div style="padding: 5px;color:#666565; ">
                                <table width="100%">
                                    <tr>
                                        <td valign="top" align="left" >
                                            <table class="ac-form-table">
                                                <tr>
                                                    <td width="150" align="right"><span class="red-color">*&nbsp;</span>考核测评名称</td>
                                                    <td class="ac-input-td"><input type="text" id="reviewName" name="name" value="${name }"  > </td>
                                                </tr>
                                                <tr>
                                                    <td width="150" align="right"><span class="red-color">*&nbsp;</span>考核表模板</td>
                                                    <td class="ac-input-td">
                                                        <select name="feedbackDemoId">
                                                            <c:forEach items="${feedbackDemo}" var="feedbackDemo">
                                                                <option value="${feedbackDemo.id}"
                                                                        data-own-weight="${feedbackDemo.feedbackDetail.ownWeight}"
                                                                        data-top-weight="${feedbackDemo.feedbackDetail.topWeight}"
                                                                        data-middle-weight="${feedbackDemo.feedbackDetail.middleWeight}"
                                                                        data-bottom-weight="${feedbackDemo.feedbackDetail.bottomWeight}"
                                                                        <c:if test="${feedbackDemo.id eq feedbackDemoId}">selected</c:if>
                                                                >${feedbackDemo.name}
                                                                </option>
                                                            </c:forEach>
                                                        </select>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td width="150" align="right"><span class="red-color">*&nbsp;</span>评价关系</td>
                                                    <td class="ac-input-td">
                                                        <%--<input type="text" readonly value="${selectExaminee}" class="selectName" />--%>
                                                        <table class="examinee-table">
                                                            <tr><th>被评人</th><th>上级</th><th>同级</th><th>下级</th></tr>
                                                            <c:forEach items="${reviewUsers}" var="reviewUser">
                                                                <tr class = "examinee-tr">
                                                                    <td>${reviewUser.examinee}</td><td>${reviewUser.investigateTop}</td>
                                                                    <td>${reviewUser.investigateMiddle}</td><td>${reviewUser.investigateBottom}</td>
                                                                </tr>
                                                            </c:forEach>
                                                        </table>
                                                        <a href="javascript:;" id="selectExaminee">选择</a>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td width="150" align="right"><span class="red-color">*&nbsp;</span>权重设置</td>
                                                    <td class="ac-input-td">
                                                        <table>
                                                            <tr><th>维度名称</th><th>权重(%)</th></tr>
                                                            <c:forEach items="${dimensionList}" var="dimensionList">
                                                                <tr class="selectDimensionTr">
                                                                    <td>${dimensionList.name}</td>
                                                                    <td><input type="text" name="dimension_${dimensionList.id}_weight" id="dimension_${dimensionList.id}_weight"
                                                                            <c:forEach items="${reviewDimensionList}" var="reviewDimension">
                                                                                <c:if test="${reviewDimension.dimensionId eq dimensionList.id}">value="${reviewDimension.weight}"</c:if>
                                                                            </c:forEach>
                                                                               value="0"
                                                                    />
                                                                    </td>
                                                                </tr>
                                                            </c:forEach>
                                                        </table>
                                                    </td>
                                                </tr>
                                            </table>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td height="50">
                                            <c:if test='${msg==null}'>
                                                <input type="button" value="确认发布" class="sbtn25 sbtn25_1 button_submit" style="margin-left: 125px;">
                                            </c:if>
                                            <c:if test='${msg!=null}'>
                                                <a href="javascript:" onclick='alert("${msg}");' class="a_submit" style="margin-left: 125px;">确认发布</a>
                                            </c:if>

                                        </td>
                                        <td class="ac-input-td"> </td>
                                    </tr>
                                </table>
                                <div class="modal fade" id="examineeModal2" tabindex="-1" role="dialog" aria-labelledby="examineeModal2Label" aria-hidden="true">
                                    <div class="modal-dialog" style="width:1000px;">
                                        <div class="modal-content">
                                            <div class="modal-header">
                                                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                                                <h4 class="modal-title">用户选择器2</h4>
                                            </div>
                                            <div class="modal-body" style="height: 410px;">
                                                <p><span style="color: red;">tip</span>:一行对应一个被考核人，其上级关系对应相关行,用户之间用"|"隔开</p>
                                                <div class="div-set" style="margin-top: 20px;">
                                                <div class="div-set-own" style="float: left; display: inline-block; width: 180px; margin-right: 20px;">
                                                    <p>被考核人</p>
                                                    <textarea id="own-textarea" wrap="off" rows="10" cols="5" style="width: 180px; ">${examinee}</textarea>
                                                </div>
                                                <div class="div-set-top" style="float: left; display: inline-block; width: 180px; margin-right: 20px;">
                                                    <p>上级</p>
                                                    <textarea id="top-textarea" wrap="off" rows="10" cols="5"  style="width: 180px;">${investigateTop}</textarea>
                                                </div>
                                                <div class="div-set-middle" style="float: left; display: inline-block; width: 280px; margin-right: 20px;">
                                                    <p>同级</p>
                                                    <textarea id="middle-textarea" wrap="off" rows="10" cols="5"  style="width: 280px;">${investigateMiddle}</textarea>
                                                </div>
                                                <div class="div-set-bottom" style="float: left; display: inline-block; width: 180px; margin-right: 20px;">
                                                    <p>下级</p>
                                                    <textarea id="bottom-textarea" wrap="off" rows="10" cols="5"  style="width: 180px;">${investigateBottom}</textarea>
                                                </div>
                                                <div style="clear: both;"></div>
                                                </div>
                                            </div>
                                            <div class="modal-footer">
                                                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                                                <button type="button" class="btn btn-primary textarea-submit" data-dismiss="modal">提交更改</button>
                                            </div>
                                        </div><!-- /.modal-content -->
                                    </div><!-- /.modal -->
                                </div>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
</body>
</html>
