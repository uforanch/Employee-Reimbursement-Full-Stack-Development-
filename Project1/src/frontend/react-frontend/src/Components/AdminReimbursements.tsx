import { useContext, useEffect, useState } from "react"
import { AuthContext } from "./Common/Authorization"
import { ReimbursementProps, reimbusementMap, UserReturn } from "./Common/Utils";
import { useNavigate } from "react-router-dom";

import configData from "../config/config.json"///////////
import axios from "axios";

function EditButton({id}:{id:number}){
    const nav = useNavigate()
    return <button key={"btn"+id} onClick={()=>{nav("/user/reimbursements/edit/"+id)}}>EDIT</button>

}

const valueFormatter = new Intl.NumberFormat("en-US", {
  style: "currency",
  currency: "USD",
  maximumFractionDigits: 2,
})

/*
effects pull all users

effect:
if select is all, do all reimburements (value = 0)

*/

function AdminReimbursementItem({id,value,description,status,date_issued, username}:ReimbursementProps){
    return <><tr><td>{date_issued.toString()}</td>
      <td>{username}</td>
    <td>{description}</td>
  
    <td>{status}</td>
    <td>{valueFormatter.format(value)}</td>
    <td><EditButton id={id}/></td>
    </tr></>

}


const topOption ={userId:0,username:"Any User"}

function AdminReimbursements(){
    const auth = useContext(AuthContext); 
    const nav = useNavigate()
    const [getUsers, setUsers] = useState<UserReturn[]>([])
    const r: ReimbursementProps[]=[{id:1, value:2.0, description:"x", status:"Pending", date_issued:new Date(), username:"USER 1"},
    {id:2, value:2.0, description:"y", status:"Pending", date_issued:new Date(), username:"USER 2"}
]
    const [getStatus, setStatus] = useState("any");
    const [getUserFilter, setUserFilter] = useState(0);
    const [getR, setR] =useState(r);

    

    const loadAllUsers = async ()=>{
        let urlString = configData.SERVER_URL;

        console.log(urlString)
        const config = {withCredentials:false}
        await axios.get(urlString +"/users", config).then(
            (response)=>{console.log(response.data)
            response.data.unshift(topOption)
            setUsers(response.data as UserReturn[])
            console.log(getUsers)

            }

        ).catch(
            (error)=>{console.log(error)}

        )

    }



    const axiosSubmit00=async ()=>{
        let urlString = configData.SERVER_URL;

        if (getUserFilter!=0){
            switch (getStatus) {
                case "any": { urlString+="/users/"+getUserFilter+"/reimbursements"; break;}
                case "pending": { urlString+="/users/"+getUserFilter+"/reimbursements/pending"; break;}
                default: urlString+="/users/"+getUserFilter+"/reimbursements"; /*TODO change later for more statuses*/
            }
        } else {
            switch (getStatus) {
                case "any": { urlString+="/reimbursements"; break;}
                case "pending": { urlString+="/reimbursements/pending"; break;}
                default: urlString+="/reimbursements"; /*TODO change later for more statuses*/
            }
        }
        console.log(urlString)
        const config = {withCredentials:false}
        await axios.get(urlString, config).then(
            (response)=>{console.log(response)
                setR(response.data.map(reimbusementMap))
            }

            

        ).catch(
            (error)=>{console.log(error)}

        )

    }    
    //useEffect(()=>{},[getStatus])

    useEffect(()=>{loadAllUsers(); axiosSubmit00()},[getStatus, getUserFilter])
    
    const username = auth.username;
    const itemMap = (item:{userId:number; username:string})=><option value={item.userId}>{item.username}</option>
    
    return <>
    <div>
        <select value={getStatus} onChange={event=>{setStatus(event.target.value)}}>
            <option value={"any"}>{"Any Status"}</option>
            <option value={"pending"}>{"Pending"}</option>
        </select>
        {/*todo populate with a second useeffect to get users */}
        <select value={getUserFilter} onChange={event=>{setUserFilter(Number.parseInt( event.target.value) )}}>
            {getUsers.map((item:UserReturn)=><option value={item.userId}>{item.username}</option>)}
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
                    User
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
                {getR.map((item: ReimbursementProps)=><><AdminReimbursementItem {...item} key={item.id} /></>)}
            </tbody>
        </table>
    </div>
    </>
}

export default AdminReimbursements