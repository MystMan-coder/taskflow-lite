package com.dhananjaykr.taskflow.taskflowlite.shared.exception.custom;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class ErrorResponseDto {

    private String message;
    private int status;
    private LocalDateTime timestamp;

}
