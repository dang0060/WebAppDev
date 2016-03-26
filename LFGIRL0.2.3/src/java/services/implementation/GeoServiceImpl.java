/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services.implementation;

import dao.GeoDAO;
import hibernate.dataobjects.GroupLocations;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import services.interfaces.GeoService;

/**
 *
 * @author Alayna
 */
@Primary
@Service
public class GeoServiceImpl implements GeoService{

    @Autowired
    private GeoDAO geoDAO;
    
    public void setGeoDAO(GeoDAO geoDAO){
        this.geoDAO=geoDAO;
    }
    
    @Override
    public List<Object[]> findNearestGroups(float lati, float longi, float maxdistance) {
        return geoDAO.findNearestGroups(lati, longi, maxdistance);
    }
    
}
