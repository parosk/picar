/*-----------------------------------------------------------------------------
    Project name:  firmata w/arduino  + node(server) + servo + keyboard(client)
    
    Description:   The program aims at creating sever in pi as host
                   for client to control servo
		   First, using keyboard
		   Second, using gamepad
		   Not using pi-gpio
		   Use Arduino for actuators

    Hardware:      Raspberry Pi
		   Arduino
                   Servo
		
    Software:      Node.JS firmata html

    Created Date:  1-8-2015

    Modified Date: 7-8-2015

    Author:        Jacky Leung

-----------------------------------------------------------------------------*/
"use strict";

//Loading modules
var http = require('http');
var fs = require('fs'); //file system
var path = require('path');

// Connect to the Arduino
var serialPort = '/dev/ttyAMA0';
var board = require('./firmataConnector.js').start(serialPort);


// Initialize the server on port 8000
var server = http.createServer(function (req, res) {
    // requesting files
    var file = '.'+((req.url=='/')?'/servo_control.html':req.url);
    var fileExtension = path.extname(file);
    var contentType = 'text/html';
    if(fileExtension == '.css'){
        contentType = 'text/css';
    }
    fs.exists(file, function(exists){
        if(exists){
            fs.readFile(file, function(error, content){
                if(!error){
                    // Page found, write content
                    res.writeHead(200,{'content-type':contentType});
                    res.end(content);
                }
            })
        }
        else{
            // Page not found
            res.writeHead(404);
            res.end('Page not found');
        }
    })
}).listen(8000);

// Loading socket io module
var io = require('socket.io').listen(server);

board.on('connection', function () {      //function()??
    
    // WebSocket connection
    io.on('connection', function (socket) {
        socket.on('servo_control', handleServo);
    });
    
    function handleServo(data) {
	var newData = JSON.parse(data);
	//var positionValue = newData.state;
	var degrees = newData.state;
        //Set the pin 11 to Pulse Width Modulation
        //board.pinMode(11, board.PWM);
        //board.analogWrite(11, positionValue);
	
	//Set the pin 9 to Servo
        board.pinMode(9, board.MODES.SERVO);
        board.servoWrite(9, degrees);
        
        
	console.log('Degree: ', degrees);
    };
        
});

// Displaying a console message for user feedback
server.listen(console.log("Server Running ..."));
