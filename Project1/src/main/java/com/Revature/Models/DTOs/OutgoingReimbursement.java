package com.Revature.Models.DTOs;

import com.Revature.Models.Reimbursement;
import com.Revature.Models.User;

import java.sql.Date;

public class OutgoingReimbursement {
    private int reimbursementId;

    private float value;

    private String description;

    private String status = "Pending";

    private Date date_issued;

    private OutgoingUser user;


    public OutgoingReimbursement() {
    }

    public OutgoingReimbursement(int reimbursementId, float value, String description, String status, Date date_issued, OutgoingUser user) {
        this.reimbursementId = reimbursementId;
        this.value = value;
        this.description = description;
        this.status = status;
        this.date_issued = date_issued;
        this.user = user;
    }

    public OutgoingReimbursement(Reimbursement reimbursement){
        this.reimbursementId = reimbursement.getReimbursementId();
        this.value = reimbursement.getValue();
        this.description = reimbursement.getDescription();
        this.status = reimbursement.getStatus();
        this.date_issued = reimbursement.getDate_issued();
        this.user = new OutgoingUser(reimbursement.getUser());
    }

    public int getReimbursementId() {
        return reimbursementId;
    }


    public void setReimbursementId(int reimbursementId) {
        this.reimbursementId = reimbursementId;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getDate_issued() {
        return date_issued;
    }

    public void setDate_issued(Date date_issued) {
        this.date_issued = date_issued;
    }

    public OutgoingUser getUser() {
        return user;
    }

    public void setUser(OutgoingUser user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Reimbursement{" +
                "reimbursementId=" + reimbursementId +
                ", value=" + value +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                ", date_issued=" + date_issued +
                ", user=" + user +
                '}';
    }
}
