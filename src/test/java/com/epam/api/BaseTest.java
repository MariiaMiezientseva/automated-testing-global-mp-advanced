package com.epam.api;

import com.epam.model.filter.update.CreateFilterResponseDto;
import com.epam.model.filter.update.MessageDto;
import com.epam.service.RestClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static com.epam.constant.Constants.*;

public abstract class BaseTest {

    private static final Logger LOGGER = LogManager.getLogger(BaseTest.class);
    protected static RestClient client;
    protected static int id;
    protected LocalDateTime testStartTime;
    protected String testName;

    @BeforeAll
    public static void setUp() {
        client = new RestClient();
        id = client.post(String.format(BASE_URL, String.format(FILTER, PROJECT_NAME)), CREATE_FILTER_REQUEST_BODY, CreateFilterResponseDto.class).getId();
        LOGGER.info("New filter with id {} is created.", id);
    }

    @BeforeEach
    void setUp(TestInfo testInfo) {
        testStartTime = LocalDateTime.now(ZoneOffset.UTC);
        testName = testInfo.getDisplayName();
        LOGGER.info("{} test started at: {}", testName, testStartTime);
    }

    @AfterAll
    public static void tearDown() {
        int expectedStatus = client.get(String.format(BASE_URL, String.format(FILTER_BY_ID, PROJECT_NAME, id)));
        if (expectedStatus == 200) {
            String message = client.delete(String.format(BASE_URL, String.format(FILTER_BY_ID, PROJECT_NAME, id)), MessageDto.class).getMessage();
            LOGGER.info(message);
        } else {
            LOGGER.warn("Response to delete filter by '{}' id returns '{}' status code", id, expectedStatus);
        }
    }
}