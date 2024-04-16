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
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;

import static com.epam.constant.Constants.*;

public class DataDrivenWithDataProviderTest {
    private final static Logger LOGGER = LogManager.getLogger(DataDrivenWithDataProviderTest.class);
    private final static List<Integer> ids = new ArrayList<>();
    private static RestClient client;
    private static String projectName;
    private static String baseUrl;

    @BeforeAll
    public static void setUp() {
        client = new RestClient();
        PropertyHolderService propertyHolderService = new PropertyHolderService();
        projectName = propertyHolderService.getOrSystem("rp.project", "PROJECT_NAME");
        String rpEndpoint = propertyHolderService.getOrSystem("rp.endpoint", "BASE_URL");
        baseUrl = rpEndpoint + "/%s";
    }

    @AfterAll
    public static void cleanUp() {
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

    @ParameterizedTest
    @DisplayName("Create filters from 1 dataset")
    @MethodSource("createFilterWithBodyFrom1DataSet")
    public void createFilterWithFilteringFieldNameTest(FilterRequestDto requestDto) {
        int id = client.post(
                        String.format(baseUrl, String.format(FILTER, projectName)),
                        requestDto,
                        CreateFilterResponseDto.class
                )
                .getId();
        logFilterCreationById(id);
        ids.add(id);
        logTotalTestIds(ids);
    }

    @ParameterizedTest
    @DisplayName("Create filters from 2 dataset")
    @MethodSource("createFilterWithBodyFrom2DataSet")
    public void createFilterWithFilteringFieldCompositeAttributeTest(FilterRequestDto requestDto) {
        int id = client.post(
                        String.format(baseUrl, String.format(FILTER, projectName)),
                        requestDto,
                        CreateFilterResponseDto.class
                )
                .getId();
        logFilterCreationById(id);
        ids.add(id);
        logTotalTestIds(ids);
    }

    @ParameterizedTest
    @DisplayName("Create filters from 3 dataset")
    @MethodSource("createFilterWithBodyFrom3DataSet")
    public void createFilterFromThirdDataSetTest(FilterRequestDto requestDto) {
        int id = client.post(
                        String.format(baseUrl, String.format(FILTER, projectName)),
                        requestDto,
                        CreateFilterResponseDto.class
                )
                .getId();
        logFilterCreationById(id);
        ids.add(id);
        logTotalTestIds(ids);
    }

    public static Object[][] createFilterWithBodyFrom1DataSet() throws Exception {
        ExtUtils extract = new CSVUtils(FIRST_BODY, true);
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

    public static Object[][] createFilterWithBodyFrom2DataSet() throws Exception {
        ExtUtils extract = new CSVUtils(SECOND_BODY, true);
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

    public static Object[][] createFilterWithBodyFrom3DataSet() throws Exception {
        ExtUtils extract = new CSVUtils(THIRD_BODY, true);
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

    private void logFilterCreationById(int id) {
        LOGGER.info("New filter with 'id' = {} is created by Thread: {}.", id, Thread.currentThread().getId());
    }

    private void logTotalTestIds(List<Integer> ids) {
        LOGGER.info("Created ids are: {}", ids);
    }
}
