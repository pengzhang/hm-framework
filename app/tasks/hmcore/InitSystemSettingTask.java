package tasks.hmcore;

import java.util.List;

import models.hmcore.setting.SystemSetting;
import play.Logger;
import play.Play;
import play.jobs.Job;
import play.jobs.OnApplicationStart;

/**
 * 系统配置
 * @author zp
 *
 */
@OnApplicationStart
public class InitSystemSettingTask extends Job{

	public void doJob() {
		initSetting();
	}
	
	private void initSetting() {
		accessLog();
	}
	
	private void accessLog() {
		List<SystemSetting> sets = SystemSetting.findAll();
		if(sets.size() == 0) {
			new SystemSetting("accesslog.log2play", "true").save();
			new SystemSetting("accesslog.logpost", "true").save();
			new SystemSetting("accesslog.path", "logs/access.log").save();
		}else {
			for(SystemSetting set : sets) {
				Play.configuration.setProperty(set.settingKey, set.settingValue);
			}
		}
	}
	
}
