/*
 * Created on 2021-5-7
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package org.eclipse.contribution.junit;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jdt.core.Flags;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.ITypeHierarchy;
import org.eclipse.jdt.core.JavaModelException;

/**
 * @author Cocoa
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class TestSearcher {

	/**
	 * @param javaProject
	 * @param object
	 * @return
	 */
	public IType[] findAll(IJavaProject project, IProgressMonitor pm) 
		throws JavaModelException {
		// TODO Auto-generated method stub
		IType[] candidates = getAllTestCaseSubclasses(project, pm);
		return collectTestsInProject(candidates, project);
	}

	/**
	 * @param candidates
	 * @param project
	 * @return
	 */
	private IType[] collectTestsInProject(IType[] candidates, IJavaProject project) {
		// TODO Auto-generated method stub
		List result = new ArrayList();
		for (int i = 0; i < candidates.length; i++) {
			try {
				if (isTestInProject(candidates[i], project))
					result.add(candidates[i]);
			} catch (JavaModelException e) {
				// Fall through
			}
		}
		
		return (IType[]) result.toArray(new IType[result.size()]);
	}

	/**
	 * @param type
	 * @param project
	 * @return
	 */
	private boolean isTestInProject(IType type, IJavaProject project) 
	 	throws JavaModelException {
		// TODO Auto-generated method stub
		IResource resource = type.getUnderlyingResource();
		if (resource == null) 
			return false;
		if ( !resource.getProject().equals(project.getProject()))
			return false;
		
		return !Flags.isAbstract(type.getFlags());
	}

	/**
	 * @param project
	 * @param pm
	 * @return
	 */
	private IType[] getAllTestCaseSubclasses(IJavaProject project, IProgressMonitor pm)
		throws JavaModelException {
		// TODO Auto-generated method stub
		IType testCase = project.findType("junit.framework.TestCase");
		if (testCase == null) 
			return new IType[0];
		ITypeHierarchy hierarchy = testCase.newTypeHierarchy(project, pm);
		return hierarchy.getAllSubtypes(testCase);
	}

}
