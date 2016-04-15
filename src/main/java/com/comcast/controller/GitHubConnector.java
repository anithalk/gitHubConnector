package com.comcast.controller;

import java.io.IOException;
import java.util.Collection;

import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.NoHeadException;
import org.eclipse.jgit.lib.Ref;

import com.comcast.exception.ValidationException;

public interface GitHubConnector {

	/**
	 * getBranches returns all the branches for the given Repo
	 * @param repoLink - Repo url
	 * @return - Collection of branches
	 * @throws ValidationException
	 */
	Collection<Ref> getBranches(String repoLink) throws ValidationException;

	Collection<Ref> getTags(String repoLink) throws ValidationException;

	Collection<Ref> getBranchWithNoFile(String file,String workingDir) throws IOException, NoHeadException, GitAPIException;

	Collection<Ref> getTagswithNoFile(String file,String workingDir) throws IOException, NoHeadException, GitAPIException;

}
