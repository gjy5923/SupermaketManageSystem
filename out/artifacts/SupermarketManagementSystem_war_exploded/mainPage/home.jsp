<%@ page import="DAO.LoginDAO" %>
<%@ page import="Utils.Counter" %>
<%@ page import="bean.User" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>超市管理系统</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta http-equiv="Access-Control-Allow-Origin" content="*">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="format-detection" content="telephone=no">
    <link rel="icon" href="../images/logo.png">
    <link rel="stylesheet" href="../Tool/layuimini-2-onepage/lib/layui-v2.5.5/css/layui.css" media="all">
    <link rel="stylesheet" href="../Tool/layuimini-2-onepage/lib/font-awesome-4.7.0/css/font-awesome.min.css" media="all">
    <link rel="stylesheet" href="../Tool/layuimini-2-onepage/css/layuimini.css?v=2.0.1" media="all">
    <link rel="stylesheet" href="../css/default.css" media="all">
    <link rel="stylesheet" href="../Tool/layuimini-2-onepage/css/public.css" media="all">
    <link rel="stylesheet" href="../Tool/sweetalert/sweetalert.css">
    <script src="../Tool/sweetalert/sweetalert-dev.js"></script>
    <!--[if lt IE 9]>
    <script src="https://cdn.staticfile.org/html5shiv/r29/html5.min.js"></script>
    <script src="https://cdn.staticfile.org/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
    <style id="layuimini-bg-color">
    </style>
</head>
<body  class="layui-layout-body layuimini-all" >

<%
    Counter CountFileHandler = new Counter();
    //读取文件
    long count = CountFileHandler.readFromFile(request.getRealPath("/") + "count.txt");
    count = count + 1; //修改记录 +1
    out.print(count); //显示数据
    //更新文件内容。
    CountFileHandler.write2File(request.getRealPath("/") + "count.txt", count);

    //通过数据库调取登录用户的信息，并显示在用户区
    String username=request.getParameter("username");
    User user=new User();
    user.setEno(username);
    user.setUsername(username);
   user = LoginDAO.findUser(user);
    if (user == null) {
        username=null;
    }else {
        username = user.getUsername();
       application.setAttribute("user",user);
//        把登录的用户职工号存储cookie中
        Cookie c = new Cookie("eno",user.getEno());
        c.setMaxAge(600);
        response.addCookie(c);
    }
%>
<div class="layui-layout layui-layout-admin">

    <div class="layui-header header">
        <div class="layui-logo layuimini-logo layuimini-back-home"></div>

        <div class="layuimini-header-content">
            <a>
                <div class="layuimini-tool"><i title="展开" class="fa fa-outdent" data-side-fold="1"></i></div>
            </a>

            <!--电脑端头部菜单-->
            <ul class="layui-nav layui-layout-left layuimini-header-menu layuimini-menu-header-pc layuimini-pc-show">
            </ul>

            <!--手机端头部菜单-->
            <ul class="layui-nav layui-layout-left layuimini-header-menu layuimini-mobile-show">
                <li class="layui-nav-item">
                    <a href="javascript:;"><i class="fa fa-list-ul"></i> 选择模块</a>
                    <dl class="layui-nav-child layuimini-menu-header-mobile">
                    </dl>
                </li>
            </ul>

            <ul class="layui-nav layui-layout-right">
                <li class="layui-nav-item layuimini-setting " lay-unselect>
                    <a><i id="time"></i></a>
                </li>

                <li class="layui-nav-item" lay-unselect>
                    <a href="javascript:;" data-refresh="刷新"><i class="fa fa-refresh"></i></a>
                </li>
                <li class="layui-nav-item" lay-unselect>
                    <a href="javascript:;" data-clear="清理" class="layuimini-clear"><i class="fa fa-trash-o"></i></a>
                </li>
                <li class="layui-nav-item mobile layui-hide-xs" lay-unselect>
                    <a href="javascript:;" data-check-screen="full"><i class="fa fa-arrows-alt"></i></a>
                </li>
                <li class="layui-nav-item layuimini-setting ">
                    <a href="home.jsp?username=<%=username%>"><%=username%></a><%--得到从login.jsp中传的值--%>
                    <dl class="layui-nav-child">
                        <dd>
                            <a href="javascript:;" layuimini-content-href="changePassword.jsp?username=<%=username%>" style="padding: 8px 0;text-align: center" data-title="修改密码" class="fa fa-gears"> &nbsp; 修改密码</a>
                        </dd>
                        <dd>
                            <hr>
                        </dd>
                        <dd>

                            <a href="javascript:;"style="padding: 8px 0;text-align: center" class="login-out fa fa-power-off"> &nbsp;退出登录<%request.getSession().invalidate();%></a>
                        </dd>
                    </dl>
                </li>
                <li class="layui-nav-item layuimini-select-bgcolor" lay-unselect>
                    <a href="javascript:;" data-bgcolor="配色方案"><i class="fa fa-ellipsis-v"></i></a>
                </li>
            </ul>
        </div>
    </div>

    <!--无限极左侧菜单-->
    <div class="layui-side layui-bg-black layuimini-menu-left">
    </div>

    <!--初始化加载层-->
    <div class="layuimini-loader">
        <div style="text-align: center"><img src="../images/loading2.gif"></div>
    </div>

    <!--手机端遮罩层-->
    <div class="layuimini-make"></div>

    <!-- 移动导航 -->
    <div class="layuimini-site-mobile"><i class="layui-icon"></i></div>

    <div class="layui-body">

        <div class="layui-card layuimini-page-header layui-hide">
            <div class="layui-breadcrumb layuimini-page-title">
                <a lay-href="" href="/">主页</a><span lay-separator="">/</span>
                <a><cite>常规管理</cite></a><span lay-separator="">/</span>
                <a><cite>安全管理</cite></a><span lay-separator="">/</span>
            </div>
        </div>

        <div class="layuimini-content-page">
        </div>

    </div>

</div>
<script src="../Tool/layuimini-2-onepage/lib/layui-v2.5.5/layui.js" charset="utf-8"></script>
<script src="../Tool/layuimini-2-onepage/js/lay-config.js?v=2.0.0" charset="utf-8"></script>
<script src="../Tool/layuimini-2-onepage/lib/jquery-3.4.1/jquery-3.4.1.min.js"></script>



<script>
    layui.use(['jquery', 'layer', 'miniAdmin'], function () {
        var $ = layui.jquery,
            layer = layui.layer,
            miniAdmin = layui.miniAdmin


        var options = {
            iniUrl: "api/init.json",    // 初始化接口
            clearUrl: "api/clear.json", // 缓存清理接口
            renderPageVersion: true,    // 初始化页面是否加版本号
            bgColorDefault: false,      // 主题默认配置
            multiModule: true,          // 是否开启多模块
            menuChildOpen: false,       // 是否默认展开菜单
            loadingTime: 0,             // 初始化加载时间
            pageAnim: true,             // 切换菜单动画
        };
        miniAdmin.render(options);

        $('.login-out').on("click", function () {
            layer.msg('退出登录成功', function () {
                window.location = 'login.jsp';
            });
        });
    });
</script>
<%--&lt;%&ndash;计时器&ndash;%&gt;--%>
<%--<script type="text/javascript">--%>
<%--    var seconds = 0;--%>
<%--    var m=0;--%>
<%--    var h=0;--%>
<%--    function _fresh()--%>
<%--    {--%>
<%--        seconds ++;--%>
<%--        var result = '';--%>

<%--        if (seconds > 59) {--%>
<%--            seconds=0;--%>
<%--            m++;--%>
<%--        }--%>
<%--        if (m>59) {--%>
<%--            m=0;--%>
<%--            h++;--%>
<%--        }--%>
<%--        var temp1 = h;--%>
<%--        var temp2 = " : "+m;--%>
<%--        var temp3 = " : "+seconds;--%>
<%--        if (seconds < 10) {--%>
<%--            temp3 = ": 0"+seconds;--%>
<%--        }--%>
<%--        if (m < 10) {--%>
<%--            temp2 = ": 0"+m;--%>
<%--        }--%>
<%--        if (h < 10) {--%>
<%--            temp1 = "0"+h;--%>
<%--        }--%>
<%--        result=temp1+temp2+temp3;--%>
<%--        document.getElementById('time').innerHTML = result;--%>
<%--    }--%>
<%--    _fresh()--%>
<%--    setInterval(_fresh,1000);--%>

<%--</script>--%>
</body>
</html>
