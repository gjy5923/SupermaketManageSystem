<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>超市管理系统-注册</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="icon" href="../images/logo.png">
    <!-- style CSS -->
    <link rel="stylesheet" href="../css/style.css" type="text/css" media="all">
    <link rel="stylesheet" href="../Tool/sweetalert/sweetalert.css">
    <script src="../Tool/sweetalert/sweetalert-dev.js"></script>
    <!-- fontawesome -->
    <link rel="stylesheet" href="../Tool/layuimini-2-onepage/lib/font-awesome-4.7.0/css/font-awesome.css" type="text/css" media="all">


</head>
<body>
<section action="" class="main">
    <div class="bottom-grid">
        <div class="logo">
            <h1> <a href="login.jsp"> 超市管理系统<br>Supermarket Management System</a></h1>
        </div>
    </div>
    <div class="content-w3ls">
        <div class="text-center icon">
            <span class="fa fa-meetup"></span>
        </div>
        <div class="content-bottom ">
            <form action="" method="post" id="frmRegister">
                <div class="field-group">
                    <span class="fa fa-user" aria-hidden="true"></span>
                    <div class="wthree-field">
                        <input name="username" id="uname" type="text" placeholder="Username" class="field-group2" style="width: 103%" required>
                    </div>
                </div>

                <div class="field-group">
                    <span class="fa fa-lock" aria-hidden="true"></span>
                    <div class="wthree-field">
                        <input name="password" id="pw" type="Password"  placeholder="Password">
                    </div>
                </div>
                <div class="field-group">
                    <span class="fa fa-lock" aria-hidden="true"></span>
                    <div class="wthree-field">
                        <input name="password2" id="pw2" type="Password"  placeholder="Confirm Password">
                    </div>
                </div>
                <div class="field-group" >
                    <div class="" style="padding: 10px 2px 10px 2px;text-align: center">
                        <label style="font-size: 16px;font-family: '微软雅黑 Light'"> <input type="radio" name="user" value="admin" title="admin" class="radio_type">admin</label>
                        <label style="font-size: 16px;font-family: '微软雅黑 Light'">  <input type="radio" name="user" value="manager" title="manager" class="radio_type">manager</label>
                        <label style="font-size: 16px;font-family: '微软雅黑 Light'"> <input type="radio" name="user" value="staff" title="staff" checked="1" class="radio_type">staff</label>
                    </div>
                </div>
                <div class="wthree-field" style="text-align: center">
                    <button type="button" class="btn" id="btnOk" style="width: 46%">Register</button>
                    <button type="reset" class="btn" style="width: 46%;margin-left: 10px" onclick="reset()">Reset</button>
                </div>
                <ul class="list-login-bottom" style="padding-top: 10px;text-align: center">
                    <li class="" >
                        <span style="font-family:'宋体';font-size: 10px;color: #dfdfdf  "> 已有账号密码， <a href="login.jsp" class="" style="font-family:'宋体';font-size: 13px"> 前往登录</a></span>
                    </li>
                    <li class="clearfix"></li>
                </ul>
            </form>
        </div>
    </div>
    <div class="copyright">
        <p>© 2021 Supermarket Management System. All rights reserved | Design by 2019401210 顾济玉 </p>
    </div>
</section>
<script src="../Tool/layuimini-2-onepage/lib/layui-v2.5.5/layui.js" charset="utf-8"></script>
<script src="../Tool/layuimini-2-onepage/lib/jquery-3.4.1/jquery-3.4.1.min.js"></script>
<script>
    //JQuery实现无刷新提交
    layui.use('layer', function () {//使用layer弹层组件
        var layer = layui.layer//定义变量
        $ = layui.jquery;//定义变量
        $("#btnOk").click(function () {//点击登录按钮执行函数,使用JQuery的post方法
            if ($("#uname").val() == "") {

                swal("用户名不能为空!");

            } else if ($("#pw").val() == "") {
                swal("密码不能为空");

            }else if($("#pw").val()!=$("#pw2").val()){
                swal("两次密码不一致");

            } else {
                $.post(
                    '../RegisterServlet', //提交给谁处理
                    $("#frmRegister").serialize(),//传递表单中所有组件的值
                    function(result){//回调函数，根据返回结果进行相应的操作,返回error则用户已存在，否则返回分配的职工号
                        if(result=="error"){
                            swal("用户名已存在");

                        }else {
                            swal({
                                title: "注册成功",
                                text: "您的职工号为：<h2>"+result+"</h2>",
                                type: "success",
                                confirmButtonText:"前往登录",
                                html:true
                            },function () {
                                    window.location.href="login.jsp";
                            });

                        }
                    });
            }
        })

    });
    $(function(){ //加载完DOM树后，立即运行
        $("#uname").blur(function(){ //获取id为re_password的节点，监听当光标离开输入框时，运行function方法
            var username = $("#uname").val(); //获取id为password的输入框中的内容
            if(username==""){ //判断两个变量是否相等
               swal("用户名不能为空!");
            }
        }),
        $("#pw2").blur(function(){ //获取id为re_password的节点，监听当光标离开输入框时，运行function方法
            var password = $("#pw").val(); //获取id为password的输入框中的内容
            var repassword = $("#pw2").val();
            if(password!=repassword){ //判断两个变量是否相等
                swal("两次密码不一致");
                $("#pw").select();
                $("#pw").focus();

            }
        });
    });

</script>
</body>
</html>
