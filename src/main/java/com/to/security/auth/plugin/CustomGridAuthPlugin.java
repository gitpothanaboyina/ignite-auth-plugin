package com.to.security.auth.plugin;

import org.apache.ignite.plugin.IgnitePlugin;

import com.to.security.auth.plugin.configuration.CustomGridPluginConfiguration;

/**
 * @author Sai
 */
public interface CustomGridAuthPlugin extends IgnitePlugin {

    /**
     * @return Security plugin configuration
     */
    CustomGridPluginConfiguration getPluginConfiguration();

}
