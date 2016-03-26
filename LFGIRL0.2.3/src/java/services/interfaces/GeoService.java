/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services.interfaces;

import java.util.List;

/**
 *
 * @author Alayna
 */
public interface GeoService {
    public List<Object[]>findNearestGroups(float lati, float longi, float maxdistance);
}
