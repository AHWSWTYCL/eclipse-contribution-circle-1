/*
 * Created on 2021-5-4
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package org.eclipse.contribution.junit;

import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IType;

/**
 * @author Cocoa
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class MarkerCreator implements ITestRunListener {

	private IJavaProject project;
	
	
	/**
	 * @param project
	 */
	public MarkerCreator(IJavaProject project) {
		this.project = project;
	}
	/* (non-Javadoc)
	 * @see org.eclipse.contribution.junit.ITestRunListener#testsStarted(org.eclipse.jdt.core.IJavaProject, int)
	 */
	public void testsStarted(IJavaProject project, int testCount) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.eclipse.contribution.junit.ITestRunListener#testsFinished(org.eclipse.jdt.core.IJavaProject)
	 */
	public void testsFinished(IJavaProject project) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.eclipse.contribution.junit.ITestRunListener#testStarted(org.eclipse.jdt.core.IJavaProject, java.lang.String, java.lang.String)
	 */
	public void testStarted(IJavaProject project, String klass, String method) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.eclipse.contribution.junit.ITestRunListener#testFailed(org.eclipse.jdt.core.IJavaProject, java.lang.String, java.lang.String, java.lang.String)
	 */
	public void testFailed(IJavaProject testProject, String klass, String method,
			String trace) {
		// TODO Auto-generated method stub
		if ( !project.equals(testProject))
			return;
		IType type = null;
		try {
			type = project.findType(klass);
		} catch (JavaModelException e) { // Fall through
			
		}
		
		if (type == null)
			return; // TODO: Log later
		try {
			IResoure
		}
	}

}
