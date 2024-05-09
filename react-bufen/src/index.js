import React from 'react';
import ReactDOM from 'react-dom/client';
import {ConfigProvider} from 'antd';
import {BrowserRouter} from 'react-router-dom'
import App from './component/App/App';

// 主界面
const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
    <React.StrictMode>
        <BrowserRouter basename="/">
            <ConfigProvider
                theme={{
                    token: {
                        colorPrimary: "#52c41a",
                        colorInfo: "#52c41a",
                        colorSuccess: "#52c41a"
                    },
                }}
            >
                <App></App>
            </ConfigProvider>
        </BrowserRouter>
    </React.StrictMode>

);