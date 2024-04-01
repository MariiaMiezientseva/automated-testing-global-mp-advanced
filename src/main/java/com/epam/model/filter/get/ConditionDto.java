package com.epam.model.filter.get;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConditionDto implements Serializable {
    private String filteringField;
    private String condition;
    private String value;
}