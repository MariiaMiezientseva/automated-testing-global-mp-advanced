package com.epam.api;

import com.epam.helper.RequestBodyHelper;
import com.epam.model.filter.response.ErrorMessageDto;
import com.epam.model.filter.response.FilterResponseDto;
import com.epam.model.filter.response.GetFiltersResponseDto;
import com.epam.model.filter.response.MessageDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;

import static com.epam.constant.Constants.*;

public class ReportPortalApiTest extends BaseTest {
    private static final Logger LOGGER = LogManager.getLogger(ReportPortalApiTest.class);

    @Test
    void userShouldGetAllProjectFilters() {
        var response = client.get(String.format(baseUrl, String.format(FILTER, projectName)), GetFiltersResponseDto.class);
        Assertions.assertThat(response.getPage().getTotalElements()).isGreaterThanOrEqualTo(1);
    }

    @Test
    void userShouldGetFilterById() {
        FilterResponseDto response = client.get(
                String.format(baseUrl, String.format(FILTER_BY_ID, projectName, id)),
                FilterResponseDto.class);
        Assertions.assertThat(response.getName()).isEqualTo(TEST_FILTER_NAME);
    }

    @Test
    void userShouldUpdateFilterById() {
        String message = client.put(
                        String.format(baseUrl, String.format(FILTER_BY_ID, projectName, id)),
                        RequestBodyHelper.getBodyForUpdateFilterRequest(),
                        MessageDto.class, 200)
                .getMessage();
        Assertions.assertThat(message).isEqualTo(String.format(UPDATE_MESSAGE_TEMPLATE, id));
    }

    @Test
    void userShouldNotUpdateListOfFilters() {
        var response = client.put(
                String.format(baseUrl, String.format(FILTER, projectName)),
                RequestBodyHelper.getBodyForUpdateFiltersRequest(String.valueOf(id)),
                ErrorMessageDto.class,
                500);
        LOGGER.warn("Internal Server Error occurs. Response message: {}", response.getMessage());
        Assertions.assertThat(response.getErrorCode()).isEqualTo(5000);
    }
}
