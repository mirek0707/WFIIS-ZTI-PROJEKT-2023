import React, { useState, useEffect, } from "react";

import axios from 'axios'

import moment from 'moment';

import Form from "react-validation/build/form";
import Textarea from "react-validation/build/textarea";
import CheckButton from "react-validation/build/button";

import AuthService from "../services/auth_service";

import Tab from 'react-bootstrap/Tab';
import Tabs from 'react-bootstrap/Tabs';
import Card from 'react-bootstrap/Card';

export default function CommentComp(props) {

    //forms
    const [commentForm, setCommentForm] = useState("")
    //buttons
    const [commentButton, setCommentButton] = useState("")
    //for adding comments
    const [commentContent, setCommentContent] = useState("")

    const [successfulCom, setSuccessfulCom] = useState(false)
    const [messageCom, setMessageCom] = useState("")

    const [commentsArr, setCommentsArr] = useState([])
    
    //validation
    const required = value => {
        if (!value) {
            return (
                <div className="alert alert-danger" role="alert">
                    Pole wymagane!
                </div>
            );
        }
    };
    
    const lt = value => {

        if (value.toString().length > 300) {
            return (
                    <div className="alert alert-danger" role="alert">
                        Twój komentarz jest zbyt długi!
                    </div>
            );
        }
    };

    const getComments = async () => {
        const dataJson = JSON.stringify({
            transfer_id: props.status === "" ? "" : props.status.id.toString()
        })
        try {
            const res = await axios.post((process.env.baseURL || "http://localhost:8080") + '/api/test/getComments', dataJson, {
                headers: { 'Content-Type': 'application/json' }
            })
            if (res.status === 200) {
                setCommentsArr(res.data)
            }
        }
        catch (err) {
        }
    }

    useEffect(() => {
        getComments()
    }, [props.status, successfulCom===true]) // eslint-disable-line react-hooks/exhaustive-deps
    

    const addComment = async () => {
        const dataJson = JSON.stringify({
            transfer_id: props.status.id,
            user: AuthService.getCurrentUser().username,
            date: new Date(moment().format()),
            content: commentContent
        })
        var res
        try {
            res = await axios.post((process.env.baseURL || "http://localhost:8080") + '/api/test/addComment', dataJson, {
                headers: { 'Content-Type': 'application/json' }
            })
            if (res.status === 200) {
                alert("Dodanie komentarza powiodło się.")
                setCommentContent("")
                return res;
            }
        }
        catch (err) {
            alert("Błąd dodawania.")
            return [res,err];
        }
    }

    const handleComment = (e) => {
        e.preventDefault();

        commentForm.validateAll();

        if (commentButton.context._errors.length === 0) {
            addComment().then(
                res => {
                    setMessageCom(res.message)
                    setSuccessfulCom(true)
                },
                err => {
                    const resMessage =
                        (err.response &&
                            err.response.data &&
                            err.response.data.message) ||
                        err.message ||
                        err.toString();

                    setMessageCom(resMessage)
                    setSuccessfulCom(false)
                }
            );
        }
        setCommentForm("")
        setSuccessfulCom(false)
        setMessageCom("")
        setCommentButton("")
    }


    return (
        <>
            <Tabs
                defaultActiveKey="comments"
                transition={true}
                id="noanim-tab-example"
                className="mb-3"
            >
                <Tab eventKey="comments" title="Komentarze">
                    
                        <Form
                            onSubmit={handleComment}
                            ref={c => {
                                setCommentForm(c);
                            }}
                        >
                            <div>
                                <div className="form-group">
                                    <label htmlFor="content">Napisz komentarz:</label>
                                    <Textarea
                                        className="form-control"
                                        name="content"
                                        id="content"
                                        placeholder="..."
                                        value={commentContent}
                                        onChange={(e) => { setCommentContent(e.target.value) }}
                                        validations={[required, lt]}
                                    />
                                </div>

                                <div className="form-group">
                                    <button className="btn btn-primary btn-block">Dodaj komentarz</button>
                                </div>
                            </div>

                            {messageCom && (
                                <div className="form-group">
                                    <div
                                        className={
                                            successfulCom
                                                ? "alert alert-success"
                                                : "alert alert-danger"
                                        }
                                        role="alert"
                                    >
                                        {messageCom}
                                    </div>
                                </div>
                            )}
                            <CheckButton
                                style={{ display: "none" }}
                                ref={c => {
                                    setCommentButton(c);
                                }}
                            />
                        </Form>

                        {commentsArr.map((comment) => (
                            
                            <Card key={comment.id}>
                                <Card.Body>
                                    <Card.Title><strong>{comment.user}</strong> - {moment(comment.date).add(2, 'hours').format('DD.MM.YYYY, HH:mm:ss')}</Card.Title>
                                    <Card.Text>
                                        {comment.content}
                                    </Card.Text>
                                </Card.Body>
                            </Card>
                        
                        ))}
                </Tab>
            </Tabs>
        </>
    )
}
