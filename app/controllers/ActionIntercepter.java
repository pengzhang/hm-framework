package controllers;

import play.Logger;
import play.mvc.After;
import play.mvc.Before;
import play.mvc.Catch;
import play.mvc.Controller;
import play.mvc.Finally;
import tasks.hmcore.AccessLogTask;

public class ActionIntercepter extends Controller {

	@Before()
	private static void actionBeforeProcess() {
		AccessLogTask.record();
	}

	@After
	private static void actionAfterProcess() {
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
