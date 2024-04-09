import React, { useState } from 'react';
import { Popover, Avatar } from 'antd';
import { UserOutlined } from '@ant-design/icons';
import './avapopo.css'

const Avapopover = () => {

  const [ome,setOme] = useState(false);

  const bianDa = () => {
    setOme(true);
  };

  const bianXi = () => {
    setOme(false);
  };


  return (
    <div className='ava-popo' onMouseEnter={bianDa} onMouseLeave={bianXi}>
      <Avatar icon={<UserOutlined />} size={(ome ? 'large' : 'default')}/>
      <div className='content' style={{display:(ome ? 'inline-block' : 'none')}}>
      </div>
    </div>
  );
}
export default Avapopover;