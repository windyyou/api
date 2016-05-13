package com.fnst.cloudapi.pojo.commen;

import java.util.Date;

public final class TokenGui {
	
	private String tokenid;
	private Date   creattime;
	private Date   expirestime;
	
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
	
	

}
