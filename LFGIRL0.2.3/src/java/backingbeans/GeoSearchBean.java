/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backingbeans;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.GeocodingResult;
import hibernate.dataobjects.GroupLocations;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.WebApplicationContextUtils;
import other.dataobjects.GeoSearchGroup;
import other.dataobjects.Keychain;
import serializer.Autowirer;
import services.interfaces.GeoService;

/**
 *
 * @author Alayna
 */
@ManagedBean(name = "GeoSearchBean")
@ViewScoped
public class GeoSearchBean implements Serializable{
    @Autowired
    transient GeoService geoService;
    
    
    private ArrayList<GeoSearchGroup> locations=new ArrayList<>();
    private float latitude;
    private float longitude;
    private String address;
    private String key;
    private String markersXML=null;
    
    public String getMarkersXML(){
        return markersXML;
    }
    
    public void setMarkersXML(String markersXML){
        this.markersXML=markersXML;
    }
    
    
    public String getKey(){
        return key;
    }
    
    public void setKey(String key){
        this.key=key;
    }
    
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
    
    public void setAddress(String address){
        this.address=address;
    }
    
    public String getAddress(){
        return address;
    }
    /**
     * Creates a new instance of GeoSearchBean
     */
    @PostConstruct
    public void init(){
         ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ServletContext sC = (ServletContext) ec.getContext();
        WebApplicationContextUtils.getRequiredWebApplicationContext(sC).getAutowireCapableBeanFactory().autowireBean(this);
        Keychain keychain=Keychain.getInstance();
        key=keychain.getKey("googlemapsapi");
    }
    
     private void readObject(ObjectInputStream ois) throws ClassNotFoundException, IOException {
        ois.defaultReadObject();
        Autowirer.wireObject(this);
    }
    
    public GeoSearchBean() {
    }
    
    public void search(){
        if(!address.isEmpty())
            addressToCoordinate();
        List<Object[]> tempLocations=geoService.findNearestGroups(latitude, longitude, 50);
        for(Object[] gl: tempLocations){
            GeoSearchGroup temp=new GeoSearchGroup((GroupLocations)gl[0], (float)gl[1]);
            locations.add(temp);
        }
        makeMarkers();
    }

    private void addressToCoordinate() {
        try {

            GeoApiContext context = new GeoApiContext().setApiKey(key);
            GeocodingResult[] results =  GeocodingApi.geocode(context,address).await();
            if(results!=null){
                setLatitude((float)results[0].geometry.location.lat);
                setLongitude((float)results[0].geometry.location.lng);
            }
        } catch (Exception ex) {
            Logger.getLogger(GeoSearchBean.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    /*makes a string of xml that defines map markers*/
    private void makeMarkers(){
        try(StringWriter stringWriter = new StringWriter();) {
            XMLOutputFactory outputFactory = XMLOutputFactory.newInstance();
            XMLStreamWriter streamWriter = outputFactory.createXMLStreamWriter(stringWriter);
            
            streamWriter.writeStartDocument("utf-8", "1.0");
            streamWriter.writeStartElement("markers");
            
            for(GeoSearchGroup loc:locations){
                streamWriter.writeStartElement("marker");
                streamWriter.writeAttribute("name", escapeXML(loc.getGroupLocation().getGroups().getGroupname()));
                streamWriter.writeAttribute("address", escapeXML(loc.getGroupLocation().getAddress()));
                streamWriter.writeAttribute("lat", Float.toString(loc.getGroupLocation().getLatitude()));
                streamWriter.writeAttribute("lng", Float.toString(loc.getGroupLocation().getLongitude()));
                streamWriter.writeAttribute("distance", Float.toString(loc.getDistance()));
                streamWriter.writeEndElement();
            }
            streamWriter.writeEndElement();
            streamWriter.writeEndDocument();
            
            streamWriter.flush();
            streamWriter.close();
            
            markersXML = stringWriter.getBuffer().toString();
            // string will be enclosed in ''
            markersXML = markersXML.replace("'", "\"");
            stringWriter.close();
               
        } catch (XMLStreamException | IOException ex) {
            Logger.getLogger(GeoSearchBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /*escape special characters*/
    private String escapeXML(String s){
        s = s.replace("'", "&apos;");
        s = s.replace("\"", "&quot;");
        s = s.replace("<", "&lt;");
        s = s.replace(">", "&gt;");
        s = s.replace("&", "&amp;");
        return s;
        
    }
    
}
