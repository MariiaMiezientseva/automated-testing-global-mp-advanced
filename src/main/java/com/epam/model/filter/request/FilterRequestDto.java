package com.epam.model.filter.request;

import com.epam.model.filter.ConditionDto;
import com.epam.model.filter.OrderDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class FilterRequestDto implements Serializable {
    private List<ConditionDto> conditions;
    private String description;
    private String name;
    private List<OrderDto> orders;
    private String type;
}
