package com.fnst.cloudapi.controller.openstackapi;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fnst.cloudapi.pojo.openstackapi.forgui.Image;
import com.fnst.cloudapi.service.openstackapi.ImageService;
import com.fnst.cloudapi.service.openstackapi.OsApiServiceFactory;
import com.fnst.cloudapi.util.ParamConstant;

@RestController
public class ImageController {
	 @RequestMapping(value="/allimages",method=RequestMethod.GET)
	 public List<Image>  getImagesList(@RequestHeader(value=ParamConstant.AUTH_TOKEN,defaultValue="nownoimpl") String guiToken, 
	    		@RequestParam(value=ParamConstant.LIMIT,defaultValue="") String limit,
	    		@RequestParam(value=ParamConstant.NAME, defaultValue="") String name,
	    		@RequestParam(value=ParamConstant.STATUS,defaultValue="") String status,
	            @RequestParam(value=ParamConstant.VISIBILITY,defaultValue="") String visibility){
		 
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
		 
		 if(!"".equals(status)){
			 if(null == paramMap)
				 paramMap = new HashMap<String,String>();
			 paramMap.put(ParamConstant.STATUS, status); 
		 }
		 
		 if(!"".equals(visibility)){
			 if(null == paramMap){
				 paramMap = new HashMap<String,String>();
			 paramMap.put(ParamConstant.VISIBILITY,visibility);
			 }
		 }
		 
		 //TODO
		 
		 ImageService resService= OsApiServiceFactory.getImageService();
		 List<Image> list=resService.getImageList(paramMap, guiToken);
		 return list;
	  }
}
