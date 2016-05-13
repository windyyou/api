package com.fnst.cloudapi.service.openstackapi.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fnst.cloudapi.pojo.commen.TokenOs;
import com.fnst.cloudapi.pojo.openstackapi.forgui.Image;
import com.fnst.cloudapi.service.commen.impl.CloudConfigAndTokenHandler;
import com.fnst.cloudapi.service.openstackapi.ImageService;
import com.fnst.cloudapi.util.ResponseConstant;
import com.fnst.cloudapi.util.http.HttpClientForOsRequest;
import com.fnst.cloudapi.util.http.RequestUrlHelper;

public class ImageServiceImpl extends CloudConfigAndTokenHandler implements ImageService{
	private HttpClientForOsRequest httpClient=null;
    private int ERROR_HTTP_CODE = 400;
    private int NORMAL_HTTP_CODE = 200;
    
	public ImageServiceImpl() {
		httpClient = new HttpClientForOsRequest();
	}
	@Override
	public List<Image> getImageList(Map<String,String> paraMap,String guiTokenId) {
	
		//todo 1: 通过guitokenid 取得实际，用户信息
        //AuthService	as = new AuthServiceImpl();	
        //as.GetTokenOS(guiTokenId);
		   
		TokenOs ot = super.osToken;
	    //token should have Regioninfo
		
		
		String region ="RegionOne";//we should get the regioninfo by the guiTokenId
		
		String url=ot.getEndPoint(TokenOs.EP_TYPE_IMAGE, region).getPublicURL();		
		url=RequestUrlHelper.createFullUrl(url+"/v2/images", paraMap);
		
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
				JsonNode imagesNode=rootNode.path(ResponseConstant.IMAGES);
				int imagesCount =imagesNode.size();
                if(0 == imagesCount)
                	return null;
                
                List<Image> list= new ArrayList<Image>();	
				for(int index = 0; index < imagesCount; ++index){
					Image imageInfo = new Image();
					JsonNode imageNode = imagesNode.get(index);
		
					imageInfo.setId(imageNode.path(ResponseConstant.ID).textValue());
					imageInfo.setName(imageNode.path(ResponseConstant.NAME).textValue());
					//imageInfo.setTags(imageNode.path(ResponseConstant.TAGS).textValue());
//					imageInfo.setVisibility(imageNode.path(ResponseConstant.VISIBILITY).textValue());
//					imageInfo.setDiskFormat(imageNode.path(ResponseConstant.DISK_FORMAT).textValue());
//					imageInfo.setMinDisk(imageNode.path(ResponseConstant.MIN_DISK).intValue());
//					imageInfo.setMinRam(imageNode.path(ResponseConstant.MIN_RAM).intValue());
//				    imageInfo.setSize(imageNode.path(ResponseConstant.SIZE).intValue());
//				    imageInfo.setFile(imageNode.path(ResponseConstant.FILE).textValue());
//				    imageInfo.setOwner(imageNode.path(ResponseConstant.OWNER).textValue());
//				  
//				    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
//				    imageInfo.setCreateAt( dateFormat.parse(imageNode.path(ResponseConstant.CREATE_AT).textValue()));
//				    imageInfo.setUpdateAt( dateFormat.parse(imageNode.path(ResponseConstant.UPDATE_AT).textValue()));
				    
				    list.add(imageInfo);
			}
			return list;
		}
		catch(Exception e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}
}
