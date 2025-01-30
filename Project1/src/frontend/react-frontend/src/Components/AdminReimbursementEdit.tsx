

import { useContext, useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom"

import configData from "../config/config.json"
import axios from "axios";
import { reimbursementUnMap, reimbusementMap, ReimbursementProps} from "./Common/Utils";
import { AuthContext } from "./Common/Authorization";

/*
useeffect get the current reimburesment
if you can't, error and redirect
*/ 

function AdminReimbursementEdit() {
    const token = useContext(AuthContext).token;
        const config = {headers: {
                'Authorization':`Bearer ${token}`
            }}
    
    const nav = useNavigate()
    const id = useParams().id;
    const [getDesc, setDesc] = useState<string>("")
    const [getR, setR] = useState<ReimbursementProps>({id:0, value:0, description:"", status: "", date_issued:new Date(), username:""});
    console.log(id);

    
    const axiosSubmit00=async ()=>{
        const urlString = configData.SERVER_URL ;


        console.log(urlString)
        await axios.get(urlString +"/reimbursements/"+id, config).then(
            (response)=>{console.log(response);
                const r:ReimbursementProps = reimbusementMap(response.data)
                setR(r);
                setDesc(r.description);

            }

            

        ).catch(
            (error)=>{console.log(error)}

        )

    }


    
    const axiosSubmit01=async ()=>{
        const urlString = configData.SERVER_URL ;


        console.log(urlString)
        const config = {withCredentials:false}
        const r:ReimbursementProps = {...getR, description:getDesc as string}

        await axios.patch(urlString +"/reimbursements", reimbursementUnMap(r),config).then(
            (response)=>{console.log(response);
            //setDesc(r.description);
            
                nav("/user")



            }

            

        ).catch(
            (error)=>{console.log(error)}

        )
    }

        const axiosSubmit02=async ()=>{
        const urlString = configData.SERVER_URL ;


        console.log(urlString)
        const config = {withCredentials:false}

        await axios.patch(urlString +"/reimbursements/" + getR.id + "/delete", null,config).then(
            (response)=>{console.log(response);
            //setDesc(r.description);
            
                nav("/user")



            }

            

        ).catch(
            (error)=>{console.log(error)}

        )
    }
    useEffect(()=>{axiosSubmit00()}, [])

    return <>
    <div className="Edit">
        <p>date issued: </p> <p className="Disp">{getR.date_issued.toString()}</p><p> value:</p><p className="Disp"> {getR.value} </p><p>user: </p><p className="Disp"> {getR.username} </p><p> status: 
            </p><p><select value={getR.status} onChange={(event)=>{setR({...getR, status:event.target.value})}}>
                <option value="Pending">Pending</option>
                <option value="Denied">Denied</option>
                <option value="Fulfilled">Fulfilled</option>
            </select> </p>
        <p>description:</p> <p> <input type="text" className="Description" value={getDesc} onChange={(event)=>{setDesc(event.target.value)}}/></p>
            <div>
        <button className="OK" onClick={()=>{axiosSubmit01()}}>OK</button><button className="Cancel"  onClick={()=>{nav('/user')}}>Cancel</button>
    </div>
    </div>

    </>
}

export default AdminReimbursementEdit
//<button className="Cancel" onClick={()=>{axiosSubmit02()}}>Delete</button> //didn't work