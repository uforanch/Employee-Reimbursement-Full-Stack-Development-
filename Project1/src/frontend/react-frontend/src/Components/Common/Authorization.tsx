import { createContext } from "react";

interface AuthorizationProps{
    username:string;
    userId:number;
    role:string;
}
    
export const DefaultAuth:AuthorizationProps = {username:"",userId:0,role:"employee"}
export const AuthContext = createContext<AuthorizationProps>(DefaultAuth)
export default AuthorizationProps
