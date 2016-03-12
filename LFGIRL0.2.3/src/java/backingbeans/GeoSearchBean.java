/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backingbeans;

import hibernate.dataobjects.GroupLocations;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.WebApplicationContextUtils;
import other.dataobjects.GeoSearchGroup;
import serializer.Autowirer;
import services.interfaces.GeoService;

/**
 *
 * @author Alayna
 */
@ManagedBean(name = "GeoSearchBean")
@RequestScoped
public class GeoSearchBean {
    @Autowired
    transient GeoService geoService;
    
    
    private ArrayList<GeoSearchGroup> locations=new ArrayList<>();
    private float latitude;
    private float longitude;
    
    public ArrayList<GeoSearchGroup> getLocations(){
        return locations;
    }
    
    public void setLocations(ArrayList<GeoSearchGroup> locations){
        this.locations=locations;
    }
    
    public void setLatitude(float latitude){
        this.latitude=latitude;
    }
    
    public float getLatitude(){
        return latitude;
    }
    
     public void setLongitude(float longitude){
        this.longitude=longitude;
    }
    
    public float getLongitude(){
        return longitude;
    }
    /**
     * Creates a new instance of GeoSearchBean
     */
    @PostConstruct
    public void init(){
         ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ServletContext sC = (ServletContext) ec.getContext();
        WebApplicationContextUtils.getRequiredWebApplicationContext(sC).getAutowireCapableBeanFactory().autowireBean(this);
    }
    
     private void readObject(ObjectInputStream ois) throws ClassNotFoundException, IOException {
        ois.defaultReadObject();
        Autowirer.wireObject(this);
    }
    
    public GeoSearchBean() {
    }
    
    public void search(){
        List<Object[]> tempLocations=geoService.findNearestGroups(latitude, longitude, 50);
        for(Object[] gl: tempLocations){
            GeoSearchGroup temp=new GeoSearchGroup((GroupLocations)gl[0], (float)gl[1]);
            locations.add(temp);
        }
    }
}
