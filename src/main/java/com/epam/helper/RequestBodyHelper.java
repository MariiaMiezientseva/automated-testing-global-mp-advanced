package com.epam.helper;

import com.epam.model.filter.ConditionDto;
import com.epam.model.filter.OrderDto;
import com.epam.model.filter.request.ElementsDto;
import com.epam.model.filter.request.FilterRequestDto;
import com.epam.model.filter.request.FiltersRequestDto;

import java.util.List;

public class RequestBodyHelper {
    private static final ConditionDto conditions = ConditionDto.builder()
            .filteringField("name")
            .condition("cnt")
            .value("api")
            .build();

    private static final OrderDto orders = OrderDto.builder()
            .isAsc(Boolean.FALSE)
            .sortingColumn("startTime")
            .build();

    public static FilterRequestDto getBodyForCreateFilterRequest() {
        return FilterRequestDto.builder()
                .conditions(List.of(conditions))
                .description("Filter, created via api test")
                .name("Api-test filter")
                .orders(List.of(orders))
                .type("launch")
                .build();
    }

    public static FilterRequestDto getBodyForUpdateFilterRequest() {
        FilterRequestDto bodyForUpdate = getBodyForCreateFilterRequest();
        bodyForUpdate.setDescription("updated via api test");
        bodyForUpdate.setName("API TEST FILTER");
        return bodyForUpdate;
    }

    public static FiltersRequestDto getBodyForUpdateFiltersRequest(String id) {
        conditions.setCondition("has");
        conditions.setFilteringField("compositeAttribute");
        conditions.setValue("tests");
        ElementsDto elements = ElementsDto.builder()
                .conditions(List.of(conditions))
                .description("Updated filters form api tests via POST")
                .id(id)
                .name("API TEST FILTER")
                .orders(List.of(orders))
                .type("launch")
                .build();
        return FiltersRequestDto.builder()
                .elements(List.of(elements))
                .build();
    }
}
