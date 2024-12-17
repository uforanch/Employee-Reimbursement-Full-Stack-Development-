import { ReactNode, useContext, useState } from "react"
import { useNavigate } from "react-router-dom"
import AuthorizationProps, { AuthContext } from "./Common/Authorization";

interface DisabledAdvice{
    disabled: boolean;
    advice:string;
}

function Register():ReactNode{
    const [getUsername, setUsername] = useState<string>("")
    const [getPassword1, setPassword1] = useState<string>("")
    const [getPassword2, setPassword2] = useState<string>("")
    const nav = useNavigate()
    
    
    
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
        <input type="text" onChange={(event)=>{setUsername(event.target.value)}}/>
        <input type="password" onChange={(event)=>{setPassword1(event.target.value)}}/>
        <input type="password" onChange={(event)=>{setPassword2(event.target.value)}}/>
        <button disabled={btnDisable().disabled}>Submit</button>
        <p><span className="Error">{btnDisable().advice}</span></p>
    </div>
    </>
}

export default Register;