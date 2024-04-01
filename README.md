# automated-testing-global-mp-advanced
## Home task. UI testing

### HOW TO RUN TESTS:

-- WEB-VERSION --
1) set to User Environment variable next values: 
- RP_PASSWORD=from_secret
- RP_USERNAME=from_secret
2) Uncomment _tasks.test_ into [build.gradle.kts](build.gradle.kts) and related _configureTestLogging()_
3) Run tests: via CLI command _**gradle test**_ or Run from [testng_test_execution.xml](src%2Ftest%2Fresources%2Ftestng_test_execution.xml) 
4) Observe logs into console, _reportportal_logs.txt_ file and into [test-result](test-result)