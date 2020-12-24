package com.lunatech.security.blocking;

import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.net.UnknownHostException;
import com.google.common.net.InetAddresses;

public class URLValidator {

	
	/**
	 * check if path is valid url
	 * host can't refer to any address for the current machine
	 * 
	 * @param path windows shared path
	 * @return
	 */
	public boolean isValidURL(String url) {

		try {
			URL urlInfo = new URL(url);
			return !isThisMyIpAddress( urlInfo.getHost()) ;
		} catch (MalformedURLException e) {
			return false;
		}
		
	}
	/**
	 * check if path is valid windows shared path
	 * host can't refer to any address for the current machine
	 * 
	 * @param path windows shared path
	 * @return
	 */
	public boolean isValidSharedURL(String url) {

		System.out.println("path:" + url);
		if (url.startsWith("\\\\")) {
			String ipPath = url.substring(2);
			if (ipPath.length() >= 2) {
				int end = ipPath.indexOf('\\');
				if (end >= 0) {
					ipPath = ipPath.substring(0, end);
					return InetAddresses.isInetAddress(ipPath) &&  ( !isThisMyIpAddress(ipPath) );
				} else {
					return InetAddresses.isInetAddress(ipPath) &&  ( !isThisMyIpAddress(ipPath) );
				}
			}
		}
		return false;

	}

	private boolean isLocalAddress(String ip) {

		switch (ip) {
		case "localhost":
			return true;
		case "::1":
			return true;
		case "127.0.0.1":
			return true;
		default:
			return false;
		}
	}
	
	public static boolean isThisMyIpAddress(String ip) {
		
		InetAddress addr;
		try {
			addr = InetAddress.getByName(ip);
		} catch (UnknownHostException e1) {
			return false;
		}
		
	    // Check if the address is a valid special local or loop back
	    if (addr.isAnyLocalAddress() || addr.isLoopbackAddress())
	        return true;

	    // Check if the address is defined on any interface
	    try {
	        return NetworkInterface.getByInetAddress(addr) != null;
	    } catch (SocketException e) {
	        return false;
	    }
	}
	
	/*
	private boolean isLocalNetwork(String ip) {
		SubnetInfo subnet = (new SubnetUtils("10.10.10.0", "255.255.255.128")).getInfo();
		boolean test = subnet.isInRange("10.10.10.10");
	}
	*/

}
