# automated-testing-global-mp-advanced
## Home task. TAF-design. API

### HOW TO RUN TESTS:

-- LOCALLY --
1) Deploy ReportPortal locally
2) Open your web browser with an IP address of the deployed environment at port 8080
3) Use Administrator login/password to access: superadmin/erebus
4) Generate token using http://localhost:8080/ui/#api (workaround):
- select 'Try it out' option on any GET endpoint in '[user-filter-controller](http://localhost:8080/ui/#api:~:text=user%2Dfilter%2Dcontroller)' where _projectName_="**_superadmin_personal_**"
- copy from Curl value of Authorization field (ignoring 'bearer ' prefix) and paste it to _**reportportal.properties**_ file by key 'rp.token',
  **or** set by key 'TOKEN' to User Environment variables
5) also set to _**reportportal.properties**_ file or to User Environment variable next values:
- ADMIN=superadmin;
- BASE_URL=http://localhost:8080;
- PASSWORD=erebus;
- PROJECT_NAME=superadmin_personal;
6) Run tests, observe logs into console and _reportportal_logs.txt_ file


-- WEB-VERSION --
1) Sign in to a ReportPortal using valid credentials
2) Generate token using https://reportportal.epam.com/ui/#api (workaround):
- select 'Try it out' option on any GET endpoint in '[user-filter-controller](https://reportportal.epam.com/ui/#api:~:text=user%2Dfilter%2Dcontroller)' where _projectName_ can be get from _[rp.project](https://reportportal.epam.com/ui/#userProfile/configExamples)_
- copy from Curl value of Authorization field (ignoring 'bearer ' prefix) and paste it to _**reportportal.properties**_ file by key 'rp.token',
**or** set by key 'TOKEN' to User Environment variables
4) also set to _**reportportal.properties**_ file or to User Environment variable  next values: 
- ADMIN=user_name_from_login;
- BASE_URL=https://reportportal.epam.com;
- PASSWORD=password_from_login;
- PROJECT_NAME=[rp.project](https://reportportal.epam.com/ui/#userProfile/configExamples);
3) Run tests, observe logs into console and _reportportal_logs.txt_ file