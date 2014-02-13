/* Gets a list of images from the server and adds them to the imageList
 * Also adds a unique id to each part, viewable when clicked
 */


$(document).ready(function() {

	/*
	 * RUN Button Listener
	 */
    $('#runButton').click(function() {
        
    	//$('#outputArea').collapse('show');
        
        var text = false; //is the text editor active?

        if ($('div#textEditorTab').hasClass("active")) {
            text = true;
        }
        
        if (text) {

        	// we disable the Run button
        	$('#runButton').attr("disabled", "disabled");

        	// and clear the console
        	$('#consoleArea').html('');
                    	
            var command = {
            	"command":"run", 
            	"script":editor.getValue(),
            	"n":$("input").val()
            	};
            
            /*
             * send the request to the servlet
             * (asynchronous call)
             */
            $.post("servlet", command, function(response) {

            	// when we receive the response, 
            	// we enable the Run button again
                $('#runButton').removeAttr("disabled");
                
                // whatever we receive from the servlet
                // we put it into the console
                if (response["response"] !== undefined) {                    	
                    $('#consoleArea').html(response["response"]);
                }
            });
        }
    });


    //functions to run on page load
    var editor = CodeMirror.fromTextArea(document.getElementById("textEditor"), {
        styleActiveLine: true,
        lineNumbers: true,
        lineWrapping: true,
        theme: "neat",
        mode: "eugene"
    });

});

