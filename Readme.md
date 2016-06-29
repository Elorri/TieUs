# Tie Us

This repository contains the app "Tie Us", which is the last project of the Android Developer Nanodegree program (Capstone_Stage2)

## Description

The app change your “dead” contact list into an alive one by classifying your contacts into lists with priorities. It basically answer one question “Who should I contact today ?” and make sure all the contacts you decide to follow will see you or hear from you before they turn unsatisfied. 
To achieve this, the app encourages you to always think and plan the next appropriate action to take.

Aside from that, the app encourages you to evaluate each interaction you had and associate the contact with a satisfaction face. This is then used to evaluate all your relationships and resume them under one single general info : the forecast (sunny, slightly cloudy, cloudy, rainy).
So, it basically answer another simple question “How are my relationships going ?”

## Intended User

People who want to maintain existing relationships with friends, family, or business contacts.

People who want to increase their network and keep track of each action done.

## Features

Show list of contacts ordered with priorities : 
- People that should have already be contacted
- People to contact today
- People to contact in the future
Save the degree of satisfaction of each contact

Plan future actions

Allow choosing most appropriate social network

Display reminders messages

Display guidance messages
	

## Interfaces

### Main screen

![screen](../master/screenshot/Screenshot_contact_list.png "Watch contact list")

So whenever you feel like networking, open this app, and see what actions you have to perform today. 
For instance today, I have to offer a present to Françoise at a local event and I have to go on gmail to inform Mélissa of an event that might interest her.	


### Detail screen

![screen](../master/screenshot/Screenshot_contact_detail_screen.png "Watch contact details")

Detail screen is composed of 3 parts and a floating action button :

- The contact picture or default avatar picture (when no picture found) with communication with user default contact management app to allow editing.
- The contact satisfaction represented by a face and that the app encourage to update after each new interaction.
- The contacts next actions planned that gives us a quick overview of the places we have to go to interact with the contact.
- A floating action button that will open a serie of 3 screens and end up adding a new action on the contact profile.

### On tablet landscape

![screen](../master/screenshot/Screenshot_tablet_contact_detail_screen.png "Contact list and contact detail side by side")


### Select action screen

![screen](../master/screenshot/Screenshot_add_action_select_action.png "Select the action you plan to perform")

The list of actions is defined in the app and can’t be customised.
It is organise in 4 parts : 
- Introduce
- Inform
- Make an offer
- Give feedback

Each of these parts contains a list of relevant actions like : 
- Get to know what the contact is looking for (in Introduce)
- Give your expertise (in Inform)
- Make an offer of work (in Make an offer)
- Thanks (in Give feedback)

	
### Select vector of communication screen

![screen](../master/screenshot/Screenshot_add_action_select_vector.png "Select the social network you plan to use")

Vectors of communications are :
- Social networks installed on the user phone.
- Small text messages
- Phone
- Real life meetings
Once selected they appear both in Detail page and Main page, and gives the user a quick overview of “What are the people I need to contact on facebook today ?” for example.

Note : if you feel a vector is missing, let me know and I will add it.


### Select date screen

![screen](../master/screenshot/Screenshot_add_action_select_date.png "Select date when you plan to interact")

Last step that needs to be done to add action is to fill the “due date”.
This step achieved, the app will be able sort all contacts actions and display them in relevant order on the Main page.


### Guidance messages

![screen](../master/screenshot/Screenshot_contact_list_with_guidance_messages.png "Example of helpful message")

The app is also made up with a list of messages suggesting the user to take action. They shows up on Main screen when appropriate.
Here is a few examples : 
- Make sure all your contacts are scheduled:”You have 6 contacts with no actions registered. Add actions on their profile or mark them as unfollowed.”
- Reminder to update your contact satisfaction face : “Are those 3 contacts satisfied by your actions ?”
- Reminder to contact people before they turn unsatisfied : “You have 3 contacts that may turn unsatisfied if you don't interact with them. Take action, it's still time !”. Note : After a delay (you choose) satisfied people will become neutral or unsatisfied automatically if no action taken.


	
## Libraries used

* [Glide] (https://github.com/bumptech/glide) 
	Licensed mostly under the Apache License, Version 2.0 
* [MaterialDateTimePicker] (https://github.com/wdullaer/MaterialDateTimePicker) 
	Licensed under the Apache License, Version 2.0 
* [Udacity Sunshine Icons] (https://github.com/udacity/Advanced_Android_Development) 
	Licensed under the Apache License, Version 2.0 -  I used 4 of them in my app.
* [Stetho] (https://github.com/facebook/stetho) 
	Licensed under BSD License
* [Google Service AdMob] (https://www.google.com/admob/) 
* [Google Service Firebase] (https://firebase.google.com/) 
	used for getting Analytics
	

## Installation
An apk realease can be found under the app directory. Just download it on your phone.
App can also be downloaded from [google play] (https://play.google.com/store/apps/details?id=com.elorri.android.capstone.tieus.paid&hl=en) 
	


## License
	
		The MIT License (MIT)

	Copyright (c) 2016 ETCHEMENDY ELORRI

	Permission is hereby granted, free of charge, to any person obtaining a copy
	of this software and associated documentation files (the "Software"), to deal
	in the Software without restriction, including without limitation the rights
	to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
	copies of the Software, and to permit persons to whom the Software is
	furnished to do so, subject to the following conditions:

	The above copyright notice and this permission notice shall be included in all
	copies or substantial portions of the Software.

	THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
	IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
	FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
	AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
	LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
	OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
	SOFTWARE.
