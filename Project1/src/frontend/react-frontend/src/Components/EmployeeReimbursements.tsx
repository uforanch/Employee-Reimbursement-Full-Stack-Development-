import { useContext, useEffect, useState } from "react"
import { AuthContext } from "./Common/Authorization"
import {ReimbursementProps} from "./Common/Utils"
import { useNavigate } from "react-router-dom";

import configData from "../config/config.json"
import axios from "axios";
import { reimbusementMap } from "./Common/Utils";

function EditButton({id}:{id:number}){
    const nav = useNavigate()
    return <button key={"btn"+id} onClick={()=>{nav("/user/reimbursements/edit/"+id)}}>EDIT</button>

}


const valueFormatter = new Intl.NumberFormat("en-US", {
  style: "currency",
  currency: "USD",
  maximumFractionDigits: 2,
})


function EmployeeReimbursementItem({id,value,description,status,date_issued, username}:ReimbursementProps){
    return <><tr><td>{date_issued.toString()}</td>
    <td>{description}</td>
    <td>{status}</td>
    <td>{valueFormatter.format(value)}</td>
    <td><EditButton id={id}/></td>
    </tr></>

}


/*
effects

pull reimburesments into menu


*/



function EmployeeReimbursements(){
    const nav = useNavigate();
    const auth = useContext(AuthContext);
    const token = useContext(AuthContext).token;
    
        const r: ReimbursementProps[]=[{id:1, value:2.0, description:"x", status:"Pending", date_issued:new Date(), username:"USER 1"},
    {id:2, value:2.0, description:"y", status:"Pending", date_issued:new Date(), username:"USER 2"}
]
    const [getStatus, setStatus] = useState("any");
    const [getR, setR] =useState(r);
    const testEffect = ()=>{


    }

    const axiosSubmit00=async ()=>{
        let urlString = configData.SERVER_URL;
        switch (getStatus) {
            case "any": { urlString+="/users/"+auth.userId+"/reimbursements"; break;}
            case "pending": { urlString+="/users/"+auth.userId+"/reimbursements/pending"; break;}
            default: urlString+="/users/"+auth.userId+"/reimbursements"; /*TODO change later for more statuses*/
        }
        console.log(urlString)
        const config = {headers: {
                'Authorization':`Bearer ${token}`
            }}
        await axios.get(urlString, config).then(
            (response)=>{console.log(response)
                setR(response.data.map(reimbusementMap))
            }

            

        ).catch(
            (error)=>{console.log(error)}

        )

    }
    
    useEffect(()=>{axiosSubmit00()},[getStatus])
    
    const username = auth.username;

    return <>
    <div>
        <select value={getStatus} onChange={event=>{setStatus(event.target.value)}}>
            <option key={1} value={"any"}>{"Any Status"}</option>
            <option key={2} value={"pending"}>{"Pending"}</option>
        </select>
        <button onClick={()=>{nav("/user/reimbursements/new")}}>
            New 
        </button>
    </div>
    <div>
        <table>
            <thead>
                <tr>
                <th>
                    Date Issued
                </th>
                <th>
                    Description
                </th>
                <th>
                    Status
                </th>
                <th>
                    Value
                </th>
                <th>
                    Edit
                </th>
                </tr>
            </thead>
            <tbody>
                {getR.map((item: ReimbursementProps)=><><EmployeeReimbursementItem {...item} key={item.id}  /></>)}
            </tbody>
        </table>
    </div>
    </>
}

export default EmployeeReimbursements