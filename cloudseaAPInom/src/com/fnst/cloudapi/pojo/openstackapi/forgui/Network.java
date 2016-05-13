package com.fnst.cloudapi.pojo.openstackapi.forgui;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Network {
	private String id;

	private String name;

	private String status;

	private String createdAt;
	
	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return this.id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatus() {
		return this.status;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public String getCreatedAt() {
		return this.createdAt;
	}

}

