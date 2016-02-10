/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import hibernate.dataobjects.UsersGroups;
import hibernate.dataobjects.UsersGroupsId;
import java.io.IOException;
import java.util.*;
import org.hibernate.collection.internal.PersistentSet;

/**
 *
 * @author Protostar
 */
public class UsersGroupsSerializer extends JsonSerializer<Set<UsersGroups>> {

    @Override
    public void serialize(Set<UsersGroups> t, JsonGenerator jg, SerializerProvider sp) throws IOException, JsonProcessingException {
        HashSet<UsersGroupsId> ugId = new HashSet<>();
        
        
        t.stream().forEach((g) -> {
            ugId.add(g.getId());
        });
        jg.writeObject(ugId);
    }
    
}
