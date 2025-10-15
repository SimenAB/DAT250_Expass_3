<h2> Experiment Assignment 6</h2>

### The things I did
- Started RabbitMQ server using docker
- Added RabbitMQ dependencies in build.gradle
- Reloaded gradle
- Added [connection settings](https://github.com/SimenAB/DAT250_Expass_3/blob/Expass_6/expass1_spring_boot/src/main/resources/application.properties) for rabbit
- Added RabbitAdmin in [config](https://github.com/SimenAB/DAT250_Expass_3/blob/Expass_6/expass1_spring_boot/src/main/java/no/hvl/dat250/pollapp/config/PollRabbit_Config.java)
- Create a queue when poll is created in [pollmanager](https://github.com/SimenAB/DAT250_Expass_3/blob/Expass_6/expass1_spring_boot/src/main/java/no/hvl/dat250/pollapp/service/PollManager.java)
- Created messaging package with listener class

- Added links and cleaned up md

### What I still need to do
- Clean up the code

- Create the subscription option 
  - Testing with making a vote and subscribing
    - Will do this asap tonight

### Technical issues
- reoccurring issue with rebuilding gradle
- 
