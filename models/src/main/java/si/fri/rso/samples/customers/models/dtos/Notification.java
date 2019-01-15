package si.fri.rso.samples.customers.models.dtos;

public class Notification {

    private Integer id;

    private Integer userid;

    private Integer seen;

    private String notificationtext;

    private Integer rideid;



    public Integer getRideid() { return rideid; }
    public void setRideid(Integer rideid){ this.rideid = rideid; }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserid() {
        return userid;
    }

    public Integer getSeen(){
        return seen;
    }
    public void setSeen(Integer seen){
        this.seen = seen;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getNotificationtext () { return notificationtext;}
    public void setNotificationtext (String notificationtext) { this.notificationtext = notificationtext;}
}
