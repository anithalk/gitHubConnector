This project can access a git Repo and perform the following functions:

1)get All the Branches for a repo
2)get all the Tags for a Repo
3)return the Branch names which does not have the file .travis.yml<can be customized> in the root
4)return the Tag Names which does not have .travis.yml in the root <can be customized>

Execution of the main method, will automatically invoke all the above four routines
and the results will be printed on the console.

Please note pom.xml is configured to use JDK 7. Please change it if required

*************************************************************************************
******************************Build the project**************************************
*************************************************************************************
Use the following maven command to  build the project fromt the root directory:
mvn clean install
(or)
mvn install 


*************************************************************************************
***********************Execute the Program with default Settings:********************
*************************************************************************************
Execution of the main method, will automatically invoke all the above four routines
and the results will be printed on the console.
Execute the following command from the project root directory, where pom.xml is present 

mvn exec:java -Dexec.mainClass="com.comcast.GitHubDemo"


*************************************************************************************
***********************Execute the Program with Custom Settings:********************
*************************************************************************************
Alternatively, we can also execute the tests with following custom parameters
The following properties can be changed in the file config.properties present under src/main/resources
repoURL=<repo url>
fileName=<fileName that should not be present in the Branches/Tags>
workingDir=<local directory name, where the repo will be cloned>


Also there are Five Unit Tests present in the class com.comcast.connectorTest with Mock and withOut as well.

