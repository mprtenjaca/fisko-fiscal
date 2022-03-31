
import Dashboard from "views/Dashboard.js";
import Notifications from "views/Notifications.js";
import Icons from "views/Icons.js";
import Typography from "views/Typography.js";
import TableList from "views/TableList.js";
import Upgrade from "views/Upgrade.js";
import UserPage from "views/UserPage.js";
import Customers from "views/Customers";
import OutputInvoice from "views/OutputInvoice";
import InputInvoice from "views/InputInvoice";
import Services from "views/Services";

var dashRoutes = [
  {
    path: "/dashboard",
    name: "Dashboard",
    icon: "design_app",
    component: Dashboard,
    layout: "/admin",
  },
  // {
  //   path: "/login",
  //   name: "Login",
  //   icon: "business_badge",
  //   component: Login,
  //   layout: "/admin",
  // },
  {
    path: "/icons",
    name: "Icons",
    icon: "design_image",
    component: Icons,
    layout: "/admin",
  },
  {
    path: "/notifications",
    name: "Notifications",
    icon: "ui-1_bell-53",
    component: Notifications,
    layout: "/admin",
  },
  {
    path: "/user-page",
    name: "User Profile",
    icon: "users_circle-08",
    component: UserPage,
    layout: "/admin",
  },
  {
    path: "/customers",
    name: "Customers",
    icon: "users_single-02",
    component: Customers,
    layout: "/admin",
  },
  {
    path: "/output-invoice",
    name: "Output Invoice",
    icon: "files_paper",
    component: OutputInvoice,
    layout: "/admin",
  },
  {
    path: "/input-invoice",
    name: "Input Invoice",
    icon: "files_single-copy-04",
    component: InputInvoice,
    layout: "/admin",
  },
  {
    path: "/services",
    name: "Services",
    icon: "ui-2_settings-90",
    component: Services,
    layout: "/admin",
  },
  {
    path: "/extended-tables",
    name: "Table List",
    icon: "files_paper",
    component: TableList,
    layout: "/admin",
  },
  {
    path: "/typography",
    name: "Typography",
    icon: "design-2_ruler-pencil",
    component: Typography,
    layout: "/admin",
  },
  // {
  //   pro: true,
  //   path: "/upgrade",
  //   name: "Upgrade to PRO",
  //   icon: "objects_spaceship",
  //   component: Upgrade,
  //   layout: "/admin",
  // },
];
export default dashRoutes;
