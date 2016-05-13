package com.fnst.cloudapi.controller.openstackapi;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fnst.cloudapi.pojo.openstackapi.forgui.Network;
import com.fnst.cloudapi.service.openstackapi.NetworkService;
import com.fnst.cloudapi.service.openstackapi.OsApiServiceFactory;
import com.fnst.cloudapi.util.ParamConstant;

@RestController
public class NetworkController {
	
	/**
	 * get the network list by parameter and guitoken
	 * @param guiToken guitokenid
	 * @param limit    how many to be show
	 * @param name     the name of network
	 * @param status   the status of network
	 * @return
	 */
  
    @RequestMapping(value="/networks",method=RequestMethod.GET)
    public List<Network>  getNetworksList(@RequestHeader(value=ParamConstant.AUTH_TOKEN,defaultValue="nownoimpl") String guiToken,
		    @RequestParam(value=ParamConstant.LIMIT,defaultValue="") String limit,
    		@RequestParam(value=ParamConstant.NAME, defaultValue="") String name,
    		@RequestParam(value=ParamConstant.STATUS,defaultValue="") String status) {
    	
    	Map<String,String> paramMap=null; 
    	
    	if(!"".equals(limit)){
    		paramMap=new HashMap<String,String>();
    		paramMap.put(ParamConstant.LIMIT, limit);
    	}
    	
    	if(!"".equals(name)){		
    		if(paramMap==null) paramMap=new HashMap<String,String>();
    		paramMap.put(ParamConstant.NAME, name);
    	}
    	
    	if(!"".equals(status)){		
    		if(paramMap==null) paramMap=new HashMap<String,String>();
    		paramMap.put(ParamConstant.STATUS, status);
    	}
    	
    	    	
    	//@TODO 1. guitoken should has no defaultValue,if there no token ,bad request  
    	
    	//@TODO 2. guitoken should be checked, timeout or not
   	
    	
    	NetworkService nwService= OsApiServiceFactory.getNetworkService();
    	List<Network> list=nwService.getNetworkList(paramMap, guiToken);
    	
    	return list;
        
    }

}
