package uk.co.rpl.demonstartionapi.mapping;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import uk.co.rpl.demonstartionapi.configuration.AppConfig;
import uk.co.rpl.demonstartionapi.controllers.dto.Status;

/**
 *
 * @author philip
 */
@Mapper(componentModel="spring")
public interface StatusMapper {
    @Mapping(target="status", expression="""
                                         java(online?"Online":"Offline")
                                         """)
    Status toStatus(boolean online, AppConfig src);
}
