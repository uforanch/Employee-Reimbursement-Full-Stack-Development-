interface ReimbursementProps {
    id:number;
    value:number;
    description: string;
    status: string;
    date_issued: Date;
    username:string; // only show on ALL view, 
}

export default ReimbursementProps