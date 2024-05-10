import React, {useEffect, useState} from "react";
import Home from "../../pages/Home/Home";
import {useNavigate} from 'react-router';
import {Route, Routes, useLocation} from 'react-router-dom';
import SignBox from '../../pages/SignBox/SignBox';
import Pencen from "../../pages/Pencen/Pencen";
import cookie from 'react-cookies'
import axios from 'axios';

const App = () => {

    const [isLogin, setIsLogin] = useState(cookie.load('isLogin') === 'true');
    const navigate = useNavigate()
    const location = useLocation();
    useEffect(() => {
        axios.get("/api/cook");
        if (location.pathname === "/")
            navigate("/主页", {replace: false})
        setIsLogin(cookie.load('isLogin') === 'true');
    }, [navigate, location, isLogin])

    return (
        <div>
            <Routes>
                <Route path="/主页/*" element={<Home isLogin={isLogin}/>}/>
                <Route path="/注册" element={<SignBox isLogin={isLogin}/>}/>
                <Route path="/个人中心/*" element={<Pencen isLogin={isLogin}/>}/>
            </Routes>
        </div>
    )
}


export default App;