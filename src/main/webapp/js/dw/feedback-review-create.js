jQuery.extend(jQuery.validator.messages, {
    required: "考核表名称不能为空"
});

$(function () {
    //考核人modal
    $("a.selectDimensionClass").click(function () {
        $(this).next().find(".modal-middle iframe").attr("src", "");
        $(this).next().modal();
    });
    //选择模板时清空数据
    $("select").bind("change",function(){
        $("#examineeValue").val("");
        $(".examinee-table .examinee-tr").remove();
        $("#top-textarea").val("")
        $("#middle-textarea").val("");
        $("#bottom-textarea").val("");
    });
    //被考核人modal
    $("a#selectExaminee").click(function () {
        $("#examineeModal2 .div-set .div-set-top").show();
        $("#examineeModal2 .div-set .div-set-middle").show();
        $("#examineeModal2 .div-set .div-set-bottom").show();
        var ownWeight = $("option:selected").data()["ownWeight"];
        var topWeight = $("option:selected").data()["topWeight"];
        var middleWeight = $("option:selected").data()["middleWeight"];
        var bottomWeight = $("option:selected").data()["bottomWeight"];
        console.log(ownWeight,topWeight,middleWeight,bottomWeight);
        if(topWeight == "0") {
            $("#examineeModal2 .div-set .div-set-top").hide();
        }
        if(middleWeight == "0") {
            $("#examineeModal2 .div-set .div-set-middle").hide();
        }
        if(bottomWeight == "0") {
            $("#examineeModal2 .div-set .div-set-bottom").hide();
        }
        $("#examineeModal2").modal();
    });

    var examineeValue = "";
    examineeValue += "{";
    var arrayOwn = $("#own-textarea").val().split("\n");//获取内容按\n分割成数组 被评人
    var arrayTop = $("#top-textarea").val().split("\n"); // 上级
    var arrayMiddle = $("#middle-textarea").val().split("\n"); //同级
    var arrayBottom = $("#bottom-textarea").val().split("\n"); //下级
    for(var i=0; i<arrayOwn.length; i++){
        if(arrayTop[i]==undefined){arrayTop[i]="";}
        if(arrayMiddle[i]==undefined){arrayMiddle[i]="";}
        if(arrayBottom[i]==undefined){arrayBottom[i]="";}
        var examineeOwnValue = "";
        examineeOwnValue += arrayOwn[i] ;
        examineeOwnValue += "={";
        if(arrayTop[i]!=""){
            examineeOwnValue += arrayTop[i];
            examineeOwnValue += "=2,";
        }
        if(arrayMiddle[i]!=""){
            examineeOwnValue += arrayMiddle[i];
            examineeOwnValue += "=3,";
        }
        if(arrayBottom[i]!=""){
            examineeOwnValue += arrayBottom[i];
            examineeOwnValue += "=4,";
        }
        examineeOwnValue += "},"
        examineeValue += examineeOwnValue;
    }
    examineeValue += "}";
    console.log(examineeValue);
    $("#examineeValue").val(examineeValue);

    $(".textarea-submit").click(function () {
        var examineeValue = "";
        examineeValue += "{";
        $(".examinee-table").find(".examinee-tr").remove();
        var arrayOwn = $("#own-textarea").val().split("\n");//获取内容按\n分割成数组 被评人
        var arrayTop = $("#top-textarea").val().split("\n"); // 上级
        var arrayMiddle = $("#middle-textarea").val().split("\n"); //同级
        var arrayBottom = $("#bottom-textarea").val().split("\n"); //下级
        for(var i=0; i<arrayOwn.length; i++) {
            if (arrayTop[i] == undefined) {
                arrayTop[i] = "";
            }
            if (arrayMiddle[i] == undefined) {
                arrayMiddle[i] = "";
            }
            if (arrayBottom[i] == undefined) {
                arrayBottom[i] = "";
            }
            var examineeOwnValue = "";
            if (arrayOwn[i] != "") {
            examineeOwnValue += arrayOwn[i];
            examineeOwnValue += "={";
            if (arrayTop[i] != "") {
                examineeOwnValue += arrayTop[i];
                examineeOwnValue += "=2,";
            }
            if (arrayMiddle[i] != "") {
                examineeOwnValue += arrayMiddle[i];
                examineeOwnValue += "=3,";
            }
            if (arrayBottom[i] != "") {
                examineeOwnValue += arrayBottom[i];
                examineeOwnValue += "=4,";
            }
            examineeOwnValue += "},";
            examineeValue += examineeOwnValue;
            $(".examinee-table").append("<tr class='examinee-tr'><td>" + arrayOwn[i] + "</td><td>" + arrayTop[i] + "</td><td>" + arrayMiddle[i] + "</td><td>" + arrayBottom[i] + "</td></tr>");
        }
        }
        examineeValue += "}";
        console.log(examineeValue);
        var postExamineeValue = examineeValue.replace(/\&/g, "%26");
        $.ajax({
            type: "post",
            url: "/review/design/feedback-review!checkExamineeUn.action",
            data: "examineeValue=" + postExamineeValue,
            success: function (msg) {
                var jsons=eval("("+msg+")");
                $("td.ac-input-error label").remove();
                if( jsons.result == true) {
                    $("#examineeValue").val(examineeValue);
                } else {
                    $("td.ac-input-error").append("<label class='error'>" + jsons.name + "为非法用户</label>");
                }
            },
            error: function(){
                console.error("error");
            }
        })
    });
    //规则验证
    $("#inputReviewForm").validate({
        rules:{
            name:{required:true}
        },
        errorPlacement: function (error, element) {
            element.parent().append(error);
        },
        submitHandler:function (form) {
            $("#dimension_1_weight").val($("option:selected").data()["ownWeight"]);
            $("#dimension_2_weight").val($("option:selected").data()["topWeight"]);
            $("#dimension_3_weight").val($("option:selected").data()["middleWeight"]);
            $("#dimension_4_weight").val($("option:selected").data()["bottomWeight"]);
            form.submit();
        }
    });

});

