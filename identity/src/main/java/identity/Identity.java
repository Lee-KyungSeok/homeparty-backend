package identity;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
public class Identity {
    String id;
    String provider;

    protected Identity() {
    }

    public Identity(String id, String provider) {
        this.id = id;
        this.provider = provider;
    }

    public String getId() {
        return id;
    }

    public String getProvider() {
        return provider;
    }
}
