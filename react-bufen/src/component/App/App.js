import './app.css'
import React, { useState } from 'react';
import { Button, Breadcrumb, Layout, theme, Input, Menu } from 'antd';
import { LaptopOutlined, NotificationOutlined, UserOutlined } from '@ant-design/icons';
import LoginBox from '../LoginBox/LoginBox';
import Avapopover from '../Avapopover/Avapopover';
import Sider from 'antd/es/layout/Sider';
const { Search } = Input;

const { Header, Content } = Layout;
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

  const onSearch = (value, _e, info) => console.log(info?.source, value);

  const items2 = [UserOutlined, LaptopOutlined, NotificationOutlined].map((icon, index) => {
    const key = String(index + 1);
    return {
      key: `sub${key}`,
      icon: React.createElement(icon),
      label: `subnav ${key}`,
      children: new Array(4).fill(null).map((_, j) => {
        const subKey = index * 4 + j + 1;
        return {
          key: subKey,
          label: `option${subKey}`,
        };
      }),
    };
  });

  return (

    <Layout>
      <div className='model' style={{ display: (isModalOpen ? 'inline-block' : 'none') }}>
        <div className='mask' onClick={closeModel}></div>
        <LoginBox />
      </div>
      <Header className='header-style'>
        <div className="demo-logo" />
        <Search
          className='search'
          placeholder="input search text"
          onSearch={onSearch}
          style={{
            width: 500,
          }}
        />
        <Button type='primary' className='login-but' style={{ display: 'none' }} onClick={showModal}>登陆</Button>
        <Avapopover />
      </Header>
      <Layout>
        <Sider
          width={200}
          style={{
            background: colorBgContainer,
          }}
        >
          <Menu
            mode="inline"
            defaultSelectedKeys={['1']}
            defaultOpenKeys={['sub1']}
            style={{
              height: '100%',
              borderRight: 0,
            }}
            items={items2}
          />
        </Sider>
        <Layout
          style={{
            padding: '0 24px 24px',
          }}
        >
          <Breadcrumb
            style={{
              margin: '16px 0',
            }}
          >
          </Breadcrumb>
          <Content
            style={{
              padding: 24,
              margin: 0,
              minHeight: 280,
              background: colorBgContainer,
              borderRadius: borderRadiusLG,
            }}
          >
            Content
          </Content>
        </Layout>
      </Layout>
    </Layout>

  );
};
export default App;