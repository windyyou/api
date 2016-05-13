package com.fnst.cloudapi.util.http;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.Header;
import org.apache.http.HttpStatus;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.fnst.cloudapi.pojo.commen.CloudConfig;
import com.fnst.cloudapi.pojo.commen.TokenOs;
import com.fnst.cloudapi.pojo.commen.TokenOsEndPoint;
import com.fnst.cloudapi.pojo.commen.TokenOsEndPointV3;
import com.fnst.cloudapi.pojo.commen.TokenOsEndPoints;
import com.fnst.cloudapi.util.DateHelper;

public class HttpClientForOsRequest {
	
	private  HttpClientForRest client=null;

	
	public HttpClientForOsRequest() {
		client = new HttpClientForRest(); 
	}

	public Map<String,String>  httpDoPost(String url,Map<String,String> headers,String jsonbody){
		
		CloseableHttpResponse rs = null;
		Map<String,String>   rsmap=null;
		try {
			rs=client.post(url, headers, jsonbody);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (rs == null) {
			
			System.out.println("wo cha:request failed"); // prints true
			
		} else {
			rsmap =new HashMap<String,String>();
			rsmap.put("httpcode", String.valueOf(rs.getStatusLine().getStatusCode()));
			
			try {
				ObjectMapper mapper = new ObjectMapper();
				String resData = EntityUtils.toString(rs.getEntity());
				JsonNode node = mapper.readTree(resData);
				rsmap.put("jsonbody", node.toString());
			} catch (ParseException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}		
		return rsmap;
		
	}
	
	public Map<String,String>  httpDoPost(String url,String tokenid,String jsonbody){
		
		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("X-Auth-Token", tokenid);
		
		return httpDoPost(url,headers,jsonbody);
	}
	
	public Map<String,String>  httpDoPut(String url,Map<String,String> headers,String jsonbody){
		
		CloseableHttpResponse rs = null;
		Map<String,String>   rsmap=null;
		
		try {
			rs=client.put(url, headers, jsonbody);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (rs == null) {
			
			System.out.println("wo cha:request failed"); // prints true
			
		} else {
			rsmap =new HashMap<String,String>();
			rsmap.put("httpcode", String.valueOf(rs.getStatusLine().getStatusCode()));
			
			try {
				ObjectMapper mapper = new ObjectMapper();
				String resData = EntityUtils.toString(rs.getEntity());
				JsonNode node = mapper.readTree(resData);
				rsmap.put("jsonbody", node.toString());
			} catch (ParseException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}		
		return rsmap;
		
	}
	
	public Map<String,String>  httpDoPut(String url,String tokenid,String jsonbody){
		
		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("X-Auth-Token", tokenid);
		
		return httpDoPut(url,headers,jsonbody);
	}
	
	public Map<String,String>  httpDoPatch(String url,Map<String,String> headers,String jsonbody){
		
		CloseableHttpResponse rs = null;
		Map<String,String>   rsmap=null;
		try {
			rs=client.patch(url, headers, jsonbody);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (rs == null) {
			
			System.out.println("wo cha:request failed"); // prints true
			
		} else {
			rsmap =new HashMap<String,String>();
			rsmap.put("httpcode", String.valueOf(rs.getStatusLine().getStatusCode()));
			
			try {
				ObjectMapper mapper = new ObjectMapper();
				String resData = EntityUtils.toString(rs.getEntity());
				JsonNode node = mapper.readTree(resData);
				rsmap.put("jsonbody", node.toString());
			} catch (ParseException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}		
		return rsmap;
		
	}
	
	public Map<String,String>  httpDoPatch(String url,String tokenid,String jsonbody){
		
		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("X-Auth-Token", tokenid);
		
		return httpDoPatch(url,headers,jsonbody);
	}
	
	public Map<String,String>  httpDoDelete(String url,Map<String,String> headers){
		
		CloseableHttpResponse rs = null;
		Map<String,String>   rsmap=null;
		try {
			rs=client.delete(url, headers, null);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (rs == null) {
			
			System.out.println("wo cha:request failed"); // prints true
			
		} else {
			rsmap =new HashMap<String,String>();
			rsmap.put("httpcode", String.valueOf(rs.getStatusLine().getStatusCode()));
			
			try {
				ObjectMapper mapper = new ObjectMapper();
				String resData = EntityUtils.toString(rs.getEntity());
				JsonNode node = mapper.readTree(resData);
				rsmap.put("jsonbody", node.toString());
			} catch (ParseException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}		
		return rsmap;	
	}
	
	public Map<String,String>  httpDoDelete(String url,String tokenid){
		
		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("X-Auth-Token", tokenid);
		
		return httpDoDelete(url,headers);
	}
	
	public Map<String,String>  httpDoGet(String url,Map<String,String> headers){
		
		CloseableHttpResponse rs = null;
		Map<String,String>   rsmap=null;
		try {
			rs=client.get(url, headers, null);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (rs == null) {
			
			System.out.println("wo cha:request failed"); // prints true
			
		} else {
			rsmap =new HashMap<String,String>();
			rsmap.put("httpcode", String.valueOf(rs.getStatusLine().getStatusCode()));
			
			try {
				ObjectMapper mapper = new ObjectMapper();
				String resData = EntityUtils.toString(rs.getEntity());
				JsonNode node = mapper.readTree(resData);
				rsmap.put("jsonbody", node.toString());
			} catch (ParseException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}		
		return rsmap;	
	}
	
	public Map<String,String>  httpDoGet(String url,String tokenid){
		
		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("X-Auth-Token", tokenid);
		
		return httpDoGet(url,headers);
	}
	
}
