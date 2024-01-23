package fr.agaspardcilia.filestore.configuration.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * The application's properties.
 */
@ConfigurationProperties(prefix = "app", ignoreUnknownFields = false)
public class AppProperties {
    private Store store = new Store();

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }
}
