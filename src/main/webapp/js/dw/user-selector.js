$(function () {
    $(".user-selector tr.userTr").click(function () {
        var userId = $(this).data()["userId"];
        var userName = $(this).find(".userTd").text();
        var dimensionValue = $("#selectDimension").val();
        if (dimensionValue == null || dimensionValue==""){
            parent.addExaminee(userId, userName);
        } else {
            parent.addInvestigate(userId, userName, dimensionValue);
        }
    })
});
