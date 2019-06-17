package com.arvato.cc.model;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

/**
 * Area entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "area")
public class Area implements java.io.Serializable {

    // Fields

    private Integer id;
    private String name;
    private String oname;
    private String code;
    private Integer parentId;
    private Integer needMark;

    // Constructors

    /** default constructor */
    public Area() {
    }

    /** full constructor */
    public Area(String name, String oname, String code, Integer parentId,
                Integer needMark) {
        this.name = name;
        this.oname = oname;
        this.code = code;
        this.parentId = parentId;
        this.needMark = needMark;
    }

    // Property accessors
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", unique = true, nullable = false)
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "name", length = 200)
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "oname", length = 200)
    public String getOname() {
        return this.oname;
    }

    public void setOname(String oname) {
        this.oname = oname;
    }

    @Column(name = "code", length = 100)
    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Column(name = "parent_id")
    public Integer getParentId() {
        return this.parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    @Column(name = "need_mark")
    public Integer getNeedMark() {
        return this.needMark;
    }

    public void setNeedMark(Integer needMark) {
        this.needMark = needMark;
    }

}