package com.fnst.cloudapi.service.openstackapi.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fnst.cloudapi.pojo.commen.TokenOs;
import com.fnst.cloudapi.pojo.openstackapi.forgui.Keypair;
import com.fnst.cloudapi.pojo.openstackapi.forgui.Tenant;
import com.fnst.cloudapi.service.commen.impl.CloudConfigAndTokenHandler;
import com.fnst.cloudapi.service.openstackapi.TenantService;
import com.fnst.cloudapi.util.ResponseConstant;
import com.fnst.cloudapi.util.http.HttpClientForOsRequest;
import com.fnst.cloudapi.util.http.RequestUrlHelper;

public class TenantServiceImpl extends CloudConfigAndTokenHandler implements TenantService{
	private HttpClientForOsRequest httpClient=null;
    private int ERROR_HTTP_CODE = 400;

    
	public TenantServiceImpl() {
		httpClient = new HttpClientForOsRequest();
	}
	
	@Override
	public List<Tenant> getTenantList(Map<String,String> paramMap,String tokenId){
		//todo 1: 通过guitokenid 取得实际，用户信息
        //AuthService	as = new AuthServiceImpl();	
        //as.GetTokenOS(guiTokenId);
		   
		TokenOs ot = super.osToken;
	    //token should have Regioninfo
		
		
		String region ="RegionOne";//we should get the regioninfo by the guiTokenId
		
		String url=ot.getEndPoint(TokenOs.EP_TYPE_IDENTIFY, region).getPublicURL();		
		url=RequestUrlHelper.createFullUrl(url+"/tenants", paramMap);
		
				
		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("X-Auth-Token", ot.getTokenid());
		
		
		Map<String, String>  rs =httpClient.httpDoGet(url, headers);
//		Map<String, String>  rs =client.httpDoGet(url, ot.getTokenid());
		
		System.out.println("httpcode:"+rs.get("httpcode")); 
		System.out.println("jsonbody:"+rs.get("jsonbody")); 
			
		if(Integer.parseInt(rs.get("httpcode")) > ERROR_HTTP_CODE){
			System.out.println("wo cha:request failed"); 
			return null;
		}
		
		try {
			ObjectMapper mapper = new ObjectMapper();
			JsonNode rootNode = mapper.readTree(rs.get("jsonbody"));
			JsonNode tenantsNode=rootNode.path(ResponseConstant.TENANTS);
			int tenantsCount =tenantsNode.size();
            if(0 == tenantsCount)
            	return null;
            
            List<Tenant> list= new ArrayList<Tenant>();	
			for(int index = 0; index < tenantsCount; ++index){
				Tenant tenant = new Tenant();
				JsonNode tenantNode = tenantsNode.get(index);
				
				tenant.setDescription(tenantNode.path(ResponseConstant.DESCRIPTION).textValue());
				tenant.setEnabled(tenantNode.path(ResponseConstant.ENABLED).booleanValue());
				tenant.setId(tenantNode.path(ResponseConstant.ID).textValue());
				tenant.setName(tenantNode.path(ResponseConstant.NAME).textValue());
				
			    list.add(tenant);
		     }
			return list;
		}catch(Exception e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 return null;
	}
}
