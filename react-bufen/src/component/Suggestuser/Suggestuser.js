import {Avatar, Button} from "antd";
import axios from "axios";
import React, {useLayoutEffect, useState} from "react";
import "./Suggestuser.css";

const Suggestuser = (props) => {

    const [userList, setUserList] = useState([]);
    const [gz, setGz] = useState(false);
    const guanzhu = (id) => {
        setGz(true);
        axios.post("/api/attention", {
            userId: id,
        });
    }

    const quguan = (id) => {
        setGz(false);
        axios.post("/api/calAttention", {
            userId: id,
        });
    }

    const gzfun = (id) => {
        if (!gz) {
            guanzhu(id);
        } else {
            quguan(id);
        }
    }

    useLayoutEffect(() => {
        axios.get("/api/getUserList")
            .then((res) => {
                setUserList(res.data.data);
            });
    }, [])
    return (
        <div className="Suggestuser-div">
            {
                userList != null ?
                    (userList.filter((user) => user !== null).map((user, index) => {
                        return (
                            <div key={"user" + index} className="Suggestuser-user">
                                <Avatar className="Suggestuser-avatar" src={user.avatar}/>
                                <p className="Suggestuser-nickName">{user.nickName}</p>
                                <Button className="Suggestuser-button" disabled={!props.isLogin}
                                        onClick={() => gzfun(user.id)}>{gz ? "取关" : "关注"}</Button>
                            </div>
                        )
                    })) :
                    (<></>)
            }
        </div>
    )
}

export default Suggestuser;