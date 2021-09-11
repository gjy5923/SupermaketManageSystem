<%@ page import="Utils.CookieEncryptTool" %>


<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>超市管理系统-登录</title>
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
<%
    String username = "";
    String password = "";
    Cookie[] cookies = request.getCookies();
    if (cookies != null) {
        for (Cookie cookie : cookies) {
            if ("COOKIE_USERNAME".equals(cookie.getName())) {
                username = CookieEncryptTool.decodeBase64(cookie.getValue());
            }
            if ("COOKIE_PASSWORD".equals(cookie.getName())) {
                password = CookieEncryptTool.decodeBase64(cookie.getValue());
            }
        }

    }
%>
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

        <div class="content-bottom" >
            <form action="" method="post" id="frmLogin">
                <div class="field-group">
                    <span class="fa fa-user" aria-hidden="true"></span>
                    <div class="wthree-field">
                        <input name="username" id="username" type="text" value="<%=username%>" placeholder="Username" class="field-group2" style="width: 103%" required>
                    </div>
                </div>
                <div class="field-group">
                    <span class="fa fa-lock" aria-hidden="true"></span>
                    <div class="wthree-field">
                        <input name="password" id="password" type="Password" value="<%=password%>" placeholder="Password">
                    </div>
                </div>
                <div class="wthree-field">
                    <button type="button" class="btn" id="btnOk">Sign in</button>
                </div>
                <ul class="list-login">
                    <li class="switch-agileits">
                        <label class="switch">
                            <input type="checkbox" checked="checked" value="true" name="rememberMe" id="rememberMe">
                            <span class="slider round"></span>
                            记住密码&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                        </label>
                    </li>
                    <li>
                        <a href="#" class="text-right" id="forget">忘记密码？</a>
                    </li>
                    <li class="clearfix"></li>
                </ul>
                <ul class="list-login-bottom" style="padding-top: 10px;text-align: center">
                    <li class="" >
                       <span style="font-family:'宋体';font-size: 10px;color: #dfdfdf  "> 没有账号密码， <a href="register.jsp" id="register" style="font-family:'宋体';font-size: 13px"> 点击注册</a></span>
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
        $ = layui.jquery;//定义变量
        $("#btnOk").click(function () {//点击登录按钮执行函数,使用JQuery的post方法
            //判断账号密码是否为空
            if ($("#username").val() == "") {

                // swal("用户名不能为空!");
        swal({
          position: 'top-end',
           text:"22 "
        })
            } else if($("#password").val() == ""){
                swal("密码不能为空");

            }else {
                $.post(
                    '../LoginInfoServlet', //提交给谁处理
                    $("#frmLogin").serialize(),//传递表单中所有组件的值
                    function(result){//回调函数，根据返回结果进行相应的操作
                        if(result=="ok"){
                            swal({
                                title: " 登录成功！",
                                type: "success",
                                timer: 1500,
                                showConfirmButton: false
                            },function () {
                                window.location.href='home.jsp?username='+$('#username').val();
                            })
                        }else if(result=="error"){
                            swal('用户名或密码错误');
                        }
                    });
            }
        });
        $("#forget").click(function () {//点击登录按钮执行函数,使用JQuery的post方法
            swal("请联系管理员进行操作！");
        });
    } );
</script>
</body>
</html>