<%@ page import="DAO.UserInfoDAO" %>
<%@ page import="Utils.Counter" %>
<%@ page import="Utils.DataFormat" %>
<%@ page import="bean.User" %>


<style>
    .layui-top-box {
        padding: 40px 20px 20px 20px;
        color: #fff
    }

    .panel {
        margin-bottom: 17px;
        background-color: #fff;
        border: 1px solid transparent;
        border-radius: 3px;
        -webkit-box-shadow: 0 1px 1px rgba(0, 0, 0, .05);
        box-shadow: 0 1px 1px rgba(0, 0, 0, .05)
    }

    .panel-body {
        padding: 15px
    }

    .panel-title {
        margin-top: 0;
        margin-bottom: 0;
        font-size: 14px;
        color: inherit
    }

    .label {
        display: inline;
        padding: .2em .6em .3em;
        font-size: 75%;
        font-weight: 700;
        line-height: 1;
        color: #fff;
        text-align: center;
        white-space: nowrap;
        vertical-align: baseline;
        border-radius: .25em;
        margin-top: .3em;
    }

    .layui-red {
        color: red
    }

    .main_btn > p {
        height: 40px;
    }
</style>
<%

    String date = (String) application.getAttribute("date");//获取登录时间
    //获取存在cookie中的登录账号
    String eno=null;
    Cookie[] cookies=request.getCookies();
    for(int i=0;i<cookies.length;i++) {
        if(cookies[i].getName().equals("eno")) {
            eno=cookies[i].getValue();
            break;
        }
    }
    User user=UserInfoDAO.findEno2(eno);

    //浏览次数访问
    Counter CountFileHandler = new Counter();
    //读取文件
    long count = CountFileHandler.readFromFile(request.getRealPath("/") + "count.txt");
    Object temp;
    temp=count;
    //数据格式化
    String num=DataFormat.dataFormat(temp);
%>
<style type="text/css">
    .bg{
        text-align: center;

    }
    .bg td{
        font-size: 23px;
        width: 500px;
        height: 50px;
    }

</style>
<div class="layuimini-container layuimini-page-anim">
    <div class="layuimini-main layui-top-box">
        <div class="layui-row layui-col-space10" style="align-content: center">

            <div class="layui-col-md4">
                <div class="col-xs-6 col-md-3">
                    <div class="panel layui-bg-cyan">
                        <div class="panel-body">
                            <div class="panel-title">
                                <span class="label pull-right layui-bg-blue">实时</span>
                                <h5>登录时间</h5>
                            </div>
                            <div class="panel-content">
                                <h1 class="no-margins"><%=date%></h1>
                                <div class="stat-percent font-bold text-gray"><i class="fa fa-commenting"></i> <%=date%></div>
                                <small>当前分类总记录数</small>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <div class="layui-col-md4">
                <div class="col-xs-6 col-md-3">
                    <div class="panel layui-bg-blue">
                        <div class="panel-body">
                            <div class="panel-title">
                                <span class="label pull-right layui-bg-cyan">实时</span>
                                <h5>登陆人数</h5>
                            </div>
                            <div class="panel-content">
                                <h1 class="no-margins">  <%=application.getAttribute("personNum")%></h1>
                                <div class="stat-percent font-bold text-gray"><i class="fa fa-commenting"></i><%=application.getAttribute("personNum")%></div>
                                <small>当前分类总记录数</small>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <div class="layui-col-md4">
                <div class="col-xs-6 col-md-3">
                    <div class="panel layui-bg-green">
                        <div class="panel-body">
                            <div class="panel-title">
                                <span class="label pull-right layui-bg-orange">实时</span>
                                <h5>浏览统计</h5>
                            </div>
                            <div class="panel-content">
                                <h1 class="no-margins"><%=num%></h1>
                                <div class="stat-percent font-bold text-gray"><i class="fa fa-commenting"></i><span> <%=num%></span></div>
                                <small>当前分类总记录数</small>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="layui-col-md12" style="background:url(../images/firstBg.jpg) no-repeat;background-size: 100%;height: 440px;">

        <div class="col-xs-8 col-md-4" style="margin-top: 30px;">
            <div><h1 style="text-align: center">用户信息</h1></div>
            <div>
                <table class="bg" style="width: 100%">
                <tr><td>职工号</td><td><%=user.getEno()%></td></tr>
                <br>
                <tr><td>用户名</td><td><%=user.getUsername()%></td></tr>
                <br>
                <tr><td>权限</td><td><%=user.getPosition()%></td></tr>
                </table>
            </div>

        </div>
    </div>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

