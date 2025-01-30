import { ReactNode, useContext, useEffect, useState } from "react"
import { useNavigate, useParams } from "react-router-dom"
import AuthorizationProps, { AuthContext } from "./Common/Authorization";
import { UserReturn } from "./Common/Utils";

import configData from "../config/config.json"
import axios from "axios";
interface DisabledAdvice{
    disabled: boolean;
    advice:string;
}

/*
use effect populate from userid

*/

interface fullUserSend{userId:string, username:string, password:string, firstName:string, lastName:string, role:string}

function UserManagementPage():ReactNode{
    const [getUsername, setUsername] = useState<string>("")
    const [getPassword1, setPassword1] = useState<string>("")
    const [getPassword2, setPassword2] = useState<string>("")
    const [getFirstName, setFirstName] = useState<string>("")
    const [getLastName, setLasttName] = useState<string>("")
    const [getRole, setRole] = useState<string>("")
    const [getTrueUserId, setTrueUserId]=useState<string>("")
    const id = useParams().id
    const userid = useContext(AuthContext).userId
    
    const role = useContext(AuthContext).role;
    const token = useContext(AuthContext).token;
    const nav = useNavigate()
    console.log(id, userid)


      
    const axiosSubmit00=async ()=>{
        const urlString = configData.SERVER_URL ;


        console.log(urlString)
        const config = {headers: {
                'Authorization':`Bearer ${token}`
            }}
        await axios.get(urlString +"/users/"+id, config).then(
            (response)=>{console.log(response);
                setUsername(response.data.username);
                setFirstName(response.data.firstName);
                setLasttName(response.data.lastName);
                setPassword1(response.data.password);
                setPassword2("");
                setRole(response.data.role);
                setTrueUserId(response.data.userId)
            }

            

        ).catch(
            (error)=>{console.log(error)}

        )

    }

    const axiosSubmit01=async ()=>{
        const urlString = configData.SERVER_URL ;


        
        const config = {headers: {
                'Authorization':`Bearer ${token}`
            }}
        const userSend:fullUserSend = {userId:getTrueUserId, username:getUsername, password:getPassword1, firstName:getFirstName, lastName:getLastName, role:role}
        
        console.log(userSend)
        await axios.patch(urlString + "/users" , userSend, config).then(
            (response)=>{console.log(response);
            nav("/user");

            }

            

        ).catch(
            (error)=>{console.log(error)}

        )

    }

    useEffect(()=>{    
        console.log("UserManagementPage GO")
        axiosSubmit00()
    if (id!=userid){
        
        nav("/")
    }

    },[])

    
    
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
    <div className="Edit">
        <div className="Form">
        <input type="text"  value={getUsername} onChange={(event)=>{setUsername(event.target.value)}}/> <label> Username</label>
        </div>
        <div className="Form">
        <input type="text"  value={getFirstName} onChange={(event)=>{setFirstName(event.target.value)}}/> <label> First Name</label>
        </div>
        <div className="Form">
        <input type="text" value={getLastName} onChange={(event)=>{setLasttName(event.target.value)}}/> <label> Last Name</label> 
        </div>
        <div>
            <div className="Form">
                <span className="Disp">{getRole}</span> <label> Role</label>
            </div>
        </div>
        <div className="Form">
        <input type="password" placeholder={getPassword1} onChange={(event)=>{setPassword1(event.target.value)}}/> <label> Password</label>
        </div>
        <div className="Form">
        <input type="password" onChange={(event)=>{setPassword2(event.target.value)}}/> <label> Same Password</label>
        </div>
        <button disabled={btnDisable().disabled} onClick={axiosSubmit01}>Submit</button><button className=".Cancel" onClick={()=>{nav("/user")}}>Cancel</button>
        <p><span className="Error">{btnDisable().advice}</span></p>
    </div>
    </>
}

export default UserManagementPage;