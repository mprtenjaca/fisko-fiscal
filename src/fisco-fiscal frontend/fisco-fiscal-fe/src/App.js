import React, { useEffect } from "react";
import { Redirect } from "react-router-dom";
import { Switch } from "react-router-dom";
import { BrowserRouter as Router, Route } from "react-router-dom";
import { useDispatch, useSelector } from "react-redux";
import { refreshToken, logout } from "redux/actions/authAction";
import PageRender from "components/CustomRenderer/pageRender";
import PrivateRouter from "components/CustomRenderer/privateRouter";
import { getCustomers } from "redux/actions/customerAction";
import { useHistory } from "react-router-dom";
import Admin from "views/Admin";
import Login from "views/Login";
import { getOutputInvoices } from "redux/actions/outputInvoiceAction";
import { getServices } from "redux/actions/serviceAction";
import { getCompany } from "redux/actions/companyAction";

const App = () => {
  const { auth } = useSelector((state) => state);
  const dispatch = useDispatch();
  const history = useHistory();

  useEffect(() => {
    dispatch(refreshToken());
  }, [dispatch]);

  useEffect(() => {
    if (auth.token) {
      dispatch(getCustomers(auth));
      dispatch(getOutputInvoices(auth));
      dispatch(getServices(auth));
      dispatch(getCompany(auth));
    }
    dispatch(refreshToken());

    history.push(history.location.pathname);
  }, [dispatch, auth.token]);


  return (
    <Router>
      <div className="App">
        <div className="main">
          <Switch>
            {/* {auth.token ? (
              <Route path="/admin" render={(props) => <Admin {...props} />} />
            ) : (
              <Route path="/login" component={Login} />
            )}
            <Redirect to={auth.token ? "/admin" : "/login"} /> */}
            
            {localStorage.getItem("tkn_fisco") && auth.token ? (
              <Route path="/admin" render={(props) => <Admin {...props} />} />
            ) : (
              <Route path="/login" component={Login} />
            )}
            <Redirect to={auth.token ? 

              history.location.pathname === "/login" ? "/admin/dashboard" : history.location.pathname
              
              : "/login"} />
          </Switch>
        </div>
      </div>
    </Router>
  );
};

//DEFAULT AND WOKRING
// {localStorage.getItem("tkn_fisco") ?
// <Route path="/admin" render={(props) => <Admin {...props} />}/>
// : <Route path="/login" component={Login} />}
// <Redirect to={auth.token ? "/admin/dashboard" : "/login"} />

export default App;
