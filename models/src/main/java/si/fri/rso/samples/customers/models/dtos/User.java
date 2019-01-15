package si.fri.rso.samples.customers.models.dtos;

import java.time.Instant;

public class User {

    private Integer id;

    private String firstName;

    private String lastName;

    private String email;

    private String status;
    private String dateOfBirth;
    /*private Instant dateOfBirthInstant;*/

    private String messageGroupId;
    private String lastLoggedIn;

    /*private Instant lastLoggedInInstant;*/

    public User(){}

    public User (int id) {
        this.id = id;
    }



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }



    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMessageGroupId() {
        return messageGroupId;
    }

    public void setMessageGroupId(String messageGroupId) {
        this.messageGroupId= messageGroupId;
    }


    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }


    /*
    public Instant getDateOfBirthInstant() {
        return dateOfBirthInstant;
    }

    public void setDateOfBirthInstant(Instant dateOfBirthInstant) {
        this.dateOfBirthInstant = dateOfBirthInstant;
    }
    */


    public String getLastLoggedIn() {
        return lastLoggedIn;
    }

    public void setLastLoggedIn(String lastLoggedIn) {
        this.lastLoggedIn = lastLoggedIn;
    }

}
