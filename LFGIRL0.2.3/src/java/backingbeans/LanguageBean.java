/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backingbeans;
import java.io.Serializable;
import java.util.Locale;
import javax.faces.bean.ApplicationScoped;

import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

@ManagedBean(name="language")
@ApplicationScoped
/**
 *
 * @author Ya
 */
public class LanguageBean implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private Locale locale = FacesContext.getCurrentInstance().getViewRoot().getLocale();
    
    public Locale getLocale(){
      return locale;
    }
    
    public String getLanguage(){
      return locale.getLanguage();
    }
    
    public void changeLanguage(String language){
      locale = new Locale(language);
      FacesContext.getCurrentInstance().getViewRoot().setLocale(new Locale(language));
    }
}
