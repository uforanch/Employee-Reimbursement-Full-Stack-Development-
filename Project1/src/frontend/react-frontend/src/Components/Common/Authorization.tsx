import { createContext } from "react";

interface AuthorizationProps{
    username:string;
    userId:string;
    role:string;
    token:string;
}
    
export const DefaultAuth:AuthorizationProps = {username:"anonymous",userId:"1234567890",role:"employee", token:""}
export const AuthContext = createContext<AuthorizationProps>(DefaultAuth)
export default AuthorizationProps
