package com.fnst.cloudapi.service.openstackapi.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fnst.cloudapi.pojo.commen.CloudConfig;
import com.fnst.cloudapi.pojo.commen.TokenOs;
import com.fnst.cloudapi.pojo.openstackapi.forgui.FloatingIP;
import com.fnst.cloudapi.service.openstackapi.FloatingIPService;
import com.fnst.cloudapi.util.ResponseConstant;
import com.fnst.cloudapi.util.http.HttpClientForOsBase;
import com.fnst.cloudapi.util.http.HttpClientForOsRequest;
import com.fnst.cloudapi.util.http.RequestUrlHelper;

@Service
public class FloatingIPServiceImpl implements FloatingIPService{
	
	@Autowired
	private CloudConfig cloudconfig;
	
	private HttpClientForOsRequest httpClient=null;
    private int ERROR_HTTP_CODE = 400;

	/*public FloatingIPServiceImpl() {
		super();
		httpClient = new HttpClientForOsRequest();
	}
	*/
	@Override
	public List<FloatingIP> getFloatingIPList(Map<String,String> paramMap,String tokenId){
		
		HttpClientForOsBase osClient = new HttpClientForOsBase(cloudconfig);
		TokenOs ot=osClient.getToken();
		//todo 1: 通过guitokenid 取得实际，用户信息
        //AuthService	as = new AuthServiceImpl();	
        //as.GetTokenOS(guiTokenId);
		String region ="RegionOne";//we should get the regioninfo by the guiTokenId
		
		String url=ot.getEndPoint(TokenOs.EP_TYPE_NETWORK, region).getPublicURL();		
		url=RequestUrlHelper.createFullUrl(url+"/v2.0/floatingips", null);
		
		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("X-Auth-Token", ot.getTokenid());
		
		httpClient =new HttpClientForOsRequest();
		Map<String, String>  rs = httpClient.httpDoGet(url, headers);
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
				JsonNode floatingipsNode=rootNode.path(ResponseConstant.FLOATINGIPS);
				int floatingipsCount =floatingipsNode.size();
                if(0 == floatingipsCount)
                	return null;
                
                List<FloatingIP> list= new ArrayList<FloatingIP>();	
				for(int index = 0; index < floatingipsCount; ++index){
					FloatingIP floatingIPInfo = new FloatingIP();
					JsonNode floatingIPNode = floatingipsNode.get(index);
					floatingIPInfo.setFloatingNetId(floatingIPNode.path(ResponseConstant.FLOATING_NETWORK_ID).textValue());
					floatingIPInfo.setRouterId(floatingIPNode.path(ResponseConstant.ROUTER_ID).textValue());
					floatingIPInfo.setFixedIpAddress(floatingIPNode.path(ResponseConstant.FIXED_IP_ADDRESS).textValue());
					floatingIPInfo.setFloatingIpAddress(floatingIPNode.path(ResponseConstant.FLOATING_IP_ADDRESS).textValue());
					floatingIPInfo.setTenantId(floatingIPNode.path(ResponseConstant.TENANT_ID).textValue());
					floatingIPInfo.setStatus(floatingIPNode.path(ResponseConstant.STATUS).textValue());
					floatingIPInfo.setId(floatingIPNode.path(ResponseConstant.ID).textValue());
					
					list.add(floatingIPInfo);
			}
	
			return list;
		}
		catch(Exception e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

//	@Override
//	public List<FloatingIPExt> getFloatingIPExtList(Map<String, String> paraMap, String tokenId) {
//		// TODO Auto-generated method stub
//		HttpClientForOsBase osClient = new HttpClientForOsBase(cloudconfig);
//		TokenOs ot=osClient.getToken();
//		//todo 1: 通过guitokenid 取得实际，用户信息
//        //AuthService	as = new AuthServiceImpl();	
//        //as.GetTokenOS(guiTokenId);
//		String region ="RegionOne";//we should get the regioninfo by the guiTokenId
//		
//		String url=ot.getEndPoint(TokenOs.EP_TYPE_NETWORK, region).getPublicURL();		
//		url=RequestUrlHelper.createFullUrl(url+"/v2.0/floatingips", null);
//		
//		HashMap<String, String> headers = new HashMap<String, String>();
//		headers.put("X-Auth-Token", ot.getTokenid());
//		
//		httpClient =new HttpClientForOsRequest();
//		Map<String, String>  rs = httpClient.httpDoGet(url, headers);
////		Map<String, String>  rs =client.httpDoGet(url, ot.getTokenid());
//		
//		System.out.println("httpcode:"+rs.get("httpcode")); 
//		System.out.println("jsonbody:"+rs.get("jsonbody")); 
//			
//		if(Integer.parseInt(rs.get("httpcode")) > ERROR_HTTP_CODE){
//			System.out.println("wo cha:request failed"); 
//			return null;
//		}
//	    
//		try {
//				ObjectMapper mapper = new ObjectMapper();
//				JsonNode rootNode = mapper.readTree(rs.get("jsonbody"));
//				JsonNode floatingipsNode=rootNode.path(ResponseConstant.FLOATINGIPS);
//				int floatingipsCount =floatingipsNode.size();
//                if(0 == floatingipsCount)
//                	return null;
//                
//                List<FloatingIPExt> list= new ArrayList<FloatingIPExt>();	
//				for(JsonNode floatingIPNode:floatingipsNode){
//					HashMap map = new HashMap();
//					FloatingIPExt floatingIPExt = new FloatingIPExt();					
//					floatingIPExt.setId(floatingIPNode.path(ResponseConstant.ID).textValue());
//					floatingIPExt.setName("Fake Name");
//					floatingIPExt.setBandwidth("100 Mbps");
//					floatingIPExt.setLine("中国电信");
//					floatingIPExt.setStatus(floatingIPNode.path(ResponseConstant.STATUS).textValue());
//					floatingIPExt.setIp(floatingIPNode.path(ResponseConstant.FLOATING_IP_ADDRESS).textValue());
//					floatingIPExt.setCreatedAt("2016-01-01");
//					if(floatingIPNode.path("port_id").textValue()!=null&&!(floatingIPNode.path("port_id").textValue().equals(""))){
//						
//					}
//					
//					list.add(floatingIPExt);
//					
//			}
//	
//			return list;
//		}
//		catch(Exception e){
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		return null;
//	}
	
}
