package com.fnst.cloudapi.controller.openstackapi;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fnst.cloudapi.pojo.openstackapi.forgui.Keypair;
import com.fnst.cloudapi.service.openstackapi.KeypairService;
import com.fnst.cloudapi.service.openstackapi.OsApiServiceFactory;
import com.fnst.cloudapi.util.ParamConstant;

@RestController
public class KeypairController {

	 @RequestMapping(value="/keypairs",method=RequestMethod.GET)
	 public List<Keypair>  getKeypairList(@RequestHeader(value=ParamConstant.AUTH_TOKEN,defaultValue="nownoimpl") String guiToken,
			    @RequestParam(value=ParamConstant.LIMIT,defaultValue="") String limit,
	    		@RequestParam(value=ParamConstant.NAME, defaultValue="") String name){
        
         Map<String,String> paramMap=null; 
		 
		 if(!"".equals(limit)){
			 paramMap = new HashMap<String,String>();
			 paramMap.put(ParamConstant.LIMIT, limit);
		 }
		 
		 if(!"".equals(name)){
			 if(null == paramMap)
				 paramMap = new HashMap<String,String>();
			 paramMap.put(ParamConstant.OWNER, name);
		 }
		 
		 KeypairService resService= OsApiServiceFactory.getkeypairService();
		 List<Keypair> list=resService.getKeypairList(paramMap, guiToken);
		 
		 return list;
	 }
	 
	 @RequestMapping(value="/keypairs",method=RequestMethod.POST)
	 public String createKeypair(@RequestHeader(value=ParamConstant.AUTH_TOKEN,defaultValue="nownoimpl") String guiToken,
			    @RequestParam(value=ParamConstant.NAME,defaultValue="") String name,
	    		@RequestParam(value=ParamConstant.PUBLIC_KEY, defaultValue="") String public_key){
        
         Map<String,String> paramMap=null; 
		 
         name = "testKeyGen4";
		 if(!"".equals(name)){
			 paramMap = new HashMap<String,String>();
			 paramMap.put(ParamConstant.NAME, name);
		 }
		 
		 if(!"".equals(public_key)){
			 if(null == paramMap)
				 paramMap = new HashMap<String,String>();
			 paramMap.put(ParamConstant.PUBLIC_KEY, public_key);
		 }
		 
		 KeypairService resService= OsApiServiceFactory.getkeypairService();
		 return resService.createKeypair(paramMap, guiToken);
		 
	 }
}
