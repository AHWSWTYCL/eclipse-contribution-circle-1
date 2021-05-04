/*
 * Created on 2021-4-24
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package org.eclipse.contribution.junit;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.part.ViewPart;

/**
 * @author Cocoa
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ResultView extends ViewPart {
	
	private ITestRunListener listener;
	private RerunTestAction rerunAction;
	private Control control;
	
	private class RerunTestAction extends Action {
		private RerunTestAction() {
			setText("Re-run");
		}
		
		public void run() {
			rerunTest();
		}

		/**
		 * 
		 */
		private void rerunTest() {
			// TODO Auto-generated method stub
			System.out.println("==============> rerun test...");
		}
	}
	
	private class Listener implements ITestRunListener {

		private boolean success;
		
		/* (non-Javadoc)
		 * @see org.eclipse.contribution.junit.ITestRunListener#testsStarted(int)
		 */
		public void testsStarted(int testCount) {
			// TODO Auto-generated method stub
			success = true;
		}

		/* (non-Javadoc)
		 * @see org.eclipse.contribution.junit.ITestRunListener#testsFinished()
		 */
		public void testsFinished() {
			// TODO Auto-generated method stub
			if (success) {
				Display display = control.getDisplay();
				Color green = display.getSystemColor(SWT.COLOR_GREEN);
				control.setBackground(green);
			}
		}

		/* (non-Javadoc)
		 * @see org.eclipse.contribution.junit.ITestRunListener#testStarted(java.lang.String, java.lang.String)
		 */
		public void testStarted(String klass, String method) {
			// TODO Auto-generated method stub
			
		}

		/* (non-Javadoc)
		 * @see org.eclipse.contribution.junit.ITestRunListener#testFailed(java.lang.String, java.lang.String, java.lang.String)
		 */
		public void testFailed(String klass, String method, String trace) {
			// TODO Auto-generated method stub
			Display display = control.getDisplay();
			Color red = display.getSystemColor(SWT.COLOR_RED);
			control.setBackground(red);
			success = false;
		}
		
	}
	

	/* (non-Javadoc)
	 * @see org.eclipse.ui.part.WorkbenchPart#createPartControl(org.eclipse.swt.widgets.Composite)
	 */
	public void createPartControl(Composite parent) {
		// TODO Auto-generated method stub
		listener = new Listener();
		JUnitPlugin.getPlugin().addTestListener(listener);
		control = new Label(parent, SWT.NONE);
		createContextMenu();
		rerunAction = new RerunTestAction();
	}

	/**
	 * 
	 */
	private void createContextMenu() {
		// TODO Auto-generated method stub
		MenuManager menuManager = new MenuManager();
		menuManager.setRemoveAllWhenShown(true);
		menuManager.addMenuListener(new IMenuListener() {

			public void menuAboutToShow(IMenuManager manager) {
				// TODO Auto-generated method stub
				fillContextMenu(manager);
			}});
		Menu menu = menuManager.createContextMenu(control);
		control.setMenu(menu);
		getSite().registerContextMenu(menuManager, getSite().getSelectionProvider());
	}

	/**
	 * @param manager
	 */
	protected void fillContextMenu(IMenuManager menu) {
		// TODO Auto-generated method stub
		menu.add(rerunAction);
		menu.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
		menu.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS + 
				"-end"));
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.part.WorkbenchPart#setFocus()
	 */
	public void setFocus() {
		// TODO Auto-generated method stub
		control.setFocus();
	}

	public void dispose() {
		if (listener != null)
			JUnitPlugin.getPlugin().removeTestListener(listener);
		
		listener = null;
	}

	/**
	 * @return
	 */
	public Control getControl() {
		// TODO Auto-generated method stub
		return control;
	}
	/**
	 * @return Returns the listener.
	 */
	public ITestRunListener getListener() {
		return listener;
	}
	/**
	 * @param listener The listener to set.
	 */
	public void setListener(ITestRunListener listener) {
		this.listener = listener;
	}
	/**
	 * @param control The control to set.
	 */
	public void setControl(Control control) {
		this.control = control;
	}
}
