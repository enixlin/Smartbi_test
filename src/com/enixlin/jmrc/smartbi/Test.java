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
		String ClientId = "";
		String url_query = "http://110.0.170.88:9083/smartbi/vision/RMIServlet?debug=true";
		Map<String, String> map = new HashMap<String, String>();
		String encoding = "utf8";
		String result = "";

		
		url_query = "http://110.0.170.88:9083/smartbi/vision/RMIServlet?debug=true";
		
		/**
		 * 用户登录，传入用户编号和密码
		 * 返回的结果是用户信息，是否已登录等内容
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
		map.put("params", "[\"Iee801fbd227e43eb01583d989ca32e84\"]");
		result = ns.HttpPost(url_query, map, encoding);
		// System.out.println(result);

		// 打开复合查询SA-ISS-01-国际业务自助分析
		map.clear();
		map.put("className", "CombinedQueryService");
		map.put("methodName", "openCombinedQuery");
		map.put("params", "[\"Iee801fbd227e43eb01583d989ca32e84\",null ]");
		result = ns.HttpPost(url_query, map, encoding);
		// System.out.println(result);
		JsonService js = new JsonService(result);
		JsonArray ja = js.getJsonArray("result");
		reportID1 = ja.get(0).toString();
		reportID2 = ja.get(1).toString();

		// System.out.println(result);

		// 将reportID1传入生成样版报告
		url_query = "http://110.0.170.88:9083/smartbi/vision/RMIServlet?debug=true";
		map.clear();
		map.put("className", "CombinedQueryService");
		map.put("methodName", "createSimpleReport");
		map.put("params", "[\'" + reportID1 + "\']");
		result = ns.HttpPost(url_query, map, encoding);
		JsonService js1 = new JsonService(result);
		JsonObject ja1 = js1.getJsonObject("result");
		ClientId = ja1.get("clientId").toString();
		// System.out.println(result);

		url_query = "http://110.0.170.88:9083/smartbi/vision/RMIServlet?debug=true";
		map.clear();
		map.put("className", "BusinessViewService");
		map.put("methodName", "getBusinessViewByBizThemeCatalogTree");
		map.put("params", "[\'THEME.新会特色报表.国际业务交易自助分析主题\']");
		result = ns.HttpPost(url_query, map, encoding);
		// System.out.println(result);

		String p1 = "Iee80208c3589431b01593f95adda57ec";
		url_query = "http://110.0.170.88:9083/smartbi/vision/RMIServlet?debug=true";
		map.clear();
		map.put("className", "CombinedQueryService");
		map.put("methodName", "createSimpleReport");
		map.put("params", "[\'" + p1 + "\']");
		result = ns.HttpPost(url_query, map, encoding);
		JsonService js2 = new JsonService(result);
		JsonObject jo = js2.getJsonObject("result");
		String clientID_1 = jo.get("clientId").toString();
		String parameterPanelId = jo.get("parameterPanelId").toString();

		// System.out.println(result);

		// className:CatalogService
		// methodName:getChildElements
		// params:["Iee80208c3589431b01593f95adda57ec"]

		url_query = "http://110.0.170.88:9083/smartbi/vision/RMIServlet?debug=true";
		map.clear();
		map.put("className", "ClientReportService");
		map.put("methodName", "getFunctionValue");
		map.put("params", "[\'" + clientID_1 + "\',\'CurrentReportName()\']");
		result = ns.HttpPost(url_query, map, encoding);
		System.out.println(result);

		url_query = "http://110.0.170.88:9083/smartbi/vision/RMIServlet?debug=true";
		map.clear();
		map.put("className", "ClientReportService");
		map.put("methodName", "setRowsPerPage");
		map.put("params", "[\'" + ClientId + "\',1000 ]");
		result = ns.HttpPost(url_query, map, encoding);
		// System.out.println(result);

		url_query = "http://110.0.170.88:9083/smartbi/vision/RMIServlet?debug=true";
		map.clear();
		map.put("className", "ClientReportService");
		map.put("methodName", "getReportDataWithFuture");
		map.put("params", "[\'" + ClientId + "\',0 ]");
		result = ns.HttpPost(url_query, map, encoding);
		// System.out.println(result);
		// 这里就是输出结果
		// className:ClientReportService
		// methodName:getReportDataWithFuture
		// params:["Iee8020950167e4e1e4e125bc01684c58f82513b0",0]

		// 这里就是输出结果d

		//
		//

	}

}
