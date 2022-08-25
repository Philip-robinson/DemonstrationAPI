package uk.co.rpl.demonstartionapi.controllers.dto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Status {
    public final String name = "Demonstration API";
    public final String status;
}
