import Notes from "./Notes";
import AuthService from "../services/auth.service";

const PublicNotes = () => {
    const user = AuthService.getCurrentUser();
    return (
        <div>
            <Notes isPublic={true} username={user && user.username}/>
        </div>
    )
}

export default PublicNotes;