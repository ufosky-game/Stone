package com.stone.actor.player.module;

import com.stone.actor.player.PlayerActor;

public abstract class BasePlayerModule implements IPlayerModule {
	protected PlayerActor player;

	@Override
	public PlayerActor getPlayer() {
		return player;
	}

}
