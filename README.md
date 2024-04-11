# automated-testing-global-mp-advanced
## Home task. TAF-design. API

### HOW TO RUN TESTS:

-- WEB-VERSION --
1) Set to _**reportportal.properties**_ file values:
 - rp.password=from_secret
   rp.token=from_secret
2) or set to User Environment variable next values: 
- PASSWORD=from_secret
- RP_TOKEN=from_secret
3) Run tests via CLI command _**gradle build**_ or using Run(Ctrl+Shift+F10); observe logs into console and _reportportal_logs.txt_ file