package com.to.security.auth.plugin.provider;

import java.io.Serializable;
import java.util.UUID;

import org.apache.ignite.IgniteCheckedException;
import org.apache.ignite.cluster.ClusterNode;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.internal.GridKernalContext;
import org.apache.ignite.internal.IgniteKernal;
import org.apache.ignite.internal.processors.security.GridSecurityProcessor;
import org.apache.ignite.internal.util.tostring.GridToStringExclude;
import org.apache.ignite.plugin.CachePluginContext;
import org.apache.ignite.plugin.CachePluginProvider;
import org.apache.ignite.plugin.ExtensionRegistry;
import org.apache.ignite.plugin.IgnitePlugin;
import org.apache.ignite.plugin.PluginConfiguration;
import org.apache.ignite.plugin.PluginContext;
import org.apache.ignite.plugin.PluginProvider;
import org.apache.ignite.plugin.PluginValidationException;
import org.jetbrains.annotations.Nullable;

import com.to.security.auth.plugin.CustomGridAuthPlugin;
import com.to.security.auth.plugin.DefaultCustomGridAuthPlugin;
import com.to.security.auth.plugin.configuration.CustomGridPluginConfiguration;
import com.to.security.auth.processors.CustomGridSecurityProcessor;

/**
 * 
 * @author Sai
 *
 */
public class CustomGridPluginProvider implements PluginProvider<CustomGridPluginConfiguration> {

    private static final String PLUGIN_NAME = "Ignite_Auth_Plugin";
    private static final String COPYRIGHT = "Copyright(c) Techouts.";
    private static final String VERSION = "0.1";
    
    private CustomGridPluginConfiguration pluginConfiguration;
    private CustomGridAuthPlugin plugin;
    
    
	@GridToStringExclude
	private GridKernalContext ctx;

    public String name() {
        return PLUGIN_NAME;
    }

    public String version() {
        return VERSION;
    }

    public String copyright() {
        return COPYRIGHT;
    }

    @SuppressWarnings("unchecked")
    public <T extends IgnitePlugin> T plugin() {
        return (T) plugin;
    }

    public void initExtensions(PluginContext ctx, ExtensionRegistry registry) {
        this.initPluginConfiguration(ctx);
    }

    @Nullable
    @SuppressWarnings("unchecked")
    public <T> T createComponent(PluginContext ctx, Class<T> cls) {
        if (cls.equals(GridSecurityProcessor.class))
        {
            if (ctx.grid().log().isDebugEnabled()) {
                ctx.grid().log().debug("Creating plugin: " + PLUGIN_NAME);
            }
            return (T) new CustomGridSecurityProcessor(((IgniteKernal) ctx.grid()).context());
        }
        return null;
    }

    public CachePluginProvider createCacheProvider(CachePluginContext ctx) {
        return null;
    }

    public void start(PluginContext ctx) throws IgniteCheckedException {
    }

    public void stop(boolean cancel) throws IgniteCheckedException {
    }

    public void onIgniteStart() throws IgniteCheckedException {
    }

    public void onIgniteStop(boolean cancel) {
    }

    @Nullable
    public Serializable provideDiscoveryData(UUID nodeId) {
        return null;
    }

    public void receiveDiscoveryData(UUID nodeId, Serializable data) {

    }

    public void validateNewNode(ClusterNode node) throws PluginValidationException {

    }

    public CustomGridPluginConfiguration getPluginConfiguration() {
        return pluginConfiguration;
    }

    private void initPluginConfiguration(PluginContext ctx) {
        IgniteConfiguration configuration = ctx.igniteConfiguration();

        if (configuration.getPluginConfigurations() != null) {
        	 PluginConfiguration[] pluginConfigurations = configuration.getPluginConfigurations();

            for(int i = 0; i < pluginConfigurations.length; i++) {
                PluginConfiguration pluginConfiguration = pluginConfigurations[i];

                if (pluginConfiguration instanceof CustomGridPluginConfiguration) {
                    this.pluginConfiguration = (CustomGridPluginConfiguration) pluginConfiguration;
                   }
            }
        }

		if (pluginConfiguration == null) {
			pluginConfiguration = new CustomGridPluginConfiguration();
		}
            plugin = new DefaultCustomGridAuthPlugin(this);
    }
}
