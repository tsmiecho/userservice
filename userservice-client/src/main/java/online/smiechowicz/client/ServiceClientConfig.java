package online.smiechowicz.client;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

@Getter
public class ServiceClientConfig {
    private final String url;
    private final int retries;
    private final int retryDelay;

    @Builder
    protected ServiceClientConfig(@NonNull String url, int retries, int retryDelay) {
        this.url = url;
        this.retries = retries;
        this.retryDelay = retryDelay;
    }
}
