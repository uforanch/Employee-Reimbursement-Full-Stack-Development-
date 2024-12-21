/* 
TODO: Refactor so everything involving the server is in server utils, probably
types will be in here too, because 


*/


//import configData from "../../config/config.json"
export interface UserReturn{
    userId: number;
    username: string;
    role: string;
    firstname?: string;
    lastname?: string;
    
}

export interface ReimburementReturn {
    reimbursementId: number;
    value: number;
    status: string;
    description: string;
    date_issued: Date;
    user?: UserReturn;
}

export interface ReimbursementProps {
    id:number;
    value:number;
    description: string;
    status: string;
    date_issued: Date;
    username:string; // only show on ALL view, 
}

export const reimbusementMap = (r: ReimburementReturn):ReimbursementProps=>{
    return {id:r.reimbursementId, 
        value:r.value, 
        description: r.description, 
        date_issued: r.date_issued, 
        username: r.user==null? "" :  r.user.username, 
        status: r.status}}

export const reimbursementUnMap = (r:ReimbursementProps):ReimburementReturn=>{
    console.log()
    return {reimbursementId:r.id, 
        value:r.value, 
        description: r.description, 
        date_issued: r.date_issued, 
  
        status: r.status}

}   

