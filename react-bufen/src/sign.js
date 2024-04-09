import React from 'react';
import ReactDOM from 'react-dom/client';
import SignBox from './component/SignBox/SignBox';
import { ConfigProvider } from 'antd';


const root = ReactDOM.createRoot(document.getElementById('sign'));
root.render(
  <React.StrictMode>
    <ConfigProvider
      theme={{
        token: {
          // Seed Token，影响范围大
          colorPrimary: '#00b96b',
          borderRadius: 2,

          // 派生变量，影响范围小
          colorBgContainer: '#f6ffed',
        },
      }}
    >
      <SignBox></SignBox>
    </ConfigProvider>
  </React.StrictMode>

);
