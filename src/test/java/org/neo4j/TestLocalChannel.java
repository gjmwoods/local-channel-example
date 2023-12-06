package org.neo4j;

import io.netty.channel.local.LocalAddress;
import org.junit.jupiter.api.Test;
import org.neo4j.configuration.connectors.BoltConnectorInternalSettings;
import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Config;
import org.neo4j.driver.internal.security.StaticAuthTokenManager;
import org.neo4j.harness.Neo4jBuilders;

import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;

public class TestLocalChannel {

	@Test
	public void test() {
		String localChannelAddress = "test-channel";
		Neo4jBuilders.newInProcessBuilder().withConfig(BoltConnectorInternalSettings.enable_local_connector, true)
				.withConfig(BoltConnectorInternalSettings.local_channel_address, localChannelAddress)
				.build();

		var localDriverFactory  = new LocalChannelDriverFactory(new LocalAddress(localChannelAddress));

		try (var aDriver = localDriverFactory.newInstance(URI.create("bolt://ignored.com:7474"), new StaticAuthTokenManager(AuthTokens.none()), Config.defaultConfig())) {
			var result = aDriver.session().run("RETURN 1");
			assertThat(result.next().get(0).asInt()).isEqualTo(1);
		}
	}
}
