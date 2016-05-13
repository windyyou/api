package com.fnst.cloudapi.controller.hello;

import java.io.IOException;

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

@RestController
public class HelloController {
	
	private static final String template = "Hello, %s!";
    
	/**
	 * all get,post
	 * @param name
	 * @return
	 */
    @RequestMapping("/tokenhello")
    public Hello hello(@RequestParam(value="name", defaultValue="World") String name,@RequestParam(value="type",defaultValue="default") String type) {
        return new Hello(10000, String.format(template, name),type);
    }
    
    /**
     * get 
     * @param name
     * @return
     */
    @RequestMapping(value="/tokenhello2",method=RequestMethod.GET)
    public Hello hello2(@RequestParam(value="name") String name,@RequestParam(value="type") String type) {
        return new Hello(10000, String.format(template, name),type);
    }
    
    /**
     * post
     * @param name
     * @return
     */
    @RequestMapping(value="/tokenhello3",method=RequestMethod.POST)
    public Hello hello3(@RequestParam(value="name") String name,@RequestParam(value="type") String type) {
    	
        return new Hello(10000, name,type);
    }

    
    /**
     * for get request and json content
     * @param name
     * @return
     */
    @RequestMapping("/hellojson")
    public Hello hellojson(@RequestHeader("Accept-Encoding") String header, @RequestBody String body) {
    	
    	
        return new Hello(10000, header,body);
    }
    
    /**
     * for get request and json content
     * @param name
     * @return
     */
    @RequestMapping("/hellojson2")
    public String hellojson2(@RequestHeader("Accept-Encoding") String header, @RequestBody String body) {
    	
    	
        return header;
    }
    
    /**
     * for get request and json content
     * @param name
     * @return
     */
    @RequestMapping("/hellojson3")
    public String hellojson3(@RequestHeader("Accept-Encoding")  String header, @RequestBody String body) {
    	
    	
        return body;
    }
    
    /**
     * for get request and json content
     * get data from json content
     * @param name
     * @return
     */
    @RequestMapping("/hellojson4")
    public Hello hellojson4(@RequestHeader("Accept-Encoding")  String header, @RequestBody String body) {
    	
    	 ObjectMapper mapper = new ObjectMapper();
         Hello hello = null;
         try {
        	 hello= mapper.readValue(body,  Hello.class);
         } catch (JsonParseException e) {
             e.printStackTrace();
         } catch (JsonMappingException e) {
             e.printStackTrace();
         } catch (IOException e) {
             e.printStackTrace();
         }
         return hello;
    }
    
}
