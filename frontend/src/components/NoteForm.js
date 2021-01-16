import React, { useState, useRef } from "react";
import Form from "react-validation/build/form";
import Input from "react-validation/build/input";
import CheckButton from "react-validation/build/button";
import NoteService from "../services/note.service"
import {required} from "./Validations";

const NoteForm = ({isPublic, onAdd}) => {
    const form = useRef();
    const checkBtn = useRef();

    const [body, setBody] = useState("");

    const onChangeBody = (e) => {
        const body = e.target.value;
        setBody(body);
    };

    const handleAddNote = (e) => {
        e.preventDefault();

        form.current.validateAll();

        if (checkBtn.current.context._errors.length === 0) {
            NoteService.add(isPublic, body);
            onAdd();
        }
    };

    return (
    <div className="card card-outline-danger">
        <div className="card-header container-fluid">
            <div className="row">
                <div className="col-md-8">
                    <h5>Add note</h5>
                </div>
            </div>
        </div>
        <div className="card-body">
            <Form onSubmit={handleAddNote} ref={form}>
                <div className="form-group">
                    <label htmlFor="body">Text: </label>
                    <Input
                        type="text"
                        className="form-control"
                        name="username"
                        value={body}
                        onChange={onChangeBody}
                        validations={[required]}
                    />
                </div>
                <div className="form-group">
                    <button className="btn btn-primary btn-block">
                        <span>Add</span>
                    </button>
                </div>

                <CheckButton style={{ display: "none" }} ref={checkBtn} />
            </Form>
        </div>
    </div>
    );
};

export default NoteForm;
