

import { useContext, useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom"

import configData from "../config/config.json"
import {ReimbursementProps, UserReturn} from "./Common/Utils"
import axios from "axios";
import { AuthContext } from "./Common/Authorization";
import { ReimburementReturn, reimbursementUnMap } from "./Common/Utils";

function AdminNewReimbursementEdit() {
    
    const nav = useNavigate();
    const id  = useContext(AuthContext).userId;
    const username =  useContext(AuthContext).username;
    const role = useContext(AuthContext).role;
    const [getStatus, setStatus] = useState("Pending");
    const [getDesc, setDesc] = useState<String>("")
    const [getDate, setDate] = useState("")/*tODO change to date, set to date*/
    const [getValue, setValue] = useState("")//for all are strings and will be changed
    const [getR, setR] = useState<ReimbursementProps>({id:0, value:0, description:"", status:"", date_issued:new Date(), username:""});
    const [getUsers, setUsers] = useState<UserReturn[]>([])
    const [getUsersFilter, setUsersFilter] = useState(0)
    
    
    const urlString = configData.SERVER_URL ;
        const token = useContext(AuthContext).token;
            const config = {headers: {
                'Authorization':`Bearer ${token}`
            }}
    
    const loadAllUsers = async ()=>{
        let urlString = configData.SERVER_URL;

        console.log(urlString)
        await axios.get(urlString +"/users", config).then(
            (response)=>{console.log(response.data)
            setUsers(response.data as UserReturn[])
            console.log(getUsers)

            }

        ).catch(
            (error)=>{console.log(error)}
        )

    }
       
            

    const axiosSubmit01=async ()=>{
        const urlString = configData.SERVER_URL ;


        console.log(urlString)
        const r:ReimbursementProps = {...getR, description:getDesc as string, status:getStatus}
        const r_send:ReimburementReturn ={...reimbursementUnMap(r), user:getUsers[getUsersFilter]}
        console.log(r_send)
        await axios.post(urlString +"/reimbursements", r_send,config).then(
            (response)=>{console.log(response);
            //setDesc(r.description);
            
                nav("/user")



            }

            

        ).catch(
            (error)=>{console.log(error)}

        )
    }
   
    useEffect(()=>{loadAllUsers()},[])

    return <>
    <div className="Edit">
        <p>date issued:</p> <p><input type="date" onChange={(event)=>{setDate(event.target.value)}}/></p><p> value: </p><p><input type="number" onChange={(event)=>{setValue(event.target.value)}}/></p>
        <p> user: </p><p> <select onChange={(event)=>{setUsersFilter(Number.parseInt(event.target.value))}}>{getUsers.map((u)=><option value={u.userId}>{u.username}</option>)}</select> </p>
        
        <p>status:</p><p> </p><p><select value={getStatus} onChange={(event)=>{setStatus(event.target.value)}}>
                <option value="Pending">Pending</option>
                <option value="Denied">Denied</option>
                <option value="Fulfilled">Fulfilled</option>
            </select> </p>
        <p>description:</p> <p><input type="text" className="Description" onChange={(event)=>{setDesc(event.target.value)}}/></p>
        <div>
        <button className="OK" onClick={()=>{axiosSubmit01()}}>OK</button><button className="Cancel" onClick={()=>{nav('/user')}}>Cancel</button>
    </div>
    </div>

    </>
}

export default AdminNewReimbursementEdit