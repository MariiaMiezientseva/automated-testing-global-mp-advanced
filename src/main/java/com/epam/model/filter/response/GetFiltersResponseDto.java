package com.epam.model.filter.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetFiltersResponseDto implements Serializable {
    private List<FilterResponseDto> content;
    private PageDto page;
}
