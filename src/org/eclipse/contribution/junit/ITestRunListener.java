/*
 * Created on 2021-4-17
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package org.eclipse.contribution.junit;

import org.eclipse.jdt.core.IJavaProject;

/**
 * @author Cocoa
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public interface ITestRunListener {
	
	void testsStarted(IJavaProject project, int testCount);
	void testsFinished(IJavaProject project);
	void testStarted(IJavaProject project, String klass, String method);
	void testFailed(IJavaProject project,String klass, String method, String trace);

}
