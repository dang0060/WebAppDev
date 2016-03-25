/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package other.dataobjects;

import hibernate.dataobjects.Groups;

/**
 *
 * @author Alayna
 */
public class SearchResult {
    private float distance;
    private Groups group;
    
    public SearchResult(){}
    
    public SearchResult(Groups group, float distance){
        this.group=group;
        this.distance=distance;
    }
    
    public void setDistance(float distance){
        this.distance=distance;
    }
    
    public float getDistance(){
        return distance;
    }
    
    public void setGroup(Groups group){
        this.group=group;
    }
    
    public Groups getGroup(){
        return group;
    }
}
