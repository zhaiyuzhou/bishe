import React, {useEffect, useState, useRef} from "react";
import {Button, Input} from 'antd';
import "./Msgcen.css"
import Messageleft from "../../component/Message/Messageleft";
import Messageright from "../../component/Message/Messageright";

const {TextArea} = Input;

const Msgcon = (props) => {

    const [value, setValue] = useState('');
    const [messageList, setMessageList] = useState([]);

    const ws = useRef(null);

    useEffect(() => {
        ws.current = new WebSocket('ws://localhost:8080/chat/' + (typeof props.user === "undefined" ? 0 : props.user.id));

        ws.current.onmessage = e => {
            setMessageList([...messageList, <Messageleft user={props.friend} message={e.data}/>])
        };

        return () => {
            ws.current.close();
        };
    }, [props, messageList])

    // 接受消息


    // 发送消息
    const sendMessage = () => {
        ws.current.send(
            JSON.stringify({
                to: props.friend.id,
                message: value,
            })
        );
        setMessageList([...messageList, <Messageright user={props.user} message={value}/>])
    }

    return (
        <div className="Msgcon-div">
            <div className="Msgcon-message">
                {messageList}
            </div>
            <TextArea
                className="Msgcon-input"
                value={value}
                onChange={(e) => setValue(e.target.value)}
                placeholder="请输入内容"
                autoSize={{
                    minRows: 5,
                    maxRows: 10,
                }}
            />
            <Button type='primary' className="Msgcon-send-button" onClick={sendMessage}>发送</Button>
        </div>
    )
}

export default Msgcon;