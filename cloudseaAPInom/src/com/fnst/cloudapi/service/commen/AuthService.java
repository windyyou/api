package com.fnst.cloudapi.service.commen;

import com.fnst.cloudapi.pojo.commen.CloudUser;
import com.fnst.cloudapi.pojo.commen.TokenGui;
import com.fnst.cloudapi.pojo.commen.TokenOs;

public interface AuthService {
	
	/**
	 * 用户登录验证
	 * @param user
	 * @return
	 */
	public boolean authLogin(CloudUser user);
	
	/**
	 * 为新用户生成用于web访问的Token
	 * @param user
	 * @return
	 */
	public TokenGui authCeate(CloudUser user);
	
	/**
	 * 检查所给Token是否有效
	 * @param tokengui
	 * @return
	 */
	public boolean  authCheck(TokenGui tokengui);
	
	/**
	 * 取得用于Openstack 访问的Token
	 * @param user
	 * @return
	 */
	public TokenOs  GetTokenOS(CloudUser user);
	
	
	/**
	 * 取得用于Openstack 访问的Token
	 * @param user
	 * @return
	 */
	public TokenOs  GetTokenOS(String guiTokenId);
	
}
