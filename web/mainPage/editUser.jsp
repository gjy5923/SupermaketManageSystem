<%@ page import="bean.User" %>
<%@ page import="DAO.UserInfoDAO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>用户修改</title>
    <link rel="stylesheet" href="../Tool/layui-v2.6.6/layui/css/layui.css" media="all">
</head>
<body>
<%

    String eno=request.getParameter("eno");//得到从userManager.jsp中传来的值
    User user = UserInfoDAO.findEno2(eno);
    if(user!=null){
%>
<div style="padding: 10px;">

    <form id="editUser"  class="layui-form" >
        <div class="layui-row">
            <div class="layui-col-sm8"style="margin-top: 40px;margin-left: 5%">
                <div class="layui-form-item"style="text-align: center">
                    <label class="layui-form-label">职工号号</label>
                    <div class="layui-input-block">
                        <input type="text" name="eno" id="eno"  value="<%=user.getEno()%>" readonly="true" class="layui-input" lay-verify="required">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">用户名</label>
                    <div class="layui-input-block">
                        <input type="text" name="username" id="username" value="<%=user.getUsername()%>" class="layui-input">
                    </div>
                </div>

                <div class="layui-form-item">
                    <label class="layui-form-label">密码</label>
                    <div class="layui-input-block">
                        <input type="text" name="password"  value="<%=user.getPassword()%>" class="layui-input">
                    </div>
                </div>

                <div class="layui-form-item">
                    <label class="layui-form-label">权限</label>
                    <div class="layui-input-block">
                        <select name="position">
                            <%if(user.getPosition().equals("admin")){%>
                            <option selected>admin</option><option>manger</option><option>staff</option>
                            <%}else if(user.getPosition().equals("manager")){%>
                            <option >admin</option><option selected>manger</option><option>staff</option>
                            <%}else if(user.getPosition().equals("staff")){%>
                            <option >admin</option><option >manger</option><option selected>staff</option>
                            <%}%>
                        </select>
                    </div>
                </div>
                <div class="layui-form-item">
                    <div class="layui-input-block"style="text-align: center;margin-left: -50px">
                        <button type="button" class="layui-btn" lay-submit="" lay-filter="ok">修改</button>
                    </div>
                </div>
            </div>
        </div>
    </form>
</div>
<script src="../Tool/layui-v2.6.6/layui/layui.js" charset="utf-8"></script>
<script>
    layui.use(['form'], function () {
        var form = layui.form//定义layui的表单变量
            , $ = layui.jquery//定义layui的jquery变量
            , layer = layui.layer//定义layui的弹窗变量
        form.render();//渲染表单
        form.on('submit(ok)', function () {//单击添加按钮执行
            var formdata = $("#editUser").serialize();//传递表单中所有组件的值
            $.ajax({
                async:false,//同步方式提交表单
                url: '<%=request.getContextPath()%>/UserUpdateServlet',//提交给谁处理
                data: formdata,//传递表单中所有组件的值
                type: "post",//提交方式
                dataType: "json",//返回的数据格式，常见有json、text、html等
                success: function (data) {//提交成功，将处理后的返回值存储在data中
                    if (data.code == 1) {//判断返回值
                        layer.msg('用户信息修改成功', {icon: 6, time: 1000}, function () {//使用Jquery弹层组件，自动关闭
                            //当你在iframe页面关闭自身时
                            var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
                            parent.layer.close(index); //再执行关闭
                        });
                    } else {
                        layer.msg('信息修改失败！', {icon: 2, time: 1000});
                    }
                }
            });
        });
    })
</script>
<%
    }
%>
</body>
</html>
