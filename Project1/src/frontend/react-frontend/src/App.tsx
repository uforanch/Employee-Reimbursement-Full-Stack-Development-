
import { BrowserRouter, Route, Routes, useParams } from 'react-router-dom'
import './App.css'
import About from './Components/About'
import Register from './Components/Register'

import { createContext, useEffect, useState } from 'react'
import AuthorizationProps, { AuthContext, DefaultAuth } from './Components/Common/Authorization'
import UserPage from './Components/UserPage'
import LogOut from './Components/LogOut'
import ErrorScreen from './Components/ErrorScreen'
import EmployeeReimbursements from './Components/EmployeeReimbursements'
import AdminReimbursements from './Components/AdminReimbursements'
import EmployeeReimbursementEdit from './Components/EmployeeReimbursementEdit'
import EmployeeNewReimbursementEdit from './Components/EmployeeNewReimbursementEdit'
import AdminPage from './Components/AdminPage'
import AdminNewReimbursementEdit from './Components/AdminNewReimbursementEdit'
import AdminReimbursementEdit from './Components/AdminReimbursementEdit'
import Login from './Components/LogIn'
import axios, { AxiosError } from 'axios'
import configData from "./config/config.json"
import UserManagementPage from './Components/UserManagementPage'
import AdminUserManagementPage from './Components/AdminUserManagementPage'

function Test() {
  let {id} = useParams();
  return (<div>{id}</div>)
}

/*
effect to add - get auth 

*/

function App() {
  const [getAuth, setAuth] = useState<AuthorizationProps>(DefaultAuth)
  const [authChangeFlag, setAuthChangeFlag] = useState<number>(Date.now());
  const authSetter = (a:AuthorizationProps) =>(setAuth(a))

  
    const GetAuthFunc = () =>{
      if (getAuth.token===""){
        const storedUserJson = localStorage.getItem("loggedInUser");
        const storedUser = storedUserJson ? JSON.parse(storedUserJson) : null;
        if (storedUser===null){ 
          setAuth(DefaultAuth);
        } else {
          setAuth(storedUser);
        }
      }
      
        
    }
    useEffect(GetAuthFunc, [authChangeFlag])

    
  console.log(getAuth)

  if (getAuth.token.length !=0 && getAuth.role==="Manager"){
return (<>
    <div>
    <h1 className='Title'>Rimbsr</h1>
    </div>
    <AuthContext.Provider value={getAuth}>
    <BrowserRouter>
    <Routes>
      
   
      <Route path="/" element={<><ErrorScreen text="" navTo='/user'/></>}/>
      <Route path="/register" element={ <><ErrorScreen text="You are already logged in" navTo='/user'/></>}/>
      <Route path="/login" element={<><ErrorScreen text="You are already logged in" navTo='/user'/></>}/>
      <Route path="/logout" element={<LogOut authSetter={authSetter} flagSetter={setAuthChangeFlag}/>}/>
      <Route path="/user" element={<><UserPage/><AdminReimbursements/></>}/>
      <Route path="/user/reimbursements/edit/:id" element={<><UserPage/><AdminReimbursementEdit/></>}/>
      <Route path="/user/reimbursements/new" element={<><UserPage/> <AdminNewReimbursementEdit/></>}/>
      <Route path="/user/adminpage" element={<><UserPage/> <AdminPage/></>}/>
      <Route path="/user/:id" element={<><UserPage/><AdminUserManagementPage/></>}/>
    
    </Routes>
    </BrowserRouter>
    </AuthContext.Provider>
    </>
  )
    
  } else if (getAuth.token.length != 0) {
return (<>
    <div>
    <h1 className='Title'>Rimbsr</h1>
    </div>
    <AuthContext.Provider value={getAuth}>
    <BrowserRouter>
    <Routes>
      
   
      <Route path="/" element={<><ErrorScreen text="" navTo='/user'/></>}/>
      <Route path="/register" element={ <><ErrorScreen text="You are already logged in" navTo='/user'/></>}/>
      <Route path="/login" element={<><ErrorScreen text="You are already logged in" navTo='/user'/></>}/>
      <Route path="/logout" element={<LogOut authSetter={authSetter} flagSetter={setAuthChangeFlag}/>}/>
      <Route path="/user" element={<><UserPage/><EmployeeReimbursements/></>}/>
      <Route path="/user/reimbursements/edit/:id" element={<><UserPage/><EmployeeReimbursementEdit/></>}/>
      <Route path="/user/reimbursements/new" element={<><UserPage/> <EmployeeNewReimbursementEdit/></>}/>
      <Route path="/user/adminpage" element={<><ErrorScreen text="No access" navTo='/user'/></>}/>
      <Route path="/user/:id" element={<><UserPage/><UserManagementPage/></>}/>
    
    </Routes>
    </BrowserRouter>
    </AuthContext.Provider>
    </>
  )
}
  return (<>
    <div>
    <h1 className='Title'>Rimbsr</h1>
    </div>
    <AuthContext.Provider value={getAuth}>
    <BrowserRouter>
    <Routes>
      
   
      <Route path="/" element={<About/>}/>
      <Route path="/register" element={ <Register authSetter={authSetter} flagSetter={setAuthChangeFlag}/>}/>
      <Route path="/login" element={<Login authSetter={authSetter} flagSetter={setAuthChangeFlag} />}/>
      <Route path="/logout" element={<LogOut authSetter={authSetter} flagSetter={setAuthChangeFlag}/>}/>
  
      <Route path="/user" element={<><ErrorScreen text="Please log in" navTo='/'/></>}/>
      <Route path="/user/reimbursements/edit/:id" element={<><ErrorScreen text="Please log in" navTo='/'/></>}/>
      <Route path="/user/reimbursements/new" element={<><ErrorScreen text="Please log in" navTo='/'/></>}/>
      <Route path="/user/adminpage" element={<><ErrorScreen text="Please log in" navTo='/'/></>}/>
      <Route path="/user/:id" element={<><ErrorScreen text="Please log in" navTo='/'/></>}/>

    
    </Routes>
    </BrowserRouter>
    </AuthContext.Provider>
    </>
  )
}

export default App
