import React from 'react';
import ReactDOM from 'react-dom/client';
import App from './component/App/App';
import { ConfigProvider } from 'antd';
import Dynamic from './component/Dynamic/Dynamic';
import PubDyn from './component/Pubdyn/Pubdyn';

// 主界面
const root = ReactDOM.createRoot(document.getElementById('root'));
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
      {/* <App></App> */}
      {/* <Dynamic></Dynamic> */}
      <PubDyn></PubDyn>
    </ConfigProvider>
  </React.StrictMode>

);