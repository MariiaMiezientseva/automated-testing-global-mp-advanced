# automated-testing-global-mp-advanced
## Home task. TAF-design. API

### HOW TO RUN TESTS:

1) Deploy ReportPortal with Docker - instruction is here https://reportportal.io/docs/installation-steps/DeployWithDocker
2) Open your web browser with an IP address of the deployed environment at port 8080
3) Use Administrator login/password to access: superadmin/erebus
4) Generate token using http://localhost:8080/ui/#api (workaround):
- select 'Try it out' option on any GET endpoint in '[user-filter-controller](http://localhost:8080/ui/#api:~:text=user%2Dfilter%2Dcontroller)' where _projectName_="**_superadmin_personal_**"
- copy from Curl value of Authorization field (ignoring 'bearer ' prefix) and paste it to TAF as a TOKEN to project Environment variable value
5) Run tests, observe logs into console and reportportal_logs.txt file