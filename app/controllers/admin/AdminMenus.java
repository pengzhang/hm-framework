package controllers.admin;

import annotations.Check;
import annotations.For;
import annotations.Login;
import controllers.AdminActionIntercepter;
import controllers.CRUD;
import controllers.Secure;
import models.hmcore.setting.AdminMenu;
import play.mvc.With;

@Login
@Check("")
@For(AdminMenu.class)
@With({AdminActionIntercepter.class,Secure.class})
public class AdminMenus extends CRUD {

}
