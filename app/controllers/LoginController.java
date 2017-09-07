package controllers;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;

import controllers.wechat.WechatAuthController;
import controllers.wechat.config.WechatConfig;
import play.Logger;
import play.Play;
import play.cache.Cache;
import play.mvc.Before;
import play.mvc.Controller;
import plugins.router.Get;
import plugins.router.Post;
import utils.UserAgentUtil;

public class LoginController extends Controller {

	@Before(unless= {"login","loginAction","binduser","bindUserAction"})
	public static void checkLogin() {

		// 如果浏览器是微信进行授权操作
		String wxLogin = Play.configuration.getProperty("wechat.login", "true");
		if (UserAgentUtil.isWechat(request) && wxLogin.equals("true")) {

			// 如果openid为空,要求用户授权
			if (StringUtils.isEmpty(session.get("openid"))) {

				String returnUrl = request.getBase() + "/wechat/openid";
				Logger.info("get wechat opendid, request url:%s", returnUrl);

				// 记录用户请求的URL
				String state = RandomStringUtils.randomAlphanumeric(20);
				Cache.set(state, request.url, "5mn");

				try {
					returnUrl = URLEncoder.encode(returnUrl, "utf-8");
				} catch (UnsupportedEncodingException e) {
					Logger.info("wechat auth redirect url encode error,%s", e.getMessage());
				}

				Logger.info("wechat auth redirect url,%s", returnUrl);
				//微信静默登录和授权登录
				String wxSnsApi = Play.configuration.getProperty("wechat.snsapi", "base");
				if(wxSnsApi.equals("base")) {
					WechatAuthController.snsapi_base(WechatConfig.APPID, returnUrl, state);
				}else {
					WechatAuthController.snsapi_userinfo(WechatConfig.APPID, returnUrl, state);
				}
			}else {
				//是否要求微信绑定用户
				String binduser = Play.configuration.getProperty("wechat.binduser", "false");
				if(binduser.equals("true")) {
					binduser();
				}
			}
		} else {
			login();
		}
	}

	@Get("/user/login")
	public static void login() {
		renderText("TODO");
	}

	@Post("/user/login")
	public static void loginAction() {
		renderText("TODO");
	}

	@Get("/user/binduser")
	public static void binduser() {
		renderText("TODO");
	}

	@Post("/user/binduser")
	public static void bindUserAction() {
		renderText("TODO");
	}

}
