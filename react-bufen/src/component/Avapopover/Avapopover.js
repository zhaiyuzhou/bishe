import React, {useState} from 'react';
import {Avatar, Button} from 'antd';
import {LeftOutlined, PoweroffOutlined, UserOutlined} from '@ant-design/icons';
import './avapopo.css'
import {Link} from 'react-router-dom';
import axios from 'axios';

const Avapopover = (props) => {

  const [ome, setOme] = useState(false);

  const display = props.style.display;

  const bianDa = () => {
    setOme(true);
  };

  const bianXi = () => {
    setOme(false);
  };

    const loginout = () => {
        axios.get("/api/loginout").then((res) => {
            window.location.href = "/";
        });
    }

  return (
    <div className='ava-popo' style={{display:display}} onMouseEnter={bianDa} onMouseLeave={bianXi}>
        <Avatar icon={<UserOutlined/>} size={(ome ? 'large' : 'default')} src={props.avatar}/>
      <div className='content' style={{ display: (ome ? 'inline-block' : 'none') }}>
          <Link to={"/个人中心"}><Button type="text" icon={<LeftOutlined/>}>个人中心</Button></Link>
          <Button type="text" danger icon={<PoweroffOutlined/>} onClick={loginout}>退出登录</Button>
      </div>
    </div>
  );
}
export default Avapopover;