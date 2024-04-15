import './app.css'
import React, {useState} from 'react';
import {Breadcrumb, Button, Input, Layout, Menu, theme} from 'antd';
import {HomeOutlined, UnorderedListOutlined} from '@ant-design/icons';
import LoginBox from '../LoginBox/LoginBox';
import Avapopover from '../Avapopover/Avapopover';
import Sider from 'antd/es/layout/Sider';
import Dynamic from '../Dynamic/Dynamic';
import cookie from 'react-cookies'
import Pubdyn from '../Pubdyn/Pubdyn';

const { Search } = Input;

const { Header, Content } = Layout;
const App = () => {
  const {
    token: { colorBgContainer, borderRadiusLG },
  } = theme.useToken();

  // 登陆气泡框显示
  const [isModalOpen, setIsModalOpen] = useState(false);

  const showModal = () => {
    setIsModalOpen(true);
  };

  const closeModel = () => {
    setIsModalOpen(false);
  };

  // 搜索内容
  const onSearch = (value, _e, info) => console.log(info?.source, value);

  // 标签
  const tags = ['新闻', '电影', '电视剧', '动画', '番剧', '游戏', '音乐', '美术', '动物', '知识', '科技', '美食', '汽车', '运动', '生活', '其他']

  const items2 = [
    {
      key: 'sider1',
      icon: React.createElement(HomeOutlined),
      label: '主页',
    },
    {
      key: 'sider2',
      icon: React.createElement(UnorderedListOutlined),
      label: '分类',
      children: tags.map((tag, index) => {
        return {
          key: 'category' + index,
          label: tag
        }
      })
    }
  ]

    const isLogin = (cookie.load('islogin') == 'true');

  return (
    <div className='index'>
      <div className='model' style={{ display: (isModalOpen ? 'inline-block' : 'none') }}>
        <div className='mask' onClick={closeModel}></div>
        <LoginBox />
      </div>
      <Layout>
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
            <Button type='primary' className='login-but' style={{display: (isLogin ? 'none' : 'inline-block')}}
                    onClick={showModal}>登陆</Button>
            <Avapopover style={{display: (isLogin ? 'inline-block' : 'none')}}/>
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
                minWidth: 800,
                background: colorBgContainer,
                borderRadius: borderRadiusLG,
              }}
            >
                <Pubdyn></Pubdyn>
              <Dynamic></Dynamic>
              <Dynamic></Dynamic>
              <Dynamic></Dynamic>
              <Dynamic></Dynamic>
              <Dynamic></Dynamic>
              <Dynamic></Dynamic>
            </Content>
          </Layout>
        </Layout>
      </Layout>
    </div>

  );
};
export default App;