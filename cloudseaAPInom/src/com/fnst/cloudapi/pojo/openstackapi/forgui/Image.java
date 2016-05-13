package com.fnst.cloudapi.pojo.openstackapi.forgui;

import java.util.Date;
import java.util.List;

public class Image {
	private String id;
	private String name;
	private String status;
	private List<String> tags;
	private Date create_at;
	private Date update_at;
	private String visibility;
	private Boolean isProtected;
	private String file;
	private String owner;
	private String disk_format;
	private int min_disk;
	private int size;
	private int min_ram;
	
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
	
	public void setStatus(String status){
		this.status = status;
	}
	
	public String getStatus(){
		return this.status;
	}
	
	public void setTags(List<String> tags){
		this.tags = tags;
	}

	public List<String> getTags(){
		return tags;
	}
	
	public void setCreateAt(Date create_at){
		this.create_at = create_at;
	}
	
	public Date getCreateAt(){
		return this.create_at;
	}
	
	public void setUpdateAt(Date update_at){
		this.update_at = update_at;
	}
	
	public Date getUpdateAt(){
		return this.update_at;
	}
	
	public void setVisibility(String visibility){
		this.visibility = visibility;
	}
	
	public String getVisibility(){
		return visibility;
	}
	
	public void setFile(String file){
		this.file = file;
	}
	
	public String getFile(){
		return this.file;
	}
	
	public void setOwner(String owner){
		this.owner = owner;
	}
	
	public String getOwner(){
		return this.owner;
	}
	
	public void setDiskFormat(String disk_format){
		this.disk_format = disk_format;
	}
	
	public String getDiskFormat(){
		return this.disk_format;
	}
	
	public void setProtectedValue(Boolean isProtectd){
		this.isProtected = isProtectd;
	}
	
	public Boolean getProtectedValue(){
		return this.isProtected;
	}
	
	public void setSize(int size){
		this.size = size;
	}
	
	public int getSize(){
		return this.size;
	}
	
	public void setMinDisk(int min_disk){
		this.min_disk = min_disk;
	}
	
	public int getMinDisk(){
		return this.min_disk;
	}
	
	public void setMinRam(int min_ram){
		this.min_ram = min_ram;
	}
	
	public int getMinRam(){
		return this.min_ram;
	}
}