/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backingbeans;

import hibernate.dataobjects.Groups;
import hibernate.dataobjects.Tags;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.servlet.ServletContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.WebApplicationContextUtils;
import other.dataobjects.SearchResult;
import services.interfaces.GroupsService;

/**
 *
 * @author Alayna
 */
@ManagedBean(name = "TagBean", eager = true)
@ViewScoped
public class TagBean implements Serializable{
    @Autowired
    transient GroupsService groupsService;
    
    private int tid;
    private Tags tag;
    private ArrayList<SearchResult> groupsList;
    /**
     * Creates a new instance of TagBean
     */
    @PostConstruct
    private void init() {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ServletContext sC = (ServletContext) ec.getContext();
        WebApplicationContextUtils.getRequiredWebApplicationContext(sC).getAutowireCapableBeanFactory().autowireBean(this);
        setTID();
    
    }
    
    public TagBean() {
    }

    private void setTID() {
        String tempId=FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("tid");
        tid=Integer.parseInt(tempId);
        tag=groupsService.findTagByID(tid);
        List<Groups>tempList=new ArrayList<>(groupsService.findGroupsByTagID(tid));
        groupsList=new ArrayList();
        for(Groups g:tempList){
            /*get group with full taglist*/
            g=groupsService.findGroupById(g.getGroupId());
            groupsList.add(new SearchResult(g, -1.0f));
        }
    }
    
    public Tags getTag(){
        return tag;
    }
    
    public void setTag(Tags tag){
        this.tag=tag;
    }
    
    public ArrayList<SearchResult> getGroupsList(){
        return groupsList;
    }
    
    public void setGroupsList(ArrayList<SearchResult> groupsList){
        this.groupsList=groupsList;
    }
}

