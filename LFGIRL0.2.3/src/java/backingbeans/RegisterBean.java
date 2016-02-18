/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backingbeans;

import hibernate.dataobjects.Users;
import java.io.IOException;
import java.io.ObjectInputStream;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.WebApplicationContextUtils;
import serializer.Autowirer;
import services.interfaces.UsersService;

/**
 *
 * @author Protostar
 */
@ManagedBean(name = "RegisterBean")
@RequestScoped
public class RegisterBean {
    
    @Autowired
    private UsersService usersService;
    
    @PostConstruct
    private void init() {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ServletContext sC = (ServletContext) ec.getContext();
        WebApplicationContextUtils.getRequiredWebApplicationContext(sC).getAutowireCapableBeanFactory().autowireBean(this);
    }
    
    private void readObject(ObjectInputStream ois) throws ClassNotFoundException, IOException {
        ois.defaultReadObject();
        Autowirer.wireObject(this);
    }
    
    public void registerUser(String userName, String password, String email) {
        FacesContext fc = FacesContext.getCurrentInstance();
        RequestContext context = RequestContext.getCurrentInstance();
        FacesMessage message;
        boolean success;
        Users newUser = new Users(userName, password, email, null, null, null);
        String result = usersService.registerNewUser(newUser);
        if (result != null) {
            message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Registration Error", result);
            success = false;
        }
        else {
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Registration successful, email dispatched to: ", email);
            success = true;
        }
        fc.addMessage(null, message);
        context.addCallbackParam("regSuccess", success);
            
    }
    
}
