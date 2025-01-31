import { ReactNode, useContext } from "react"
import { useNavigate } from "react-router-dom"
import { AuthContext } from "./Common/Authorization"



function About():ReactNode {
    const nav = useNavigate()
    const toLogin = ()=>{nav("login")}
    const toRegister = ()=>{nav("register")}
    const auth = useContext(AuthContext)

    
    if (auth.token===""){
    return <>
    <button onClick={toLogin} >Login</button><button onClick={toRegister}>Register</button>
    <h3 className="Welcome">Welcome to Rimbsr</h3>
    <h5 className="Quote">Your reimbursment paradise</h5>
    
    </>
    } else {
        nav("/user")
    }
}

export default About 