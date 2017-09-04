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
			//上传类型
			new SystemSetting("attachments.type", "local").save();
			new SystemSetting("image.server.domain", "http://127.0.0.1:9000").save();
			
			//七牛云存储
			new SystemSetting("qiniu.access_key", "").save();
			new SystemSetting("qiniu.secret_key", "").save();
			new SystemSetting("qiniu.bucketname", "").save();
			new SystemSetting("qiniu.domain", "").save();
			
			
			//微信配置
			new SystemSetting("wechat.wxpay_appid", "").save();
			new SystemSetting("wechat.wxpay_appsecret", "").save();
			new SystemSetting("wechat.wxpay_mchid", "").save();
			new SystemSetting("wechat.wxpay_key", "").save();
			new SystemSetting("wechat.wxpay_curl_proxy_host", "").save();
			new SystemSetting("wechat.wxpay_curl_proxy_port", "").save();
			new SystemSetting("wechat.wxpay_report_levenl", "").save();
			new SystemSetting("wechat.wxpay_sslcert_path", "").save();
			new SystemSetting("wechat.wxpay_sslkey_path", "").save();
			new SystemSetting("wechat.wxpay_sslrootca_path", "").save();
			new SystemSetting("wechat.wxpay_notify_url", "").save();
			new SystemSetting("wechat.wxpay_domain", "").save();
			

			
			
			
			
		}else {
			for(SystemSetting set : sets) {
				Play.configuration.setProperty(set.settingKey, set.settingValue);
			}
		}
	}

}
