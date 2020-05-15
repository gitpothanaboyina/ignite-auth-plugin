package com.to.security.auth.plugin.configuration;

import org.apache.ignite.plugin.PluginConfiguration;

/**
 * 
 * @author Sai
 *
 */
public class CustomGridPluginConfiguration implements PluginConfiguration {

    private CustomGridPluginConfiguration configuration;

    public CustomGridPluginConfiguration() {
    }

    public CustomGridPluginConfiguration(CustomGridPluginConfiguration configuration) {
        this.configuration = configuration;
    }

    public void setConfiguration(CustomGridPluginConfiguration configuration) {
        this.configuration = configuration;
    }

    public CustomGridPluginConfiguration getConfiguration() {
        return configuration;
    }
}
