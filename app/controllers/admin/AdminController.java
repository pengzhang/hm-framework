package controllers.admin;


import static java.util.Arrays.asList;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.jamonapi.Monitor;
import com.jamonapi.MonitorFactory;

import annotation.hmcore.Login;
import controllers.AdminActionIntercepter;
import play.CorePlugin;
import play.Invoker;
import play.Play;
import play.PlayPlugin;
import play.cache.Cache;
import play.cache.EhCacheImpl;
import play.mvc.Controller;
import play.mvc.With;
import plugins.hmcore.router.Get;
import tasks.hmcore.SyncSystemSettingTask;
import utils.hmcore.PZDate;

@Login
@With(AdminActionIntercepter.class)
public class AdminController extends Controller{
	
	@Get("/admin/info")
	public static void info() {
		render();
	}
	
	@Get("/admin/cache/clear")
	public static void clearCache() {
		long access =  Cache.get(PZDate.today(), Long.class);
		Cache.clear();
		Cache.set(PZDate.today(), access);
		flash.success("清除缓存成功");
		redirect("/admin/info");
	}
	
	@Get("/admin/status")
	public static void adminStatus() {
		PlayPlugin plugin = Play.pluginCollection.getPluginInstance(CorePlugin.class);
		List status = Arrays.asList(plugin.getStatus().split("\n"));
		render(status);
	}
	
	@Get("/admin/access/count")
	public static void adminAccessCount() {
		renderJSON(Cache.get(PZDate.today()));
	}
	
	@Get("/admin/config")
	public static void allConfig() {
		Enumeration configs = Play.configuration.propertyNames();
		List<Map<String,String>> list = new ArrayList<>();
		while(configs.hasMoreElements()) {
			Map<String,String> map = new HashMap<String, String>();
			String key = String.valueOf(configs.nextElement());
			map.put(key, Play.configuration.getProperty(key));
			list.add(map);
		}
		render(list);
	}

}
