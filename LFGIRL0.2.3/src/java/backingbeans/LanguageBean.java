package backingbeans;
import java.io.Serializable;
import java.util.Locale;
import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;

import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
/*This is the bean to set the locale, depends on users' choice with language buttons @yawei*/
@ManagedBean(name="language")
@ApplicationScoped
/**
 *
 * @author Ya
 */
public class LanguageBean implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private Locale locale;
    
    @PostConstruct
    public void init() {
        locale = FacesContext.getCurrentInstance().getViewRoot().getLocale();
    }
    
    public Locale getLocale(){
      return locale;
    }
    
    public String getLanguage(){
      return locale.getLanguage();
    }
    
    public void changeLanguage(String language){
      locale = new Locale(language);
      FacesContext.getCurrentInstance().getViewRoot().setLocale(locale);
    }
}
