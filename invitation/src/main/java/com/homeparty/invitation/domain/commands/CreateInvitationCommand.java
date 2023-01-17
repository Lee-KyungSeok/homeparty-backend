package com.homeparty.invitation.domain.commands;

import abstraction.command.Command;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@Getter
@ToString
public class CreateInvitationCommand implements Command {
    private UUID invitationId;
    private UUID hostId;
    private String title;
    private String description;
    private String type;
    private String dressCodes;
    private String foods;
    private LocalDateTime partiedAt;

    private String locationName;
    private BigDecimal latitude;
    private BigDecimal longitude;
}
