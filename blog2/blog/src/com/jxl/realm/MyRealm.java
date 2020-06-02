package com.jxl.realm;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import com.jxl.entity.Blogger;
import com.jxl.service.BloggerService;

/**
 * title:MyRealm.java
 * description:�Զ���Realm(��)
 * time:2019��1��22�� ����10:50:57
 * author:jxl
 */
public class MyRealm extends AuthorizingRealm{

	@Resource
	private BloggerService bloggerService;
	
	/**
	 * Ϊ����ǰ��¼���û������ɫ��Ȩ
	 * (����Ŀǰϵͳû��ɶ��Դ��ֻ��admin�����û�,�ʶ�ûд�����ɫ��Ȩ��(��Դ)�߼�)
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		return null;
	}

	/**
	 * ��֤��ǰ��¼���û�
	 * (�ɹ�ʱ,����½�û��󶨵��Ự��;ʧ��ʱ,��ʵ�ᱨ����exception,���׳�����ǰ��ҳ��չʾ(����ʵ��))
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		//��ȡ���(������ָ �û���)-ƾ֤(������ָ ����)
		String userName=(String)token.getPrincipal();
		Blogger blogger=bloggerService.getByUserName(userName);
		if(blogger!=null){
			//��ǰ�û���Ϣ�浽session��
			SecurityUtils.getSubject().getSession().setAttribute("currentUser", blogger); 
			AuthenticationInfo authcInfo=new SimpleAuthenticationInfo(blogger.getUserName(),blogger.getPassword(),getName());
			return authcInfo;
		}else{
			return null;				
		}
	}

}
