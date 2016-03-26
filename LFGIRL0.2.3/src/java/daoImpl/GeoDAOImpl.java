/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daoImpl;

import dao.GeoDAO;
import hibernate.dataobjects.GroupLocations;
import java.util.List;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Alayna
 */
@Repository
public class GeoDAOImpl implements GeoDAO{
    private SessionFactory sFac;
    
    public void setSessionFactory(SessionFactory sessionFactory) {
        sFac = sessionFactory;
    }
    
    
    @Override
    public List<Object[]> findNearestGroups(float lati, float longi, float maxDistance) {
        Session session = sFac.openSession();
        SQLQuery getDistances=session.createSQLQuery("Select group_locations.*, groups.*, "
                + "(6371 * acos( cos( radians(:lati )) * cos( radians( group_locations.latitude ) ) * cos( radians( group_locations.longitude ) - radians(:longi) ) + sin( radians(:lati) ) * sin( radians( group_locations.latitude ) ) ) ) as distance "
                + "from group_locations join groups on (group_locations.group_id_fk=groups.group_id) having distance < :maxDistance order by distance;");
        getDistances.setParameter("lati", lati);
        getDistances.setParameter("longi", longi);
        getDistances.setParameter("maxDistance", maxDistance);
        getDistances.addEntity("group_locations",GroupLocations.class);
        getDistances.addJoin("groups", "group_locations.groups");
        getDistances.addScalar("distance", StandardBasicTypes.FLOAT);
        List closestGroups=getDistances.list();
        return closestGroups;
    }
    
}
