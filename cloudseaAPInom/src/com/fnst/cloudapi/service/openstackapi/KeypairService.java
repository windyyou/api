package com.fnst.cloudapi.service.openstackapi;

import java.util.List;
import java.util.Map;

import com.fnst.cloudapi.pojo.openstackapi.forgui.Keypair;

public interface KeypairService {
	public List<Keypair> getKeypairList(Map<String,String> paraMap,String tokenId);
	public String createKeypair(Map<String,String> paraMap,String tokenId);
}
