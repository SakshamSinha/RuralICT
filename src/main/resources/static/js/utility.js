var API_ADDR = $("#contextRoot").val();
var ORG_ABBR = $("#organizationAbbr").val();
/* Function to dynamically load audio */
function loadAudio(audioTagName, voiceURL){
	var audio = document.getElementById(audioTagName);
	audio.src = voiceURL;
	audio.load();
}

function getAudioPlayer(linkObject){
	
	var parent = linkObject.parentNode;
	var url = linkObject.href;
	console.log(url);
	var audioNode = document.createElement("audio");
	audioNode.id = "newAudio" 
	parent.removeChild(linkObject);
	parent.innerHTML = '<audio controls id="newAudio"></audio>'
	loadAudio("newAudio",url);
	audioNode.removeAttribute("id"); 
	console.log(audioNode.id);
}


function getId(object){
	var urlChunks = object["_links"]["self"]["href"].split("/");
	return urlChunks[urlChunks.length-1];
}

function createAlert(heading, message){
	$("#messageHeading").text(heading);
	$("#messageContent").text(message);
	$('#message-modal').modal("toggle");
}

function normalizePhoneNumber(phoneNumber){
	
	// strip all non numeric data
	phoneNumber = phoneNumber.replace(/[^0-9]/g, '');
	
	// check if there are atleast 10 digits
	if(phoneNumber.length < 10)
		return false;
	else
	{
		// get the last 10 digits of the phone number
		phoneNumber = phoneNumber.substr(phoneNumber.length - 10);
		phoneNumber = "91" + phoneNumber;
		return phoneNumber;
	}
}

function validatedate(inputText){
	var dateformat = /^(19|20)\d\d([- /.])(0[1-9]|1[012])\2(0[1-9]|[12][0-9]|3[01])$/;
	
	// Match the date format through regular expression
	if(inputText.match(dateformat)){
		
		//Test which seperator is used '/' or '-'
		var opera1 = inputText.split('/');
		var opera2 = inputText.split('-');

		lopera1 = opera1.length;
		lopera2 = opera2.length;

		// Extract the string into month, date and year
		if (lopera1>1){
			var pdate = inputText.split('/');
		}
		else if (lopera2>1) {
			var pdate = inputText.split('-');
		}
		
		var mm  = parseInt(pdate[0]);
		var dd = parseInt(pdate[1]);
		var yy = parseInt(pdate[2]);

		// Create list of days of a month [assume there is no leap year by default]
		var ListofDays = [31,28,31,30,31,30,31,31,30,31,30,31];
		
		if (mm==1 || mm>2){
			if (dd>ListofDays[mm-1]){
				return false;

			}
		}
		if (mm==2){
			var lyear = false;
			if ( (!(yy % 4) && yy % 100) || !(yy % 400)){
				lyear = true;
			}

			if ((lyear==false) && (dd>=29)){
				return false;

			}

			if ((lyear==true) && (dd>29)){
				return false;

			}
		}
	}
	else{
		return false;
	}

	return true;
}