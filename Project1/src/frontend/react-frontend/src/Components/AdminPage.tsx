/*



*/

import axios from "axios";
import { useNavigate } from "react-router-dom"
import configData from "../config/config.json"
import { useEffect, useState } from "react";
import { UserReturn } from "./Common/Utils";

function AdminPage() {
    const nav = useNavigate()

    const [getUsers, setUsers] = useState<UserReturn[]>([])
    
    const loadAllUsers = async ()=>{
        let urlString = configData.SERVER_URL;

        console.log(urlString)
        const config = {withCredentials:false}
        await axios.get(urlString +"/users", config).then(
            (response)=>{console.log(response.data)
            setUsers(response.data as UserReturn[])
            console.log(getUsers)

            }

        ).catch(
            (error)=>{console.log(error)}

        )

    }
    useEffect(()=>{loadAllUsers()},[])

    return <>
    <table>
        <thead>
            <tr>
                <th>
                    User
                </th>
                <th>
                    Role
                </th>
                <th>
                    Options
                </th>
            </tr>
        </thead>
        <tbody>
            
                {getUsers.map((u)=><tr key={u.userId}>
                    <td>{u.username}</td>
                    <td>{u.role}</td>
                    <td><button className="Cancel" onClick={()=>{nav("/user/"+u.userId)}}>EDIT</button>
                    </td>
                    </tr>)}
            
        </tbody>
    </table>
    <div>
        <button onClick={()=>{nav("/user")}}>Back to Main</button>
    </div>
    </>
}

export default AdminPage