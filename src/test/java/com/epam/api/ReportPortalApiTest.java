package com.epam.api;

import com.epam.helper.RequestBodyHelper;
import com.epam.model.filter.response.FilterResponseDto;
import com.epam.model.filter.response.GetFiltersResponseDto;
import com.epam.model.filter.response.MessageDto;
import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;

import static com.epam.constant.Constants.*;

public class ReportPortalApiTest extends BaseTest {

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
    void userShouldNotGetFilterByNonExitingId() {
        var response = client.get(
                String.format(baseUrl, String.format(FILTER_BY_ID, projectName, 1)));
        Assertions.assertThat(response).isEqualTo(404);
    }

    @Test
    void userShouldNotCreateFilterWithTheSameId() {
        Assertions.assertThat(client.post(
                String.format(baseUrl, String.format(FILTER, projectName)),
                RequestBodyHelper.getBodyForCreateFilterRequest())).isEqualTo(409);
    }

    @Test
    void userShouldNotCreateFilterWithMissingBody() {
        Assertions.assertThat(client.post(
                String.format(baseUrl, String.format(FILTER, projectName)), "")).isEqualTo(400);
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
    void userShouldNotUpdateFilterWithMissingBody() {
        Assertions.assertThat(client.put(
                        String.format(baseUrl, String.format(FILTER_BY_ID, projectName, id)),
                        "")).isEqualTo(400);
    }

    @Test
    void userShouldNotUpdateListOfFilters() {
        Assertions.assertThat(client.put(
                String.format(baseUrl, String.format(FILTER, projectName)),
                RequestBodyHelper.getBodyForUpdateFiltersRequest(String.valueOf(id)))).isEqualTo(500);
    }

    @Test
    void userShouldNotDeleteFilterByNonExistingId() {
        Assertions.assertThat(client.delete(String.format(baseUrl, String.format(FILTER_BY_ID, projectName, 1)))).isEqualTo(404);
    }
}
