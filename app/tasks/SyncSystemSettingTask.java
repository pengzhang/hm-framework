package tasks;

import java.util.List;

import models.hmcore.setting.SystemSetting;
import play.Play;
import play.jobs.Every;
import play.jobs.Job;
import play.jobs.OnApplicationStart;

/**
 * 5分钟同步系统配置
 * @author zp
 *
 */
@Every("5s")
public class SyncSystemSettingTask extends Job{

	public void doJob() {
		List<SystemSetting> sets = SystemSetting.all().fetch();
		for(SystemSetting set : sets) {
			Play.configuration.setProperty(set.settingKey, set.settingValue);
		}
	}
	
}
