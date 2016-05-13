package com.fnst.cloudapi.service.openstackapi;

import java.util.List;
import java.util.Map;

import com.fnst.cloudapi.pojo.openstackapi.forgui.Network;

public interface NetworkService {

	public List<Network> getNetworkList(Map<String,String> paramMap,String tokenId);
	public Network getNetwork(String NetworkId, String guiTokenId);	
	
}
