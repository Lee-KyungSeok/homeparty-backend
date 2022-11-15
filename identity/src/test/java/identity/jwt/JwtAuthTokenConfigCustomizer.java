package identity.jwt;

import autoparams.customization.Customizer;
import autoparams.generator.ObjectContainer;
import autoparams.generator.ObjectGenerator;

public class JwtAuthTokenConfigCustomizer implements Customizer {

    @Override
    public ObjectGenerator customize(ObjectGenerator generator) {
        return (query, context) -> query.getType().equals(JwtAuthTokenConfig.class)
                ? new ObjectContainer(factory())
                : generator.generate(query, context);
    }

    private JwtAuthTokenConfig factory() {
        String secretKey = "basljdbfjhbdfjbeyfbev123sQEE0R44Zy345nlTYv8Gawxbc9vAHgUjlhruX2LSfxiLyeRqjn73J4";
        return new JwtAuthTokenConfig(secretKey);
    }

}
