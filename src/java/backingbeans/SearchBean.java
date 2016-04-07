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
import hibernate.dataobjects.Users;
import hibernate.dataobjects.UsersGroups;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.TreeMap;
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
import serializer.Autowirer;
import services.interfaces.GroupsService;
import services.interfaces.UsersService;
import other.dataobjects.SearchResult;

/**
 *
 * @author Protostar
 */
@ManagedBean(name="SearchBean" ,eager=true)
@ViewScoped
public class SearchBean implements Serializable {
    
    @Autowired
    transient GroupsService groupsService;
    
    @Autowired
    transient UsersService usersService;
    
    private ArrayList<SearchResult> locations=new ArrayList<>();
    //private List<Groups> groups = new ArrayList<>();
    private List<Users> users = new ArrayList<>();
    private LoginBean user = (LoginBean)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("LoginBean"); //check login bean for user session info
    private boolean loggedIn = false; //user login status
    private FacesContext context = FacesContext.getCurrentInstance(); //for I18n of yes and no strings
    private ResourceBundle msg = context.getApplication().evaluateExpressionGet(context,"#{msg}", ResourceBundle.class); //for I18n of yes and no strings
    private boolean isGeoSearch=false;
    private boolean isTagSearch=false;
    private float latitude=100;
    private float longitude=100;
    private String address="";
    private String key;
    private String markersXML=null;
    private String emptyMessage="No records found.";
    private int searchType= 3;
    private TreeMap<String,Integer> searchTypes;
    
    
    @PostConstruct
    public void init() {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ServletContext sC = (ServletContext) ec.getContext();
        WebApplicationContextUtils.getRequiredWebApplicationContext(sC).getAutowireCapableBeanFactory().autowireBean(this);
        key=groupsService.getSecretKey("googlemapsapi");
        
        searchTypes=new TreeMap<>();
        searchTypes.put("Users", 0);
        searchTypes.put("Groups by Name", 1);
        searchTypes.put("Groups by Tag", 2);
        searchTypes.put("Groups by Description", 3);
        
    }
    
    private void readObject(ObjectInputStream ois) throws ClassNotFoundException, IOException {
        ois.defaultReadObject();
        Autowirer.wireObject(this);
    }

    public int getSearchType(){
        return searchType;
    }
    
    public void setSearchType(int searchType){
        this.searchType=searchType;
    }
    
    public TreeMap<String,Integer> getSearchTypes(){
        return searchTypes;
    }
    
    public void setSearchTypes(TreeMap<String,Integer> searchTypes){
        this.searchTypes=searchTypes;
    }
    /**
     * @return the users
     */
    public List<Users> getUsers() {
        return users;
    }

    /**
     * @param users the users to set
     */
    public void setUsers(List<Users> users) {
        this.users = users;
    }
    
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
   
    public void setIsGeoSearch(Boolean isGeoSearch){
        this.isGeoSearch=isGeoSearch;
    }
    
    public boolean getIsGeoSearch(){
        return isGeoSearch;
    }
    
    public String getEmptyMessage(){
        return emptyMessage;
    }
    
    public void setEmptyMessage(String emptyMessage){
        this.emptyMessage=emptyMessage;
    }
    

    
    
    public void userNameSearch(String username) {
        setUsers(usersService.getUsersByName(username));
        //FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Searching...", "Search in progress"));
    }
    
    public void search(String searchTerm, float maxDistance, String address){
        locations.clear();
        emptyMessage="No records found.";
        
        if(searchType==0)
            userNameSearch(searchTerm);
        else{
            if(address==null||address.isEmpty()){
                searchWithoutLocation(searchTerm);
            }
            else
                searchWithLocation(searchTerm, maxDistance, address);
           
        }
        //clears markers if not needed
        makeMarkers();
    }
    
    
    public void searchForGroup(String searchTerm, float maxDistance, String address) {
        emptyMessage="No records found.";
        if(address==null||address.isEmpty()){
            searchWithoutLocation(searchTerm);
        }
        else
            searchWithLocation(searchTerm, maxDistance, address);

            
    }
    
    public void searchForGroupByTag(String input, float distance, String address){
        emptyMessage="No records found.";
        isTagSearch=true;
        
        if(address==null||address.isEmpty()){
            searchWithoutLocation(input);
        }
        else
            searchWithLocation(input, distance, address);
        
    }
    
    
    
     //get the name of the group leader @yawei 
     public String searchGroupLeader(int gid) {
        return groupsService.findGroupLeader(gid);
    }
    
    //indicates if the current user is the leader for a group @yawei
    public String returnLeaderStatus(int gid){
      //put yes and no under I18n   
      if(user.getUserName().equals(groupsService.findGroupLeader(gid))){
        return msg.getString("yesButton");
      }
       return msg.getString("noButton");    
    }
    
    //display the column if user is logged in @yawei
    public boolean displayJoinedColumn(){
      if(user!=null && user.getUserName()!= null){ 
          loggedIn = true;
          return true;      
    }   
      return false;
    }
    
    //show if user has joined a group @yawei
    public String JoinedGroup(int gid){    
      Groups group=groupsService.findGroupById(gid);
      Boolean isLeader = false;
      Boolean isMember = false; 
      //check to see if a user is a member or a leader in the group
        if(user!=null){
            Set<UsersGroups> members = group.getUsersGroupses();
            for(UsersGroups member:members){
                if(member.getUsers().getUserId()==user.getUserId()){
                   // isLeader=member.getIsLeader();
                    isLeader = member.getIsLeader();
                    isMember = true; 
                }
            }
        }
        if(isLeader==null)
         isLeader = false;
        
      //default is no if not logged in, even is not showing 
      if(!loggedIn){
        return msg.getString("noButton");  
        } 
      //in the group if is member or leader
      if(isMember || isLeader){
        return  msg.getString("yesButton");    
      }
      //if not a member or leader, than has not joined
      return msg.getString("noButton");                
    }

    

    private void searchWithoutLocation(String searchTerm) {
        
        setAddress("");
        setLatitude(100);
        setLongitude(100);
        List<Groups> results;
        switch (searchType) {
            case 1:
                results=groupsService.findGroupsByName(searchTerm);
                break;
            case 2:
                results=groupsService.findGroupsByTagName(searchTerm);
                break;
            default:
                results=groupsService.findGroupByDescription(searchTerm);
                break;
        }
        for(Groups group:results){
            locations.add(new SearchResult(group, -1.0f));
        }    
        setIsGeoSearch(false);
    }
    
    private void searchWithLocation(String searchTerm, float maxDistance, String address) {
            addressToCoordinate(address);
            if (maxDistance<=0||maxDistance>12756)
                maxDistance=10;
            List<SearchResult>results;
             
            switch (searchType) {
            case 1:
                results=groupsService.findNearestGroupsbyName(latitude, longitude, maxDistance, searchTerm);
                break;    
            case 2:
                results=groupsService.findNearestGroupsByTag(latitude, longitude, maxDistance, searchTerm);
                break;
            default:
                results=groupsService.findNearestGroupsbyDesc(latitude, longitude, maxDistance, searchTerm);
                break;
            }
            
            setLocations(new ArrayList<>(results));
            setIsGeoSearch(true);
            
    }
    
    public Object[] addressToCoordinate(String address) {
        Object[] result={"none",0.0,0.0};
        try {
            GeoApiContext geoContext = new GeoApiContext().setApiKey(key);
            GeocodingResult[] results =  GeocodingApi.geocode(geoContext,address).await();
            if(results!=null&&results.length>0){
                setLatitude((float)results[0].geometry.location.lat);
                setLongitude((float)results[0].geometry.location.lng);
                setAddress(results[0].formattedAddress);
                result[0]=this.address;
                result[1]=latitude;
                result[2]=longitude;
            }
            else{
                emptyMessage="Location not found.";
            }
        } catch (Exception ex) {
            Logger.getLogger(SearchBean.class.getName()).log(Level.SEVERE, null, ex);
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
            
            /*create marker for search location if searching by location*/
            if(isGeoSearch){
            streamWriter.writeStartElement("marker");
            streamWriter.writeAttribute("name", "");
            streamWriter.writeAttribute("address", address);
            streamWriter.writeAttribute("lat", Float.toString(latitude));
            streamWriter.writeAttribute("lng", Float.toString(longitude));
            streamWriter.writeAttribute("distance", "0.0");
            streamWriter.writeEndElement();
            }
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
            Logger.getLogger(SearchBean.class.getName()).log(Level.SEVERE, null, ex);
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
