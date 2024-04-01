package com.epam.model.filter.get;

import lombok.*;

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