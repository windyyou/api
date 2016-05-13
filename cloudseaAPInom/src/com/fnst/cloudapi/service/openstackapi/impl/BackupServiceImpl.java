package com.fnst.cloudapi.service.openstackapi.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fnst.cloudapi.pojo.commen.TokenOs;
import com.fnst.cloudapi.pojo.openstackapi.forgui.Backup;
import com.fnst.cloudapi.pojo.openstackapi.forgui.Disk;
import com.fnst.cloudapi.service.commen.impl.CloudConfigAndTokenHandler;
import com.fnst.cloudapi.service.openstackapi.BackupService;
import com.fnst.cloudapi.service.openstackapi.DiskService;
import com.fnst.cloudapi.service.openstackapi.OsApiServiceFactory;
import com.fnst.cloudapi.util.DateHelper;
import com.fnst.cloudapi.util.ResponseConstant;
import com.fnst.cloudapi.util.http.HttpClientForOsRequest;
import com.fnst.cloudapi.util.http.RequestUrlHelper;

public class BackupServiceImpl extends CloudConfigAndTokenHandler implements BackupService {
	
	private HttpClientForOsRequest client = null;
	private int ERROR_HTTP_CODE = 400;
	
	public BackupServiceImpl() {
		super();
		this.client = new HttpClientForOsRequest();
	}

	@Override
	public List<Backup> getBackupList(Map<String, String> paraMap, String tokenId) {
		
		// todo 1: 通过guitokenid 取得实际，用户信息
		// AuthService as = new AuthServiceImpl();
		// as.GetTokenOS(guiTokenId);

		TokenOs ot = super.osToken;
		// token should have Regioninfo

		String region = "RegionOne";

		String url = ot.getEndPoint(TokenOs.EP_TYPE_VOLUMEV2, region).getPublicURL();
		url = RequestUrlHelper.createFullUrl(url + "/backups/detail", paraMap);

		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("X-Auth-Token", ot.getTokenid());

		Map<String, String> rs = client.httpDoGet(url, headers);
		// Map<String, String> rs =client.httpDoGet(url, ot.getTokenid());

		System.out.println("httpcode:" + rs.get("httpcode"));
		System.out.println("jsonbody:" + rs.get("jsonbody"));

		List<Backup> list = null;

		if (Integer.parseInt(rs.get("httpcode")) > ERROR_HTTP_CODE) {

			System.out.println("wo cha:request failed");

		} else {
			
			try {
				
				ObjectMapper mapper = new ObjectMapper();
				JsonNode rootNode = mapper.readTree(rs.get("jsonbody"));
				JsonNode backupsNode = rootNode.path(ResponseConstant.BACKUPS);
				int backups_num = backupsNode.size();

				if (backups_num > 0) {
					list = new ArrayList<Backup>();
					for (int i = 0; i < backups_num; i++) {

						Backup one = new Backup();
						JsonNode oneBackup = backupsNode.get(i);
						one.setId(oneBackup.path(ResponseConstant.ID).textValue());
						one.setName(this.getName(oneBackup.path(ResponseConstant.NAME).textValue()));
						one.setStatus(oneBackup.path(ResponseConstant.STATUS).textValue());
						one.setSize(this.getSize(oneBackup.path(ResponseConstant.SIZE).intValue()));
						one.setCreatedAt(DateHelper.getDateByString(oneBackup.path(ResponseConstant.CREATED_AT).textValue()).toLocaleString());
						one.setDisk(this.getDisk(oneBackup.path(ResponseConstant.VOLUME_ID).textValue()));
						
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
	
	private Disk getDisk(String volumeId) {
		Disk disk = new Disk();
		if(volumeId != null) {
			DiskService diskService = OsApiServiceFactory.getDiskService();
			disk = diskService.getDiskById(volumeId, null);
		}
		return disk;
	}
	
	public static void main(String[] args) {
		List<Backup> list = new BackupServiceImpl().getBackupList(null, null);
		ObjectMapper mapper = new ObjectMapper();
		String result = null;
		try {
			result = mapper.writeValueAsString(list);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		System.out.println(result);
	}

}
