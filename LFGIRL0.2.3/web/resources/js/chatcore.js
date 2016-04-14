        var wsocket;
        var prvsocket;
	var serviceLocation = "ws://" + document.location.host.toString() + "/LFGIRL0.3.1/chat/";
	var $nickName;
	var $message;
	var $chatWindow;
        var $privateMessage;
        var $targetUser;
	var room = '';
        var userName = '';
 
	function onMessageReceived(evt) {
		//var msg = eval('(' + evt.data + ')');
		var msg = JSON.parse(evt.data); // native API
		var $messageLine = $('<tr><td class="received">' + msg.received
				+ '</td><td class="user label label-info">' + msg.sender
				+ '</td><td class="message badge">' + msg.message
				+ '</td></tr>');
		$chatWindow.append($messageLine);
	}
        
        function onPrivateMessageReceived(evt) {
            var msg = JSON.parse(evt.data);
            alert(msg.sender + ' said to you: ' + msg.message);
        }
        
	function sendMessage() {
		var msg = '{"message":"' + $message.val() + '", "sender":"'
				+ $nickName.val() + '", "receiver":"", "received":""}';
		wsocket.send(msg);
		$message.val('').focus();
	}
        
        function sendPrivateMessage() {
                var msg = '{"message":"' + $privateMessage.val() + '", "sender":"'
				+ userName + '", "receiver":"' + $targetUser.val() + '", "received":""}';
		prvsocket.send(msg);
		$privateMessage.val('').focus();
        }
 
	function connectToChatserver() {
		room = $('#chatroom option:selected').val();
		wsocket = new WebSocket(serviceLocation + room);
		wsocket.onmessage = onMessageReceived;
	}
        
        function openAndConfig(value) {
		room = value;
                userName = value;
		//wsocket = new WebSocket(serviceLocation + room);
		//wsocket.onmessage = onPrivateMessageReceived;
                prvsocket = new WebSocket(serviceLocation + room);
                prvsocket.onmessage = onPrivateMessageReceived;
	}
 
	function leaveRoom() {
		wsocket.close();
		$chatWindow.empty();
		$('.chat-wrapper').hide();
		$('.chat-signin').show();
		$nickName.focus();
	}
        
        function getUsername(value) {
            var msg = JSON.stringify(value);
            var values = JSON.parse(msg);
            if (values.value) {
                userName = values.value;
                openAndConfig(values.value);
            }           
        }
 
	$(document).ready(function() {
		$nickName = $('#nickname');
		$message = $('#message');
		$chatWindow = $('#response');
                $privateMessage = $('#privateMessage');
                $targetUser = $('#targetUser');
		$('.chat-wrapper').hide();
		$nickName.focus();
 
		$('#enterRoom').click(function(evt) {
			evt.preventDefault();
			connectToChatserver();
			$('.chat-wrapper h2').text('Chat # '+$nickName.val() + "@" + room);
			$('.chat-signin').hide();
			$('.chat-wrapper').show();
			$message.focus();
		});
		$('#do-chat').submit(function(evt) {
			evt.preventDefault();
			sendMessage()
		});
                
                $('#private-message').submit(function(evt) {
                        evt.preventDefault();
                        sendPrivateMessage()
                });
 
		$('#leave-room').click(function(){
			leaveRoom();
		});
	});