package com.fnst.cloudapi.pojo.commen;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class TokenOs {
	
	public static final String EP_TYPE_COMPUTE="compute";
	public static final String EP_TYPE_NETWORK="network";
	public static final String EP_TYPE_VOLUMEV2="volumev2";
	public static final String EP_TYPE_IMAGE="image";
	public static final String EP_TYPE_METERING="metering";
	public static final String EP_TYPE_VOLUME="volume";
	public static final String EP_TYPE_LBAAS="loadbalancing";
	public static final String EP_TYPE_ORCHESTRATION="orchestration";
	public static final String EP_TYPE_CLOUDFORMATION="cloudformation";
	public static final String EP_TYPE_IDENTIFY="identity";
	public static final String EP_TYPE_SHARE="share";
	public static final String EP_TYPE_SHAREV2="sharev2";
	public static final String EP_TYPE_RATING="rating";
	
	
	private String tokenid;
	private Date   creattime;
	private Date   expirestime;	
	private String tenantid;
	private String domainid;
	private String currentRegion;
	
	public String getDomainid() {
		return domainid;
	}

	public void setDomainid(String domainid) {
		this.domainid = domainid;
	}

	private Map<String,TokenOsEndPoint> endpointsMap;
	private List<TokenOsEndPoints> endpointlist;
					
	
	public String getTokenid() {
		return tokenid;
	}

	public void setTokenid(String tokenid) {
		this.tokenid = tokenid;
	}

	public Date getCreattime() {
		return creattime;
	}

	public void setCreattime(Date creattime) {
		this.creattime = creattime;
	}

	public Date getExpirestime() {
		return expirestime;
	}

	public void setExpirestime(Date expirestime) {
		this.expirestime = expirestime;
	}

	public String getTenantid() {
		return tenantid;
	}

	public void setTenantid(String tenantid) {
		this.tenantid = tenantid;
	}

	public String getCurrentRegion() {
		return currentRegion;
	}

	public void setCurrentRegion(String currentRegion) {
		this.currentRegion = currentRegion;
	}

	public List<TokenOsEndPoints> getEndpointlist() {
		return endpointlist;
	}

	public void setEndpointlist(List<TokenOsEndPoints> endpointlist) {
		this.endpointlist = endpointlist;
		
		if (this.endpointsMap==null){
		 this.endpointsMap = new HashMap<String,TokenOsEndPoint>();	
		}
		
		for (TokenOsEndPoints one : endpointlist) {	
			String serviceType=one.getServiceType();
			List<TokenOsEndPoint> oplist=one.getEndpointList();
			
			for(TokenOsEndPoint onep:oplist){
			System.out.println("tokenos:  "+serviceType+"_"+onep.getRegion()+"  :admin:"+onep.getAdminURL());	
			 this.endpointsMap.put(serviceType+"_"+onep.getRegion(), onep);
			 
			}
			
			
		}
	}
	
	public TokenOsEndPoint getEndPoint(String serviceType,String region){
		
		return this.endpointsMap.get(serviceType+"_"+region);
	}

	public TokenOsEndPoint getEndPoint(String serviceType){
		
		return getEndPoint(serviceType,this.currentRegion);
	}	
	
	
	public Map<String, TokenOsEndPoint> getEndpointsMap() {
		return endpointsMap;
	}

}
