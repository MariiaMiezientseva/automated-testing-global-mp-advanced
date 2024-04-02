package com.epam.model.filter.response;

import com.epam.model.filter.ConditionDto;
import com.epam.model.filter.OrderDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FilterResponseDto implements Serializable {
    private String description;
    private String owner;
    private Integer id;
    private String name;
    private List<ConditionDto> conditions;
    private List<OrderDto> orders;
    private String type;
}
