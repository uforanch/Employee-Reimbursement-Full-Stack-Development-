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

    const LogOutFunc = ()=>{
        
            console.log("Logout GO")
            const authProp:AuthorizationProps = DefaultAuth
            authSetter(authProp)
            flagSetter(Date.now())
            localStorage.removeItem("loggedInUser")
        
        }
        
    
    
    const nav = useNavigate();
    useEffect(()=>{
        LogOutFunc();
        nav("/")

    },[])
    return <><h1>Goodbye</h1><h2>You are now logged out</h2></>
}

export default LogOut