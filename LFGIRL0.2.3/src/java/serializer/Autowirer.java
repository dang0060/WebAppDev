/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serializer;

import java.io.IOException;
import java.io.ObjectInputStream;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 *
 * @author Protostar
 */
public final class Autowirer {
    
    public static void wireObject(Object object) {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ServletContext sC = (ServletContext) ec.getContext();
        WebApplicationContextUtils.getRequiredWebApplicationContext(sC).getAutowireCapableBeanFactory().autowireBean(object);
    }
    
    /*public void readObject(ObjectInputStream ois, Object object) throws ClassNotFoundException, IOException {
        ois.defaultReadObject();
        wireObject(object);
    }*/
}
