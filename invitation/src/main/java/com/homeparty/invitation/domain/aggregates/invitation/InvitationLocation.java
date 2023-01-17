package com.homeparty.invitation.domain.aggregates.invitation;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Embeddable
@Getter
@Builder
@AllArgsConstructor
@ToString
@Slf4j
public class InvitationLocation {

    @Column(name = "location_name")
    private String name;

    @Column(name = "latitude")
    private BigDecimal latitude;

    @Column(name = "longitude")
    private BigDecimal longitude;

    protected InvitationLocation() {
    }
}
