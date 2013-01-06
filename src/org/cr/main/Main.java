/**
 * @description 
 * @author caorong
 * @date 2012-12-31
 * 
 */
package org.cr.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.log4j.Logger;
import org.cr.service.ServiceGetAuthorUidThread;
import org.cr.service.ServiceThread;
import org.cr.service.ServletRelationShipThread;

import weibo4j.Oauth;
import weibo4j.http.AccessToken;
import weibo4j.model.WeiboException;
import weibo4j.util.BareBonesBrowserLaunch;

/**
 * @description 
 * @author caorong
 * @date 2012-12-31
 */
public class Main implements Runnable{

	static Logger log = Logger.getLogger(Main.class);
 	@Override
	public void run() {
		Oauth oauth = new Oauth();
		String code = null;
		try {
			BareBonesBrowserLaunch.openURL(oauth.authorize("code"));
			System.out.println(oauth.authorize("code"));
			System.out.print("Hit enter when it's done.[Enter]:");
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			code = br.readLine().trim();// 获得code
			log.info("code: " + code);
		} catch (WeiboException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		AccessToken accessToken = null;
		
		try{
			accessToken = oauth.getAccessTokenByCode(code); 
			System.out.println(accessToken);
		} catch (WeiboException e) {
			if(401 == e.getStatusCode()){
				log.info("Unable to get the access token.");
			}else{
				e.printStackTrace();
			}
		}
		//my uid: 1057297283
		String access_token = accessToken.getAccessToken();
		new Thread(new ServletRelationShipThread(access_token)).start();
//		new Thread(new ServiceThread(access_token)).start();
//		new Thread(new ServiceGetAuthorUidThread(access_token)).start();
	}
 	
	public static void main(String[] args) {
		new Thread(new Main()).start();
	}

}
