package com.comcast.connectorTest;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collection;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.lib.Ref;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import com.comcast.connector.impl.GitHubConnectorImpl;
import com.comcast.controller.GitHubConnector;
import com.comcast.exception.ValidationException;
import static org.junit.Assert.assertNotNull;

public class ConnectorTest {

	private GitHubConnector gitHubConnector;
	private String validRepoLink;
	private String invalidRepoLink;
	
	@MockitoAnnotations.Mock
	private GitHubConnector mockedGitHubConnector;

	@Before
	public void setup() throws Exception {
		gitHubConnector = new GitHubConnectorImpl();
		MockitoAnnotations.initMocks(this);
		validRepoLink = "https://github.com/ruby/ruby.git";
		invalidRepoLink = "https://github.invalid.com";
 	}
		
	@Test
	/**
	 * This is a mock Test , 
	 * Validates that when the repolink is valid, results are returned
	 * 
	 * @throws ValidationException
	 */
	public void getBranchesValidMockTest() throws ValidationException {
		when(mockedGitHubConnector.getBranches("repoLink")).thenReturn(new ArrayList<Ref>());
		assertNotNull(mockedGitHubConnector.getBranches("repoLink"));
	}

	
	@Test
	/**
	 * Validates that when the repolink is valid, results are returned
	 * 
	 * @throws ValidationException
	 */
	public void getBranchesValidTest() throws ValidationException {
		Collection<Ref> response = gitHubConnector.getBranches(validRepoLink);
		Assert.assertNotNull(response);
		Assert.assertTrue(response.size() > 0);

	}
	
	
	

	/**
	 * This test validates that when the repolink is set as null, Validation
	 * Exception is thrown
	 * 
	 * @throws ValidationException
	 */
	@Test(expected = ValidationException.class)
	public void getBranchesInValidTest() throws ValidationException {
		Collection<Ref> response = gitHubConnector.getBranches(null);
		System.out.println("Response  = " + response);
		Assert.assertNull(response);

	}

	/**
	 * This test validates that when the repo link is valid, the response is not
	 * null and returns results
	 * 
	 * @throws ValidationException
	 */
	@Test
	public void getTagsValidTest() throws ValidationException {
		Collection<Ref> response = gitHubConnector.getTags(validRepoLink);
		Assert.assertNotNull(response);
		Assert.assertTrue(response.size() > 0);
	}

	/**
	 * This test validates that when the repolink is invalid, no results are
	 * returned and it throws JGitInternalException
	 * 
	 * @throws JGitInternalException
	 */
	@Test(expected = JGitInternalException.class)
	public void getTagsInValidTest() throws ValidationException {
		Collection<Ref> response = gitHubConnector.getTags(invalidRepoLink);
		System.out.println("Response  = " + response);
		Assert.assertNull(response);

	}

}
