package com.fnst.cloudapi.service.openstackapi;

import java.util.List;
import java.util.Map;

import com.fnst.cloudapi.pojo.openstackapi.forgui.FloatingIP;

public interface FloatingIPService {
	
	public List<FloatingIP> getFloatingIPList(Map<String, String> paraMap, String tokenId);
	//public List<FloatingIPExt> getFloatingIPExtList(Map<String, String> paraMap, String tokenId);

}
