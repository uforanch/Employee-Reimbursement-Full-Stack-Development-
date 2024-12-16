package com.Revature.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.springframework.stereotype.Component;

import java.sql.Date;

@Component
@Entity
@Table(name="reimbursements")
public class Reimbursement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int reimbursementId;

    private float value;

    private String description;

    @Column(nullable = false)
    private String status = "Pending";

    @Column(nullable = false)
    private Date date_issued;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userId")//name in the team CLASS
    private User user;


    public Reimbursement() {
    }

    public Reimbursement(int reimbursementId, float value, String description, String status, Date date_issued, User user) {
        this.reimbursementId = reimbursementId;
        this.value = value;
        this.description = description;
        this.status = status;
        this.date_issued = date_issued;
        this.user = user;
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

    public User getUser() {
        return user;
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

    public void setUser(User user) {
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
