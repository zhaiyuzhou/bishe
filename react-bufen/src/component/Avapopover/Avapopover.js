import React, { useState } from 'react';
import { Button, Avatar } from 'antd';
import { UserOutlined, LeftOutlined } from '@ant-design/icons';
import './avapopo.css'

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
      <Avatar icon={<UserOutlined />} size={(ome ? 'large' : 'default')} />
      <div className='content' style={{ display: (ome ? 'inline-block' : 'none') }}>
        <Button type="text" icon={<LeftOutlined />}>个人中心</Button>
      </div>
    </div>
  );
}
export default Avapopover;