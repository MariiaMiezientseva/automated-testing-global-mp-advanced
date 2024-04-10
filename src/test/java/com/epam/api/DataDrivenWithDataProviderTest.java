package com.epam.api;

import com.epam.model.filter.ConditionDto;
import com.epam.model.filter.OrderDto;
import com.epam.model.filter.request.FilterRequestDto;
import com.epam.model.filter.response.CreateFilterResponseDto;
import com.epam.model.filter.response.MessageDto;
import com.epam.service.PropertyHolderService;
import com.epam.service.RestClient;
import com.github.hemanthsridhar.CSVUtils;
import com.github.hemanthsridhar.lib.ExtUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static com.epam.constant.Constants.*;

public class DataDrivenWithDataProviderTest {
    private final Logger LOGGER = LogManager.getLogger(DataDrivenWithDataProviderTest.class);
    private final List<Integer> ids = new ArrayList<>();
    private RestClient client;
    private String projectName;
    private String baseUrl;

    @BeforeClass
    public void setUp() {
        client = new RestClient();
        PropertyHolderService propertyHolderService = new PropertyHolderService();
        projectName = propertyHolderService.getOrSystem("rp.project", "PROJECT_NAME");
        String rpEndpoint = propertyHolderService.getOrSystem("rp.endpoint", "BASE_URL");
        baseUrl = rpEndpoint + "/%s";
    }

    @AfterClass
    public void cleanUp() {
        for (int id : ids) {
            int expectedStatus = client.get(String.format(baseUrl, String.format(FILTER_BY_ID, projectName, id)));
            if (expectedStatus == 200) {
                String message = client.delete(String.format(baseUrl, String.format(FILTER_BY_ID, projectName, id)), MessageDto.class).getMessage();
                LOGGER.info(message);
            } else {
                LOGGER.warn("Response to delete filter by '{}' id returns '{}' status code", id, expectedStatus);
            }
        }
    }

    @Test(dataProvider = "createBody")
    public void createFilter(FilterRequestDto requestDto) {
        LOGGER.info("Extracted from CSV value: {}", requestDto);
        int id = client.post(
                        String.format(baseUrl, String.format(FILTER, projectName)),
                        requestDto,
                        CreateFilterResponseDto.class
                )
                .getId();
        LOGGER.info("New filter with id {} is created by Thread: {}.", id, Thread.currentThread().getId());
        ids.add(id);
        System.out.println("Ids are: " + ids);
    }

    @DataProvider(name = "createBody", parallel = true)
    public Object[][] createBody() throws Exception {
        ExtUtils extract = new CSVUtils(CSV_FILE, true);
        String[][] rows = extract.parseData();
        Object[][] objects = new Object[extract.parseData().length][1];
        for (int i = 0; i < rows.length; i++) {
            String[] row = rows[i];
            objects[i][0] = FilterRequestDto.builder()
                    .conditions(List.of(ConditionDto.builder()
                            .condition(row[1])
                            .filteringField(row[0])
                            .value(row[2])
                            .build()))
                    .description(row[3])
                    .name(row[4])
                    .orders(List.of(OrderDto.builder()
                            .isAsc(Boolean.valueOf(row[6]))
                            .sortingColumn(row[5])
                            .build()))
                    .type(row[7])
                    .build();
        }
        return objects;
    }
}
