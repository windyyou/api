package com.fnst.cloudapi.service.openstackapi.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fnst.cloudapi.pojo.commen.TokenOs;
import com.fnst.cloudapi.pojo.openstackapi.forgui.QuotaDetails;
import com.fnst.cloudapi.pojo.openstackapi.forgui.Host;
import com.fnst.cloudapi.pojo.openstackapi.forgui.HardQuota;
import com.fnst.cloudapi.pojo.openstackapi.forgui.StorageQuota;
import com.fnst.cloudapi.pojo.openstackapi.forgui.Tenant;
import com.fnst.cloudapi.service.commen.impl.CloudConfigAndTokenHandler;
import com.fnst.cloudapi.service.openstackapi.QuotaService;
import com.fnst.cloudapi.util.ParamConstant;
import com.fnst.cloudapi.util.ResponseConstant;
import com.fnst.cloudapi.util.http.HttpClientForOsRequest;
import com.fnst.cloudapi.util.http.RequestUrlHelper;

public class QuotaServiceImpl extends CloudConfigAndTokenHandler implements QuotaService{
	private HttpClientForOsRequest httpClient=null;
    private int ERROR_HTTP_CODE = 400;

    
	public QuotaServiceImpl() {
		httpClient = new HttpClientForOsRequest();
	}
	
	@Override
	public List<HardQuota> getHardQuotas(Map<String,String> paramMap,String tokenId){
		//todo 1: 通过guitokenid 取得实际，用户信息
        //AuthService	as = new AuthServiceImpl();	
        //as.GetTokenOS(guiTokenId);
		   
		TokenOs ot = super.osToken;
	    //token should have Regioninfo
		
		String region ="RegionOne";//we should get the regioninfo by the guiTokenId
		
		String url=ot.getEndPoint(TokenOs.EP_TYPE_COMPUTE, region).getPublicURL();		
		//url=RequestUrlHelper.createFullUrl(url+"/os-hosts", null);
		
		//HashMap<String, String> headers = new HashMap<String, String>();
		//headers.put("X-Auth-Token", ot.getTokenid());
		

		String admTokenId = ""; //TODO to get the admin token id
		List<Tenant> tenants = new TenantServiceImpl().getTenantList(null, admTokenId);
		if(null == tenants || 0 == tenants.size())
			return null;
		List<HardQuota> quotas = new ArrayList<HardQuota>();
		for(Tenant tenant:tenants){
			if(false == tenant.getEnabled())
				continue;
			HardQuota quota = getTenantQuota(tenant.getId(),region,url);
			if(null != quota)
				quotas.add(quota);
		}

		return null;
	}
	
	@Override
	public List<StorageQuota> getStorageQuotas(Map<String,String> paramMap,String tokenId){
		//todo 1: 通过guitokenid 取得实际，用户信息
        //AuthService	as = new AuthServiceImpl();	
        //as.GetTokenOS(guiTokenId);
		   
		TokenOs ot = super.osToken;
	    //token should have Regioninfo
		
		String region ="RegionOne";//we should get the regioninfo by the guiTokenId
		
		String url=ot.getEndPoint(TokenOs.EP_TYPE_VOLUMEV2, region).getPublicURL();		
		url=RequestUrlHelper.createFullUrl(url+"/scheduler-stats/get_pools?detail=true", null);
		
		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("X-Auth-Token", ot.getTokenid());
		//get storage quota
		System.out.println(ot.getTokenid());
		System.out.println(url.toString());
		Map<String, String>  rs = httpClient.httpDoGet(url.toString(), headers);
						
				
		System.out.println("httpcode:"+rs.get("httpcode")); 
		System.out.println("jsonbody:"+rs.get("jsonbody")); 	
		if(Integer.parseInt(rs.get("httpcode")) > ERROR_HTTP_CODE){
			System.out.println("wo cha:request failed"); 
			return null;
		}
		
		try {
			  ObjectMapper mapper = new ObjectMapper();
			  JsonNode rootNode = mapper.readTree(rs.get("jsonbody"));
			  JsonNode poolsNode=rootNode.path(ResponseConstant.POOLS);
			  int poolsCount =poolsNode.size();
			  if(0 == poolsCount)
			    	return null;
              List<StorageQuota> list= new ArrayList<StorageQuota>();	
			  for(int index = 0; index < poolsCount; ++index){
				
				  StorageQuota storageCapacity = new StorageQuota(); 
				  JsonNode poolCapacityNode = poolsNode.get(index);

				  storageCapacity.setName(poolCapacityNode.path(ResponseConstant.NAME).textValue());
				  JsonNode capabilityNode = poolCapacityNode.path(ResponseConstant.CAPABILITIES);
				  if(null != capabilityNode){
					  storageCapacity.setTotalCapacity(capabilityNode.path(ResponseConstant.TOTAL_CAPACITY).floatValue());
					  storageCapacity.setFreeCapacity(capabilityNode.path(ResponseConstant.FREE_CAPACITY).floatValue());
					  storageCapacity.setStorageProtocol(capabilityNode.path(ResponseConstant.STORAGE_PROTOCOL).textValue());
					  storageCapacity.setVolumeBackendName(capabilityNode.path(ResponseConstant.VOLUME_BACKEND_NAME).textValue());
					  storageCapacity.setPoolName(capabilityNode.path(ResponseConstant.POOL_NAME).textValue());
				}
				list.add(storageCapacity);
			}
			    return list;
		    }catch(Exception e){
					// TODO Auto-generated catch block
					e.printStackTrace();
			}
	
		return null;
	}
	
	private HardQuota getTenantQuota(String tenantId,String region,String url){
		
	    ///​{admin_tenant_id}​/os-quota-sets/​{tenant_id}​/detail
		StringBuffer tenantUrl = new StringBuffer();
		tenantUrl.append(url);
		tenantUrl.append("/os-quota-sets/");
		tenantUrl.append(tenantId);
	//	tenantUrl.append("/detail"); //maybe we need to change it on MITAKA 
		
		TokenOs ot = super.osToken;
		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("X-Auth-Token", ot.getTokenid());
		
		//get tenant quota
		System.out.println(ot.getTokenid());
		System.out.println(tenantUrl.toString());
		Map<String, String>  rs = httpClient.httpDoGet(tenantUrl.toString(), headers);
				
		
		System.out.println("httpcode:"+rs.get("httpcode")); 
		System.out.println("jsonbody:"+rs.get("jsonbody")); 	
		if(Integer.parseInt(rs.get("httpcode")) > ERROR_HTTP_CODE){
			System.out.println("wo cha:request failed"); 
			return null;
		}
			
		try {
			ObjectMapper mapper = new ObjectMapper();
			JsonNode rootNode = mapper.readTree(rs.get("jsonbody"));
			JsonNode quotaNode=rootNode.path(ResponseConstant.QUOTA_SET);
			if(null == quotaNode)
				return null;
		    HardQuota quota = new HardQuota(); 
		    //RAM Quota
		    QuotaDetails quotaRAM = new QuotaDetails();
		    quotaRAM.setLimit(quotaNode.path(ResponseConstant.RAM).intValue());
		    quota.setTenantId(tenantId);
		    quota.addQuotaDetails(ParamConstant.RAM_LIMIT,quotaRAM);
	        
		    //vcpu Quota
		    QuotaDetails quotaCPU = new QuotaDetails();
		    quotaCPU.setLimit(quotaNode.path(ResponseConstant.CORES).intValue());
		    quota.addQuotaDetails(ParamConstant.CORES_LIMIT,quotaCPU);
            
		    //floating_ip Quota
		    QuotaDetails quotaFloatingIP = new QuotaDetails();
		    quotaFloatingIP.setLimit(quotaNode.path(ResponseConstant.FLOATING_IPS).intValue());
		    quota.addQuotaDetails(ParamConstant.FLOATING_IPS_LIMIT,quotaFloatingIP);
		    
			return quota;
		}catch(Exception e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
}
