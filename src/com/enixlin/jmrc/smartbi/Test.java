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


		map.clear();
		map.put("className", "CatalogService");
		map.put("methodName", "getCatalogElementById");
		map.put("params", "[Iee801fbd227e43eb01583d989ca32e84]");
		result = ns.HttpPost(url_query, map, encoding);

		
		map.clear();
		map.put("className", "CombinedQueryService");
		map.put("methodName", "openCombinedQuery");
		map.put("params", "[ Iee801fbd227e43eb01583d989ca32e84,null ]");
		result = ns.HttpPost(url_query, map, encoding);
		// System.out.println(result);
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
		JsonObject reportBean = jo.getAsJsonObject("reportBean");
		String clientConfig = reportBean.get("clientConfig").getAsString();

		JsonService jss = new JsonService(clientConfig);
		BizViewOutField = jss.getJsonObject().get("gridProp").getAsJsonObject().get("fieldProps").getAsJsonObject();
		JsonArray opParameter = jss.getJsonObject().get("paramSetting").getAsJsonObject().get("applyDefaultValueParams").getAsJsonArray();
		String StartDay=opParameter.get(0).toString();
	
		
		

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
//		System.out.println(result);

		map.clear();
		map.put("className", "CompositeService");
		map.put("methodName", "setParamValuesWithRelated");
		map.put("params", "[" + parameterPanelId +","+StartDay+ ",\"2019-05-01\",\"2019-05-01\"]");
		result = ns.HttpPost(url_query, map, encoding);
		System.out.println("设定参数");
		System.out.println(result);

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
		map.put("params", "[" + clientId + "]");
		result = ns.HttpPost(url_query, map, encoding);
		
		 System.out.println("clean sql");
		 System.out.println(result);

		map.clear();
		 map.put("className", "ClientReportService");
		 map.put("methodName", "setRowsPerPage");
		 map.put("params", "[" + clientId + ",3000]");
		 result = ns.HttpPost(url_query, map, encoding);
		 System.out.println(result);

		map.clear();
		map.put("className", "ClientReportService");
		map.put("methodName", "getTotalRowsCountWithFuture");
		map.put("params", "[" + clientId + ",0]");
		result = ns.HttpPost(url_query, map, encoding);
		// System.out.println(result);

		map.clear();
		map.put("className", "ClientReportService");
		map.put("methodName", "getReportDataWithFuture");
		map.put("params", "[" + clientId + ",0]");
		result = ns.HttpPost(url_query, map, encoding);
		 System.out.println(result);

	}

}
