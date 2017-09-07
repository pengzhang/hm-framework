package controllers;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;

import controllers.wechat.WechatAuthController;
import controllers.wechat.config.WechatConfig;
import models.hmcore.user.User;
import play.Logger;
import play.cache.Cache;
import play.mvc.Before;
import play.mvc.Controller;
import utils.HConstant;

public class LoginActionIntercepter extends Controller {
	
	public static void login() {
		
		// 如果浏览器是微信进行授权操作
		String userAgentStr = StringUtils.defaultIfBlank(request.headers.get("user-agent").value(),HConstant.DEFAULTUSERAGENT);
		if (userAgentStr.contains("MicroMessenger")) {
			String returnUrl = request.getBase() + "/wechat/openid";
			Logger.info("get wechat opendid, request url:%s", returnUrl);

			// 记录用户请求的URL
			String state = RandomStringUtils.randomAlphanumeric(6);
			Cache.set(state, request.url, "30s");

			try {
				returnUrl = URLEncoder.encode(returnUrl, "utf-8");
			} catch (UnsupportedEncodingException e) {
				Logger.info("wechat auth redirect url encode error,%s", e.getMessage());
			}

			// 如果openid为空,要求用户授权
			if (StringUtils.isEmpty(session.get("openid"))) {
				Logger.info("wechat auth redirect url,%s", returnUrl);
				WechatAuthController.snsapi_userinfo(WechatConfig.APPID, returnUrl, state);
			} 
		} 
		else {
//			session.put("uid", 1);
			renderText("请用微信访问");
		}
	}

}
