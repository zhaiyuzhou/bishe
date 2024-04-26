import React from 'react';
import ReactDOM from 'react-dom/client';
import {ConfigProvider} from 'antd';
import {BrowserRouter} from 'react-router-dom'
import Pencen from './component/Pencen/Pencen';

// 主界面
const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  <React.StrictMode>
      <BrowserRouter>
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
        {/* <SignBox></SignBox> */}
              {/* <App></App> */}
              <Pencen></Pencen>
        {/* <Dynamic></Dynamic> */}
        {/* <Pubdyn></Pubdyn> */}
          </ConfigProvider>
      </BrowserRouter>
  </React.StrictMode>

);