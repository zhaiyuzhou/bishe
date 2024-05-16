import React, {useState} from 'react';
import {Avatar, Button} from 'antd';
import {PoweroffOutlined, UserOutlined, CommentOutlined} from '@ant-design/icons';
import './avapopo.css'
import {useNavigate} from 'react-router';
import axios from 'axios';

const Avapopover = (props) => {

    const navigate = useNavigate()

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
      <div className='ava-popo' style={{display: display}} onMouseEnter={bianDa} onMouseLeave={bianXi}>
          <Avatar className='ava-img' icon={<UserOutlined/>} size={(ome ? 64 : 'large')} src={props.avatar}/>
      <div className='content' style={{ display: (ome ? 'inline-block' : 'none') }}>
          <div style={{width: "100%", position: "relative", height: "30px"}}>
              <p className='ava-nickName'>{props.nickName}</p>
          </div>
          <Button className='ava-button-1' type="text" icon={<UserOutlined/>} onClick={() => {
              navigate("/个人中心", {replace: true})
          }}>个人中心</Button>
          <Button className='ava-button-2' type="text" icon={<CommentOutlined/>} onClick={() => {
              navigate("/消息中心", {replace: true})
          }}>消息中心</Button>
          <Button className='ava-button-3' type="text" danger icon={<PoweroffOutlined/>}
                  onClick={loginout}>退出登录</Button>
      </div>
    </div>
  );
}
export default Avapopover;