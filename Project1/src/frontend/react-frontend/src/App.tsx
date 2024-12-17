
import { BrowserRouter, Route, Routes, useParams } from 'react-router-dom'
import './App.css'
import About from './Components/About'
import Register from './Components/Register'

import { createContext, useState } from 'react'
import AuthorizationProps, { AuthContext, DefaultAuth } from './Components/Common/Authorization'
import UserPage from './Components/UserPage'
import Login from './Components/Login'
import LogOut from './Components/LogOut'
import Reimbursements from './Components/Reimbursements'

function Test() {
  let {id} = useParams();
  return (<div>{id}</div>)
}

function App() {
  const [getAuth, setAuth] = useState<AuthorizationProps>(DefaultAuth)
  const authSetter = (a:AuthorizationProps) =>(setAuth(a))

  return (<>
    <div>
    <h1 className='Title'>Rimbsr</h1>
    </div>
    <AuthContext.Provider value={getAuth}>
    <BrowserRouter>
    <Routes>
      
   
      <Route path="/" element={<About/>}/>
      {/*TODO: add auth setting to this */}
      <Route path="/register" element={ <Register/>}/>
      <Route path="/login" element={<Login authSetter={authSetter}/>}/>
      <Route path="/logout" element={<LogOut authSetter={authSetter}/>}/>
      {/*TODO add reimbursements and reim edit to the below
      */}
      <Route path="/user" element={<><UserPage/><Reimbursements/></>}/>
      <Route path="/user/reimbursements/:id" element={<><UserPage/><Test></Test></>}/>

    
    </Routes>
    </BrowserRouter>
    </AuthContext.Provider>
    </>
  )
}

export default App
