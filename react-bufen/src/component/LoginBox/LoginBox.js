import React, {useEffect, useRef} from 'react';
import LoginForm from './LoginForm';
import './login.css'

const LoginBox = ({}) => {
    const ref = useRef(null);

    useEffect(() => {

    }, []);

    return (
    <div className='loginBox'>
        <LoginForm></LoginForm>
    </div>
    );
};

export default LoginBox;
