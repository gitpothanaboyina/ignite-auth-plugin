package com.to.security.auth.plugin;

import com.to.security.auth.plugin.configuration.CustomGridPluginConfiguration;
import com.to.security.auth.plugin.provider.CustomGridPluginProvider;


public class DefaultCustomGridAuthPlugin implements CustomGridAuthPlugin
{

    private CustomGridPluginProvider customGridPluginProvider;

    public DefaultCustomGridAuthPlugin(CustomGridPluginProvider pluginProvider) {
        this.customGridPluginProvider = pluginProvider;
    }

    public CustomGridPluginConfiguration getPluginConfiguration() {
        return customGridPluginProvider.getPluginConfiguration();
    }
}
