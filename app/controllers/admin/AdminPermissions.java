package controllers.admin;


import annotation.hmcore.Check;
import annotation.hmcore.For;
import annotation.hmcore.Login;
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
