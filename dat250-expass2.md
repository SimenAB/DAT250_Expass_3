<h2> Experiment Assignment 2</h2>

### The things i did
- Created a new GitHub repository for the : [DAT250_Expass_2](https://github.com/SimenAB/DAT250_Expass_2)
- Copied the Spring Boot project over from the last assignment, this turned out to be more work than just creating a new one, lesson learned
- Added classes inside a "domain" package: [domain](https://github.com/SimenAB/DAT250_Expass_2/tree/main/expass2_spring_boot/src/main/java/no/hvl/dat250/pollapp/domain)
- Used Lombok to save time with getters and setters
- Implemented a [Pollmanager](https://github.com/SimenAB/DAT250_Expass_2/blob/main/expass2_spring_boot/src/main/java/no/hvl/dat250/pollapp/service/PollManager.java) class in a "service" package
  - Added necessary functions like creating and listing users, but it is lacking some other parts that i did not get to test
- Wrote a http file to implement test events, but only got through the user ones: [http](https://github.com/SimenAB/DAT250_Expass_2/blob/main/expass1_spring_boot/test_scenarioes.http) 
  - Implemented [Controllers](https://github.com/SimenAB/DAT250_Expass_2/blob/main/expass1_spring_boot/src/main/java/no/hvl/dat250/pollapp/web/Controllers.java) for the ones i had time to create tests for

### What i still need to do
- Need to be able to go through all the scenarios
- I have to automate the testing


### Technical issues
 - Had installed different JDK versions in different environments
   - Had to find out the different versions i had in my VM, wsl and Windows
 - File path issues where spaces caused errors
 - Spring Boot app didnt run. I copied the one from last assignment, but changes to folder names and moving files led to path issues -> Spent more time than necessary, ended up just deleting run configurations and let IDEA create new
 - Gradle/IntelliJ refused to pick up changes to the directory


