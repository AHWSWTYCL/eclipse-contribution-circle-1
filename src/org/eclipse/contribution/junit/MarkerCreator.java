/*
 * Created on 2021-5-4
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package org.eclipse.contribution.junit;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

import junit.runner.BaseTestRunner;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.ISourceRange;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;

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
		try {
			project.getProject().deleteMarkers(
					"org.eclipse.contribution.junit.failure",
					false,
					IResource.DEPTH_INFINITE);
		} catch (CoreException e) {
			// TODO Log later
		}
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
	public void testFailed(IJavaProject testProject, String klass, final String method,
			final String trace) {
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
		
		final IType finalType = type;
		
		try {
			final IResource resource = type.getUnderlyingResource();
			IWorkspaceRunnable runnable = new IWorkspaceRunnable() {

				public void run(IProgressMonitor monitor) throws CoreException {
					// TODO Auto-generated method stub
					IMarker marker = resource.createMarker(
					"org.eclipse.contribution.junit.failure");
			IMethod testMethod = finalType.getMethod(method, new String[0]);
			setMarkerAttributes(marker, testMethod, trace);
				}
				
			};
			resource.getWorkspace().run(runnable, null);
		} catch(CoreException e) {
			
		}
	}
	/**
	 * @param marker
	 * @param testMethod
	 * @param trace
	 */
	private void setMarkerAttributes(IMarker marker, IMethod testMethod, String trace) 
		throws JavaModelException, CoreException {
		// TODO Auto-generated method stub
		ISourceRange range = testMethod.getSourceRange();
		Map map = new HashMap(5);
		map.put(IMarker.CHAR_START, new Integer(range.getOffset()));
		map.put(IMarker.CHAR_END, new Integer(range.getOffset() + 
				range.getLength()));
		map.put(IMarker.SEVERITY, new Integer(IMarker.SEVERITY_ERROR));
		map.put(IMarker.MESSAGE, extractMessage(trace));
		map.put("trace", trace);
		marker.setAttributes(map);
	}
	/**
	 * @param trace
	 * @return
	 */
	private String extractMessage(String trace) {
		// TODO Auto-generated method stub
		String filteredTrace = BaseTestRunner.getFilteredTrace(trace);
		BufferedReader br = new BufferedReader(
				new StringReader(filteredTrace));
		String line, message = trace;
		try {
			if ((line = br.readLine()) != null) {
				message = line;
				if ((line = br.readLine()) != null) {
					message += " - " + line;
				}
			}
				return message.replace('\t', ' ');
		} catch (Exception IOException) {
				
		}
		
		return message;
	}

}
