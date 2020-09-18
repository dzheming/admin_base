declare namespace API {
  export interface CurrentUser {
    avatar?: string;
    name?: string;
    title?: string;
    groups?: string;
    signature?: string;
    username?: string;
    access?: 'editor' | 'guest' | 'admin';
  }

  export interface ResponseCommonType {
    success?: boolean;
    errorCode?: string;
    errorMessage?: string;
    data?: any;
  }
}
