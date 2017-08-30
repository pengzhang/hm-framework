package tasks.hmcore;

import java.util.List;

import models.hmcore.accesslog.AccessLog;
import models.hmcore.setting.SystemSetting;
import play.Play;
import play.cache.Cache;
import play.jobs.Every;
import play.jobs.Job;
import play.jobs.OnApplicationStart;
import utils.hmcore.PZDate;

/**
 * 5分钟同步访问量数据库
 * @author zp
 *
 */
@Every("1mn")
public class AccessLogTask extends Job{

	public void doJob() {
		String key = PZDate.today();
		AccessLog access = AccessLog.find("accessDate", key).first();
		if(access == null) {
			new AccessLog(key).save();
		}else {
			Long total = Cache.get(key,Long.class);
			if(total != null) {
				if(access.total > total) {
					access.total += total;
				}else {
					access.total = total;
				}
				access.save();
			}
		}
	}
	
	public static void record() {
		String key = PZDate.today();
		if(Cache.get(key)==null) {
			Cache.set(key, 0);
		}
		Cache.incr(key);
	}
	
}
