function loadAudio(audioTagName, voiceURL){
	var audio = document.getElementById(audioTagName);
    audio.src = voiceURL;
    audio.load();
}