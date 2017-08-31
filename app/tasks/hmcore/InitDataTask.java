package tasks.hmcore;

import java.util.Iterator;
import java.util.List;

import models.hmcore.adminuser.AdminUser;
import models.hmcore.adminuser.Permission;
import models.hmcore.setting.SystemSetting;
import play.Play;
import play.jobs.Job;
import play.jobs.OnApplicationStart;
import play.libs.Crypto;
import play.mvc.Router;
import play.mvc.Router.Route;
/**
 * 初始化数据
 * @author zhangpeng
 *
 */
@OnApplicationStart
public class InitDataTask extends Job{
	
	public void doJob(){
		initAdmin();
		initPermissions();
		initAccessLog();
	}
	
	/**
	 * 初始化超级管理员
	 */
	public static void initAdmin(){
		AdminUser admin = AdminUser.find("username", "admin").first();
		if(admin == null){
			new AdminUser("admin", Crypto.passwordHash("admin")).save();
		}
	}
	
	/**
	 * 初始化HAdmin的权限列表
	 * 扫码Router生成
	 */
	public static void initPermissions(){
		Iterator it = Router.routes.iterator();
		while(it.hasNext()){
			Route route = (Route) it.next();
			Permission permission = Permission.find("action", route.action).first();
			if(permission == null){
				new Permission(route.action, route.action, route.path).save();
			}
		}
	}
	
	/**
	 * 初始化访问日志配置
	 */
	public static void initAccessLog() {
		List<SystemSetting> sets = SystemSetting.findAll();
		if(sets.size() == 0) {
			new SystemSetting("accesslog.log2play", "true").save();
			new SystemSetting("accesslog.logpost", "true").save();
			new SystemSetting("accesslog.path", "logs/access.log").save();
//			new SystemSetting("check.login","enabled").save();
//			new SystemSetting("login.url","/login").save();
//			new SystemSetting("check.permission","disabled").save();
		}else {
			for(SystemSetting set : sets) {
				Play.configuration.setProperty(set.settingKey, set.settingValue);
			}
		}
	}

}
