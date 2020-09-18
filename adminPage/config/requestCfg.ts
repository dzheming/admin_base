import { notification } from 'antd';
import { ResponseError } from 'umi-request';

const codeMessage = {
  200: '服务器成功返回请求的数据。',
  201: '新建或修改数据成功。',
  202: '一个请求已经进入后台排队（异步任务）。',
  204: '删除数据成功。',
  400: '发出的请求有错误，服务器没有进行新建或修改数据的操作。',
  401: '用户没有权限（令牌、用户名、密码错误）。',
  403: '用户得到授权，但是访问是被禁止的。',
  404: '发出的请求针对的是不存在的记录，服务器没有进行操作。',
  405: '请求方法不被允许。',
  406: '请求的格式不可得。',
  410: '请求的资源被永久删除，且不会再得到的。',
  422: '当创建一个对象时，发生一个验证错误。',
  500: '服务器发生错误，请检查服务器。',
  502: '网关错误。',
  503: '服务不可用，服务器暂时过载或维护。',
  504: '网关超时。',
};

const errorHandler = (error: ResponseError) => {
  const { response } = error;
  if (response && response.status) {
    const errorText = codeMessage[response.status] || response.statusText;
    const { status, url } = response;
    console.log(url)
    notification.error({
      message: `请求错误 ${status}`,
      description: `${errorText}`,
    });
  }

  if (!response) {
    notification.error({
      message: `请求错误`,
      description: `${response}`,
    });
  }

  if (!response) {
    notification.error({
      description: '您的网络发生异常，无法连接服务器',
      message: `网络异常 ${error}`,
    });
  }
  throw error;
};

const requestInterceptor = (url: string, options: any) => {
  let c_token = localStorage.getItem("accessToken");
  let c_token_type = localStorage.getItem("tokenType");
  const headers = {
    'Content-Type': 'application/json',
    'Accept': 'application/json',
  };
  if (c_token && c_token_type) {
    const token = {
      ...headers,
      Authorization: c_token_type + " " + c_token
    };
    return (
      {
        url: url,
        options: { ...options, headers: token},
      }
    );
  } else {
    return (
      {
        url: url,
        options: { ...options },
      }
    );
  }
}

const responseInterceptor = (response: any, options: any) => {
  let token = response.headers.get("access_token");
  if (token) {
    localStorage.setItem("accessToken", token);
  }
  return response;
}

export default {errorHandler, requestInterceptor, responseInterceptor}