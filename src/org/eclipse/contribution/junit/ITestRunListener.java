/*
 * Created on 2021-4-17
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package org.eclipse.contribution.junit;

/**
 * @author Cocoa
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public interface ITestRunListener {
	
	void testsStarted(int testCount);
	void testsFinished();
	void testStarted(String klass, String method);
	void testFailed(String klass, String method, String trace);

}
