package controllers.admin;


import annotations.Check;
import annotations.For;
import annotations.Login;
import controllers.AdminActionIntercepter;
import controllers.CRUD;
import controllers.Secure;
import models.hmcore.adminuser.AdminUser;
import play.mvc.With;

@Login
@Check("")
@For(AdminUser.class)
@With({AdminActionIntercepter.class,Secure.class})
public class AdminMangers extends CRUD{

}
