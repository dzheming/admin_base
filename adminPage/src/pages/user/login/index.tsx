import { Alert, Modal, message } from 'antd';
import React, { useState } from 'react';
import { Link, useModel } from 'umi';
import { getPageQuery } from '@/utils/utils';
import logo from '@/assets/logo.svg';
import { LoginParamsType, fakeAccountLogin } from '@/services/login';
import LoginFrom from './components/Login';
import styles from './style.less';

const { Tab, Username, Password, Submit } = LoginFrom;

const LoginMessage: React.FC<{
  content: string;
}> = ({ content }) => (
  <Alert
    style={{
      marginBottom: 24,
    }}
    message={content}
    type="error"
    showIcon
  />
);

/**
 * 此方法会跳转到 redirect 参数所在的位置
 */
const replaceGoto = () => {
  const urlParams = new URL(window.location.href);
  const params = getPageQuery();
  let { redirect } = params as { redirect: string };
  if (redirect) {
    const redirectUrlParams = new URL(redirect);
    if (redirectUrlParams.origin === urlParams.origin) {
      redirect = redirect.substr(urlParams.origin.length);
      if (redirect.match(/^\/.*#/)) {
        redirect = redirect.substr(redirect.indexOf('#'));
      }
    } else {
      window.location.href = '/';
      return;
    }
  }
  window.location.href = urlParams.href.split(urlParams.pathname)[0] + (redirect || '/');
};

const Login: React.FC<{}> = () => {
  const [userLoginState, setUserLoginState] = useState<API.ResponseCommonType>({});
  const [submitting, setSubmitting] = useState(false);

  const { refresh } = useModel('@@initialState');
  const [type, setType] = useState<string>('account');

  const handleSubmit = async (values: LoginParamsType) => {
    setSubmitting(true);
    try {
      // 登录
      const msg = await fakeAccountLogin({ ...values, type });
      if (msg.errorCode === "100") {
        message.success('登录成功！');
        localStorage.setItem("accessToken", msg.data.accessToken);
        localStorage.setItem("tokenType", msg.data.tokenType);
        replaceGoto();
        setTimeout(() => {
          refresh();
        }, 0);
        return;
      }
      // 如果失败去设置用户错误信息
      setUserLoginState(msg);
    } catch (error) {
      message.error('登录失败，请重试！');
    }
    setSubmitting(false);
  };

  const { errorCode, errorMessage } = userLoginState;
  const info = () => {
    Modal.info({
      title: '无法修改密码',
      content: (
        <div>
          <p>内部系统，不提供修改密码功能</p>
          <p>请联系管理帮忙重置密码</p>
        </div>
      ),
    });
  }
  return (
    <div className={styles.container}>
      <div className={styles.lang}>
      </div>
      <div className={styles.content}>
        <div className={styles.top}>
          <div className={styles.header}>
            <Link to="/">
              <img alt="logo" className={styles.logo} src={logo} />
              <span className={styles.title}>Admin Manager</span>
            </Link>
          </div>
        </div>

        <div className={styles.main}>
          <LoginFrom activeKey={type} onTabChange={setType} onSubmit={handleSubmit}>
            <Tab key="account" tab="">
              { errorCode && !submitting && errorMessage &&  (
                <LoginMessage content={errorMessage} />
              )}

              <Username
                name="username"
                placeholder="用户名: 请输入用户名"
                rules={[
                  {
                    required: true,
                    message: '请输入用户名!',
                  },
                ]}
              />
              <Password
                name="password"
                placeholder="密码: 请输入密码"
                rules={[
                  {
                    required: true,
                    message: '请输入密码！',
                  },
                ]}
              />
            </Tab>
            <div>
              <a
                style={{
                  float: 'right',
                }}
                onClick={info}
              >
                忘记密码
              </a>
            </div>
            <Submit loading={submitting}>登录</Submit>
          </LoginFrom>
            </div>
        </div>
      <div className={styles.footer}>
        Copyright 
        <span role="img" aria-label="copyright" className={styles.footerCopyright}>
        <svg viewBox="64 64 896 896" focusable="false" data-icon="copyright" width="1em" height="1em" fill="currentColor" aria-hidden="true"><path d="M512 64C264.6 64 64 264.6 64 512s200.6 448 448 448 448-200.6 448-448S759.4 64 512 64zm0 820c-205.4 0-372-166.6-372-372s166.6-372 372-372 372 166.6 372 372-166.6 372-372 372zm5.6-532.7c53 0 89 33.8 93 83.4.3 4.2 3.8 7.4 8 7.4h56.7c2.6 0 4.7-2.1 4.7-4.7 0-86.7-68.4-147.4-162.7-147.4C407.4 290 344 364.2 344 486.8v52.3C344 660.8 407.4 734 517.3 734c94 0 162.7-58.8 162.7-141.4 0-2.6-2.1-4.7-4.7-4.7h-56.8c-4.2 0-7.6 3.2-8 7.3-4.2 46.1-40.1 77.8-93 77.8-65.3 0-102.1-47.9-102.1-133.6v-52.6c.1-87 37-135.5 102.2-135.5z"></path>
        </svg>
        </span> 
        2020 design by zm Abel
      </div>
    </div>
  );
};

export default Login;
