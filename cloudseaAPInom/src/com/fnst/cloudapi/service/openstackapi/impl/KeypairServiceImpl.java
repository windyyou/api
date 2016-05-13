package com.fnst.cloudapi.service.openstackapi.impl;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fnst.cloudapi.json.forgui.KeypairJSON;
import com.fnst.cloudapi.pojo.commen.TokenOs;
import com.fnst.cloudapi.pojo.openstackapi.forgui.Keypair;
import com.fnst.cloudapi.service.commen.impl.CloudConfigAndTokenHandler;
import com.fnst.cloudapi.service.openstackapi.KeypairService;
import com.fnst.cloudapi.util.ParamConstant;
import com.fnst.cloudapi.util.ResponseConstant;
import com.fnst.cloudapi.util.http.HttpClientForOsRequest;
import com.fnst.cloudapi.util.http.RequestUrlHelper;

public class KeypairServiceImpl extends CloudConfigAndTokenHandler implements KeypairService{
	private HttpClientForOsRequest httpClient=null;
    private int ERROR_HTTP_CODE = 400;
    private int NORMAL_HTTP_CODE = 200;
    
	public KeypairServiceImpl() {
		httpClient = new HttpClientForOsRequest();
	}
	
	@Override
	public List<Keypair> getKeypairList(Map<String,String> paraMap,String guiTokenId) {
		
		//todo 1: 通过guitokenid 取得实际，用户信息
        //AuthService	as = new AuthServiceImpl();	
        //as.GetTokenOS(guiTokenId);
		   
		TokenOs ot = super.osToken;
	    //token should have Regioninfo
		
		
		String region ="RegionOne";//we should get the regioninfo by the guiTokenId
		
		String url=ot.getEndPoint(TokenOs.EP_TYPE_COMPUTE, region).getPublicURL();		
		url=RequestUrlHelper.createFullUrl(url+"/os-keypairs", paraMap);
		
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
			JsonNode keypairsNode=rootNode.path(ResponseConstant.KEYPAIRS);
			int keypairsCount =keypairsNode.size();
            if(0 == keypairsCount)
            	return null;
            
            List<Keypair> list= new ArrayList<Keypair>();	
			for(int index = 0; index < keypairsCount; ++index){
				Keypair keypairInfo = new Keypair();
				JsonNode keypairNode = keypairsNode.get(index);
				JsonNode keypairInfoNode = keypairNode.path(ResponseConstant.KEYPAIR);
				
				keypairInfo.setPublickey(keypairInfoNode.path(ResponseConstant.PUBLIC_KEY).textValue());
				keypairInfo.setFingerprint(keypairInfoNode.path(ResponseConstant.FINGERPRINT).textValue());
				keypairInfo.setName(keypairInfoNode.path(ResponseConstant.NAME).textValue());
			    
			    list.add(keypairInfo);
		}
		return list;
	}
	catch(Exception e){
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		return null;
	}
	
	@Override
	public String createKeypair(Map<String,String> paraMap,String tokenId){
		//todo 1: 通过guitokenid 取得实际，用户信息
        //AuthService	as = new AuthServiceImpl();	
        //as.GetTokenOS(guiTokenId);
		   
		TokenOs ot = super.osToken;
	    //token should have Regioninfo
		String keyFilePath="c:\\keys\\";
		
		String region ="RegionOne";//we should get the regioninfo by the guiTokenId
		
		String url=ot.getEndPoint(TokenOs.EP_TYPE_COMPUTE, region).getPublicURL();		
		url=RequestUrlHelper.createFullUrl(url+"/os-keypairs", null);
		
		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("X-Auth-Token", ot.getTokenid());
		
		String body = generateBody(paraMap);
	
		Map<String, String>  rs =httpClient.httpDoPost(url, headers, body);
//		Map<String, String>  rs =client.httpDoGet(url, ot.getTokenid());
		
		System.out.println("httpcode:"+rs.get("httpcode")); 
		System.out.println("jsonbody:"+rs.get("jsonbody")); 
			
		if(Integer.parseInt(rs.get("httpcode")) != NORMAL_HTTP_CODE){
			System.out.println("wo cha:request failed"); 
			return ""; //TODO
		}
		
		try {
			ObjectMapper mapper = new ObjectMapper();
			JsonNode rootNode = mapper.readTree(rs.get("jsonbody"));
			JsonNode keypairNode=rootNode.path(ResponseConstant.KEYPAIR);
			int keypairProCount =keypairNode.size();
            if(5 != keypairProCount)
            	return "";
         //   Keypair keypair = new Keypair();
            String keyName = keypairNode.path(ResponseConstant.NAME).textValue();
            String privateKey = keypairNode.path(ResponseConstant.PRIVATE_KEY).textValue();
            keyFilePath.concat(keyName);
            if(true == saveKeyFile(keyFilePath,privateKey))
            	return keyFilePath.concat("\\key.pem");
          //  keypair.setName(keyName);
          //  keypair.setFingerprint(keypairNode.path(ResponseConstant.FINGERPRINT).textValue());
          //  keypair.setPrivatekey(keypairNode.path(ResponseConstant.PRIVATE_KEY).textValue());
          //  keypair.setPublickey(keypairNode.path(ResponseConstant.PUBLIC_KEY).textValue());
          //  keypair.setUserid(keypairNode.path(ResponseConstant.USER_ID).textValue());
            
            
		}
	    catch(Exception e){
		    // TODO Auto-generated catch block
		    e.printStackTrace();
	   }
		
		return "";
	}
	
	private String generateBody(Map<String,String> paraMap){
		if(null == paraMap || 0 == paraMap.size())
			return "";
		     
        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
        mapper.setSerializationInclusion(Include.NON_NULL);
        mapper.setSerializationInclusion(Include.NON_EMPTY);
        KeypairJSON keypair = null;
        if(1 == paraMap.size())
             keypair = new KeypairJSON(new Keypair(paraMap.get(ParamConstant.NAME)));   
        else
        	 keypair = new KeypairJSON(new Keypair(paraMap.get(ParamConstant.NAME),paraMap.get(ParamConstant.PUBLIC_KEY)));  
        String jsonStr = "";
		try {
			jsonStr = mapper.writeValueAsString(keypair);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return jsonStr; 
	}
	
	private boolean saveKeyFile(String keyPath,String privateKey){
		if("".equals(privateKey))
			return false;
		 String filePath = keyPath.concat("\\key.pem");
		 try{
			 // 获得文件对象  
			 File Keyfile = new File(filePath);  
			 if (!Keyfile.exists()) {  
				 if (!Keyfile.getParentFile().exists())
					 Keyfile.getParentFile().mkdirs(); 
				 Keyfile.createNewFile(); 
			 } 
		     FileWriter resultFile = new FileWriter(filePath);
		     PrintWriter myFile = new PrintWriter(resultFile);
		     myFile.println(privateKey);
		     resultFile.close();
		 }catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
		}
		 
		 return true;
	}
}
