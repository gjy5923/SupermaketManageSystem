<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>JSP - Hello World</title>
</head>
<body>
<div style="text-align: center;">
    <h1>实时抓拍</h1>
    <img id="img" src="" alt="">
</div>
<script type="text/javascript">
    // 初始化 ws 对象
    let ws = {};
    // init() 方法为了初始化 ws 的一些具体回调函数，在 body 的 onload 事件中触发
    function init() {
        // 连接服务器
        if( 'WebSocket' in window) {
            ws = new WebSocket("ws://localhost:8080/SMS_exploded/socket")
        } else {
            console.log("not support web socket")
        }
        // 配置客户端处理消息的回调方法
        ws.onopen = function (event) {
            console.log('opening...');
        }
        // 配置客户端处理消息的回调方法
        ws.onmessage = function (event) {
            // 创建 FileReader 对象，该对象时 html5 中的特有对象，详细用法可以
            let reader = new FileReader();
            reader.onload = function (event) {
                // 判断文件是否读取完成
                if(event.target.readyState === FileReader.DONE) {
                    let img = document.getElementById('img');
                    img.src = this.result;
                }
            }
            // 调用 FileReader 的 readAsDataURL 的方法自动就会触发 onload 事件
            reader.readAsDataURL(event.data);
        }
        // 和后台进行沟通，后台接收到消息后启动监听线程，页面刷新的话会导致多次启动监听程序，socket 端口冲突
        ws.addEventListener('open', function () {
            ws.send('visit web socket...');
        });

    }

    init();
</script>
</body>
</html>