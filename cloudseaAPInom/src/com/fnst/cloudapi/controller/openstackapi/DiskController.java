package com.fnst.cloudapi.controller.openstackapi;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fnst.cloudapi.pojo.openstackapi.forgui.Disk;
import com.fnst.cloudapi.service.openstackapi.DiskService;
import com.fnst.cloudapi.service.openstackapi.OsApiServiceFactory;

@RestController
public class DiskController {
	
	@RequestMapping(value = "/disks", method = RequestMethod.GET)
	public List<Disk> getDisksList(
			@RequestHeader(value = "X-ApiAuth-Token", defaultValue = "nownoimpl") String guiToken,
			@RequestParam(value = "limit", defaultValue = "") String limit,
			@RequestParam(value = "name", defaultValue = "") String name,
			@RequestParam(value = "status", defaultValue = "") String status,
			@RequestParam(value = "instance", defaultValue = "") String instanceId) {

		Map<String, String> paramMap = null;

		if (!"".equals(limit)) {
			paramMap = new HashMap<String, String>();
			paramMap.put("limit", limit);
		}

		if (!"".equals(name)) {
			if (paramMap == null)
				paramMap = new HashMap<String, String>();
			paramMap.put("name", name);
		}

		if (!"".equals(status)) {
			if (paramMap == null)
				paramMap = new HashMap<String, String>();
			paramMap.put("status", status);
		}

		if (!"".equals(instanceId)) {
			if (paramMap == null)
				paramMap = new HashMap<String, String>();
			paramMap.put("instance", instanceId);
		}

		// @TODO 1. guitoken should has no defaultValue,if there no token ,bad
		// request

		// @TODO 2. guitoken should be checked, timeout or not

		DiskService diskService = OsApiServiceFactory.getDiskService();
		List<Disk> list = diskService.getDiskList(paramMap, guiToken);

		return list;

	}
}
