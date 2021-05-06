/*
 * Created on 2021-4-17
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package org.eclipse.contribution.junit.internal.core;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import junit.framework.AssertionFailedError;
import junit.framework.Test;
import junit.framework.TestListener;
import junit.framework.TestResult;
import junit.framework.TestSuite;

/**
 * @author Cocoa
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class SocketTestRunner implements TestListener {
	private int port;
	private Socket socket;
	private PrintWriter writer;
	
	public static void main(String[] args) {
		new SocketTestRunner().runTests(args);
	}
	/**
	 * @param args
	 */
	private void runTests(String[] args) {
		// TODO Auto-generated method stub
		port = Integer.parseInt(args[0]);
		openClientSocket();
		try {
			TestSuite suite = new TestSuite();
			for (int i = 1; i < args.length; i++) {
				suite.addTestSuite(Class.forName(args[i]));
			}
			
			writer.println("starting tests " + suite.countTestCases());
			TestResult result = new TestResult();
			result.addListener(this);
			suite.run(result);
			writer.println("ending tests");
		} catch (ClassNotFoundException e) {
			System.out.println("Not a class: " + args[1]);
		} finally {
			closeClientSocket();
		}
			
	}
	/**
	 * 
	 */
	private void closeClientSocket() {
		// TODO Auto-generated method stub
		writer.close();
		try {
			socket.close();
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 
	 */
	private void openClientSocket() {
		// TODO Auto-generated method stub
		for (int i = 0; i < 10; i++) {
				try {
					socket = new Socket("localhost", port);
					writer = new PrintWriter(socket.getOutputStream(), true);
					return;
				} catch (UnknownHostException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				try {
					Thread.sleep(500);
				} catch (InterruptedException el) {
					
				}
			}
	}
	
	public void addError(Test test, Throwable t) {
		writer.println("failing test " + test);
		t.printStackTrace();
		writer.println("END TRACE");
	}
	
	public void addFailure(Test test, AssertionFailedError t) {
		addError(test, t);
	}
	
	public void endTest(Test test) {

	}
	
	public void startTest(Test test) {
		writer.println("starting test " + test);
	}
}
