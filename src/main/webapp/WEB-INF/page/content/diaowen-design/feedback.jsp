<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>测评编辑</title>
    <link href="${ctx }/js/plugs/jquery-ui-1.10.3.custom/css/mycss/jquery-ui-1.10.3.custom.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="${ctx }/js/plugs/jquery-ui-1.10.3.custom/js/jquery-1.10.1.js"></script>
    <script type="text/javascript" src="${ctx }/js/plugs/jquery-ui-1.10.3.custom/js/jquery-ui-1.10.3.custom.js"></script>
    <script type="text/javascript" charset="utf-8" src="${ctx }/js/plugs/ueditor1_4_2-utf8-jsp/ueditor.config.js"></script>
    <script type="text/javascript" charset="utf-8" src="${ctx }/js/plugs/ueditor1_4_2-utf8-jsp/ueditor.all.min.js"> </script>
    <!--建议手动加在语言，避免在ie下有时因为加载语言失败导致编辑器加载失败-->
    <!--这里加载的语言文件会覆盖你在配置项目里添加的语言类型，比如你在配置项目里配置的是英文，这里加载的中文，那最后就是中文-->
    <script type="text/javascript" charset="utf-8" src="${ctx }/js/plugs/ueditor1_4_2-utf8-jsp/lang/zh-cn/zh-cn.js"></script>
    <script type="text/javascript" src="${ctx }/js/dw/dw-feedback-design.js"></script>
    <script type="text/javascript" src="${ctx }/js/dw/address.js"></script>
    <script language="javascript" type="text/javascript" src="${ctx }/js/plugs/My97DatePickerBeta/My97DatePicker/WdatePicker.js"></script>
    <link href="${ctx }/css/design-survey.css" rel="stylesheet" type="text/css" />
    <!--[if lt IE 7]><link rel="stylesheet" href="ie-stuff.css" type="text/css" media="screen"/><![endif]-->
    <script type="text/javascript">
    </script>
</head>
<body>
<div id="wrap">
    <input type="hidden" id="id" name="id" value="${feedback.id }">
    <input type="hidden" id="ctx" value="${ctx }">

    <div id="header" >
        <div id="header_left">
            <%-- <img alt="调问网" src="${ctx }/images/logo/logo.jpg" > --%>
            <!-- <div id="header_title">DIAOWEN-在线问卷编辑器</div> -->
            <div class="header_Item header_logo">
                <a href="${ctx }/"><img alt="调问网" src="${ctx }/images/logo/LOGO.png" ></a>
                <!-- <div style="font-family: '微软雅黑';font-size:26px;padding-left:10px;">DIAOWEN&nbsp;&nbsp; </div>-->
                &nbsp;&nbsp;
                <span style="font-family: '微软雅黑';font-size: 16px;line-height: 46px;" id="pageHeaderSpan">在线测评内容编辑器</span>
            </div>
        </div>
        <div id="header_right">
            <div style="line-height: 22px;"><a href="${ctx }/design/my-feedback.action">测评模板</a></div>
        </div>
    </div>

    <div id="tools_wrap">
        <div id="tools">
            <div class="tools_tabs">
                <div class="tools_tabs_left"><ul><li class="current" id="tools_tab1_li">基本</li></ul></div>
            </div>

            <div class="tools_contents">

                <div id="tools_tab1" class="tools_tab_div" style="display: inline;">
                    <div id="toolsBashQu" class="tools_item" style="width:205px;">
                        <div class="toolbars" style="width:200px;">
                            <ul class="dragQuUl" >
                                <li id="tableQuModel">
                                    <div class="dwToolbar_icon" style="width: 180px;">测评表格</div>
                                    <div class="dwQuTypeModel">
                                        <div class="surveyQuItemBody quDragBody">
                                            <div class="initLine"></div>
                                            <div class="quInputCase" style="display: none;">
                                                <input type="hidden" name="quType" value="DEGREEFEEDBACK" >
                                                <input type="hidden" name="quId" value="">
                                                <input type="hidden" name="orderById" value="0"/>
                                                <input type="hidden" name="saveTag" value="0">
                                                <input type="hidden" name="hoverTag" value="0">
                                                <input type="hidden" name="isRequired" value="1">
                                                <input type="hidden" name="hv" value="2">
                                                <input type="hidden" name="randOrder" value="0">
                                                <input type="hidden" name="cellCount" value="0">

                                                <input type="hidden" name="paramInt01" value="1">
                                                <input type="hidden" name="paramInt02" value="100">
                                                <div class="quLogicInputCase">
                                                    <input type="hidden" name="quLogicItemNum" value="0">
                                                </div>
                                            </div>
                                            <div class="surveyQuItem">
                                                <div class="surveyQuItemLeftTools">
                                                    <ul class="surveyQuItemLeftToolsUl">
                                                        <li title="移动排序" class="dwQuMove"><div class="dwQuIcon"></div></li>
                                                        <li title="设置" class="dwQuSet"><div class="dwQuIcon"></div></li>
                                                        <li title="删除" class="dwQuDelete"><div class="dwQuIcon"></div></li>
                                                    </ul>
                                                </div>
                                                <div class="surveyQuItemRightTools">
                                                    <ul class="surveyQuItemRightToolsUl">
                                                        <li class="questionUp"><div class="dwQuIcon"></div></li>
                                                        <li class="questionDown"><div class="dwQuIcon"></div></li>
                                                    </ul>
                                                </div>
                                                <div class="surveyQuItemContent">
                                                    <div class="quCoTitle">
                                                        <div class="editAble quCoTitleEdit" >测评指标内容</div>
                                                        <input type="hidden" name="quTitleSaveTag" value="0">
                                                    </div>
                                                    <div class="quCoItem">
                                                            <table class="feedbackTable">
                                                                <tr><th style="width:20%;">评估项目</th><th style="width:20%;">具体评分内容</th><th style="width:60%;">评估内容说明</th></tr>
                                                                <%--<tr class="feedbackTableTr">--%>
                                                                    <%--<td class="feedbackTableEditTd">--%>
                                                                        <%--<label class="editAble quCoOptionEdit">指标1</label>--%>
                                                                        <%--<div class="quItemInputCase">--%>
                                                                            <%--<input type="hidden" name="quItemId" value="" />--%>
                                                                            <%--<input type="hidden" name="quItemSaveTag" value="0" />--%>
                                                                            <%--<input type="hidden" name="quOptionDescSaveTag" value="0" />--%>
                                                                        <%--</div>--%>
                                                                    <%--</td>--%>
                                                                    <%--<td>--%>
                                                                        <%--<ul class="quItemItemEditUl">--%>
                                                                            <%--<li class="quItemItemEditLi">--%>
                                                                                <%--<label class="editAble quItemItemEdit">评分指标1</label>--%>
                                                                                <%--<div class="quItemInputCase">--%>
                                                                                    <%--<input type="hidden" name="quItemItemId" value="" />--%>
                                                                                    <%--<input type="hidden" name="quItemItemSaveTag" value="0">--%>
                                                                                <%--</div>--%>
                                                                            <%--</li>--%>
                                                                        <%--</ul>--%>
                                                                        <%--<div class="quCoBottomTools" >--%>
                                                                            <%--<ul class="quCoBottomToolsUl" >--%>
                                                                                <%--<li class="addOptionItem" title="添加"><div class="dwQuIcon"></div></li>--%>
                                                                            <%--</ul>--%>
                                                                        <%--</div>--%>
                                                                    <%--</td>--%>
                                                                    <%--<td>--%>
                                                                        <%--<ul class="quItemItemDescEditUl">--%>
                                                                            <%--<li class="quItemItemDescEditLi">--%>
                                                                                <%--<label class="editAble quItemItemDescEdit">评分指标说明1</label>--%>
                                                                                <%--<div class="quItemInputCase">--%>
                                                                                    <%--<input type="hidden" name="quItemItemId" value="" />--%>
                                                                                    <%--<input type="hidden" name="quItemItemDescSaveTag" value="0">--%>
                                                                                <%--</div>--%>
                                                                            <%--</li>--%>
                                                                        <%--</ul>--%>
                                                                        <%--<div class="quCoBottomTools">--%>
                                                                            <%--<ul class="quCoBottomToolsUl" >--%>
                                                                                <%--<li class="addOptionItem2" title="添加"><div class="dwQuIcon"></div></li>--%>
                                                                            <%--</ul>--%>
                                                                        <%--</div>--%>
                                                                    <%--</td>--%>
                                                                <%--</tr>--%>
                                                            </table>
                                                    </div>
                                                    <div class="quCoBottomTools" >
                                                        <ul class="quCoBottomToolsUl" >
                                                            <li class="addOption" title="添加"><div class="dwQuIcon"></div></li>
                                                        </ul>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </li>
                            </ul>
                        </div>
                        <div class="tooltext" style="width:205px;">基本题型</div>
                    </div>

                    <div class="tools_item">
                        <form action="${ctx}/design/my-feedback-design!importExcel.action?feedbackId=${feedback.id}" method="post" enctype="multipart/form-data" >
                            <%--<form action="${ctx}/design/my-feedback-design!excelWrite.action" method="post" enctype="multipart/form-data" >--%>
                        <div class="toolbars" style="height:30px; padding: 20px 10px;">
                            <input type="file" name="some" style="width: 200px;" />
                            <%--<input type="file" name="some" style="width: 200px;" />--%>
                        </div>
                        <div class="tooltext"><input type="submit" value="上传文件"></div>
                        </form>
                    </div>

                    <div id="toolsEvent" class="tools_item">
                        <div class="toolbars">
                            <ul>
                                 <%--<li id="exportToolbar">--%>
				<%--<div class="dwToolbar_icon"></div>--%>
			<%--</li>--%>
                                <li id="surveyAttrSetToolbar"  class="surveyAttrSetToolbar_li">
                                    <a href="${ctx }/design/my-survey-design!previewDev.action?surveyId=${param['surveyId']}" >
                                        <span class="dwToolbar_icon" title="模板规则设置"></span>
                                    </a>
                                </li>
                            </ul>
                        </div>
                        <div class="tooltext">规则设置</div>
                    </div>
                </div>


                <div id="toolsPubBtn" >
                    <div class="toolbars" style="padding:10px  15px 10px 0px;">
                        <ul>
                            <li id="saveBtn" >
                                <div class="dwToolbar_icon"></div>
                            </li>
                            <!-- <li id="previewBtn" >
				<div class="dwToolbar_icon"></div>
			</li> -->
                        </ul>
                    </div>
                </div>

            </div>
        </div>

    </div>

    <div id="dw_body">
        <div id="dw_body_content">
            <div id="dwSurveyTitle">
                <div id="dwSurveyName" class="editAble dwSvyName">${feedback.name }</div>
            </div>
            <input type="hidden" name="svyNmSaveTag" value="1">
            <div id="dwSurveyNote">
                <div id="dwSurveyNoteTools">参考样例</div>
                <div id="dwSurveyNoteEdit" class="editAble dwSvyNoteEdit"  >${feedback.feedbackDetail.feedbackNote }</div>
                <input type="hidden" name="svyNoteSaveTag" value="1">
            </div>

            <div id="dwSurveyQuContent" style="min-height: 500px;">

                <ul id="dwSurveyQuContentAppUl">
                    <!-- 题目内容 -->
                    <c:forEach items="${feedback.questions }" var="en" varStatus="i">
                        <li class="li_surveyQuItemBody">
                            <c:choose>
                                <%-- 测评表单行 --%>
                                <c:when test="${en.quType eq 'DEGREEFEEDBACK'}">
                                    <div class="surveyQuItemBody">
                                        <div class="initLine"></div>
                                        <div class="quInputCase" style="display: none;">
                                            <input type="hidden" name="quType" value="DEGREEFEEDBACK" >
                                            <input type="hidden" name="quId" value="${en.id}">
                                            <input type="hidden" name="orderById" value="${en.orderById}"/>
                                            <input type="hidden" name="saveTag" value="1">
                                            <input type="hidden" name="hoverTag" value="0">
                                            <input type="hidden" name="isRequired" value="${en.isRequired}">
                                            <input type="hidden" name="hv" value="${en.hv}">
                                            <input type="hidden" name="randOrder" value="${en.randOrder}">
                                            <input type="hidden" name="cellCount" value="${en.cellCount}">

                                            <input type="hidden" name="paramInt01" value="${en.paramInt01}">
                                            <input type="hidden" name="paramInt02" value="${en.paramInt02}">
                                            <div class="quLogicInputCase">
                                                <input type="hidden" name="quLogicItemNum" value="0">
                                            </div>
                                        </div>
                                        <div class="surveyQuItem">
                                            <div class="surveyQuItemLeftTools">
                                                <ul class="surveyQuItemLeftToolsUl">
                                                    <li title="移动排序" class="dwQuMove"><div class="dwQuIcon"></div></li>
                                                    <li title="设置" class="dwQuSet"><div class="dwQuIcon"></div></li>
                                                    <li title="删除" class="dwQuDelete"><div class="dwQuIcon"></div></li>
                                                </ul>
                                            </div>
                                            <div class="surveyQuItemRightTools">
                                                <ul class="surveyQuItemRightToolsUl">
                                                    <li class="questionUp"><div class="dwQuIcon"></div></li>
                                                    <li class="questionDown"><div class="dwQuIcon"></div></li>
                                                </ul>
                                            </div>
                                            <div class="surveyQuItemContent">
                                                <div class="quCoTitle">
                                                    <div class="editAble quCoTitleEdit" >${en.quTitle}</div>
                                                    <input type="hidden" name="quTitleSaveTag" value="1">
                                                </div>
                                                <div class="quCoItem">
                                                    <table class="feedbackTable">
                                                        <tr><th style="width:20%;">评估项目</th><th style="width:20%;">具体评分内容</th><th style="width:60%;">评估内容说明</th></tr>
                                                        <c:forEach items="${en.degreeFeedbackItems}" var="item">
                                                        <tr class="feedbackTableTr">
                                                            <td class="feedbackTableEditTd">
                                                                <label class="editAble quCoOptionEdit">${item.name}</label>
                                                                <div class="quItemInputCase">
                                                                    <input type="hidden" name="quItemId" value="${item.id}" />
                                                                    <input type="hidden" name="quItemSaveTag" value="1" />
                                                                    <input type="hidden" name="quOptionDescSaveTag" value="1" />
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
                                                                <div class="quCoBottomTools" >
                                                                    <ul class="quCoBottomToolsUl" >
                                                                        <li class="addOptionItem" title="添加"><div class="dwQuIcon"></div></li>
                                                                    </ul>
                                                                </div>
                                                            </td>
                                                            <td>
                                                                    <ul class="quItemItemDescEditUl">
                                                                        <c:forEach items="${item.itemList}" var="itemItem">
                                                                            <li class="quItemItemDescEditLi">
                                                                                <label class="editAble quItemItemDescEdit">${itemItem.description}</label>
                                                                                <div class="quItemInputCase">
                                                                                    <input type="hidden" name="quItemItemId" value="${itemItem.id}" />
                                                                                    <input type="hidden" name="quItemItemSaveTag" value="1">
                                                                                </div>
                                                                            </li>
                                                                        </c:forEach>
                                                                    </ul>
                                                                <div class="quCoBottomTools">
                                                                    <ul class="quCoBottomToolsUl" >
                                                                        <li class="addOptionItem2" title="添加"><div class="dwQuIcon"></div></li>
                                                                    </ul>
                                                                </div>
                                                            </td>

                                                        </tr>
                                                        </c:forEach>
                                                    </table>
                                                </div>
                                                <div class="quCoBottomTools" >
                                                    <ul class="quCoBottomToolsUl" >
                                                        <li class="addOption" title="添加"><div class="dwQuIcon"></div></li>
                                                    </ul>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </c:when>

                            </c:choose>
                        </li>
                    </c:forEach>

                    <%--
				<li>
				<div id="defaultAppQuObj" class="surveyQuItemBody"  style="height: 100px;">
					<div class="initLine"></div>
					<input type="hidden" name="quType" value="NOQUESTION">
					<input type="hidden" name="orderById" value="0"/>
				</div>
				</li>
				--%>

                </ul>

            </div>
        </div>
    </div>

</div>

<!-- 各种模板 -->

<!-- 测评指标模板 -->
<table class="modelHtml">
    <tr id="feedbackTableModel">
        <td class="feedbackTableEditTd">
            <label class="editAble quCoOptionEdit">指标1</label>
            <div class="quItemInputCase">
                <input type="hidden" name="quItemId" value="" />
                <input type="hidden" name="quItemSaveTag" value="0" />
                <input type="hidden" name="quOptionDescSaveTag" value="0" />
            </div>
        </td>
        <td>
            <ul class="quItemItemEditUl">
                <li class="quItemItemEditLi">
                    <label class="editAble quItemItemEdit">评分指标1</label>
                    <div class="quItemInputCase">
                        <input type="hidden" name="quItemItemId" value="" />
                        <input type="hidden" name="quItemItemSaveTag" value="0">
                    </div>
                </li>
            </ul>
            <div class="quCoBottomTools" >
                <ul class="quCoBottomToolsUl" >
                    <li class="addOptionItem" title="添加"><div class="dwQuIcon"></div></li>
                </ul>
            </div>
        </td>
        <td>
            <ul class="quItemItemDescEditUl">
                <li class="quItemItemDescEditLi">
                    <label class="editAble quItemItemDescEdit">评分指标1</label>
                    <div class="quItemInputCase">
                        <input type="hidden" name="quItemItemId" value="" />
                        <input type="hidden" name="quItemItemSaveTag" value="0">
                    </div>
                </li>
            </ul>
            <div class="quCoBottomTools">
                <ul class="quCoBottomToolsUl" >
                    <li class="addOptionItem2" title="添加"><div class="dwQuIcon"></div></li>
                </ul>
            </div>
        </td>
    </tr>
</table>
<!-- 测评指标选项模板 -->
<ul class="modelHtml">
    <li id="feedbackTableItemModel" >
        <label class="editAble quItemItemEdit">评分指标1</label>
        <div class="quItemInputCase">
            <input type="hidden" name="quItemItemId" value="" />
            <input type="hidden" name="quItemItemSaveTag" value="0">
        </div>
    </li>
</ul>
<!-- 测评指标描述内容选项模板 -->
<ul class="modelHtml">
    <li id="feedbackTableItemDescModel" >
        <label class="editAble quItemItemDescEdit">评分指标1</label>
        <div class="quItemInputCase">
            <input type="hidden" name="quItemItemId" value="" />
            <input type="hidden" name="quItemItemSaveTag" value="0">
        </div>
    </li>
</ul>

<table class="modelHtml" >
    <tr id="quChenColumnModel">
        <td class="quChenColumnTd">
            <label class="editAble quCoOptionEdit">新项</label>
            <div class="quItemInputCase"><input type="hidden" name="quItemId" value=""><input type="hidden" name="quItemSaveTag" value="0"></div>
        </td>
    </tr>
    <tr id="quChenRowModel">
        <td class="quChenRowTd">
            <label class="editAble quCoOptionEdit">新项</label>
            <div class="quItemInputCase"><input type="hidden" name="quItemId" value=""><input type="hidden" name="quItemSaveTag" value="0"></div>
        </td>
    </tr>
</table>

<!-- 逻辑值保存模板 -->
<div id="quLogicItemModel" class="modelHtml">
    <div class="quLogicItem">
        <input type="hidden" name="quLogicId" value=""/>
        <input type="hidden" name="cgQuItemId" value="0"/>
        <input type="hidden" name="skQuId" value="0"/>
        <input type="hidden" name="visibility" value="0">
        <input type="hidden" name="logicSaveTag" value="0">

        <input type="hidden" name="geLe" value="le">
        <input type="hidden" name="scoreNum" value="2">
        <input type="hidden" name="logicType" value="1">
    </div>
</div>
<table id="setQuLogicItem" style="display: none;">
    <tr id="setQuLogicItemTrModel">
        <td class="ifSpanText1">如果本题回答</td>
        <td><select name="option_id" class="logicQuOptionSel" ></select></td>
        <td>则&nbsp;<select name="option_id" class="logicType"  style="width: 60px;"><option value="2">显示</option><option value="1">跳到</option></select> </td>
        <td><select name="jump_to_qid" class="logicQuSel" ></select></td>
        <td><div class="dialogRemoveLogic"></div></td>
    </tr>
    <tr id="setQuLogicItemTrModel_score"><td class="ifSpanText1">如果选项</td>
        <td><select name="option_id" class="logicQuOptionSel" style="width: 120px;"></select>
            评分&nbsp;<select name="logicScoreGtLt" class="logicScoreGtLt"  style="width: 80px;"><option value="le">小等于</option><option value="ge">大等于</option></select>
            <select name="logicScoreNum" class="logicScoreNum"  style="width: 40px;"><option>2</option><option>3</option></select>&nbsp;分,</td>
        <td>则&nbsp;<select name="logicEvent" class="logicType"  style="width: 60px;"><option value="2">显示</option><option value="1">跳到</option></select> </td>
        <td><select name="jump_to_qid" class="logicQuSel" ></select></td>
        <td><div class="dialogRemoveLogic"></div></td>
    </tr>
</table>

<div id="dwCommonEditRoot"  >
    <div class="dwCommonEdit">
        <ul class="dwComEditMenuUl" >
            <li><a href="javascript:;" class="SeniorEdit"><i class="menu_edit2_icon"></i>高级编辑</a></li>
            <li class="option_Set_Li"><a href="javascript:;" class="option_Set"><i class="menu_edit4_icon"></i>选项设置</a></li>
            <!-- <li><a href="javascript:;" class="reference_Set" style="display: none;"><i class="menu_edit4_icon"></i>引用设置</a></li> -->
        </ul>
        <ul class="dwComEditOptionUl">
            <li class="dwOptionUp"><div class=dwQuIcon></div></li>
            <li class="dwOptionDown"><div class=dwQuIcon></div></li>
            <li class="dwOptionDel"><div class=dwQuIcon></div></li>
        </ul>
        <ul class="dwComEditOptionItemUl">
            <li class="dwOptionItemUp"><div class=dwQuIcon></div></li>
            <li class="dwOptionItemDown"><div class=dwQuIcon></div></li>
            <li class="dwOptionItemDel"><div class=dwQuIcon></div></li>
        </ul>
        <div class="dwComEditMenuBtn"></div>
        <div id="dwComEditContent" contenteditable="true" >请问你的年级是？</div>
    </div>
</div>

<div id="dialog" title="Basic dialog">
    <div id="editDialogCenter" class="editDialogCenter">
        <div id="dialogUeditor" ></div>
    </div>
    <div id="dialogUeBottom">
        <div class="dwQuDialogBtnCon" ><input type="button" value="保存" class="quDialogBtn" id="dwDialogUeOk"/></div>
    </div>
</div>

<div id="dwCommonDialog">
    <div class="dwCommonRefIcon"><div class="dwCommonRefIcon1"></div><div class="dwCommonRefIcon2"></div></div>
    <div class="dwCommonDialogBody">
        <div  class="dwCommonDialogTitle"><span id="dwComDialogTitleText">标题文本</span><span id="dwCommonDialogClose" class="closeDialog"></span></div>
        <div class="dwCommonDialogContent">
            <!-- 默认显示的LOAD -->
            <div class="dwQuDialogLoad dwQuDialogCon"><img alt="" src="${ctx }/images/load.gif"></div>
            <!-- 题目设置 -->

            <div class="dwQuSetCon dwQuFormSetDialog dwQuDialogCon" >
                <ul>
                    <!-- <li><input type="checkbox" name="quChage"><label>切换为多选</label> </li> -->
                    <li><label><input type="checkbox" name="setIsRequired"  >此题必答</label> </li>
                    <li class="optionAutoOrder"><label><input type="checkbox" name="setRandOrder" >选择随机排列</label> </li>
                    <li class="contactsAttrLi"><label><input type="checkbox" name="setAutoContacts" >关联到联系人属性</label> </li>
                    <li class="contactsFieldLi"><label>用户填写的内容，会成为联系人的</label>
                        <select class="contacts_range" name="setContactsField"  style="width:120px;">
                            <option value="1">姓名</option>
                            <option value="2">手机</option>
                            <option value="3">地址</option>
                            <option value="4">生日</option>
                            <option value="5">Email</option>
                            <option value="6">性别</option>
                            <option value="7">公司</option>
                            <option value="8">城市</option>
                            <option value="9">婚姻</option>
                            <option value="10">收入</option>
                        </select>
                    </li>
                    <li class="optionRangeHv"><label>选项排放：</label>
                        <select class="option_range" name="setHv"  style="width:120px;">
                            <option value="2">竖排</option>
                            <option value="1">横排</option>
                            <option value="3">按列</option>
                            <!-- <option value="4">下拉显示</option> -->
                        </select>
                        <span class="option_range_3" style="display:none;"><input type="text" name="setCellCount"  size="2" value="2">&nbsp;列</span>
                    </li>
                    <!-- <li class="minNumLi">最少选&nbsp;<input type="text" size="3" name="minNum">&nbsp;项 </li>
					<li class="maxNumLi">最多选&nbsp;<input type="text" size="3" name="maxNum">&nbsp;项 </li> -->
                    <li class="minMaxLi">
                        <span class="minSpan"><label class="lgleftLabel">&nbsp;最低分</label>&nbsp;<input class="minNum" value="1"  type="text" size="2" >&nbsp; <label class="lgRightLabel">分</label></span>&nbsp;&nbsp;
                        <span class="maxSpan"><label class="lgLeftLabel">最高分</label>&nbsp;<input class="maxNum"  value="5"  type="text" size="2" >&nbsp;<label class="lgRightLabel">分</label> </span>
                    </li>
                    <li class="scoreMinMax">&nbsp;&nbsp;<label>最高分</label>&nbsp;<select class="maxScore"  ><option value="5">5分</option><option value="10">10分</option></select>&nbsp; </li>
                </ul>
                <div class="dwQuDialogBtnCon" ><input type="button" value="保存" class="quDialogBtn" id="dwDialogQuSetSave"/></div>
            </div>

            <!-- 逻辑设置 -->
            <div class="dwQuDialogLogic dwQuDialogCon">
                <div class="dwQuDialogLogicTitle">逻辑设置</div>
                <table id="dwQuLogicTable">
                    <!-- <tr><td>如果本题选项选中</td>
						<td><select name="option_id" class="option_select" ><option>任意选项</option></select></td>
						<td>则跳转到</td>
						<td><select name="jump_to_qid" class="jump_qid_select" ><option value="">-请选择题目-</option><option value="1">正常结束（计入结果）</option><option value="2">提前结束（不计入结果）</option></select></td>
						<td><div class="dialogRemoveLogic"></div></td>
					</tr> -->
                    <!-- <tr><td>如果选项</td>
						<td><select name="option_id" class="option_select" style="width: 120px;"><option>任意选项</option></select>
							评分&nbsp;<select name="option_id" class="option_select"  style="width: 60px;"><option>小于</option><option>大于</option></select>
						<select name="option_id" class="option_select"  style="width: 40px;"><option>2</option><option>3</option></select>&nbsp;分,</td>
						<td>则&nbsp;<select name="option_id" class="option_select"  style="width: 60px;"><option>显示</option><option>跳到</option></select> </td>
						<td><select name="jump_to_qid" class="jump_qid_select" ><option value="" style="width: 140px;">-请选择题目-</option><option value="1">正常结束（计入结果）</option><option value="2">提前结束（不计入结果）</option></select></td>
						<td><div class="dialogRemoveLogic"></div></td>
					</tr> -->
                </table>
                <div class="dwQuDialogBotEvent"><div class="dwQuDialogAddLogic"><div class="dwQuIcon"></div></div></div>
                <div class="dwQuDialogBtnCon" ><input type="button" value="保存" class="quDialogBtn" id="dwDialogSaveLogic"/></div>
            </div>

            <!-- 批量添加，单选 -->
            <div class="dwQuAddMore dwQuDialogCon"  >
                <div class="dwQuTextSpan">每行一个选项</div>
                <textarea id="dwQuMoreTextarea"></textarea>
                <div class="dwQuDialogBtnCon" ><input type="button" value="保存" class="quDialogBtn" id="dwDialogSaveMoreItem"/></div>
            </div>


        </div>
    </div>
</div>

<div id="modelUIDialog">

    <div id="modelUIDialogContent" >
        <!-- 填空题  填空数据类型设置  -->
        <div class="dwQuFillDataTypeOption dwQuFormSetDialog dwQuDialogCon" >
            <ul>
                <li><label>输入框宽：</label>
                    <input type="text" name="qu_inputWidth" value="300"><span>&nbsp;字符</span>
                </li>
                <li><label>输入框高：</label>
                    <input type="text" name="qu_inputRow" value="1"><span>&nbsp;行</span>
                </li>
                <li><label>数据类型：</label>
                    <select class="option_range" name="quFill_checkType"  style="width:120px;">
                        <option value="NO">无验证</option>
                        <option value="EMAIL">Email</option>
                        <!-- <option value="STRLEN">字符长度</option> -->
                        <option value="UNSTRCN">禁止中文</option>
                        <option value="STRCN">仅许中文</option>
                        <option value="NUM">数值</option>
                        <option value="TELENUM">电话号码</option>
                        <option value="PHONENUM">手机号码</option>
                        <option value="DATE">日期</option>
                        <option value="IDENTCODE">身份证号</option>
                        <option value="ZIPCODE">邮政编码</option>
                        <option value="URL">网址</option>
                        <!-- <option value="4">下拉显示</option> -->
                    </select>
                    <span class="option_range_3" style="display:none;">&nbsp;列</span>
                </li>
                <!-- <li>最少选&nbsp;<input type="text" size="3">&nbsp;项 </li>
					<li>最多选&nbsp;<input type="text" size="3">&nbsp;项 </li> -->
            </ul>
            <div class="dwQuDialogBtnCon" ><input type="button" value="保存" class="quDialogBtn" id="dwDialogQuFillOptionSave"/></div>
        </div>

        <div class="dwQuRadioCheckboxOption dwQuFormSetDialog dwQuDialogCon" >
            <ul>
                <li><label>选项设置</label></li>
                <li class="quOptionAddFill"><label><input type="checkbox" name="quOption_isNote" >选项后添加填空</label> </li>
                <li class="quOptionFillContentLi"><label style="padding-left:16px;">填空内容：</label>
                    <select class="option_range" name="quOption_checkType"  style="width:120px;">
                        <option value="NO">无限制</option>
                        <option value="EMAIL">Email</option>
                        <!-- <option value="STRLEN">字符长度</option> -->
                        <option value="UNSTRCN">禁止中文</option>
                        <option value="STRCN">仅许中文</option>
                        <option value="NUM">数值</option>
                        <option value="TELENUM">电话号码</option>
                        <option value="PHONENUM">手机号码</option>
                        <option value="DATE">日期</option>
                        <option value="IDENTCODE">身份证号</option>
                        <option value="ZIPCODE">邮政编码</option>
                        <option value="URL">网址</option>
                        <!-- <option value="4">下拉显示</option> -->
                    </select>
                    <span class="option_range_3" style="display:none;">&nbsp;列</span>
                </li>
                <li class="quOptionFillRequiredLi"><label style="padding-left:15px;"><input type="checkbox" name="quOption_isRequiredFill" checked="checked" >&nbsp;该空可不填</label>&nbsp;</li>
                <!-- <li>最多选&nbsp;<input type="text" size="3">&nbsp;项 </li> -->
            </ul>
            <div class="dwQuDialogBtnCon" ><input type="button" value="保存" class="quDialogBtn" id="dwDialogQuOptionSetSave"/></div>
        </div>

        <div class="dwSurveyAttrSetDialog dwQuFormSetDialog dwQuDialogCon" style="padding: 0;">
            <div class="tabbarDialog_1" >
                <div class="p_DialogContent" >
                    <input type="hidden" name="svyAttrSaveTag" value="1">
                    <div class="p_DialogContentRoot" style="width: 480px;">
                        <div class="p_DialogContentItem">
                            <div class="p_DialogContentItem">
                                <label for="ownWeight">自评权重</label><input type="text" size="10" id="ownWeight" name="ownWeight" class="inputSytle_1" value="${feedback.feedbackDetail.ownWeight}">
                                <span>需要自评直接填写权重，不需要自评权重设置为0</span>
                            </div>
                            <div class="p_DialogContentItem"><label for="topWeight">上级权重</label><input type="text" size="10" id="topWeight" name="topWeight" class="inputSytle_1" value="${feedback.feedbackDetail.topWeight}"></div>
                            <div class="p_DialogContentItem"><label for="middleWeight">同级权重</label><input type="text" size="10" id="middleWeight" name="middleWeight" class="inputSytle_1" value="${feedback.feedbackDetail.middleWeight}"></div>
                            <div class="p_DialogContentItem"><label for="bottomWeight">下级权重</label><input type="text" size="10" id="bottomWeight" name="bottomWeight" class="inputSytle_1" value="${feedback.feedbackDetail.bottomWeight}"></div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="dwQuDialogBtnCon" ><input type="button" value="保存" class="quDialogBtn" id="dwDialogSurveyAttrSave"/></div>
        </div>






    </div>
</div>


<%@ include file="/WEB-INF/page/layouts/other.jsp"%>

<div style="text-align: center;">
    <div class="dw_foot" style="padding-bottom: 30px;">
        <div class="footer-copyright" style="color: gray;padding-top: 0px;font-size: 16px;">
            Powered by <a href="http://www.dwsurvey.net" target="_blank" style="text-decoration: none;color: gray;">DWSurvey3.0</a>&nbsp;&nbsp;&nbsp;
            Copyright © 2012-2017
            <a href="http://www.diaowen.net" target="_blank" style="text-decoration: none;color: rgb(53, 117, 136);">调问网</a>
        </div>
    </div>
</div>

</body>
</html>