package com.comcast.util;

import java.io.File;
import java.io.IOException;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.PullResult;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.TransportException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;


/**
 * This is a utility class that will clone the repo if not present
 * or pulls the latest if the rerpo is present
 * @author Anitha Krishnasamy
 *
 */
public class CloneRemoteRepository {

	private static final String REMOTE_URL = "https://github.com/ruby/ruby.git";

	public static void repoClone() throws IOException, InvalidRemoteException, TransportException, GitAPIException {
		// prepare a new folder for the cloned repository
		File localPath = new File("testRuby");
		boolean directoryExist = localPath.exists();
		localPath.delete();
		// then clone
		System.out.println("Cloning from " + REMOTE_URL + " to " + localPath);
		FileRepositoryBuilder builder = new FileRepositoryBuilder();
		Repository repository = builder.setWorkTree(localPath).readEnvironment().build();
		Git git = new Git(repository);
		if (!directoryExist) {
			try (Git result = git.cloneRepository().setURI(REMOTE_URL).setDirectory(localPath).call()) {
				// Note: the call() returns an opened repository already which
				// needs to be closed to avoid file handle leaks!
				System.out.println("Having repository: " + result.getRepository().getDirectory());
			}
		} else {
			PullResult pullresult=git.pull().call();
			// Note: the call() returns an opened repository already which needs
			// to be closed to avoid file handle leaks!
			System.out.println("pulled repository: " + pullresult.isSuccessful());

		}
	}

}
