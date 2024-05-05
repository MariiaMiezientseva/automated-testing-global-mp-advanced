package stepdefs;

import com.epam.model.filter.response.FilterResponseDto;
import com.epam.model.filter.response.GetFiltersResponseDto;
import com.epam.service.RestClient;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import org.assertj.core.api.Assertions;

import java.util.List;

import static com.epam.constant.Constants.FILTER;

public class CommonAssertSteps {

    private final RestClient client = new RestClient();

    @Then("^New filter appears with:$")
    public void checkFilterIsCreated(List<String> values) {
        var filterResponseDto = getDto();
        Assertions.assertThat(filterResponseDto.getName()).isEqualTo(values.get(0));
        Assertions.assertThat(filterResponseDto.getDescription()).isEqualTo(values.get(1));
    }

    @Then("^User check that filter is updated and has new name:$")
    public void checkFilterIsUpdated(DataTable name) {
        Assertions.assertThat(getDto().getName()).isEqualTo(name.column(0).get(0));
    }

    private FilterResponseDto getDto() {
        return client.get(
                        String.format(client.getBaseUrl(), String.format(FILTER, client.getProjectName())),
                        GetFiltersResponseDto.class
                )
                .getContent().get(0);
    }
}
