# Currency Converter

- This small web application allows you to quickly check the latest currency exchange rates for over 30 currency pair.
- Built with Spring Framework and its derivatives.

- Default user is provided below to test this app:
 - email: test@test.com
 - password: secret
 
- User can sign up with personal email or login with social websites

- App is deployed to cloud platform heroku. 
- [Link to deployed version](https://calm-savannah-70817.herokuapp.com/)

- Useful commands:
 - mvn clean - deletes the /target folder
 - mvn package -  Converts your .java source code into a .jar/.war file and puts it into the /target folder
 - mvn install - First, it does a package(!). Then it takes that .jar/.war file and puts it into your local Maven repository

 - heroku login - command opens your web browser to complete the login flow
 - heroku create - creates a new empty application on Heroku, along with an associated empty Git repository
 - heroku local - runs app locally (by default on port 5000)
 
 - git init - initializes empty Git repository
 - git add . - adds changes in the working directory to the staging area
 - git commit -m "commit message" - saves changes to local repository
 - git push heroku master - pushs the code from your local repository’s master branch to your heroku remote
 
 

