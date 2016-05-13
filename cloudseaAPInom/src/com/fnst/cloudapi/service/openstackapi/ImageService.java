package com.fnst.cloudapi.service.openstackapi;

import java.util.List;
import java.util.Map;

import com.fnst.cloudapi.pojo.openstackapi.forgui.Image;

public interface ImageService {
	public List<Image> getImageList(Map<String,String> paraMap,String tokenId);
}
