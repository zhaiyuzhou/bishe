import React from 'react';
import ReactDOM from 'react-dom/client';
import App from './component/App/App';
import LoginBox from './component/LoginBox/LoginBox';


// 主界面
const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  <React.StrictMode>
    <App></App>
  </React.StrictMode>
);

// // 登陆界面
// const login = ReactDOM.createRoot(document.getElementById('login'));
// login.render(
//   <React.StrictMode>
//     <LoginBox></LoginBox>
//   </React.StrictMode>
// );
