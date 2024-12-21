import { useContext } from "react"
import AuthorizationProps, { AuthContext } from "./Common/Authorization"
import { BrowserRouter, Route, Routes, useNavigate } from "react-router-dom"

function UserPage() {
    const user = useContext(AuthContext)
    const nav = useNavigate()
    const logOut = ()=>{nav("/logout")}
    if (user.userId==0){
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