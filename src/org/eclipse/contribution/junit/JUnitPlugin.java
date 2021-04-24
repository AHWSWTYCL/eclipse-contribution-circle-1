/*
 * Created on 2021-4-17
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package org.eclipse.contribution.junit;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.contribution.junit.internal.core.TestRunner;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IPluginDescriptor;
import org.eclipse.core.runtime.IPluginRegistry;
import org.eclipse.core.runtime.ISafeRunnable;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Plugin;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IType;

/**
 * @author Cocoa
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class JUnitPlugin extends Plugin {

	private static JUnitPlugin instance;
	private List listeners = new ArrayList();
	private static final String listenerId =
		"org.eclipse.contribution.junit.listeners";
	/**
	 * @param descriptor
	 */
	public JUnitPlugin(IPluginDescriptor descriptor) {
		super(descriptor);
		// TODO Auto-generated constructor stub
		instance = this;
	}
	
	public static JUnitPlugin getPlugin() {
		return instance;
	}
	
	public void run(IType type) throws CoreException {
		new TestRunner().run(type);
	}
	
	public void run(IType[] types, IJavaProject project) throws CoreException {
		new TestRunner(project).run(types);
	}

	/**
	 * @param listener
	 */
	public void addTestListener(ITestRunListener listener) {
		// TODO Auto-generated method stub
		listeners.add(listener);
	}

	/**
	 * @param listener
	 */
	public void removeTestListener(ITestRunListener listener) {
		// TODO Auto-generated method stub
		listeners.remove(listener);
	}

	/**
	 * @param count
	 */
	public void fireTestsStarted(final int count) {
		// TODO Auto-generated method stub
		for (final Iterator all = getListeners().iterator(); all.hasNext();) {
			final ITestRunListener each = (ITestRunListener) all.next();
			ISafeRunnable runnable = new ISafeRunnable() {

				public void handleException(Throwable exception) {
					// TODO Auto-generated method stub
					all.remove();
				}

				public void run() throws Exception {
					// TODO Auto-generated method stub
					each.testsStarted(count);
				}
				
			};
			Platform.run(runnable);
		}
	}

	/**
	 * 
	 */
	private List getListeners() {
		// TODO Auto-generated method stub
		if (listeners == null) {
			listeners = computeListeners();
		}
		
		return listeners;
	}

	/**
	 * @return
	 */
	private List computeListeners() {
		// TODO Auto-generated method stub
		IPluginRegistry registry = Platform.getPluginRegistry();
		IExtensionPoint extensionPoint = 
			registry.getExtensionPoint(listenerId);
		IExtension[] extensions = extensionPoint.getExtensions();
		ArrayList results = new ArrayList();
		for (int i = 0; i < extensions.length; i++) {
			IConfigurationElement[] elements = 
				extensions[i].getConfigurationElements();
			for (int j = 0; j < elements.length; j++) {
				try {
					Object listener = 
						elements[j].createExecutableExtension("class");
					if (listener instanceof ITestRunListener) {
						results.add(listener);
					}
				} catch (CoreException e) {
					e.printStackTrace();
				}
			}
		}
		
		return results;
	}

	/**
	 * 
	 */
	public void fireTestsFinished() {
		// TODO Auto-generated method stub
		for (final Iterator all = getListeners().iterator(); all.hasNext();) {
			final ITestRunListener each = (ITestRunListener) all.next();
			
			ISafeRunnable runnable = new ISafeRunnable() {

				public void handleException(Throwable exception) {
					// TODO Auto-generated method stub
					all.remove();
				}

				public void run() throws Exception {
					// TODO Auto-generated method stub
					each.testsFinished();
				}
				
			};
			Platform.run(runnable);
		}
	}

	/**
	 * @param klass
	 * @param method
	 */
	public void fireTestStarted(final String klass, final String method) {
		// TODO Auto-generated method stub
		for (final Iterator all = getListeners().iterator(); all.hasNext();) {
			final ITestRunListener each = (ITestRunListener) all.next();
			ISafeRunnable runnable = new ISafeRunnable() {

				public void handleException(Throwable exception) {
					// TODO Auto-generated method stub
					all.remove();
				}

				public void run() throws Exception {
					// TODO Auto-generated method stub
					each.testStarted(klass, method);
				}
				
			};
			Platform.run(runnable);

		}
	}

	/**
	 * @param klass
	 * @param method
	 * @param trace
	 */
	public void fireTestFailed(final String klass, final String method, final String trace) {
		// TODO Auto-generated method stub
		for (final Iterator all = getListeners().iterator(); all.hasNext();) {
			final ITestRunListener each = (ITestRunListener) all.next();
			
			ISafeRunnable runnable = new ISafeRunnable() {

				public void handleException(Throwable exception) {
					// TODO Auto-generated method stub
					all.remove();
				}

				public void run() throws Exception {
					// TODO Auto-generated method stub
					each.testFailed(klass, method, trace);
				}
				
			};
			Platform.run(runnable);

		}
	}

	/**
	 * @param project
	 * @param count
	 */
	public void fireTestsStarted(IJavaProject project, int count) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * @param project
	 */
	public void fireTestsFinished(IJavaProject project) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * @param project
	 * @param klass
	 * @param method
	 */
	public void fireTestStarted(IJavaProject project, String klass, String method) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * @param project
	 * @param klass
	 * @param method
	 * @param trace
	 */
	public void fireTestFailed(IJavaProject project, String klass, String method, String trace) {
		// TODO Auto-generated method stub
		
	}

}
