package com.fnst.cloudapi.service.openstackapi;

import java.util.List;
import java.util.Map;

import com.fnst.cloudapi.pojo.openstackapi.forgui.Snapshot;

public interface SnapshotService {
	public List<Snapshot> getSnapshotList(Map<String,String> paraMap,String tokenId);

}
