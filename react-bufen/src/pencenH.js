import React from 'react';
import ReactDOM from 'react-dom/client';
import {ConfigProvider} from 'antd';
import Pencen from './component/Pencen/Pencen';
import {BrowserRouter} from 'react-router-dom'


const root = ReactDOM.createRoot(document.getElementById('pencen'));

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
                <Pencen></Pencen>
            </ConfigProvider>
        </BrowserRouter>
    </React.StrictMode>
);