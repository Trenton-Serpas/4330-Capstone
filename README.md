# 4330-Capstone
The purpose of this project is to allow the user to scan a set of documents for the 10 most used words and display it on a word cloud.

**Group Discord:** https://discord.gg/h6247Wu

## How to run:
To run our code, there are a few necessary steps you must take to compile the project properly. In our GitHub, make sure to include all of the ‘libs’ files. Another pre-requirement is to install Apache Tomcat 8.5. Open “Final-4330-Submission” in Eclipse and include the jar files in the build path. There is hard-coded information at the top of the file. This information is the folder location for the HTML called “fpath,” and the user, password, and database name necessary to communicate to the database connection.  

To run the program out of Eclipse, it is important to install the five dependencies. Do this by navigating through the following path: 

Help-> install new software-> select work with /preferred/ 

Then select the five following dependencies: 

-JST Server Adapters 

-JST Server Adapters Extensions 

-Eclipse Java EE Developer Tools 

-Eclipse Java Web Developer Tools 

-Eclipse Web Developer Tools 

 

Another step to take in Eclipse is to show the software you hold the Apache code: 

Windows->preferences-> server-> run time environment-> Add-> apache tomcat8.5-> next-> browse: 

Find your tomcat (which will typically be in program files-> Apache Software Foundation)  

In eclipse, set up a server. Go to: 

New-> server-> tomcat8.5-> next-> select the code 

Finally, you will need to know the URL access web browser: 

http://localhost/:[http port]/Final-4330-Submission/ 

