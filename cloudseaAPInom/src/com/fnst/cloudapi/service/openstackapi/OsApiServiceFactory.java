package com.fnst.cloudapi.service.openstackapi;


import com.fnst.cloudapi.service.openstackapi.impl.BackupServiceImpl;
import com.fnst.cloudapi.service.openstackapi.impl.DiskServiceImpl;
import com.fnst.cloudapi.service.openstackapi.impl.FloatingIPServiceImpl;
import com.fnst.cloudapi.service.openstackapi.impl.HostServiceImpl;
import com.fnst.cloudapi.service.openstackapi.impl.ImageServiceImpl;
import com.fnst.cloudapi.service.openstackapi.impl.InstanceServiceImpl;
import com.fnst.cloudapi.service.openstackapi.impl.KeypairServiceImpl;
import com.fnst.cloudapi.service.openstackapi.impl.NetworkServiceImpl;
import com.fnst.cloudapi.service.openstackapi.impl.SubnetServiceImpl;
import com.fnst.cloudapi.service.openstackapi.impl.QuotaServiceImpl;
import com.fnst.cloudapi.service.openstackapi.impl.TenantServiceImpl;

public class OsApiServiceFactory {
	
	
	public static InstanceService getInstanceService(){
		
		return new InstanceServiceImpl();
	}
	
    public static NetworkService getNetworkService(){
		
		return new NetworkServiceImpl();
	}
	
	public static DiskService getDiskService() {
		
		return new DiskServiceImpl();
	}

	public static ImageService getImageService(){
		
		return new ImageServiceImpl();
	}
	
	public static KeypairService getkeypairService(){
		
		return new KeypairServiceImpl();
	}
	
	public static BackupService getBackupService() {
		
		return new BackupServiceImpl();
	}
	
	public static HostService getHostService(){
		return new HostServiceImpl();
	}
	
	public static QuotaService getQuotaService(){
		return new QuotaServiceImpl();
	}
	
	public static TenantService getTenantService(){
		return new TenantServiceImpl();
	}
	
	public static FloatingIPService getFloatingIPService(){
		return new FloatingIPServiceImpl();
	}
	
	public static SubnetService getSubnetService(){
		return new SubnetServiceImpl();
	}
	
}
