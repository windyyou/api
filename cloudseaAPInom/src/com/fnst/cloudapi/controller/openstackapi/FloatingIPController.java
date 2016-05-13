package com.fnst.cloudapi.controller.openstackapi;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fnst.cloudapi.pojo.openstackapi.forgui.FloatingIP;
import com.fnst.cloudapi.service.openstackapi.FloatingIPService;
import com.fnst.cloudapi.util.ParamConstant;

/**
 * 
 * floating ip操作
 *
 */
@RestController
public class FloatingIPController {
	@Autowired
	private FloatingIPService floatingIPServiceImpl;
	
	
	 @RequestMapping(value="/floatingips",method=RequestMethod.GET)
	 public List<FloatingIP>  getFloatingIPList(@RequestHeader(value=ParamConstant.AUTH_TOKEN,defaultValue="nownoimpl") String guiToken, 
	    		@RequestParam(value=ParamConstant.LIMIT,defaultValue="") String limit){
		 
		 Map<String,String> paramMap=null; 
		 
		 if(!"".equals(limit)){
			 paramMap = new HashMap<String,String>();
			 paramMap.put(ParamConstant.LIMIT, limit);
		 }
		 
		 //TODO
		 
		 List<FloatingIP> list=floatingIPServiceImpl.getFloatingIPList(paramMap, guiToken);
		 return list;
	 }
	 
	 /**
	  * 
	  * @param guiToken
	  * @param limit
	  * @param name
	  * @param status
	  * @param imageid
	  * @return list<FloatingIPExt>
	  */
/*	 @RequestMapping(value="/floating-ips",method=RequestMethod.GET)
	    public List<FloatingIPExt>  getSnapshotList(@RequestHeader(value=ParamConstant.AUTH_TOKEN,defaultValue="nownoimpl") String guiToken, 
	    		@RequestParam(value="limit",defaultValue="") String limit,
	    		@RequestParam(value="name", defaultValue="") String name,
	    		@RequestParam(value="status",defaultValue="") String status,
	            @RequestParam(value="image",defaultValue="") String imageid) {
	    	
	    	Map<String,String> paramMap=null; 
	    	
	    	if(!"".equals(limit)){
	    		paramMap=new HashMap<String,String>();
	    		paramMap.put("limit", limit);
	    	}
	    	
	    	if(!"".equals(name)){		
	    		if(paramMap==null) paramMap=new HashMap<String,String>();
	    		paramMap.put("name", name);
	    	}
	    	
	    	if(!"".equals(status)){		
	    		if(paramMap==null) paramMap=new HashMap<String,String>();
	    		paramMap.put("status", status);
	    	}
	    	
	    	if(!"".equals(imageid)){		
	    		if(paramMap==null) paramMap=new HashMap<String,String>();
	    		paramMap.put("image", imageid);
	    	}
	    	
	    	//@TODO 1. guitoken should has no defaultValue,if there no token ,bad request  
	    	
	    	//@TODO 2. guitoken should be checked, timeout or not
	   	
	    	
	    	List<FloatingIPExt> list=floatingIPServiceImpl.getFloatingIPExtList(paramMap, guiToken);
	    	
	    	return list;
	        
	    }*/
}
