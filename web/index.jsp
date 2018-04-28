<%--
  Created by IntelliJ IDEA.
  User: mevur
  Date: 2018/3/31
  Time: 20:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>Session</title>
    <link rel="stylesheet" href="assests/css/bootstrap.min.css"/>
  </head>
  <body>
    <div class="container-fluid">
      <div class="container main-container">
        <form>
          <div class="form-control">
            <h4>Session属性</h4>
          </div>
          <div class="attr-container">
          </div>
          <div class="form-control">
            <button type="button" class="btn btn-primary btn-block" onclick="addAttr()">添加属性</button>
          </div>
        </form>
        <button type="button" class="btn btn-primary" onclick="request()">请求 session</button>
        <button type="button" class="btn btn-success">刷新 session</button>
        <div class="result"></div>
      </div>
    </div>
  </body>
  <style type="text/css">
    .attr-row {
      margin-top: 10px;
    }
    .main-container {
      margin-top: 5%;
      width: 40%;
      margin-left: 30%;
    }
  </style>
  <script type="text/javascript" src="./assests/js/jquery-3.3.1.min.js"></script>
  <script type="text/javascript" src="./assests/js/bootstrap.min.js"></script>
  <script type="text/javascript">
    function request() {
        var attrs = $('.attr-container').children();
        var data = {};
        for (i = 0; i < attrs.length; i++){
            var row = attrs[i];
            var key = $(row.children[0].children[0]).val();
            var value = $(row.children[1].children[0]).val();
            if (key !== "" && value !== "") {
                data[key] = value;
            }
        }
        console.log(data);
        $.get(
            "/session/test", data, function(data, state){
              console.log(data);
            }
        )
    }
    //添加属性
    function addAttr() {
        var row = $("<div class=\"form-row attr-row\">\n" +
            "              <div class=\"col-5\">\n" +
            "                <input type=\"text\" class=\"form-control\" placeholder=\"key\">\n" +
            "              </div>\n" +
            "              <div class=\"col-5\">\n" +
            "                <input type=\"text\" class=\"form-control\" placeholder=\"value\">\n" +
            "              </div>\n" +
            "              <div class=\"col-2\">\n" +
            "                <button type=\"button\" class=\"btn btn-outline-danger btn-block remove-btn\"\n" +
            "                        onclick=\"remove(this)\">删除</button>\n" +
            "              </div>\n" +
            "            </div>");
        $('.attr-container').append(row);
    }
    //删除属性
    function remove(taret) {
        var row = taret.parentNode.parentNode;
        console.log(row);
        $(row).remove();
    }
  </script>
</html>
