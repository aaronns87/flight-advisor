package com.aaron.challenge.flightadvisor.imports.csv;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AirlineCsv {

    private Long id;

    private String code;
}
