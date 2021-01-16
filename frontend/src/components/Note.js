import React from "react";

const Note = ({id, user, body, canDelete, onDelete}) => {
    return (
        <div className="card card-outline-danger" key={id}>
            <div className="card-header container-fluid">
                <div className="row">
                    <div className="col-md-8">
                        <h5>Autor: {user}</h5>
                    </div>
                    <div className="col">
                        {canDelete && (<button type="button" className="close" aria-label="Close" value={id}
                                               onClick={onDelete}>
                            <span aria-hidden="true">&times;</span>
                        </button>)}
                    </div>
                </div>
            </div>
            <div className="card-body">
                <h6>{body}</h6>
            </div>
        </div>
    )
}
export default Note;