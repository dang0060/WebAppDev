Select mydb.group_locations.*, mydb.groups.*, (6371 * acos( cos( radians(45.371123) ) * cos( radians( mydb.group_locations.latitude ) ) * cos( radians( mydb.group_locations.longitude ) - radians(-75.778597) ) + sin( radians(45.371123) ) * sin( radians( mydb.group_locations.latitude ) ) ) ) as distance from mydb.group_locations left join mydb.groups on (mydb.group_locations.group_id_fk=mydb.groups.group_id) having distance < 5 order by distance;