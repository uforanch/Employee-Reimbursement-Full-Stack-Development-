import { ReactNode } from "react"
import { useNavigate } from "react-router-dom"

function About():ReactNode {
    const nav = useNavigate()
    const toLogin = ()=>{nav("login")}
    const toRegister = ()=>{nav("register")}

    return <>
    <h3>Welcome to Rimbsr</h3>
    <h5 className="Quote">Your reimbursment paradise</h5>
    <button onClick={toLogin}>Login</button><button onClick={toRegister}>Register</button>

    </>
}

export default About 