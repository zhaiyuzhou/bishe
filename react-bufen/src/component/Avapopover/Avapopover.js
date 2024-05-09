import React, {useState} from 'react';
import {Avatar, Button} from 'antd';
import {LeftOutlined, UserOutlined} from '@ant-design/icons';
import './avapopo.css'
import {Link} from 'react-router-dom';

const Avapopover = (props) => {

  const [ome, setOme] = useState(false);

  const display = props.style.display;

  const bianDa = () => {
    setOme(true);
  };

  const bianXi = () => {
    setOme(false);
  };


  return (
    <div className='ava-popo' style={{display:display}} onMouseEnter={bianDa} onMouseLeave={bianXi}>
        <Avatar icon={<UserOutlined/>} size={(ome ? 'large' : 'default')} src={props.avatar}/>
      <div className='content' style={{ display: (ome ? 'inline-block' : 'none') }}>
          <Link to={"/个人中心"}><Button type="text" icon={<LeftOutlined/>}>个人中心</Button></Link>
      </div>
    </div>
  );
}
export default Avapopover;