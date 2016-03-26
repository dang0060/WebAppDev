/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package other.dataobjects;

import hibernate.dataobjects.Groups;
import hibernate.dataobjects.Tags;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

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
    
    public void setTagList(List<Tags> tags){
        group.setTagses(new HashSet<>(tags));
    }
    
    public ArrayList<Tags> getTagsList(){
        return new ArrayList<>(group.getTagses());
    }
}
