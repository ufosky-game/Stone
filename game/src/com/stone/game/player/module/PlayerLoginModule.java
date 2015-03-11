package com.stone.game.player.module;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import akka.actor.ActorRef;

import com.stone.core.msg.MessageParseException;
import com.stone.db.entity.HumanEntity;
import com.stone.db.entity.PlayerEntity;
import com.stone.db.msg.internal.InternalCreateRole;
import com.stone.db.msg.internal.InternalGetRoleList;
import com.stone.db.msg.internal.player.InternalGetRoleListResult;
import com.stone.db.msg.internal.player.InternalLoginResult;
import com.stone.game.msg.ProtobufMessage;
import com.stone.game.player.BasePlayerModule;
import com.stone.game.player.Player;
import com.stone.proto.Auths.CreateRole;
import com.stone.proto.Auths.Login;
import com.stone.proto.Auths.LoginResult;
import com.stone.proto.Auths.Role;
import com.stone.proto.Auths.RoleList;
import com.stone.proto.MessageTypes.MessageType;

public class PlayerLoginModule extends BasePlayerModule {
	/** logger */
	protected Logger logger = LoggerFactory.getLogger(PlayerLoginModule.class);

	public PlayerLoginModule(Player player) {
		super(player);
	}

	@Override
	public void onInternalMessage(Object msg, ActorRef playerActor) {
		if (msg instanceof InternalLoginResult) {
			InternalLoginResult loginResult = (InternalLoginResult) msg;
			handleLoginResult(loginResult, player, playerActor);
		} else if (msg instanceof InternalGetRoleListResult) {
			InternalGetRoleListResult roleList = (InternalGetRoleListResult) msg;
			handleRoleListResult(roleList, player);
		}
	}

	/**
	 * Handle the role list;
	 * 
	 * @param roleList
	 */
	private void handleRoleListResult(InternalGetRoleListResult roleList, Player player) {
		if (roleList.getHumanEntities().size() <= 0) {
			player.sendMessage(MessageType.GC_GET_ROLE_LIST_VALUE, RoleList.newBuilder());
		} else {
			RoleList.Builder roleListBuilder = RoleList.newBuilder();
			for (HumanEntity eachHuman : roleList.getHumanEntities()) {
				roleListBuilder.addRoleList(Role.newBuilder().setRoleId(eachHuman.getGuid()).setName(eachHuman.getName()));
			}
			player.sendMessage(MessageType.GC_GET_ROLE_LIST_VALUE, roleListBuilder);
		}
	}

	/**
	 * Handle the login result;
	 * 
	 * @param loginResult
	 */
	private void handleLoginResult(InternalLoginResult loginResult, Player player, ActorRef playerActor) {
		if (loginResult.getPlayerEntities().size() > 0) {
			PlayerEntity playerEntity = loginResult.getPlayerEntities().get(0);
			player.setPlayerId(playerEntity.getId());
			// change state
			// if (player.canTransferStateTo(PlayerState.AUTHORIZED)) {
			// player.transferStateTo(PlayerState.AUTHORIZED);
			// }
			logger.info(String.format("Player login, userName: %s", playerEntity.getUserName()));
			// send login result
			player.sendMessage(MessageType.GC_PLAYER_LOGIN_RESULT_VALUE, LoginResult.newBuilder().setSucceed(true));

		}
	}

	@Override
	public Player getPlayer() {
		return player;
	}

	@Override
	public void onExternalMessage(ProtobufMessage msg, ActorRef playerActor, ActorRef dbMaster) throws MessageParseException {
		if (msg.getType() == MessageType.CG_PLAYER_LOGIN_VALUE) {
			final Login.Builder login = msg.parseBuilder(Login.newBuilder());
			dbMaster.tell(login, playerActor);
		} else if (msg.getType() == MessageType.CG_GET_ROLE_LIST_VALUE) {
			// get role list
			InternalGetRoleList getRoleList = new InternalGetRoleList(player.getPlayerId());
			dbMaster.tell(getRoleList, playerActor);
		} else if (msg.getType() == MessageType.CG_CREATE_ROLE_VALUE) {
			// create role list
			CreateRole.Builder createRole = msg.parseBuilder(CreateRole.newBuilder());
			dbMaster.tell(new InternalCreateRole(player.getPlayerId(), createRole), playerActor);
		}
	}

}
