package com.tommasocodella.androdmtp.services;

import com.tommasocodella.androdmtp.gps.AndroDMTPLocationListener;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.widget.Toast;

public class AndroDMTPMainService extends Service {
	
	public static final int MSG_SAY_START 		= 0;
	public static final int MSG_SAY_PAUSE 		= 1;
	public static final int MSG_SAY_RESUME 		= 2;
	public static final int MSG_SAY_STOP 		= 3;
	public static final int MSG_SAY_RESTART 	= 4;
	
	public static final int MSG_SET_SRVADDR		= 10;
	public static final int MSG_SET_SRVPORT		= 11;
	public static final int MSG_SET_SRVDEVICE	= 12;
	public static final int MSG_SET_SRVACCOUNT	= 13;
	public static final int MSG_SET_SRVUNIQUE	= 14;
	public static final int MSG_SET_SRVACCESS	= 15;
	
	public static final int MSG_SET_GPSRATE				= 20;
	public static final int MSG_SET_GPSACCURACY			= 21;
	public static final int MSG_SET_GPSMINSPEED			= 22;
	public static final int MSG_SET_GPSMOTIONSTARTTYPE	= 23;
	public static final int MSG_SET_GPSMOTIONSTARTMETER	= 24;
	public static final int MSG_SET_GPSMOTIONSTARTKPH	= 25;
	public static final int MSG_SET_GPSMOTIONINMOTION	= 26;
	public static final int MSG_SET_GPSMOTIONSTOP		= 27;
	public static final int MSG_SET_GPSMOTIONDORMANT	= 28;
	
	
	private AndroDMTP dmtp = null;
	private LocationListener locationListener;
	final Messenger mMessenger = new Messenger(new IncomingHandler());
	
	
	
	
	class IncomingHandler extends Handler{
		@Override
		public void handleMessage(Message msg){		
			switch (msg.what) {
				case MSG_SAY_START:
					Toast.makeText(getApplicationContext(), "AndroDMTP Started", Toast.LENGTH_SHORT).show();
					dmtp.startApp();
					break;
				case MSG_SAY_PAUSE:
					Toast.makeText(getApplicationContext(), "AndroDMTP Paused", Toast.LENGTH_SHORT).show();
					dmtp.pauseApp();
					break;
				case MSG_SAY_RESUME:
					Toast.makeText(getApplicationContext(), "AndroDMTP Resumed", Toast.LENGTH_SHORT).show();
					dmtp.awakeApp();
					break;
				case MSG_SAY_STOP:
					Toast.makeText(getApplicationContext(), "AndroDMTP Stopped", Toast.LENGTH_SHORT).show();
					dmtp.exitApp();
					break;
				case MSG_SAY_RESTART:	
					Toast.makeText(getApplicationContext(), "AndroDMTP Restarted", Toast.LENGTH_SHORT).show();
					dmtp.restartApp();
					break;
					
				case MSG_SET_SRVADDR:
					dmtp.setServerAddr((String) msg.obj);
					break;
				case MSG_SET_SRVPORT:
					dmtp.setServerPort((String) msg.obj);
					break;
				case MSG_SET_SRVDEVICE:
					dmtp.setServerDevice((String) msg.obj);
					break;
				case MSG_SET_SRVACCOUNT:
					dmtp.setServerAccount((String) msg.obj);
					break;
				case MSG_SET_SRVUNIQUE:
					dmtp.setServerUnique((String) msg.obj);
					break;
				case MSG_SET_SRVACCESS:
					dmtp.setServerAccess((String) msg.obj);
					break;
					
				case MSG_SET_GPSRATE:
					dmtp.setGpsRate((String) msg.obj);
					break;
				case MSG_SET_GPSACCURACY:
					dmtp.setGpsAccuracy((String) msg.obj);
					break;
				case MSG_SET_GPSMINSPEED:
					dmtp.setGpsMinSpeed((String) msg.obj);
					break;
				case MSG_SET_GPSMOTIONSTARTTYPE:
					dmtp.setMotionStartType(msg.arg1);
					break;
				case MSG_SET_GPSMOTIONSTARTMETER:
					dmtp.setMotionStartMeter((String) msg.obj);
					break;
				case MSG_SET_GPSMOTIONSTARTKPH:
					dmtp.setMotionStartKph((String) msg.obj);
					break;
				case MSG_SET_GPSMOTIONINMOTION:
					dmtp.setMotionInMotion((String) msg.obj);
					break;
				case MSG_SET_GPSMOTIONSTOP:
					dmtp.setMotionStop((String) msg.obj);
					break;
				case MSG_SET_GPSMOTIONDORMANT:
					dmtp.setMotionDormant((String) msg.obj);
					break;


				default:
					super.handleMessage(msg);
					break;
			}
		}
	}
	
	
	
	@Override
	public IBinder onBind(Intent intent) {
		Toast.makeText(getApplicationContext(), "AndroDMTP binded", Toast.LENGTH_SHORT).show();
		return mMessenger.getBinder();
	}
	
	@Override
	public void onCreate() {
		//Toast.makeText(this, "AndroDMTP instantiated", Toast.LENGTH_SHORT).show();
		
		LocationManager locationManager = (LocationManager)this.getSystemService(Context.LOCATION_SERVICE);
        locationListener = new AndroDMTPLocationListener();
        
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
               
        dmtp = AndroDMTP.getInstance((AndroDMTPLocationListener) locationListener);
        
        
		
	}
	
	@Override
	public void onDestroy(){
		stopSelf();
	}
	  
	public int onStartCommand(Intent intent, int flags, int startId) {
		Toast.makeText(this, "service starting", Toast.LENGTH_SHORT).show();
		// If we get killed, after returning from here, restart
		return 0;
	}

}
