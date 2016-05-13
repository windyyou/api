package com.fnst.cloudapi.controller.hello;
import java.io.IOException;
import java.util.List;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fnst.cloudapi.pojo.hello.Hello;
import com.fnst.cloudapi.service.hello.TestOS4J;

import org.openstack4j.model.compute.Action;
import org.openstack4j.model.compute.Flavor;
import org.openstack4j.model.compute.Server;
import org.openstack4j.model.identity.Tenant;
import org.openstack4j.model.identity.User;
import org.openstack4j.model.image.Image;
import org.openstack4j.model.network.Network;
import org.openstack4j.model.network.Router;
import org.openstack4j.model.network.Subnet;


@RestController
public class TestOS4jController {
	
	@RequestMapping(value="/users",method=RequestMethod.GET)
	List<? extends User> getUsers(){
		
		return TestOS4J.getUsers();
	}
	
	@RequestMapping(value="/images",method=RequestMethod.GET)
	List<? extends Image> getImages(){
		
		return TestOS4J.getImages();
	}
	
	@RequestMapping(value="/tenants",method=RequestMethod.GET)
	List<? extends Tenant> getTenants(){
		
		return TestOS4J.getTenants();
	}
	
	@RequestMapping(value="/tenant?name=&ddds=sd&dsdfsdfs=dddd",method=RequestMethod.POST)
	Tenant createTenant(@RequestParam(value="name") String name,@RequestParam(value="description") String desc){
	
		return TestOS4J.createTenantv2(name,desc);
	}
	
	@RequestMapping(value="/tenantjson",method=RequestMethod.POST)
	Tenant createTenantByjson(@RequestBody String body){
		
	   ObjectMapper mapper = new ObjectMapper();
	   Tenant tt = null;
	    try {
	      tt= mapper.readValue(body,  Tenant.class);
	    } catch (JsonParseException e) {
	        e.printStackTrace();
	    } catch (JsonMappingException e) {
	        e.printStackTrace();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }	
	    
	   return TestOS4J.createTenantv2(tt.getName(),tt.getDescription());
	}
	
    @RequestMapping(value="/testv3")
    void testv3(){
    	TestOS4J.justTestv3();
    }
}
