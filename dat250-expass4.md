<h2> Experiment Assignment 4</h2>

### The things I did
- Added dependencies to gradle and removed spring boot starter
- Created the polltest file and copied in test code
- Added necessary constructors and methods 
- 

### What I still need to do
- clean up warnings
- get the jpa annotations to work

### Technical issues
- Got many errors when adding "public user in user.java"
  - changed imports
  - alot of trial and error to fix this
  - removed jackson
  - removed @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
  - made sure all list iterations where non-final
  - Added @Entity to all clases



