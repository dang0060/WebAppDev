package hibernate.dataobjects;
// Generated 25-Mar-2016 11:25:17 PM by Hibernate Tools 4.3.1


import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Conversation generated by hbm2java
 */
@Entity
@Table(name="conversation"
    ,catalog="mydb"
)
public class Conversation  implements java.io.Serializable {


     private Integer conversationId;
     private Users usersByUserStartId;
     private Users usersByUserRecieveId;
     private String status;
     private Set<ConversationMessage> conversationMessages = new HashSet<ConversationMessage>(0);

    public Conversation() {
    }

	
    public Conversation(Users usersByUserStartId, Users usersByUserRecieveId) {
        this.usersByUserStartId = usersByUserStartId;
        this.usersByUserRecieveId = usersByUserRecieveId;
    }
    public Conversation(Users usersByUserStartId, Users usersByUserRecieveId, String status, Set<ConversationMessage> conversationMessages) {
       this.usersByUserStartId = usersByUserStartId;
       this.usersByUserRecieveId = usersByUserRecieveId;
       this.status = status;
       this.conversationMessages = conversationMessages;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)

    
    @Column(name="conversation_id", unique=true, nullable=false)
    public Integer getConversationId() {
        return this.conversationId;
    }
    
    public void setConversationId(Integer conversationId) {
        this.conversationId = conversationId;
    }

@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="user_start_id", nullable=false)
    public Users getUsersByUserStartId() {
        return this.usersByUserStartId;
    }
    
    public void setUsersByUserStartId(Users usersByUserStartId) {
        this.usersByUserStartId = usersByUserStartId;
    }

@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="user_recieve_id", nullable=false)
    public Users getUsersByUserRecieveId() {
        return this.usersByUserRecieveId;
    }
    
    public void setUsersByUserRecieveId(Users usersByUserRecieveId) {
        this.usersByUserRecieveId = usersByUserRecieveId;
    }

    
    @Column(name="status", length=45)
    public String getStatus() {
        return this.status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }

@OneToMany(fetch=FetchType.LAZY, mappedBy="conversation")
    public Set<ConversationMessage> getConversationMessages() {
        return this.conversationMessages;
    }
    
    public void setConversationMessages(Set<ConversationMessage> conversationMessages) {
        this.conversationMessages = conversationMessages;
    }




}


