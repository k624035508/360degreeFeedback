<%@include file="/common/taglibs.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link href="${ctx }/js/plugs/font-awesome-4.2.0/css/font-awesome.css" rel="stylesheet">

    <script src="${ctx }/js/plugs/echarts/echarts-master/dist/echarts.js"></script>
    <script src="${ctx }/js/plugs/echarts/echarts-master/theme/shine.js"></script>
    <link type="text/css" rel="stylesheet" href="${ctx}/css/report-detail.css" />
    <title>考核报告</title>
    <shiro:lacksRole name="admin">
    <script>
        $(function () {
            $(".header_menu ul li").eq(2).addClass("menu-select");
        })
    </script>
    </shiro:lacksRole>
    <shiro:hasRole name="admin">
    <script>
        $(function () {
            $(".header_menu ul li").eq(3).addClass("menu-select");
        })
    </script>
    </shiro:hasRole>
    <script type="text/javascript">
        $(function () {
            var quId = $(".report-content").find("input[name='quId']").val();
            var amchartdivId = "column_chart_" + quId;
            var amchartdivObj = $("#" + amchartdivId);
            if(!amchartdivObj[0]) {
                $("#amchart_"+quId).find(".higChartSvg").hide();
                higColumnChart(quId);
            } else {
                $("#amchart_"+quId).find(".higChartSvg").hide();
                amchartdivObj.show();
            }
            return false;
        });

        function getHighchartsData(quItemBody, charType){
            var categories=[];
            var series=new Array();
            var seriesData=new Array();
            var tagText="次数";
            var legendData=new Array();
            var seriesType = 'bar';

            var seriesDataTemp="[";
            var quItemOptions = quItemBody.find(".quSpanOptions");
            $.each(quItemOptions, function (i, item) {
                var quOptionName=$(this).find(".optionName").text();
                categories.push(quOptionName);
                //平均分
                var avgScore=$(this).find("input[name='quItemAvgScore']").val();
                if(charType==="PIE"){
                    //seriesData.push([quOptionName,   parseFloat(avgScore)]);
                    var data = {};
                    data["value"] = parseFloat(avgScore);
                    data["name"] = quOptionName;
                    seriesData.push(data);
                }else{
                    seriesData.push(parseFloat(avgScore));
                }
                tagText="分数";
            });
            if(charType==="PIE"){
                /*
                 series=[{
                 type: 'pie',
                 data: seriesData
                 }];
                 */
                series = seriesData;
            }else if(charType === "BAR"){
                series=[{
                    //name: '选项',
                    name: tagText,
                    type: 'bar',
                    data: seriesData
                }];
            }else{
                /*series=[{                                 //指定数据列
                 name: '选项',                          //数据列名
                 data: seriesData                      //数据
                 }];*/
                //series = seriesData;
                series = [{
                    //name: '选项',
                    name: tagText,
                    //type: 'bar',
                    type: seriesType,
                    // barWidth: 80,
                    //data: [5, 20, 36, 10, 10, 20]
                    //data: [5, 20]
                    data: seriesData
                }]
            }
            return [categories,series,tagText,legendData];
        }

        function higColumnChart(quId) {
            var chartdivId = "column_chart_" + quId;
            $("#amchart_" + quId).prepend("<div id='"+ chartdivId + "' class=\"higChartSvg\"></div>");
            $("#"+chartdivId).css({"height": "300px"});
            var quItemBody = $(".report-body");
            var quTitle = "能力维度分析";
            var datas = getHighchartsData(quItemBody,"column");
            var categories=datas[0];
            var series=datas[1];
            var tagText=datas[2];
            var legendData = datas[3];
            var myChart = echarts.init($('#'+chartdivId)[0],"shine");
            // 指定图表的配置项和数据
            var option = {
                title: {
                    text: quTitle,
                    //     subtext: '来自调问 www.diaowen.net',
                    top: 8,
                    /*  left: 10, */
                    textStyle: {
                        fontSize: 16
                    },
                    x:'center'
                },
                toolbox: {
                    show : true,
                    feature : {
                        // magicType : {show: true, type: ['line', 'bar']},
                        saveAsImage : {show: true}
                    }
                },
                color: ['#7cb5ec'],
                backgroundColor: '#fff',
                tooltip : {
                    trigger: 'axis',
                    axisPointer : {            // 坐标轴指示器，坐标轴触发有效
                        type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                    },
                    top:20
                },
                grid: {
                    left: '3%',
                    right: '3%',
                    bottom: '5%',
                    containLabel: true
                },
                xAxis: {
                    //data: ["衬衫","羊毛衫","雪纺衫","裤子","高跟鞋","袜子"],
                    type : 'category',
                    data: categories,
                    axisTick: {
                        alignWithLabel: true
                    },
                    nameGap: 20,
                    axisLabel: {
                        //rotate: 5,
                        interval: 0,
                        margin: 15
                    },
                    axisLine:{
                        show: false,
                        lineStyle:{width: 1}
                    },
                    axisTick: {
                        show: false
                    }/*,
                     splitLine: {show:true}

                     splitArea:{
                     interval: 0,
                     show : true
                     } */
                },
                yAxis: {
                    //minInterval: 2,
                    splitNumber: 5,
                    //nameLocation: 'middle',
                    //boundaryGap: ['30%', '30%'],
                    //nameGap: 20,
                    type:'value',
                    axisLine: {
                        show: false
                    },
                    axisTick: {
                        show: false
                    },
                    name: tagText
                },
                legend: {
                    bottom: 0,
                    data:legendData
                },
                series: series
            };
            // 使用刚指定的配置项和数据显示图表。
            myChart.setOption(option);
        }
    </script>
</head>
<body>
<div class="report-body">
    <div class="report-header">
        ${review.name}--个人报告
    </div>
    <div class="report-name">
        <div class="answer-name">姓名：${user.name}</div>
        <div class="answer-name">部门：
            <c:forEach items="${departmentList}" var="department">
                <c:if test="${department.id eq user.department}">${department.name}</c:if>
            </c:forEach>
        </div>
            <div>
                <a href="/report/feedback-report!downloadExcel.action?reviewId=${reviewId}&userId=${user.id}" class="dw_btn025">
                    <i class="fa fa-download">下载数据</i>
                </a>
            </div>
    </div>
    <c:forEach items="${feedbackProject.questions}" var="en">
    <div class="report-content">
        <input type="hidden" name="quId" value="${en.id }">
    <ul class="report-left">
        <li>考核项目</li>
        <li>考核分解</li>
        <c:forEach items="${feedbackDimensionList}" var="dimension">
            <li>${dimension.name}
                <c:forEach items="${reviewDimensionList}" var="reviewDimension">
                    <c:if test="${reviewDimension.dimensionId eq dimension.id}">
                        (${reviewDimension.weight}%)
                    </c:if>
                </c:forEach>
            </li>
        </c:forEach>
       <li>总得分</li>
    </ul>
        <div class="report-item-div">
            <div class="report-item-content">
        <c:forEach items="${en.degreeFeedbackItems}" var="items">
            <ul class="report-item">
                <li>${items.name}</li>
                <li>
                    <c:forEach items="${items.itemList}" var="itemItem">
                        <span class="quSpanOptions">
                            <i class="optionName" style="font-style: normal;">${itemItem.name}</i>
                            <input name="quItemAvgScore" type="hidden" value='<fmt:formatNumber type="number" value="${itemItem.avgScore}" pattern="#0.00" />' />
                        </span>
                    </c:forEach>
                </li>
                <c:forEach items="${feedbackDimensionList}" var="feedbackDimension">
                    <li>
                        <c:forEach items="${tempScoreList}" var="score">
                            <c:if test="${score.dimensionId eq feedbackDimension.id}">
                                <c:forEach items="${items.itemList}" var="itemItem">
                                    <c:if test="${(score.itemItemId eq itemItem.id)}">
                                        <span><fmt:formatNumber type="number" value="${score.avgScores}" pattern="#.00" /></span>
                                    </c:if>
                                </c:forEach>
                            </c:if>
                        </c:forEach>
                    </li>
                </c:forEach>
                <li>
                    <c:forEach items="${items.itemList}" var="itemItem">
                        <span style="color:#464646; font-weight: bold;">
                            <fmt:formatNumber type="number" value="${itemItem.avgScore}" pattern="#0.00" />
                        </span>
                    </c:forEach>
                </li>
            </ul>
        </c:forEach>
            <div style="clear:both;"></div>
        </div>
        </div>
    <div style="clear:both;"></div>
    </div>
    <div class="reportPic">
        <div class="chartBtnEvent">
            <%--<a href="javascript:" class="dw_btn026 columnchart_pic"><i class="fa fa-bar-chart"></i>柱状图</a>--%>
        </div>
        <div id="amchart_${en.id }" ></div>
    </div>
    </c:forEach>
</div>
</body>
</html>
