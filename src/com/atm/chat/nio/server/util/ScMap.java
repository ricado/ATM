package com.atm.chat.nio.server.util;

import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.atm.chat.nio.server.ReadSelector;


public interface ScMap {
	
	public static HashMap<String, SocketChannel> map = 
			new HashMap<String, SocketChannel>();
	
	public static HashMap<String,SelectionKey> mapkey =
			new HashMap<String,SelectionKey>();
	
	public static List<Selector> list = new ArrayList<Selector>();
	
	public static List<Integer> channelNums = new ArrayList<Integer>(); 
	
	public static List<ReadSelector> readSelectors = new ArrayList<ReadSelector>();
}
