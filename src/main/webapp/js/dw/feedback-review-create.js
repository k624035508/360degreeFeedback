//添加被考核者
function addExaminee(userId, userName) {
    var examineeValue = $("#examinee").val();
    var selectName = $(".selectName").val();
    if(examineeValue.indexOf(userId)==-1) {
        $("#userSelectList").append("<tr><td>" + userName + "</td><td class='user-delete' data-select-id='" + userId + "'>&times;</td></tr>");
        if (examineeValue == "" || examineeValue == null) {
            $("#examinee").val(userId);
            $(".selectName").val(userName);
        } else {
            $("#examinee").val(examineeValue + "," + userId);
            $(".selectName").val(selectName + "," + userName);
        }
    }
}

//添加考核者
function addInvestigate(userId, userName, dimensionValue){
    var modalId = "dimensionModal_" + dimensionValue;
    var $dimensionModal = $("#" + modalId);
    var selectName= $(".selectDimensionTr").find("#investigate_name_"+ dimensionValue).val();
    var investigate = $(".selectDimensionTr").find("#dimension_"+ dimensionValue +"_investigate").val();
    if(investigate.indexOf(userId)==-1) {
        $dimensionModal.find("#dimensionSelectList_" + dimensionValue).append("<tr><td>" + userName + "</td><td class='user-delete'>&times;</td></tr>");
        if (investigate == null || investigate == "") {
            $(".selectDimensionTr").find("#dimension_"+ dimensionValue +"_investigate").val(userId);
            $(".selectDimensionTr").find("#investigate_name_" + dimensionValue).val(userName);
        } else {
            $(".selectDimensionTr").find("#dimension_"+ dimensionValue +"_investigate").val(investigate + "," + userId);
            $(".selectDimensionTr").find("#investigate_name_" + dimensionValue).val(selectName + "," + userName);
        }
    }
}

$(function () {
    //考核人modal
    $("a.selectDimensionClass").click(function () {
        $(this).next().find(".modal-middle iframe").attr("src", "");
        $(this).next().modal();
    });
    //选择模板时清空数据
    $("select").bind("change",function(){
        alert(123);
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
    $(".user-left li").click(function () {
        var dimensionString = $(this).parents(".selectDimensionModalClass").attr("id");
        var departmentId = $(this).data()["departmentId"];
        if(dimensionString!=null){
            var dimensionId = dimensionString.substr(dimensionString.length-1, 1);
            $(".modal-middle iframe").attr("src", "/department.action?departmentId=" + departmentId + "&dimensionId=" + dimensionId);
        } else{
            $(".modal-middle iframe").attr("src", "/department.action?departmentId=" + departmentId);
        }
    });

    $("#userSelectList td.user-delete").click(function () {
        $(this).parent().remove();
        var userSelectId = $(this).data()["selectId"];
        var userSelectName = $(this).prev().text();

        var examineeValue = $("#examinee").val();
        var newExamineeValue = "";
        if(examineeValue.indexOf(userSelectId) != -1){
            newExamineeValue = examineeValue.split(userSelectId).join("");
        }
        newExamineeValue = newExamineeValue.split(",,").join(",");
        if(newExamineeValue.indexOf(",") == 0){
            newExamineeValue = newExamineeValue.substr(1);
        }
        var reg=/,$/gi;
        newExamineeValue=newExamineeValue.replace(reg,"");
        $("#examinee").val(newExamineeValue);

        var selectName = $(".selectName").val();
        var newSelectName = "";
        if(selectName.indexOf(userSelectName) != -1){
            newSelectName = selectName.split(userSelectName).join("");
        }
        newSelectName = newSelectName.split(",,").join(",");
        if(newSelectName.indexOf(",") == 0){
            newSelectName = newSelectName.substr(1);
        }
        var reg=/,$/gi;
        newSelectName=newSelectName.replace(reg,"");
        $(".selectName").val(newSelectName);
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
            examineeOwnValue += "},";
            examineeValue += examineeOwnValue;
                $(".examinee-table").append("<tr class='examinee-tr'><td>" + arrayOwn[i] + "</td><td>" + arrayTop[i] + "</td><td>" + arrayMiddle[i] + "</td><td>" + arrayBottom[i] + "</td></tr>");
        }
        examineeValue += "}";
        console.log(examineeValue);
        $("#examineeValue").val(examineeValue);
    });

    $(".button_submit").click(function(){
        if($("#reviewName").val() == "" || $("#reviewName").val() == null){
            alert("请填写考核测评名称");
            return false;
        }
        $("#inputReviewForm").submit();
    })

});

