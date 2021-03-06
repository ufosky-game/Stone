package com.stone.core.akka.typed;

import scala.concurrent.Future;
import akka.actor.ActorSystem;
import akka.actor.TypedActor;
import akka.actor.TypedProps;
import akka.dispatch.OnSuccess;
import akka.japi.Creator;
import akka.japi.Option;

public class TypedActorTest {

	public static void main(String[] args) {
		ActorSystem system = ActorSystem.create("TypedSystem");
		@SuppressWarnings("serial")
		IPlayer player = TypedActor.get(system).typedActorOf(new TypedProps<PlayerTester>(IPlayer.class, new Creator<PlayerTester>() {

			@Override
			public PlayerTester create() throws Exception {
				// TODO Auto-generated method stub
				return new PlayerTester("foo");
			}
		}));

		// 1. fire and forget
		player.levelUpDontCare(10);
		// 2. request and blocking wait request
		Option<Integer> waitDamage = player.beatNow(100);
		System.out.println(waitDamage.get());
		// 3. request and future listener
		Future<Integer> futureDamage = player.beatFuture(100);
		futureDamage.onSuccess(new OnSuccess<Integer>() {

			@Override
			public void onSuccess(Integer result) throws Throwable {
				System.out.println(result);
			}
			
		}, system.dispatcher());
	}
}
