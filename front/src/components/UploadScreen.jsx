import React, { Component } from 'react';

/*
Screen:LoginScreen
Loginscreen is the main screen which the user is shown on first visit to page and after
hitting logout
*/
import Login from './LogIn';
/*
Module:Material-UI
Material-UI is used for designing ui of the app
*/
import MuiThemeProvider from 'material-ui/styles/MuiThemeProvider';
import AppBar from 'material-ui/AppBar';
import RaisedButton from 'material-ui/RaisedButton';
import Drawer from 'material-ui/Drawer';
import MenuItem from 'material-ui/MenuItem';
import FontIcon from 'material-ui/FontIcon';
import {blue500, red500, greenA200} from 'material-ui/styles/colors';

var apiBaseUrl = "http://localhost:8080/";

/*
Module:superagent
superagent is used to handle post/get requests to server
*/
//var request = require('superagent');

class UploadScreen extends Component {
  constructor(props){
    super(props);
    this.state={
      place: '',
      name: ''
    }
  }
}
export default UploadScreen;