package com.fnst.cloudapi.controller.openstackapi;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fnst.cloudapi.pojo.openstackapi.forgui.Subnet;
import com.fnst.cloudapi.service.openstackapi.SubnetService;
import com.fnst.cloudapi.service.openstackapi.OsApiServiceFactory;
import com.fnst.cloudapi.util.ParamConstant;

@RestController
public class SubnetController {
	
	/**
	 * get the subnet list by parameter and guitoken
	 *  - this user's subnets filter by opentack api (tenant_id)
	 *  - network filter by openstack api
	 *  -  
	 * @param guiToken guitokenid
	 * @param limit    how many to be show
	 * @param name     the name of subnet
	 * @param status   the status of subnet 
	 * @param network   the id of network
	 * @return
	 */
  
    @RequestMapping(value="/subnets",method=RequestMethod.GET)
    public List<Subnet>  getSubnetsList(@RequestHeader(value=ParamConstant.AUTH_TOKEN,defaultValue="nownoimpl") String guiToken,
		    @RequestParam(value=ParamConstant.LIMIT,defaultValue="") String limit,
    		@RequestParam(value=ParamConstant.NAME, defaultValue="") String name,
    		@RequestParam(value=ParamConstant.NETWORK_ID,defaultValue="") String network_id) {
    	
    	Map<String,String> paramMap=null; 
    	
    	if(!"".equals(limit)){
    		paramMap=new HashMap<String,String>();
    		paramMap.put(ParamConstant.LIMIT, limit);
    	}
    	
    	if(!"".equals(name)){		
    		if(paramMap==null) paramMap=new HashMap<String,String>();
    		paramMap.put(ParamConstant.NAME, name);
    	}
    	
    	if(!"".equals(network_id)){		
    		if(paramMap==null) paramMap=new HashMap<String,String>();
    		paramMap.put(ParamConstant.NETWORK_ID, network_id);
    	}
    	
    	    	
    	//@TODO 1. guitoken should has no defaultValue,if there no token ,bad request  
    	
    	//@TODO 2. guitoken should be checked, timeout or not
   	
    	
    	SubnetService snService= OsApiServiceFactory.getSubnetService();
    	List<Subnet> list = snService.getSubnetList(paramMap, guiToken);
    	
    	return list;
        
    }

}
