/*


TODO:
Add order by date in backend
edit buttons functionality


*/

import { useContext, useEffect, useState } from "react"
import { AuthContext } from "./Common/Authorization"
import ReimbursementProps from "./Common/ReimbursementType";
import { useNavigate } from "react-router-dom";

import configData from "../config/config.json"
import axios from "axios";

function EditButton({id}:{id:number}){
    const nav = useNavigate()
    return <button key={"btn"+id} onClick={()=>{nav("/users/reimbursements/"+id)}}>EDIT</button>

}

function HighLightUsername({username}:{username:string}) {
    const authUsername = useContext(AuthContext).username
    if (authUsername===username){
        return <span className="Highlight">username</span>
    }
    return <>username</>
    
}

function ReimbursementItem({id,value,description,status,date_issued, username}:ReimbursementProps){
    return <><tr><td>{date_issued.toISOString()}</td>
    <td><HighLightUsername username={username}/></td>
    <td>{description}</td>
    <td>{status}</td>
    <td>{value}</td>
    <td><EditButton id={id}/></td>
    </tr></>

}

const r: ReimbursementProps[]=[{id:1, value:2.0, description:"x", status:"Pending", date_issued:new Date(), username:"Person1111"}]

function Reimbursements(){
    const auth = useContext(AuthContext);
    const [getStatus, setStatus] = useState("user");
    const [getR, setR] =useState(r);
    const axiosSubmit00=async ()=>{
        let urlString = configData.SERVER_URL;
        switch (getStatus) {
            case "user": { urlString+="/users/"+auth.userId+"/reimbursments"; break;}
            case "user_pending": { urlString+="/users/"+auth.userId+"/reimbursments/pending"; break;}
            default: urlString+="/users/"+auth.userId+"/reimbursments"; /*TODO change later*/
        }
        console.log(urlString)
        const config = {headers:{"Content-Type":"application/json", "Authorization":"Bearer yourAccessToken"}, timeout:5000, withCredentials:false}
        await axios.get(urlString, config).then(
            (response)=>{console.log(response.data)}

        ).catch(
            (error)=>{console.log(error)}

        )

    }
    useEffect(()=>{axiosSubmit00() },[getStatus])
    
    const username = auth.username;

    return <>
    <div>
        <select value={getStatus} onChange={event=>{setStatus(event.target.value)}}>
            <option value={"ALL"}>ALL</option>
            <option value={"user"}>{username +"'s"}</option>
            <option value={"user_pending"}>{username +"'s pending"}</option>
        </select>
    </div>
    <div>
        <table>
            <thead>
                <tr>
                <th>
                    Date Issued
                </th>
                <th>
                    Username
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
                {r.map((item: ReimbursementProps)=><><ReimbursementItem {...item}/></>)}
            </tbody>
        </table>
    </div>
    </>
}

export default Reimbursements