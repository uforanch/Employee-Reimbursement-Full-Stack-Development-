import { useContext } from "react"
import AuthorizationProps, { AuthContext } from "./Common/Authorization"
import { BrowserRouter, Route, Routes } from "react-router-dom"

function UserPage() {
    const user = useContext(AuthContext)
    if (user.userId==0){
        return <>No User</>
    }

    return <>
    <div>
    <h1>{user.username}</h1>
    <h2>{user.role}</h2>
    <button>LogOut</button>
    </div>
    <BrowserRouter>
    <Routes>
        <Route>  </Route>
    </Routes>
    </BrowserRouter></>

}

export default UserPage