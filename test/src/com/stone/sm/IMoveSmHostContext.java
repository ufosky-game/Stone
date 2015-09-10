/*
 * ex: set ro:
 * DO NOT EDIT.
 * generated by smc (http://smc.sourceforge.net/)
 * from file : IMoveSmHostContext.sm
 */

package com.stone.sm;

import com.stone.sm.host.EventType;
import com.stone.sm.host.IEvent;
import com.stone.sm.host.StateMachineMessages;
import com.stone.sm.host.IMoveSmHost;

public class IMoveSmHostContext
    extends statemap.FSMContext
{
//---------------------------------------------------------------
// Member methods.
//

    public IMoveSmHostContext(IMoveSmHost owner)
    {
        super (MapIMove.Default);

        _owner = owner;
    }

    public IMoveSmHostContext(IMoveSmHost owner, IMoveSmHostState initState)
    {
        super (initState);

        _owner = owner;
    }

    public void enterStartState()
    {
        getState().Entry(this);
        return;
    }

    public void onEvent(IEvent event)
    {
        _transition = "onEvent";
        getState().onEvent(this, event);
        _transition = "";
        return;
    }

    public void start()
    {
        _transition = "start";
        getState().start(this);
        _transition = "";
        return;
    }

    public IMoveSmHostState getState()
        throws statemap.StateUndefinedException
    {
        if (_state == null)
        {
            throw(
                new statemap.StateUndefinedException());
        }

        return ((IMoveSmHostState) _state);
    }

    protected IMoveSmHost getOwner()
    {
        return (_owner);
    }

    public void setOwner(IMoveSmHost owner)
    {
        if (owner == null)
        {
            throw (
                new NullPointerException(
                    "null owner"));
        }
        else
        {
            _owner = owner;
        }

        return;
    }

//---------------------------------------------------------------
// Member data.
//

    transient private IMoveSmHost _owner;

    public static abstract class IMoveSmHostState
        extends statemap.State
    {
    //-----------------------------------------------------------
    // Member methods.
    //

        protected IMoveSmHostState(String name, int id)
        {
            super (name, id);
        }

        protected void Entry(IMoveSmHostContext context) {}
        protected void Exit(IMoveSmHostContext context) {}

        protected void onEvent(IMoveSmHostContext context, IEvent event)
        {
            Default(context);
        }

        protected void start(IMoveSmHostContext context)
        {
            Default(context);
        }

        protected void Default(IMoveSmHostContext context)
        {
            throw (
                new statemap.TransitionUndefinedException(
                    "State: " +
                    context.getState().getName() +
                    ", Transition: " +
                    context.getTransition()));
        }

    //-----------------------------------------------------------
    // Member data.
    //
    }

    /* package */ static abstract class MapIMove
    {
    //-----------------------------------------------------------
    // Member methods.
    //

    //-----------------------------------------------------------
    // Member data.
    //

        //-------------------------------------------------------
        // Constants.
        //
        public static final MapIMove_Stand Stand =
            new MapIMove_Stand("MapIMove.Stand", 5);
        public static final MapIMove_Move Move =
            new MapIMove_Move("MapIMove.Move", 6);
        private static final MapIMove_Default Default =
            new MapIMove_Default("MapIMove.Default", -1);

    }

    protected static class MapIMove_Default
        extends IMoveSmHostState
    {
    //-----------------------------------------------------------
    // Member methods.
    //

        protected MapIMove_Default(String name, int id)
        {
            super (name, id);
        }

        protected void start(IMoveSmHostContext context)
        {
            IMoveSmHost ctxt = context.getOwner();


            (context.getState()).Exit(context);
            context.clearState();
            try
            {
                ctxt.setState(StateMachineMessages.MoveState.MOVE_STAND);
            }
            finally
            {
                context.setState(MapIMove.Stand);
                (context.getState()).Entry(context);
            }
            return;
        }
    //-----------------------------------------------------------
    // Member data.
    //
    }

    private static final class MapIMove_Stand
        extends MapIMove_Default
    {
    //-------------------------------------------------------
    // Member methods.
    //

        private MapIMove_Stand(String name, int id)
        {
            super (name, id);
        }

        protected void Entry(IMoveSmHostContext context)
            {
                IMoveSmHost ctxt = context.getOwner();

            ctxt.addListener(EventType.START_MOVE);
            return;
        }

        protected void Exit(IMoveSmHostContext context)
            {
            IMoveSmHost ctxt = context.getOwner();

            ctxt.removeListener();
            return;
        }

        protected void onEvent(IMoveSmHostContext context, IEvent event)
        {
            IMoveSmHost ctxt = context.getOwner();

            if (event.getType() == EventType.START_MOVE)
            {

                (context.getState()).Exit(context);
                context.clearState();
                try
                {
                    ctxt.setState(StateMachineMessages.MoveState.MOVE_MOVING);
                    ctxt.startMove(event);
                }
                finally
                {
                    context.setState(MapIMove.Move);
                    (context.getState()).Entry(context);
                }
            }
            else
            {
                super.onEvent(context, event);
            }

            return;
        }

    //-------------------------------------------------------
    // Member data.
    //
    }

    private static final class MapIMove_Move
        extends MapIMove_Default
    {
    //-------------------------------------------------------
    // Member methods.
    //

        private MapIMove_Move(String name, int id)
        {
            super (name, id);
        }

        protected void Entry(IMoveSmHostContext context)
            {
                IMoveSmHost ctxt = context.getOwner();

            ctxt.addListener(EventType.STOP_MOVE, EventType.SKILL_USE_START, EventType.CAST_SPELL_START, EventType.START_MOVE, EventType.DEAD, EventType.SLEEPED, EventType.STUN, EventType.MONSTER_START_SERVICE, EventType.CHANGE_PROPERTY, EventType.IMMOBILIZED);
            return;
        }

        protected void Exit(IMoveSmHostContext context)
            {
            IMoveSmHost ctxt = context.getOwner();

            ctxt.removeListener();
            return;
        }

        protected void onEvent(IMoveSmHostContext context, IEvent event)
        {
            IMoveSmHost ctxt = context.getOwner();

            if (event.getType() == EventType.STOP_MOVE || event.getType() == EventType.DEAD || event.getType() == EventType.SKILL_USE_START || event.getType() == EventType.CAST_SPELL_START || event.getType() == EventType.SLEEPED || event.getType() == EventType.STUN || event.getType() == EventType.IMMOBILIZED || event.getType() == EventType.MONSTER_START_SERVICE)
            {

                (context.getState()).Exit(context);
                context.clearState();
                try
                {
                    ctxt.setState(StateMachineMessages.MoveState.MOVE_STAND);
                    ctxt.stopMove();
                }
                finally
                {
                    context.setState(MapIMove.Stand);
                    (context.getState()).Entry(context);
                }
            }
            else if (event.getType() == EventType.START_MOVE)
            {
                IMoveSmHostState endState = context.getState();

                context.clearState();
                try
                {
                    ctxt.startMove(event);
                }
                finally
                {
                    context.setState(endState);
                }
            }
            else if (event.getType() == EventType.CHANGE_PROPERTY)
            {
                IMoveSmHostState endState = context.getState();

                context.clearState();
                try
                {
                    ctxt.speedChange(event);
                }
                finally
                {
                    context.setState(endState);
                }
            }            else
            {
                super.onEvent(context, event);
            }

            return;
        }

    //-------------------------------------------------------
    // Member data.
    //
    }
}

/*
 * Local variables:
 *  buffer-read-only: t
 * End:
 */