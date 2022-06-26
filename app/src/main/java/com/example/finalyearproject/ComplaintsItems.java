package com.example.finalyearproject;

public class ComplaintsItems {

    String poster, timeStamp, complaintPost, complaintAccom;

    public ComplaintsItems() {
    }

    public ComplaintsItems(String poster, String timeStamp, String complaintPost, String complaintAccom) {
        this.poster = poster;
        this.timeStamp = timeStamp;
        this.complaintPost = complaintPost;
        this.complaintAccom = complaintAccom;
    }

    public String getComplaintAccom() {
        return complaintAccom;
    }

    public void setComplaintAccom(String complaintAccom) {
        this.complaintAccom = complaintAccom;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getComplaintPost() {
        return complaintPost;
    }

    public void setComplaintPost(String complaintPost) {
        this.complaintPost = complaintPost;
    }
}
