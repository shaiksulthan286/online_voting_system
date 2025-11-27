package com.klef.sdp.dto;


public class EmailDTO {

    private String name;
    private String subject;
    private String message;
    private String email;
    private String location;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "EMailDTO [name=" + name + ", subject=" + subject + ", message=" + message + ", email=" + email
                + ", location=" + location + "]";
    }
}
