/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backingbeans;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.GeocodingResult;
import hibernate.dataobjects.Groups;
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
import other.dataobjects.SearchResult;
import other.dataobjects.Keychain;
import serializer.Autowirer;
import services.interfaces.GroupsService;

/**
 *
 * @author Alayna
 */
@ManagedBean(name = "GeoSearchBean")
@ViewScoped
public class GeoSearchBean implements Serializable{
    @Autowired
    transient GroupsService groupService;
    
    
    private ArrayList<SearchResult> locations=new ArrayList<>();
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
    
    public ArrayList<SearchResult> getLocations(){
        return locations;
    }
    
    public void setLocations(ArrayList<SearchResult> locations){
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
        locations.clear();
        if(!address.isEmpty())
            addressToCoordinate(address);
        List<Object[]> tempLocations=groupService.findNearestGroups(latitude, longitude, 50);
        for(Object[] gl: tempLocations){
            SearchResult temp=new SearchResult((Groups)gl[0], (float)gl[1]);
            locations.add(temp);
        }
        makeMarkers();
    }

    public Object[] addressToCoordinate(String address) {
        Object[] result = new Object[3];
        result[0]="none";
        try {
            GeoApiContext context = new GeoApiContext().setApiKey(key);
            GeocodingResult[] results =  GeocodingApi.geocode(context,address).await();
            if(results!=null&&results.length>0){
                setLatitude((float)results[0].geometry.location.lat);
                setLongitude((float)results[0].geometry.location.lng);
                result[1]=latitude;
                result[2]=longitude;
                result[0]=results[0].formattedAddress;
            }
        } catch (Exception ex) {
            Logger.getLogger(GeoSearchBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    /*makes a string of xml that defines map markers*/
    private void makeMarkers(){
        try(StringWriter stringWriter = new StringWriter();) {
            XMLOutputFactory outputFactory = XMLOutputFactory.newInstance();
            XMLStreamWriter streamWriter = outputFactory.createXMLStreamWriter(stringWriter);
            
            streamWriter.writeStartDocument("utf-8", "1.0");
            streamWriter.writeStartElement("markers");
            
            /*create marker for search location*/
            
            streamWriter.writeStartElement("marker");
            streamWriter.writeAttribute("name", "");
            streamWriter.writeAttribute("address", address);
            streamWriter.writeAttribute("lat", Float.toString(latitude));
            streamWriter.writeAttribute("lng", Float.toString(longitude));
            streamWriter.writeAttribute("distance", "0.0");
            streamWriter.writeEndElement();
            
            /*create markers for search results*/
            for(SearchResult loc:locations){
                streamWriter.writeStartElement("marker");
                streamWriter.writeAttribute("name", escapeXML(loc.getGroup().getGroupname()));
                streamWriter.writeAttribute("address", escapeXML(loc.getGroup().getAddress()));
                streamWriter.writeAttribute("lat", Float.toString(loc.getGroup().getLatitude()));
                streamWriter.writeAttribute("lng", Float.toString(loc.getGroup().getLongitude()));
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
