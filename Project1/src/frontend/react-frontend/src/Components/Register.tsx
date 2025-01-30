import { ReactNode, useContext, useState } from "react"
import { useNavigate } from "react-router-dom"
import AuthorizationProps, { AuthContext } from "./Common/Authorization";
import AuthSetterType from "./Common/AuthSetterType";
import configData from "../config/config.json"
import axios, { AxiosError } from "axios";

/*
effect insert new user



*/

interface DisabledAdvice{
    disabled: boolean;
    advice:string;
}

function Register({authSetter, flagSetter}:AuthSetterType):ReactNode{
    const [getUsername, setUsername] = useState<string>("")
    const [getPassword1, setPassword1] = useState<string>("")
    const [getPassword2, setPassword2] = useState<string>("")
    const [getFirstName, setFirstName] = useState<String>("")
    const [getLasttName, setLasttName] = useState<String>("")
    const nav = useNavigate()
    const axiosSubmit00 =async () => {
            console.log("Login GO")
            const userLoginCredentials = {username:getUsername, password:getPassword1, firstName:getFirstName, lastName:getLasttName};
            const config = { timeout:5000, withCredentials:true}
            await axios.post(configData.SERVER_URL+"/auth/register", userLoginCredentials, config)
            .then(
                response=>{
                    console.log(response)

                    const authProp:AuthorizationProps = {username:response.data.user.username,
                        userId:response.data.user.shortId,
                        role:response.data.user.role,
                        token: response.data.token

                    }
                    localStorage.setItem("loggedInUser", JSON.stringify(authProp))
                    authSetter(authProp)
                    flagSetter(Date.now())
                    nav("/user")
                }
                )
            .catch((error:AxiosError)=>{
                console.log("error"); 
                console.log(error); 
                console.log(error.response);

        })}
    
    
    
    const btnDisable = ():DisabledAdvice =>{
    
        let etext:string = "";
        if(getUsername.length == 0){
            etext="Cannot have blank username"
            
        } else if(getUsername.length < 8){
            etext="Username must have at least 8 characters"
            
        } else if(getPassword1.length == 0){
            etext="Cannot have blank password"
            
        } else if(getPassword1.length < 8){
            etext="Password must be at least 8 characters"
            
        } else if (getPassword1 != getPassword2){
            etext="Passwords do not match"
            
        } else{
        return  {disabled:false,advice:""}}
        return {disabled:true, advice:etext}
        
    }
    
    return <>
    <div>
    <button onClick={()=>{nav("/")}}>Welcome</button> 
    <button onClick={()=>{nav("/Login")}}>Login</button>
    </div>
    <div className="Edit">
        <div className="Form">
        <input type="text" onChange={(event)=>{setUsername(event.target.value)}}/> <label> Username</label>
        </div>
        <div className="Form">
        <input type="text" onChange={(event)=>{setFirstName(event.target.value)}}/> <label> First Name</label>
        </div>
        <div className="Form">
        <input type="text" onChange={(event)=>{setLasttName(event.target.value)}}/> <label> Last Name</label> 
        </div>
        <div className="Form">
        <input type="password" onChange={(event)=>{setPassword1(event.target.value)}}/> <label> Password</label>
        </div>
        <div className="Form">
        <input type="password" onChange={(event)=>{setPassword2(event.target.value)}}/> <label> Same Password</label>
        </div>
        <button disabled={btnDisable().disabled} onClick={axiosSubmit00}>Submit</button>
        <p><span className="Error">{btnDisable().advice}</span></p>
    </div>
    </>
}

export default Register;