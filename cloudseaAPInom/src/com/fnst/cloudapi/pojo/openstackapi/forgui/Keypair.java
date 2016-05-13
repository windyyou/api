package com.fnst.cloudapi.pojo.openstackapi.forgui;

public class Keypair {

	private String fingerprint;
	private String name;
	private String public_key;
	private String user_id;
	private String private_key;
	
	public Keypair(){
		this.fingerprint = "";
		this.name = "";
		this.public_key = "";
//		this.user_id = "";
//		this.private_key = "";
	}
	
	public Keypair(String name){
		this.name = name;
	}
	
	public Keypair(String name,String public_key){
		this.name = name;
		this.public_key = public_key;
	}
	
	public Keypair(String name,String public_key,String fingerprint){
		this.name = name;
		this.public_key = public_key;
		this.fingerprint = fingerprint;
	}
	
	public void setFingerprint(String fingerprint){
		this.fingerprint = fingerprint;
	}
	
	public String getFingerprint(){
		return this.fingerprint;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public String getName(){
		return this.name;
	}
	
	public void setPublickey(String public_key){
		this.public_key = public_key;
	}
	
	public String getPublickey(){
		return this.public_key;
	}
	
	public void setUserid(String user_id){
		this.user_id = user_id;
	}
	
	public String getUserid(){
		return this.user_id;
	}
	
	public void setPrivatekey(String private_key){
		this.private_key = private_key;
	}
	
	public String getPrivatekey(){
		return this.private_key;
	}
	
	
}
