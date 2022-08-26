package uk.co.rpl.demonstartionapi.controllers.dto;

import lombok.Data;

@Data
public class Status {
    public final String name = "Demonstration API";
    private String branch;
    private String version;
    private String buildTime;
    private String status;
}
