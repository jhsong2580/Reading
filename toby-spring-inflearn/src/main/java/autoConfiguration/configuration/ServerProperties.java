package autoConfiguration.configuration;

import autoConfiguration.MyConfigurationProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@MyConfigurationProperties(prefix = "context")
public class ServerProperties {
    @Value("${context.path}")
    private String path;
    @Value("${context.port}")
    private int port;

    public ServerProperties(String path, int port) {
        this.path = path;
        this.port = port;
    }

    public ServerProperties() {
    }

    public String getPath() {
        return path;
    }

    public int getPort() {
        return port;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
