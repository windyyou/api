package com.fnst.cloudapi.service.openstackapi.impl;

import java.io.IOException;
import java.text.DecimalFormat;
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
import com.fnst.cloudapi.pojo.openstackapi.forgui.Image;
import com.fnst.cloudapi.pojo.openstackapi.forgui.Instance;
import com.fnst.cloudapi.pojo.openstackapi.forgui.Snapshot;
import com.fnst.cloudapi.service.commen.impl.CloudConfigAndTokenHandler;
import com.fnst.cloudapi.service.openstackapi.SnapshotService;
import com.fnst.cloudapi.util.http.HttpClientForOsBase;
import com.fnst.cloudapi.util.http.HttpClientForOsRequest;
import com.fnst.cloudapi.util.http.RequestUrlHelper;

@Service
public class SnapshotServiceImpl  implements SnapshotService {
	
	@Autowired
	private CloudConfig cloudconfig;

	@Override
	public List<Snapshot> getSnapshotList(Map<String, String> paraMap, String tokenId) {
		// TODO Auto-generated method stub
		List list = new ArrayList();
		HttpClientForOsBase osClient = new HttpClientForOsBase(cloudconfig);
		TokenOs ot=osClient.getToken();
		   //token should have Regioninfo
		
		
		String region ="RegionOne";
		
		String url=ot.getEndPoint(TokenOs.EP_TYPE_IMAGE, region).getPublicURL();
		//paraMap.put("user_id",cloudconfig.g);
		url=RequestUrlHelper.createFullUrl(url+"/v2/images?owner_id="+cloudconfig.getOs_authtenantid()+"&image_type=snapshot", paraMap);
		
		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("X-Auth-Token", ot.getTokenid());
		
		HttpClientForOsRequest client =new HttpClientForOsRequest();
		Map<String, String>  rs =client.httpDoGet(url, headers);
//		Map<String, String>  rs =client.httpDoGet(url, ot.getTokenid());
		
		System.out.println("=============url:"+url); 
		System.out.println("=============httpcode:"+rs.get("httpcode")); 
		System.out.println("=============jsonbody:"+rs.get("jsonbody")); 
		
		
		if(Integer.parseInt(rs.get("httpcode"))>400){	
			
		 System.out.println("wo cha:request failed"); 
		 
		}else{
			
			   try {
					
					ObjectMapper mapper = new ObjectMapper();
					JsonNode rootNode = mapper.readTree(rs.get("jsonbody"));
					JsonNode snapshotNode=rootNode.path("images");
					int snapshots_num =snapshotNode.size();
					JsonNode oneSnapshot;
					//保留小数点后三位数字
					DecimalFormat decimalFormat=new DecimalFormat("0.###");

					if (snapshots_num > 0) {
						list = new ArrayList<Snapshot>();
						for (int i = 0; i < snapshots_num; i++) {
							oneSnapshot = snapshotNode.get(i);
							Snapshot snapshot = new Snapshot();
							snapshot.setId(oneSnapshot.path("id").textValue());
							snapshot.setName(oneSnapshot.path("name").textValue());
							snapshot.setStatus(oneSnapshot.path("status").textValue());
							float size = oneSnapshot.path("size").intValue();
							
							snapshot.setSize(decimalFormat.format(size/(1024*1024*1024))+"GB");
							
							snapshot.setCreatedAt(oneSnapshot.path("created_at").textValue());
							
							list.add(snapshot);
								    
  					  }
					}
					

			   }catch(Exception e){
				   e.printStackTrace();
			   }
		}
		return list;
	}
	
	

}
