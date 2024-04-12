package com.epam.api;

import com.epam.helper.RequestBodyHelper;
import com.epam.model.filter.response.CreateFilterResponseDto;
import com.epam.model.filter.response.MessageDto;
import com.epam.service.PropertyHolderService;
import com.epam.service.RestClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static com.epam.constant.Constants.*;

public abstract class BaseTest {
    private static final Logger LOGGER = LogManager.getLogger(BaseTest.class);
    protected static RestClient client;
    protected static PropertyHolderService propertyHolderService;
    protected static int id;
    protected LocalDateTime testStartTime;
    protected String testName;
    protected static String userName;
    protected static String password;
    protected static String rpToken;
    protected static String projectName;
    protected static String rpEndpoint;
    protected static String baseUrl;

    @BeforeClass
    public static void setUp() {
        client = new RestClient();
        propertyHolderService = new PropertyHolderService();
        userName = propertyHolderService.getOrSystem("rp.user-name", "ADMIN");
        password = propertyHolderService.getOrSystem("rp.password", "PASSWORD");
        rpToken = propertyHolderService.getOrSystem("rp.token", "RP_TOKEN");
        projectName = propertyHolderService.getOrSystem("rp.project", "PROJECT_NAME");
        rpEndpoint = propertyHolderService.getOrSystem("rp.endpoint", "BASE_URL");
        baseUrl = rpEndpoint + "/%s";

        //create filter for testing
        id = client.post(
                        String.format(baseUrl, String.format(FILTER, projectName)),
                        RequestBodyHelper.getBodyForCreateFilterRequest(),
                        CreateFilterResponseDto.class
                )
                .getId();
        LOGGER.info("New filter with id {} is created.", id);
    }

    @BeforeTest
    void setUp(ITestContext testInfo) {
        testStartTime = LocalDateTime.now(ZoneOffset.UTC);
        testName = testInfo.getName();
        LOGGER.info("{} test started at: {}", testName, testStartTime);
    }

    @AfterClass
    public static void tearDown() {
        //delete testing filter
        int expectedStatus = client.get(String.format(baseUrl, String.format(FILTER_BY_ID, projectName, id)));
        if (expectedStatus == 200) {
            String message = client.delete(String.format(baseUrl, String.format(FILTER_BY_ID, projectName, id)), MessageDto.class).getMessage();
            LOGGER.info(message);
        } else {
            LOGGER.warn("Response to delete filter by '{}' id returns '{}' status code", id, expectedStatus);
        }
    }
}
