package com.fnst.cloudapi.service.openstackapi;

import java.util.List;
import java.util.Map;

import com.fnst.cloudapi.pojo.openstackapi.forgui.Disk;

public interface DiskService {
	
	public List<Disk> getDiskList(Map<String,String> paraMap,String tokenId);
	
	public Disk getDiskById(String diskId, String tokenId);
	
}
