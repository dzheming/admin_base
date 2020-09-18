const routes = [
  {
    path: "/user",
    layout: false,
    routes: [
      {
        name: "login",
        path: "/user/login",
        component: "./user/login"
      }
    ]
  },
  {
    path: "/welcome",
    name: "欢迎",
    icon: "smile",
    component: "./Welcome"
  },
  {
    path: "/admin",
    name: "admin",
    icon: "crown",
    access: "canAdmin",
    routes: [
      {
        path: "/admin/sub-page",
        name: "sub-page",
        icon: "smile",
        component: "./Admin"
      }
    ]
  },
  {
    path: "/editor",
    name: "editor",
    icon: "crown",
    access: "canEditor",
    routes: [
      {
        path: "/editor/sub-page",
        name: "sub-page",
        icon: "smile",
        component: "./Editor"
      }
    ]
  },
  {
    path: "/guest",
    name: "guest",
    icon: "crown",
    access: "canGuest",
    routes: [
      {
        path: "/guest/sub-page",
        name: "sub-page",
        icon: "smile",
        component: "./Guest"
      }
    ]
  },
  {
    name: "查询表格",
    icon: "table",
    path: "/list",
    component: "./ListTableList"
  },
  {
    path: "/",
    redirect: "/welcome",
  },
  {
    component: "./404"
  }
];

export default routes;
