import { useEffect } from "react";
import { useNavigate } from "react-router-dom";
import AuthSetterType from "./Common/AuthSetterType";
import { DefaultAuth } from "./Common/Authorization";

function LogOut({authSetter}:AuthSetterType){
    const nav = useNavigate();
    useEffect(()=>{
        authSetter(DefaultAuth)
        nav("/")

    },[])
    return <><h1>Goodbye</h1><h2>You are now logged out</h2></>
}

export default LogOut