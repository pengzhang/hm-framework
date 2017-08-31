package controllers.admin;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import annotation.hmcore.Check;
import annotation.hmcore.For;
import annotation.hmcore.Login;
import controllers.AdminActionIntercepter;
import controllers.CRUD;
import controllers.Secure;
import controllers.CRUD.ObjectType;
import models.hmcore.setting.SystemSetting;
import play.Logger;
import play.Play;
import play.data.binding.Binder;
import play.data.validation.Password;
import play.db.Model;
import play.exceptions.TemplateNotFoundException;
import play.libs.Crypto;
import play.mvc.With;

@Login
@Check("")
@For(SystemSetting.class)
@With({AdminActionIntercepter.class,Secure.class})
public class AdminSystemSetting extends CRUD {
	
}
