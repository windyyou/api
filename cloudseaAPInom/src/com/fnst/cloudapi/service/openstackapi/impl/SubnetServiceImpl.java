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
import com.fnst.cloudapi.pojo.openstackapi.forgui.Subnet;
import com.fnst.cloudapi.service.commen.AuthService;
import com.fnst.cloudapi.service.commen.impl.AuthServiceImpl;
import com.fnst.cloudapi.service.commen.impl.CloudConfigAndTokenHandler;
import com.fnst.cloudapi.service.openstackapi.NetworkService;
import com.fnst.cloudapi.service.openstackapi.SubnetService;
import com.fnst.cloudapi.util.ResponseConstant;
import com.fnst.cloudapi.util.ParamConstant;
import com.fnst.cloudapi.util.http.HttpClientForOsBase;
import com.fnst.cloudapi.util.http.HttpClientForOsRequest;
import com.fnst.cloudapi.util.http.RequestUrlHelper;

public class SubnetServiceImpl extends CloudConfigAndTokenHandler implements SubnetService {
	private HttpClientForOsRequest client=null;
	private int ERROR_HTTP_CODE = 400;

	public SubnetServiceImpl() {

	this.client =new HttpClientForOsRequest();

	}
	
    /**
     * get this users subnet list 
     */
	
	@Override
	public List<Subnet> getSubnetList(Map<String,String> paramMap,String guiTokenId) {
		
		//todo 1: 通过guitokenid 取得实际，用户信息
           //AuthService	as = new AuthServiceImpl();	
           //as.GetTokenOS(guiTokenId);
		   
		TokenOs ot = super.osToken;
		   //token should have Regioninfo
		
		
		String region ="RegionOne";
		
		//select this user's subnets
		if(paramMap==null) paramMap=new HashMap<String,String>();
		paramMap.put(ParamConstant.TENANT_ID, ot.getTenantid());
		
		String url=ot.getEndPoint(TokenOs.EP_TYPE_NETWORK, region).getPublicURL();		
		url=RequestUrlHelper.createFullUrl(url+"/v2.0/subnets", paramMap);
		
		
		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("X-Auth-Token", ot.getTokenid());
		
//		Map<String, String>  rs =client.httpDoGet(url, headers);
		Map<String, String>  rs =client.httpDoGet(url, ot.getTokenid());
		
		System.out.println("httpcode:"+rs.get("httpcode")); 
		System.out.println("jsonbody:"+rs.get("jsonbody")); 
		
		List<Subnet> list=null;	
		
		if(Integer.parseInt(rs.get("httpcode")) > ERROR_HTTP_CODE){	
			
		 System.out.println("wo cha:request failed!!!!"); 
		 
		}else{
			
			   try {
					ObjectMapper mapper = new ObjectMapper();
					JsonNode rootNode = mapper.readTree(rs.get("jsonbody"));
					JsonNode subnetsNode = rootNode.path(ResponseConstant.SUBNETS);
					int subnets_num = subnetsNode.size();
					if(0 == subnets_num)
		            	return null;

					if (subnets_num > 0) {
						Integer paramLimit = null;
						
					if(paramMap.get(ParamConstant.LIMIT) != null)
						paramLimit = Integer.parseInt(paramMap.get(ParamConstant.LIMIT));							
						
						list = new ArrayList<Subnet>();
						for (int i = 0; i < subnets_num; i++) {
							Subnet one = new Subnet();
							JsonNode oneSubnet = subnetsNode.get(i);
							
							if(paramMap != null){
								if(paramLimit != null && i >= paramLimit)
									break;								
							}
							
							one.setId(oneSubnet.path(ResponseConstant.ID).textValue());
							one.setName(oneSubnet.path(ResponseConstant.NAME).textValue());
							one.setIpVersion(oneSubnet.path(ResponseConstant.IP_VERSION).textValue());
							one.setGateway(oneSubnet.path(ResponseConstant.GATEWAY_IP).textValue());							
							//@TODO time maybe turn to china time
							one.setCreatedAt(oneSubnet.path(ResponseConstant.CREATED_AT).textValue());
							
							NetworkService nwService = new NetworkServiceImpl();
							Network nt = nwService.getNetwork(oneSubnet.path(ResponseConstant.NETWORK_ID).textValue(), guiTokenId);
							one.setNetwork(nt);
							
							//@TODO segment not have???
							
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
	public Subnet getSubnet(String SubnetId, String guiTokenId) {
		Subnet sn = null;
		//todo 1: 通过guitokenid 取得实际，用户信息
        //AuthService	as = new AuthServiceImpl();	
        //as.GetTokenOS(guiTokenId);
		   
		TokenOs ot = super.osToken;
		   //token should have Regioninfo
		
		
		String region ="RegionOne";
		
		//String url=ot.getEndPoint(TokenOs.EP_TYPE_NETWORK, region).getPublicURL();
		//url=url+"/v2.0/networks/" + NetworkId;
		String url=ot.getEndPoint(TokenOs.EP_TYPE_COMPUTE, region).getPublicURL();		
		url=url+"/v2.0/subnets/" + SubnetId;
		
		
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
					JsonNode subnetNode=rootNode.path(ResponseConstant.SUBNET);
					
					sn = new Subnet();
					sn.setId(SubnetId);
					sn.setName(subnetNode.path(ResponseConstant.NAME).textValue());
					sn.setIpVersion(subnetNode.path(ResponseConstant.IP_VERSION).textValue());
					sn.setGateway(subnetNode.path(ResponseConstant.GATEWAY_IP).textValue());							
					//@TODO time maybe turn to china time
					sn.setCreatedAt(subnetNode.path(ResponseConstant.CREATED_AT).textValue());
					
					NetworkService nwService = new NetworkServiceImpl();
					Network nt = nwService.getNetwork(subnetNode.path(ResponseConstant.NETWORK_ID).textValue(), guiTokenId);
					sn.setNetwork(nt);
					
					//@TODO segment not have???
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		return sn;

	}
	
}
