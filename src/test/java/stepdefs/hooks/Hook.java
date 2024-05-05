package stepdefs.hooks;

import com.epam.model.filter.response.FilterResponseDto;
import com.epam.model.filter.response.GetFiltersResponseDto;
import com.epam.model.filter.response.MessageDto;
import com.epam.service.RestClient;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.epam.constant.Constants.FILTER;
import static com.epam.constant.Constants.FILTER_BY_ID;

public class Hook {
    private final Logger LOGGER = LogManager.getLogger(Hook.class);
    private final RestClient client = new RestClient();
    private String projectName;
    private String baseUrl;


    @Before
    public void beforeAll() {
        projectName = client.getProjectName();
        baseUrl = client.getBaseUrl();
    }

    @After(value = "@deleteFilters", order = 20)
    public void afterTest() {
        var listFilterDto = client.get(
                        String.format(baseUrl, String.format(FILTER, projectName)), GetFiltersResponseDto.class)
                .getContent();
        for (FilterResponseDto filter : listFilterDto) {
            var urlFilterById = String.format(baseUrl, String.format(FILTER_BY_ID, projectName, filter.getId()));
            int expectedStatus = client.get(urlFilterById);
            if (expectedStatus == 200) {
                String message = client.delete(urlFilterById, MessageDto.class).getMessage();
                LOGGER.info(message);
            } else {
                LOGGER.error("Response to delete filter by '{}' id returns '{}' status code!", filter.getId(), expectedStatus);
            }
        }
    }
}
