package com.fnst.cloudapi.service.openstackapi;

import java.util.List;
import java.util.Map;

import com.fnst.cloudapi.pojo.openstackapi.forgui.Tenant;

public interface TenantService {
	public List<Tenant> getTenantList(Map<String,String> paramMap,String tokenId);
}
