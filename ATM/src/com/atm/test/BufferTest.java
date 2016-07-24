package com.atm.test;

import java.nio.ByteBuffer;

public class BufferTest {
	public static void main(String[] args) {
		BufferTest test= new BufferTest();
		test.println();
	}
	public void println(){
		ByteBuffer buffer = ByteBuffer.allocate(6);
		buffer.putInt(13);
		String userId = "10001";
		String userPwd = "ccy";
		
		buffer.put(userId.getBytes());
		buffer.put(userPwd.getBytes());
		
		System.out.println(buffer.getInt()+"");
	}
}
