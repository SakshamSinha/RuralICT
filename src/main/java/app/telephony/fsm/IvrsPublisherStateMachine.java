package app.telephony.fsm;
import java.util.Dictionary;

import app.telephony.fsm.action.*;

import com.continuent.tungsten.commons.patterns.fsm.Action;
import com.continuent.tungsten.commons.patterns.fsm.FiniteStateException;
import com.continuent.tungsten.commons.patterns.fsm.Guard;
import com.continuent.tungsten.commons.patterns.fsm.State;
import com.continuent.tungsten.commons.patterns.fsm.StateMachine;
import com.continuent.tungsten.commons.patterns.fsm.StateTransitionMap;

import in.ac.iitb.ivrs.telephony.base.IVRSession;
import in.ac.iitb.ivrs.telephony.base.fsm.EventGuard;
import in.ac.iitb.ivrs.telephony.base.fsm.IVRStateTransitionMap;
import in.ac.iitb.ivrs.telephony.base.fsm.guards.OnGotDTMFKey;

public class IvrsPublisherStateMachine extends StateMachine<IVRSession>{
	
	
	/**
	 * The transition map that holds the rules for the state machine. Each state machine object is instantiated with
	 * this map.
	 */
	static StateTransitionMap<IVRSession> transitionMap;

	static {
		try {
			transitionMap = buildTransitionMap();
		} catch (FiniteStateException e) {
			e.printStackTrace();
		}
	}

	public IvrsPublisherStateMachine(StateTransitionMap<IVRSession> map,
			IVRSession entity) {
		super(map, entity);
	
	}

	/**
	 * Builds the state transition map for the state machine.
	 * @return The state transition map
	 * @throws FiniteStateException
	 */
	static StateTransitionMap<IVRSession> buildTransitionMap() throws FiniteStateException {
		IVRStateTransitionMap map = new IVRStateTransitionMap();

		/* ACTIONS */

		// asking user for action
		Action<IVRSession> askConfirmBroadcastMessageAction = new AskConfirmBroadcastMessageAction();
		Action<IVRSession> askBroadcastMediumAction = new AskBroadcastMediumAction();
		Action<IVRSession> askChooseGroupAction = new AskChooseGroupAction();
		//Action<IVRSession> askSelectGroupAction = new AskSelectGroupAction();
		Action<IVRSession> askEnterGroupIDAction = new AskEnterGroupIDAction();

		// actions that do something with the system
		Action<IVRSession> doRecordMessageAction = new DoRecordMessageAction();
		Action<IVRSession> doStoreBroadcastMessageAction = new DoStoreBroadcastMessageAction();
		Action<IVRSession> doDisconnectAction = new DoDisconnectAction();
		Action<IVRSession> doEndAction = new DoEndAction();
		Action<IVRSession> doInvalidInputAction = new DoInvalidInputAction();		

		// playing a message to the user
		Action<IVRSession> playMessageDiscardedAction = new PlayMessageDiscardedAction();
		Action<IVRSession> playGroupSelectedAction = new PlayGroupSelectedAction();
		Action<IVRSession> playInvalidGroupAction = new PlayInvalidGroupAction();
		Action<IVRSession> playGroupIDsAction = new PlayGroupIDsAction();
		Action<IVRSession> playThankYouMessageAction = new PlayThankYouMessageAction();
		
		/* STATES */

		// start state
		State<IVRSession> start = map.addStartState("Start", null);

		// end state
		State<IVRSession> end = map.addEndState("End", doEndAction);

		// parent state for the entire call flow
		State<IVRSession> callFlow = map.addActiveState("CallFlow", null, null);

		// state to initiate disconnection
		State<IVRSession> disconnect = map.addActiveState("Disconnect", callFlow, doDisconnectAction);

		State<IVRSession> recordBroadcast = map.addActiveState("RecordBroadcast", callFlow, doRecordMessageAction);
		State<IVRSession> broadcastMedium = map.addActiveState("BroadcastMedium", callFlow, askBroadcastMediumAction);
		State<IVRSession> confirmPCMessage = map.addActiveState("ConfirmPCMessage", callFlow, askConfirmBroadcastMessageAction);
		State<IVRSession> choosePhoneGroup = map.addActiveState("ChoosePhoneGroup", callFlow, askChooseGroupAction);
		State<IVRSession> playGroupIDs = map.addActiveState("PlayGroupIDs", callFlow, playGroupIDsAction);
		State<IVRSession> enterGroupID = map.addActiveState("EnterGroupID", callFlow, askEnterGroupIDAction);
		State<IVRSession> publisherExit = map.addActiveState("PublisherExit", callFlow, playThankYouMessageAction);

		/* CUSTOM GUARD CONDITIONS */

		Guard<IVRSession, Object> onGotDTMFKeyNot1nor2 = new OnGotDTMFKey(new String[] {"1", "2"}, false);
		Guard<IVRSession, Object> onGotDTMFKey1 = new OnGotDTMFKey(new String[] {"1"}, true);
		Guard<IVRSession, Object> onGotDTMFKey2 = new OnGotDTMFKey(new String[] {"2"}, true);
		Guard<IVRSession, Object> onDTMFGroupIDExist = new OnGotDTMFKey(
				new String[] {"54"} //TODO: service that returns string
				, true); 			   //array containing group IDs
		Guard<IVRSession, Object> onDTMFGroupIDNotExist = new OnGotDTMFKey(
				new String[] {"54"} //TODO: service that returns string
				, false); 			   //array containing group IDs
		
		/* TRANSITIONS */

		// transitions from start
		map.allowTransition(start, EventGuard.onNewCall, recordBroadcast,null);

		// transitions from main menu
		map.allowTransition(recordBroadcast, EventGuard.proceed, broadcastMedium, null);

		// transitions from broadcastMedium
		map.allowTransition(broadcastMedium, onGotDTMFKey2, confirmPCMessage, null);
		map.allowTransition(broadcastMedium, onGotDTMFKey1, choosePhoneGroup, null);
		map.allowTransition(broadcastMedium, onGotDTMFKeyNot1nor2, broadcastMedium, doInvalidInputAction);
		
		// transitions from confirmPCMessage
		map.allowTransition(confirmPCMessage, onGotDTMFKey1, publisherExit, doStoreBroadcastMessageAction);
		map.allowTransition(confirmPCMessage, onGotDTMFKey2, recordBroadcast, playMessageDiscardedAction);
		map.allowTransition(confirmPCMessage, onGotDTMFKeyNot1nor2, confirmPCMessage,doInvalidInputAction);
		
		// transitions from choosePhoneGroup
	    map.allowTransition(choosePhoneGroup, onGotDTMFKey1, playGroupIDs, null);
	    map.allowTransition(choosePhoneGroup, onGotDTMFKey2, enterGroupID, null);
	    map.allowTransition(choosePhoneGroup, onGotDTMFKeyNot1nor2, choosePhoneGroup, playGroupIDsAction);
	    
	    // transitions from playGroupIDs
	    map.allowTransition(playGroupIDs, EventGuard.proceed, enterGroupID, null);
	    
	    // transitions from enterGroupID
	    map.allowTransition(enterGroupID, onDTMFGroupIDExist, publisherExit, playGroupSelectedAction);
	    map.allowTransition(enterGroupID, onDTMFGroupIDNotExist, choosePhoneGroup, playInvalidGroupAction);
        
	    // transitions from publisherExit
	    map.allowTransition(publisherExit, EventGuard.proceed, end, null);
	    
	    // transitions from call flow
	 	map.allowTransition(callFlow, EventGuard.onDisconnect, end, null);
	 		
	    
		/* Build this map */
		map.build();
		return map;
	}

}

