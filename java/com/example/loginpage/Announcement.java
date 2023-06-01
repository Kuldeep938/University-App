package com.example.loginpage;

public class Announcement {
    private String facultyName;
    private String announcementDescription;

    public Announcement() {
    }

    public Announcement(String facultyName, String announcementDescription) {
        this.facultyName = facultyName;
        this.announcementDescription = announcementDescription;
    }

    public String getFacultyName() {
        return facultyName;
    }

    public void setFacultyName(String facultyName) {
        this.facultyName = facultyName;
    }

    public String getAnnouncementDescription() {
        return announcementDescription;
    }

    public void setAnnouncementDescription(String announcementDescription) {
        this.announcementDescription = announcementDescription;
    }
}