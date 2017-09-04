package controllers.wechat.config;

import java.util.Map;

import controllers.wechat.model.WechatJsApiPay;
import controllers.wechat.model.WechatPayData;
import controllers.wechat.util.Configure;
import controllers.wechat.util.HttpsRequest;
import controllers.wechat.util.JaxbParser;
import controllers.wechat.util.WechatUtil;
import models.hmcore.order.Order;

/**
 * 组装微信支付参数
 * @author zhangpeng
 *
 */
public class WechatPayParams {
	
	/**
	 * 设置微信支付数据
	 * @param payType JSAPI NATIVE
	 * @param order
	 * @return
	 */
	public static WechatPayData setWechatPayData(Order order, String payType){
		WechatPayData jsapiData = new WechatPayData();
		jsapiData.setOut_trade_no(order.out_trade_no);
		jsapiData.setAppid(WechatConfig.APPID);
		jsapiData.setMch_id(WechatConfig.MCHID);
		jsapiData.setBody(order.body);
		jsapiData.setNotify_url(WechatConfig.NOTIFY_URL);
		jsapiData.setSpbill_create_ip(WechatConfig.CURL_PROXY_HOST);
		jsapiData.setTotal_fee(order.total_fee);
		jsapiData.setTrade_type(payType);
		jsapiData.setOpenid(order.openid);
		jsapiData.setNonce_str(WechatUtil.createNonceSstr());
		jsapiData.setProduct_id(String.valueOf(order.product_id));
		jsapiData.setSign(WechatUtil.createSign(jsapiData));
		return jsapiData;
	}

	/**
	 * 设置微信支付数据
	 * @param payType JSAPI NATIVE
	 * @param order
	 * @return
	 */
	public static WechatPayData setWechatPayData(String out_trade_no, String body, long total_fee, String openid, String product_id, String payType){
		WechatPayData jsapiData = new WechatPayData();
		jsapiData.setOut_trade_no(out_trade_no);
		jsapiData.setAppid(WechatConfig.APPID);
		jsapiData.setMch_id(WechatConfig.MCHID);
		jsapiData.setBody(body);
		jsapiData.setNotify_url(WechatConfig.NOTIFY_URL);
		jsapiData.setSpbill_create_ip(WechatConfig.CURL_PROXY_HOST);
		jsapiData.setTotal_fee(total_fee);
		jsapiData.setTrade_type(payType);
		jsapiData.setOpenid(openid);
		jsapiData.setNonce_str(WechatUtil.createNonceSstr());
		jsapiData.setProduct_id(String.valueOf(product_id));
		jsapiData.setSign(WechatUtil.createSign(jsapiData));
		return jsapiData;
	}

	/**
	 * 获取JsApiPay结果
	 * @param jsapiData
	 * @return
	 */
	public static Map<String, String> getWechatPayResult(WechatPayData jsapiData) throws Exception{
		JaxbParser parser = new JaxbParser(jsapiData.getClass());
		String[] keyStringarr = WechatUtil.getFiledNameArr(jsapiData);
		parser.setCdataNode(keyStringarr);
		String postXml = parser.toXML(jsapiData);
		HttpsRequest requret = new HttpsRequest();
		String result = requret.sendPost(Configure.PAY_API, postXml);
		return WechatUtil.getMapFromXML(result);
	}
	/**
	 * 设置微信支付JSApi数据
	 * @param prepay_id
	 * @return
	 */
	public static WechatJsApiPay setWechatJsApiPay(String prepay_id){
		WechatJsApiPay pay = new WechatJsApiPay();
		pay.setAppId(WechatConfig.APPID);
		pay.setNonceStr(WechatUtil.createNonceSstr());
		pay.setSignType("MD5");
		pay.setTimeStamp(System.currentTimeMillis() / 1000 + "");
		pay.setWxpackage("prepay_id=" + prepay_id);
		pay.setPaySign(WechatUtil.createSign(pay));
		return pay;
	}

}
