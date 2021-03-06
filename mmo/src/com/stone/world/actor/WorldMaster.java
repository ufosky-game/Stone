package com.stone.world.actor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import akka.actor.UntypedActor;

/**
 * The world master actor;
 * 
 * @author crazyjohn
 *
 */
public class WorldMaster extends UntypedActor {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public void onReceive(Object msg) throws Exception {
		logger.info("Received msg:" + msg);
	}

}
