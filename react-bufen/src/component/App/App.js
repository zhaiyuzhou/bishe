import './app.css'
import React, {useState} from 'react';
import {Breadcrumb, Button, Input, Layout, Menu, theme} from 'antd';
import {HomeOutlined, UnorderedListOutlined} from '@ant-design/icons';
import {Route, Routes, useNavigate} from 'react-router'
import LoginBox from '../LoginBox/LoginBox';
import Avapopover from '../Avapopover/Avapopover';
import Sider from 'antd/es/layout/Sider';
import cookie from 'react-cookies'
import Pubdyn from '../Pubdyn/Pubdyn';
import Dynamicbody from '../../pages/Dynamicbody/Dynamicbody';

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
    const tagsEn = ['news', 'movie', 'show', 'animated', 'bangumi', 'game', 'music', 'art', 'animal', 'knowledge', 'technology', 'food', 'car', 'movement', 'live', 'other']

  const items2 = [
    {
        key: '/home',
      icon: React.createElement(HomeOutlined),
      label: '主页',
    },
    {
      key: 'sider2',
      icon: React.createElement(UnorderedListOutlined),
      label: '分类',
      children: tags.map((tag, index) => {
        return {
            key: '/' + tag,
          label: tag
        }
      })
    }
  ]

    // 检查登录
    const isLogin = (cookie.load('isLogin') === 'true');

    const navigate = useNavigate()

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
                  width: 200,
                borderRight: 0,
                  position: 'fixed',
              }}
              items={items2}
              onClick={(e) => {
                  navigate(e.key, {replace: true})
              }}
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
                <Pubdyn/>
                <Routes>
                    <Route path='/home' element={<Dynamicbody/>}/>
                    {tags.map((tag, index) => {
                        return (
                            <Route path={'/' + tag} element={<Dynamicbody tag={tagsEn[index]}/>}/>
                        )
                    })}
                </Routes>
            </Content>
          </Layout>
        </Layout>
      </Layout>
    </div>

  );
};
export default App;