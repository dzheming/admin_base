import { Settings as LayoutSettings } from '@ant-design/pro-layout';

export default {
  "navTheme": "dark",
  "primaryColor": "#13C2C2",
  "layout": "side",
  "contentWidth": "Fluid",
  "fixedHeader": false,
  "fixSiderbar": true,
  "menu": {
    "locale": false
  },
  "logo": "https://s1.ax1x.com/2020/07/02/NqJNfP.png",
  "title": "Admin Manager",
  "pwa": false,
  "iconfontUrl": "",
  "splitMenus": false,
} as LayoutSettings & {
  pwa: boolean;
};
