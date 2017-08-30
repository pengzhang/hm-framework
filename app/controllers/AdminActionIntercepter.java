package controllers;

import org.apache.commons.lang.StringUtils;

import annotation.hmcore.Login;
import exceptions.hmcore.ControllerException;
import play.Logger;
import play.Play;
import play.mvc.After;
import play.mvc.Before;
import play.mvc.Catch;
import play.mvc.Controller;
import play.mvc.Finally;
import tasks.hmcore.AccessLogTask;

public class AdminActionIntercepter extends Controller {

	/*
	 * Custom Configuration
	  #Check User Login
	  check.login=enabled
	  login.url=http://user.hm55.cn/login
	  #Check User Permission  
	  check.permission=enabled
	 */

	@Before()
	private static void actionBeforeProcess() {
		AccessLogTask.record();
	}

	@After
	private static void actionAfterProcess() {
	}
	
	@Catch(value = ControllerException.class, priority = 2)
	private static void actionControllerExceptionProcess(ControllerException ce) {
		ce.printStackTrace();
		Logger.error("controller exception %s", ce.getMessage());
		error(ce.getMessage());
	}

	@Catch(value = Throwable.class, priority = 1)
	private static void actionExceptionProcess(Throwable throwable) {
		throwable.printStackTrace();
		Logger.error("exception %s", throwable.getMessage());
		error(throwable.getMessage());
	}
	
	@Finally
    static void log() {
        //Logger.info("Response contains : " + response.out);
    }

}
