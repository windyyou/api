package com.fnst.cloudapi.controller.openstackapi;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fnst.cloudapi.pojo.openstackapi.forgui.Backup;
import com.fnst.cloudapi.service.openstackapi.BackupService;
import com.fnst.cloudapi.service.openstackapi.OsApiServiceFactory;
import com.fnst.cloudapi.util.ParamConstant;

@RestController
public class BackupController {

	@RequestMapping(value = "/backups", method = RequestMethod.GET)
	public List<Backup> getBackupsList(
			@RequestHeader(value = ParamConstant.AUTH_TOKEN, defaultValue = "nownoimpl") String guiToken,
			@RequestParam(value = ParamConstant.LIMIT, defaultValue = "") String limit,
			@RequestParam(value = ParamConstant.NAME, defaultValue = "") String name,
			@RequestParam(value = ParamConstant.STATUS, defaultValue = "") String status,
			@RequestParam(value = ParamConstant.DISK, defaultValue = "") String diskId) {

		Map<String, String> paramMap = null;

		if (!"".equals(limit)) {
			paramMap = new HashMap<String, String>();
			paramMap.put(ParamConstant.LIMIT, limit);
		}

		if (!"".equals(name)) {
			if (paramMap == null)
				paramMap = new HashMap<String, String>();
			paramMap.put(ParamConstant.NAME, name);
		}

		if (!"".equals(status)) {
			if (paramMap == null)
				paramMap = new HashMap<String, String>();
			paramMap.put(ParamConstant.STATUS, status);
		}

		if (!"".equals(diskId)) {
			if (paramMap == null)
				paramMap = new HashMap<String, String>();
			paramMap.put(ParamConstant.DISK, diskId);
		}

		// @TODO 1. guitoken should has no defaultValue,if there no token ,bad
		// request

		// @TODO 2. guitoken should be checked, timeout or not

		BackupService backupService = OsApiServiceFactory.getBackupService();
		List<Backup> list = backupService.getBackupList(paramMap, guiToken);

		return list;

	}
	
}
