import React, {useEffect, useState} from "react";
import Home from "../../pages/Home/Home";
import {useNavigate} from 'react-router';
import {Route, Routes, useLocation} from 'react-router-dom';
import SignBox from '../../pages/SignBox/SignBox';
import Pencen from "../../pages/Pencen/Pencen";
import cookie from 'react-cookies'
import axios from 'axios';
import Forget from "../../pages/Forget/Forget";

const App = () => {

    const [isLogin, setIsLogin] = useState(cookie.load('isLogin') === 'true');
    const [user, setUser] = useState({avatar: ""});
    const navigate = useNavigate()
    const location = useLocation();
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
            navigate("/主页", {replace: false})
        setIsLogin(cookie.load('isLogin') === 'true');
    }, [navigate, location, isLogin])

    return (
        <div>
            <Routes>
                <Route path="/主页/*" element={<Home isLogin={isLogin} user={user}/>}/>
                <Route path="/注册" element={<SignBox isLogin={isLogin} user={user}/>}/>
                <Route path="/个人中心/*" element={<Pencen isLogin={isLogin} user={user}/>}/>
                <Route path="/忘记密码/*" element={<Forget user={user}/>}/>
            </Routes>
        </div>
    )
}


export default App;