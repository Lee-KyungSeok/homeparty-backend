package identity.testing;

import autoparams.customization.CompositeCustomizer;

public class CommandsCustomizer extends CompositeCustomizer {

    public CommandsCustomizer() {
        super(
                new JwtAuthTokenConfigCustomizer(),
                new JwtAuthTokenGeneratorCustomizer()
        );
    }
}