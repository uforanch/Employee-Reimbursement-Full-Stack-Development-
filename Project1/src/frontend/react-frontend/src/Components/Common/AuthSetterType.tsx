import AuthorizationProps from "./Authorization";

interface AuthSetterType{
    authSetter:(_:AuthorizationProps)=>void;
    flagSetter:(_:number)=>void;
}

export default AuthSetterType