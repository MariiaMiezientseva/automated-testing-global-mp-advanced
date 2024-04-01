package com.epam.api;

import com.epam.model.filter.get.FilterResponseDto;
import com.epam.model.filter.get.GetFiltersResponseDto;
import com.epam.model.filter.update.ErrorMessageDto;
import com.epam.model.filter.update.MessageDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;

import static com.epam.constant.Constants.*;

public class ReportPortalApiTest extends BaseTest {
    private static final Logger LOGGER = LogManager.getLogger(ReportPortalApiTest.class);

    @Test
    void userShouldGetAllProjectFilters() {
        var response = client.get(String.format(BASE_URL, String.format(FILTER, PROJECT_NAME)), GetFiltersResponseDto.class);
        Assertions.assertThat(response.getPage().getTotalElements()).isEqualTo(2);
    }

    @Test
    void userShouldGetFilterById() {
        FilterResponseDto response = client.get(String.format(BASE_URL, String.format(FILTER_BY_ID, PROJECT_NAME, id)), FilterResponseDto.class);
        Assertions.assertThat(response.getName()).isEqualTo(TEST_FILTER_NAME);
    }

    @Test
    void userShouldUpdateFilterById() {
        File body = new File(UPDATE_FILTER_REQUEST_BODY);
        String message = client.put(String.format(BASE_URL, String.format(FILTER_BY_ID, PROJECT_NAME, id)), body,
                MessageDto.class, 200).getMessage();
        Assertions.assertThat(message).isEqualTo(String.format(UPDATE_MESSAGE_TEMPLATE, id));
    }

    @Test
    void userShouldNotUpdateListOfFilters() {
        File body = new File(UPDATE_FILTERS_REQUEST_BODY);
        var response = client.put(String.format(BASE_URL, String.format(FILTER, PROJECT_NAME)), body, ErrorMessageDto.class, 500);
        LOGGER.warn("Internal Server Error occurs. Response message: {}", response.getMessage());
        Assertions.assertThat(response.getErrorCode()).isEqualTo(5000);
    }
}