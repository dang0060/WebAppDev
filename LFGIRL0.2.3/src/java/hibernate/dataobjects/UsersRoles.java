package hibernate.dataobjects;
// Generated Feb 14, 2016 6:00:22 AM by Hibernate Tools 4.3.1


import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * UsersRoles generated by hbm2java
 */
@Entity
@Table(name="users_roles"
    ,catalog="mydb"
)
public class UsersRoles  implements java.io.Serializable {


     private UsersRolesId id;
     private Users users;

    public UsersRoles() {
    }

    public UsersRoles(UsersRolesId id, Users users) {
       this.id = id;
       this.users = users;
    }
   
     @EmbeddedId

    
    @AttributeOverrides( {
        @AttributeOverride(name="userRoleId", column=@Column(name="user_role_id", nullable=false) ), 
        @AttributeOverride(name="role", column=@Column(name="role", nullable=false, length=16) ) } )
    public UsersRolesId getId() {
        return this.id;
    }
    
    public void setId(UsersRolesId id) {
        this.id = id;
    }

@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="user_role_id", nullable=false, insertable=false, updatable=false)
    public Users getUsers() {
        return this.users;
    }
    
    public void setUsers(Users users) {
        this.users = users;
    }




}


