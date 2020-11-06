package com.aaron.challenge.flightadvisor.routes.cheapest;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;

@Getter
@NoArgsConstructor
public class CheapestFlightChain {

    private static final String BRACKET_START = "{";
    private static final String BRACKET_END = "}";
    private static final String SEPARATOR = ",";

    private String routes;

    private Float totalPrice;

    private Integer hops;

    // Required for JPA automatic mapping in named native query
    @SuppressWarnings("unused")
    public CheapestFlightChain(String routes, Float totalPrice, Integer hops) {
        this.routes = routes;
        this.totalPrice = totalPrice;
        this.hops = hops;
    }

    public List<String> getRouteIds() {
        return Arrays.asList(trimCurlyBrackets(routes)
                .split(SEPARATOR));
    }

    /***
     * A bit of a ugly hack to use SQL text array[] as text
     * and split it into array in code.
     * Trim start bracket { and end bracket }
     * @param input Input to trim
     * @return Input without { and }
     */
    private String trimCurlyBrackets(String input) {
        return input
                .replace(BRACKET_START, StringUtils.EMPTY)
                .replace(BRACKET_END, StringUtils.EMPTY);
    }
}
