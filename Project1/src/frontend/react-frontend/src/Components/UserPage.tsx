import { useContext, useEffect } from "react"
import AuthorizationProps, { AuthContext } from "./Common/Authorization"
import { BrowserRouter, Route, Routes, useNavigate } from "react-router-dom"

import configData from "../config/config.json"
import axios from "axios";
function UserPage() {
    const user = useContext(AuthContext)
    const nav = useNavigate()
    const logOut = ()=>{nav("/logout")}

 
    const axiosSubmit00=async ()=>{
        const urlString = configData.SERVER_URL ;


        console.log(urlString)
        const config = {headers: {
                'Authorization':`Bearer ${user.token}`
            }}
        await axios.get(urlString +"/users/"+user.userId, config).then(
            (response)=>{console.log(response);
           
            }

            

        ).catch(
            (error)=>{console.log(error); logOut();}
            

        )

    }

    useEffect(()=>{axiosSubmit00()}
        , [])

    if (user.token===""){
        return <>No User</>
    } else if (user.role==="Manager"){

    return <>

        <div className="NavPanel" >
    <button className="UserButton" onClick={logOut}>LogOut</button>
    
    <button className="UserButton"  onClick={()=>{nav("/user/adminpage")}}>Admin</button>
    </div>
    <div className="RoleDisp">
    <button className="UserPageButton" onClick={()=>{nav("/user/"+user.userId)}}>{user.username}</button>
    <h5 className="UserInfo">{user.role}</h5>
    </div>

    <br></br>
    </>


    } else {

    return <>
    <div>
    
        <div className="NavPanel" >
    <button className="UserButton" onClick={logOut}>LogOut</button></div>
    
    <div className="RoleDisp">
    <button className="UserPageButton" onClick={()=>{nav("/user/"+user.userId)}}>{user.username}</button>
    <h5 className="UserInfo">{user.role}</h5>
    </div>
    </div></>

    }

}

export default UserPage