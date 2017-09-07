package controllers.admin;


import annotations.Check;
import annotations.For;
import annotations.Login;
import controllers.AdminActionIntercepter;
import controllers.CRUD;
import controllers.Secure;
import models.hmcore.adminuser.Permission;
import play.mvc.With;

@Login
@Check("")
@For(Permission.class)
@With({AdminActionIntercepter.class,Secure.class})
public class AdminPermissions extends CRUD{

}
