# Project Manager - Final Certification - IIHT - Developer: Vignesh Suryah, Mani
<h2>Build and package spring boot and angular6 into a jar file</h2>

<h4>projectmanager-server - contains the REST end-points for application </h4>
<h4>projectmanager-web - contains the angular 6 cli code for application</h4>

Repo: https://github.com/vigneshsuryah/projectmanager-casestudy.git

<b>Commands for final build:</b>
<ul>
<li>Maven build: clean install -e	[The UI code is build using "frontend-maven-plugin" and is packed inside the JAR artifacts itself. Refer projectmanager-web pom.xml]</li>
<li>Docker build: package docker:build	[spotify "docker-maven-plugin" is used to create image in the remote docker. <dockerHost> configuration : pom.xml of projectmanager-server]</li>
</ul>

<b>Commands for Docker execution:</b>
<ul>
<li>dockerx run -p 8090:8085 vs-projectmanager-app-image:latest	[to start the image, we generated]</li>
<li>dockerx exec -it <container id> sh	[to check the below URL's are working in the curl]</li>
</ul>	

<ul>
<li>To check whether application loaded correctly: curl http://localhost:8090/test</li>
<li>Service: curl http://localhost:8090/api/tasks</li>
<li>UI Home page: curl http://localhost:8090/index.html</li>
</ul>	

The above URLs can be accessed in the host machine with the Port Forwarding settings of VM toolbox [Settings -> Network -> Port Forwarding -> HOSTIP/HOSTPORT 127.0.0.1:8090 to GUESTIP/GUESTPORT :8090]

<b>Commands for local development:</b>
<ul>
<li>spring boot in projectmanager-server folder: spring-boot:run</li>
<li>angular ui in web folder of projectmanager-web: npm install -> npm start</li>
</ul>

<b>Jenkins:</b>
<ul>
<li>Branches to build: */integration</li>
<li>Script Path: projectmanager-server/Jenkinsfile</li>
</ul>

<b>Note: </b>
<ul>
<li>Have used free hosting public MySQL database jdbc:mysql://sql12.freemysqlhosting.net:3306/dbname. This is to access database layer when deployed in docker (instead of setting mysql in docker).</li>
<li>Refer dockerfile, application screenshot document in the repo folder.</li>
<li>Refer mysql-script-to-execute.txt for the database DDL and DML's</li>
<li>Before the maven build, change the port number in envioronment.prod.ts to change the port number in which the jar is gonna get deployed.</li>
</ul>


