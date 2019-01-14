<%@ page contentType="text/html; charset=UTF-8"%>
<template:basic htmlTitle="Create Ticket" bodyTitle="Create a Ticket">
	<form action="tickets" method="POST" enctype="multipart/form-data">
		<input type="hidden" name="action" value="create" /> Your Name<br />
		<input type="text" name="customerName"> <br> Subject<br>
		<input type="text" name="subject"><br> Body<br>
		<textarea rows="5" cols="30" name="body"></textarea>
		<br> <b>Attachment</b><br> <input type="file" name="file1"><br>
		<input type="submit" value="Create the Ticket">
	</form>
</template:basic>