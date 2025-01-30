import { useEffect } from "react";
import { useNavigate } from "react-router-dom"

function ErrorScreen({text, navTo}:{text:string,navTo:string}){
    const nav = useNavigate();
    useEffect(()=>{nav(navTo)},[])
    return <>{text}</>
}

export default ErrorScreen