package com.fnst.cloudapi.service.openstackapi.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.binding.MapperMethod.ParamMap;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fnst.cloudapi.pojo.commen.CloudUser;
import com.fnst.cloudapi.pojo.commen.TokenOs;
import com.fnst.cloudapi.pojo.commen.TokenOsEndPoint;
import com.fnst.cloudapi.pojo.openstackapi.forgui.Network;
import com.fnst.cloudapi.service.commen.AuthService;
import com.fnst.cloudapi.service.commen.impl.AuthServiceImpl;
import com.fnst.cloudapi.service.commen.impl.CloudConfigAndTokenHandler;
import com.fnst.cloudapi.service.openstackapi.NetworkService;
import com.fnst.cloudapi.util.ResponseConstant;
import com.fnst.cloudapi.util.ParamConstant;
import com.fnst.cloudapi.util.http.HttpClientForOsBase;
import com.fnst.cloudapi.util.http.HttpClientForOsRequest;
import com.fnst.cloudapi.util.http.RequestUrlHelper;

public class NetworkServiceImpl extends CloudConfigAndTokenHandler implements NetworkService {
	private HttpClientForOsRequest client=null;
	private int ERROR_HTTP_CODE = 400;

	public NetworkServiceImpl() {

	this.client =new HttpClientForOsRequest();

	}

	@Override
	public List<Network> getNetworkList(Map<String,String> paramMap,String guiTokenId) {
		
		//todo 1: 通过guitokenid 取得实际，用户信息
           //AuthService	as = new AuthServiceImpl();	
           //as.GetTokenOS(guiTokenId);
		   
		TokenOs ot = super.osToken;
		   //token should have Regioninfo
		
		
		String region ="RegionOne";
		
		String url=ot.getEndPoint(TokenOs.EP_TYPE_NETWORK, region).getPublicURL();		
		url=RequestUrlHelper.createFullUrl(url+"/v2.0/networks", paramMap);
		
		
		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("X-Auth-Token", ot.getTokenid());
		
//		Map<String, String>  rs =client.httpDoGet(url, headers);
		Map<String, String>  rs =client.httpDoGet(url, ot.getTokenid());
		
		System.out.println("httpcode:"+rs.get("httpcode")); 
		System.out.println("jsonbody:"+rs.get("jsonbody")); 
		
		List<Network> list=null;	
		
		if(Integer.parseInt(rs.get("httpcode")) > ERROR_HTTP_CODE){	
			
		 System.out.println("wo cha:request failed!!!!"); 
		 
		}else{
			
			   try {
					ObjectMapper mapper = new ObjectMapper();
					JsonNode rootNode = mapper.readTree(rs.get("jsonbody"));
					JsonNode networksNode=rootNode.path(ResponseConstant.NETWORKS);
					int networks_num =networksNode.size();
					if(0 == networks_num)
		            	return null;

					if (networks_num > 0) {
						Integer paraLimit = null;
						
						if(paramMap != null){
							if(paramMap.get(ParamConstant.LIMIT) != null)
								paraLimit = Integer.parseInt(paramMap.get(ParamConstant.LIMIT));							
						}
						
						list = new ArrayList<Network>();
						for (int i = 0; i < networks_num; i++) {
							Network one = new Network();
							JsonNode oneNetwork=networksNode.get(i);

							//check this network is belong to this user
							if(!ot.getTenantid().equals(oneNetwork.path(ResponseConstant.TENANT_ID).textValue()))
								continue;
							
							if(paramMap != null){
								if(paraLimit != null && i >= paraLimit)
									break;								
							}
							
							one.setId(oneNetwork.path(ResponseConstant.ID).textValue());
							one.setName(oneNetwork.path(ResponseConstant.NAME).textValue());
							one.setStatus(oneNetwork.path(ResponseConstant.STATUS).textValue());
							
							//@TODO time maybe turn to china time
							//@TODO created_at not have in the networks. not same as the api
							//one.setCreatedAt(oneNetwork.path("created_at").textValue());							
							one.setCreatedAt(getNetworkCreatedat(one.getId()));
							
							list.add(one);
  					  }
					}
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		
		return list;
	}
	
	@Override
	public Network getNetwork(String NetworkId, String guiTokenId) {
		Network nw = null;
		//todo 1: 通过guitokenid 取得实际，用户信息
        //AuthService	as = new AuthServiceImpl();	
        //as.GetTokenOS(guiTokenId);
		   
		TokenOs ot = super.osToken;
		   //token should have Regioninfo
		
		
		String region ="RegionOne";
		
		//String url=ot.getEndPoint(TokenOs.EP_TYPE_NETWORK, region).getPublicURL();
		//url=url+"/v2.0/networks/" + NetworkId;
		String url=ot.getEndPoint(TokenOs.EP_TYPE_COMPUTE, region).getPublicURL();		
		url=url+"/os-networks/" + NetworkId;
		
		
		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("X-Auth-Token", ot.getTokenid());
		
//		Map<String, String>  rs =client.httpDoGet(url, headers);
		Map<String, String>  rs =client.httpDoGet(url, ot.getTokenid());
		
		System.out.println("httpcode:"+rs.get("httpcode")); 
		System.out.println("jsonbody:"+rs.get("jsonbody")); 
		
		if(Integer.parseInt(rs.get("httpcode")) > ERROR_HTTP_CODE){	
			
		 System.out.println("wo cha:request failed!!!!"); 
		 
		}else{
			   try {
				    
					ObjectMapper mapper = new ObjectMapper();
					JsonNode rootNode = mapper.readTree(rs.get("jsonbody"));
					JsonNode networkNode=rootNode.path(ResponseConstant.NETWORK);
					nw = new Network();
					nw.setId(NetworkId);
					nw.setName(networkNode.path(ResponseConstant.NAME).textValue());
					nw.setStatus(networkNode.path(ResponseConstant.STATUS).textValue());
					//@TODO time maybe turn to china time
					//@TODO created_at not have in the networks. not same as the api
					nw.setCreatedAt(getNetworkCreatedat(nw.getId()));
					
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		return nw;

	}
	
	private String getNetworkCreatedat(String NetworkId) {
		String CreatedAt = null;
		//todo 1: 通过guitokenid 取得实际，用户信息
        //AuthService	as = new AuthServiceImpl();	
        //as.GetTokenOS(guiTokenId);
		   
		TokenOs ot = super.osToken;
		   //token should have Regioninfo
		
		
		String region ="RegionOne";
		
		//String url=ot.getEndPoint(TokenOs.EP_TYPE_NETWORK, region).getPublicURL();
		//url=url+"/v2.0/networks/" + NetworkId;
		String url=ot.getEndPoint(TokenOs.EP_TYPE_COMPUTE, region).getPublicURL();		
		url=url+"/os-networks/" + NetworkId;
		
		
		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("X-Auth-Token", ot.getTokenid());
		
//		Map<String, String>  rs =client.httpDoGet(url, headers);
		Map<String, String>  rs =client.httpDoGet(url, ot.getTokenid());
		
		System.out.println("httpcode:"+rs.get("httpcode")); 
		System.out.println("jsonbody:"+rs.get("jsonbody")); 
		
		if(Integer.parseInt(rs.get("httpcode")) > ERROR_HTTP_CODE){
		 System.out.println("wo cha:request failed!!!!"); 
		 
		}else{
			   try {
				    
					ObjectMapper mapper = new ObjectMapper();
					JsonNode rootNode = mapper.readTree(rs.get("jsonbody"));
					JsonNode networkNode=rootNode.path(ResponseConstant.NETWORK);
					//@TODO time maybe turn to china time
					CreatedAt = networkNode.path(ResponseConstant.CREATE_AT).textValue();

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		return CreatedAt;

	}
	
}
