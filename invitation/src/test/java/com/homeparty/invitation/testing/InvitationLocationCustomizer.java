package com.homeparty.invitation.testing;

import autoparams.customization.Customizer;
import autoparams.generator.ObjectContainer;
import autoparams.generator.ObjectGenerationContext;
import autoparams.generator.ObjectGenerator;
import com.homeparty.invitation.domain.aggregates.invitation.InvitationLocation;

import java.math.BigDecimal;
import java.util.concurrent.ThreadLocalRandom;

public class InvitationLocationCustomizer implements Customizer {

    @Override
    public ObjectGenerator customize(ObjectGenerator generator) {
        return (query, context) -> query.getType().equals(InvitationLocation.class)
                ? new ObjectContainer(factory(context))
                : generator.generate(query, context);
    }

    private InvitationLocation factory(ObjectGenerationContext context) {
        String name = (String) context.generate(() -> String.class);
        BigDecimal latitude = BigDecimal.valueOf(ThreadLocalRandom.current().nextDouble(-150, 150));
        BigDecimal longitude = BigDecimal.valueOf(ThreadLocalRandom.current().nextDouble(-150, 150));
        return new InvitationLocation(name, latitude, longitude);
    }
}