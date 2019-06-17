package com.arvato.cc.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.xml.crypto.Data;
import java.sql.Timestamp;

/**
 * Created by XUSO002 on 2015/9/1 0001.
 */
@Entity
@Table(name = "email")
public class Email {
    private Integer id;
    private String email;
    private String token;
    private Timestamp time;
    private Integer status;

    public Email() {

    }

    public Email(String email, String token, Timestamp time, Integer status) {
        this.email = email;
        this.token = token;
        this.time = time;
        this.status = status;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    @Column(name = "email",length = 200)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    @Column(name = "token",length = 200)
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
    @Column(name = "time", length = 19)
    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }
    @Column(name = "status", nullable = false)
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
