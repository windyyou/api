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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fnst.cloudapi.pojo.commen.CloudUser;
import com.fnst.cloudapi.pojo.commen.TokenOs;
import com.fnst.cloudapi.pojo.commen.TokenOsEndPoint;
import com.fnst.cloudapi.pojo.openstackapi.forgui.Image;
import com.fnst.cloudapi.pojo.openstackapi.forgui.Instance;
import com.fnst.cloudapi.service.commen.AuthService;
import com.fnst.cloudapi.service.commen.impl.AuthServiceImpl;
import com.fnst.cloudapi.service.commen.impl.CloudConfigAndTokenHandler;
import com.fnst.cloudapi.service.openstackapi.InstanceService;
import com.fnst.cloudapi.util.http.HttpClientForOsBase;
import com.fnst.cloudapi.util.http.HttpClientForOsRequest;
import com.fnst.cloudapi.util.http.RequestUrlHelper;

public class InstanceServiceImpl extends CloudConfigAndTokenHandler implements InstanceService {
	private HttpClientForOsRequest client=null;

	public InstanceServiceImpl() {

	this.client =new HttpClientForOsRequest();

	}

	@Override
	public List<Instance> getInstanceList(Map<String,String> paraMap,String guiTokenId) {
		
		//todo 1: 通过guitokenid 取得实际，用户信息
           //AuthService	as = new AuthServiceImpl();	
           //as.GetTokenOS(guiTokenId);
		   
		TokenOs ot = super.osToken;
		   //token should have Regioninfo
		
		
		String region ="RegionOne";
		
		String url=ot.getEndPoint(TokenOs.EP_TYPE_COMPUTE, region).getPublicURL();		
		url=RequestUrlHelper.createFullUrl(url+"/servers/detail", paraMap);
		
		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("X-Auth-Token", ot.getTokenid());
		
		
		Map<String, String>  rs =client.httpDoGet(url, headers);
//		Map<String, String>  rs =client.httpDoGet(url, ot.getTokenid());
		
		System.out.println("httpcode:"+rs.get("httpcode")); 
		System.out.println("jsonbody:"+rs.get("jsonbody")); 
		
		List<Instance> list=null;	
		
		if(Integer.parseInt(rs.get("httpcode"))>400){	
			
		 System.out.println("wo cha:request failed"); 
		 
		}else{
			
			   try {
					
					ObjectMapper mapper = new ObjectMapper();
					JsonNode rootNode = mapper.readTree(rs.get("jsonbody"));
					JsonNode serversNode=rootNode.path("servers");
					int servers_num =serversNode.size();

					if (servers_num > 0) {
						list = new ArrayList<Instance>();
						for (int i = 0; i < servers_num; i++) {
							
							Instance one =new Instance();
							JsonNode oneServer=serversNode.get(i);
							String vmid =oneServer.path("id").textValue();
							one.setId(vmid);
							one.setName(oneServer.path("name").textValue());
							one.setStatus(oneServer.path("status").textValue());
							
							//@TODO time maybe turn to china time
							one.setCreatedAt(oneServer.path("created").textValue());	
										
							one.setType(this.getType(oneServer.path("OS-EXT-AZ:availability_zone").textValue()));
							
							one.setImage(this.getImageInfo(oneServer.path("image").path("id").textValue(), ot));
							List<String> [] iplist=this.getFloatingIpAndIps(oneServer.path("addresses"));
							one.setIps(iplist[0]);
							one.setFloatingIps(iplist[1]);
							
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
	
	
	private String getType(String az){
		
		//@todo get type by az from apidb
		
		return az;

	}
	
	private List<String>[] getFloatingIpAndIps(JsonNode addr){
		Iterator<JsonNode> ir=	addr.elements();
		ArrayList<String> ips = new ArrayList<String>();
		ArrayList<String> floatingips = new ArrayList<String>();
		while(ir.hasNext()){					
			JsonNode oneNetCard=ir.next();
			int size2 =oneNetCard.size();		
			for (int ii=0;ii<size2;ii++){
				String type =oneNetCard.path(ii).path("OS-EXT-IPS:type").textValue();
				String ip   =oneNetCard.path(ii).path("addr").textValue();			
                if ("floating".equals(type)) {
					System.out.println("type floating");
					System.out.println("ip=" + ip);
					floatingips.add(ip);
				} else {
					System.out.println("type other");
					System.out.println("ip=" + ip);
					ips.add(ip);
				}
					
			}
		}
			
		ArrayList<String>[] rs = new ArrayList[2];
		rs[0]=ips;
	    rs[1]=floatingips;	
		return rs;
	}
	
	private Image getImageInfo(String imageid,TokenOs token){
		Image img=null;
		String url =token.getEndPoint(TokenOs.EP_TYPE_IMAGE).getPublicURL();
		url=RequestUrlHelper.createFullUrl(url+"/v2/images/"+imageid, null);
	
		Map<String, String>  rs =this.client.httpDoGet(url, token.getTokenid());
		if(Integer.parseInt(rs.get("httpcode"))>400){	
			
		 System.out.println("wo cha:request failed"); 
			 
		}else{
			
			ObjectMapper mapper = new ObjectMapper();
		 try {
				img = new Image();
				JsonNode rootNode = mapper.readTree(rs.get("jsonbody"));
				img.setId(imageid);
				img.setName(rootNode.path("name").textValue());

		     } catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			 }
	
		}
		
		return img;
	}
	
	public static void main(String arg[]){
		
		
		List<Instance> list=new InstanceServiceImpl().getInstanceList(null, null);
		
		System.out.println("list="+list);
		
		for(Instance in : list){
			
			System.out.println("in="+in);
			
		}
		ObjectMapper mapper = new ObjectMapper();
        String resultStr=null;
		try {
			resultStr = mapper.writeValueAsString(list);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
        System.out.println(resultStr);  
		
		
	}

}
