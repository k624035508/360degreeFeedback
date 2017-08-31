<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp" %>
<%@include file="/WEB-INF/page/layouts/other.jsp"%>
<script src="${ctx }/js/plugs/bootstrap-3.3.0-dist/dist/js/bootstrap.js"></script>
<link rel="stylesheet" href="${ctx }/js/plugs/bootstrap-3.3.0-dist/dist/css/bootstrap.css">
<script>
	$(function () {
        $('.dropdown-toggle').dropdown();
    })
</script>
<div id="header" >
		<div id="headerCenter"  class="bodyCenter">
			<div class="header_Item header_logo">
			<%--
			<a href="${ctx }/">
				<img alt="调问网" src="${ctx }/images/logo/LOGO.png" >
			</a> 
			--%>
			<%@ include file="logo-img.jsp"%>
			</div>
			<shiro:guest>
				<div class="header_Item header_menu">
				<ul>
				<%--<li><a href="/" class="active dw-menu-a" id="indexMenu">首页</a></li>--%>
				<%--<li><a href="/feature.jsp" class="dw-menu-a" id="featureMenu">功能</a></li>--%>
				<%--<li><a href="${ctx }/survey-model.action" class="dw-menu-a">下载</a></li>--%>
				<%--<li><a href="${ctx }/survey-model.action" class="dw-menu-a">帮助</a></li>--%>
				<%--<li><a href="${ctx }/survey-model.action" class="dw-menu-a">GITHUB</a></li>--%>
				<!--
				<li><a href="http://support.diaowen.net/" class="dw-menu-a" id="helpMenu">帮助</a></li>
				-->
				</ul>
				</div>
				<div class="header_Item header_user" style="float: right;">
					<a href="${ctx }/login.jsp" class="btn-a-1">登录</a>
				</div>
			</shiro:guest>
			
			<shiro:user>
				<div class="header_Item header_menu">
					<ul>
					<%-- <li><a href="${ctx }/" >首页</a></li> --%>
						<shiro:hasRole name="admin" >
							<li><a href="${ctx }/design/my-survey.action">问卷管理</a></li>
							<li class="dropdown" id="accountmenu">
								<a class="dropdown-toggle" data-toggle="dropdown" href="#">测评管理<b class="caret"></b></a>
								<ul class="dropdown-menu">
										<li><a href="${ctx }/design/my-feedback.action">测评模板</a></li>
										<li><a href="${ctx }/review/design/feedback-review.action">考核表</a></li>
										<li><a href="${ctx}/statistics/feedback-statistics.action">测评报告</a></li>
								</ul>
							</li>
					<li><a href="${ctx }/sy/user/user-admin.action">用户管理</a></li>
						</shiro:hasRole>

						<shiro:hasRole name="leader">
							<li><a href="${ctx }/design/my-survey.action">问卷管理</a></li>
							<li class="dropdown">
								<a class="dropdown-toggle" data-toggle="dropdown" href="#">测评管理<b class="caret"></b></a>
								<ul class="dropdown-menu">
									<li><a href="${ctx }/design/my-feedback.action">测评模板</a></li>
									<li><a href="${ctx }/review/design/feedback-review.action">考核表</a></li>
									<li><a href="${ctx}/statistics/feedback-statistics.action">测评报告</a></li>
								</ul>
							</li>
							<li class="dropdown" >
								<a class="dropdown-toggle" data-toggle="dropdown" href="#">测评填写<b class="caret"></b></a>
								<ul class="dropdown-menu">
									<li><a href="${ctx}/my-pend-evaluate.action">待评考核</a></li>
									<li><a href="${ctx}/my-pend-evaluate!haveEvaluate.action">已评价数据查看</a></li>
									<li><a href="${ctx}/report/feedback-report.action">我的被评考核报告</a></li>
								</ul>
							</li>
						</shiro:hasRole>

						<shiro:hasRole name="worker">
							<li><a href="${ctx}/my-pend-evaluate.action">待评考核</a></li>
							<li><a href="${ctx}/my-pend-evaluate!haveEvaluate.action">已评价数据查看</a></li>
							<li><a href="${ctx}/report/feedback-report.action">我的被评考核报告</a></li>
						</shiro:hasRole>
					</ul>
				</div>
				<div class="header_Item header_user" style="float: right;margin-top: 12px;position: relative;zoom: 1;z-index: 165;">
					<a href="#" class="clickHideUserMenu">
						<span class="head_use_name">
						<shiro:principal></shiro:principal>
						</span>
						<span class="header_user_icon">&nbsp;</span>
					</a>
					<div class="a-w-sel a-w-sel-head" style="">
		            	<div class="w-sel" style="margin-top: 16px;">
		                	<div class="selc">
		                    	<div class="selcc tbtag">
		                            <div class="seli"><a class="nx-1" href="${ctx }/ic/user!myaccount.action">修改密码</a></div>
		                            <div class="seli"><a class="nx-7" href="http://support.diaowen.net/">帮助及反馈</a></div>
		                            <div class="seli"><a class="nx-8" href="${ctx }/login!logout.action">退出</a></div>
		                        </div>
		                    </div>
		                </div>
		            </div>
				</div>
			</shiro:user>
		</div>
		<div style="clear: both;"></div>
	</div>