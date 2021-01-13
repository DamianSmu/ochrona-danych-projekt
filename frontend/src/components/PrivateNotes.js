import Notes from "./Notes";
import AuthService from "../services/auth.service";

const PrivateNotes = () => {
    const user = AuthService.getCurrentUser();
    return (
        <div>
            <Notes isPublic={false} username={user && user.username}/>
        </div>
    )
}

export default PrivateNotes;