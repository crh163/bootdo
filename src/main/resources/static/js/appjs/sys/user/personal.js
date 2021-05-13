$(function () {
});
/**
 * 基本信息提交
 */
$("#base_save").click(function () {
    if($("#userName").val() == ''){
        top.layer.msg("姓名不能为空");
        return null;
    }
    $.ajax({
        cache : true,
        type : "POST",
        url : ctx + "sys/user/updatePeronal",
        data : $('#basicInfoForm').serialize(),
        async : false,
        error : function(request) {
            top.layer.alert("Connection error");
        },
        success : function(data) {
            if (data.code == 0) {
                top.layer.alert("更新成功");
            } else {
                top.layer.alert(data.msg);
            }
        }
    });

});
$("#pwd_save").click(function () {
    if($("#modifyPwd").valid()){
        $.ajax({
            cache : true,
            type : "POST",
            url : ctx + "sys/user/resetPwd",
            data : $('#modifyPwd').serialize(),
            async : false,
            error : function(request) {
                top.laryer.alert("Connection error");
            },
            success : function(data) {
                if (data.code == 0) {
                    top.layer.alert("更新密码成功");
                    $("#photo_info").click();
                } else {
                    top.layer.alert(data.msg)
                }
            }
        });
    }
});