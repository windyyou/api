package com.fnst.cloudapi.service.openstackapi;

import java.util.List;
import java.util.Map;

import com.fnst.cloudapi.pojo.openstackapi.forgui.Subnet;

public interface SubnetService {

	public List<Subnet> getSubnetList(Map<String,String> paramMap,String tokenId);
	public Subnet getSubnet(String SubnetId, String guiTokenId);	
	
}
