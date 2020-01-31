// vim: ts=4:sw=6
/**
 *	Set Input Source
 *
 *	Copyright 2019 Ross Tyler
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
	description		: 'Set the input source of media players when a switch or sensor closes',
	category		: 'Convenience',
	singleInstance	: false,
	iconUrl			: 'https://raw.githubusercontent.com/rtyle/set-input-source/master/smartapps/rtyle/set-input-source.src/app.png',
	iconX2Url		: 'https://raw.githubusercontent.com/rtyle/set-input-source/master/smartapps/rtyle/set-input-source.src/app@2x.png',
	iconX3Url		: 'https://raw.githubusercontent.com/rtyle/set-input-source/master/smartapps/rtyle/set-input-source.src/app@3x.png',
)

preferences {
	section() {
		input 'players'		, 'capability.mediaInputSource'	, title: 'Players'		, multiple: true
		input 'inputSource'	, 'text'						, title: 'Input Source'
		input 'switches'	, 'capability.switch'			, title: 'Switches'		, multiple: true,	required: false
		input 'sensors'		, 'capability.contactSensor'	, title: 'Sensors'		, multiple: true,	required: false
	}
}

private void respond(String message) {
	log.info message
	if (false
		|| switches	.find {'on'		== it.currentSwitch}
		|| sensors	.find {'closed'	== it.currentContact}
	) {
		log.info "setInputSource $inputSource"
		players.on()
		players.setInputSource inputSource
	}
}

def getIndent() {/* non-breaking space */ '\u00a0' * 8}

void respondToSwitch(physicalgraph.app.EventWrapper e) {
	respond(indent + "⚡ $e.value $e.name $e.device")
}

void respondToContact(physicalgraph.app.EventWrapper e) {
	respond(indent + "⚡ $e.value $e.name $e.device")
}

private void initialize() {
	subscribe switches	, 'switch'	, respondToSwitch
	subscribe sensors	, 'contact'	, respondToContact
}

def installed() {
	initialize()
}

def updated() {
	unsubscribe()
	initialize()
}
