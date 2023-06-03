package com.anet.archiveevents.objects;

import java.util.UUID;

public class Compline {

    private String complineTitle;
    private String complieName;
    private String complineMail;
    private String complineContent;
    private String complineUID;

    public Compline() {
    }

    public Compline(String complineTitle, String complieName, String complineMail, String complineContent) {
        this.complineTitle = complineTitle;
        this.complieName = complieName;
        this.complineMail = complineMail;
        this.complineContent = complineContent;
        this.complineUID = UUID.randomUUID().toString();
    }

    public String getComplineTitle() {
        return complineTitle;
    }

    public void setComplineTitle(String complineTitle) {
        this.complineTitle = complineTitle;
    }

    public String getComplieName() {
        return complieName;
    }

    public void setComplieName(String complieName) {
        this.complieName = complieName;
    }

    public String getComplineMail() {
        return complineMail;
    }

    public void setComplineMail(String complineMail) {
        this.complineMail = complineMail;
    }

    public String getComplineContent() {
        return complineContent;
    }

    public void setComplineContent(String complineContent) {
        this.complineContent = complineContent;
    }

    public String getComplineUID() {
        return complineUID;
    }

    public void setComplineUID(String complineUID) {
        this.complineUID = complineUID;
    }
}
