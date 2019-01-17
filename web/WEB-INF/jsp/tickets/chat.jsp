<%--elvariable id="roomId" type="long"--%>
<template:basic htmlTitle="Support Chat" bodyTitle="Support Chat">
	<jsp:attribute name="extraHeadContent">
        <link rel="stylesheet"
              href="<c:url value="/resource/stylesheet/chat.css" />" />
        <script src="http://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/2.3.1/js/bootstrap.min.js"></script>
    </jsp:attribute>
    <jsp:body>
        <div id="chatContainer">
            <div id="chatLog">

            </div>
            <div id="messageContainer">
                <textarea id="messageArea"></textarea>
            </div>
            <div id="buttonContainer">
                <button class="btn btn-primary" onclick="send();">Send</button>
                <button class="btn" onclick="disconnect();">Disconnect</button>
            </div>
        </div>
        <div id="modalError" class="modal hide fade">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h3>Error</h3>
            </div>
            <div class="modal-body" id="modalErrorBody">A blah error occurred.</div>
            <div class="modal-footer">
                <button class="btn btn-primary" data-dismiss="modal">OK</button>
            </div>
        </div>
        
        <script type="text/javascript" language="javascript">
        	var send, disconnect;
        	$(document).ready(function(){
        		var modalError = $("#modalError");
        		var modalErrorBody = $("#modalErrorBody");
        		var chatLog = $("#chatLog");
        		var messageArea = $('#messageArea');
        		var username = '${sessionScope.username}';
        		
        		if(!("WebSocket" in window)){
        			modalErrorBody.text('WebSockets are not support in this browser. Try IE10 or latest versions of Moailla or GoogleChrome');
        			modalError.modal('show');
        			return;
        		}
        		
        		var infoMessage = function(m){
        			chatLog.append(
        							$('<div>').addClass('informational').text(moment().format('h:mm:ss a')+': '+m)		
        			);
        		}
        		infoMessage('Connecting to the char server...');
        		
        		
        	});
        </script>
        
        </jsp:body>
</template:basic>