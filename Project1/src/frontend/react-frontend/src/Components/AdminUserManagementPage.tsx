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
use effect populate from  user id

*/
interface fullUserSend{userId:string, shortId:string, username:string, password:string, firstName:string, lastName:string, role:string}


function AdminUserManagementPage():ReactNode{
    const token = useContext(AuthContext).token;
    const [getUserId, setUserId] = useState<string>("")
    const [getShortId, setShortId] = useState<string>("")
    const [getUsername, setUsername] = useState<string>("")
    const [getPassword1, setPassword1] = useState<string>("")
    const [getPassword2, setPassword2] = useState<string>("")
    const [getFirstName, setFirstName] = useState<string>("")
    const [geLasttName, setLasttName] = useState<string>("")
    const [getRole, setRole] = useState<string>("Employee")
    const nav = useNavigate()
     
    const config = {headers: {
            'Authorization':`Bearer ${token}`
        }}

    const id = Number(useParams().id)
    
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

    
    const axiosSubmit00=async ()=>{
        const urlString = configData.SERVER_URL ;


        console.log(urlString)

        await axios.get(urlString +"/users/"+id, config).then(
            (response)=>{console.log(response);
                setUserId(response.data.userId);
                setShortId(response.data.shortId);
                setUsername(response.data.username);
                setFirstName(response.data.firstName === null? "" : response.data.firstName );
                setLasttName(response.data.lastName=== null? "" : response.data.lastName );
                setPassword1(response.data.password);
                setRole(response.data.role);
                setPassword2("");
            }

            

        ).catch(
            (error)=>{console.log(error)}

        )

    }

    const axiosSubmit01=async ()=>{
        const urlString = configData.SERVER_URL ;


       
        const userSend:fullUserSend = {userId:getUserId, shortId:getShortId, username:getUsername, password:getPassword1, firstName:getFirstName, lastName:geLasttName, role:getRole}
        
        console.log(userSend)
        await axios.patch(urlString + "/users" , userSend, config).then(
            (response)=>{console.log(response);
            nav("/user");

            }

            

        ).catch(
            (error)=>{console.log(error)}

        )

    }

    
    const axiosSubmit02=async ()=>{
        const urlString = configData.SERVER_URL ;
        
        await axios.patch(urlString + "/users/" + id+"/delete" , null, config).then(
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


    },[])
    
    return <>
    <div className="Edit">
        <div className="Form">
        <input type="text" value={getUsername} onChange={(event)=>{setUsername(event.target.value)}}/> <label> Username</label>
        </div>
        <div className="Form">
        <input type="text" value={getFirstName} onChange={(event)=>{setFirstName(event.target.value)}}/> <label> First Name</label>
        </div>
        <div className="Form">
        <input type="text" value={geLasttName} onChange={(event)=>{setLasttName(event.target.value)}}/> <label> Last Name</label> 
        </div>
        <div>
            <div className="Form">
                <select value={getRole} onChange={(event)=>{setRole(event.target.value)}}><option value="Employee">Employee</option><option value="Manager">Manager</option></select> <label> Role</label>
            </div>
        </div>

        <div className="Form">
        <input type="password" value={getPassword1} onChange={(event)=>{setPassword1(event.target.value)}}/> <label> Password</label>
        </div>
        <div className="Form">
        <input type="password" onChange={(event)=>{setPassword2(event.target.value)}}/> <label> Same Password</label>
        </div>
        <button disabled={btnDisable().disabled} onClick={()=>{axiosSubmit01()}}>Submit</button><button className=".Cancel" onClick={()=>{nav("/user")}}>Cancel</button><button className=".Cancel" onClick={()=>{axiosSubmit02()}}>Delete</button>
        <p><span className="Error">{btnDisable().advice}</span></p>
    </div>
    </>
}

export default AdminUserManagementPage;