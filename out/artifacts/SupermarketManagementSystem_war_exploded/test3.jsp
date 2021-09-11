<%--
  Created by IntelliJ IDEA.
  User: Laity.GU
  Date: 2021/6/30
  Time: 10:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<script>
    var websocket = null;
    //判断当前浏览器是否支持WebSocket
    if ('WebSocket' in window) {
        //建立连接，这里的/websocket ，是Servlet中注解中的那个值
        websocket = new WebSocket("ws://localhost:8080/SMS_exploded/websocket");
    }
    else {
        alert('当前浏览器 Not support websocket');
    }
    //连接发生错误的回调方法
    websocket.onerror = function () {
        console.log("WebSocket连接发生错误");
    };
    //连接成功建立的回调方法
    websocket.onopen = function () {
        console.log("WebSocket连接成功");
    }
    //接收到消息的回调方法
    websocket.onmessage = function (event) {
        console.log(event.data);
        if(event.data=="1"){
           alert("数据更新啦");
        }
    }
    //连接关闭的回调方法
    websocket.onclose = function () {
        console.log("WebSocket连接关闭");
    }
    //监听窗口关闭事件，当窗口关闭时，主动去关闭WebSocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
    window.onbeforeunload = function () {
        closeWebSocket();
    }
    //关闭WebSocket连接
    function closeWebSocket() {
        websocket.close();
    }
</script>
</body>
</html>
