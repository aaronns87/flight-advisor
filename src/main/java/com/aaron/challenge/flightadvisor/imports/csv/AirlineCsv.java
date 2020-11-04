package com.aaron.challenge.flightadvisor.imports.csv;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class AirlineCsv {

    private Long externalId;

    private String code;
}
