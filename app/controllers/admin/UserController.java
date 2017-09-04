package controllers.admin;


import annotation.hmcore.Check;
import annotation.hmcore.For;
import annotation.hmcore.Login;
import controllers.AdminActionIntercepter;
import controllers.CRUD;
import controllers.Secure;
import models.hmcore.user.User;
import play.mvc.With;

@Login
@Check("")
@For(User.class)
@With({AdminActionIntercepter.class,Secure.class})
public class UserController extends CRUD{

}
