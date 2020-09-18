import { request } from 'umi';

export interface LoginParamsType {
  username: string;
  password: string;
  mobile: string;
  captcha: string;
  type: string;
}

export async function fakeAccountLogin(params: LoginParamsType) {
  return request<API.ResponseCommonType>('/api/auth/login', {
    method: 'POST',
    data: params,
  });
}


export async function outLogin() {
  return request('/api/auth/outLogin');
}
