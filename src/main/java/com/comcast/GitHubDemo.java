package com.comcast;

import java.io.IOException;
import java.util.Collection;
import java.util.Properties;

import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Ref;

import com.comcast.connector.impl.GitHubConnectorImpl;
import com.comcast.controller.GitHubConnector;
import com.comcast.exception.ValidationException;

/**
 * This is the executable class, which invokes all the services
 * 
 * @author Anitha Krishnasamy
 *
 */
public class GitHubDemo {
	final static Properties properties = new Properties();
	public static void main(String[] args) throws IOException, GitAPIException {
		properties.load(GitHubDemo.class.getResourceAsStream("/config.properties"));
		// Repo -
		String repo = properties.getProperty("repoURL");
		String fileName = properties.getProperty("fileName");
		String workingDir = properties.getProperty("workingDir");
		
		System.out.println("*****************************************************************");
		System.out.println("**repo*********************************************************"+repo);
		System.out.println("**fileName*********************************************************"+fileName);
		System.out.println("**workingDir*********************************************************"+workingDir);
		System.out.println("*****************************************************************");

		Collection<Ref> remoteRefs = null;
		GitHubConnector gitHubConnector = new GitHubConnectorImpl();
		Collection<Ref> remoteBranchRefs;
		try {
			remoteBranchRefs = gitHubConnector.getBranches(repo);

			if (null != remoteBranchRefs) {
				System.out.println("************************************************************************");
				System.out.println("--------------  Branches in the Repository   ----------------------------");
				System.out.println("--------------  Number of Branches::" + remoteBranchRefs.size()
						+ "    --------------------------------");
				System.out.println("************************************************************************");
				System.out.println(remoteBranchRefs.toString());
			}
		} catch (ValidationException e) {
			System.out.println(e.getMessage());
		}

		try {
			Collection<Ref> remoteTagRefs = gitHubConnector.getTags(repo);
			System.out.println("*****************************************************************************");
			System.out.println("----------------------    Tags in the Repository   ---------------------------");
			System.out.println("-----------------------    Number of Tags::" + remoteTagRefs.size()
					+ "    --------------------------------");
			System.out.println("******************************************************************************");
			System.out.println(remoteTagRefs.toString());

		} catch (ValidationException e) {
			System.out.println(e.getMessage());
		}

		Collection<Ref> branchWithNoFile = gitHubConnector.getBranchWithNoFile(fileName, workingDir);
		System.out.println("*****************************************************************************");
		System.out.println("----------------------    Branch With File Not Present    ---------------------------");
		System.out.println("******************************************************************************");
		for (Ref branchRef : branchWithNoFile) {
			System.out.println(
					"Branch: " + branchRef + " " + branchRef.getName() + " " + branchRef.getObjectId().getName());
		}

		
		
		Collection<Ref> tagWithNoFile = gitHubConnector.getTagswithNoFile(fileName, workingDir);
		System.out.println("*****************************************************************************");
		System.out.println("----------------------    Tag With File Not Present    ---------------------------");
		System.out.println("******************************************************************************");
		for (Ref tagRef : tagWithNoFile) {
			System.out.println("Tag: " + tagRef + " " + tagRef.getName() + " " + tagRef.getObjectId().getName());
		}
	}

}
