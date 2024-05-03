import './app.css'
import React, {useEffect} from 'react';
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

    const navigate = useNavigate()
  const {
    token: { colorBgContainer, borderRadiusLG },
  } = theme.useToken();

    useEffect(() => {

        // 登陆气泡框显示
        let model = document.querySelector(".model");

        const showModal = () => {
            model.style.display = "inline-block";
        };

        const closeModel = () => {
            model.style.display = "none";
        };

        let mask = document.querySelector(".mask");
        let loginBut = document.querySelector(".login-but");

        mask.addEventListener("click", closeModel);
        loginBut.addEventListener("click", showModal);
        navigate('/home', {replace: true});

        function updatePosition() {
            const element = document.querySelector(".model"); // 获取你想要居中的元素

            element.style.position = 'absolute'; // 设置元素的定位为绝对定位
            element.style.top = window.scrollY + 'px'; // 设置元素的上边距，使其垂直居中
        }

        updatePosition();
        window.addEventListener('scroll', updatePosition);

        // 在组件卸载时移除事件监听器
        return () => {
            mask.removeEventListener("click", closeModel);
            loginBut.removeEventListener("click", showModal);
            window.removeEventListener('scroll', updatePosition);
        };
    }, []);

  // 搜索内容
  const onSearch = (value, _e, info) => console.log(info?.source, value);

  // 标签
    const tags = ['新闻', '电影', '电视剧', '动画', '番剧', '游戏', '音乐', '美术', '动物', '知识', '科技', '美食', '汽车', '运动', '生活', '其他'];
    const tagsEn = ['news', 'movie', 'show', 'animated', 'bangumi', 'game', 'music', 'art', 'animal', 'knowledge', 'technology', 'food', 'car', 'movement', 'live', 'other'];

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

  return (
      <div>
          <div className='model'
               style={{display: 'none', width: (window.innerWidth - 18), height: (window.innerHeight)}}>
              <div className='mask'></div>
        <LoginBox />
      </div>
          <div className='index'>
              <Layout>
                  <Header className='header-style'>
                      <div className="demo-logo"/>
                      <Search
                          className='search'
                          placeholder="input search text"
                          onSearch={onSearch}
                          style={{
                              width: 500,
                          }}
                      />
                      <Button type='primary' className='login-but'
                              style={{display: (isLogin ? 'none' : 'inline-block')}}
                      >登陆</Button>
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
                              defaultOpenKeys={['sider2']}
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
                                  padding: 10,
                                  margin: 0,
                                  minHeight: 900,
                                  minWidth: 800,
                                  background: "rgb(231, 231, 231)",
                                  borderRadius: borderRadiusLG,
                              }}
                          >
                              <Pubdyn/>
                <Routes>
                    <Route path='/home' element={<Dynamicbody/>}/>
                    {tags.map((tag, index) => {
                        return (
                            <Route key={'/' + tag} path={'/' + tag} element={<Dynamicbody tag={tagsEn[index]}/>}/>
                        )
                    })}
                </Routes>
                          </Content>
                      </Layout>
                  </Layout>
              </Layout>
          </div>
    </div>

  );
};
export default App;