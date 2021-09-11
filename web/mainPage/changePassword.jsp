<style>
    .layui-form-item .layui-input-company {
        width: auto;
        padding-right: 10px;
        line-height: 38px;
    }
</style>
<div style="padding: 10px;">

            <form id="editPassword"  class="layui-form layuimini-form layuimini-main" >
                <div class="layui-form-item">
                    <label class="layui-form-label required">原始密码</label>
                    <div class="layui-input-block">
                        <input type="password" name="old_password" id="oldPw" placeholder="请输入原始密码"  class="layui-input">
                        <tip>填写自己账号的原始密码。</tip>
                    </div>
                </div>

                <div class="layui-form-item">
                    <label class="layui-form-label required">新的密码</label>
                    <div class="layui-input-block">
                        <input type="password" name="new_password" id="pw" placeholder="请输入新的密码"  class="layui-input">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label required">新的密码</label>
                    <div class="layui-input-block">
                        <input type="password" name="again_password" id="pw2" placeholder="请输入新的密码"  class="layui-input">
                    </div>
                </div>

                <div class="layui-form-item">
                    <div class="layui-input-block">
                        <button type="button" class="layui-btn" id="btnOk">确认保存</button>
                    </div>
                </div>
            </form>
</div>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script>
    //JQuery实现无刷新提交
    layui.use('layer', function () {//使用layer弹层组件
        $ = layui.jquery;//定义变量

        $("#btnOk").click(function () {//点击登录按钮执行函数,使用JQuery的post方法
            if ($("#oldPw").val() == "") {

                swal("原密码不能为空!");

            } else if ($("#pw").val() == ""||$("#pw2").val()=="") {
                swal("新的密码不能为空");

            }else if($("#pw").val()!=$("#pw2").val()){
                swal("两次密码不一致");
            } else {
                $.post(
                    '../ChangePasswordServlet', //提交给谁处理
                    $("#editPassword").serialize(),//传递表单中所有组件的值
                    function (result) {//回调函数，根据返回结果进行相应的操作
                        if (result == "ok") {
                            swal({
                                title: " 密码修改成功！",
                                type: "success",
                                showConfirmButton:false,
                                timer: 1000,
                            },function () {
                            window.location.href='login.jsp';
                            })
                        } else {
                            swal('原始密码错误！');
                        }
                    });
                }
            });

    } );
</script>

