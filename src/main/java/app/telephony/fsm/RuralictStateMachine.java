package app.telephony.fsm;
import java.util.ArrayList;
import java.util.HashMap;

import app.telephony.fsm.action.*;
import app.telephony.fsm.action.member.AskConfirmFeedbackAction;
import app.telephony.fsm.action.member.AskConfirmOrderAction;
import app.telephony.fsm.action.member.AskForLanguageAction;
import app.telephony.fsm.action.member.AskForOrderMenuAction;
import app.telephony.fsm.action.member.AskForResponseAction;
import app.telephony.fsm.action.member.AskForResponseTypeAction;
import app.telephony.fsm.action.member.DoAskPlayFeedbackMessagesAction;
import app.telephony.fsm.action.member.DoStoreFeedbackMessageAction;
import app.telephony.fsm.action.member.DoStoreOrderMessageAction;
import app.telephony.fsm.action.member.PlayFeedbackRecordAction;
import app.telephony.fsm.action.member.PlayInvalidOrderAction;
import app.telephony.fsm.action.member.PlayOrderCancelAction;
import app.telephony.fsm.action.member.AskOrderIDAction;
import app.telephony.fsm.action.member.PlayOrderRecordAction;
import app.telephony.fsm.action.member.PlayResponeIsNoAction;
import app.telephony.fsm.action.member.PlayResponeIsYesAction;
import app.telephony.fsm.action.member.PlayWelcomeMessageAction;
import app.telephony.fsm.guards.OnGroupIDExist;
import app.telephony.fsm.guards.OnIsPublisher;
import app.telephony.fsm.guards.OnLanguageSelect;
import app.telephony.fsm.guards.OnOrderIDExist;

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

public class RuralictStateMachine extends StateMachine<IVRSession>{
	
	
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

	public RuralictStateMachine(IVRSession entity) {
		super(transitionMap, entity);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Builds the state transition map for the state machine.
	 * @return The state transition map
	 * @throws FiniteStateException
	 */
	static StateTransitionMap<IVRSession> buildTransitionMap() throws FiniteStateException {
		IVRStateTransitionMap map = new IVRStateTransitionMap();
		
		
		/* RUDIMENTARY VARIABLES */
		HashMap<String,String> tempLanguageMap = new HashMap<String, String>();
		tempLanguageMap.put("1", "Hindi");
		tempLanguageMap.put("2", "Marathi");
		tempLanguageMap.put("3", "English");

		/* ACTIONS FOR USER CALL FLOW  */

		// asking user for action
		Action<IVRSession> askForLanguageAction = new AskForLanguageAction();
		Action<IVRSession> askForResponseTypeAction = new AskForResponseTypeAction();
		Action<IVRSession> askForOrderMenuAction = new AskForOrderMenuAction();
		Action<IVRSession> askForResponseAction = new AskForResponseAction();
		Action<IVRSession> askConfirmOrderAction = new AskConfirmOrderAction();
		Action<IVRSession> askOrderIDAction = new AskOrderIDAction();
		Action<IVRSession> askConfirmFeedbackAction = new AskConfirmFeedbackAction();

		// actions that do something with the system
		Action<IVRSession> doAskPlayFeedbackMessagesAction = new DoAskPlayFeedbackMessagesAction();
		//Action<IVRSession> doRecordOrderMessageAction = new DoRecordOrderMessageAction();
		Action<IVRSession> doStoreOrderMessageAction = new DoStoreOrderMessageAction();
		Action<IVRSession> doStoreFeedbackMessageAction = new DoStoreFeedbackMessageAction();
		Action<IVRSession> doDisconnectAction = new DoDisconnectAction();
		Action<IVRSession> doEndAction = new DoEndAction();
		Action<IVRSession> doInvalidInputAction = new DoInvalidInputAction();		

		// playing a message to the user
		Action<IVRSession> playFeedbackRecordAction = new PlayFeedbackRecordAction();
		Action<IVRSession> playOrderRecordAction = new PlayOrderRecordAction();
		Action<IVRSession> playInvalidOrderAction = new PlayInvalidOrderAction();
		Action<IVRSession> playThankYouMessageAction = new PlayThankYouMessageAction();
		Action<IVRSession> playWelcomeMessageAction = new PlayWelcomeMessageAction();
		Action<IVRSession> playOrderCancelAction = new PlayOrderCancelAction();
		Action<IVRSession> playResponseIsNoAction = new PlayResponeIsNoAction();
		Action<IVRSession> playResponseIsYesAction = new PlayResponeIsYesAction();
			
		/* ACTIONS FOR PUBLISHER CALL FLOW */

		// asking user for action
		Action<IVRSession> askConfirmBroadcastMessageAction = new AskConfirmBroadcastMessageAction();
		Action<IVRSession> askBroadcastMediumAction = new AskBroadcastMediumAction();
		Action<IVRSession> askChooseGroupAction = new AskChooseGroupAction();
		Action<IVRSession> askEnterGroupIDAction = new AskEnterGroupIDAction();

		// actions that do something with the system
		Action<IVRSession> doRecordMessageAction = new DoRecordMessageAction();
		Action<IVRSession> doStoreBroadcastMessageAction = new DoStoreBroadcastMessageAction();
		
		// playing a message to the user
		Action<IVRSession> playMessageDiscardedAction = new PlayMessageDiscardedAction();
		Action<IVRSession> playGroupSelectedAction = new PlayGroupSelectedAction();
		Action<IVRSession> playInvalidGroupAction = new PlayInvalidGroupAction();
		Action<IVRSession> playGroupIDsAction = new PlayGroupIDsAction();
		
		/* STATES */

		// start state
		State<IVRSession> start = map.addStartState("Start", null);

		// state to check caller role
		State<IVRSession> checkCallerRole = map.addActiveState("CheckCallerRole", null, null);
		
		// end state
		State<IVRSession> end = map.addEndState("End", doEndAction);

		// parent state for the entire call flow
		State<IVRSession> parentCallFlow = map.addActiveState("ParentCallFlow", null, null);
		
		// parent states for the respective call flows
		State<IVRSession> userCallFlow = map.addActiveState("UserCallFlow", parentCallFlow, null);
		State<IVRSession> publisherCallFlow = map.addActiveState("PublisherCallFlow", parentCallFlow, null);

		// state to initiate disconnection
	//	State<IVRSession> disconnect = map.addActiveState("Disconnect", parentCallFlow, doDisconnectAction);

		//states for user call flow
		State<IVRSession> userStart = map.addActiveState("UserStart", userCallFlow, playWelcomeMessageAction);
		State<IVRSession> languageMenu = map.addActiveState("LanguageMenu", userCallFlow, askForLanguageAction);
		State<IVRSession> responseType = map.addActiveState("ResponseType", userCallFlow, askForResponseTypeAction);
		State<IVRSession> recordFeedback = map.addActiveState("RecordFeedback", userCallFlow,playFeedbackRecordAction);
		State<IVRSession> orderMenu = map.addActiveState("OrderMenu", userCallFlow, askForOrderMenuAction);
		State<IVRSession> confirmFeedbackMessage = map.addActiveState("ConfirmFeedbackMessage", userCallFlow, askConfirmFeedbackAction);
		State<IVRSession> recordOrder = map.addActiveState("RecordOrder", userCallFlow, playOrderRecordAction);
		State<IVRSession> enterOrderID = map.addActiveState("EnterOrderID", userCallFlow, askOrderIDAction);
        State<IVRSession> responseMenu = map.addActiveState("ResponseMenu",userCallFlow,askForResponseAction);
		State<IVRSession> customerExit = map.addActiveState("CustomerExit",userCallFlow,playThankYouMessageAction);
		/*State<IVRSession> listenFeedbackMessages = map.addActiveState("ListenFeedbackMessages",userCallFlow, doAskPlayFeedbackMessagesAction);*/
		
		//states for publisher call flow

		State<IVRSession> recordBroadcast = map.addActiveState("RecordBroadcast", publisherCallFlow, doRecordMessageAction);
		State<IVRSession> broadcastMedium = map.addActiveState("BroadcastMedium", publisherCallFlow, askBroadcastMediumAction);
		State<IVRSession> confirmPCMessage = map.addActiveState("ConfirmPCMessage", publisherCallFlow, askConfirmBroadcastMessageAction);
		State<IVRSession> choosePhoneGroup = map.addActiveState("ChoosePhoneGroup", publisherCallFlow, askChooseGroupAction);
		State<IVRSession> playGroupIDs = map.addActiveState("PlayGroupIDs", publisherCallFlow, playGroupIDsAction);
		State<IVRSession> enterGroupID = map.addActiveState("EnterGroupID", publisherCallFlow, askEnterGroupIDAction);
		State<IVRSession> publisherExit = map.addActiveState("PublisherExit", publisherCallFlow, playThankYouMessageAction);

		/* CUSTOM GUARD CONDITIONS */

		Guard<IVRSession, Object> onGotDTMFKeyNot1nor2 = new OnGotDTMFKey(new String[] {"1", "2"}, false);
		Guard<IVRSession, Object> onGotDTMFKey1 = new OnGotDTMFKey(new String[] {"1"}, true);
		Guard<IVRSession, Object> onGotDTMFKey2 = new OnGotDTMFKey(new String[] {"2"}, true);
		Guard<IVRSession, Object> onDTMFGroupIDExist = new OnGroupIDExist(true);
		Guard<IVRSession, Object> onDTMFGroupIDNotExist = new OnGroupIDExist(false);
		Guard<IVRSession, Object> onGotDTMFKeyNot1nor2nor3 = new OnGotDTMFKey(new String[] {"1", "2" ,"3"}, false);
		Guard<IVRSession, Object> onLanguageSelect = new OnLanguageSelect(tempLanguageMap);
		Guard<IVRSession, Object> onGotDTMFKey3 = new OnGotDTMFKey(new String[] {"3"}, true);
		Guard<IVRSession, Object> onDTMFOrderIDExist = new OnOrderIDExist(true);
		Guard<IVRSession, Object> onDTMFOrderIDNotExist = new OnOrderIDExist(false);
		Guard<IVRSession, Object> onIsPublisher = new OnIsPublisher(true);
		Guard<IVRSession, Object> onIsUser = new OnIsPublisher(false);
		
		/* TRANSITIONS */

		// transitions from start
		map.allowTransition(start, EventGuard.onNewCall, checkCallerRole, null);
		
		// transitions from checkCallerRole
		map.allowTransition(checkCallerRole, onIsPublisher , recordBroadcast, null);
		map.allowTransition(checkCallerRole, onIsUser , userStart, null);

		// transitions from main menu
		map.allowTransition(userStart, EventGuard.proceed,languageMenu, null);

		// transitions from responseType(Order , Feedback , Response)
		map.allowTransition(responseType, onGotDTMFKey1,orderMenu, null);
		map.allowTransition(responseType, onGotDTMFKey2,recordFeedback, doAskPlayFeedbackMessagesAction);
		map.allowTransition(responseType,onGotDTMFKey3,responseMenu,null);
		map.allowTransition(responseType, onGotDTMFKeyNot1nor2nor3,responseType, doInvalidInputAction);
		
		// transitions from langugaeMenu(Hindi , English , Marathi)
		//map.allowTransition(languageMenu, onGotDTMFKey1,responseType, null);
		//map.allowTransition(languageMenu, onGotDTMFKey2, responseType, null);
		map.allowTransition(languageMenu, onLanguageSelect, responseType, null);
		map.allowTransition(languageMenu, onGotDTMFKeyNot1nor2nor3,languageMenu,doInvalidInputAction);
		
		// transitions from recordFeedback
	    map.allowTransition(recordFeedback, onGotDTMFKey1, confirmFeedbackMessage, null);
	    map.allowTransition(recordFeedback, onGotDTMFKey2, recordFeedback, null);
	    map.allowTransition(recordFeedback, onGotDTMFKeyNot1nor2, recordFeedback, doInvalidInputAction);
	    
	    // transitions from responseMenu(Yes or No)
	    map.allowTransition(responseMenu, onGotDTMFKey1,customerExit ,playResponseIsYesAction);
	    map.allowTransition(responseMenu,onGotDTMFKey2, customerExit, playResponseIsNoAction);
	    map.allowTransition(responseMenu,onGotDTMFKeyNot1nor2 ,responseMenu,null);
	    

/*	    map.allowTransition(confirmFeedbackMessage, onGotDTMFKey1, listenFeedbackMessages, null);
	    map.allowTransition(confirmFeedbackMessage, onGotDTMFKeyNot1,recordFeedback, null);*/
	    
	    // transitions from confirmFeedbackMessage
	    map.allowTransition(confirmFeedbackMessage, EventGuard.proceed,customerExit , doStoreFeedbackMessageAction);
        
	    // transitions from orderMenu
	    map.allowTransition(orderMenu, onGotDTMFKey1, recordOrder, null);
	    map.allowTransition(orderMenu, onGotDTMFKey2, enterOrderID, null);
	   
	    // transition from enterOrderID
	    map.allowTransition(enterOrderID,onDTMFOrderIDExist,customerExit, playOrderCancelAction);
	    map.allowTransition(enterOrderID, onDTMFOrderIDNotExist, enterOrderID, playInvalidOrderAction);
	 
	    // transitions from recordOrder
	    map.allowTransition(recordOrder, EventGuard.proceed,customerExit, doStoreOrderMessageAction);
	    
	    // transitions from call flow
	 	map.allowTransition(parentCallFlow, EventGuard.onDisconnect, end, null);
				
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
	 	map.allowTransition(parentCallFlow, EventGuard.onDisconnect, end, null);
	 		
	    
		/* Build this map */
		map.build();
		return map;
	}

}

