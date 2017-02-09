<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%-- import CSS --%>
<spring:url value="/resources/lib/normalize/normalize.css"
	var="normalizeCss" />
<spring:url value="/resources/lib/h5bp/h5bp.css" var="h5bpCss" />
<spring:url value="/resources/css/common.css" var="commonCss" />
<spring:url
	value="/resources/lib/jquery.easyui/themes/default/easyui.css"
	var="easyuiCss" />
<spring:url value="/resources/lib/jquery.easyui/themes/icon.css"
	var="iconCss" />
<spring:url value="/resources/lib/dist/css/drop-theme-basic.css"
	var="dropTheme" />
<%-- import JS --%>
<spring:url value="/resources/lib/html5shiv/html5shiv.js"
	var="html5shivJs" />
<spring:url value="/resources/lib/jquery/jquery-2.0.3.min.js"
	var="jqueryJs" />
<spring:url value="/resources/lib/jquery/plugins.js" var="pluginJs" />
<spring:url value="/resources/lib/jquery.blockui/jquery.blockUI.js"
	var="blockUIJs" />
<spring:url value="/resources/lib/jquery.json/jquery.json-2.4.min.js"
	var="jsonJs" />
<spring:url value="/resources/lib/jquery.cookie/jquery.cookie.js"
	var="cookiejs" />
<spring:url value="/resources/lib/jquery.easyui/jquery.easyui.min.js"
	var="easyuiJs" />
<spring:url
	value="/resources/lib/jquery.easyui/locale/easyui-lang-zh_CN.js"
	var="zhJs" />
<spring:url value="/resources/js/common.js" var="commonJs" />
<spring:url value="/resources/lib/My97DatePicker/WdatePicker.js"
	var="WdatePicker" />
<spring:url value="/resources/lib/dist/tether.min.js" var="tetherjs" />
<spring:url value="/resources/lib/dist/js/drop.min.js" var="dropjs" />
<spring:url value="/resources/lib/jquery/ajaxfileupload.js"
	var="ajaxfileuploadJs" />
<!DOCTYPE html>
<!--[if lt IE 7]>      <html class="no-js lt-ie9 lt-ie8 lt-ie7"> <![endif]-->
<!--[if IE 7]>         <html class="no-js lt-ie9 lt-ie8"> <![endif]-->
<!--[if IE 8]>         <html class="no-js lt-ie9"> <![endif]-->
<!--[if gt IE 8]><!-->
<html class="no-js">
<!--<![endif]-->
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=9,chrome=1">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet" href="${normalizeCss }">
<link rel="stylesheet" href="${h5bpCss }">
<link rel="stylesheet" href="${commonCss }">
<link rel="stylesheet" href="${easyuiCss }">
<link rel="stylesheet" href="${iconCss }">
<link rel="stylesheet" href="${userListCss }">

<link rel="stylesheet" href="${index }">
<link rel="stylesheet" href="${stepdcstyle }">
<link rel="stylesheet" href="${dropTheme }">
<!--[if lt IE 9]>
		<script>window.html5 || document.write('<script src="html5shivJs"><\/script>')</script>
	<![endif]-->
<script src="${jqueryJs }"></script>
<script src="${pluginJs }"></script>
<script src="${blockUIJs }"></script>
<script src="${jsonJs }"></script>
<script src="${easyuiJs }"></script>
<script src="${zhJs }"></script>
<script src="${commonJs }"></script>
<script src="${WdatePicker }"></script>
<script src="${stepjquery }"></script>
<script src="${cookiejs }"></script>
<script src="${indexjs }"></script>
<script src="${tetherjs }"></script>
<script src="${dropjs }"></script>
<script type="text/javascript" src="${ajaxfileuploadJs}"></script>
<script type="text/javascript">
	$().ready(function(){
		loadData(function(msg) {
			$("#pay_id").val(msg.billNo);
		}, '/Pay/pay/get/billno',null);
			
		$("#submit").on('click',function(){
			var id= $('#pay_id').val().trim();
			var title=$("#pay_title").val().trim();
			var channel=$("#pay_channel").val().trim();
			var price=$("#pay_price").val().trim();
			var returl=$("#pay_returl").val().trim();
			loadData(function(msg) {
			$("body").val("");
			$("body").html(msg.result);
			}, '/Pay/pay/income',$.toJSON({
				billNo:id,
				payChannel:channel,
				payPrice:price,
				title:title,
				returnUrl:returl 
			}));
		});
		
		$("#test").on('click',function(){
			loadData(function(msg) {
				alert(msg);
			}, '/Pay/pay/hook/callback',$.toJSON({
				sign:"123123123123",
				timestamp:"111111"
			}));
		});
	});
</script>
<style type="text/css">
input {
	width:350px;
}
</style>
</head>
<body>
	订单ID：
	<input type="text" id="pay_id">
	<br /> 订单title：
	<input type="text" id="pay_title">
	<br /> 订单支付通道：
	<input type="text" id="pay_channel" value="UN_WEB">
	<br /> 订单支付金额：
	<input type="text" id="pay_price">
	<br /> 返回URL ：
	<input type="text" id="pay_returl" value="http://localhost:8081/Pay/unReturnUrl.jsp">
	<br />
	
	<button id="submit">发起</button>
	
	<button id="test">test</button>
</body>
</html>