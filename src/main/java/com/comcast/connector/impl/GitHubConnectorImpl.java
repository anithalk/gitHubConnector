package com.comcast.connector.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.ListBranchCommand.ListMode;
import org.eclipse.jgit.api.LogCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.NoHeadException;
import org.eclipse.jgit.api.errors.TransportException;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.treewalk.filter.PathFilter;

import com.comcast.controller.GitHubConnector;
import com.comcast.exception.ValidationException;
import com.comcast.util.CloneRemoteRepository;

/**
 * 
 * @author Anitha Krishnasamy This class is the actual implementation of the
 *         services offered by this project
 *
 */
public class GitHubConnectorImpl implements GitHubConnector {

	/**
	 * getBranches will fetch all the Branches for a repo
	 * 
	 * @param -
	 *            repoLink - pass the repo for whihc the branches are to be
	 *            fetched
	 */
	public Collection<Ref> getBranches(String repoLink) throws ValidationException {
		Collection<Ref> remoteRefs = null;
		if (null != repoLink) {
			try {
				remoteRefs = Git.lsRemoteRepository().setHeads(true).setRemote(repoLink).call();

			} catch (GitAPIException e) {
				e.printStackTrace();
				throw new ValidationException("Repo is Null.Provide proper repo URL");
			} /*catch (TransportException e) {
				e.printStackTrace();
				throw new ValidationException("Repo is Null.Provide proper repo URL");
			} catch (GitAPIException e) {
				e.printStackTrace();
				throw new ValidationException("Repo is Null.Provide proper repo URL");
			}*/
		} else {
			throw new ValidationException("Repo is Null.Provide proper repo URL");
		}
		return remoteRefs;
	}

	/**
	 * getTag will fetch all the Tags for a repo
	 * 
	 * @param -
	 *            repoLink - pass the repo for whihc the tags are to be fetched
	 */
	public Collection<Ref> getTags(String repoLink) throws ValidationException {
		Collection<Ref> remoteRefs = null;
		if (null != repoLink) {
			try {
				remoteRefs = Git.lsRemoteRepository().setHeads(false).setTags(true).setRemote(repoLink).call();
			} catch (InvalidRemoteException e) {
				e.printStackTrace();
			} catch (TransportException e) {
				e.printStackTrace();
			} catch (GitAPIException e) {
				e.printStackTrace();
			}
		} else {
			throw new ValidationException("Repo is Null.Provide proper repo URL");
		}
		return remoteRefs;
	}

	
	
	/**
	 * getBranchWithNoFile will fetch all the Branches for a repo
	 * which will not have the file specified in the parameter
	 * @param - fileExtension , this file should not be present in the branches
	 *  @param - dir, where the repo will be cloned/pulled
	 */
	public Collection<Ref> getBranchWithNoFile(String fileExtension,String dir) throws IOException, NoHeadException, GitAPIException {
		System.out.println("Searching for Branches with no "+fileExtension +".....................");
 		CloneRemoteRepository.repoClone();
		System.out.println("dir");
		File gitWorkDir = new File(dir);
		Git git = Git.open(gitWorkDir);
		Repository repository = git.getRepository();
		List<Ref> call = git.branchList().setListMode(ListMode.ALL).call();
		List<Ref> callcopy = new ArrayList<>(call);
 		for (Ref ref : call) {
			 
			// fetch all commits for this tag
			LogCommand log = git.log();
			Ref peeledRef = repository.peel(ref);
			if (peeledRef.getPeeledObjectId() != null) {
				log.add(peeledRef.getPeeledObjectId());
			} else {
				log.add(ref.getObjectId());
			}
			Iterable<RevCommit> logs = log.call();
			
			for (RevCommit rev : logs) {
				RevWalk revWalk = new RevWalk(repository);
				RevTree tree = rev.getTree();
				TreeWalk treeWalk = new TreeWalk(repository);
				treeWalk.addTree(tree);
				treeWalk.setRecursive(true);
				treeWalk.setFilter(PathFilter.create(fileExtension));
				if (treeWalk.next()) {
					callcopy.remove(ref);
					break;
				}
			}
		}
		return callcopy;
	}
	

	/**
	 * getBranchWithNoFile will fetch all the Tag for a repo
	 * which will not have the file specified in the parameter
	 * @param - fileExtension , this file should not be present in the Tags
	 *  @param - dir, where the repo will be cloned/pulled
	 */
	public Collection<Ref>  getTagswithNoFile(String fileName,String dir) throws IOException, GitAPIException {
		System.out.println("Searching for Tags with no "+fileName +".....................");
		CloneRemoteRepository.repoClone();
 		File gitWorkDir = new File(dir);
		Git git = Git.open(gitWorkDir);
		Repository repository = git.getRepository();
		List<Ref> call = git.tagList().call();
		List<Ref> callcopy = new ArrayList(call);
 		for (Ref ref : call) {
			LogCommand log = git.log();
			Ref peeledRef = repository.peel(ref);
			if (peeledRef.getPeeledObjectId() != null) {
				log.add(peeledRef.getPeeledObjectId());
			} else {
				log.add(ref.getObjectId());
			}
			Iterable<RevCommit> logs = log.call();
			for (RevCommit rev : logs) {
 				RevTree tree = rev.getTree();
				TreeWalk treeWalk = new TreeWalk(repository);
				treeWalk.addTree(tree);
				treeWalk.setRecursive(true);
				treeWalk.setFilter(PathFilter.create(fileName));
				if ((treeWalk.next())) {
					callcopy.remove(ref);
					break;
				}
			}
		}
		return callcopy;
	}
}
