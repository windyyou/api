package com.fnst.cloudapi.service.openstackapi;

import java.util.List;
import java.util.Map;

import com.fnst.cloudapi.pojo.openstackapi.forgui.Backup;

public interface BackupService {

	public List<Backup> getBackupList(Map<String,String> paraMap,String tokenId);
}
