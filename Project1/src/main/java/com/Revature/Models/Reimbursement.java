package com.Revature.Models;

import jakarta.persistence.*;
import org.springframework.stereotype.Component;

@Component
@Entity
@Table(name="reimbursements")
public class Reimbursement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int reimbursementId;

    private float value;

    private String description;


    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="userId")//name in the team CLASS
    private User user;


    public Reimbursement() {
    }

    public Reimbursement(int reimbursementId, float value, String description, User user) {
        this.reimbursementId = reimbursementId;
        this.value = value;
        this.description = description;
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

    @Override
    public String toString() {
        return "Reimbursement{" +
                "reimbursementId=" + reimbursementId +
                ", value=" + value +
                ", description='" + description + '\'' +
                ", user=" + user +
                '}';
    }
}
