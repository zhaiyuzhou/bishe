import React, {useEffect, useState, useMemo} from "react";
import Home from "../../pages/Home/Home";
import {useNavigate} from 'react-router';
import {Route, Routes, useLocation} from 'react-router-dom';
import SignBox from '../../pages/SignBox/SignBox';
import Pencen from "../../pages/Pencen/Pencen";
import cookie from 'react-cookies'
import axios from 'axios';
import Forget from "../../pages/Forget/Forget";
import Perspac from "../../pages/Perspac/Perspac";
import MyHeader from "../Header/MyHeader";
import Msgcen from "../../pages/Msgcen/Msgcen";

const App = () => {


    const [isLogin, setIsLogin] = useState(cookie.load('isLogin') === 'true');
    const [user, setUser] = useState({avatar: ""});
    const navigate = useNavigate();
    const location = useLocation();

    // 搜索内容
    const [searchDate, setSearchDate] = useState();

    useEffect(() => {
        axios.get("/api/cook").then(() => {
            setIsLogin(cookie.load('isLogin') === 'true');
        });

        if (isLogin) {
            axios.get("/api/user")
                .then(function (response) {
                    console.log(response);
                    setUser(response.data.data);
                })
                .catch(function (error) {
                    console.log(error);
                })
        }

        if (location.pathname === "/")
            navigate("/主页", {replace: true});
        setIsLogin(cookie.load('isLogin') === 'true');
    }, [isLogin])

    return (
        <div>
            {
                useMemo(() => {
                    return (
                        <Routes>
                <Route path="/主页/*"
                       element={<MyHeader isLogin={isLogin} user={user} setSearchDate={setSearchDate} noSearch={false}
                                          element={<Home searchDate={searchDate} isLogin={isLogin} user={user}/>}/>}/>
                            <Route path="/注册" element={<SignBox isLogin={isLogin} user={user}/>}/>
                <Route path="/个人中心/*" element={<MyHeader isLogin={isLogin} user={user} noSearch={true}
                                                             element={<Pencen isLogin={isLogin} user={user}/>}/>}/>
                <Route path="/忘记密码/*"
                       element={<MyHeader isLogin={isLogin} noSearch={true} element={<Forget user={user}/>}/>}/>
                            <Route path={'/个人动态/*'}
                                   element={<MyHeader isLogin={isLogin} user={user} noSearch={true}
                                                      element={<Perspac isLogin={isLogin} user={user}/>}/>}/>
                            <Route path={'/消息中心/*'}
                                   element={<MyHeader isLogin={isLogin} user={user} noSearch={true}
                                                      element={<Msgcen user={user}/>}/>}/>
            </Routes>
                    );
                })
            }
        </div>
    )
}


export default App;