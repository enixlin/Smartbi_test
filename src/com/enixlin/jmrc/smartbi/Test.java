package com.enixlin.jmrc.smartbi;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.impl.client.CloseableHttpClient;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.internal.LinkedTreeMap;

public class Test {

	public static void main(String[] args) {
		NetService ns = new NetService();

		ns.createHttpClient();

		String reportID1 = "";
		String reportID2 = "";
		String reportID3 = "";
		String clientId = "";
		String parameterPanelId = "";
		String url_query = "http://110.0.170.88:9083/smartbi/vision/RMIServlet?debug=true";
		Map<String, String> map = new HashMap<String, String>();
		String encoding = "utf8";
		String result = "";
		String BizTheme = "";
		JsonArray ja_BIZATTR;
		JsonArray ja_filter;
		JsonObject BizViewOutField;

		/**
		 * 用户登录，传入用户编号和密码 返回的结果是用户信息，是否已登录等内容
		 */
		map.clear();
		map.put("className", "CompositeService");
		map.put("methodName", "compositeLogin");
		map.put("params", "[\"32311\",\"123\"]");
		result = ns.HttpPost(url_query, map, encoding);

		// 打开自助分析中的国际业务
		map.clear();
		map.put("className", "CatalogService");
		map.put("methodName", "getCatalogElementById");
		map.put("params", "[Iee801fbd227e43eb01583d989ca32e84]");
		result = ns.HttpPost(url_query, map, encoding);
		// System.out.println(result);

		// 这里传入的参数freequery-gen488是一个变量，需要从dom树中取得，
//		但这个是随机节点的编号，要想办法取得？？？？？？？？？？
		map.clear();
		map.put("className", "CatalogService");
		map.put("methodName", "getCatalogElementById");
		map.put("params", "[freequery-gen488]");
		result = ns.HttpPost(url_query, map, encoding);

		map.clear();
		map.put("className", "CatalogService");
		map.put("methodName", "getCatalogElementById");
		map.put("params", "[Iee801fbd227e43eb01583d989ca32e84]");
		result = ns.HttpPost(url_query, map, encoding);

		// 打开复合查询SA-ISS-01-国际业务自助分析
		//
		// 这里返回了几个比较关键的参数 ,第0，1，8都是后续会用到，先用变量保存起来
		/*
		 * 0:"Iee8020950167e4e1e4e125bc0168abeb410827fc"
		 * 1"Iee8020950167e4e1e4e125bc0168abeb410827fd"
		 * 2:["BIZATTR.新会特色报表.国际业务交易自助分析主题.数据日期", "BIZATTR.新会特色报表.国际业务交易自助分析主题.客户号",…]
		 * 3:["FILTER.新会特色报表.国际业务交易自助分析主题.业务起始日期",
		 * "FILTER.新会特色报表.国际业务交易自助分析主题.p_sa_iss_end_date",…]
		 * 4:"THEME.新会特色报表.国际业务交易自助分析主题" 5："DS.新会特色报表" 6:"SA-ISS-01-国际业务自助分析"
		 * 7:"国际业务自助分析" 8:"Iee8020950167e4e1e4e125bc0168abeb410827fe" 9:""
		 * 10:{isAutoUpdateSelected: true}isAutoUpdateSelected:true
		 * 11:["BIZATTR.新会特色报表.国际业务交易自助分析主题.数据日期", "BIZATTR.新会特色报表.国际业务交易自助分析主题.客户号",…]
		 * 0:"BIZATTR.新会特色报表.国际业务交易自助分析主题.数据日期" 1:"BIZATTR.新会特色报表.国际业务交易自助分析主题.客户号"
		 * 2:"BIZATTR.新会特色报表.国际业务交易自助分析主题.客户名称" 3:"BIZATTR.新会特色报表.国际业务交易自助分析主题.客户类型"
		 * 4:"BIZATTR.新会特色报表.国际业务交易自助分析主题.业务号" 5:"BIZATTR.新会特色报表.国际业务交易自助分析主题.产品名称"
		 * 6:"BIZATTR.新会特色报表.国际业务交易自助分析主题.业务类型" 7:"BIZATTR.新会特色报表.国际业务交易自助分析主题.业务币种"
		 * 8:"BIZATTR.新会特色报表.国际业务交易自助分析主题.业务金额" 9:"BIZATTR.新会特色报表.国际业务交易自助分析主题.业务办理日期"
		 * 10:"BIZATTR.新会特色报表.国际业务交易自助分析主题.对方国别" 11:"BIZATTR.新会特色报表.国际业务交易自助分析主题.对方银行编号"
		 * 12:"BIZATTR.新会特色报表.国际业务交易自助分析主题.对方银行名称"
		 * 13:"BIZATTR.新会特色报表.国际业务交易自助分析主题.付款人账户" 14:"BIZATTR.新会特色报表.国际业务交易自助分析主题.付款人名称"
		 * 15:"BIZATTR.新会特色报表.国际业务交易自助分析主题.收款人账户" 16:"BIZATTR.新会特色报表.国际业务交易自助分析主题.收款人名称"
		 * 17:"BIZATTR.新会特色报表.国际业务交易自助分析主题.经办上级机构号"
		 * 18:"BIZATTR.新会特色报表.国际业务交易自助分析主题.经办上级机构名称"
		 * 19:"BIZATTR.新会特色报表.国际业务交易自助分析主题.经办网点号"
		 * 20:"BIZATTR.新会特色报表.国际业务交易自助分析主题.经办网点名称"
		 * 21:"BIZATTR.新会特色报表.国际业务交易自助分析主题.计价归属上级机构号"
		 * 22:"BIZATTR.新会特色报表.国际业务交易自助分析主题.计价归属上级机构名称"
		 * 23:"BIZATTR.新会特色报表.国际业务交易自助分析主题.计价归属网点号"
		 * 24:"BIZATTR.新会特色报表.国际业务交易自助分析主题.计价归属网点名称"
		 * 25:"BIZATTR.新会特色报表.国际业务交易自助分析主题.经办人员" 26:"BIZATTR.新会特色报表.国际业务交易自助分析主题.复核人员"
		 * 12:[]
		 */
		map.clear();
		map.put("className", "CombinedQueryService");
		map.put("methodName", "openCombinedQuery");
		map.put("params", "[ Iee801fbd227e43eb01583d989ca32e84,null ]");
		result = ns.HttpPost(url_query, map, encoding);
//		System.out.println(result);
		JsonService js = new JsonService(result);
		JsonArray ja = js.getJsonArray("result");
		ja_BIZATTR = (JsonArray) js.getJsonArray("result").get(2);
		ja_filter = (JsonArray) js.getJsonArray("result").get(3);
		reportID1 = ja.get(0).toString();
		reportID2 = ja.get(1).toString();
		reportID3 = ja.get(8).toString();
		BizTheme = ja.get(4).toString();

		// System.out.println(result);

		map.clear();
		map.put("className", "CatalogService");
		map.put("methodName", "getCatalogElementPath");
		map.put("params", "[ Iee801fbd227e43eb01583d989ca32e84]");
		result = ns.HttpPost(url_query, map, encoding);
		// System.out.println(result);

		// 将reportID1传入生成样版报告
		// 这里将返回一个关键对象

		// result:{
		// clientId:"Iee8020950167e4e1e4e125bc0168abeb413f281e",//
		// parameterPanelId:"Iee8020950167e4e1e4e125bc0168abeb413f281f"
		// reportBean:{
		// clientConfig:"{"gridProp":{"
		// + ""defaultRow":false,"
		// + ""fieldProps":{"
		// +
		// ""BizViewOutField.Iee8020950167e4e1e4e125bc0168abeb410827fd.Iee8020950167e4e1e4e125bc0168abeb410a2800":{"width":72,"align":"LEFT"},"
		// +
		// ""BizViewOutField.Iee8020950167e4e1e4e125bc0168abeb410827fd.Iee8020950167e4e1e4e125bc0168abeb410a2801":{"width":126,"align":"LEFT"},"
		// +
		// ""BizViewOutField.Iee8020950167e4e1e4e125bc0168abeb410827fd.Iee8020950167e4e1e4e125bc0168abeb410a2802":{"width":199,"align":"LEFT"},"
		// +
		// ""BizViewOutField.Iee8020950167e4e1e4e125bc0168abeb410827fd.Iee8020950167e4e1e4e125bc0168abeb410a2803":{"width":67,"align":"LEFT"},"
		// +
		// ""BizViewOutField.Iee8020950167e4e1e4e125bc0168abeb410827fd.Iee8020950167e4e1e4e125bc0168abeb410a2804":{"width":224,"align":"LEFT"},"
		// +
		// ""BizViewOutField.Iee8020950167e4e1e4e125bc0168abeb410827fd.Iee8020950167e4e1e4e125bc0168abeb410a2805":{"width":90,"align":"LEFT"},"
		// +
		// ""BizViewOutField.Iee8020950167e4e1e4e125bc0168abeb410827fd.Iee8020950167e4e1e4e125bc0168abeb410a2806":{"width":88,"align":"LEFT"},"
		// +
		// ""BizViewOutField.Iee8020950167e4e1e4e125bc0168abeb410827fd.Iee8020950167e4e1e4e125bc0168abeb410a2807":{"width":72,"align":"LEFT"},"
		// +
		// ""BizViewOutField.Iee8020950167e4e1e4e125bc0168abeb410827fd.Iee8020950167e4e1e4e125bc0168abeb410a2808":{"width":89,"align":"RIGHT"},"
		// +
		// ""BizViewOutField.Iee8020950167e4e1e4e125bc0168abeb410827fd.Iee8020950167e4e1e4e125bc0168abeb410a2809":{"width":94,"align":"LEFT"},"
		// +
		// ""BizViewOutField.Iee8020950167e4e1e4e125bc0168abeb410827fd.Iee8020950167e4e1e4e125bc0168abeb410a280a":{"width":"138","align":"LEFT","visible":true},"
		// +
		// ""BizViewOutField.Iee8020950167e4e1e4e125bc0168abeb410827fd.Iee8020950167e4e1e4e125bc0168abeb410a280b":{"width":91,"align":"LEFT"},"
		// +
		// ""BizViewOutField.Iee8020950167e4e1e4e125bc0168abeb410827fd.Iee8020950167e4e1e4e125bc0168abeb410a280c":{"width":208,"align":"LEFT"},"
		// +
		// ""BizViewOutField.Iee8020950167e4e1e4e125bc0168abeb410827fd.Iee8020950167e4e1e4e125bc0168abeb410a280d":{"width":193,"align":"LEFT"},"
		// +
		// ""BizViewOutField.Iee8020950167e4e1e4e125bc0168abeb410827fd.Iee8020950167e4e1e4e125bc0168abeb410a280e":{"width":256,"align":"LEFT"},"
		// +
		// ""BizViewOutField.Iee8020950167e4e1e4e125bc0168abeb410827fd.Iee8020950167e4e1e4e125bc0168abeb410a280f":{"width":137,"align":"LEFT"},"
		// +
		// ""BizViewOutField.Iee8020950167e4e1e4e125bc0168abeb410827fd.Iee8020950167e4e1e4e125bc0168abeb410a2810":{"width":242,"align":"LEFT"},"
		// +
		// ""BizViewOutField.Iee8020950167e4e1e4e125bc0168abeb410827fd.Iee8020950167e4e1e4e125bc0168abeb410a2811":{"width":105,"align":"LEFT"},"
		// +
		// ""BizViewOutField.Iee8020950167e4e1e4e125bc0168abeb410827fd.Iee8020950167e4e1e4e125bc0168abeb410a2812":{"width":132,"align":"LEFT"},"
		// +
		// ""BizViewOutField.Iee8020950167e4e1e4e125bc0168abeb410827fd.Iee8020950167e4e1e4e125bc0168abeb410a2813":{"width":78,"align":"LEFT"},"
		// +
		// ""BizViewOutField.Iee8020950167e4e1e4e125bc0168abeb410827fd.Iee8020950167e4e1e4e125bc0168abeb410a2814":{"width":93,"align":"LEFT"},"
		// +
		// ""BizViewOutField.Iee8020950167e4e1e4e125bc0168abeb410827fd.Iee8020950167e4e1e4e125bc0168abeb410a2815":{"width":129,"align":"LEFT"},"
		// +
		// ""BizViewOutField.Iee8020950167e4e1e4e125bc0168abeb410827fd.Iee8020950167e4e1e4e125bc0168abeb410a2816":{"width":143,"align":"LEFT"},"
		// +
		// ""BizViewOutField.Iee8020950167e4e1e4e125bc0168abeb410827fd.Iee8020950167e4e1e4e125bc0168abeb410a2817":{"width":105,"align":"LEFT"},"
		// +
		// ""BizViewOutField.Iee8020950167e4e1e4e125bc0168abeb410827fd.Iee8020950167e4e1e4e125bc0168abeb410a2818":{"width":122,"align":"LEFT"},"
		// +
		// ""BizViewOutField.Iee8020950167e4e1e4e125bc0168abeb410827fd.Iee8020950167e4e1e4e125bc0168abeb410a2819":{"width":71,"align":"LEFT"},"
		// },
		// rowsPerPage:30
		// }

		map.clear();
		map.put("className", "CombinedQueryService");
		map.put("methodName", "createSimpleReport");
		map.put("params", "[" + reportID1 + "]");
		result = ns.HttpPost(url_query, map, encoding);
		// System.out.println(result);
		js = new JsonService(result);
		JsonObject jo = js.getJsonObject("result");
		clientId = jo.get("clientId").toString();
		parameterPanelId = jo.get("parameterPanelId").toString();
		// BizViewOutField= jo.getAsJsonObject("reportBean");
		JsonObject jk = jo.getAsJsonObject("reportBean");
		String jm = jk.get("clientConfig").getAsString();
		JsonService jss = new JsonService(jm);
		BizViewOutField = jss.getJsonObject().get("gridProp").getAsJsonObject().get("fieldProps").getAsJsonObject();

		map.clear();
		map.put("className", "BusinessViewService");
		map.put("methodName", "getBusinessViewByBizThemeCatalogTree");
		map.put("params", "[" + BizTheme + "]");
		result = ns.HttpPost(url_query, map, encoding);

		// System.out.println(BizTheme);
		// System.out.println(result);

		map.clear();
		map.put("className", "CombinedQueryService");
		map.put("methodName", "getLocalFilterElements");
		map.put("params", "[" + reportID1 + "]");
		result = ns.HttpPost(url_query, map, encoding);

		map.clear();
		map.put("className", "ClientReportService");
		map.put("methodName", "getFunctionValue");
		map.put("params", "[ " + clientId + ",CurrentReportName()]");
		result = ns.HttpPost(url_query, map, encoding);
		// System.out.println(reportID1);
		// System.out.println(clientId);
		System.out.println(result);

		map.clear();
		map.put("className", "CombinedQueryService");
		map.put("methodName", "initFromBizViewEx");
		map.put("params", "[" + reportID1 + "," + clientId + "," + reportID2 + ",true]");
		result = ns.HttpPost(url_query, map, encoding);
		// System.out.println(result);

		map.clear();
		map.put("className", "CombinedQueryService");
		map.put("methodName", "setSimpleReportClientId");
		map.put("params", "[" + reportID1 + "," + clientId + "]");
		result = ns.HttpPost(url_query, map, encoding);
		// System.out.println(result);

		map.clear();
		map.put("className", "ConfigClientService");
		map.put("methodName", "getSystemConfig");
		map.put("params", "[REPORT_BROWSE_AUTO_REFRESH]");
		result = ns.HttpPost(url_query, map, encoding);
		// System.out.println(result);

		map.clear();
		map.put("className", "CatalogService");
		map.put("methodName", "getCatalogElementPath");
		map.put("params", "[" + ja_BIZATTR.get(0) + "]");
		result = ns.HttpPost(url_query, map, encoding);
		// System.out.println(result);

		map.clear();
		map.put("className", "CatalogService");
		map.put("methodName", "getChildElements");
		map.put("params", "[" + ja_BIZATTR.get(0) + "]");
		result = ns.HttpPost(url_query, map, encoding);
		// System.out.println(result);

		map.clear();
		map.put("className", "CatalogService");
		map.put("methodName", "getChildElements");
		map.put("params", "['BIZOBJ.新会特色报表.国际业务交易自助分析主题.国际业务交易自助分析表']");
		result = ns.HttpPost(url_query, map, encoding);
		// System.out.println(result);

		map.clear();
		map.put("className", "CatalogService");
		map.put("methodName", "getChildElements");
		map.put("params", "['BIZOBJ.新会特色报表.国际业务交易自助分析主题.基本属性']");
		result = ns.HttpPost(url_query, map, encoding);
		// System.out.println(result);

		map.clear();
		map.put("className", "BusinessThemeService");
		map.put("methodName", "getBusinessAttributesDataType");
		map.put("params", "['BIZOBJ.新会特色报表.国际业务交易自助分析主题.基本属性']");
		result = ns.HttpPost(url_query, map, encoding);
		// System.out.println(result);
		//
		map.clear();
		map.put("className", "CatalogService");
		map.put("methodName", "getCatalogElementPath");
		map.put("params", "[" + ja_BIZATTR.get(1) + "]");
		result = ns.HttpPost(url_query, map, encoding);
		// System.out.println(result);

		map.clear();
		map.put("className", "CatalogService");
		map.put("methodName", "getChildElements");
		map.put("params", "['BIZOBJ.新会特色报表.国际业务交易自助分析主题.客户信息']");
		result = ns.HttpPost(url_query, map, encoding);
		// System.out.println(result);

		map.clear();
		map.put("className", "BusinessThemeService");
		map.put("methodName", "getBusinessAttributesDataType");
		map.put("params", "['BIZOBJ.新会特色报表.国际业务交易自助分析主题.客户信息']");
		result = ns.HttpPost(url_query, map, encoding);
		// System.out.println(result);

		map.clear();
		map.put("className", "CatalogService");
		map.put("methodName", "getCatalogElementPath");
		map.put("params", "[" + ja_BIZATTR.get(2) + "]");
		result = ns.HttpPost(url_query, map, encoding);
		// System.out.println(result);

		map.clear();
		map.put("className", "CatalogService");
		map.put("methodName", "getCatalogElementPath");
		map.put("params", "[" + ja_BIZATTR.get(3) + "]");
		result = ns.HttpPost(url_query, map, encoding);
		// System.out.println(result);

		map.clear();
		map.put("className", "CatalogService");
		map.put("methodName", "getCatalogElementPath");
		map.put("params", "[" + ja_BIZATTR.get(4) + "]");
		result = ns.HttpPost(url_query, map, encoding);
		// System.out.println(result);

		map.clear();
		map.put("className", "CatalogService");
		map.put("methodName", "getCatalogElementPath");
		map.put("params", "[" + ja_BIZATTR.get(5) + "]");
		result = ns.HttpPost(url_query, map, encoding);
		// System.out.println(result);

		map.clear();
		map.put("className", "CatalogService");
		map.put("methodName", "getCatalogElementPath");
		map.put("params", "[" + ja_BIZATTR.get(6) + "]");
		result = ns.HttpPost(url_query, map, encoding);
		// System.out.println(result);

		map.clear();
		map.put("className", "CatalogService");
		map.put("methodName", "getCatalogElementPath");
		map.put("params", "[" + ja_BIZATTR.get(7) + "]");
		result = ns.HttpPost(url_query, map, encoding);
		// System.out.println(result);

		map.clear();
		map.put("className", "CatalogService");
		map.put("methodName", "getCatalogElementPath");
		map.put("params", "[" + ja_BIZATTR.get(8) + "]");
		result = ns.HttpPost(url_query, map, encoding);
		// System.out.println(result);

		map.clear();
		map.put("className", "CatalogService");
		map.put("methodName", "getCatalogElementPath");
		map.put("params", "[" + ja_BIZATTR.get(9) + "]");
		result = ns.HttpPost(url_query, map, encoding);
		// System.out.println(result);
		map.clear();
		map.put("className", "CatalogService");
		map.put("methodName", "getCatalogElementPath");
		map.put("params", "[" + ja_BIZATTR.get(10) + "]");
		result = ns.HttpPost(url_query, map, encoding);
		// System.out.println(result);

		map.clear();
		map.put("className", "CatalogService");
		map.put("methodName", "getChildElements");
		map.put("params", "['BIZOBJ.新会特色报表.国际业务交易自助分析主题.交易对象信息']");
		result = ns.HttpPost(url_query, map, encoding);
		// System.out.println(result);

		map.clear();
		map.put("className", "BusinessThemeService");
		map.put("methodName", "getBusinessAttributesDataType");
		map.put("params", "['BIZOBJ.新会特色报表.国际业务交易自助分析主题.交易对象信息']");
		result = ns.HttpPost(url_query, map, encoding);
		// System.out.println(result);

		map.clear();
		map.put("className", "CatalogService");
		map.put("methodName", "getCatalogElementPath");
		map.put("params", "[" + ja_BIZATTR.get(11) + "]");
		result = ns.HttpPost(url_query, map, encoding);
		// System.out.println(result);
		map.clear();
		map.put("className", "CatalogService");
		map.put("methodName", "getCatalogElementPath");
		map.put("params", "[" + ja_BIZATTR.get(12) + "]");
		result = ns.HttpPost(url_query, map, encoding);
		// System.out.println(result);

		map.clear();
		map.put("className", "CatalogService");
		map.put("methodName", "getCatalogElementPath");
		map.put("params", "[" + ja_BIZATTR.get(13) + "]");
		result = ns.HttpPost(url_query, map, encoding);
		// System.out.println(result);

		map.clear();
		map.put("className", "CatalogService");
		map.put("methodName", "getCatalogElementPath");
		map.put("params", "[" + ja_BIZATTR.get(14) + "]");
		result = ns.HttpPost(url_query, map, encoding);
		// System.out.println(result);

		map.clear();
		map.put("className", "CatalogService");
		map.put("methodName", "getCatalogElementPath");
		map.put("params", "[" + ja_BIZATTR.get(15) + "]");
		result = ns.HttpPost(url_query, map, encoding);
		// System.out.println(result);

		map.clear();
		map.put("className", "CatalogService");
		map.put("methodName", "getCatalogElementPath");
		map.put("params", "[" + ja_BIZATTR.get(16) + "]");
		result = ns.HttpPost(url_query, map, encoding);
		// System.out.println(result);

		map.clear();
		map.put("className", "CatalogService");
		map.put("methodName", "getCatalogElementPath");
		map.put("params", "[" + ja_BIZATTR.get(17) + "]");
		result = ns.HttpPost(url_query, map, encoding);
		// System.out.println(result);

		map.clear();
		map.put("className", "CatalogService");
		map.put("methodName", "getCatalogElementPath");
		map.put("params", "[" + ja_BIZATTR.get(18) + "]");
		result = ns.HttpPost(url_query, map, encoding);
		// System.out.println(result);

		map.clear();
		map.put("className", "CatalogService");
		map.put("methodName", "getChildElements");
		map.put("params", "['BIZOBJ.新会特色报表.国际业务交易自助分析主题.归属机构信息']");
		result = ns.HttpPost(url_query, map, encoding);
		// System.out.println(result);

		map.clear();
		map.put("className", "BusinessThemeService");
		map.put("methodName", "getBusinessAttributesDataType");
		map.put("params", "['BIZOBJ.新会特色报表.国际业务交易自助分析主题.归属机构信息']");
		result = ns.HttpPost(url_query, map, encoding);
		// System.out.println(result);

		map.clear();
		map.put("className", "CatalogService");
		map.put("methodName", "getCatalogElementPath");
		map.put("params", "[" + ja_BIZATTR.get(18) + "]");
		result = ns.HttpPost(url_query, map, encoding);
		// System.out.println(result);
		map.clear();
		map.put("className", "CatalogService");
		map.put("methodName", "getCatalogElementPath");
		map.put("params", "[" + ja_BIZATTR.get(19) + "]");
		result = ns.HttpPost(url_query, map, encoding);
		// System.out.println(result);
		map.clear();
		map.put("className", "CatalogService");
		map.put("methodName", "getCatalogElementPath");
		map.put("params", "[" + ja_BIZATTR.get(20) + "]");
		result = ns.HttpPost(url_query, map, encoding);
		// System.out.println(result);
		map.clear();
		map.put("className", "CatalogService");
		map.put("methodName", "getCatalogElementPath");
		map.put("params", "[" + ja_BIZATTR.get(21) + "]");
		result = ns.HttpPost(url_query, map, encoding);
		// System.out.println(result);
		map.clear();
		map.put("className", "CatalogService");
		map.put("methodName", "getCatalogElementPath");
		map.put("params", "[" + ja_BIZATTR.get(22) + "]");
		result = ns.HttpPost(url_query, map, encoding);
		// System.out.println(result);
		map.clear();
		map.put("className", "CatalogService");
		map.put("methodName", "getCatalogElementPath");
		map.put("params", "[" + ja_BIZATTR.get(23) + "]");
		result = ns.HttpPost(url_query, map, encoding);
		// System.out.println(result);
		map.clear();
		map.put("className", "CatalogService");
		map.put("methodName", "getCatalogElementPath");
		map.put("params", "[" + ja_BIZATTR.get(24) + "]");
		result = ns.HttpPost(url_query, map, encoding);
		// System.out.println(result);
		map.clear();
		map.put("className", "CatalogService");
		map.put("methodName", "getCatalogElementPath");
		map.put("params", "[" + ja_BIZATTR.get(25) + "]");
		result = ns.HttpPost(url_query, map, encoding);
		// System.out.println(result);
		map.clear();
		map.put("className", "CatalogService");
		map.put("methodName", "getCatalogElementPath");
		map.put("params", "[" + ja_BIZATTR.get(26) + "]");
		result = ns.HttpPost(url_query, map, encoding);
		// System.out.println(result);

		map.clear();
		map.put("className", "CatalogService");
		map.put("methodName", "getCatalogElementPath");
		map.put("params", "[" + ja_filter.get(0) + "]");
		result = ns.HttpPost(url_query, map, encoding);
		// System.out.println(result);

		map.clear();
		map.put("className", "CatalogService");
		map.put("methodName", "getChildElements");
		map.put("params", "[ Iee80208c3589431b01593f95adda57ec ]");
		result = ns.HttpPost(url_query, map, encoding);
		// System.out.println(result);

		map.clear();
		map.put("className", "FilterService");
		map.put("methodName", "isHiddenFilter");
		map.put("params", "[" + ja_filter.get(1) + "]");
		result = ns.HttpPost(url_query, map, encoding);
		// System.out.println(result);

		map.clear();
		map.put("className", "FilterService");
		map.put("methodName", "isHiddenFilter");
		map.put("params", "[" + ja_filter.get(2) + "]");
		result = ns.HttpPost(url_query, map, encoding);
		// System.out.println(result);
		map.clear();
		map.put("className", "FilterService");
		map.put("methodName", "isHiddenFilter");
		map.put("params", "[" + ja_filter.get(3) + "]");
		result = ns.HttpPost(url_query, map, encoding);
		// System.out.println(result);
		map.clear();
		map.put("className", "FilterService");
		map.put("methodName", "isHiddenFilter");
		map.put("params", "[" + ja_filter.get(4) + "]");
		result = ns.HttpPost(url_query, map, encoding);
		// System.out.println(result);
		map.clear();
		map.put("className", "FilterService");
		map.put("methodName", "isHiddenFilter");
		map.put("params", "[" + ja_filter.get(5) + "]");
		result = ns.HttpPost(url_query, map, encoding);
		// System.out.println(result);
		map.clear();
		map.put("className", "FilterService");
		map.put("methodName", "isHiddenFilter");
		map.put("params", "[" + ja_filter.get(6) + "]");
		result = ns.HttpPost(url_query, map, encoding);
		// System.out.println(result);
		map.clear();
		map.put("className", "FilterService");
		map.put("methodName", "isHiddenFilter");
		map.put("params", "[" + ja_filter.get(7) + "]");
		result = ns.HttpPost(url_query, map, encoding);
		// System.out.println(result);
		map.clear();
		map.put("className", "FilterService");
		map.put("methodName", "isHiddenFilter");
		map.put("params", "[" + ja_filter.get(8) + "]");
		result = ns.HttpPost(url_query, map, encoding);
		// System.out.println(result);

		map.clear();
		map.put("className", "CatalogService");
		map.put("methodName", "getCatalogElementPath");
		map.put("params", "[" + ja_filter.get(0) + "]");
		result = ns.HttpPost(url_query, map, encoding);
		// System.out.println(result);
		map.clear();
		map.put("className", "CatalogService");
		map.put("methodName", "getCatalogElementPath");
		map.put("params", "[" + ja_filter.get(1) + "]");
		result = ns.HttpPost(url_query, map, encoding);
		map.clear();
		map.put("className", "CatalogService");
		map.put("methodName", "getCatalogElementPath");
		map.put("params", "[" + ja_filter.get(2) + "]");
		result = ns.HttpPost(url_query, map, encoding);
		map.clear();
		map.put("className", "CatalogService");
		map.put("methodName", "getCatalogElementPath");
		map.put("params", "[" + ja_filter.get(3) + "]");
		result = ns.HttpPost(url_query, map, encoding);
		map.clear();
		map.put("className", "CatalogService");
		map.put("methodName", "getCatalogElementPath");
		map.put("params", "[" + ja_filter.get(4) + "]");
		result = ns.HttpPost(url_query, map, encoding);
		map.clear();
		map.put("className", "CatalogService");
		map.put("methodName", "getCatalogElementPath");
		map.put("params", "[" + ja_filter.get(5) + "]");
		result = ns.HttpPost(url_query, map, encoding);
		map.clear();
		map.put("className", "CatalogService");
		map.put("methodName", "getCatalogElementPath");
		map.put("params", "[" + ja_filter.get(6) + "]");
		result = ns.HttpPost(url_query, map, encoding);
		map.clear();
		map.put("className", "CatalogService");
		map.put("methodName", "getCatalogElementPath");
		map.put("params", "[" + ja_filter.get(7) + "]");
		result = ns.HttpPost(url_query, map, encoding);
		map.clear();
		map.put("className", "CatalogService");
		map.put("methodName", "getCatalogElementPath");
		map.put("params", "[" + ja_filter.get(8) + "]");
		result = ns.HttpPost(url_query, map, encoding);
		// System.out.println(result);

		/*
		 * 以下是报表输出
		 * 
		 */

		System.out.println("reportID1 is:" + reportID1);
		System.out.println("reportID2 is:" + reportID2);
		System.out.println("clientId is" + clientId);
		map.clear();
		map.put("className", "ClientReportService");
		map.put("methodName", "clearSQLResultStore");
		map.put("params", "[" + reportID1 + "]");
		result = ns.HttpPost(url_query, map, encoding);
		// System.out.println(result);

		map.clear();
		map.put("className", "ClientReportService");
		map.put("methodName", "setRowsPerPage");
		map.put("params", "[" + reportID1 + ",30]");
		result = ns.HttpPost(url_query, map, encoding);
		System.out.println(result);

		map.clear();
		map.put("className", "ClientReportService");
		map.put("methodName", "getTotalRowsCountWithFuture");
		map.put("params", "[" + reportID1 + ",0]");
		result = ns.HttpPost(url_query, map, encoding);
		// System.out.println(result);

		map.clear();
		map.put("className", "ClientReportService");
		map.put("methodName", "getReportDataWithFuture");
		map.put("params", "[" + reportID1 + ",0]");
		result = ns.HttpPost(url_query, map, encoding);
		// System.out.println(result);

	}

}
