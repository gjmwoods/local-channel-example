package org.neo4j;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.local.LocalChannel;
import org.neo4j.driver.internal.async.connection.EventLoopGroupFactory;

public class LocalChannelBootstrapFactory {

	public static Bootstrap newBootstrap(int threadCount) {
		var bootstrap = new Bootstrap();
		bootstrap.group(EventLoopGroupFactory.newEventLoopGroup(threadCount));
		bootstrap.channel(LocalChannel.class);
		return bootstrap;
	}
}
