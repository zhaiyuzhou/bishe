import React from 'react';
import {LockOutlined, UserOutlined} from '@ant-design/icons';
import {Link} from "react-router-dom";
import {Button, Checkbox, Form, Input, notification} from 'antd';
import './login.css'
import qs from 'qs'
import axios from 'axios';

const LoginForm = () => {

    const [api, contextHolder] = notification.useNotification();
  const onFinish = (values) => {
    const data = qs.stringify(values);
      axios.post('/api/login', data, {
          headers: {
              'content-type': 'application/x-www-form-urlencoded'
          },
      }).then((response) => {
          console.log(response);
          if (response.data.success) {
              window.location.assign('/');
          } else {
              api.open({
                  message: response.data.message,
              });
          }
      })
  };

  return (
    <Form
      name="normal_login"
      className="login-form"
      initialValues={{
        remember: true,
      }}
      onFinish={onFinish}
    >
        {contextHolder}
      <Form.Item
        name="username"
        className='input-up'
        rules={[
          {
            required: true,
            message: '请输入你的用户名!',
          },
        ]}
      >
        <Input prefix={<UserOutlined className="site-form-item-icon" />} placeholder="用户名" />
      </Form.Item>
      <Form.Item
        name="password"
        className='input-up'
        rules={[
          {
            required: true,
            message: '请输入密码!',
          },
        ]}
      >
        <Input
          prefix={<LockOutlined className="site-form-item-icon" />}
          type="password"
          placeholder="密码"
        />
      </Form.Item>
      <Form.Item>
        <Form.Item name="remember" valuePropName="checked" noStyle>
          <Checkbox>记住我</Checkbox>
        </Form.Item>
          <Link className="login-form-forgot" to="/忘记密码">忘记密码</Link>
      </Form.Item>

      <Form.Item>
        <Button type="primary" htmlType="submit" className="login-form-button">
          登陆
        </Button>
        Or
        <Link to={"/注册"}>注册!</Link>
      </Form.Item>
    </Form>
  );
};

export default LoginForm;