package controllers;

import com.qiniu.util.Json;

import models.hmcore.common.ResponseData;
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
	private static void actionBeforeProcess() {
		AccessLogTask.record(request);
		
		//是否必须微信访问
		String wxLoginNeed = Play.configuration.getProperty("wechat.loginneed", "false");
		if(wxLoginNeed.equals("true")) {
			if(!UserAgentUtil.isWechat(request)) {
				error(666, "请使用微信登录");
			}
		}
	}

	@After
	private static void actionAfterProcess() {
		Object status = renderArgs.get("status");
		Object data = renderArgs.get("data");
		Object message = renderArgs.get("message");
		
		if(request.format.equals("json")) {
			renderJSON(ResponseData.response((boolean)status, data, (String)message));
		}else if(request.format.equals("xml")){
			renderXml(ResponseData.response((boolean)status, data, (String)message));
		}
		render();
	}

	@Catch(value = Throwable.class, priority = 1)
	private static void actionExceptionProcess(Throwable throwable) {
		throwable.printStackTrace();
		Logger.error("exception %s", throwable.getMessage());
		error(throwable.getMessage());
	}

	@Finally
	static void log() {
	}
	

}
