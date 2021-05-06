/*
 * Created on 2021-4-16
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package org.eclipse.contribution.junit;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IType;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

/**
 * @author Cocoa
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class RunTestAction implements IObjectActionDelegate {

	ISelection selection;
	
	public static class Listener implements ITestRunListener {
		private boolean passed = true;

		/* (non-Javadoc)
		 * @see org.eclipse.contribution.junit.ITestRunListener#testsStarted(int)
		 */
		public void testsStarted(IJavaProject project, int testCount) {
			// TODO Auto-generated method stub
			
		}

		/* (non-Javadoc)
		 * @see org.eclipse.contribution.junit.ITestRunListener#testsFinished()
		 */
		public void testsFinished(IJavaProject project) {
			// TODO Auto-generated method stub
			String message = passed ? "Pass" : "Fail";
			MessageDialog.openInformation(null, "Test Results", message);
		}

		/* (non-Javadoc)
		 * @see org.eclipse.contribution.junit.ITestRunListener#testStarted(java.lang.String, java.lang.String)
		 */
		public void testStarted(IJavaProject project, String klass, String method) {
			// TODO Auto-generated method stub
			
		}

		/* (non-Javadoc)
		 * @see org.eclipse.contribution.junit.ITestRunListener#testFinished(java.lang.String, java.lang.String, java.lang.String)
		 */
		public void testFailed(IJavaProject project, String klass, String method, String trace) {
			// TODO Auto-generated method stub
			passed = false;
		}
	}
	/* (non-Javadoc)
	 * @see org.eclipse.ui.IObjectActionDelegate#setActivePart(org.eclipse.jface.action.IAction, org.eclipse.ui.IWorkbenchPart)
	 */
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IActionDelegate#run(org.eclipse.jface.action.IAction)
	 */
	public void run(IAction action) {
		// TODO Auto-generated method stub
		if ( !(selection instanceof IStructuredSelection))
			return;
		IStructuredSelection structured = 
			(IStructuredSelection) selection;
			
		IType type = (IType) structured.getFirstElement();
		
//		ITestRunListener listener = new Listener();
//		JUnitPlugin.getPlugin().addTestListener(listener);
		try {
			JUnitPlugin.getPlugin().run(type);
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		JUnitPlugin.getPlugin().removeTestListener(listener);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IActionDelegate#selectionChanged(org.eclipse.jface.action.IAction, org.eclipse.jface.viewers.ISelection)
	 */
	public void selectionChanged(IAction action, ISelection selection) {
		// TODO Auto-generated method stub
		this.selection = selection;
	}

}
