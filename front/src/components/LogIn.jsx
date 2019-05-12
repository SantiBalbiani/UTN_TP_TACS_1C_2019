import React, { Component } from 'react';
import './LogIn.css';
import axios from 'axios';
import MuiThemeProvider from 'material-ui/styles/MuiThemeProvider';
import RaisedButton from 'material-ui/RaisedButton';
import TextField from 'material-ui/TextField';
import UploadScreen from './UploadScreen';
import AppBar from 'material-ui/AppBar';

const PRUEBALOCA = 'login'
const API_URL = 'http://localhost:8080'
const PLACES_API_URL = `${API_URL}/${PRUEBALOCA}`
class LogIn extends Component {
  constructor(props) {
    super(props)
    this.state = {
      username:'',
      password:''
      };
  //  this.handleChange = this.handleChange.bind(this)
  //  this.addContact = this.addContact.bind(this)
  }
  handleClick(event){
    
    var self = this;
    var payload = this.state.username + "&" + this.state.password
    axios.get(API_URL + "/login/" + payload)
    .then(function (response) {
    console.log(response);
    if(response.data.code == 200){
    alert("El usuario" + this.state.username + "se ha logueado con éxito")
    }
    
   // console.log("Login successfull");
   
   var uploadScreen=[];
   uploadScreen.push(<UploadScreen appContext={self.props.appContext}/>)
   self.props.appContext.setState({loginPage:[],uploadScreen:uploadScreen})
   }
    
    //else if(response.data.code == 204){
    //console.log("Username password do not match");
    )
    .catch(function (error) {
    console.log(error);
    });
    }

  render() {
    return (
      <div>
        <MuiThemeProvider>
          <div>
          <AppBar
             title="Login"
           />
           <TextField
             hintText="Enter your Username"
             floatingLabelText="Username"
             onChange = {(event,newValue) => this.setState({username:newValue})}
             />
           <br/>
             <TextField
               type="password"
               hintText="Enter your Password"
               floatingLabelText="Password"
               onChange = {(event,newValue) => this.setState({password:newValue})}
               />
             <br/>
             <RaisedButton label="LogIn" primary={true} style={style} onClick={(event) => this.handleClick(event)}/>
         </div>
         </MuiThemeProvider>
      </div>
      
    );
  }
}

const style = {
  margin: 1
};

export default LogIn;