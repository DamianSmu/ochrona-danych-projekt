import React, {useEffect, useState} from "react";
import NoteService from "../services/note.service"
import NoteForm from "./NoteForm";
import Note from "./Note";


const Notes = ({isPublic, username}) => {
    const [content, setContent] = useState("");
    const [updated, setUpdated] = useState(false);

    const deleteNote = (isPublic, id) => {
        NoteService.deleteNote(isPublic, id);
        setUpdated(true)
    }

    useEffect(() => {
        NoteService.getAll(isPublic).then(
            (response) => {
                setContent(response.data);
                setUpdated(false)
            },
            (error) => {
                const _content =
                    (error.response && error.response.data) ||
                    error.message ||
                    error.toString();
                setContent(_content);
                setUpdated(false)
            }
        );
    },[updated])

    return (
        <div className="container">
            <header className="jumbotron">
                <h4>Your {isPublic ? "public" : "private"} notes:</h4>
                {content.length > 0 && (
                    content.map((item, i) => (
                        <Note item={item.id} user={item.user.username} body={item.body} canDelete={username === item.user.username} onDelete={() => deleteNote(isPublic, item.id)} key={i}/>
                    ))
                )}
            </header>
            <NoteForm isPublic={isPublic} onAdd={()=> setUpdated(true)}/>
        </div>
    );
};

export default Notes;
