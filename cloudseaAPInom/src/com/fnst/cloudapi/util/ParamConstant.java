package com.fnst.cloudapi.util;

public class ParamConstant {

	public static final String AUTH_TOKEN = "X-ApiAuth-Token";
	public static final String LIMIT = "limit";
	public static final String NAME = "name";
	public static final String OWNER = "owner";
	public static final String STATUS = "status";
	public static final String VISIBILITY = "visibility";
    public static final String TENANT_ID = "tenant_id";
	public static final String PUBLIC_KEY = "public_key";
	public static final String DISK = "disk";
	public static final String CORE_NUMBERS = "core_numbers";
	public static final String MEMORY_SIZE = "memory_size";
	public static final String DISK_INFO = "disk_info";
	public static final String PERFORMANCE_DISK = "performanceDisk";
	public static final String HIGH_PERFORMANCE_DISK = "highPerformanceDisk";
	public static final String CAPACITY_DISK = "capacityDisk";
	public static final String SERVICE_NAME = "service";
	public static final String HOST_NAME = "host_name";
	public static final int TOTAL_RES = 1;
	public static final int USED_RES = 2;
	public static final int FREE_RES = 3;
	public static final int MB = 1024;
	/****** network type******/
	public static final String BGP_NETWORK = "bgp_net";
	public static final String TELECOM_NETWORK = "telcom_net";
	public static final String UNICOM_NETWORK = "unicom_net";
	public static final String MOBILE_NETWORK = "mobile_net";
	public static final String NETWORK_ID = "network_id";
	
	public static final String RAM_LIMIT = "ram";
	public static final String FLOATING_IPS_LIMIT = "floating_ips";
	public static final String CORES_LIMIT = "limit";
    
	public static final String ACTIVE_STATUS = "ACTIVE";
	
	private ParamConstant(){}
}

