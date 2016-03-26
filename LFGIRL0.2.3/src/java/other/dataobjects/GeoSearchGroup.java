/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package other.dataobjects;

import hibernate.dataobjects.GroupLocations;

/**
 *
 * @author Alayna
 */
public class GeoSearchGroup {
    private float distance;
    private GroupLocations groupLocation;
    
    public GeoSearchGroup(){}
    
    public GeoSearchGroup(GroupLocations groupLocation, float distance){
        this.groupLocation=groupLocation;
        this.distance=distance;
    }
    
    public void setDistance(float distance){
        this.distance=distance;
    }
    
    public float getDistance(){
        return distance;
    }
    
    public void setGroupLocation(GroupLocations groupLocation){
        this.groupLocation=groupLocation;
    }
    
    public GroupLocations getGroupLocation(){
        return groupLocation;
    }
}
