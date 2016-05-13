package com.fnst.cloudapi.service.openstackapi;

import java.util.List;
import java.util.Map;

import com.fnst.cloudapi.pojo.openstackapi.forgui.Instance;

public interface InstanceService {

	public List<Instance> getInstanceList(Map<String,String> paraMap,String tokenId);
	
	
}
