import axios, { AxiosError } from "axios";
import { ReactNode, useState } from "react";
import { useNavigate } from "react-router-dom";
import configData from "../config/config.json"
import AuthorizationProps from "./Common/Authorization";
import AuthSetterType from "./Common/AuthSetterType";
function Login({authSetter}:AuthSetterType):ReactNode{


    const nav=useNavigate()
    const [getUsername, setUsername] = useState<string>("")
    const [getPassword, setPassword] = useState<string>("")
    const [getErrorText, setErrorText] = useState<string>("")
    const btnDisable = ():boolean =>{
        return !(getUsername.length>=8 && getPassword.length>=8)
        }
    
    

    const LoginFunc = () =>{
        const axiosSubmit00 =async () => {
            console.log("GO")
            const userLoginCredentials = {username:getUsername, password:getPassword};
            const config = {headers:{"Content-Type":"application/json", "Authorization":"Bearer yourAccessToken"}, timeout:5000, withCredentials:false}
            await axios.post(configData.SERVER_URL+"/users/login", userLoginCredentials, config)
            .then(
                response=>{
                    setErrorText("");
                    console.log(response)
                    const authProp:AuthorizationProps = {username:response.data.username,
                        userId:response.data.userId,
                        role:response.data.role
                    }
                    authSetter(authProp)
                    nav("/user")
                }
                )
            .catch((error:AxiosError)=>{
                console.log("error"); 
                console.log(error); 
                console.log(error.response);
                let errorText = error.message;
                if (error.response != undefined){
                    if (typeof error.response.data =='string'){
                        setErrorText(error.response.data)
                    } else {
                        setErrorText(errorText)
                    }
                } else {
                    setErrorText(errorText)
                }
        })}
        axiosSubmit00()
    }
    return <>
    <div>
    <button onClick={()=>{nav("/")}}>Welcome</button> 
    <button onClick={()=>{nav("/register")}}>Register</button>
    </div>
    <div className="Edit">
        <input type="text" onChange={(event)=>{setUsername(event.target.value)}}/>
        <input type="password" onChange={(event)=>{setPassword(event.target.value)}}/>
        <button disabled={btnDisable()} onClick={LoginFunc}>Submit</button><span className="Error">{getErrorText}</span>
    </div>
    </>
    
    }

export default Login
