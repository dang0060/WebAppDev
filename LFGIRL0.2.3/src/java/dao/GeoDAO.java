/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;

/**
 *
 * @author Alayna
 */
public interface GeoDAO {
    public List<Object[]> findNearestGroups(float lati, float longi, float maxDistance);
}
