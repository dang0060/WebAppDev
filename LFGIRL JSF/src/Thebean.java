import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.application.FacesMessage;
import org.primefaces.context.RequestContext;
import dao.UsersDAO;
import daoImpl.UsersDAOImpl;
import hibernate.dataobjects.Users;
import javax.faces.bean.SessionScoped;
import javax.servlet.http.HttpSession;
 
@ManagedBean
@SessionScoped
public class Thebean {
  
	 private String username;
	 private String password;
	 
	public Thebean (){	
	}  
 
  public String getUsername() {
        return username;
  }
 
  public void setUsername(String username) {
        this.username = username;
  }
 
  public String getPassword() {
        return password;
  }
 
  public void setPassword(String password) {
        this.password = password;
  }
   
    public void login(ActionEvent event) {
        RequestContext context = RequestContext.getCurrentInstance();
        FacesMessage message;
        boolean loggedIn;
         
        if(username==null || password==null){
            loggedIn=false;
            message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Login Error", "Invalid credentials");
        }
        else{
            UsersDAO usersDao = new UsersDAOImpl();
            Users loginUser=usersDao.login(username, password);

            if(loginUser!=null){
                HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
                session.setAttribute("name", loginUser.getUsername());
                session.setAttribute("userid", loginUser.getUserId());
                loggedIn = true;
                message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Welcome", username);
            }
            else {
                loggedIn = false;
                message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Login Error", "Invalid credentials");
            }
        } 
        FacesContext.getCurrentInstance().addMessage(null, message);
        context.addCallbackParam("logedIn", loggedIn);
    }   
}