/* Display log messages on console */
function __log(e, data) {
	console.log(e + " " + (data || ''));
}

var audio_context;
var recorder;

function RecordFile(recordButton, stopButton, audio, url) {
	this.recordButtonTagName = recordButton;
	this.stopButtonTagName = stopButton;
	this.audioTagName = audio;
	this.urlTagName = url;
}

RecordFile.prototype.startUserMedia = function(stream){
	__log(this.audio_context);
	var input = audio_context.createMediaStreamSource(stream);
	__log('Media stream created.');
	recorder = new Recorder(input);
	__log('Recorder initialised.');
};

RecordFile.prototype.startRecording = function() {
	recorder && recorder.record();
	document.getElementById(this.recordButtonTagName).disabled = true;
	document.getElementById(this.stopButtonTagName).disabled = false;
	__log('Recording...');
};

RecordFile.prototype.createDownloadLink = function() {
	var urlTagName = this.urlTagName;
	var audioTagName = this.audioTagName;
	console.log("createDownloadLink executed");
	recorder && recorder.exportWAV(function(blob) {
		var url = URL.createObjectURL(blob);
		var hf = document.getElementById(urlTagName);
		console.log(this.urlTagName);
		console.log(hf);
		hf.href = url;
		hf.download = new Date().toISOString() + '.wav';
		hf.innerHTML = hf.download;
		loadAudio(audioTagName, url)
	});
};

RecordFile.prototype.stopRecording = function() {
	recorder && recorder.stop();
	document.getElementById(this.stopButtonTagName).disabled = true;
	document.getElementById(this.recordButtonTagName).disabled = false;
	__log('Stopped recording.');
    
	// create WAV download link using audio data blob
	this.createDownloadLink();
    
	recorder.clear();
};

RecordFile.prototype.init = function(){
	
	try {
	      window.AudioContext = window.AudioContext || window.webkitAudioContext;
	      navigator.getUserMedia = navigator.getUserMedia || navigator.webkitGetUserMedia;
	      window.URL = window.URL || window.webkitURL;
	      
	      audio_context = new AudioContext();
	      __log('Audio context set up.');
	      __log('navigator.getUserMedia ' + (navigator.getUserMedia ? 'available.' : 'not present!'));
	    } catch (e) {
	      alert('No web audio support in this browser!');
	    }
	    
	    navigator.getUserMedia({audio: true}, this.startUserMedia, function(e) {
	      __log('No live audio input: ' + e);
	    });
};

var recordFile = new RecordFile("recordButton", "stopButton", "audio", "audioDownload");
recordFile.init();