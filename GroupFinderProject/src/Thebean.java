import java.io.IOException;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.application.FacesMessage;
import org.primefaces.context.RequestContext;
 
@ManagedBean
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
        FacesMessage message = null;
        boolean loggedIn = false;
         
        if(username != null && username.equals("admin") && password != null && password.equals("admin")) {
            loggedIn = true;
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Welcome", username);
        } else {
            loggedIn = false;
            message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Login Error", "Invalid credentials");
        }
         
        FacesContext.getCurrentInstance().addMessage(null, message);
        context.addCallbackParam("logedIn", loggedIn);
    }   
}