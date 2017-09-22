package controllers;

import java.lang.reflect.Method;

import annotations.DefaultPageParam;
import play.Logger;
import play.Play;
import play.mvc.After;
import play.mvc.Before;
import play.mvc.Catch;
import play.mvc.Controller;
import play.mvc.Finally;
import tasks.AccessLogTask;
import utils.UserAgentUtil;

public class ActionIntercepter extends Controller {

	@Before()
	static void actionBeforeProcess() {
		AccessLogTask.record(request);
		
		//是否必须微信访问
		String wxLoginNeed = Play.configuration.getProperty("wechat.loginneed", "false");
		if(wxLoginNeed.equals("true")) {
			if(!UserAgentUtil.isWechat(request)) {
				error(666, "请使用微信登录");
			}
		}
	}
	
	@Before
	static void devDefaultUser() {
		if(Play.mode.isDev()) {
			session.put("uid", 1);
		}
	}
	
	@Before
	static void defaultPageParam() throws Exception{
		Class controller = Class.forName("controllers." + request.action.substring(0, request.action.lastIndexOf(".")));
		Method[] methods = controller.getMethods();
		for(Method method : methods) {
			if(method.isAnnotationPresent(DefaultPageParam.class)  && request.actionMethod.equals(method.getName())) {
				String page = request.params.get("page");
				if(page == null) {
					request.params.put("page", "1");
				}else if(Integer.parseInt(page) < 1) {
					request.params.put("page", "1");
				}
				
				String size = request.params.get("size");
				if(size == null) {
					request.params.put("size", "10");
				}else if(Integer.parseInt(size) < 1) {
					request.params.put("size", "10");
				}
			}
		}
	}

	@After
	static void actionAfterProcess() {
		
	}

	@Catch(value = Throwable.class, priority = 1)
	static void actionExceptionProcess(Throwable throwable) {
		throwable.printStackTrace();
		Logger.error("exception %s", throwable.getMessage());
		error(throwable.getMessage());
	}

	@Finally
	static void log() {
	}
	

}
