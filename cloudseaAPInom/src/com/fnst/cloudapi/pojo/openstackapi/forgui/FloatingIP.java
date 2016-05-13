package com.fnst.cloudapi.pojo.openstackapi.forgui;

public class FloatingIP {
	private String id;
	private String tenantId;
	private String status;
	private String routerId;
	private String floatingNetId;
	private String fixedIpAddress;
	private String floatingIpAddress;
	private String portId;
	
	public void setId(String id){
		this.id = id;
	}
	
	public String getId(){
		return this.id;
	}
    
	public void setTenantId(String tenantId){
		this.tenantId = tenantId;
	}
	
	public String getTenantId(){
		return this.tenantId;
	}
	
	public void setStatus(String status){
		this.status = status;
	}
	
	public String getStatus(){
		return this.status;
	}
	
	public void setRouterId(String routerId){
		this.routerId = routerId;
	}
	
	public String getRouterId(){
		return this.routerId;
	}
	
	public void setFloatingNetId(String floatingNetId){
		this.floatingNetId = floatingNetId;
	}
	
	public String getFloatingNetId(){
		return this.floatingNetId;
	}
	
	public void setFixedIpAddress(String fixedIpAddress){
		this.fixedIpAddress = fixedIpAddress;
	}
	
	public String getFixedIpAddress(){
		return this.fixedIpAddress;
	}
	
	public void setFloatingIpAddress(String floatingIpAddress){
		this.floatingIpAddress = floatingIpAddress;
	}
	
	public String getFloatingIpAddress(){
		return this.floatingIpAddress;
	}
	
	public void setPortId(String portId){
		this.portId = portId;
	}
	
	public String getPortId(){
		return this.portId;
	}
	
}
