package com.hzih.ra.domain;

/**
 * Orgcode entity. @author MyEclipse Persistence Tools
 */

public class Orgcode implements java.io.Serializable {

	// Fields

	private String orgcode;
	private String orgname;

	// Constructors

	/** default constructor */
	public Orgcode() {
	}

	/** full constructor */
	public Orgcode(String orgname) {
		this.orgname = orgname;
	}

	// Property accessors

	public String getOrgcode() {
		return this.orgcode;
	}

	public void setOrgcode(String orgcode) {
		this.orgcode = orgcode;
	}

	public String getOrgname() {
		return this.orgname;
	}

	public void setOrgname(String orgname) {
		this.orgname = orgname;
	}

}