package com.fnst.cloudapi.service.openstackapi.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fnst.cloudapi.pojo.commen.TokenOs;
import com.fnst.cloudapi.pojo.openstackapi.forgui.Disk;
import com.fnst.cloudapi.pojo.openstackapi.forgui.Instance;
import com.fnst.cloudapi.service.commen.impl.CloudConfigAndTokenHandler;
import com.fnst.cloudapi.service.openstackapi.DiskService;
import com.fnst.cloudapi.util.DateHelper;
import com.fnst.cloudapi.util.ResponseConstant;
import com.fnst.cloudapi.util.http.HttpClientForOsRequest;
import com.fnst.cloudapi.util.http.RequestUrlHelper;

public class DiskServiceImpl extends CloudConfigAndTokenHandler implements DiskService {

	private HttpClientForOsRequest client = null;
	private int ERROR_HTTP_CODE = 400;

	public DiskServiceImpl() {
		super();
		this.client = new HttpClientForOsRequest();
	}

	@Override
	public List<Disk> getDiskList(Map<String, String> paraMap, String tokenId) {

		// todo 1: 通过guitokenid 取得实际，用户信息
		// AuthService as = new AuthServiceImpl();
		// as.GetTokenOS(guiTokenId);

		TokenOs ot = super.osToken;
		// token should have Regioninfo

		String region = "RegionOne";

		String url = ot.getEndPoint(TokenOs.EP_TYPE_VOLUMEV2, region).getPublicURL();
		url = RequestUrlHelper.createFullUrl(url + "/volumes/detail", paraMap);

		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("X-Auth-Token", ot.getTokenid());

		Map<String, String> rs = client.httpDoGet(url, headers);
		// Map<String, String> rs =client.httpDoGet(url, ot.getTokenid());

		System.out.println("httpcode:" + rs.get("httpcode"));
		System.out.println("jsonbody:" + rs.get("jsonbody"));

		List<Disk> list = null;

		if (Integer.parseInt(rs.get("httpcode")) > ERROR_HTTP_CODE) {

			System.out.println("wo cha:request failed");

		} else {

			try {

				ObjectMapper mapper = new ObjectMapper();
				JsonNode rootNode = mapper.readTree(rs.get("jsonbody"));
				JsonNode volumesNode = rootNode.path(ResponseConstant.VOLUMES);
				int volumes_num = volumesNode.size();

				if (volumes_num > 0) {
					list = new ArrayList<Disk>();
					for (int i = 0; i < volumes_num; i++) {

						Disk one = new Disk();
						JsonNode oneVolume = volumesNode.get(i);
						one.setId(oneVolume.path(ResponseConstant.ID).textValue());
						one.setName(this.getName(oneVolume.path(ResponseConstant.NAME).textValue()));
						one.setStatus(oneVolume.path(ResponseConstant.STATUS).textValue());
						one.setSize(this.getSize(oneVolume.path(ResponseConstant.SIZE).intValue()));
						one.setCreatedAt(DateHelper.getDateByString(oneVolume.path(ResponseConstant.CREATED_AT).textValue()).toLocaleString());
						one.setType(this.getType(oneVolume.path(ResponseConstant.AVAILABILITY_ZONE).textValue()));
						one.setInstance(this.getInstance(oneVolume.path(ResponseConstant.ATTACHMENTS)));

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
	
	private String getName(String name){
		
		return name == null ? "" : name;
	}
	
	private String getSize(int size){
		
		return String.valueOf(size).concat("GB");
	}
	
	private String getType(String az){
		
		//@todo get type by az from apidb
		
		return az;

	}
	
	private Instance getInstance(JsonNode attachments) throws JsonProcessingException, IOException{
		Instance instance = new Instance();
		Iterator<JsonNode> ir =	attachments.elements();
		if (ir.hasNext()) {
			JsonNode oneInstance = ir.next();
			instance.setId(oneInstance.path("server_id").textValue());
			instance.setName(oneInstance.path("host_name").textValue());
		}
		
		return instance;
	}
	
	@Override
	public Disk getDiskById(String diskId, String tokenId) {
		// todo 1: 通过guitokenid 取得实际，用户信息
		// AuthService as = new AuthServiceImpl();
		// as.GetTokenOS(guiTokenId);

		TokenOs ot = super.osToken;
		// token should have Regioninfo

		String region = "RegionOne";

		String url = ot.getEndPoint(TokenOs.EP_TYPE_VOLUMEV2, region).getPublicURL();
		url = RequestUrlHelper.createFullUrl(url + "/volumes/" + diskId, null);

		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("X-Auth-Token", ot.getTokenid());

		Map<String, String> rs = client.httpDoGet(url, headers);
		// Map<String, String> rs =client.httpDoGet(url, ot.getTokenid());

		System.out.println("httpcode:" + rs.get("httpcode"));
		System.out.println("jsonbody:" + rs.get("jsonbody"));

		Disk disk = new Disk();

		if (Integer.parseInt(rs.get("httpcode")) > ERROR_HTTP_CODE) {

			System.out.println("wo cha:request failed");

		} else {

			try {

				ObjectMapper mapper = new ObjectMapper();
				JsonNode rootNode = mapper.readTree(rs.get("jsonbody"));
				JsonNode volumeNode = rootNode.path(ResponseConstant.VOLUME);
				if(volumeNode != null) {
					disk.setId(volumeNode.path(ResponseConstant.ID).textValue());
					disk.setName(this.getName(volumeNode.path(ResponseConstant.NAME).textValue()));
//					disk.setStatus(volumeNode.path(ResponseConstant.STATUS).textValue());
//					disk.setSize(this.getSize(volumeNode.path(ResponseConstant.SIZE).intValue()));
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return disk;
	}
	
	
	public static void main(String[] args) {
		List<Disk> list = new DiskServiceImpl().getDiskList(null, null);
		ObjectMapper mapper = new ObjectMapper();
        String resultStr=null;
		try {
			mapper.setSerializationInclusion(Include.NON_NULL);
			resultStr = mapper.writeValueAsString(list);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
        System.out.println(resultStr);  
//		new DiskServiceImpl().getDiskById("afbc53f1-6a81-4c60-af99-f7d1f1c54b37", null);
	}


}
