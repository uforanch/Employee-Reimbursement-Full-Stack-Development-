import { useEffect } from "react";
import { useNavigate } from "react-router-dom";
import AuthSetterType from "./Common/AuthSetterType";
import AuthorizationProps, { DefaultAuth } from "./Common/Authorization";

import configData from "../config/config.json"
import axios, { AxiosError } from "axios";

/*
effect log out

*/

function LogOut({authSetter, flagSetter}:AuthSetterType){

    const LogOutFunc = ()=>{const axiossubmit00= async () =>{
        
            console.log("Logout GO")
            const config = { withCredentials:true}
            await axios.post(configData.SERVER_URL+"/auth/logout", config)
            .then(
                response=>{
                    console.log(response)
                    const authProp:AuthorizationProps = {username:"",
                        userId:0,
                        role:""
                    }
                    authSetter(authProp)
                    flagSetter(Date.now())
                    
                }
                )
            .catch((error:AxiosError)=>{
                console.log("error"); 
                console.log(error); 
        })}
        axiossubmit00();
        
    }
    
    const nav = useNavigate();
    useEffect(()=>{
        LogOutFunc();
        nav("/")

    },[])
    return <><h1>Goodbye</h1><h2>You are now logged out</h2></>
}

export default LogOut