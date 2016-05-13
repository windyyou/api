package com.fnst.cloudapi.pojo.openstackapi.forgui;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Instance {
	private String id;

	private String name;

	private String status;
	
	private String type;

	private Image image;

	private List<String> ips;

	private List<String> floatingIps;

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

	public void setImage(Image image) {
		this.image = image;
	}

	public Image getImage() {
		return this.image;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getType() {
		return this.type;
	}

	public List<String> getFloatingIps() {
		return floatingIps;
	}

	public void setFloatingIps(List<String> floatingIp) {
		this.floatingIps = floatingIp;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public String getCreatedAt() {
		return this.createdAt;
	}

	public List<String> getIps() {
		return ips;
	}

	public void setIps(List<String> ips) {
		this.ips = ips;
	}

}

