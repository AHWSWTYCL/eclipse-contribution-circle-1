/*
 * Created on 2021-5-5
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package org.eclipse.contribution.junit;

import org.eclipse.core.resources.IMarker;
import org.eclipse.ui.IMarkerResolution;
import org.eclipse.ui.IMarkerResolutionGenerator;

/**
 * @author Cocoa
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class RerunMarkerResolutionGenerator implements IMarkerResolutionGenerator {

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IMarkerResolutionGenerator#getResolutions(org.eclipse.core.resources.IMarker)
	 */
	public IMarkerResolution[] getResolutions(IMarker marker) {
		// TODO Auto-generated method stub
		IMarkerResolution resolution = new IMarkerResolution() {

			public String getLabel() {
				// TODO Auto-generated method stub
				return "Re-run test";
			}

			public void run(IMarker marker) {
				// TODO Auto-generated method stub
				
			}
			
		};
		
		return new IMarkerResolution[] {resolution};
	}

}
