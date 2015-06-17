package app.telephony.fsm;

import in.ac.iitb.ivrs.telephony.base.IVRSession;

import in.ac.iitb.ivrs.telephony.base.fsm.EventGuard;
import in.ac.iitb.ivrs.telephony.base.fsm.IVRStateTransitionMap;
import in.ac.iitb.ivrs.telephony.base.fsm.guards.OnGotDTMFKey;

import java.util.HashMap;

import app.telephony.fsm.action.AskBroadcastMediumAction;
import app.telephony.fsm.action.AskChooseGroupAction;
import app.telephony.fsm.action.AskConfirmBroadcastMessageAction;
import app.telephony.fsm.action.AskEnterGroupIDAction;
import app.telephony.fsm.action.DoDisconnectAction;
import app.telephony.fsm.action.DoEndAction;
import app.telephony.fsm.action.DoInvalidInputAction;
import app.telephony.fsm.action.DoReceivedBroadcastMessageAction;
import app.telephony.fsm.action.DoRecordMessageAction;
import app.telephony.fsm.action.DoStoreBroadcastMessageAction;
import app.telephony.fsm.action.PlayGroupIDsAction;
import app.telephony.fsm.action.PlayGroupSelectedAction;
import app.telephony.fsm.action.PlayInvalidGroupAction;
import app.telephony.fsm.action.PlayMessageDiscardedAction;
import app.telephony.fsm.action.PlayRecordedBroadcastMessageAction;
import app.telephony.fsm.action.PlayThankYouMessageAction;
import app.telephony.fsm.action.member.AskConfirmFeedbackAction;
import app.telephony.fsm.action.member.AskConfirmOrderAction;
import app.telephony.fsm.action.member.AskForLanguageAction;
import app.telephony.fsm.action.member.AskForLanguageAndOtherAction;
import app.telephony.fsm.action.member.AskForOrderMenuAction;
import app.telephony.fsm.action.member.AskForResponseAction;
import app.telephony.fsm.action.member.AskForResponseTypeAction;
import app.telephony.fsm.action.member.AskOrderIDAction;
import app.telephony.fsm.action.member.DoAskPlayFeedbackMessagesAction;
import app.telephony.fsm.action.member.DoStoreFeedbackMessageAction;
import app.telephony.fsm.action.member.DoStoreOrderMessageAction;
import app.telephony.fsm.action.member.PlayFeedbackRecordAction;
import app.telephony.fsm.action.member.PlayInvalidOrderAction;
import app.telephony.fsm.action.member.PlayOrderCancelAction;
import app.telephony.fsm.action.member.PlayOrderRecordAction;
import app.telephony.fsm.action.member.PlayRecordedMessageAction;
import app.telephony.fsm.action.member.PlayResponeIsNoAction;
import app.telephony.fsm.action.member.PlayResponeIsYesAction;
import app.telephony.fsm.action.member.PlayUnRegisterMessageAction;
import app.telephony.fsm.action.member.PlayWelcomeMessageAction;
import app.telephony.fsm.guards.OnGroupIDExist;
import app.telephony.fsm.guards.OnIsPublisher;
import app.telephony.fsm.guards.OnIsUnRegisteredUser;
import app.telephony.fsm.guards.OnLanguageSelect;
import app.telephony.fsm.guards.OnOrderIDExist;
import app.telephony.fsm.guards.OnResponseType;
import app.telephony.fsm.guards.OnUniqueOption;

import com.continuent.tungsten.commons.patterns.fsm.Action;
import com.continuent.tungsten.commons.patterns.fsm.FiniteStateException;
import com.continuent.tungsten.commons.patterns.fsm.Guard;
import com.continuent.tungsten.commons.patterns.fsm.State;
import com.continuent.tungsten.commons.patterns.fsm.StateMachine;
import com.continuent.tungsten.commons.patterns.fsm.StateTransitionMap;

public class RuralictStateMachine extends StateMachine<IVRSession>{


	/**
	 * The transition map that holds the rules for the state machine. Each state machine object is instantiated with
	 * this map.
	 */
	static StateTransitionMap<IVRSession> transitionMap;

	public static HashMap<String,String> tempLanguageMap;
	public static HashMap<String,String> tempResponseMap;

	static {
		try {
			transitionMap = buildTransitionMap();
		} catch (FiniteStateException e) {
			e.printStackTrace();
		}
	}

	public RuralictStateMachine(IVRSession entity) {
		super(transitionMap, entity);

	}

	/**
	 * Builds the state transition map for the state machine.
	 * @return The state transition map
	 * @throws FiniteStateException
	 */
	static StateTransitionMap<IVRSession> buildTransitionMap() throws FiniteStateException {
		IVRStateTransitionMap map = new IVRStateTransitionMap();


		/* INITIALIZING RUDIMENTARY VARIABLES */
		tempLanguageMap = new HashMap<String, String>();
		tempLanguageMap.put("1", "mr");
		tempLanguageMap.put("2", "hi");
		tempLanguageMap.put("3", "en");


		tempResponseMap = new HashMap<String, String>();
		tempResponseMap.put("1", "Order");
		tempResponseMap.put("2", "Feedback");
		tempResponseMap.put("3", "Response");

		/* ACTIONS FOR USER CALL FLOW  */

		// asking user for action
		Action<IVRSession> askForLanguageAction = new AskForLanguageAction();
		Action<IVRSession> askForLanguageAndOtherAction = new AskForLanguageAndOtherAction();
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
		Action<IVRSession> playUnRegisterMessageAction = new PlayUnRegisterMessageAction();	
		Action<IVRSession> playRecordedBroadcastMessageAction = new PlayRecordedBroadcastMessageAction();

		/* ACTIONS FOR PUBLISHER CALL FLOW */

		// asking user for action
		Action<IVRSession> askConfirmBroadcastMessageAction = new AskConfirmBroadcastMessageAction();
		Action<IVRSession> askBroadcastMediumAction = new AskBroadcastMediumAction();
		Action<IVRSession> askChooseGroupAction = new AskChooseGroupAction();
		Action<IVRSession> askEnterGroupIDAction = new AskEnterGroupIDAction();

		// actions that do something with the system
		Action<IVRSession> doRecordMessageAction = new DoRecordMessageAction();
		Action<IVRSession> doStoreBroadcastMessageAction = new DoStoreBroadcastMessageAction();
		Action<IVRSession> doReceivedBroadcastMessageAction = new DoReceivedBroadcastMessageAction();

		// playing a message to the user
		Action<IVRSession> playMessageDiscardedAction = new PlayMessageDiscardedAction();
		Action<IVRSession> playGroupSelectedAction = new PlayGroupSelectedAction();
		Action<IVRSession> playInvalidGroupAction = new PlayInvalidGroupAction();
		Action<IVRSession> playGroupIDsAction = new PlayGroupIDsAction();
		Action<IVRSession> playRecordedMessageAction = new PlayRecordedMessageAction();

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
		State<IVRSession> UnRegisterCallFlow = map.addActiveState("UnRegisterCallFlow", parentCallFlow, null);


		//states for user call flow
		State<IVRSession> userStart = map.addActiveState("UserStart", userCallFlow, playWelcomeMessageAction);
		State<IVRSession> languageMenu = map.addActiveState("LanguageMenu", userCallFlow, askForLanguageAction);
		State<IVRSession> languageAndOtherMenu = map.addActiveState("languageAndOtherMenu", userCallFlow, askForLanguageAndOtherAction);
		State<IVRSession> responseType = map.addActiveState("ResponseType", userCallFlow, askForResponseTypeAction);
		//State<IVRSession> recordFeedback = map.addActiveState("RecordFeedback", userCallFlow,doAskPlayFeedbackMessagesAction);
		State<IVRSession> recordFeedback = map.addActiveState("RecordFeedback", userCallFlow,playFeedbackRecordAction);
		State<IVRSession> orderMenu = map.addActiveState("OrderMenu", userCallFlow, askForOrderMenuAction);
		State<IVRSession> confirmFeedbackMessage = map.addActiveState("ConfirmFeedbackMessage", userCallFlow, askConfirmFeedbackAction);
		State<IVRSession> recordOrder = map.addActiveState("RecordOrder", userCallFlow, playOrderRecordAction);
		State<IVRSession> enterOrderID = map.addActiveState("EnterOrderID", userCallFlow, askOrderIDAction);
		State<IVRSession> responseMenu = map.addActiveState("ResponseMenu",userCallFlow,askForResponseAction);
		State<IVRSession> customerExit = map.addActiveState("CustomerExit",userCallFlow,playThankYouMessageAction);
		State<IVRSession> unRegisterUser= map.addActiveState("UnRegisterUser",UnRegisterCallFlow,playUnRegisterMessageAction);
		State<IVRSession> dummyStateForResponse = map.addActiveState("DummyStateForResponse", userCallFlow, null);
		State<IVRSession> stateForResponse = map.addActiveState("StateForResponse", userCallFlow, null);
		State<IVRSession> dummyStateForOrder = map.addActiveState("DummyStateForOrder", userCallFlow, null);
		State<IVRSession> replayRecord = map.addActiveState("ReplayRecord", userCallFlow, playRecordedMessageAction);


		//states for publisher call flow

		State<IVRSession> recordBroadcast = map.addActiveState("RecordBroadcast", publisherCallFlow, doRecordMessageAction);
		State<IVRSession> broadcastMedium = map.addActiveState("BroadcastMedium", publisherCallFlow, askBroadcastMediumAction);
		State<IVRSession> confirmPCMessage = map.addActiveState("ConfirmPCMessage", publisherCallFlow, askConfirmBroadcastMessageAction);
		State<IVRSession> choosePhoneGroup = map.addActiveState("ChoosePhoneGroup", publisherCallFlow, askChooseGroupAction);
		State<IVRSession> playGroupIDs = map.addActiveState("PlayGroupIDs", publisherCallFlow, playGroupIDsAction);
		State<IVRSession> enterGroupID = map.addActiveState("EnterGroupID", publisherCallFlow, askEnterGroupIDAction);
		State<IVRSession> publisherExit = map.addActiveState("PublisherExit", publisherCallFlow, playThankYouMessageAction);
		State<IVRSession> replayRecordedBroadcast = map.addActiveState("ReplayRecordedBroadcast", publisherCallFlow, playRecordedBroadcastMessageAction);

		/* CUSTOM GUARD CONDITIONS */

		Guard<IVRSession, Object> onGotDTMFKeyNot1nor2 = new OnGotDTMFKey(new String[] {"1", "2"}, false);
		Guard<IVRSession, Object> onGotDTMFKey1 = new OnGotDTMFKey(new String[] {"1"}, true);
		Guard<IVRSession, Object> onGotDTMFKey2 = new OnGotDTMFKey(new String[] {"2"}, true);
		Guard<IVRSession, Object> onGotDTMFKey9 = new OnGotDTMFKey(new String[] {"9"}, true);
		Guard<IVRSession, Object> onDTMFGroupIDExist = new OnGroupIDExist(true);
		Guard<IVRSession, Object> onDTMFGroupIDNotExist = new OnGroupIDExist(false);
		Guard<IVRSession, Object> onGotDTMFKeyNot1nor2nor3 = new OnGotDTMFKey(new String[] {"1", "2" ,"3"}, false);
		Guard<IVRSession, Object> onGotDTMFKeyEmpty = new OnGotDTMFKey(new String[] {""}, true);
		Guard<IVRSession, Object> onLanguageSelect = new OnLanguageSelect();
		Guard<IVRSession, Object> onGotDTMFKey3 = new OnGotDTMFKey(new String[] {"3"}, true);
		Guard<IVRSession, Object> onDTMFOrderIDExist = new OnOrderIDExist(true);
		Guard<IVRSession, Object> onDTMFOrderIDNotExist = new OnOrderIDExist(false);
		Guard<IVRSession, Object> onIsPublisher = new OnIsPublisher(true);
		Guard<IVRSession, Object> onIsUser = new OnIsPublisher(false);

		Guard<IVRSession, Object> onGotDTMFOrder = new OnResponseType("Order");
		Guard<IVRSession, Object> onGotDTMFFeedback = new OnResponseType("Feedback");
		Guard<IVRSession, Object> onGotDTMFResponse = new OnResponseType("Response");
		Guard<IVRSession, Object> onIsUnRegisteredUser = new OnIsUnRegisteredUser(true);

		Guard<IVRSession, Object> onUniqueLanguage = new OnUniqueOption("language", "", true);
		Guard<IVRSession, Object> onNotUniqueLanguage = new OnUniqueOption("language", "", false);
		Guard<IVRSession, Object> onUniqueResponseFeedback = new OnUniqueOption("response", "Feedback", true);
		Guard<IVRSession, Object> onUniqueResponseOrder = new OnUniqueOption("response", "Order", true);
		Guard<IVRSession, Object> onUniqueResponseResponse = new OnUniqueOption("response", "Response", true);
		Guard<IVRSession, Object> onNotUniqueResponse = new OnUniqueOption("response", "", false);

		Guard<IVRSession, Object> onCancellationDisabled = new OnUniqueOption("orderMenu", "", true);
		Guard<IVRSession, Object> onCancellationEnabled = new OnUniqueOption("orderMenu", "", false);

		/* TRANSITIONS */

		// transitions from start
		map.allowTransition(start, EventGuard.onNewCall, checkCallerRole, null);

		// transitions from checkCallerRole
		map.allowTransition(checkCallerRole, onIsUnRegisteredUser, unRegisterUser, null);
		map.allowTransition(checkCallerRole, onIsPublisher , recordBroadcast, null);
		map.allowTransition(checkCallerRole, onIsUser , userStart, null);
	
		// transitions from main menu
		map.allowTransition(userStart, onUniqueLanguage, stateForResponse, null);
		map.allowTransition(userStart, onNotUniqueLanguage,languageMenu, null);

		map.allowTransition(stateForResponse, EventGuard.proceed, languageAndOtherMenu, null);
		map.allowTransition(languageAndOtherMenu,onGotDTMFKey1, languageMenu, null);
		map.allowTransition(languageAndOtherMenu, onGotDTMFKey9, dummyStateForResponse, null);

		// transitions from dummyStateForResponse
		map.allowTransition(dummyStateForResponse, onUniqueResponseOrder, dummyStateForOrder, null);
		map.allowTransition(dummyStateForResponse, onUniqueResponseFeedback, recordFeedback, null);
		map.allowTransition(dummyStateForResponse, onUniqueResponseResponse, responseMenu, null);
		map.allowTransition(dummyStateForResponse, onNotUniqueResponse, responseType, null);

		// transitions from dummyStateForOrder
		map.allowTransition(dummyStateForOrder, onCancellationDisabled, recordOrder, null);
		map.allowTransition(dummyStateForOrder, onCancellationEnabled, orderMenu, null);


		// transitions from unregisteredUser
		map.allowTransition(unRegisterUser,EventGuard.proceed ,customerExit, null);

		// transitions from responseType(Order , Feedback , Response)
		map.allowTransition(responseType, onGotDTMFOrder,dummyStateForOrder, null);
		map.allowTransition(responseType, onGotDTMFFeedback,recordFeedback, null);
		map.allowTransition(responseType, onGotDTMFResponse,responseMenu,null);
		map.allowTransition(responseType, onGotDTMFKeyNot1nor2nor3,responseType, doInvalidInputAction);


		// transition from recordfeedback
		map.allowTransition(recordFeedback, EventGuard.onRecord ,replayRecord, doAskPlayFeedbackMessagesAction);
		map.allowTransition(replayRecord, EventGuard.proceed, confirmFeedbackMessage, null);

		// transitions from confirm record
		map.allowTransition(confirmFeedbackMessage, onGotDTMFKey1, customerExit, doStoreFeedbackMessageAction);
		map.allowTransition(confirmFeedbackMessage, onGotDTMFKey2, recordFeedback, null);
		map.allowTransition(confirmFeedbackMessage, onGotDTMFKeyNot1nor2, responseMenu, null);

		// transitions from langugaeMenu(Hindi , English , Marathi)
		map.allowTransition(languageMenu, onLanguageSelect, dummyStateForResponse, null);
		map.allowTransition(languageMenu, onGotDTMFKeyNot1nor2nor3, languageMenu, doInvalidInputAction);
		map.allowTransition(languageMenu, onGotDTMFKeyEmpty, languageMenu, doInvalidInputAction);


		// transitions from responseMenu(Yes or No)
		map.allowTransition(responseMenu, onGotDTMFKey1,customerExit ,playResponseIsYesAction);
		map.allowTransition(responseMenu,onGotDTMFKey2, customerExit, playResponseIsNoAction);
		map.allowTransition(responseMenu,onGotDTMFKeyNot1nor2 ,responseMenu,null);

		// transitions from orderMenu
		map.allowTransition(orderMenu, onGotDTMFKey1, recordOrder, null);
		map.allowTransition(orderMenu, onGotDTMFKey2, enterOrderID, null);

		// transition from enterOrderID
		map.allowTransition(enterOrderID,onDTMFOrderIDExist,customerExit, playOrderCancelAction);
		map.allowTransition(enterOrderID, onDTMFOrderIDNotExist, enterOrderID, playInvalidOrderAction);

		// transitions from recordOrder
		map.allowTransition(recordOrder, EventGuard.onRecord, customerExit, doStoreOrderMessageAction);

		// transitions from call flow
		map.allowTransition(parentCallFlow, EventGuard.onDisconnect, end, null);

		// transitions from main menu
		map.allowTransition(recordBroadcast, EventGuard.onRecord ,replayRecordedBroadcast, doReceivedBroadcastMessageAction);
		map.allowTransition(replayRecordedBroadcast, EventGuard.proceed, broadcastMedium, null);

		// transitions from broadcastMedium
		map.allowTransition(broadcastMedium, onGotDTMFKey2, confirmPCMessage, null);
		map.allowTransition(broadcastMedium, onGotDTMFKey1, choosePhoneGroup, null);
		map.allowTransition(broadcastMedium, onGotDTMFKeyNot1nor2, broadcastMedium, doInvalidInputAction);

		// transitions from confirmPCMessage
		map.allowTransition(confirmPCMessage, onGotDTMFKey1, publisherExit, doStoreBroadcastMessageAction);
		map.allowTransition(confirmPCMessage, onGotDTMFKey2, recordBroadcast, playMessageDiscardedAction);
		map.allowTransition(confirmPCMessage, onGotDTMFKeyNot1nor2, confirmPCMessage,doInvalidInputAction);

		// transitions from choosePhoneGroup
		map.allowTransition(choosePhoneGroup, onGotDTMFKey1, enterGroupID, null);
		map.allowTransition(choosePhoneGroup, onGotDTMFKey2, playGroupIDs, null);
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

