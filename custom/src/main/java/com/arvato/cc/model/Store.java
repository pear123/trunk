package com.arvato.cc.model;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

/**
 * Store entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "store")
public class Store implements java.io.Serializable {

	// Fields

	private Integer id;
	private String orderStore;
	private String parentStore;

	// Constructors

	/** default constructor */
	public Store() {
	}

	/** full constructor */
	public Store(String orderStore, String parentStore) {
		this.orderStore = orderStore;
		this.parentStore = parentStore;
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

	@Column(name = "ORDER_STORE", length = 200)
	public String getOrderStore() {
		return this.orderStore;
	}

	public void setOrderStore(String orderStore) {
		this.orderStore = orderStore;
	}

	@Column(name = "PARENT_STORE", length = 200)
	public String getParentStore() {
		return this.parentStore;
	}

	public void setParentStore(String parentStore) {
		this.parentStore = parentStore;
	}

}