package stepdefs;

import com.epam.context.ScenarioContext;
import com.epam.enums.Context;
import com.epam.helper.RequestBodyHelper;
import com.epam.model.filter.ConditionDto;
import com.epam.model.filter.OrderDto;
import com.epam.model.filter.request.FilterRequestDto;
import com.epam.model.filter.response.CreateFilterResponseDto;
import com.epam.model.filter.response.MessageDto;
import com.epam.service.RestClient;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.assertj.core.api.Assertions;

import java.util.List;

import static com.epam.constant.Constants.FILTER;
import static com.epam.constant.Constants.FILTER_BY_ID;

public class UserFilterSteps {

    private final RestClient client = new RestClient();
    private final ScenarioContext scenarioContext = new ScenarioContext();
    private final Logger LOGGER = LogManager.getLogger(UserFilterSteps.class);

    @Given("^User create new filter$")
    public void createFilter() {
        int id = client.post(
                        String.format(client.getBaseUrl(), String.format(FILTER, client.getProjectName())),
                        RequestBodyHelper.getBodyForCreateFilterRequest(),
                        CreateFilterResponseDto.class
                )
                .getId();
        scenarioContext.setContext(Context.ID, id);
        LOGGER.info("New filter with {} was created", id);
    }

    @When("^User update filter with values:$")
    public void sendPut(DataTable values) {
        FilterRequestDto bodyForUpdate = FilterRequestDto.builder()
                .conditions(List.of(ConditionDto.builder()
                        .filteringField(values.column(0).get(0))
                        .condition(values.column(1).get(0))
                        .value(values.column(2).get(0))
                        .build()))
                .description(values.column(3).get(0))
                .name(values.column(4).get(0))
                .orders(List.of(OrderDto.builder()
                        .isAsc(Boolean.valueOf(values.column(5).get(0)))
                        .sortingColumn(values.column(6).get(0))
                        .build()))
                .type(values.column(7).get(0))
                .build();

        int id = (int) scenarioContext.getContext(Context.ID);

        client.put(
                String.format(client.getBaseUrl(), String.format(FILTER_BY_ID, client.getProjectName(), id)),
                bodyForUpdate,
                MessageDto.class,
                200);
        LOGGER.info("Filter with {} was updated", id);
    }

    @Then("^User delete created filter$")
    public void checkFilterIsDeleted() {
        int id = (int) scenarioContext.getContext(Context.ID);
        var urlFilterById = String.format(client.getBaseUrl(), String.format(FILTER_BY_ID, client.getProjectName(), id));
        int expectedStatus = client.get(urlFilterById);
        if (expectedStatus == 200) {
            String message = client.delete(urlFilterById, MessageDto.class).getMessage();
            Assertions.assertThat(message).contains(String.valueOf(id));
            LOGGER.info("Filter with {} was deleted", id);
        } else {
            LOGGER.error("Response to delete filter by '{}' id returns '{}' status code!", id, expectedStatus);
        }
    }
}
