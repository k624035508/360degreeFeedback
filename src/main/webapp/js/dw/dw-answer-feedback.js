//查看是否有空没填
function validateCheck(radioTrBody){
    var validateStatus = true;
    $.each(radioTrBody, function(){
        var radioCheck = $(this).find("input[type='radio']:checked")[0];
        if(radioCheck){
            $(this).find(".errorItem").remove();
        } else {
            if(!$(this).find(".errorItem")[0]) {
                var errorHtml = "<td class=\"errorItem\" style=\"width: 100px;\"><label for=\"\" class=\"error\">请填写分值！</label></td>";
                $(this).append(errorHtml);
            }
            validateStatus = false;
        }
    });
    return validateStatus;
}

$(function () {
    //直接提交
    $(".answerPost").click(function () {
        var validateStatus = validateCheck($(".feedbackOptionTr"));
        if(validateStatus){
            if(confirm("直接提交后将无法修改评分，确认提交？")) {
                $(".answerStatus").val(1);
                $("#feedbackAnswerForm").submit();
            }
        };
    });

    //草稿
    $(".answerPostDraft").click(function () {
        var validateStatus = validateCheck($(".feedbackOptionTr"));
        if(validateStatus){
            $(".answerStatus").val(0);
            $("#feedbackAnswerForm").submit();
        };
    })
});