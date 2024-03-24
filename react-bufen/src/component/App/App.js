import './app.css'
import React, { useState } from 'react';
import { Button, ConfigProvider, Breadcrumb, Layout, theme } from 'antd';
import LoginBox from '../LoginBox/LoginBox';

const { Header, Content, Footer } = Layout;
const App = () => {
  const {
    token: { colorBgContainer, borderRadiusLG },
  } = theme.useToken();

  const [isModalOpen, setIsModalOpen] = useState(false);

  const showModal = () => {
    setIsModalOpen(true);
  };

  const closeModel = () => {
    setIsModalOpen(false);
  };


  return (
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
      <div className='model' style={{display:(isModalOpen ? 'inline-block' : 'none')}}>
          <div className='mask' onClick={closeModel}></div>
          <LoginBox />
      </div>
      <Layout>
        <Header className='header-style'>
          <div className="demo-logo" />
          <Button type='primary' className='login-but' onClick={showModal}>登陆</Button>
        </Header>
        <Content
          style={{
            padding: '0 48px',
          }}
        >
          <Breadcrumb
            style={{
              margin: '16px 0',
            }}
            items={[
              { title: "Home" },
              { title: "List" },
              { title: "App" }
            ]}
          >
          </Breadcrumb>
          <div
            style={{
              padding: 24,
              minHeight: 380,
              background: colorBgContainer,
              borderRadius: borderRadiusLG,
            }}
          >
            Content
          </div>
        </Content>
        <Footer
          style={{
            textAlign: 'center',
          }}
        >
          Ant Design ©{new Date().getFullYear()} Created by Ant UED
        </Footer>
      </Layout>
    </ConfigProvider>

  );
};
export default App;