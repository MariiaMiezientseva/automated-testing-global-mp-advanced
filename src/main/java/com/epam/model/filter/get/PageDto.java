package com.epam.model.filter.get;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageDto implements Serializable {
    private Integer number;
    private Integer size;
    private Integer totalElements;
    private Integer totalPages;
}