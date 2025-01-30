

import { useContext, useState } from "react";
import { useNavigate, useParams } from "react-router-dom"

import configData from "../config/config.json"
import {ReimbursementProps} from "./Common/Utils"
import axios from "axios";
import { AuthContext } from "./Common/Authorization";
import { ReimburementReturn, reimbursementUnMap } from "./Common/Utils";

function EmployeeNewReimbursementEdit() {
    const token = useContext(AuthContext).token;
            const config = {headers: {
                'Authorization':`Bearer ${token}`
            }}
    
    const nav = useNavigate();
    const id  = useContext(AuthContext).userId;
    const username =  useContext(AuthContext).username;
    const role = useContext(AuthContext).role;
    
    const [getDesc, setDesc] = useState<String>("")
    const [getDate, setDate] = useState("")/*tODO change to date, set to date*/
    const [getValue, setValue] = useState("")//for all are strings and will be changed

    
    const axiosSubmit01=async ()=>{
        const urlString = configData.SERVER_URL ;


        console.log(urlString)

        const r:ReimbursementProps = {date_issued: new Date(getDate), description:getDesc as string, status:"Pending", value:parseFloat(getValue), username:"", id:0};    
        let userId = "";
        await axios.get(urlString + "/users/" + id, config).then((response)=>{userId=response.data.userId})
        const r_send:ReimburementReturn ={...reimbursementUnMap(r), user:{username:username, shortId:id, role:role, userId:userId}}

        await axios.post(urlString +"/reimbursements", r_send,config).then(
            (response)=>{console.log(response);
            //setDesc(r.description);
            
            nav("/user")

            }

            

        ).catch(
            (error)=>{console.log(error)}

        )
    }

    return <>
    <div className="Edit">
        <p>date issued:</p> <p><input type="date" onChange={(event)=>{setDate(event.target.value)}}/></p><p> value: </p><p><input type="number" onChange={(event)=>{setValue(event.target.value)}}/></p><p> user: </p><p className="Disp"> {username} </p><p>status:</p><p className="Disp"> PENDING</p>
        <p>description:</p> <p><input type="text" className="Description" onChange={(event)=>{setDesc(event.target.value)}}/></p>
        <div>
        <button className="OK" onClick={()=>{axiosSubmit01()}}>OK</button><button className="Cancel" onClick={()=>{nav('/user')}}>Cancel</button>
    </div>
    </div>

    </>
}

export default EmployeeNewReimbursementEdit