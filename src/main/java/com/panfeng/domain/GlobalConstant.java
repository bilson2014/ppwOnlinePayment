package com.panfeng.domain;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 系统常量表
 * 
 * @author Jack
 *
 */
public final class GlobalConstant extends BaseObject {

	private static final long serialVersionUID = -7702371618133614803L;
	public static String PAY_APPID;// 支付平台支付id

	public static String PAY_APPSECRET;

	public static String PAY_TESTSECRET;

	public static String PAY_MASTERSECRET;

	public static String PAY_SIGN_KEY;

	public static String PAY_SERVER;

	// ssl
	public static String KEY_STORE_TRUST_PATH; // truststore的路径

	public static String KEY_STORE_TYPE; // truststore的类型

	public static String KEY_STORE_TRUST_PASSWORD; // truststore的密码

	public static String KEY_STORE_CLIENT_PATH;

	public static String KEY_STORE_TYPE_P12;

	public static String KEY_STORE_PASSWORD;
	
	public static String COOKIES_SCOPE = null;

	private static GlobalConstant GLOBALCONSTANT = new GlobalConstant();

	static {
		final InputStream is = GLOBALCONSTANT.getClass().getClassLoader().getResourceAsStream("jdbc.properties");
		try {
			Properties propertis = new Properties();
			propertis.load(is);
			// pay begin
			PAY_APPID = propertis.getProperty("pay.appid");
			PAY_APPSECRET = propertis.getProperty("pay.appsecret");
			PAY_TESTSECRET = propertis.getProperty("pay.testsecret");
			PAY_MASTERSECRET = propertis.getProperty("pay.mastersecret");
			PAY_SIGN_KEY = propertis.getProperty("pay.sign.key");
			PAY_SERVER = propertis.getProperty("pay.server");
			// pay end

			// ssl
			KEY_STORE_TRUST_PATH = propertis.getProperty("key.store.trust.path");
			KEY_STORE_TYPE = propertis.getProperty("key.store.type");
			KEY_STORE_TRUST_PASSWORD = propertis.getProperty("key.store.trust.password");
			KEY_STORE_CLIENT_PATH = propertis.getProperty("key.store.client.path");
			KEY_STORE_TYPE_P12 = propertis.getProperty("key.store.type.p12");
			KEY_STORE_PASSWORD = propertis.getProperty("key.store.password");

		} catch (Exception e) {

		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
