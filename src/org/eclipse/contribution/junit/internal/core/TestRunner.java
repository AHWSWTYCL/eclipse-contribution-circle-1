package org.eclipse.contribution.junit.internal.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;

import org.eclipse.contribution.junit.JUnitPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.debug.core.Launch;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.launching.IVMInstall;
import org.eclipse.jdt.launching.IVMRunner;
import org.eclipse.jdt.launching.JavaRuntime;
import org.eclipse.jdt.launching.SocketUtil;
import org.eclipse.jdt.launching.VMRunnerConfiguration;


/**
 * @author Cocoa
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class TestRunner {
	static final String MAIN_CLASS = 
		"org.eclipse.contribution.junit.internal.core.SocketTestRunner";
		
	private int port;
	private IJavaProject project;
	private BufferedReader reader;
	
	public TestRunner() {
		
	}
	
	public TestRunner(IJavaProject project) {
		this.project = project;
	}
	
	public void run(IType type) throws CoreException {
		project = type.getJavaProject();
		run(new IType[] {type});
	}

	/**
	 * @param project2
	 * @param types
	 */
	public void run(IType[] classes) throws CoreException {
		// TODO Auto-generated method stub
		IVMInstall vmInstall = getVMInstall();
		if (vmInstall == null) {
			return;
		}
		
		IVMRunner vmRunner = vmInstall.getVMRunner(
		ILaunchManager.RUN_MODE);
		if (vmRunner == null)
			return;
			
		String[] classPath = computeClassPath();
		VMRunnerConfiguration vmConfig =
			new VMRunnerConfiguration(MAIN_CLASS, classPath);
		String[] args = new String[classes.length + 1];
		port = SocketUtil.findUnusedLocalPort("localhost", 10000, 15000);
		args[0] = Integer.toString(port);
		
		for(int i = 0; i < classes.length; i++) {
			args[i + 1] = classes[i].getFullyQualifiedName();
		}
		
		vmConfig.setProgramArguments(args);
		
		ILaunch launch = new Launch(null, ILaunchManager.RUN_MODE, null);
		vmRunner.run(vmConfig, launch, null);
		DebugPlugin.getDefault().getLaunchManager().addLaunch(launch);
		connect();
	}
	
	private String[] computeClassPath() throws CoreException {
		String[] defaultPath =
			JavaRuntime.computeDefaultRuntimeClassPath(project);
		
		String[] classPath = new String[defaultPath.length + 2];
		System.arraycopy(defaultPath, 0, classPath, 2, defaultPath.length);
		JUnitPlugin plugin = JUnitPlugin.getPlugin();
		URL url = plugin.getDescriptor().getInstallURL();
		try {
				classPath[0] = Platform.asLocalURL(
				new URL(url, "bin")).getFile();
				classPath[1] = Platform.asLocalURL(
				new URL(url, "contribjunit.jar")).getFile();
			} catch (IOException e) {
				IStatus status = new Status(IStatus.ERROR,
				plugin.getDescriptor().getUniqueIdentifier(),
				IStatus.OK, "Could not determine path", e);
				throw new CoreException(status);
			}
			
		return classPath;
	}
	
	private void connect() {
		try{
			ServerSocket server;
			server = new ServerSocket(port);
			try {
				Socket socket = server.accept();
				try {
					readMessage(socket);
				} finally {
					socket.close();
				}
			} finally {
				server.close();
			}
		} catch (IOException e) {
			// TODO: unhandled exception
			e.printStackTrace();
		}
	}

	/**
	 * @param socket
	 */
	private void readMessage(Socket socket) throws IOException {
		// TODO Auto-generated method stub
		reader = new BufferedReader(
			new InputStreamReader(socket.getInputStream()));
		try {
			String line = null;
			while ((line = reader.readLine()) != null) {
				parseMessage(line);	
			}
		} finally {
			reader.close();
		}
	}

	/**
	 * @param line
	 */
	private void parseMessage(String line) {
		// TODO Auto-generated method stub
		JUnitPlugin plugin = JUnitPlugin.getPlugin();
		if (line.startsWith("starting tests ")) {
			int start = "starting tests ".length();
			int count = Integer.parseInt(line.substring(start));
			plugin.fireTestsStarted(project, count);
		}
		
		if (line.startsWith("ending tests")) {
			plugin.fireTestsFinished(project);
		}
		
		if (line.startsWith("starting test ")) {
			int start = "starting test ".length();
			String method = line.substring(start, line.indexOf("("));
			String klass = line.substring(line.indexOf("(") + 1, 
				line.indexOf(")"));
			plugin.fireTestStarted(project, klass, method);
		}
		
		if (line.startsWith("failing test ")) {
			int start = "failing test ".length();
			String method = line.substring(start, line.indexOf("("));
			String klass = line.substring(line.indexOf("(") + 1,
				line.indexOf(")"));
			StringWriter buffer = new StringWriter();
			PrintWriter writer = new PrintWriter(buffer);
			String frame = null;
			try {
				while ((frame = reader.readLine()) != null &&
					(!frame.equals("END TRACE"))) {
						writer.println(frame);
					} 
			}catch (IOException e) {
				e.printStackTrace();
			}
			
			String trace = buffer.getBuffer().toString();
			plugin.fireTestFailed(project, klass, method, trace);
		}
	}
	
	private IVMInstall getVMInstall() throws CoreException {
		IVMInstall vmInstall = JavaRuntime.getVMInstall(project);
		if (vmInstall == null) 
			vmInstall = JavaRuntime.getDefaultVMInstall();
			
		return vmInstall;
	}
}
