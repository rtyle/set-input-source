// vim: ts=4:sw=6
/**
 *	Set Input Source
 *
 *	Copyright 2020 Ross Tyler
 *
 *	Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 *	in compliance with the License. You may obtain a copy of the License at:
 *
 *		http://www.apache.org/licenses/LICENSE-2.0
 *
 *	Unless required by applicable law or agreed to in writing, software distributed under the License is distributed
 *	on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License
 *	for the specific language governing permissions and limitations under the License.
 */
definition(
	name			: 'Set Input Source',
	namespace		: 'rtyle',
	author			: 'Ross Tyler',
	description		: 'Set the input source of targets when triggered',
	category		: 'Convenience',
	singleInstance	: false,
	iconUrl			: 'https://raw.githubusercontent.com/rtyle/set-input-source/master/smartapps/rtyle/set-input-source.src/app.png',
	iconX2Url		: 'https://raw.githubusercontent.com/rtyle/set-input-source/master/smartapps/rtyle/set-input-source.src/app@2x.png',
	iconX3Url		: 'https://raw.githubusercontent.com/rtyle/set-input-source/master/smartapps/rtyle/set-input-source.src/app@3x.png',
)

preferences {
	section('Targets') {
		input 'switchTargets'			, 'capability.switch'			, title: 'Switch targets (turned on when triggered)'		, multiple: true, required: false
		input 'mediaInputSourceTargets'	, 'capability.mediaInputSource'	, title: 'Media input source targets (set when triggered)'	, multiple: true
		input 'inputSource'				, 'text'						, title: 'Input source'
	}
	section('Triggers') {
		input 'switchTriggers'			, 'capability.switch'			, title: 'Switch triggers (when on)'				, multiple: true,	required: false
		input 'contactSensorTriggers'	, 'capability.contactSensor'	, title: 'Contact Sensor triggers (when closed)'	, multiple: true,	required: false
		input 'mediaPlaybackTriggers'	, 'capability.mediaPlayback'	, title: 'Media Playback triggers (when playing)'	, multiple: true,	required: false
	}
}

private void respond(String message) {
	log.info message
	if (false
		|| switchTriggers		.find {'on'			== it.currentSwitch}
		|| contactSensorTriggers.find {'closed'		== it.currentContact}
		|| mediaPlaybackTriggers.find {'playing'	== it.currentPlaybackStatus}
	) {
		log.info "setInputSource $inputSource"
		switchTargets.on()
		mediaInputSourceTargets.setInputSource inputSource
	}
}

def getIndent() {/* non-breaking space */ '\u00a0' * 8}

void respondToSwitch(physicalgraph.app.EventWrapper e) {
	respond(indent + "⚡ $e.value $e.name $e.device")
}

void respondToContact(physicalgraph.app.EventWrapper e) {
	respond(indent + "⚡ $e.value $e.name $e.device")
}

void respondToPlaybackStatus(physicalgraph.app.EventWrapper e) {
	respond(indent + "⚡ $e.value $e.name $e.device")
}

private void initialize() {
	subscribe switchTriggers		, 'switch'			, respondToSwitch
	subscribe contactSensorTriggers	, 'contact'			, respondToContact
	subscribe mediaPlaybackTriggers	, 'playbackStatus'	, respondToPlaybackStatus
}

def installed() {
	initialize()
}

def updated() {
	unsubscribe()
	initialize()
}
