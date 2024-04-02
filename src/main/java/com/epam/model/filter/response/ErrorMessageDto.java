package com.epam.model.filter.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorMessageDto implements Serializable {
    private String message;
    private Integer errorCode;
}
