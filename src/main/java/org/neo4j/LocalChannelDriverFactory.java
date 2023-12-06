package org.neo4j;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.local.LocalAddress;
import org.neo4j.driver.Config;
import org.neo4j.driver.internal.BoltAgent;
import org.neo4j.driver.internal.ConnectionSettings;
import org.neo4j.driver.internal.DriverFactory;
import org.neo4j.driver.internal.async.connection.ChannelConnector;
import org.neo4j.driver.internal.cluster.RoutingContext;
import org.neo4j.driver.internal.security.SecurityPlan;

import java.time.Clock;

public class LocalChannelDriverFactory extends DriverFactory {
	private final LocalAddress localAddress;

	public LocalChannelDriverFactory(LocalAddress localAddress) {
		this.localAddress = localAddress;
	}

	@Override
	protected Bootstrap createBootstrap(int threadCount) {
		return LocalChannelBootstrapFactory.newBootstrap(threadCount);
	}
	@Override
	protected ChannelConnector createConnector(ConnectionSettings settings, SecurityPlan securityPlan, Config config, Clock clock, RoutingContext routingContext, BoltAgent boltAgent) {
		return new LocalChannelConnector(localAddress, config.userAgent(), boltAgent, settings.authTokenProvider(), config.notificationConfig(), clock, config.logging());
	}
}
