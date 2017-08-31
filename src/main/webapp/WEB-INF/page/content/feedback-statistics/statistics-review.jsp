<%@include file="/common/taglibs.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link href="${ctx }/js/plugs/font-awesome-4.2.0/css/font-awesome.css" rel="stylesheet">

    <script src="${ctx }/js/plugs/echarts/echarts-master/dist/echarts.js"></script>
    <script src="${ctx }/js/plugs/echarts/echarts-master/theme/shine.js"></script>
    <link type="text/css" rel="stylesheet" href="${ctx}/css/report-detail.css" />
    <title>考核报告</title>
    <script type="text/javascript">
        $(function () {
            $(".header_menu ul li").eq(3).addClass("menu-select");
            $(".columnchart_pic").click(function () {
                var th=$(this);
                var quId = th.parent().parent().parent().find("input[name='itemItemId']").val();
                var amchartdivId = "column_chart_" + quId;
                var amchartdivObj = $("#" + amchartdivId);
                if (!amchartdivObj[0]) {
                    $("#amchart_" + quId).find(".higChartSvg").hide();
                    higColumnChart(quId);
                } else {
                    amchartdivObj.toggle();
                }
                return false;
            })
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
                    data: seriesData,
                    //系列中的数据标线内容
                    markLine: {
                        data: [
                            {type: 'average', name: '平均值'}
                        ]
                    }
                }]
            }
            return [categories,series,tagText,legendData];
        }

        function higColumnChart(quId) {
            var chartdivId = "column_chart_" + quId;
            $("#amchart_" + quId).prepend("<div id='"+ chartdivId + "' class=\"higChartSvg\"></div>");
            $("#"+chartdivId).css({"height": "300px"});
            var quItemBody = $("#report_"+quId);
            var quTitle = "";
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
    <script type="text/javascript" src="${ctx }/js/plugs/zeroclipboard-master/dist/ZeroClipboard.js"></script>
</head>
<body>
<div class="report-body2">
    <div class="report-header">
        (${review.name})--个人对比报告
    </div>
    <c:forEach items="${feedbackProject.questions}" var="en">
        <div class="report-content">
            <div class="report-feedbackItem">
                <div class="itemDesc">总体平均分：<fmt:formatNumber type="number" value="${allUserTotalAvgScore}" pattern="#.00" />
                    <a href="javascript:" id="clipLink" data-clipboard-text="${baseUrl}/statistics/feedback-statistics!viewStatistics.action?reviewId=${reviewId}" class="clipLink dw_btn025" style="float: right;">
                        <i class="fa fa-share">分享报告数据</i>
                    </a>
                </div>
                <table>
                    <tr><td>人员</td><td>得分</td><td>差距</td><td>详细报告</td></tr>
                    <c:forEach items="${userList}" var="user">
                        <c:forEach items="${tempScoreTotalScores}" var="tempScoreTotalScore">
                            <c:if test="${tempScoreTotalScore.userId eq user.id}">
                        <tr><td>${user.name}</td>
                            <td><fmt:formatNumber type="number" value="${tempScoreTotalScore.avgScores}" pattern="#.00" /></td>
                            <td>
                                    <c:if test="${tempScoreTotalScore.avgScores-allUserTotalAvgScore>=0}">
                                        <span style="color: green;">+<fmt:formatNumber type="number" value="${tempScoreTotalScore.avgScores-allUserTotalAvgScore}" pattern="#.00" /></span>
                                    </c:if>
                                <c:if test="${tempScoreTotalScore.avgScores-allUserTotalAvgScore<0}">
                                    <span style="color: red;"><fmt:formatNumber type="number" value="${tempScoreTotalScore.avgScores-allUserTotalAvgScore}" pattern="#.00" /></span>
                                </c:if>
                            </td>
                            <td>
                                <a class="checkDetail" target="_blank" href="${ctx}/statistics/feedback-statistics!personalReport.action?reviewId=${reviewId}&userId=${user.id}">个人测评报告</a>
                            </td>
                        </tr>
                            </c:if>
                        </c:forEach>
                    </c:forEach>
                </table>
            </div>

            <c:forEach items="${en.degreeFeedbackItems}" var="items" varStatus="key">
                <c:forEach items="${items.itemList}" var="itemItem" varStatus="keyItem">
                    <div class="report-feedbackItem" id="report_${itemItem.id}">
                        <input type="hidden" name="itemItemId" value="${itemItem.id}" />
                        <div class="itemDesc">${key.index*fn:length(items.itemList)+keyItem.count}、[${itemItem.name}]${itemItem.description}</div>
                        <table>
                            <tr><td>人员</td>
                                <c:forEach items="${userList}" var="user">
                                    <td>
                                        <span class="quSpanOptions">
                                            <i class="optionName" style="font-style: normal;">${user.name}</i>
                                        <c:forEach items="${tempScoreList}" var="tempScore">
                                            <c:if test="${user.id eq tempScore.userId && itemItem.id eq tempScore.itemItemId}">
                                                <input name="quItemAvgScore" type="hidden" value='<fmt:formatNumber type="number" value="${tempScore.avgScores}" pattern="#0.00" />' />
                                            </c:if>
                                        </c:forEach>
                                        </span>
                                    </td>
                                </c:forEach>
                                <td>平均分</td>
                            </tr>
                            <tr>
                                <td>分数</td>
                                <c:forEach items="${userList}" var="user">
                                    <c:forEach items="${tempScoreList}" var="tempScore">
                                        <c:if test="${user.id eq tempScore.userId && itemItem.id eq tempScore.itemItemId}">
                                            <td><fmt:formatNumber type="number" value="${tempScore.avgScores}" pattern="#.00" /></td>
                                        </c:if>
                                    </c:forEach>
                                </c:forEach>
                                <td><fmt:formatNumber type="number" value="${itemItem.avgScore}" pattern="#.00" /></td>
                            </tr>
                        </table>
                        <div class="reportPic">
                            <div class="chartBtnEvent" style="height: 25px;">
                                <a href="javascript:" style=" float: right;" class="dw_btn026 columnchart_pic"><i class="fa fa-bar-chart"></i>柱状图</a>
                            </div>
                            <div id="amchart_${itemItem.id }" ></div>
                        </div>
                    </div>
                </c:forEach>
            </c:forEach>
        </div>
    </c:forEach>
</div>
<script type="text/javascript">
    //复制链接
    var client = new ZeroClipboard( document.getElementById("clipLink") );
    client.on( "ready", function( readyEvent ) {
        client.on( "aftercopy", function( event ) {
            alert("报告链接地址已复制");
        } );
    } );
</script>
</body>
</html>
