package app.business.controllers.rest;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import app.business.services.InboundCallService;
import app.business.services.OrderItemService;
import app.business.services.OrderService;
import app.business.services.OrganizationService;
import app.business.services.OutboundCallService;
import app.business.services.broadcast.TextBroadcastService;
import app.business.services.broadcast.VoiceBroadcastService;
import app.business.services.message.MessageService;
import app.entities.Group;
import app.entities.InboundCall;
import app.entities.Organization;
import app.entities.OutboundCall;
import app.entities.broadcast.Broadcast;
import app.entities.message.Message;
import app.util.Utils;

@RestController
@RequestMapping("/api/generateLogs")
public class GenerateLogsRestController {

	@Autowired
	OrderItemService orderItemService;
	@Autowired
	OrganizationService organizationService;
	@Autowired
	OrderService orderService;
	@Autowired
	MessageService messageService;
	@Autowired
	TextBroadcastService textBroadcastService;
	@Autowired
	VoiceBroadcastService voiceBroadcastService;
	@Autowired
	InboundCallService inboundCallService;
	@Autowired
	OutboundCallService outboundCallService;

	@RequestMapping(value="/logsGenerate", method=RequestMethod.GET, produces="text/plain")
	public @ResponseBody String generateLog(int org, @DateTimeFormat(pattern = "yyyy-MM-dd") Date fromTime, @DateTimeFormat(pattern = "yyyy-MM-dd") Date toTime) throws ParseException {
		Organization organization = organizationService.getOrganizationById(org);
		List<Group> groups = organizationService.getOrganizationGroupList(organization);	
		System.out.println("parameters are: "+org+" "+ fromTime+" "+toTime);
		//int orderrejectedappcount=0;
		int orderprocessedcount=0;
		int ordernewcount=0;
		int ordercancelledcount=0;
		int orderrejectedcount=0;
		int ordersavedcount=0;
		int orderprocessedappcount=0;
		int ordernewappcount=0;
		int ordercancelledappcount=0;
		int ordertotappcount=0;
		int ordertotivrscount=0;
		int textbroadcastcount=0;
		int voicebroadcastcount=0;
		int messagefeedtextcount=0;
		int messagefeedvoicecount=0;
		int messageposrespvoicecount=0;
		int messageposresptextcount=0;
		int messagenegrespvoicecount=0;
		int messagenegresptextcount=0;
		int messagetotrespvoicecount=0;
		int messagetotresptextcount=0;
		int inboundcallcount=0;
		int outboundcallcount=0;
//		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//	    Date parsedDate = (Date) dateFormat.parse("2015-07-01 00:00:00");
//	    Timestamp fromTime = new java.sql.Timestamp(parsedDate.getTime());
//	    parsedDate = (Date) dateFormat.parse("2015-09-01 00:00:00");
//	    Timestamp toTime = new java.sql.Timestamp(parsedDate.getTime());
		try {
			File dir = new File(Utils.getLogDir());
			if (!dir.exists())
				dir.mkdirs();
			FileWriter fw=new FileWriter(Utils.getLogDir()+"Log"+organization.getName()+".txt");
			//Order processed through app and web
			//Order cancelled through app and web
			//Order rejected through app and web
			//Order placed through app and web
			//Total text broadcasts made
			//Total voice broadcasts made
			//total inbound calls made
			//total outbound calls made
			List<Broadcast> textbrdcstlst= textBroadcastService.getTextBroadcast(organization);
			List<Broadcast> voicebrdcstlst= voiceBroadcastService.getVoiceBroadcast(organization);
			List<InboundCall> inboundcalllst= inboundCallService.getInboundCallList(organization);
			for(Group g: groups)
			{
				List<Message> messagepro=messageService.getMessageListByOrderStatus(g, "voice", "processed");
				List<Message> messagenew=messageService.getMessageListByOrderStatus(g, "voice", "new");
				List<Message> messagecan=messageService.getMessageListByOrderStatus(g, "voice", "cancelled");
				List<Message> messagesav=messageService.getMessageListByOrderStatus(g, "voice", "saved");
				List<Message> messagerej=messageService.getMessageListByOrderStatus(g, "voice", "rejected");
				List<Message> messagetotivrs=messageService.getMessageListByModeAndFormat(g, "web","voice");
				List<Message> messageapppro=messageService.getMessageListByOrderStatus(g, "binary", "processed");
				List<Message> messageappnew=messageService.getMessageListByOrderStatus(g, "binary", "saved");
				List<Message> messageappcan=messageService.getMessageListByOrderStatus(g, "binary", "cancelled");
				List<Message> messagetotapp=messageService.getMessageListByModeAndFormat(g, "app","binary");
				//List<Message> messageapprej=messageService.getMessageListByOrderStatus(g, "binary", "rejected");
				List<Message> messagefeedvoice=messageService.getFeedbackList(g, "voice");
				List<Message> messagefeedtext=messageService.getFeedbackList(g, "text");
				List<Message> messageposresponsevoice= messageService.getPositiveResponseListType(g, "voice");
				List<Message> messageposresponsetext= messageService.getPositiveResponseListType(g, "text");
				List<Message> messagetotresponsevoice= messageService.getResponseListType(g, "voice");
				List<Message> messagenegresponsevoice= messageService.getNegativeResponseListType(g, "voice");
				List<Message> messagenegresponsetext= messageService.getNegativeResponseListType(g, "text");
				List<Message> messagetotresponsetext= messageService.getResponseListType(g, "text");
				List<OutboundCall> outboundcalllst= outboundCallService.getOutboundCallList(g);
				for(Message m: messagetotapp)
				{
					if(m.getTime().after(fromTime) && m.getTime().before(toTime))
						if(m.getOrder()!=null)
							ordertotappcount=ordertotappcount+1;
				}
				for(Message m: messagetotivrs)
				{
					if(m.getTime().after(fromTime) && m.getTime().before(toTime))
						if(m.getOrder()!=null)
							ordertotivrscount=ordertotivrscount+1;
				}
				for(Message m: messagepro)
				{
					if(m.getTime().after(fromTime) && m.getTime().before(toTime))
						orderprocessedcount=orderprocessedcount+1;						
				}
				for(Message m: messageapppro)
				{
					if(m.getTime().after(fromTime) && m.getTime().before(toTime))
						orderprocessedappcount=orderprocessedappcount+1;						
				}
				for(Message m: messagenew)
				{
					if(m.getTime().after(fromTime) && m.getTime().before(toTime))
						ordernewcount=ordernewcount+1;
				}
				for(Message m: messageappnew)
				{
					if(m.getTime().after(fromTime) && m.getTime().before(toTime))
						ordernewappcount=ordernewappcount+1;
				}
				for(Message m: messagecan)
				{
					if(m.getTime().after(fromTime) && m.getTime().before(toTime))
						ordercancelledcount=ordercancelledcount+1;
				}
				for(Message m: messagesav)
				{
					if(m.getTime().after(fromTime) && m.getTime().before(toTime))
						ordersavedcount=ordersavedcount+1;
				}
				for(Message m: messageappcan)
				{
					if(m.getTime().after(fromTime) && m.getTime().before(toTime))
						ordercancelledappcount=ordercancelledappcount+1;
				}
				for(Message m: messagerej)
				{
					if(m.getTime().after(fromTime) && m.getTime().before(toTime))
						orderrejectedcount=orderrejectedcount+1;
				}
				/*for(Message m: messageapprej)
				{
					if(m.getTime().after(fromTime) && m.getTime().before(toTime))
						orderrejectedappcount=orderrejectedappcount+1;
				}*/
				for(Message m: messagefeedtext)
				{
					if(m.getTime().after(fromTime) && m.getTime().before(toTime))
					{
						messagefeedtextcount=messagefeedtextcount+1;
					}
						
				}
				for(Message m: messagefeedvoice)
				{
					if(m.getTime().after(fromTime) && m.getTime().before(toTime))
					{
						messagefeedvoicecount=messagefeedvoicecount+1;
					}
						
				}
				for(Message m: messageposresponsevoice)
				{
					if(m.getTime().after(fromTime) && m.getTime().before(toTime))
					{
						messageposrespvoicecount=messageposrespvoicecount+1;
					}
						
				}
				for(Message m: messageposresponsetext)
				{
					if(m.getTime().after(fromTime) && m.getTime().before(toTime))
					{
						messageposresptextcount=messageposresptextcount+1;
					}
						
				}
				for(Message m: messagenegresponsevoice)
				{
					if(m.getTime().after(fromTime) && m.getTime().before(toTime))
					{
						messagenegrespvoicecount=messagenegrespvoicecount+1;
					}
						
				}
				for(Message m: messagenegresponsetext)
				{
					if(m.getTime().after(fromTime) && m.getTime().before(toTime))
					{
						messagenegresptextcount=messagenegresptextcount+1;
					}
						
				}
				for(Message m: messagetotresponsevoice)
				{
					if(m.getTime().after(fromTime) && m.getTime().before(toTime))
					{
						messagetotrespvoicecount=messagetotrespvoicecount+1;
					}
						
				}
				for(Message m: messagetotresponsetext)
				{
					if(m.getTime().after(fromTime) && m.getTime().before(toTime))
					{
						messagetotresptextcount=messagetotresptextcount+1;
					}
						
				}
				for(OutboundCall ocall: outboundcalllst)
				{
					if(ocall.getBroadcastSchedule().getTime()!=null)
					if(ocall.getBroadcastSchedule().getTime().after(fromTime) && ocall.getBroadcastSchedule().getTime().before(toTime))
					{
						outboundcallcount=outboundcallcount+1;
					}
						
				}
			}
			for(Broadcast textbc: textbrdcstlst)
			{
				if(textbc.getBroadcastedTime()!=null)
				if(textbc.getBroadcastedTime().after(fromTime) && textbc.getBroadcastedTime().before(toTime))
				{
					textbroadcastcount=textbroadcastcount+1;
				}
					
			}
			for(Broadcast voicebc: voicebrdcstlst)
			{
				if(voicebc.getBroadcastedTime()!=null)
				if(voicebc.getBroadcastedTime().after(fromTime) && voicebc.getBroadcastedTime().before(toTime))
				{
					voicebroadcastcount=voicebroadcastcount+1;
				}
					
			}
			for(InboundCall icall: inboundcalllst)
			{
				if(icall.getTime()!=null)
				if(icall.getTime().after(fromTime) && icall.getTime().before(toTime))
				{
					inboundcallcount=inboundcallcount+1;
				}
					
			}
			fw.write("Log sheet from " + fromTime + " to "+ toTime);
			fw.write(System.getProperty( "line.separator" ));
			fw.write(System.getProperty( "line.separator" ));
			fw.write("For IVRS");
			fw.write(System.getProperty( "line.separator" ));
			fw.write("Number of orders placed in total: "+ordertotivrscount);
			fw.write(System.getProperty( "line.separator" ));
			fw.write("Number of orders processed: "+orderprocessedcount);
			fw.write(System.getProperty( "line.separator" ));
			fw.write("Number of orders saved: "+ordersavedcount);
			fw.write(System.getProperty( "line.separator" ));
			fw.write("Number of orders cancelled: "+ordercancelledcount);
			fw.write(System.getProperty( "line.separator" ));
			fw.write("Number of orders rejected: "+orderrejectedcount);
			fw.write(System.getProperty( "line.separator" ));
			fw.write("Number of orders pending: "+ordernewcount);
			fw.write(System.getProperty( "line.separator" ));
			fw.write("Number of voice Broadcasts made: "+voicebroadcastcount);
			fw.write(System.getProperty( "line.separator" ));
			fw.write("Number of voice feedbacks made: "+messagefeedvoicecount);
			fw.write(System.getProperty( "line.separator" ));
			fw.write("Number of voice positive response made: "+messageposrespvoicecount);
			fw.write(System.getProperty( "line.separator" ));
			fw.write("Number of voice negative response made: "+messagenegrespvoicecount);
			fw.write(System.getProperty( "line.separator" ));
			fw.write("Number of total voice response made: "+messagetotrespvoicecount);
			fw.write(System.getProperty( "line.separator" ));
			fw.write("Number of text Broadcasts made: "+textbroadcastcount);
			fw.write(System.getProperty( "line.separator" ));
			fw.write("Number of text feedbacks made: "+messagefeedtextcount);
			fw.write(System.getProperty( "line.separator" ));
			fw.write("Number of text positive response made: "+messageposresptextcount);
			fw.write(System.getProperty( "line.separator" ));
			fw.write("Number of text negative response made: "+messagenegresptextcount);
			fw.write(System.getProperty( "line.separator" ));
			fw.write("Number of total text response made: "+messagetotresptextcount);
			fw.write(System.getProperty( "line.separator" ));
			fw.write("Number of inbound calls recieved: "+inboundcallcount);
			fw.write(System.getProperty( "line.separator" ));
			fw.write("Number of outbound calls made: "+outboundcallcount);
			fw.write(System.getProperty( "line.separator" ));
			fw.write(System.getProperty( "line.separator" ));
			fw.write(System.getProperty( "line.separator" ));
			fw.write("From App");
			fw.write(System.getProperty( "line.separator" ));
			fw.write("Number of orders placed in total: "+ordertotappcount);
			fw.write(System.getProperty( "line.separator" ));
			fw.write("Number of orders processed: "+orderprocessedappcount);
			fw.write(System.getProperty( "line.separator" ));
			fw.write("Number of orders cancelled: "+ordercancelledappcount);
			/*fw.write(System.getProperty( "line.separator" ));
			fw.write("Number of orders rejected: "+orderrejectedappcount);*/
			fw.write(System.getProperty( "line.separator" ));
			fw.write("Number of orders pending: "+ordernewappcount);
			System.out.println("written to file");
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "generateLog";
	}
}
