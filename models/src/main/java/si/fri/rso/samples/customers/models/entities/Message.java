package si.fri.rso.samples.customers.models.entities;

import org.eclipse.persistence.annotations.UuidGenerator;
import si.fri.rso.samples.customers.models.dtos.Order;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;

@Entity(name = "message")
@NamedQueries(value =
        {
                @NamedQuery(name = "Notification.getAll", query = "SELECT c FROM message c")
        })
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "userid1")
    private Integer userid1;

    @Column(name = "userid2")
    private Integer userid2;

    @Column(name = "senderid")
    private Integer senderid;

    @Column(name = "message")
    String message;



    public Integer getId() { return id; }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserid1() { return userid1; }
    public void setUserid1(Integer rideid){ this.userid1 = userid1; }

    public Integer getUserid2() { return userid2; }
    public void setUserid2(Integer userid2){ this.userid2 = userid2; }

    public Integer getSenderid() { return senderid; }
    public void setSenderid(Integer senderid){ this.senderid = senderid; }


    public String getMessage () { return message;}
    public void setMessage (String message) { this.message = message;}


}